package com.vege.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_variety")
public class Variety {

    @GeneratedValue
    @Id
    @Column(name = "varietyid")
    private int varietyId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "vegeid", referencedColumnName = "vegeid")
    private VegeInfo vegeInfo;

    @Column(name = "varietyname")
    private String varietyName;

    @Column(name = "description")
    private String description;

    @Column(name = "area")
    private String area;

    @Column(name = "imguuid")
    private String imgUuid;

    @Column(name = "source")
    private String source;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private List<CultivateMode> cultivateModes = new ArrayList<>();

    public int getVarietyId() {
        return varietyId;
    }

    public void setVarietyId(int varietyId) {
        this.varietyId = varietyId;
    }

    public VegeInfo getVegeInfo() {
        return vegeInfo;
    }

    public void setVegeInfo(VegeInfo vegeInfo) {
        this.vegeInfo = vegeInfo;
    }

    public String getVarietyName() {
        return varietyName;
    }

    public void setVarietyName(String varietyName) {
        this.varietyName = varietyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getImgUuid() {
        return imgUuid;
    }

    public void setImgUuid(String imgUuid) {
        this.imgUuid = imgUuid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public List<CultivateMode> getCultivateModes() {
        return cultivateModes;
    }

    public void setCultivateModes(List<CultivateMode> cultivateModes) {
        this.cultivateModes = cultivateModes;
    }
}
