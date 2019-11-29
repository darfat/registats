package com.darfat.registats.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A PlayerHistoryStatistic.
 */
@Entity
@Table(name = "player_history_statistic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "playerhistorystatistic")
public class PlayerHistoryStatistic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "value")
    private Long value;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("playerHistoryStatistics")
    private PlayerHistory playerHistory;

    @ManyToOne
    @JsonIgnoreProperties("playerHistoryStatistics")
    private PlayerStatisticItem statistic;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getValue() {
        return value;
    }

    public PlayerHistoryStatistic value(Long value) {
        this.value = value;
        return this;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public PlayerHistoryStatistic description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PlayerHistory getPlayerHistory() {
        return playerHistory;
    }

    public PlayerHistoryStatistic playerHistory(PlayerHistory playerHistory) {
        this.playerHistory = playerHistory;
        return this;
    }

    public void setPlayerHistory(PlayerHistory playerHistory) {
        this.playerHistory = playerHistory;
    }

    public PlayerStatisticItem getStatistic() {
        return statistic;
    }

    public PlayerHistoryStatistic statistic(PlayerStatisticItem playerStatisticItem) {
        this.statistic = playerStatisticItem;
        return this;
    }

    public void setStatistic(PlayerStatisticItem playerStatisticItem) {
        this.statistic = playerStatisticItem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlayerHistoryStatistic)) {
            return false;
        }
        return id != null && id.equals(((PlayerHistoryStatistic) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PlayerHistoryStatistic{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
