package com.darfat.registats.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A MatchLineup.
 */
@Entity
@Table(name = "match_lineup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "matchlineup")
public class MatchLineup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "role")
    private String role;

    @Column(name = "first_half_play")
    private Boolean firstHalfPlay;

    @Column(name = "second_half_play")
    private Boolean secondHalfPlay;

    @Column(name = "status")
    private String status;

    @Column(name = "minute_in")
    private Integer minuteIn;

    @Column(name = "minote_out")
    private Integer minoteOut;

    @OneToMany(mappedBy = "matchLineup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlayerMatchStatistic> statistics = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("matchLineups")
    private Position position;

    @ManyToOne
    @JsonIgnoreProperties("matchLineups")
    private Player player;

    @ManyToOne
    @JsonIgnoreProperties("matchLineups")
    private MatchHomeInfo matchHomeInfo;

    @ManyToOne
    @JsonIgnoreProperties("matchLineups")
    private MatchAwayInfo matchAwayInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public MatchLineup number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getRole() {
        return role;
    }

    public MatchLineup role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean isFirstHalfPlay() {
        return firstHalfPlay;
    }

    public MatchLineup firstHalfPlay(Boolean firstHalfPlay) {
        this.firstHalfPlay = firstHalfPlay;
        return this;
    }

    public void setFirstHalfPlay(Boolean firstHalfPlay) {
        this.firstHalfPlay = firstHalfPlay;
    }

    public Boolean isSecondHalfPlay() {
        return secondHalfPlay;
    }

    public MatchLineup secondHalfPlay(Boolean secondHalfPlay) {
        this.secondHalfPlay = secondHalfPlay;
        return this;
    }

    public void setSecondHalfPlay(Boolean secondHalfPlay) {
        this.secondHalfPlay = secondHalfPlay;
    }

    public String getStatus() {
        return status;
    }

    public MatchLineup status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMinuteIn() {
        return minuteIn;
    }

    public MatchLineup minuteIn(Integer minuteIn) {
        this.minuteIn = minuteIn;
        return this;
    }

    public void setMinuteIn(Integer minuteIn) {
        this.minuteIn = minuteIn;
    }

    public Integer getMinoteOut() {
        return minoteOut;
    }

    public MatchLineup minoteOut(Integer minoteOut) {
        this.minoteOut = minoteOut;
        return this;
    }

    public void setMinoteOut(Integer minoteOut) {
        this.minoteOut = minoteOut;
    }

    public Set<PlayerMatchStatistic> getStatistics() {
        return statistics;
    }

    public MatchLineup statistics(Set<PlayerMatchStatistic> playerMatchStatistics) {
        this.statistics = playerMatchStatistics;
        return this;
    }

    public MatchLineup addStatistic(PlayerMatchStatistic playerMatchStatistic) {
        this.statistics.add(playerMatchStatistic);
        playerMatchStatistic.setMatchLineup(this);
        return this;
    }

    public MatchLineup removeStatistic(PlayerMatchStatistic playerMatchStatistic) {
        this.statistics.remove(playerMatchStatistic);
        playerMatchStatistic.setMatchLineup(null);
        return this;
    }

    public void setStatistics(Set<PlayerMatchStatistic> playerMatchStatistics) {
        this.statistics = playerMatchStatistics;
    }

    public Position getPosition() {
        return position;
    }

    public MatchLineup position(Position position) {
        this.position = position;
        return this;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Player getPlayer() {
        return player;
    }

    public MatchLineup player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public MatchHomeInfo getMatchHomeInfo() {
        return matchHomeInfo;
    }

    public MatchLineup matchHomeInfo(MatchHomeInfo matchHomeInfo) {
        this.matchHomeInfo = matchHomeInfo;
        return this;
    }

    public void setMatchHomeInfo(MatchHomeInfo matchHomeInfo) {
        this.matchHomeInfo = matchHomeInfo;
    }

    public MatchAwayInfo getMatchAwayInfo() {
        return matchAwayInfo;
    }

    public MatchLineup matchAwayInfo(MatchAwayInfo matchAwayInfo) {
        this.matchAwayInfo = matchAwayInfo;
        return this;
    }

    public void setMatchAwayInfo(MatchAwayInfo matchAwayInfo) {
        this.matchAwayInfo = matchAwayInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchLineup)) {
            return false;
        }
        return id != null && id.equals(((MatchLineup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MatchLineup{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", role='" + getRole() + "'" +
            ", firstHalfPlay='" + isFirstHalfPlay() + "'" +
            ", secondHalfPlay='" + isSecondHalfPlay() + "'" +
            ", status='" + getStatus() + "'" +
            ", minuteIn=" + getMinuteIn() +
            ", minoteOut=" + getMinoteOut() +
            "}";
    }
}
