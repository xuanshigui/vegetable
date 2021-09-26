package com.vege.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_breedstage")
public class BreedStage {

    @Id
    @Column(name = "bsid")
    @GeneratedValue
    private Integer bsId;

    @Column(name = "stagename")
    private String stageName;

    @Column(name = "definition")
    private String definition;

    @Column(name = "duration")
    private String duration;

    @Column(name = "vegeid")
    private Integer vegeId;

    @Column(name = "update_time")
    private Timestamp updateTime;

    //一对多
    @OneToMany(cascade = CascadeType.ALL)
    private Set<EnvParam> envParams = new HashSet<>();

    public Integer getBsId() {
        return bsId;
    }

    public void setBsId(Integer bsId) {
        this.bsId = bsId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getVegeId() {
        return vegeId;
    }

    public void setVegeId(Integer vegeId) {
        this.vegeId = vegeId;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Set<EnvParam> getEnvParams() {
        return envParams;
    }

    public void setEnvParams(Set<EnvParam> envParams) {
        this.envParams = envParams;
    }
}
