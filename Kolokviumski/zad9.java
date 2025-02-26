package Kolokviumski;

import java.util.Scanner;

class Triple <T extends Number & Comparable<T>>
{
    private T a;
    private T b;
    private T c;

    public Triple(T a, T b, T c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double max()
    {
        sort();
        return c.doubleValue();
    }

    public double average()
    {
        double prosek = (a.doubleValue()+ b.doubleValue()+c.doubleValue())/3.0;
        return prosek;
    }

    public void sort()
    {
        if(a.compareTo(b)>0)
        {
            T temp = a;
            a=b;
            b=temp;
        }
        if(b.compareTo(c)>0)
        {
            T temp = b;
            b=c;
            c=temp;
        }
        if(a.compareTo(b)>0)
        {
            T temp = b;
            b=a;
            a=temp;
        }
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f",a.doubleValue(),b.doubleValue(),c.doubleValue());
    }
}

public class zad9 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.average());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.average());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.average());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
// vasiot kod ovde
// class Triple



