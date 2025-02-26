package Kolokviumski;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Canvas implements Comparable<Canvas> {
   private final String id;
   private final List<Integer> size;

   public Canvas(String str) {
       this.size = new ArrayList<Integer>();

       String[] split = str.split("\\s+");
       this.id = split[0];

       for (int i = 1; i < split.length; i++) {
           size.add(Integer.parseInt(split[i]));
       }
   }

   public int perimetar() {
       int s = 0;
       for (Integer i : size) {
           s += 4 * i;
       }
       return s;
   }

   @Override
   public String toString() {
       return String.format("%s %d %d", id, size.size(), perimetar());
   }

   @Override
   public int compareTo(Canvas o) {
       return perimetar() - o.perimetar();
   }
}

class ShapesApplication {
   private final List<Canvas> list;

   public ShapesApplication() {
       this.list = new ArrayList<Canvas>();
   }


   public int readCanvases(InputStream inputStream) {
       Scanner sc = new Scanner(inputStream);
       String str;
       int s = 0;

       while (sc.hasNextLine()) {
           str = sc.nextLine();
           s += str.split("\\s").length - 1;
           list.add(new Canvas(str));
       }
       return s;
   }

   public void printLargestCanvasTo(OutputStream outputStream) {
       System.out.println(list.stream().max(Canvas::compareTo).get());
   }
}

public class zad1 {

   public static void main(String[] args) {
       ShapesApplication shapesApplication = new ShapesApplication();

       System.out.println("===READING SQUARES FROM INPUT STREAM===");
       System.out.println(shapesApplication.readCanvases(System.in));
       System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
       shapesApplication.printLargestCanvasTo(System.out);

   }
}