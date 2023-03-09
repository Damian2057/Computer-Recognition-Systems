package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.dao.Dictionary;
import p.lodz.pl.dao.DictionaryReader;

public abstract class Extractor {
    protected final Dictionary dictionary = new DictionaryReader();
}
