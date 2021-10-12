package com.vege.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_drug")
public class Drug {

    @Id
    @GeneratedValue
    @Column(name ="drugid")
    private int drugId;

    @Column(name = "drugname")
    private String drugName;

    @Column(name = "methods")
    private String methods;

    @Column(name = "dosage")
    private String dosage;

    @Column(name = "saveperiod")
    private String savePeriod;

    @Column(name = "attention")
    private String attention;

    @Column(name = "updatetime")
    private Timestamp updateTime;

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getSavePeriod() {
        return savePeriod;
    }

    public void setSavePeriod(String savePeriod) {
        this.savePeriod = savePeriod;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
