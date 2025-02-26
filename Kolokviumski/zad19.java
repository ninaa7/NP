package Kolokviumski;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import static Kolokviumski.Driver.stringToTime;

class Element {
    int broj;
    int pocetok;
    int kraj;
    String text;

    public Element(int broj, String time, String text) {
        this.broj = broj;
        String[] parts = time.split("-->");
        pocetok = stringToTime(parts[0].trim());
        kraj = stringToTime(parts[1].trim());
        this.text = text;
    }

    public void shift(int ms) {
        pocetok += ms;
        kraj += ms;
    }

    int stringToTime (String time)
    {
        String []parts = time.split(",");
        int res = Integer.parseInt(parts[1]);
        parts = parts[0].split(":");
        int sec = Integer.parseInt(parts[2]);
        int min = Integer.parseInt(parts[1]);
        int hours = Integer.parseInt(parts[0]);
        res+=sec*1000;
        res+=min*60*1000;
        res+=hours*60*60*1000;
        return res;
    }

    String timeToStrng (int time)
    {
        int hours = time/(60*60*1000);
        time = time % (60*60*1000);
        int min = time / (60*1000);
        time = time % (60*1000);
        int sec = time / 1000;
        int ms = time % 1000;
        return String.format("%02d:%02d:%02d,%03d", hours, min, sec, ms);
    }

    @Override
    public String toString() {
        return String.format("%d\n%s --> %s\n%s",broj,timeToStrng(pocetok),timeToStrng(kraj),text);
    }
}

class Subtitles {
    ArrayList<Element> elements;

    public Subtitles() {
        this.elements = new ArrayList<>();
    }

    int loadSubtitles(InputStream inputStream) {
        int counter=0;
        Scanner sc = new Scanner(inputStream);
        while (sc.hasNextLine())
        {
            String line = sc.nextLine();
            int broj = Integer.parseInt(line);
            String time = sc.nextLine();
            StringBuilder text = new StringBuilder() ;
            while (true) {
                if(!sc.hasNextLine()) break;
                line = sc.nextLine();
                if (line.trim().length() == 0)
                    break;
                text.append(line);
                text.append("\n");
            }
            elements.add(new Element(broj,time,text.toString()));
        }
        return elements.size();
    }

    void print() {
        for (Element element : elements) {
            System.out.println(element);
        }
    }

    void shift(int ms) {
        for (Element element : elements) {
            element.shift(ms);
        }
    }
}

public class zad19 {
    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

// Вашиот код овде

