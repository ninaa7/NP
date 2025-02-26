package Kolokviumski2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class SeatNotAllowedException extends Exception {
    public SeatNotAllowedException() {
        super("SeatNotAllowedException");
    }
}

class SeatTakenException extends Exception {
    public SeatTakenException() {
        super("SeatTakenException");
    }
}

class Sector {
    String kod;
    int brMesta;
    Map<Integer, Boolean> zafatenost;
    int type;

    public Sector(String kod, int brMesta) {
        this.kod = kod;
        this.brMesta = brMesta;
        this.zafatenost = new HashMap<>();
        this.type = 0;
    }

    public String getKod() {
        return kod;
    }

    public int getBrMesta() {
        return brMesta;
    }

    public Map<Integer, Boolean> getZafatenost() {
        return zafatenost;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public int freeSeats () {
        return brMesta - zafatenost.size();
    }

    @Override
    public String toString() {
        return String.format("%s\t%d/%d\t%.1f%%",kod,freeSeats(),brMesta,((double)zafatenost.size()/brMesta)*100.00);
    }
}

class Stadium {
    String name;
    Map<String,Sector> map;

    public Stadium(String name) {
        this.name = name;
        this.map = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<String, Sector> getMap() {
        return map;
    }

    public void createSectors(String[] sectorNames, int[] sectorSizes) {
        for (int i=0;i<sectorNames.length;i++)
        {
            map.put(sectorNames[i], new Sector(sectorNames[i],sectorSizes[i]));
        }
    }

    public void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        Map<Integer, Boolean> seats = map.get(sectorName).getZafatenost();

        if (seats.containsKey(seat))
        {
            throw new SeatTakenException();
        }

        int sectorType = map.get(sectorName).getType();

        if ((type == 1 && sectorType == 2) || (type == 2 && sectorType == 1))
        {
            throw new SeatNotAllowedException();
        }

        if (type!=0 && sectorType==0) {
            map.get(sectorName).setType(type);
        }

        seats.put(seat,true);
    }

    public void showSectors() {
        map.values().stream().sorted(Comparator.comparing(Sector::freeSeats).reversed().thenComparing(Sector::getKod)).forEach(System.out::println);
    }
}

public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}

