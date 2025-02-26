//package Kolokviumski2;
//
//import java.text.DecimalFormat;
//import java.util.*;
//import java.util.stream.Collectors;
//
//class BonusNotAllowedException extends Exception {
//
//}
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
//    public Employee createEmployee(String line) {
//        String []parts =
//    }
//
//    public Map<Object, Object> getOvertimeSalaryForLevels() {
//    }
//
//    public void printStatisticsForOvertimeSalary() {
//
//    }
//
//    public Map<Object, Object> ticketsDoneByLevel() {
//
//    }
//
//    public Map<Object, Object> getFirstNEmployeesByBonus(int i) {
//
//    }
//}
//
//public class PayrollSystemTest2 {
//
//    public static void main(String[] args) {
//
//        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
//        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
//        for (int i = 1; i <= 10; i++) {
//            hourlyRateByLevel.put("level" + i, 11 + i * 2.2);
//            ticketRateByLevel.put("level" + i, 5.5 + i * 2.5);
//        }
//
//        Scanner sc = new Scanner(System.in);
//
//        int employeesCount = Integer.parseInt(sc.nextLine());
//
//        PayrollSystem ps = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);
//        Employee emp = null;
//        for (int i = 0; i < employeesCount; i++) {
//            try {
//                emp = ps.createEmployee(sc.nextLine());
//            } catch (BonusNotAllowedException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        int testCase = Integer.parseInt(sc.nextLine());
//
//        switch (testCase) {
//            case 1: //Testing createEmployee
//                if (emp != null)
//                    System.out.println(emp);
//                break;
//            case 2: //Testing getOvertimeSalaryForLevels()
//                ps.getOvertimeSalaryForLevels().forEach((level, overtimeSalary) -> {
//                    System.out.printf("Level: %s Overtime salary: %.2f\n", level, overtimeSalary);
//                });
//                break;
//            case 3: //Testing printStatisticsForOvertimeSalary()
//                ps.printStatisticsForOvertimeSalary();
//                break;
//            case 4: //Testing ticketsDoneByLevel
//                ps.ticketsDoneByLevel().forEach((level, overtimeSalary) -> {
//                    System.out.printf("Level: %s Tickets by level: %d\n", level, overtimeSalary);
//                });
//                break;
//            case 5: //Testing getFirstNEmployeesByBonus (int n)
//                ps.getFirstNEmployeesByBonus(Integer.parseInt(sc.nextLine())).forEach(System.out::println);
//                break;
//        }
//
//    }
//}