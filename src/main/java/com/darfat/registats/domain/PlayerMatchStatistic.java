package com.darfat.registats.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A PlayerMatchStatistic.
 */
@Entity
@Table(name = "player_match_statistic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "playermatchstatistic")
public class PlayerMatchStatistic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "description")
    private Long description;

    @Column(name = "value")
    private Long value;

    @Column(name = "value_double")
    private Double valueDouble;

    @Column(name = "value_long")
    private Long valueLong;

    @Column(name = "value_string")
    private String valueString;

    @ManyToOne
    @JsonIgnoreProperties("playerMatchStatistics")
    private MatchLineup matchLineup;

    @ManyToOne
    @JsonIgnoreProperties("playerMatchStatistics")
    private PlayerStatisticItem statistic;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDescription() {
        return description;
    }

    public PlayerMatchStatistic description(Long description) {
        this.description = description;
        return this;
    }

    public void setDescription(Long description) {
        this.description = description;
    }

    public Long getValue() {
        return value;
    }

    public PlayerMatchStatistic value(Long value) {
        this.value = value;
        return this;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Double getValueDouble() {
        return valueDouble;
    }

    public PlayerMatchStatistic valueDouble(Double valueDouble) {
        this.valueDouble = valueDouble;
        return this;
    }

    public void setValueDouble(Double valueDouble) {
        this.valueDouble = valueDouble;
    }

    public Long getValueLong() {
        return valueLong;
    }

    public PlayerMatchStatistic valueLong(Long valueLong) {
        this.valueLong = valueLong;
        return this;
    }

    public void setValueLong(Long valueLong) {
        this.valueLong = valueLong;
    }

    public String getValueString() {
        return valueString;
    }

    public PlayerMatchStatistic valueString(String valueString) {
        this.valueString = valueString;
        return this;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public MatchLineup getMatchLineup() {
        return matchLineup;
    }

    public PlayerMatchStatistic matchLineup(MatchLineup matchLineup) {
        this.matchLineup = matchLineup;
        return this;
    }

    public void setMatchLineup(MatchLineup matchLineup) {
        this.matchLineup = matchLineup;
    }

    public PlayerStatisticItem getStatistic() {
        return statistic;
    }

    public PlayerMatchStatistic statistic(PlayerStatisticItem playerStatisticItem) {
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
        if (!(o instanceof PlayerMatchStatistic)) {
            return false;
        }
        return id != null && id.equals(((PlayerMatchStatistic) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PlayerMatchStatistic{" +
            "id=" + getId() +
            ", description=" + getDescription() +
            ", value=" + getValue() +
            ", valueDouble=" + getValueDouble() +
            ", valueLong=" + getValueLong() +
            ", valueString='" + getValueString() + "'" +
            "}";
    }
}
