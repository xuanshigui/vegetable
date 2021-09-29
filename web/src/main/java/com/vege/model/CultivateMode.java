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
@Table(name = "tb_cultivatemode")
public class CultivateMode {

    @Id
    @GeneratedValue
    @Column(name = "cmid")
    private Integer cmId;

    @Column(name = "cultivatemodename")
    private String cultivateModeName;

    @Column(name = "note")
    private String note;

    @Column(name = "updatetime")
    private Timestamp updateTime;

    @ManyToMany(mappedBy = "cultivateModes", cascade = {CascadeType.REMOVE,CascadeType.PERSIST},fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Variety> varieties = new ArrayList<>();

    public Integer getCmId() {
        return cmId;
    }

    public void setCmId(Integer cmId) {
        this.cmId = cmId;
    }

    public String getCultivateModeName() {
        return cultivateModeName;
    }

    public void setCultivateModeName(String cultivateModeName) {
        this.cultivateModeName = cultivateModeName;
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

    public List<Variety> getVarieties() {
        return varieties;
    }

    public void setVarieties(List<Variety> varieties) {
        this.varieties = varieties;
    }
}
