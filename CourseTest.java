package Kolokviumski2;


import javax.management.remote.SubjectDelegationPermission;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class NotValidPointsException extends Exception {
    public NotValidPointsException() {
    }
}

class Student {
    String index;
    String name;
    int poeniPrv;
    int poeniVtor;
    int poeniLabs;

    public Student(String index, String name) {
        this.index = index;
        this.name = name;
        this.poeniPrv = 0;
        this.poeniVtor = 0;
        this.poeniLabs = 0;
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoeniPrv(int poeniPrv) {
        this.poeniPrv = poeniPrv;
    }

    public void setPoeniVtor(int poeniVtor) {
        this.poeniVtor = poeniVtor;
    }

    public void setPoeniLabs(int poeniLabs) {
        this.poeniLabs = poeniLabs;
    }

    public double summeryPoints () {
        return poeniPrv * 0.45 + poeniVtor * 0.45 + poeniLabs;
    }

    public int ocenkaPoPoeni () {
        if (summeryPoints()<50) {
            return 5;
        } else if (summeryPoints()>=50&&summeryPoints()<60) {
            return 6;
        } else if (summeryPoints()>=60&&summeryPoints()<70) {
            return 7;
        } else if (summeryPoints()>=70 && summeryPoints()<80) {
            return 8;
        } else if (summeryPoints()>=80 && summeryPoints()<90) {
            return 9;
        } else {
            return 10;
        }
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s First midterm: %d Second midterm %d Labs: %d Summary points: %.2f Grade: %d",index,name,poeniPrv,poeniVtor,poeniLabs,summeryPoints(),ocenkaPoPoeni());
    }
}

class AdvancedProgrammingCourse {
    Map<String, Student> students;

    public AdvancedProgrammingCourse() {
        this.students = new HashMap<>();
    }

    public void addStudent(Student student) {
        students.put(student.index,student);
    }

    public void updateStudent(String idNumber, String activity, int points) throws NotValidPointsException {

        if (activity.equals("midterm1"))
        {
            if (points<0||points>100)
            {
                throw new NotValidPointsException();
            }
            students.get(idNumber).setPoeniPrv(points);
        } else if (activity.equals("midterm2")) {
            if (points<0||points>100)
            {
                throw new NotValidPointsException();
            }
            students.get(idNumber).setPoeniVtor(points);
        } else {
            if (points<0||points>10)
            {
                throw new NotValidPointsException();
            }
            students.get(idNumber).setPoeniLabs(points);
        }
    }

    public List<Student> getFirstNStudents(int n) {
        return students.values().stream().sorted(Comparator.comparing(Student::summeryPoints).reversed()).limit(n).collect(Collectors.toList());
    }

    public Map<Integer, Integer> getGradeDistribution() {
        Map<Integer,Integer> mapOcenki = new TreeMap<>();
        IntStream.range(5,11).forEach(i -> mapOcenki.put(i,0));
        students.values().forEach(i->mapOcenki.computeIfPresent(i.ocenkaPoPoeni(), (k,v) -> v+1));
        return mapOcenki;
    }

    public void printStatistics() {
        List<Student> passedStudents = students.values().stream().filter(x->x.ocenkaPoPoeni() >= 6).collect(Collectors.toList());
        DoubleSummaryStatistics statistics = passedStudents.stream().mapToDouble(Student::summeryPoints).summaryStatistics();
        System.out.printf("Count: %d Min: %.2f Average: %.2f Max: %.2f",statistics.getCount(),statistics.getMin(),statistics.getAverage(),statistics.getMax());

    }
}

public class CourseTest {

    public static void printStudents(List<Student> students) {
        students.forEach(System.out::println);
    }

    public static void printMap(Map<Integer, Integer> map) {
        map.forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
    }

    public static void main(String[] args) {
        AdvancedProgrammingCourse advancedProgrammingCourse = new AdvancedProgrammingCourse();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            String command = parts[0];

            if (command.equals("addStudent")) {
                String id = parts[1];
                String name = parts[2];
                advancedProgrammingCourse.addStudent(new Student(id, name));
            } else if (command.equals("updateStudent")) {
                String idNumber = parts[1];
                String activity = parts[2];
                int points = Integer.parseInt(parts[3]);
                try {
                    advancedProgrammingCourse.updateStudent(idNumber, activity, points);
                } catch (NotValidPointsException e) {
                    continue;
                }
            } else if (command.equals("getFirstNStudents")) {
                int n = Integer.parseInt(parts[1]);
                printStudents(advancedProgrammingCourse.getFirstNStudents(n));
            } else if (command.equals("getGradeDistribution")) {
                printMap(advancedProgrammingCourse.getGradeDistribution());
            } else {
                advancedProgrammingCourse.printStatistics();
            }
        }
    }
}

