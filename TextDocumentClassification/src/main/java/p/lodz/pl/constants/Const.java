package p.lodz.pl.constants;

public enum Const {
    CONFIG("src/main/resources/config/config.json"),
    ARTICLES("src/main/resources/articles"),
    CURRENCY_DICTIONARY("src/main/resources/dictionaries/currency.txt"),
    COMMON_WORDS_DICTIONARY("src/main/resources/dictionaries/commonWords.txt"),
    GEOGRAPHICAL_PLACES_DICTIONARY("src/main/resources/dictionaries/geographicalPlaces.txt"),
    HISTORICAL_FIGURES_DICTIONARY("src/main/resources/dictionaries/historicalFigures.txt"),
    KEY_WORDS_DICTIONARY("src/main/resources/dictionaries/keyWords.txt"),
    COUNTRY_DICTIONARY("src/main/resources/dictionaries/country.txt"),
    SERIALIZE_VECTORS("src/main/resources/serialize/vectors.raw"),
    SERIALIZE_PATH("src/main/resources/serialize"),
    SPACE(" "),
    PLACES("PLACES"),
    D("D"),
    REUTERS("REUTERS"),
    DATE("DATE"),
    TOPICS("TOPICS"),
    PEOPLE("PEOPLE"),
    TITLE("TITLE"),
    BODY("BODY"),
    TEXT("TEXT"),
    TRAINING("training"),
    TEST("test");

    private final String name;

    Const(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
