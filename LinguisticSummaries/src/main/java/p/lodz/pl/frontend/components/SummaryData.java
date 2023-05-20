package p.lodz.pl.frontend.components;


public class SummaryData {
    private String form;
    private String summary;
    private String degreeOfTruth;

    public SummaryData(String form, String summary, String degreeOfTruth) {
        this.form = form;
        this.summary = summary;
        this.degreeOfTruth = degreeOfTruth;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getSummary() {
        return summary;
    }

    public String getDegreeOfTruth() {
        return degreeOfTruth;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDegreeOfTruth(String degreeOfTruth) {
        this.degreeOfTruth = degreeOfTruth;
    }
}
