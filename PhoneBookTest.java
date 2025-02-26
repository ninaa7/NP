package Kolokviumski2;

import java.util.*;

class DuplicateNumberException extends Exception {
    public DuplicateNumberException(String number) {
        super(String.format("Duplicate number: %s",number));
    }
}

class Contact implements Comparable<Contact>{
    String name;
    String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public int compareTo(Contact o) {
        int res = this.name.compareTo(o.name);
        if (res==0)
        {
            return this.number.compareTo(o.number);
        } else
        {
            return res;
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s",name,number);
    }
}

class PhoneBook {
    Set<String> allPhoneNumbers;
    Map<String, Set<Contact>> contactsBySubstring;
    Map<String, Set<Contact>> contactsByName;

    public PhoneBook() {
        this.allPhoneNumbers = new HashSet<>();
        this.contactsBySubstring = new HashMap<>();
        this.contactsByName = new HashMap<>();
    }

    private List<String> getSubstring (String number) //gi dobivame site mozni podstringovi od daden telefonski broj
    {
        List<String> result = new ArrayList<>();
        for (int i=3;i<=number.length();i++){
            for (int j=0;j<=number.length()-i;j++)
            {
                result.add(number.substring(j,j+i));
            }
        }
        return result;
    }

    void addContact(String name, String number) throws DuplicateNumberException {
        if (allPhoneNumbers.contains(number))
        {
            throw new DuplicateNumberException(number);
        }
        else {
            allPhoneNumbers.add(number);
            Contact c = new Contact(name,number);
            List<String> subnumbers = getSubstring(number);
            for (String subnumber : subnumbers)
            {
                contactsBySubstring.putIfAbsent(subnumber, new TreeSet<>());
                contactsBySubstring.get(subnumber).add(c);
            }

            contactsByName.putIfAbsent(name, new TreeSet<>());
            contactsByName.get(name).add(c);
        }
    }

    void contactsByNumber(String number) {
        Set<Contact> contacts = contactsBySubstring.get(number);
        if (contacts==null)
        {
            System.out.println("NOT FOUND");
            return;
        }
        contacts.forEach(System.out::println);
    }

    void contactsByName(String name) {
        Set<Contact> contacts = contactsByName.get(name);
        if (contacts==null)
        {
            System.out.println("NOT FOUND");
            return;
        }
        contacts.forEach(System.out::println);
    }
}

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}

// Вашиот код овде

