package p.lodz.pl.config;

import com.google.gson.Gson;

import java.nio.file.Files;
import java.nio.file.Path;

import static p.lodz.pl.constants.Const.CONFIG;

public class Config {
    public static Properties getProperties() {
        try {
            Path filePath = Path.of(CONFIG.getName());
            Gson gson = new Gson();
            return gson.fromJson(Files.readString(filePath), Properties.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
