import p.lodz.pl.backend.repository.DBConnection;
import p.lodz.pl.backend.repository.Dao;

public class DataBaseTest {
    public static void main(String[] args) {
        Dao dao = new DBConnection();
        System.out.println(dao.getPolicies().size());
    }
}
