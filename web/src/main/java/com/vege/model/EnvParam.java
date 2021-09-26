package com.vege.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_environmentparameter")
public class EnvParam {

    @Id
    @Column(name = "epid")
    @GeneratedValue
    private Integer epId;

    @Column(name = "paraname")
    private String paraName;

    @Column(name = "type")
    private boolean type;

    @Column(name = "boundh")
    private Double boundH;

    @Column(name = "boundl")
    private Double boundL;

    @Column(name = "paramunit")
    private String paramUnit;

    @Column(name = "note")
    private String note;

    @Column(name = "updatetime")
    private Timestamp updateTime;

    //多对一
    @ManyToOne(targetEntity = BreedStage.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "bsid", referencedColumnName = "bsid", insertable = false, unique = false)
    private BreedStage breedStage;

    public Integer getEpId() {
        return epId;
    }

    public void setEpId(Integer epId) {
        this.epId = epId;
    }

    public String getParaName() {
        return paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public Double getBoundH() {
        return boundH;
    }

    public void setBoundH(Double boundH) {
        this.boundH = boundH;
    }

    public Double getBoundL() {
        return boundL;
    }

    public void setBoundL(Double boundL) {
        this.boundL = boundL;
    }

    public String getParamUnit() {
        return paramUnit;
    }

    public void setParamUnit(String paramUnit) {
        this.paramUnit = paramUnit;
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

    public BreedStage getBreedStage() {
        return breedStage;
    }

    public void setBreedStage(BreedStage breedStage) {
        this.breedStage = breedStage;
    }
}
