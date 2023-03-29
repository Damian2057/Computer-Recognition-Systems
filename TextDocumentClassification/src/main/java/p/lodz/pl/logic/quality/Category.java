package p.lodz.pl.logic.quality;

import lombok.Data;

@Data
public class Category {
    private final String type;
    private double acc;
    private double pre;
    private double rec;
    private double f1;

    private int positivelyCorrect = 0;
    private int allClassified = 0;
    private int realNumberOfItems = 0;

    public Category(String type) {
        this.type = type;
    }

    void calculate() {
        calculatePre();
        calculateRec();
        calculateF1();
    }

    private void calculatePre() {
        this.pre = 1.0 * positivelyCorrect / allClassified;
    }

    private void calculateRec() {
        this.rec = 1.0 * positivelyCorrect / realNumberOfItems;
    }

    private void calculateF1() {
        this.f1 = 2.0 * pre * rec / (pre + rec);
    }
}
