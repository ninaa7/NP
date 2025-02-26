package Kolokviumski2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

class WrongDateException extends Exception {
    public WrongDateException(String message) {
        super(message);
    }
}

class Event {
    String name;
    String location;
    Date date;

    public Event(String name, String location, Date date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }
}

class EventCalendar {
    int year;

    List<Event> events;

    public EventCalendar(int year) {
        this.events = new ArrayList<>();
        this.year = year;
    }


    public void addEvent(String name, String location, Date date) throws WrongDateException {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.YEAR)!=year) {
            throw new WrongDateException(String.format("Wrong date: %s",date));
        }

        events.add(new Event(name,location,date));
    }

    static int getDayOfYear (Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_YEAR);
    }

    static int getMonth (Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    static int getYear (Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public void listByMonth() {
        Map<Integer, Integer> monthEventCount = new TreeMap<>();

        for (Event event : events) {
            int month = getMonth(event.getDate()) + 1; // Adding 1 because Calendar.MONTH is zero-based
            monthEventCount.merge(month, 1, Integer::sum);
        }

        for (int i = 1; i <= 12; i++) {
            System.out.printf("%d : %d\n", i, monthEventCount.getOrDefault(i, 0));
        }
    }


    public void listEvents(Date date) {
        int dayOfYear = getDayOfYear(date);

        List<Event> eventsOnDay = new ArrayList<>();

        for (Event event : events) {
            int eventDayOfYear = getDayOfYear(event.getDate());
            if (eventDayOfYear == dayOfYear) {
                eventsOnDay.add(event);
            }
        }

        if (eventsOnDay.isEmpty())
        {
            System.out.println("No events on this day!");
            return;
        }

        eventsOnDay.sort(Comparator.comparing(Event::getDate).thenComparing(Event::getName));

        eventsOnDay.forEach(event -> {
            String formattedDate = new SimpleDateFormat("dd MMM, yyyy HH:mm").format(event.getDate());
            System.out.printf("%s at %s, %s\n", formattedDate, event.getLocation(), event.getName());
        });
    }


}

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

// vashiot kod ovde