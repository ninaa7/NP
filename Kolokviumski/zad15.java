package Kolokviumski;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class AmountNotAllowedException extends Exception
{
   public AmountNotAllowedException(int amount) {
       super(String.format("Receipt with amount %d is not allowed to be scanned", amount));
   }
}

class Article {
   private int amount;
   private String type;

   public Article(int amount, String type) {
       this.amount = amount;
       this.type = type;
   }

   public double getTax() {
       if (type.equals("A")) {
           return amount * 0.18 * 0.15;
       } else if (type.equals("B")) {
           return amount * 0.05 * 0.15;
       } else {
           return 0;
       }
   }

   public int getAmount() {
       return amount;
   }

}

class Receipt {
   private String id;
   private List<Article> list;

   public Receipt(String id, List<Article> list) {
       this.id = id;
       this.list = list;
   }

   public static Receipt create(String str) {
       String[] split = str.split("\\s+");
       String id = split[0];
       List<Article> list = new ArrayList<>();

       for (int i = 1; i < split.length; i += 2) {
           list.add(new Article(Integer.parseInt(split[i]), split[i + 1]));
       }

       Receipt r = new Receipt(id, list);

       try {
           if(r.totalAmount()>30000)
           {
               throw new AmountNotAllowedException(r.totalAmount());
           }
           else
               return r;
       }
       catch (AmountNotAllowedException e)
       {
           System.out.println(e.getMessage());
           return null;
       }
   }

   public int totalAmount() {
       return list.stream().mapToInt(Article::getAmount).sum();
   }

   public double totalTaxReturn() {
       return list.stream().mapToDouble(Article::getTax).sum();
   }

   @Override
   public String toString() {
       return String.format("%s %d %.2f", id, totalAmount(), totalTaxReturn());
   }
}

class MojDDV {
   private List<Receipt> list;

   public MojDDV() {
       this.list = new ArrayList<>();
   }

   void readRecords(InputStream is) throws Exception, AmountNotAllowedException {
       BufferedReader br = new BufferedReader(new InputStreamReader(is));
       list = br.lines().map(Receipt::create).collect(Collectors.toList());
   }

   void printTaxReturns(OutputStream os) {
       PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
       StringBuilder builder = new StringBuilder();
       for (Receipt r:list)
       {
           if(r!=null)
           {
               pw.println(r.toString());
           }
       }
       pw.flush();
   }
}

public class zad15 {

   public static void main(String[] args) throws Exception {

       MojDDV mojDDV = new MojDDV();

       System.out.println("===READING RECORDS FROM INPUT STREAM===");
       mojDDV.readRecords(System.in);

       System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
       mojDDV.printTaxReturns(System.out);

   }
}
