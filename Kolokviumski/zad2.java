package Kolokviumski;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import  java.lang.Double;

class IrregularCanvasException extends Exception {
    public IrregularCanvasException(String id,double maxArea) {
        super(String.format("Canvas %s has a shape with area larger than %.2f",id,maxArea));
    }
}

enum Type {
    CIRCLE,
    SQUARE
}

abstract class Shapes {
    int size;

    public Shapes(int size) {
        this.size = size;
    }

    abstract double area();

    abstract Type getType ();

    static Shapes createShape(int size, char type, double maxArea)
    {
        switch (type){
            case 'S':
                return new Square(size);
            case 'C':
                return new Circle(size);
            default:
                return null;
        }
    }
}

class Circle extends Shapes {

    public Circle(int size) {
        super(size);
    }

    @Override
    double area() {
        return size*size*Math.PI;
    }

    @Override
    Type getType() {
        return Type.CIRCLE;
    }


}

class Square extends Shapes {

    public Square(int size) {
        super(size);
    }

    @Override
    double area() {
        return size*size;
    }

    @Override
    Type getType() {
        return Type.SQUARE;
    }
}

class Canvas implements Comparable<Canvas>{
    String id;
    ArrayList<Shapes> shapes;

    public Canvas(String id, ArrayList<Shapes> shapes) {
        this.id = id;
        this.shapes = shapes;
    }

    static Canvas createCanvas(String line, double maxArea) throws IrregularCanvasException{
        String []parts = line.split(" ");
        String id = parts[0];
        ArrayList<Shapes> shapes = new ArrayList<>();
       for (int i=1;i<parts.length;i+=2)
       {
           Shapes s = Shapes.createShape(Integer.parseInt(parts[i+1]),parts[i].charAt(0),maxArea);
           if(s.area()>maxArea)
           {
               throw new IrregularCanvasException(id,maxArea);
           }
           shapes.add(Shapes.createShape(Integer.parseInt(parts[i+1]),parts[i].charAt(0),maxArea));
       }
        return new Canvas(id,shapes);
    }

    @Override
    public int compareTo(Canvas o) {
        return Double.compare(this.shapes.stream().mapToDouble(Shapes::area).sum(),o.shapes.stream().mapToDouble(Shapes::area).sum());
    }

    int totalCircle ()
    {
        return (int) shapes.stream().filter(s -> s.getType().equals(Type.CIRCLE)).count();
    }

    @Override
    public String toString() {
        DoubleSummaryStatistics ds = shapes.stream().mapToDouble(Shapes::area).summaryStatistics();
        return String.format("%s %d %d %d %.2f %.2f %.2f",id,shapes.size(),totalCircle(), shapes.size() - totalCircle(),ds.getMin(),ds.getMax(),ds.getAverage());
    }
}

class ShapesApplication {
    List<Canvas> lista;
    double maxArea;

    public ShapesApplication(double maxArea) {
        this.lista = new ArrayList<>();
        this.maxArea = maxArea;
    }


    void readCanvases (InputStream inputStream) {
        lista = new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .map(line -> {
                    try {
                        return Canvas.createCanvas(line, maxArea);
                    } catch (IrregularCanvasException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
}

    void printCanvases (OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        lista.stream().sorted(Comparator.reverseOrder()).forEach(pw::println);
        pw.flush();
    }
}

public class zad2 {

    public static void main(String[] args) {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}
