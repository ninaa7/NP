package Kolokviumski;

import java.util.Scanner;

class MinMax <T extends Comparable<T>> {
    T min;
    T max;
    int counterMin;
    int counterMax;
    int total;

    void update(T element) {
        if(total==0)
        {
            min=element;
            max=element;
        }
        ++total;
        if(element.compareTo(min)>0) //elementot e pomal od min
        {
            counterMin=1;
            min = element;
        }
        else if (element.compareTo(min)==0)
        {
            counterMin++;
        }
        if(element.compareTo(max)<0) //elementot e pogolem od max
        {
            counterMax=1;
            max = element;
        }
        else if (element.compareTo(max)==0)
        {
            counterMax++;
        }
    }

    T max() {
        return max;
    }

    T min()
    {
        return min;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d\n",max,min,total-(counterMax+counterMin));
    }

}

public class zad5 {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<Integer>();
        for(int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }
}
