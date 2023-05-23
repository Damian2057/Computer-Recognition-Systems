package p.lodz.pl.backend.repository;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.java.Log;
import p.lodz.pl.backend.model.PolicyEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Log
public class DBConnection implements Dao {

    private final String url;
    private final String user;
    private final String password;
    private Connection c = null;
    private Statement stmt = null;

    public DBConnection() {
        Dotenv dotenv = Dotenv.load();
        this.url = dotenv.get("POSTGRES_URL");
        this.user = dotenv.get("POSTGRES_USER");
        this.password = dotenv.get("POSTGRES_PASSWORD");
    }

    @Override
    public List<PolicyEntity> getPolicies() {
        try {
            List<PolicyEntity> policies = new ArrayList<>();
            //Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(url,
                            user, password);
            c.setAutoCommit(false);
            log.info("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM POLICY;" );
            while ( rs.next() ) {
                PolicyEntity entity = PolicyEntity.builder()
                        .policyId(rs.getString("policy_id"))
                        .policyTenure(rs.getDouble("policy_tenure"))
                        .ageOfCar(rs.getDouble("age_of_car"))
                        .ageOfPolicyHolder(rs.getDouble("age_of_policyholder"))
                        .populationDensity(rs.getDouble("population_density"))
                        .displacement(rs.getDouble("displacement"))
                        .turningRadius(rs.getDouble("turning_radius"))
                        .length(rs.getDouble("length"))
                        .width(rs.getDouble("width"))
                        .height(rs.getDouble("height"))
                        .grossWeight(rs.getDouble("gross_weight"))
                        .build();
                policies.add(entity);
            }
            rs.close();
            stmt.close();
            c.close();
            log.info("Closed database successfully");
            return policies;
        } catch (Exception e) {
            log.warning("Error while connecting to database");
            throw new RuntimeException(e);
        }
    }
}
