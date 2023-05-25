import org.testng.Assert;
import org.testng.annotations.Test;
import p.lodz.pl.backend.fuzzy.function.TriangularFunction;
import p.lodz.pl.backend.fuzzy.function.domain.ContinuousDomain;
import p.lodz.pl.backend.fuzzy.set.FuzzySet;
import p.lodz.pl.backend.fuzzy.util.Operation;

import java.util.ArrayList;
import java.util.List;

public class FuzzyLogicTest {

    @Test
    public void OperationTest() {
        FuzzySet<Double> fuzzySet = new FuzzySet<>(x -> x, new TriangularFunction(new ContinuousDomain(0, 1), 0, 0.5, 1));
        double a = fuzzySet.getMemberShip(0.5);
        Operation<FuzzySet<Double>> operation = new Operation<>();
        FuzzySet<Double> fuzzySet1 = operation.not(fuzzySet);
        double b = fuzzySet1.getMemberShip(0.5);

        Assert.assertNotEquals(a, b);
    }

    @Test
    public void logicFirstFormTest() {
        List<String> sum = new ArrayList<>();
        sum.add("S1");
        sum.add("S2");
        sum.add("S3");
        //sum.add("S4");

        for (String s : sum) {
            System.out.println(s);
        }
        System.out.println("-------------------");
        for (int i = 0; i < sum.size(); i++) {
            for (int j = i; j < sum.size(); j++) {
                if (i != j) {
                    System.out.println(sum.get(i) + " " + sum.get(j));
                }
            }
        }
        System.out.println("-------------------");
        for (int i = 2; i < sum.size(); i++) {
            StringBuilder xd = new StringBuilder();
            for (int j = 0; j < i + 1; j++) {
                xd.append(sum.get(j)).append(" ");
            }
            System.out.println(xd);
        }
    }

    @Test
    public void logicSecondFormTest() {
        List<String> sum = new ArrayList<>();
        sum.add("S1");
        sum.add("S2");
        sum.add("S3");
        //sum.add("S4");

        for (int i = 0; i < sum.size(); i++) {
            for (int j = 0; j < sum.size(); j++) {
                if (i != j) {
                    System.out.println(sum.get(i) + " " + sum.get(j));
                }
            }
        }
        System.out.println("-------------------");

    }

}
