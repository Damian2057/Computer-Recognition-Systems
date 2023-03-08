package p.lodz.pl.constants;

public enum Const {
    ARTICLES("src/main/resources/articles"),
    CURRENCY_DICTIONARY("src/main/resources/dictionaries/currency.csv"),
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
    COUNTRY_REGEX("west-germany|USA");

    private final String name;

    Const(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
