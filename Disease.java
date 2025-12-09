package com.health.model;
import java.util.List;

public class Disease {
    private int id;
    private String name;
    private String precaution;
    private String medicine;
    private List<Integer> symptomIds;

    public Disease(){}

    public Disease(int id, String name, String precaution, String medicine){
        this.id = id; this.name = name; this.precaution = precaution; this.medicine = medicine;
    }

    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getPrecaution(){ return precaution; }
    public void setPrecaution(String precaution){ this.precaution = precaution; }

    public String getMedicine(){ return medicine; }
    public void setMedicine(String medicine){ this.medicine = medicine; }

    public List<Integer> getSymptomIds(){ return symptomIds; }
    public void setSymptomIds(List<Integer> symptomIds){ this.symptomIds = symptomIds; }
}
