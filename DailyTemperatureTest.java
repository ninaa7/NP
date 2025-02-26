//package Kolokviumski2;
//
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintStream;
//import java.io.PrintWriter;
//import java.util.*;
//
//enum Tip {
//    C,
//    F;
//}
//
//class Day {
//    int day;
//    List<Double> merenja;
//    Tip tip;
//
//    public Day(int day, List<Double> merenja, Tip tip) {
//        this.day = day;
//        this.merenja = merenja;
//        this.tip = tip;
//    }
//
//    public int vkupnoMerenje() {
//        return merenja.size();
//    }
//
//    public int min () {
//        if (tip == Tip.C) {
//            return merenja.stream().mapToInt()
//        }
//    }
//
//    public int max () {
//
//    }
//
//}
//
//class DailyTemperatures {
//    List<Day> list;
//
//    public DailyTemperatures() {
//        this.list = new ArrayList<>();
//    }
//
//    public void readTemperatures(InputStream in) {
//        Scanner sc = new Scanner(in);
//        while (sc.hasNextLine()) {
//            String line = sc.nextLine();
//            String[] parts = line.split(" ");
//            int den = Integer.parseInt(parts[0]);
//            List<Double> merenja = new ArrayList<>();
//            Tip type;
//            if (parts[1].contains("C")) {
//                type = Tip.C;
//            } else {
//                type = Tip.F;
//            }
//            Arrays.stream(parts).skip(1).forEach(x-> merenja.add(Double.parseDouble(x)));
//            list.add(new Day(den,merenja,type));
//        }
//        sc.close();
//    }
//
//    public void writeDailyStats(OutputStream outputStream, char scale) {
//        PrintWriter pw = new PrintWriter(outputStream);
//
//    }
//}
//
//public class DailyTemperatureTest {
//    public static void main(String[] args) {
//        DailyTemperatures dailyTemperatures = new DailyTemperatures();
//        dailyTemperatures.readTemperatures(System.in);
//        System.out.println("=== Daily temperatures in Celsius (C) ===");
//        dailyTemperatures.writeDailyStats(System.out, 'C');
//        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
//        dailyTemperatures.writeDailyStats(System.out, 'F');
//    }
//}
//
//// Vashiot kod ovde
