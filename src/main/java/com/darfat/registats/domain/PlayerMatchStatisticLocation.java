package com.darfat.registats.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "player_match_statistic_location")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlayerMatchStatisticLocation  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    private Integer minute;

    private String description;

    @ManyToOne
    @JsonIgnoreProperties("locations")
    private PlayerMatchStatistic statistic;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PlayerMatchStatistic getStatistic() {
        return statistic;
    }

    public void setStatistic(PlayerMatchStatistic statistic) {
        this.statistic = statistic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlayerMatchStatisticLocation)) {
            return false;
        }
        return id != null && id.equals(((PlayerMatchStatisticLocation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PlayerMatchStatisticLocation{" +
            "id=" + getId() +
            ", location=" + getLocation() +
            ", minute=" + getMinute() +
            ", desc=" + getDescription() +
            "}";
    }

}
