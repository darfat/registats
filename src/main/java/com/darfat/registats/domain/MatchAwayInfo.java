package com.darfat.registats.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.darfat.registats.domain.enumeration.Formation;

/**
 * A MatchAwayInfo.
 */
@Entity
@Table(name = "match_away_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "matchawayinfo")
public class MatchAwayInfo implements Serializable {

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

    @OneToMany(mappedBy = "matchAwayInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MatchLineup> lineups = new HashSet<>();

    @OneToMany(mappedBy = "matchAwayInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MatchStatistic> statistics = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("matchAwayInfos")
    private Match match;

    @ManyToOne
    @JsonIgnoreProperties("matchAwayInfos")
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

    public MatchAwayInfo description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Formation getFormation() {
        return formation;
    }

    public MatchAwayInfo formation(Formation formation) {
        this.formation = formation;
        return this;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public String getAnalysis() {
        return analysis;
    }

    public MatchAwayInfo analysis(String analysis) {
        this.analysis = analysis;
        return this;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getPreMatchTalk() {
        return preMatchTalk;
    }

    public MatchAwayInfo preMatchTalk(String preMatchTalk) {
        this.preMatchTalk = preMatchTalk;
        return this;
    }

    public void setPreMatchTalk(String preMatchTalk) {
        this.preMatchTalk = preMatchTalk;
    }

    public String getPostMatchTalk() {
        return postMatchTalk;
    }

    public MatchAwayInfo postMatchTalk(String postMatchTalk) {
        this.postMatchTalk = postMatchTalk;
        return this;
    }

    public void setPostMatchTalk(String postMatchTalk) {
        this.postMatchTalk = postMatchTalk;
    }

    public Set<MatchLineup> getLineups() {
        return lineups;
    }

    public MatchAwayInfo lineups(Set<MatchLineup> matchLineups) {
        this.lineups = matchLineups;
        return this;
    }

    public MatchAwayInfo addLineup(MatchLineup matchLineup) {
        this.lineups.add(matchLineup);
        matchLineup.setMatchAwayInfo(this);
        return this;
    }

    public MatchAwayInfo removeLineup(MatchLineup matchLineup) {
        this.lineups.remove(matchLineup);
        matchLineup.setMatchAwayInfo(null);
        return this;
    }

    public void setLineups(Set<MatchLineup> matchLineups) {
        this.lineups = matchLineups;
    }

    public Set<MatchStatistic> getStatistics() {
        return statistics;
    }

    public MatchAwayInfo statistics(Set<MatchStatistic> matchStatistics) {
        this.statistics = matchStatistics;
        return this;
    }

    public MatchAwayInfo addStatistic(MatchStatistic matchStatistic) {
        this.statistics.add(matchStatistic);
        matchStatistic.setMatchAwayInfo(this);
        return this;
    }

    public MatchAwayInfo removeStatistic(MatchStatistic matchStatistic) {
        this.statistics.remove(matchStatistic);
        matchStatistic.setMatchAwayInfo(null);
        return this;
    }

    public void setStatistics(Set<MatchStatistic> matchStatistics) {
        this.statistics = matchStatistics;
    }

    public Match getMatch() {
        return match;
    }

    public MatchAwayInfo match(Match match) {
        this.match = match;
        return this;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Team getTeam() {
        return team;
    }

    public MatchAwayInfo team(Team team) {
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
        if (!(o instanceof MatchAwayInfo)) {
            return false;
        }
        return id != null && id.equals(((MatchAwayInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MatchAwayInfo{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", formation='" + getFormation() + "'" +
            ", analysis='" + getAnalysis() + "'" +
            ", preMatchTalk='" + getPreMatchTalk() + "'" +
            ", postMatchTalk='" + getPostMatchTalk() + "'" +
            "}";
    }
}
