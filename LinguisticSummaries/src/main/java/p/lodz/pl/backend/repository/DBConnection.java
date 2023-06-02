package p.lodz.pl.backend.repository;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.java.Log;
import p.lodz.pl.backend.model.PolicyEntity;
import p.lodz.pl.backend.model.Serialize;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Log
public class DBConnection implements Dao {

    private static final String FILE = "data.raw";
    private String url;
    private String user;
    private String password;
    private Connection c = null;
    private Statement stmt = null;

    @Override
    public List<PolicyEntity> getPolicies() {
        if (checkIfFileExist()) {
            return read();
        }
        try {
            readDotEnv();
            List<PolicyEntity> policies = new ArrayList<>();
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
            savePolicies(policies);
            return policies;
        } catch (Exception e) {
            log.warning("Error while connecting to database");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void savePolicies(List<PolicyEntity> policies) {
        try {
            Serialize<PolicyEntity> serialize = new Serialize<>(policies);
            FileOutputStream f = new FileOutputStream(FILE);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(serialize);

            o.close();
            f.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkIfFileExist() {
        File file = new File(FILE);
        return file.exists();
    }

    private void readDotEnv() {
        Dotenv dotenv = Dotenv.load();
        this.url = dotenv.get("POSTGRES_URL");
        this.user = dotenv.get("POSTGRES_USER");
        this.password = dotenv.get("POSTGRES_PASSWORD");
    }

    public List<PolicyEntity> read() {
        try {
            FileInputStream fi = new FileInputStream(FILE);
            ObjectInputStream oi = new ObjectInputStream(fi);

            Serialize<PolicyEntity> serialize = (Serialize<PolicyEntity>) oi.readObject();

            return serialize.getList();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
