package p.lodz.pl.backend.fuzzy.util;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static List<Pair<List<Integer>, List<Integer>>> getSecondFormCombinations(int size) {
        List<Pair<List<Integer>, List<Integer>>> result = new ArrayList<>();

        List<List<Integer>> combinations = new ArrayList<>(getFirstFormCombinations(1, size));
        for (List<Integer> combination : combinations) {
            List<Integer> copy = new ArrayList<>(combination);
            List<Integer> supplement = new ArrayList<>(IntStream.rangeClosed(0, size - 1)
                    .boxed()
                    .toList());
            supplement.removeAll(copy);
            List<List<Integer>> sup = getFirstFormCombinations(supplement, 1, size);

            sup.forEach(x -> result.add(new Pair<>(copy, x)));
        }

        return result;
    }

    private static List<List<Integer>> getFirstFormCombinations(List<Integer> elems, int start, int size) {
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

    private static List<Integer> getCombinations(List<Integer> elements, int[] indexs) {
        List<Integer> combination = new ArrayList<>();
        for (int index : indexs) {
            combination.add(elements.get(index));
        }
        return combination;
    }
}