package com.jam3na.fitnessgo;

public class fitness {
    private String day;
    private String date;
    private String workType;
    private String wordDesc;
    private String goesQu;

    public fitness() {
    }

    public fitness(String day, String date, String workType, String wordDesc, String goesQu) {
        this.day = day;
        this.date = date;
        this.workType = workType;
        this.wordDesc = wordDesc;
        this.goesQu = goesQu;
    }

    public String getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public String getWorkType() {
        return workType;
    }

    public String getWordDesc() {
        return wordDesc;
    }

    public String getGoesQu() {
        return goesQu;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public void setWordDesc(String wordDesc) {
        this.wordDesc = wordDesc;
    }

    public void setGoesQu(String goesQu) {
        this.goesQu = goesQu;
    }

    @Override
    public String toString() {
        return "fitness{" +
                "day='" + day + '\'' +
                ", date='" + date + '\'' +
                ", workType='" + workType + '\'' +
                ", wordDesc='" + wordDesc + '\'' +
                ", goesQu='" + goesQu + '\'' +
                '}';
    }

}
