package com.vege.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_vegeinfo")
public class VegeInfo {

    @Id
    @GeneratedValue
    @Column(name ="vegeid")
    private int vegeId;

    @Column(name = "vegename")
    private String vegeName;

    @Column(name = "alias")
    private String alias;

    @Column(name = "imguuid")
    private String imgUuid;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "classification")
    private String classification;

    @Column(name = "note")
    private String note;

    @Column(name = "updatetime")
    private Timestamp updateTime;

    @OneToMany(cascade = {CascadeType.REFRESH,CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BreedStage> breedStages = new LinkedList<>();

    @OneToMany(cascade = {CascadeType.REFRESH,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Variety> varieties = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JsonIgnore
    private List<VegeKnowledge> vegeKnowledges = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.REFRESH,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Disease> diseases = new ArrayList<>();

    public int getVegeId() {
        return vegeId;
    }

    public void setVegeId(int vegeId) {
        this.vegeId = vegeId;
    }

    public String getVegeName() {
        return vegeName;
    }

    public void setVegeName(String vegeName) {
        this.vegeName = vegeName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getImgUuid() {
        return imgUuid;
    }

    public void setImgUuid(String imgUuid) {
        this.imgUuid = imgUuid;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public List<BreedStage> getBreedStages() {
        return breedStages;
    }

    public void setBreedStages(List<BreedStage> breedStages) {
        this.breedStages = breedStages;
    }

    public List<Variety> getVarieties() {
        return varieties;
    }

    public void setVarieties(List<Variety> varieties) {
        this.varieties = varieties;
    }

    public List<VegeKnowledge> getVegeKnowledges() {
        return vegeKnowledges;
    }

    public void setVegeKnowledges(List<VegeKnowledge> vegeKnowledges) {
        this.vegeKnowledges = vegeKnowledges;
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }
}