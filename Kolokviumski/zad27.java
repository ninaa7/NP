package Kolokviumski;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Risk {
   void processAttacksData (InputStream is)
   {
       Scanner sc = new Scanner(is);
       while(sc.hasNextLine())
       {
           String []red = sc.nextLine().split(";");
           String prv = red[0];
           String vtor = red[1];
           ArrayList<Integer> lista1 = new ArrayList<>();
           ArrayList<Integer> lista2 = new ArrayList<>();
           String []prvDel = prv.split(" ");
           lista1.add(Integer.valueOf(prvDel[0]));
           lista1.add(Integer.valueOf(prvDel[1]));
           lista1.add(Integer.valueOf(prvDel[2]));
           String []vtorDel = vtor.split(" ");
           lista2.add(Integer.valueOf(vtorDel[0]));
           lista2.add(Integer.valueOf(vtorDel[1]));
           lista2.add(Integer.valueOf(vtorDel[2]));

           Collections.sort(lista1);
           Collections.sort(lista2);

           int prvCounter=0;
           int vtorCounter=0;
           for(int i=0;i<prvDel.length;i++)
           {
               if(lista1.get(i)>lista2.get(i))
               {
                   prvCounter++;
               }
               else
               {
                   vtorCounter++;
               }
           }
           System.out.println(prvCounter + " " + vtorCounter);
       }
   }
}

public class zad27 {
   public static void main(String[] args) {
       Risk risk = new Risk();
       risk.processAttacksData(System.in);
   }
}
