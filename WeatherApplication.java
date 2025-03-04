package Kolokviumski2;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

interface Display {
    void setMeasurements(float temperature, float humidity, float pressure);
    int priority();
}

class WeatherDispatcher {
    float temperature;
    float humidity;
    float pressure;
    Set<Display> displays;

    public WeatherDispatcher() {
        this.temperature = 0.0F;
        this.humidity = 0.0F;
        this.pressure = 0.0F;
        displays = new HashSet<>();
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        displays.stream()
                .sorted(Comparator.comparing(Display::priority))
                .forEach(x->x.setMeasurements(temperature,humidity,pressure));
        System.out.println();
    }

    public void remove(Display forecastDisplay) {
        displays.remove(forecastDisplay);
    }

    public void register(Display forecastDisplay) {
        displays.add(forecastDisplay);
    }
}

class CurrentConditionsDisplay implements Display {

    public CurrentConditionsDisplay(WeatherDispatcher w) {
        w.register(this);
    }


    @Override
    public void setMeasurements(float temperature, float humidity, float pressure) {
        System.out.printf("Temperature: %.1fF\nHumidity: %.1f%%\n",temperature,humidity);
    }

    @Override
    public int priority() {
        return 0;
    }
}

class ForecastDisplay implements Display{
    float previousPressure;

    public ForecastDisplay(WeatherDispatcher w) {
        w.register(this);
        previousPressure=0;
    }

    @Override
    public void setMeasurements(float temperature, float humidity, float pressure) {
        if (pressure > previousPressure) {
            System.out.println("Forecast: Improving");
        } else if (pressure < previousPressure) {
            System.out.println("Forecast: Cooler");
        } else {
            System.out.println("Forecast: Same");
        }
        previousPressure=pressure;
    }

    @Override
    public int priority() {
        return 1;
    }
}

public class WeatherApplication {

    public static void main(String[] args) {
        WeatherDispatcher weatherDispatcher = new WeatherDispatcher();

        CurrentConditionsDisplay currentConditions = new CurrentConditionsDisplay(weatherDispatcher);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherDispatcher);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            weatherDispatcher.setMeasurements(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
            if(parts.length > 3) {
                int operation = Integer.parseInt(parts[3]);
                if(operation==1) {
                    weatherDispatcher.remove(forecastDisplay);
                }
                if(operation==2) {
                    weatherDispatcher.remove(currentConditions);
                }
                if(operation==3) {
                    weatherDispatcher.register(forecastDisplay);
                }
                if(operation==4) {
                    weatherDispatcher.register(currentConditions);
                }

            }
        }
    }
}
