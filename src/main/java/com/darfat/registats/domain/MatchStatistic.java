package com.darfat.registats.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A MatchStatistic.
 */
@Entity
@Table(name = "match_statistic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "matchstatistic")
public class MatchStatistic implements Serializable {

    private static final long serialVersionUID = 1L;

    public MatchStatistic(){

    }
    public MatchStatistic(MatchTeamInfo teamInfo,MatchStatisticItem item){
        this.matchTeamInfo = teamInfo;
        this.statistic = item;
        this.value = new Long(0);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private Long value;

    @Column(name = "value_double")
    private Double valueDouble;

    @Column(name = "value_long")
    private Long valueLong;

    @Column(name = "value_string")
    private String valueString;

    @ManyToOne
    @JsonIgnoreProperties("matchStatistics")
    private Team team;

    @ManyToOne
    @JsonIgnoreProperties("matchStatistics")
    private MatchStatisticItem statistic;

//    @ManyToOne
//    @JsonIgnoreProperties("matchStatistics")
//    private MatchHomeInfo matchHomeInfo;
//
//    @ManyToOne
//    @JsonIgnoreProperties("matchStatistics")
//    private MatchAwayInfo matchAwayInfo;

    @ManyToOne
    @JsonIgnore
    private MatchTeamInfo matchTeamInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public MatchStatistic description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getValue() {
        return value;
    }

    public MatchStatistic value(Long value) {
        this.value = value;
        return this;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Double getValueDouble() {
        return valueDouble;
    }

    public MatchStatistic valueDouble(Double valueDouble) {
        this.valueDouble = valueDouble;
        return this;
    }

    public void setValueDouble(Double valueDouble) {
        this.valueDouble = valueDouble;
    }

    public Long getValueLong() {
        return valueLong;
    }

    public MatchStatistic valueLong(Long valueLong) {
        this.valueLong = valueLong;
        return this;
    }

    public void setValueLong(Long valueLong) {
        this.valueLong = valueLong;
    }

    public String getValueString() {
        return valueString;
    }

    public MatchStatistic valueString(String valueString) {
        this.valueString = valueString;
        return this;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public Team getTeam() {
        return team;
    }

    public MatchStatistic team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public MatchStatisticItem getStatistic() {
        return statistic;
    }

    public MatchStatistic statistic(MatchStatisticItem matchStatisticItem) {
        this.statistic = matchStatisticItem;
        return this;
    }

    public void setStatistic(MatchStatisticItem matchStatisticItem) {
        this.statistic = matchStatisticItem;
    }

//    public MatchHomeInfo getMatchHomeInfo() {
//        return matchHomeInfo;
//    }
//
//    public MatchStatistic matchHomeInfo(MatchHomeInfo matchHomeInfo) {
//        this.matchHomeInfo = matchHomeInfo;
//        return this;
//    }
//
//    public void setMatchHomeInfo(MatchHomeInfo matchHomeInfo) {
//        this.matchHomeInfo = matchHomeInfo;
//    }
//
//    public MatchAwayInfo getMatchAwayInfo() {
//        return matchAwayInfo;
//    }
//
//    public MatchStatistic matchAwayInfo(MatchAwayInfo matchAwayInfo) {
//        this.matchAwayInfo = matchAwayInfo;
//        return this;
//    }
//
//    public void setMatchAwayInfo(MatchAwayInfo matchAwayInfo) {
//        this.matchAwayInfo = matchAwayInfo;
//    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchStatistic)) {
            return false;
        }
        return id != null && id.equals(((MatchStatistic) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MatchStatistic{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", value=" + getValue() +
            ", valueDouble=" + getValueDouble() +
            ", valueLong=" + getValueLong() +
            ", valueString='" + getValueString() + "'" +
            "}";
    }

    public MatchTeamInfo getMatchTeamInfo() {
        return matchTeamInfo;
    }

    public void setMatchTeamInfo(MatchTeamInfo matchTeamInfo) {
        this.matchTeamInfo = matchTeamInfo;
    }
}
