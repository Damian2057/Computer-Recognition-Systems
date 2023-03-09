package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.logic.CommonWords;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import java.util.List;

public class DocumentLengthExtractor implements SpecificExtractor {
    @Override
    public Feature<?> extract(Article article) {
        List<String> words = CommonWords.remove(article);
        return new Feature<Double>(Type.DOCUMENT_LENGTH, (double) words.size());
    }
}
