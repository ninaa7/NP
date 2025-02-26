package Kolokviumski;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Driver implements Comparable<Driver> {
    String name;
    int lap1;
    int lap2;
    int lap3;
    int best;

    public Driver(String name, int lap1, int lap2, int lap3) {
        this.name = name;
        this.lap1 = lap1;
        this.lap2 = lap2;
        this.lap3 = lap3;
        this.best = Math.min(Math.min(lap1,lap2),lap3);
    }

    static int stringToTime (String time)
    {
        String []parts = time.split(":");
        return Integer.parseInt(parts[0])*60*1000 + Integer.parseInt(parts[1])*1000 + Integer.parseInt(parts[2]);
    }

    static String timeToString (int time)
    {
        int minutes = (time/1000)/60;
        int seconds = (time - minutes * 1000 * 60)/1000;
        int ms = time%1000;
        return String.format("%d:%02d:%03d", minutes, seconds, ms);
    }

    @Override
    public int compareTo(Driver o) {
        return this.best-o.best;
    }

    @Override
    public String toString() {
        return String.format("%-10s%10s", name, timeToString(best));
    }
}

class F1Race {
    // vashiot kod ovde
    ArrayList<Driver> drivers;

    public F1Race() {
        this.drivers = new ArrayList<>();
    }

    void readResults(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);
        while (sc.hasNextLine())
        {
            String red = sc.nextLine();
            String []parts = red.split(" ");
            String name = parts[0];
            String lap1 = parts[1];
            String lap2 = parts[2];
            String lap3 = parts[3];
            Driver driver = new Driver(name,Driver.stringToTime(lap1),Driver.stringToTime(lap2),Driver.stringToTime(lap3));
            drivers.add(driver);
        }
        sc.close();
    }

    void printSorted(OutputStream outputStream){
        Collections.sort(drivers);
        PrintWriter pw = new PrintWriter(outputStream);
        int i=1;
        for (Driver driver : drivers) {
            pw.printf("%d. %s\n",i++,driver);
        }
        pw.close();
    }
}

public class zad21 {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

