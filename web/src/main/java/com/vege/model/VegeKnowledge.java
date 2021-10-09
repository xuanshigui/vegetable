package com.vege.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_vegeknowledge")
public class VegeKnowledge {

    @Id
    @GeneratedValue
    @Column(name ="vkid")
    private Integer vkId;

    @ManyToOne
    private VegeInfo vegeInfo;

    @ManyToOne
    private KnowledgeCategory knowledgeCategory;

    @Column(name = "headline")
    private String headline;

    @Column(name = "content")
    private String content;

    @Column(name = "updatetime")
    private Timestamp updateTime;

    public Integer getVkId() {
        return vkId;
    }

    public void setVkId(Integer vkId) {
        this.vkId = vkId;
    }

    public VegeInfo getVegeInfo() {
        return vegeInfo;
    }

    public void setVegeInfo(VegeInfo vegeInfo) {
        this.vegeInfo = vegeInfo;
    }

    public KnowledgeCategory getKnowledgeCategory() {
        return knowledgeCategory;
    }

    public void setKnowledgeCategory(KnowledgeCategory knowledgeCategory) {
        this.knowledgeCategory = knowledgeCategory;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
