package com.darfat.registats.domain;

import com.darfat.registats.domain.enumeration.Formation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A MatchTeamInfo.
 */
@Entity
@Table(name = "match_team_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "matchteaminfo")
public class MatchTeamInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "formation")
    private Formation formation;

    @Column(name = "analysis")
    private String analysis;

    @Column(name = "pre_match_talk")
    private String preMatchTalk;

    @Column(name = "post_match_talk")
    private String postMatchTalk;

    @OneToMany(mappedBy = "matchTeamInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MatchLineup> lineups = new HashSet<>();

    @OneToMany(mappedBy = "matchTeamInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MatchStatistic> statistics = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("matchTeamInfos")
    private Team team;

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

    public MatchTeamInfo description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Formation getFormation() {
        return formation;
    }

    public MatchTeamInfo formation(Formation formation) {
        this.formation = formation;
        return this;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public String getAnalysis() {
        return analysis;
    }

    public MatchTeamInfo analysis(String analysis) {
        this.analysis = analysis;
        return this;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getPreMatchTalk() {
        return preMatchTalk;
    }

    public MatchTeamInfo preMatchTalk(String preMatchTalk) {
        this.preMatchTalk = preMatchTalk;
        return this;
    }

    public void setPreMatchTalk(String preMatchTalk) {
        this.preMatchTalk = preMatchTalk;
    }

    public String getPostMatchTalk() {
        return postMatchTalk;
    }

    public MatchTeamInfo postMatchTalk(String postMatchTalk) {
        this.postMatchTalk = postMatchTalk;
        return this;
    }

    public void setPostMatchTalk(String postMatchTalk) {
        this.postMatchTalk = postMatchTalk;
    }

    public Set<MatchLineup> getLineups() {
        return lineups;
    }

    public MatchTeamInfo lineups(Set<MatchLineup> matchLineups) {
        this.lineups = matchLineups;
        return this;
    }

    public MatchTeamInfo addLineup(MatchLineup matchLineup) {
        this.lineups.add(matchLineup);
        matchLineup.setMatchTeamInfo(this);
        return this;
    }

    public MatchTeamInfo removeLineup(MatchLineup matchLineup) {
        this.lineups.remove(matchLineup);
        matchLineup.setMatchTeamInfo(null);
        return this;
    }

    public void setLineups(Set<MatchLineup> matchLineups) {
        this.lineups = matchLineups;
    }

    public Set<MatchStatistic> getStatistics() {
        return statistics;
    }

    public MatchTeamInfo statistics(Set<MatchStatistic> matchStatistics) {
        this.statistics = matchStatistics;
        return this;
    }

    public MatchTeamInfo addStatistic(MatchStatistic matchStatistic) {
        this.statistics.add(matchStatistic);
        matchStatistic.setMatchTeamInfo(this);
        return this;
    }

    public MatchTeamInfo removeStatistic(MatchStatistic matchStatistic) {
        this.statistics.remove(matchStatistic);
        matchStatistic.setMatchTeamInfo(null);
        return this;
    }

    public void setStatistics(Set<MatchStatistic> matchStatistics) {
        this.statistics = matchStatistics;
    }

    public Team getTeam() {
        return team;
    }

    public MatchTeamInfo team(Team team) {
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
        if (!(o instanceof MatchTeamInfo)) {
            return false;
        }
        return id != null && id.equals(((MatchTeamInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MatchTeamInfo{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", formation='" + getFormation() + "'" +
            ", analysis='" + getAnalysis() + "'" +
            ", preMatchTalk='" + getPreMatchTalk() + "'" +
            ", postMatchTalk='" + getPostMatchTalk() + "'" +
            "}";
    }
}
