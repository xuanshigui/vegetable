package com.vege.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_cure")
public class Cure {

    @Id
    @GeneratedValue
    @Column(name ="cureid")
    private int cureId;

    @Column(name="cureName")
    private String cureName;

    @Column(name = "agricontrol")
    private String agriControl;

    @Column(name = "biocontrol")
    private String bioControl;

    @Column(name = "chemcontrol")
    private String chemControl;

    @ManyToOne
    private Disease disease;

    public int getCureId() {
        return cureId;
    }

    public void setCureId(int cureId) {
        this.cureId = cureId;
    }

    public String getCureName() {
        return cureName;
    }

    public void setCureName(String cureName) {
        this.cureName = cureName;
    }

    public String getAgriControl() {
        return agriControl;
    }

    public void setAgriControl(String agriControl) {
        this.agriControl = agriControl;
    }

    public String getBioControl() {
        return bioControl;
    }

    public void setBioControl(String bioControl) {
        this.bioControl = bioControl;
    }

    public String getChemControl() {
        return chemControl;
    }

    public void setChemControl(String chemControl) {
        this.chemControl = chemControl;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }
}
