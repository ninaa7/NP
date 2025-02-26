package Kolokviumski;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import java.util.stream.Collectors;

class CategoryNotFoundException extends Exception
{
    public CategoryNotFoundException(String message) {
        super(message);
    }
}

class Category implements Comparable<Category>{
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Category o) {
        return this.name.compareTo(o.name);
    }
}

abstract class NewsItem {
    private String title;
    public Date date;
    private Category category;

    public NewsItem(String title, Date date, Category category) {
        this.title = title;
        this.date = date;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public abstract String getTeaser();

    @Override
    public String toString() {
        return getTeaser();
    }
}

class TextNewsItem extends NewsItem
{
    private String text;
    public TextNewsItem(String title, Date date, Category category,String text) {
        super(title, date, category);
        this.text = text;
    }



    @Override
    public String getTeaser()
    {
        long duration = Calendar.getInstance().getTime().getTime() - date.getTime();
        StringBuilder sb = new StringBuilder();
        sb.append(getTitle()).append("\n");

        sb.append(TimeUnit.MILLISECONDS.toMinutes(duration)).append("\n");

        if(text.length()<80)
        {
            sb.append(text).append("\n");
        }
        else
        {
            sb.append(text,0,80).append("\n");
        }
        return sb.toString();
    }
}

class MediaNewsItem extends NewsItem
{
    private String url;
    int views;

    public MediaNewsItem(String title, Date date, Category category, String url, int views) {
        super(title, date, category);
        this.url = url;
        this.views = views;
    }

    @Override
    public String getTeaser()
    {
        long duration = Calendar.getInstance().getTime().getTime() - date.getTime();
        StringBuilder sb = new StringBuilder();
        sb.append(getTitle()).append("\n");
        sb.append(TimeUnit.MILLISECONDS.toMinutes(duration)).append("\n");
        sb.append(url).append("\n");
        sb.append(views).append("\n");
        return sb.toString();
    }
}

class FrontPage {
    private ArrayList<NewsItem> vesti;
    private Category[] categories;

    public FrontPage(Category[] categories) {
        this.vesti = new ArrayList<>();
        this.categories = Arrays.copyOf(categories,categories.length);
    }

    void addNewsItem(NewsItem newsItem){
        vesti.add(newsItem);
    }

    List<NewsItem> listByCategory(Category category) throws CategoryNotFoundException {
        return vesti.stream().filter(i -> i.getCategory().equals(category)).collect(Collectors.toList());
    }

    List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException{
        for (Category c : categories)
        {
            if(c.getName().equals(category))
            {
                return vesti.stream().filter(i -> i.getCategory().getName().equals(category)).collect(Collectors.toList());
            }
        }
        throw new CategoryNotFoundException(String.format("Category %s was not found",category));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        vesti.forEach(sb::append);

        return sb.toString();
    }
}


public class zad17 {
    public static void main(String[] args) throws CategoryNotFoundException {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}


// Vasiot kod ovde
