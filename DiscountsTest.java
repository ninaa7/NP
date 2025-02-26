package Kolokviumski2;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

class Price {
    int price;
    int priceOnSale;

    public Price(int price, int priceOnSale) {
        this.price = price;
        this.priceOnSale = priceOnSale;
    }

    public int getPercent () {
        return (int) Math.floor(100.0 - (100.0 / price * priceOnSale));
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%2d%% %d/%d", getPercent(), priceOnSale, price);
    }

}

class Store {
    String name;
    List<Price> prices;

    Store() {
        name = "store";
        prices = new ArrayList<>();
    }

    public Store(String name, List<Price> prices) {
        this.name = name;
        this.prices = prices;
    }

    public String getName() {
        return name;
    }


    public double averageDiscount() {
        return prices.stream().mapToInt(Price::getPercent).average().orElse(0);
    }

    public int absoluteSum() {
        return prices.stream().mapToInt(i -> Math.abs(i.price - i.priceOnSale)).sum();
    }

    @Override
    public String toString() {
        String result = prices.stream().sorted(Comparator.comparing(Price::getPercent).thenComparing(Price::getPrice).reversed())
                .map(x -> String.format("%s",x))
                .collect(Collectors.joining("\n"));
        return String.format("%s\nAverage discount: %.1f%%\nTotal discount: %d\n%s", name, averageDiscount(), absoluteSum(), result);
    }
}

class Discounts {
    List<Store> stores;

    public Discounts() {
        this.stores = new ArrayList<>();
    }

    public int readStores(InputStream in) {
        Scanner scanner = new Scanner(in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");

            String name = parts[0];
            ArrayList<Price> prices = new ArrayList<>();

            for (int i = 1; i < parts.length; i++) {
                if(parts[i].isEmpty()) continue;
                String[] ceni = parts[i].split(":");
                prices.add(new Price(Integer.parseInt(ceni[1]), Integer.parseInt(ceni[0])));
            }

            stores.add(new Store(name, prices));
        }
        return stores.size();
    }

    public List<Store> byAverageDiscount() {
        return stores.stream()
                .sorted(Comparator.comparing(Store::averageDiscount).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<Store> byTotalDiscount() {
        return stores.stream()
                .sorted(Comparator.comparing(Store::absoluteSum))
                .limit(3)
                .collect(Collectors.toList());
    }
}

public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}


