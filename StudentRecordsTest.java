//package Kolokviumski2;
//
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintStream;
//import java.io.PrintWriter;
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//class Student {
//    String kod;
//    String nasoka;
//    List<Integer> oceni;
//
//    public Student(String kod, String nasoka,List<Integer> oceni) {
//        this.kod = kod;
//        this.nasoka = nasoka;
//        this.oceni = oceni;
//    }
//
//    public double average() {
//        return oceni.stream().mapToDouble(i->i).sum() / oceni.size();
//    }
//
//    public String getKod() {
//        return kod;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s %.2f",kod,average());
//    }
//}
//
//class StudentRecords {
//    Map<String, Set<Student>> students;
//
//    public StudentRecords() {
//        this.students = new HashMap<>();
//    }
//
//    public int readRecords(InputStream in) {
//        Scanner sc = new Scanner(in);
//        int counter =0;
//        while (sc.hasNextLine()) {
//            String line = sc.nextLine();
//            String[] parts = line.split(" ");
//            String kod = parts[0];
//            String nasoka = parts[1];
//            List<Integer> lista = new ArrayList<>();
//            for (int i = 2; i < parts.length; i++) {
//                lista.add(Integer.valueOf(parts[i]));
//            }
//            Student student = new Student(kod, nasoka, lista);
//            students.computeIfAbsent(nasoka, x -> new HashSet<>());
//            students.computeIfPresent(nasoka, (k, v) -> {
//                v.add(student);
//                return v;
//            });
//            counter++;
//        }
//        return counter;
//    }
//
//    public void writeTable(OutputStream outputStream) {
//        PrintWriter pw = new PrintWriter(outputStream);
//        students.forEach((k,v) -> {
//            pw.println(k);
//            v.stream().sorted(Comparator.comparing(Student::average).thenComparing(Student::getKod).reversed()).forEach(pw::println);
//        });
//        pw.flush();
//    }
//
//    public void writeDistribution(PrintStream out) {
//        PrintWriter pw = new PrintWriter(out);
//
//        // Sorting the nasoki by the total number of grades in descending order
//        List<String> sortedNasoki = students.keySet().stream()
//                .sorted(Comparator.comparingInt(nasoka -> -students.get(nasoka).stream().mapToInt(student -> student.oceni.size()).sum()))
//                .collect(Collectors.toList());
//
//        sortedNasoki.forEach(nasoka -> {
//            pw.println(nasoka);
//            Map<Integer, Long> gradeDistribution = students.get(nasoka).stream()
//                    .flatMap(student -> student.oceni.stream())
//                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//
//            gradeDistribution.entrySet().stream()
//                    .sorted(Map.Entry.comparingByKey())
//                    .forEach(entry -> {
//                        int grade = entry.getKey();
//                        long count = entry.getValue();
//                        pw.printf(" %d | %s(%d)\n", grade, "*".repeat((int) count/10), count);
//                    });
//        });
//
//        pw.flush();
//    }
//}
//
//public class StudentRecordsTest {
//    public static void main(String[] args) {
//        System.out.println("=== READING RECORDS ===");
//        StudentRecords studentRecords = new StudentRecords();
//        int total = studentRecords.readRecords(System.in);
//        System.out.printf("Total records: %d\n", total);
//        System.out.println("=== WRITING TABLE ===");
//        studentRecords.writeTable(System.out);
//        System.out.println("=== WRITING DISTRIBUTION ===");
//        studentRecords.writeDistribution(System.out);
//    }
//}
//
//// your code here
