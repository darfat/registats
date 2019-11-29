package com.darfat.registats.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A CompetitionStanding.
 */
@Entity
@Table(name = "competition_standing")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "competitionstanding")
public class CompetitionStanding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "position")
    private Integer position;

    @Column(name = "played")
    private Integer played;

    @Column(name = "win")
    private Integer win;

    @Column(name = "draw")
    private Integer draw;

    @Column(name = "lose")
    private Integer lose;

    @Column(name = "goal")
    private Long goal;

    @Column(name = "goal_againts")
    private Long goalAgaints;

    @Column(name = "point")
    private Long point;

    @Column(name = "minus_point")
    private Long minusPoint;

    @ManyToOne
    @JsonIgnoreProperties("competitionStandings")
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public CompetitionStanding position(Integer position) {
        this.position = position;
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getPlayed() {
        return played;
    }

    public CompetitionStanding played(Integer played) {
        this.played = played;
        return this;
    }

    public void setPlayed(Integer played) {
        this.played = played;
    }

    public Integer getWin() {
        return win;
    }

    public CompetitionStanding win(Integer win) {
        this.win = win;
        return this;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getDraw() {
        return draw;
    }

    public CompetitionStanding draw(Integer draw) {
        this.draw = draw;
        return this;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getLose() {
        return lose;
    }

    public CompetitionStanding lose(Integer lose) {
        this.lose = lose;
        return this;
    }

    public void setLose(Integer lose) {
        this.lose = lose;
    }

    public Long getGoal() {
        return goal;
    }

    public CompetitionStanding goal(Long goal) {
        this.goal = goal;
        return this;
    }

    public void setGoal(Long goal) {
        this.goal = goal;
    }

    public Long getGoalAgaints() {
        return goalAgaints;
    }

    public CompetitionStanding goalAgaints(Long goalAgaints) {
        this.goalAgaints = goalAgaints;
        return this;
    }

    public void setGoalAgaints(Long goalAgaints) {
        this.goalAgaints = goalAgaints;
    }

    public Long getPoint() {
        return point;
    }

    public CompetitionStanding point(Long point) {
        this.point = point;
        return this;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Long getMinusPoint() {
        return minusPoint;
    }

    public CompetitionStanding minusPoint(Long minusPoint) {
        this.minusPoint = minusPoint;
        return this;
    }

    public void setMinusPoint(Long minusPoint) {
        this.minusPoint = minusPoint;
    }

    public Competition getCompetition() {
        return competition;
    }

    public CompetitionStanding competition(Competition competition) {
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
        if (!(o instanceof CompetitionStanding)) {
            return false;
        }
        return id != null && id.equals(((CompetitionStanding) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompetitionStanding{" +
            "id=" + getId() +
            ", position=" + getPosition() +
            ", played=" + getPlayed() +
            ", win=" + getWin() +
            ", draw=" + getDraw() +
            ", lose=" + getLose() +
            ", goal=" + getGoal() +
            ", goalAgaints=" + getGoalAgaints() +
            ", point=" + getPoint() +
            ", minusPoint=" + getMinusPoint() +
            "}";
    }
}
