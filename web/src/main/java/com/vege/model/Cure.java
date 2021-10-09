package com.vege.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_cure")
public class Cure {

    @Id
    @GeneratedValue
    @Column(name ="cureid")
    private int cureId;

    @Column(name = "agricontrol")
    private String agriControl;

    @Column(name = "biocontrol")
    private String bioControl;

    @Column(name = "chemcontrol")
    private String chenControl;

    @ManyToOne
    private Disease disease;

    @OneToMany
    private List<Drug> drugs = new ArrayList<>();

    public int getCureId() {
        return cureId;
    }

    public void setCureId(int cureId) {
        this.cureId = cureId;
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

    public String getChenControl() {
        return chenControl;
    }

    public void setChenControl(String chenControl) {
        this.chenControl = chenControl;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }
}
