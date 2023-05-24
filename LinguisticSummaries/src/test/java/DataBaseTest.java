import org.testng.Assert;
import org.testng.annotations.Test;
import p.lodz.pl.backend.repository.DBConnection;
import p.lodz.pl.backend.repository.Dao;

public class DataBaseTest {

    @Test
    public void dataBaseConnectionTest() {
        Dao dao = new DBConnection();
        Assert.assertTrue(dao.getPolicies().size() > 0);
    }
}
