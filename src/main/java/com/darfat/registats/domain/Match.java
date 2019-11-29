package com.darfat.registats.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Match.
 */
@Entity
@Table(name = "jhi_match")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "match")
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "time")
    private Instant time;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "refree")
    private String refree;

    @Column(name = "live_stream_url")
    private String liveStreamUrl;

    @Column(name = "wheater")
    private String wheater;

    @Column(name = "wind")
    private String wind;

    @Column(name = "analysis")
    private String analysis;

    @Column(name = "pre_match_talk")
    private String preMatchTalk;

    @Column(name = "post_match_talk")
    private String postMatchTalk;

    @OneToMany(mappedBy = "match")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MatchCommentary> commentaries = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("matches")
    private Venue venue;

    @ManyToOne
    @JsonIgnoreProperties("matches")
    private Team homeTeam;

    @ManyToOne
    @JsonIgnoreProperties("matches")
    private Team awayTeam;

    @ManyToOne
    @JsonIgnoreProperties("matches")
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public Match date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Instant getTime() {
        return time;
    }

    public Match time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public Match name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Match description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefree() {
        return refree;
    }

    public Match refree(String refree) {
        this.refree = refree;
        return this;
    }

    public void setRefree(String refree) {
        this.refree = refree;
    }

    public String getLiveStreamUrl() {
        return liveStreamUrl;
    }

    public Match liveStreamUrl(String liveStreamUrl) {
        this.liveStreamUrl = liveStreamUrl;
        return this;
    }

    public void setLiveStreamUrl(String liveStreamUrl) {
        this.liveStreamUrl = liveStreamUrl;
    }

    public String getWheater() {
        return wheater;
    }

    public Match wheater(String wheater) {
        this.wheater = wheater;
        return this;
    }

    public void setWheater(String wheater) {
        this.wheater = wheater;
    }

    public String getWind() {
        return wind;
    }

    public Match wind(String wind) {
        this.wind = wind;
        return this;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getAnalysis() {
        return analysis;
    }

    public Match analysis(String analysis) {
        this.analysis = analysis;
        return this;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getPreMatchTalk() {
        return preMatchTalk;
    }

    public Match preMatchTalk(String preMatchTalk) {
        this.preMatchTalk = preMatchTalk;
        return this;
    }

    public void setPreMatchTalk(String preMatchTalk) {
        this.preMatchTalk = preMatchTalk;
    }

    public String getPostMatchTalk() {
        return postMatchTalk;
    }

    public Match postMatchTalk(String postMatchTalk) {
        this.postMatchTalk = postMatchTalk;
        return this;
    }

    public void setPostMatchTalk(String postMatchTalk) {
        this.postMatchTalk = postMatchTalk;
    }

    public Set<MatchCommentary> getCommentaries() {
        return commentaries;
    }

    public Match commentaries(Set<MatchCommentary> matchCommentaries) {
        this.commentaries = matchCommentaries;
        return this;
    }

    public Match addCommentary(MatchCommentary matchCommentary) {
        this.commentaries.add(matchCommentary);
        matchCommentary.setMatch(this);
        return this;
    }

    public Match removeCommentary(MatchCommentary matchCommentary) {
        this.commentaries.remove(matchCommentary);
        matchCommentary.setMatch(null);
        return this;
    }

    public void setCommentaries(Set<MatchCommentary> matchCommentaries) {
        this.commentaries = matchCommentaries;
    }

    public Venue getVenue() {
        return venue;
    }

    public Match venue(Venue venue) {
        this.venue = venue;
        return this;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Match homeTeam(Team team) {
        this.homeTeam = team;
        return this;
    }

    public void setHomeTeam(Team team) {
        this.homeTeam = team;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Match awayTeam(Team team) {
        this.awayTeam = team;
        return this;
    }

    public void setAwayTeam(Team team) {
        this.awayTeam = team;
    }

    public Competition getCompetition() {
        return competition;
    }

    public Match competition(Competition competition) {
        this.competition = competition;
        return this;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Match)) {
            return false;
        }
        return id != null && id.equals(((Match) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Match{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", time='" + getTime() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", refree='" + getRefree() + "'" +
            ", liveStreamUrl='" + getLiveStreamUrl() + "'" +
            ", wheater='" + getWheater() + "'" +
            ", wind='" + getWind() + "'" +
            ", analysis='" + getAnalysis() + "'" +
            ", preMatchTalk='" + getPreMatchTalk() + "'" +
            ", postMatchTalk='" + getPostMatchTalk() + "'" +
            "}";
    }
}
