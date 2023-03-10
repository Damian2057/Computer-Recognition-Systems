package p.lodz.pl.config;

import com.google.gson.Gson;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static p.lodz.pl.constants.Const.CONFIG;

public class Config {

    private static Properties properties;

    public static Properties getProperties() {
        try {
            if (properties == null) {
                Path filePath = Path.of(CONFIG.getName());
                Gson gson = new Gson();
                properties =  gson.fromJson(Files.readString(filePath), Properties.class);
                if (Arrays.stream(properties.getProportionOfDataSets()).sum() != 100.0) {
                    throw new RuntimeException("The percentage division of the collection does not make up 100%");
                }
            }
            return properties;
        } catch (Exception e) {
            throw new RuntimeException("Error while reading startup parameters", e);
        }
    }
}
