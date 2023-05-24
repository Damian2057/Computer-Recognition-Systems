import org.testng.Assert;
import org.testng.annotations.Test;
import p.lodz.pl.backend.fuzzy.function.TriangularFunction;
import p.lodz.pl.backend.fuzzy.function.domain.ContinuousDomain;
import p.lodz.pl.backend.fuzzy.set.FuzzySet;
import p.lodz.pl.backend.fuzzy.util.Operation;

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
}
