package p.lodz.pl.logic.quality;

public class ClassificationCategory {
    private final String type;
    private double acc;
    private double pre;
    private double rec;
    private double f1;

    private int positivelyCorrect = 0;
    private int allClassified = 0;
    private int realNumberOfItems = 0;

    public ClassificationCategory(String type) {
        this.type = type;
    }

    public void calculate() {
        calculatePre();
        calculateRec();
        calculateF1();
    }

    public String getType() {
        return type;
    }

    public double getAcc() {
        return acc;
    }

    public void setAcc(double acc) {
        this.acc = acc;
    }

    public double getPre() {
        return pre;
    }

    public double getRec() {
        return rec;
    }

    public double getF1() {
        return f1;
    }

    public int getPositivelyCorrect() {
        return positivelyCorrect;
    }

    public void setPositivelyCorrect(int positivelyCorrect) {
        this.positivelyCorrect = positivelyCorrect;
    }

    public int getAllClassified() {
        return allClassified;
    }

    public void setAllClassifiedToCategory(int allClassified) {
        this.allClassified = allClassified;
    }

    public int getRealNumberOfItems() {
        return realNumberOfItems;
    }

    public void setRealNumberOfItems(int realNumberOfItems) {
        this.realNumberOfItems = realNumberOfItems;
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
