package p.lodz.pl.backend.fuzzy.summary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Combiner {

    private Combiner() {
    }

    public static List<List<Integer>> getFirstFormCombinations(int start, int size) {
        List<Integer> elems = IntStream.rangeClosed(0, size - 1)
                .boxed()
                .toList();

        List<List<Integer>> combinations = new ArrayList<>();

        for (int k = start; k <= size; k++) {
            int[] index = new int[k];
            Arrays.fill(index, -1);

            int i = 0;
            while (i >= 0) {
                index[i]++;
                if (index[i] >= elems.size()) {
                    i--;
                } else if (i == k - 1) {
                    combinations.add(getCombinations(elems, index));
                } else {
                    i++;
                    index[i] = index[i - 1];
                }
            }
        }

        return combinations;
    }

    public static List<List<Integer>> getSecondFormCombinations(int size) {
        List<Integer> elems = new ArrayList<>(IntStream.rangeClosed(0, size - 1)
                .boxed()
                .toList());

        elems.sort(Integer::compareTo); // Sort the elements in ascending order
        List<List<Integer>> combinations = new ArrayList<>();

        for (int i = 2; i <= size; i++) {
            List<Integer> currentCombination = new ArrayList<>();
            boolean[] used = new boolean[elems.size()];
            generateSecondForm(elems, currentCombination, used, 0, i, combinations);
        }

        return combinations;
    }

    private static void generateSecondForm(List<Integer> elements, List<Integer> currentCombination,
                                                  boolean[] used, int startIndex, int remainingElements,
                                                  List<List<Integer>> combinations) {
        if (remainingElements == 0) {
            combinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int i = startIndex; i < elements.size(); i++) {
            if (!used[i]) {
                used[i] = true;
                currentCombination.add(elements.get(i));
                generateSecondForm(elements, currentCombination, used, startIndex, remainingElements - 1, combinations);
                currentCombination.remove(currentCombination.size() - 1);
                used[i] = false;
            }
        }
    }

    private static List<Integer> getCombinations(List<Integer> elements, int[] indexs) {
        List<Integer> combination = new ArrayList<>();
        for (int index : indexs) {
            combination.add(elements.get(index));
        }
        return combination;
    }
}