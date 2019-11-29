package com.darfat.registats.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A GlobalPlayerStatistic.
 */
@Entity
@Table(name = "global_player_statistic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "globalplayerstatistic")
public class GlobalPlayerStatistic implements Serializable {

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
    @JsonIgnoreProperties("globalPlayerStatistics")
    private Player player;

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

    public GlobalPlayerStatistic valueDouble(Double valueDouble) {
        this.valueDouble = valueDouble;
        return this;
    }

    public void setValueDouble(Double valueDouble) {
        this.valueDouble = valueDouble;
    }

    public Long getValueLong() {
        return valueLong;
    }

    public GlobalPlayerStatistic valueLong(Long valueLong) {
        this.valueLong = valueLong;
        return this;
    }

    public void setValueLong(Long valueLong) {
        this.valueLong = valueLong;
    }

    public String getValueString() {
        return valueString;
    }

    public GlobalPlayerStatistic valueString(String valueString) {
        this.valueString = valueString;
        return this;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public String getDescription() {
        return description;
    }

    public GlobalPlayerStatistic description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public GlobalPlayerStatistic name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return player;
    }

    public GlobalPlayerStatistic player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GlobalPlayerStatistic)) {
            return false;
        }
        return id != null && id.equals(((GlobalPlayerStatistic) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GlobalPlayerStatistic{" +
            "id=" + getId() +
            ", valueDouble=" + getValueDouble() +
            ", valueLong=" + getValueLong() +
            ", valueString='" + getValueString() + "'" +
            ", description='" + getDescription() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
