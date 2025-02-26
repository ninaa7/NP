//package Kolokviumski2;
//
//import java.io.*;
//import java.util.*;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
//
//class InvalidDimensionException extends Exception {
//    public InvalidDimensionException(String message) {
//        super(message);
//    }
//}
//
//interface Shape {
//    double perimetar ();
//    double plostina ();
//    void scale (double factor);
//    String getId();
//}
//
//class Cirle implements Shape {
//    double radius;
//    String id;
//
//    public Cirle(String id, double radius) {
//        this.radius = radius;
//        this.id = id;
//    }
//
//    @Override
//    public double perimetar() {
//        return radius*2*Math.PI;
//    }
//
//    @Override
//    public double plostina() {
//        return radius*radius;
//    }
//
//    @Override
//    public void scale(double factor) {
//        radius*=factor;
//    }
//
//    @Override
//    public String getId() {
//        return id;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("Circle -> Radius: %.2f Area: %.2f Perimetar: %.2f",radius,plostina(),perimetar());
//    }
//}
//
//class Rectangle implements Shape {
//    double a;
//    double b;
//    String id;
//
//    public Rectangle(String id, double a, double b) {
//        this.a = a;
//        this.b = b;
//        this.id = id;
//    }
//
//    @Override
//    public double perimetar() {
//        return 2*a + 2*b;
//    }
//
//    @Override
//    public double plostina() {
//        return a*b;
//    }
//
//    @Override
//    public void scale(double factor) {
//        a*=factor;
//        b*=factor;
//    }
//
//    @Override
//    public String getId() {
//        return id;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("Rectangle: -> Sides: %.2f, %.2f Area: %.2f Perimetar: %.2f",a,b,plostina(),perimetar());
//    }
//}
//
//class Square implements Shape {
//    double a;
//    String id;
//
//    public Square(String id, double a) {
//        this.a = a;
//        this.id = id;
//    }
//
//    @Override
//    public double perimetar() {
//        return a*4;
//    }
//
//    @Override
//    public double plostina() {
//        return a*a;
//    }
//
//    @Override
//    public void scale(double factor) {
//        a*=factor;
//    }
//
//    @Override
//    public String getId() {
//        return id;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("Square: -> Side: %.2f Area: %.2f Perimeter: %.2f",a,plostina(),perimetar());
//    }
//}
//
//class Canvas {
//    TreeMap<String, Shape> shapes;
//
//    public Canvas() {
//        this.shapes = new TreeMap<>(Comparator.comparingDouble(shape -> shapes.get(shape).plostina()));
//    }
//
//    public void readShapes(InputStream in) throws InvalidDimensionException {
//        Scanner sc = new Scanner(in);
//        while (sc.hasNext())
//        {
//            String line = sc.nextLine();
//            String []parts = line.split(" ");
//
//            String id = parts[1];
//            if (id.length()!=6 || !checkId(id))
//            {
//                System.out.println("ID "+id+" is not valid");
//                continue;
//            }
//
//            if (Double.parseDouble(parts[2])==0)
//            {
//                throw new InvalidDimensionException("Dimension 0 is not allowed!");
//            }
//
//            int tip = Integer.parseInt(parts[0]);
//            if (tip==1)
//            {
//                shapes.put(parts[1], new Cirle(parts[1],Double.parseDouble(parts[2])));
//            }
//            else if (tip==2)
//            {
//                shapes.put(parts[1], new Square(parts[1],Double.parseDouble(parts[2])));
//            }
//            else if (tip==3)
//            {
//                if (Double.parseDouble(parts[3])==0)
//                {
//                    throw new InvalidDimensionException("Dimension 0 is not allowed!");
//                }
//                shapes.put(parts[1], new Rectangle(parts[1],Double.parseDouble(parts[2]),Double.parseDouble(parts[3])));
//            }
//        }
//        sc.close();
//    }
//
//    public static boolean checkId (String id)
//    {
//        for (char c : id.toCharArray())
//        {
//            if (!Character.isLetterOrDigit(c))
//            {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public void printAllShapes(PrintStream out) {
//        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
//
//        shapes.values().forEach(i-> pw.println(i.toString()));
//
//        pw.flush();
//    }
//
//
//    public void scaleShapes(String userID, double coef) {
//        shapes.keySet().stream().filter(id -> id.equals(userID)).forEach(j -> shapes.get(j).scale(coef));
//    }
//
//
//    public void printByUserId(PrintStream out) {
//        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
//
//        shapes.values().stream().sorted(Comparator.comparing(Shape::getId).thenComparing());
//    }
//
//
//    public void statistics(PrintStream out) {
//
//    }
//}
//
//public class CanvasTest {
//
//    public static void main(String[] args) {
//        Canvas canvas = new Canvas();
//
//        System.out.println("READ SHAPES AND EXCEPTIONS TESTING");
//        try {
//            canvas.readShapes(System.in);
//        } catch (InvalidDimensionException e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println("BEFORE SCALING");
//        canvas.printAllShapes(System.out);
//        canvas.scaleShapes("123456", 1.5);
//        System.out.println("AFTER SCALING");
//        canvas.printAllShapes(System.out);
//
//        System.out.println("PRINT BY USER ID TESTING");
//        canvas.printByUserId(System.out);
//
//        System.out.println("PRINT STATISTICS");
//        canvas.statistics(System.out);
//    }
//}
