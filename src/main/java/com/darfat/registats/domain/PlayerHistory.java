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
 * A PlayerHistory.
 */
@Entity
@Table(name = "player_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "playerhistory")
public class PlayerHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "appearance")
    private Long appearance;

    @Column(name = "minute_played")
    private Long minutePlayed;

    @Column(name = "goals")
    private Long goals;

    @Column(name = "assists")
    private Long assists;

    @Column(name = "average_rating")
    private Long averageRating;

    @ManyToOne
    @JsonIgnoreProperties("playerHistories")
    private Player player;

    @OneToMany(mappedBy = "playerHistory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlayerHistoryStatistic> statistics = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public PlayerHistory startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public PlayerHistory endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Long getAppearance() {
        return appearance;
    }

    public PlayerHistory appearance(Long appearance) {
        this.appearance = appearance;
        return this;
    }

    public void setAppearance(Long appearance) {
        this.appearance = appearance;
    }

    public Long getMinutePlayed() {
        return minutePlayed;
    }

    public PlayerHistory minutePlayed(Long minutePlayed) {
        this.minutePlayed = minutePlayed;
        return this;
    }

    public void setMinutePlayed(Long minutePlayed) {
        this.minutePlayed = minutePlayed;
    }

    public Long getGoals() {
        return goals;
    }

    public PlayerHistory goals(Long goals) {
        this.goals = goals;
        return this;
    }

    public void setGoals(Long goals) {
        this.goals = goals;
    }

    public Long getAssists() {
        return assists;
    }

    public PlayerHistory assists(Long assists) {
        this.assists = assists;
        return this;
    }

    public void setAssists(Long assists) {
        this.assists = assists;
    }

    public Long getAverageRating() {
        return averageRating;
    }

    public PlayerHistory averageRating(Long averageRating) {
        this.averageRating = averageRating;
        return this;
    }

    public void setAverageRating(Long averageRating) {
        this.averageRating = averageRating;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerHistory player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Set<PlayerHistoryStatistic> getStatistics() {
        return statistics;
    }

    public PlayerHistory statistics(Set<PlayerHistoryStatistic> playerHistoryStatistics) {
        this.statistics = playerHistoryStatistics;
        return this;
    }

    public PlayerHistory addStatistic(PlayerHistoryStatistic playerHistoryStatistic) {
        this.statistics.add(playerHistoryStatistic);
        playerHistoryStatistic.setPlayerHistory(this);
        return this;
    }

    public PlayerHistory removeStatistic(PlayerHistoryStatistic playerHistoryStatistic) {
        this.statistics.remove(playerHistoryStatistic);
        playerHistoryStatistic.setPlayerHistory(null);
        return this;
    }

    public void setStatistics(Set<PlayerHistoryStatistic> playerHistoryStatistics) {
        this.statistics = playerHistoryStatistics;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlayerHistory)) {
            return false;
        }
        return id != null && id.equals(((PlayerHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PlayerHistory{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", appearance=" + getAppearance() +
            ", minutePlayed=" + getMinutePlayed() +
            ", goals=" + getGoals() +
            ", assists=" + getAssists() +
            ", averageRating=" + getAverageRating() +
            "}";
    }
}
