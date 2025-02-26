package Kolokviumski;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class  UnsupportedFormatException extends Exception {
    public UnsupportedFormatException(String message) {
        super(message);
    }
}

class  InvalidTimeException extends Exception {
    public InvalidTimeException(String message) {
        super(message);
    }
}

class Time implements Comparable<Time>
{
    int hour;
    int minutes;

    public Time(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    public String toStringAMPM()
    {
        String part = "AM";
        int h = hour;
        if (h==0)
        {
            h+=12;
        } else if (h==12)
        {
            part = "PM";
        } else if (h>12)
        {
            h-=12;
            part = "PM";
        }
        return String.format("%2d:%02d %s", h, minutes, part);
    }

    @Override
    public String toString() {
        return String.format("%2d:%02d", hour, minutes);
    }


    @Override
    public int compareTo(Time o) {
        if(hour == o.hour)
        {
            return minutes - o.minutes;
        }
        else
        {
            return hour-o.hour;
        }
    }
}

class TimeTable {
    ArrayList<Time> times;

    public TimeTable() {
        this.times = new ArrayList<>();
    }

    void readTimes(InputStream is) throws  UnsupportedFormatException, InvalidTimeException {
        Scanner sc=new Scanner(is);
        while(sc.hasNext()){
            String line= sc.next();
            if(!(line.contains(".")||line.contains(":"))){
                throw new UnsupportedFormatException(line);
            }
            String []parts=line.split("[\\.:]");
            int hour=Integer.parseInt(parts[0]);
            int minutes=Integer.parseInt(parts[1]);
            if((hour<0||hour>23)&&(minutes<0||minutes>59)){
                throw new InvalidTimeException(line);
            }
            times.add(new Time(hour,minutes));
        }
    }

    void writeTimes(OutputStream outputStream, TimeFormat format) {
        PrintWriter pw = new PrintWriter(outputStream);
        Collections.sort(times);
        for (Time time : times) {
            if(format==TimeFormat.FORMAT_24)
            {
                pw.println(time);
            }
            else
            {
                pw.println(time.toStringAMPM());
            }
        }
        pw.flush();
    }
}

public class zad7 {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}