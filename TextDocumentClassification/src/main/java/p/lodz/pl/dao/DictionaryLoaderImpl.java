package p.lodz.pl.dao;

import p.lodz.pl.constants.Const;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DictionaryLoaderImpl implements DictionaryLoader<String> {
    @Override
    public List<String> read(Const path) {
        List<String> words = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(path.getName()))) {
            String line = reader.readLine();

            while (line != null) {
                words.add(line);
                line = reader.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(String.format("Dictionary %s reading error", path.name()), e);
        }
        return words;
    }
}
