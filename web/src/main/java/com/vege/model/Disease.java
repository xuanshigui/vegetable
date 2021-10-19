package com.vege.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_disease")
public class Disease {

    @Id
    @GeneratedValue
    @Column(name ="diseaseid")
    private int diseaseId;

    @Column(name = "diseasename")
    private String diseaseName;

    @Column(name = "imguuid0")
    private String imgUuid0;

    @Column(name = "imguuid1")
    private String imgUuid1;

    @Column(name = "imguuid2")
    private String imgUuid2;

    @Column(name = "imguuid3")
    private String imgUuid3;

    @Column(name = "etiology")
    private String etiology;

    @Column(name = "regularity")
    private String regularity;

    @Column(name = "diseasetype")
    private Integer diseaseType;

    @ManyToOne
    private VegeInfo vegeInfo;

    @OneToMany
    @JsonIgnore
    private List<Symptom> symptoms = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<Cure> cures = new ArrayList<>();;

    @Column(name = "updatetime")
    private Timestamp updateTime;

    public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getImgUuid0() {
        return imgUuid0;
    }

    public void setImgUuid0(String imgUuid0) {
        this.imgUuid0 = imgUuid0;
    }

    public String getImgUuid1() {
        return imgUuid1;
    }

    public void setImgUuid1(String imgUuid1) {
        this.imgUuid1 = imgUuid1;
    }

    public String getImgUuid2() {
        return imgUuid2;
    }

    public void setImgUuid2(String imgUuid2) {
        this.imgUuid2 = imgUuid2;
    }

    public String getImgUuid3() {
        return imgUuid3;
    }

    public void setImgUuid3(String imgUuid3) {
        this.imgUuid3 = imgUuid3;
    }

    public String getEtiology() {
        return etiology;
    }

    public void setEtiology(String etiology) {
        this.etiology = etiology;
    }

    public String getRegularity() {
        return regularity;
    }

    public void setRegularity(String regularity) {
        this.regularity = regularity;
    }

    public Integer getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(Integer diseaseType) {
        this.diseaseType = diseaseType;
    }

    public VegeInfo getVegeInfo() {
        return vegeInfo;
    }

    public void setVegeInfo(VegeInfo vegeInfo) {
        this.vegeInfo = vegeInfo;
    }

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public List<Cure> getCures() {
        return cures;
    }

    public void setCures(List<Cure> cures) {
        this.cures = cures;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
