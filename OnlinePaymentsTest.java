//package Kolokviumski2;
//
//import java.io.InputStream;
//import java.io.PrintStream;
//import java.io.PrintWriter;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//class Item {
//    String name;
//    int price;
//
//    public Item(String name, int price) {
//        this.name = name;
//        this.price = price;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s %d", name, price);
//    }
//}
//
//class OnlinePayments {
//    Map<String, List<Item>> students;
//
//    public OnlinePayments() {
//        this.students = new HashMap<>();
//    }
//
//    public void readItems(InputStream inputStream) {
//        Scanner scanner = new Scanner(inputStream);
//
//        while(scanner.hasNextLine()) {
//            String line = scanner.nextLine();
//            String[] parts = line.split(";");
//            String ind = parts[0];
//            String itemName = parts[1];
//            int price = Integer.parseInt(parts[2]);
//            students.computeIfAbsent(ind, x -> new ArrayList<>());
//            students.computeIfPresent(ind, (k, v) -> {
//                v.add(new Item(itemName, price));
//                return v;
//            });
//        }
//    }
//
//    public void printStudentReport(String id, PrintStream out) {
//        PrintWriter pw = new PrintWriter(out);
//
//        if (!students.containsKey(id))
//        {
//            pw.println("Student " + id + " not found!");
//            pw.flush();
//            return;
//        }
//
//        List<Item> it = students.get(id);
//        int net = it.stream().mapToInt(Item::getPrice).sum();
//        int fee = (int) Math.round(net*0.0114);
//        fee = Math.max(3,Math.min(300,fee));
//
//        pw.printf("Student: %s Net: %d Fee: %d Total: %d\nItems:\n", id, net, fee, net + fee);
//        List<Item> items = students.get(id).stream().sorted(Comparator.comparing(Item::getPrice).reversed()).collect(Collectors.toList());
//        IntStream.range(0,items.size()).forEach(i -> pw.println((i+1) + ". " + items.get(i)));
//        pw.flush();
//    }
//}
//
//public class OnlinePaymentsTest {
//    public static void main(String[] args) {
//        OnlinePayments onlinePayments = new OnlinePayments();
//
//        onlinePayments.readItems(System.in);
//
//        IntStream.range(151020, 151025).mapToObj(String::valueOf).forEach(id -> onlinePayments.printStudentReport(id, System.out));
//    }
//}