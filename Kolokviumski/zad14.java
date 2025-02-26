package Kolokviumski;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

class Weather implements Comparable<Weather> {
    float temperatura;
    float vlaznost;
    float veter;
    float vidlivost;
    Date date;

    public Weather(float temperatura, float veter, float vlaznost, float vidlivost, Date date) {
        this.temperatura = temperatura;
        this.vlaznost = vlaznost;
        this.veter = veter;
        this.vidlivost = vidlivost;
        this.date = date;
    }

    @Override
    public String toString() {
        String dateString = date.toString();
        dateString = dateString.replace("UTC", "GMT");
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s", temperatura, veter, vlaznost, vidlivost, dateString);
    }

    @Override
    public int compareTo(Weather o) {
        return date.compareTo(o.date);
    }
}

class WeatherStation {
    ArrayList<Weather> weathers;
    int days;
    static int ms = 86400000;

    public WeatherStation(int days) {
        this.weathers = new ArrayList<>();
        this.days = days;
    }

    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date){
        Weather w = new Weather(temperature,wind,humidity,visibility,date);
        if(weathers.isEmpty())
        {
            weathers.add(w);
            return;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);

        Calendar lastTime = Calendar.getInstance();
        lastTime.setTime(weathers.get(weathers.size()-1).date);

        if(Math.abs(now.getTimeInMillis() - lastTime.getTimeInMillis())<2.5 * 60 * 1000){
            return;
        }
        weathers.add(w);

        ArrayList<Weather> toRemove = new ArrayList<>();
        for (Weather weather : weathers) {
            Calendar c = Calendar.getInstance();
            c.setTime(weather.date);

            if(Math.abs(now.getTimeInMillis() - c.getTimeInMillis()) >(long)days*ms){
                toRemove.add(weather);
            }
        }
        weathers.removeAll(toRemove);
    }

    public int total() {
        return weathers.size();
    }

    public void status(Date from, Date to) {
        ArrayList<Weather> arr = new ArrayList<>();

        double average = 0.0;
        int ct=0;
        for (Weather weather : weathers) {
            Date d = weather.date;
            if((d.after(from)||d.equals(from))&&(d.before(to)||d.equals(to))){
                arr.add(weather);
                average+=weather.temperatura;
                ct++;
            }
        }
        if(arr.isEmpty())
        {
            throw new RuntimeException();
        }

        StringBuilder str = new StringBuilder();
        for (Weather weather : arr) {
            str.append(weather).append("\n");
        }
        System.out.print(str.toString());
        System.out.printf("Average temperature: %.2f\n",average/ct);
    }
}

public class zad14 {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

// vashiot kod ovde