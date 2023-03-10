package p.lodz.pl;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import p.lodz.pl.constants.Const;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * This class is used to prepare glossaries based on which data analysis takes place
 */
public class PrepareFile {

    private static final boolean enable = false;
    private static final Logger LOGGER = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final String output = "output";
    private String input;
    private List<String> dictionaries;

    @BeforeClass
    public void setUp() {
        input = Const.KEY_WORDS_DICTIONARY.getName();
        dictionaries = Arrays.asList(Const.COMMON_WORDS_DICTIONARY.getName(),
                Const.COUNTRY_DICTIONARY.getName(),
                Const.GEOGRAPHICAL_PLACES_DICTIONARY.getName(),
                Const.HISTORICAL_FIGURES_DICTIONARY.getName(),
                Const.CURRENCY_DICTIONARY.getName());

        LOGGER.info(String.format("Convert file %s to output file %s", input, output));
    }

    @Test(enabled = enable, priority = 1)
    public void removeDuplicates() {
        LOGGER.info("Deleting duplicates");
        HashSet<String> words = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Problem with reading the loaded file", e);
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(output))) {
            for (String word : words) {
<<<<<<< HEAD
                if (word.length() > 1) {
                    writer.println(word);
                }
=======
                writer.println(word);
>>>>>>> origin/dev
            }
        } catch (IOException e) {
            throw new RuntimeException("Problem with saving the output file", e);
        }
    }

    @Test(enabled = enable, priority = 2)
    public void removeWordIfExistInAnotherFile() {
        String outputName = "final" + output + ".txt";
        for (String fileToCheck : dictionaries) {
            HashSet<String> firstWords = new HashSet<>();
            HashSet<String> secondWords = new HashSet<>();
            try (BufferedReader reader1 = new BufferedReader(new FileReader(output));
                 BufferedReader reader2 = new BufferedReader(new FileReader(fileToCheck))) {
                String line;
                while ((line = reader1.readLine()) != null) {
                    firstWords.add(line);
                }
                while ((line = reader2.readLine()) != null) {
                    secondWords.add(line);
                }
            } catch (IOException e) {
                throw new RuntimeException("Problem with reading the loaded files", e);
            }
            firstWords.removeAll(secondWords);
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputName))) {
                for (String word : firstWords) {
                    writer.println(word);
                }
            } catch (IOException e) {
                throw new RuntimeException("Problem with saving the output file", e);
            }
        }
        LOGGER.info("Completed!");
    }

}
