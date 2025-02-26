//package Kolokviumski2;
//
//import java.io.InputStream;
//import java.io.PrintStream;
//import java.text.DecimalFormat;
//import java.util.*;
//import java.util.stream.Collectors;
//
//class Employee implements Comparable<Employee> {
//    String id;
//    String level;
//    double rate;
//
//    public Employee(String id, String level, double rate) {
//        this.id = id;
//        this.level = level;
//        this.rate = rate;
//    }
//
//    public String getLevel() {
//        return level;
//    }
//
//    public double salary () {
//        return 0;
//    }
//
//    @Override
//    public int compareTo(Employee o) {
//        int s = Double.compare(o.salary(),salary());
//        if (s != 0) {
//            return s;
//        } else {
//            return level.compareTo(o.level);
//        }
//    }
//
//    @Override
//    public String toString() {
//        return String.format("Employee ID: %s Level: %s Salary: %.2f",id,level,salary());
//    }
//}
//
//class HourlyEmployee extends Employee{
//    double hours;
//
//
//    public HourlyEmployee(String id, String level, double rate,double hours) {
//        super(id, level, rate);
//        this.hours = hours;
//    }
//
//    public double getRegularHours () {
//        return Math.min(hours,40);
//    }
//
//    public double getOvertimeHours () {
//        return Math.max(0,hours - 40);
//    }
//
//
//    public double salary () {
//        return (getRegularHours() * rate) + (getOvertimeHours() * (rate * 1.5));
//    }
//
//    @Override
//    public String toString() {
//        DecimalFormat df = new DecimalFormat("0.00");
//        return String.format("%s Regular hours: %s Overtime hours: %s",
//                super.toString(), df.format(getRegularHours()), df.format(getOvertimeHours()));
//    }
//}
//
//class FreelanceEmployee extends Employee {
//    List<Integer> points;
//
//    public FreelanceEmployee(String id, String level, double rate,List<Integer> points) {
//        super(id, level, rate);
//        this.points = points;
//    }
//
//    public double salary () {
//        return points.stream().mapToInt(i->i).sum()  * rate;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s Tickets count: %d Tickets points: %d",
//                super.toString(), points.size(), points.stream().mapToInt(x -> x).sum());
//    }
//}
//
//
//class PayrollSystem {
//    List<Employee> employees;
//    Map<String, Double> hourlyRateByLevel;
//    Map<String, Double> ticketRateByLevel;
//
//    public PayrollSystem(Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
//        this.employees = new ArrayList<>();
//        this.hourlyRateByLevel = hourlyRateByLevel;
//        this.ticketRateByLevel = ticketRateByLevel;
//    }
//
//    public void readEmployees(InputStream in) {
//        Scanner scanner = new Scanner(in);
//        while (scanner.hasNextLine())
//        {
//            String line = scanner.nextLine();
//            String []parts = line.split(";");
//            Employee employee = null;
//            if (parts[0].equals("H"))
//            {
//                String id = parts[1];
//                String level = parts[2];
//                String hours = parts[3];
//                employee = new HourlyEmployee(id,level,hourlyRateByLevel.get(level),Double.parseDouble(hours));
//            } else if (parts[0].equals("F")) {
//                List<Integer> points = new ArrayList<>();
//                String id = parts[1];
//                String level = parts[2];
//                for (int i = 3; i < parts.length; i++) {
//                    points.add(Integer.parseInt(parts[i]));
//                }
//                employee = new FreelanceEmployee(id,level,ticketRateByLevel.get(level),points);
//            }
//            employees.add(employee);
//        }
//    }
//
//    public Map<String, Set<Employee>> printEmployeesByLevels(PrintStream out, Set<String> levels) {
//        Map<String, Set<Employee>> result = new LinkedHashMap<>();
//
//        levels.stream().sorted().forEach(level -> employees.stream().filter(x -> x.getLevel().equals(level)).forEach(employee -> {
//            result.computeIfAbsent(level, x -> new HashSet<>());
//            result.put(level, employees.stream().filter(x->x.getLevel().equals(level)).sorted().collect(Collectors.toCollection(LinkedHashSet::new)));
//        }));
//        return result;
//    }
//}
//
//public class PayrollSystemTest {
//
//    public static void main(String[] args) {
//
//        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
//        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
//        for (int i = 1; i <= 10; i++) {
//            hourlyRateByLevel.put("level" + i, 10 + i * 2.2);
//            ticketRateByLevel.put("level" + i, 5 + i * 2.5);
//        }
//
//        PayrollSystem payrollSystem = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);
//
//        System.out.println("READING OF THE EMPLOYEES DATA");
//        payrollSystem.readEmployees(System.in);
//
//        System.out.println("PRINTING EMPLOYEES BY LEVEL");
//        Set<String> levels = new LinkedHashSet<>();
//        for (int i=5;i<=10;i++) {
//            levels.add("level"+i);
//        }
//        Map<String, Set<Employee>> result = payrollSystem.printEmployeesByLevels(System.out, levels);
//        result.forEach((level, employees) -> {
//            System.out.println("LEVEL: "+ level);
//            System.out.println("Employees: ");
//            employees.forEach(System.out::println);
//            System.out.println("------------");
//        });
//
//
//    }
//}
