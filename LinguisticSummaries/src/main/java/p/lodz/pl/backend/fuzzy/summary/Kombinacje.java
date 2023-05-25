package p.lodz.pl.backend.fuzzy.summary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Kombinacje {

    public static List<List<Integer>> generujKombinacje(List<Integer> elementy, int n) {
        List<List<Integer>> kombinacje = new ArrayList<>();

        int[] indeksy = new int[n];
        Arrays.fill(indeksy, -1);

        int i = 0;
        while (i >= 0) {
            indeksy[i]++;
            if (indeksy[i] >= elementy.size()) {
                i--;
            } else if (i == n - 1) {
                kombinacje.add(pobierzKombinacje(elementy, indeksy));
            } else {
                i++;
                indeksy[i] = indeksy[i - 1];
            }
        }

        return kombinacje;
    }

    private static List<Integer> pobierzKombinacje(List<Integer> elementy, int[] indeksy) {
        List<Integer> kombinacja = new ArrayList<>();
        for (int indeks : indeksy) {
            kombinacja.add(elementy.get(indeks));
        }
        return kombinacja;
    }

    public static void main(String[] args) {
        List<Integer> elementy = new ArrayList<>();
        elementy.add(1);
        elementy.add(2);
        elementy.add(3);
        elementy.add(4);
        List<List<Integer>> kombinacje = generujKombinacje(elementy, 3);

        for (List<Integer> kombinacja : kombinacje) {
            System.out.println(kombinacja);
        }
    }
}