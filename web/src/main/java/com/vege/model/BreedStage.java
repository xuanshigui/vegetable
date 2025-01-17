package com.vege.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

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

    @Column(name = "update_time")
    private Timestamp updateTime;

    //一对多
    @OneToMany(cascade = {CascadeType.REFRESH,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EnvParam> envParams = new LinkedList<>();

    @ManyToOne
    @JoinColumn(name = "vegeid", referencedColumnName = "vegeid")
    private VegeInfo vegeInfo;

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

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public List<EnvParam> getEnvParams() {
        return envParams;
    }

    public void setEnvParams(List<EnvParam> envParams) {
        this.envParams = envParams;
    }

    public VegeInfo getVegeInfo() {
        return vegeInfo;
    }

    public void setVegeInfo(VegeInfo vegeInfo) {
        this.vegeInfo = vegeInfo;
    }
}
