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
@Table(name = "tb_knowledgecategory")
public class KnowledgeCategory {

    @Id
    @GeneratedValue
    @Column(name ="kcid")
    private Integer kcId;

    @Column(name = "knowledgecategoryname")
    private String knowledgeCategoryName;

    @Column(name = "introduction")
    private String introduction;

    @OneToMany(cascade = CascadeType.REFRESH)
    @JsonIgnore
    private List<VegeKnowledge>  vegeknowledges = new ArrayList<>();

    @Column(name = "updatetime")
    private Timestamp updateTime;

    public Integer getKcId() {
        return kcId;
    }

    public void setKcId(Integer kcId) {
        this.kcId = kcId;
    }

    public String getKnowledgeCategoryName() {
        return knowledgeCategoryName;
    }

    public void setKnowledgeCategoryName(String knowledgeCategoryName) {
        this.knowledgeCategoryName = knowledgeCategoryName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<VegeKnowledge> getVegeknowledges() {
        return vegeknowledges;
    }

    public void setVegeknowledges(List<VegeKnowledge> vegeknowledges) {
        this.vegeknowledges = vegeknowledges;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
