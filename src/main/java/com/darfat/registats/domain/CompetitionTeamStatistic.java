package com.darfat.registats.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A CompetitionTeamStatistic.
 */
@Entity
@Table(name = "competition_team_statistic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "competitionteamstatistic")
public class CompetitionTeamStatistic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "value_double")
    private Double valueDouble;

    @Column(name = "value_long")
    private Long valueLong;

    @Column(name = "value_string")
    private String valueString;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("competitionTeamStatistics")
    private Competition competition;

    @ManyToOne
    @JsonIgnoreProperties("competitionTeamStatistics")
    private CompetitionStatisticItem statistic;

    @ManyToOne
    @JsonIgnoreProperties("competitionTeamStatistics")
    private Team team;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValueDouble() {
        return valueDouble;
    }

    public CompetitionTeamStatistic valueDouble(Double valueDouble) {
        this.valueDouble = valueDouble;
        return this;
    }

    public void setValueDouble(Double valueDouble) {
        this.valueDouble = valueDouble;
    }

    public Long getValueLong() {
        return valueLong;
    }

    public CompetitionTeamStatistic valueLong(Long valueLong) {
        this.valueLong = valueLong;
        return this;
    }

    public void setValueLong(Long valueLong) {
        this.valueLong = valueLong;
    }

    public String getValueString() {
        return valueString;
    }

    public CompetitionTeamStatistic valueString(String valueString) {
        this.valueString = valueString;
        return this;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public String getDescription() {
        return description;
    }

    public CompetitionTeamStatistic description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public CompetitionTeamStatistic name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Competition getCompetition() {
        return competition;
    }

    public CompetitionTeamStatistic competition(Competition competition) {
        this.competition = competition;
        return this;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public CompetitionStatisticItem getStatistic() {
        return statistic;
    }

    public CompetitionTeamStatistic statistic(CompetitionStatisticItem competitionStatisticItem) {
        this.statistic = competitionStatisticItem;
        return this;
    }

    public void setStatistic(CompetitionStatisticItem competitionStatisticItem) {
        this.statistic = competitionStatisticItem;
    }

    public Team getTeam() {
        return team;
    }

    public CompetitionTeamStatistic team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompetitionTeamStatistic)) {
            return false;
        }
        return id != null && id.equals(((CompetitionTeamStatistic) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompetitionTeamStatistic{" +
            "id=" + getId() +
            ", valueDouble=" + getValueDouble() +
            ", valueLong=" + getValueLong() +
            ", valueString='" + getValueString() + "'" +
            ", description='" + getDescription() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
