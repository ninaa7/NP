package Kolokviumski;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class InvalidOperationException extends Exception {
   public InvalidOperationException(String message) {
       super(message);
   }
}

abstract class Item implements Comparable<Item>{
   int productId;
   String productName;
   float price;

   public Item(int productId, String productName, float price) {
       this.productId = productId;
       this.productName = productName;
       this.price = price;
   }

   abstract double vkupno();

   @Override
   public int compareTo(Item o) {
       return Double.compare(vkupno(),o.vkupno());
   }
}

class WS extends Item {
   int quantity;

   public WS(int productId, String productName, int quantity, float price) {
       super(productId, productName,price);
       this.price=price;
       this.quantity = quantity;
   }

   @Override
   double vkupno() {
       return price*quantity;
   }

   @Override
   public String toString() {
       return String.format("%d - %.2f", productId, vkupno());
   }
}

class PS extends Item {
   double quantity;

   public PS(int productId, String productName, double quantity, float price) {
       super(productId,productName,price);
       this.quantity = quantity;
       this.price = price;
   }

   @Override
   double vkupno() {
       return price*(quantity/1000);
   }

   @Override
   public String toString() {
       return String.format("%d - %.2f", productId, vkupno());
   }
}

class ItemFactory {
   static Item create(String line) throws InvalidOperationException {
       String []parts = line.split(";");
       String type = parts[0];
       int id = Integer.parseInt(parts[1]);
       String name = parts[2];
       float price= Float.parseFloat(parts[3]);
       double quantity = Double.parseDouble(parts[4]);
       if(quantity==0)
       {
           throw new InvalidOperationException(String.format("The quantity of the product with id %d can not be 0.",id,quantity));
       }
       if(type.equals("WS"))
       {
           return new WS(id,name,(int)quantity,price);
       }
       else {
           return new PS(id,name,quantity,price);
       }
   }
}

class ShoppingCart{
   ArrayList<Item> items;

   public ShoppingCart() {
       this.items = new ArrayList<>();
   }

   void addItem(String itemData) throws InvalidOperationException {
       items.add(ItemFactory.create(itemData));
   }

   void printShoppingCart(OutputStream os) {
       PrintWriter pw = new PrintWriter(os);
       items.stream().sorted(Comparator.reverseOrder()).forEach(p->pw.println(p));
       pw.flush();
   }

   void blackFridayOffer(List<Integer> discountItems, OutputStream os) throws InvalidOperationException {
       PrintWriter pw = new PrintWriter(os);
       if(discountItems.isEmpty())
       {
           throw new InvalidOperationException("There are no products with discount.");
       }
       for (Item item : items) {
           if(discountItems.contains(item.productId))
           {
               pw.println(String.format("%d - %.2f", item.productId, item.vkupno() * 0.1));
           }
       }
       pw.flush();
   }
}

public class zad25 {

   public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       ShoppingCart cart = new ShoppingCart();

       int items = Integer.parseInt(sc.nextLine());
       for (int i = 0; i < items; i++) {
           try {
               cart.addItem(sc.nextLine());
           } catch (InvalidOperationException e) {
               System.out.println(e.getMessage());
           }
       }

       List<Integer> discountItems = new ArrayList<>();
       int discountItemsCount = Integer.parseInt(sc.nextLine());
       for (int i = 0; i < discountItemsCount; i++) {
           discountItems.add(Integer.parseInt(sc.nextLine()));
       }

       int testCase = Integer.parseInt(sc.nextLine());
       if (testCase == 1) {
           cart.printShoppingCart(System.out);
       } else if (testCase == 2) {
           try {
               cart.blackFridayOffer(discountItems, System.out);
           } catch (InvalidOperationException e) {
               System.out.println(e.getMessage());
           }
       } else {
           System.out.println("Invalid test case");
       }
   }
}
