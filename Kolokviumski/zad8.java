package Kolokviumski;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class NonExistingItemException extends Exception {
    public NonExistingItemException(String message) {
        super(message);
    }
}

class InvalidArchiveOpenException extends Exception {
    public InvalidArchiveOpenException(String message) {
        super(message);
    }
}

abstract class Archive {
    int id;
    LocalDate date;

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    void archive(LocalDate datee)
    {
        this.date = datee;
    }

    abstract void open (LocalDate datee) throws InvalidArchiveOpenException;
}

class LockedArchive extends Archive {
    LocalDate dateToOpen;

    public LockedArchive(int id, LocalDate dateToOpen) {
        this.id = id;
        this.dateToOpen = dateToOpen;
    }

    @Override
    void open(LocalDate datee) throws InvalidArchiveOpenException {
        if(datee.isBefore(dateToOpen))
        {
            throw new InvalidArchiveOpenException(String.format("Item %d cannot be opened before %s",id,dateToOpen));
        }
        //return datee;
    }
}

class SpecialArchive extends Archive {
    int maxOpen;
    int counter;

    public SpecialArchive(int id, int maxOpen) {
        this.maxOpen = maxOpen;
        this.id = id;
        counter=0;
    }

    @Override
    void open(LocalDate datee) throws InvalidArchiveOpenException {
        if(counter>=maxOpen)
        {
            throw new InvalidArchiveOpenException(String.format("Item %d cannot be opened more than %d times",id,maxOpen));
        }
        counter++;
    }
}

class ArchiveStore {
    ArrayList<Archive> items;
    StringBuilder log;

    public ArchiveStore() {
        this.items = new ArrayList<>();
        this.log = new StringBuilder();
    }

    void archiveItem(Archive item, LocalDate date)
    {
        item.archive(date);
        items.add(item);
        log.append(String.format("Item %d archived at %s\n",item.getId(),date));
    }

    String getLog()
    {
        return log.toString();
    }

    void openItem(int id, LocalDate datee) throws NonExistingItemException {
        for (Archive item : items) {
            if(item.getId()==id)
            {
                try {
                    item.open(datee);
                    log.append(String.format("Item %d opened at %s\n",item.getId(),datee));
                } catch (InvalidArchiveOpenException e)
                {
                    log.append(e.getMessage());
                    log.append("\n");
                }
                return;
            }
        }
        throw new NonExistingItemException(String.format("Item with id %d doesn't exist",id));
    }
}

public class zad8 {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        LocalDate date = LocalDate.of(2013, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();

            LocalDate dateToOpen = date.atStartOfDay().plusSeconds(days * 24 * 60 * 60).toLocalDate();
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while(scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch(NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}