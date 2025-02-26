package Kolokviumski2;

import java.util.*;
import java.util.stream.Collectors;

class Names {
    Map<String, Integer> names;

    public Names() {
        this.names = new TreeMap<>();
    }

    public void addName(String name) {
        names.putIfAbsent(name, (0));
        names.computeIfPresent(name, (k, v) -> {
            v++;
            return v;
        });
    }

    public void printN(int n) {
        names.entrySet().stream().filter(x -> x.getValue() >= n).forEach(x -> {
            Set<Character> character = x.getKey().chars()
                    .mapToObj(c -> (char) Character.toLowerCase(c))
                    .collect(Collectors.toCollection(HashSet::new));
            System.out.println(String.format("%s (%d) %d",x.getKey(),x.getValue(),character.size()));
        });
    }

    public String findName(int len, int index) {
       List<String> nameList = names.keySet()
               .stream().sorted(Comparator.naturalOrder())
               .filter(name -> name.length()<len).collect(Collectors.toList());
       return nameList.get(index%nameList.size());
    }

}

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

// vashiot kod ovde
