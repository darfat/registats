package com.darfat.registats.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.darfat.registats.domain.enumeration.CompetitionFormat;

/**
 * A Competition.
 */
@Entity
@Table(name = "competition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "competition")
public class Competition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "season", nullable = false)
    private String season;

    @Column(name = "slug")
    private String slug;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "competition_format")
    private CompetitionFormat competitionFormat;

    @ManyToOne
    @JsonIgnoreProperties("competitions")
    private CompetitionName competitionName;

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompetitionTeam> teams = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompetitionStanding> standings = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompetitionPlayerStatistic> playerStatistics = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompetitionTeamStatistic> teamStatistics = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Match> matches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeason() {
        return season;
    }

    public Competition season(String season) {
        this.season = season;
        return this;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSlug() {
        return slug;
    }

    public Competition slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Competition startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Competition endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public CompetitionFormat getCompetitionFormat() {
        return competitionFormat;
    }

    public Competition competitionFormat(CompetitionFormat competitionFormat) {
        this.competitionFormat = competitionFormat;
        return this;
    }

    public void setCompetitionFormat(CompetitionFormat competitionFormat) {
        this.competitionFormat = competitionFormat;
    }

    public CompetitionName getCompetitionName() {
        return competitionName;
    }

    public Competition competitionName(CompetitionName competitionName) {
        this.competitionName = competitionName;
        return this;
    }

    public void setCompetitionName(CompetitionName competitionName) {
        this.competitionName = competitionName;
    }

    public Set<CompetitionTeam> getTeams() {
        return teams;
    }

    public Competition teams(Set<CompetitionTeam> competitionTeams) {
        this.teams = competitionTeams;
        return this;
    }

    public Competition addTeam(CompetitionTeam competitionTeam) {
        this.teams.add(competitionTeam);
        competitionTeam.setCompetition(this);
        return this;
    }

    public Competition removeTeam(CompetitionTeam competitionTeam) {
        this.teams.remove(competitionTeam);
        competitionTeam.setCompetition(null);
        return this;
    }

    public void setTeams(Set<CompetitionTeam> competitionTeams) {
        this.teams = competitionTeams;
    }

    public Set<CompetitionStanding> getStandings() {
        return standings;
    }

    public Competition standings(Set<CompetitionStanding> competitionStandings) {
        this.standings = competitionStandings;
        return this;
    }

    public Competition addStanding(CompetitionStanding competitionStanding) {
        this.standings.add(competitionStanding);
        competitionStanding.setCompetition(this);
        return this;
    }

    public Competition removeStanding(CompetitionStanding competitionStanding) {
        this.standings.remove(competitionStanding);
        competitionStanding.setCompetition(null);
        return this;
    }

    public void setStandings(Set<CompetitionStanding> competitionStandings) {
        this.standings = competitionStandings;
    }

    public Set<CompetitionPlayerStatistic> getPlayerStatistics() {
        return playerStatistics;
    }

    public Competition playerStatistics(Set<CompetitionPlayerStatistic> competitionPlayerStatistics) {
        this.playerStatistics = competitionPlayerStatistics;
        return this;
    }

    public Competition addPlayerStatistic(CompetitionPlayerStatistic competitionPlayerStatistic) {
        this.playerStatistics.add(competitionPlayerStatistic);
        competitionPlayerStatistic.setCompetition(this);
        return this;
    }

    public Competition removePlayerStatistic(CompetitionPlayerStatistic competitionPlayerStatistic) {
        this.playerStatistics.remove(competitionPlayerStatistic);
        competitionPlayerStatistic.setCompetition(null);
        return this;
    }

    public void setPlayerStatistics(Set<CompetitionPlayerStatistic> competitionPlayerStatistics) {
        this.playerStatistics = competitionPlayerStatistics;
    }

    public Set<CompetitionTeamStatistic> getTeamStatistics() {
        return teamStatistics;
    }

    public Competition teamStatistics(Set<CompetitionTeamStatistic> competitionTeamStatistics) {
        this.teamStatistics = competitionTeamStatistics;
        return this;
    }

    public Competition addTeamStatistic(CompetitionTeamStatistic competitionTeamStatistic) {
        this.teamStatistics.add(competitionTeamStatistic);
        competitionTeamStatistic.setCompetition(this);
        return this;
    }

    public Competition removeTeamStatistic(CompetitionTeamStatistic competitionTeamStatistic) {
        this.teamStatistics.remove(competitionTeamStatistic);
        competitionTeamStatistic.setCompetition(null);
        return this;
    }

    public void setTeamStatistics(Set<CompetitionTeamStatistic> competitionTeamStatistics) {
        this.teamStatistics = competitionTeamStatistics;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public Competition matches(Set<Match> matches) {
        this.matches = matches;
        return this;
    }

    public Competition addMatch(Match match) {
        this.matches.add(match);
        match.setCompetition(this);
        return this;
    }

    public Competition removeMatch(Match match) {
        this.matches.remove(match);
        match.setCompetition(null);
        return this;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Competition)) {
            return false;
        }
        return id != null && id.equals(((Competition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Competition{" +
            "id=" + getId() +
            ", season='" + getSeason() + "'" +
            ", slug='" + getSlug() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", competitionFormat='" + getCompetitionFormat() + "'" +
            "}";
    }
}
