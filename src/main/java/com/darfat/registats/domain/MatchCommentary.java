package com.darfat.registats.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

import com.darfat.registats.domain.enumeration.CommentaryType;

/**
 * A MatchCommentary.
 */
@Entity
@Table(name = "match_commentary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "matchcommentary")
public class MatchCommentary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "commentary_type")
    private CommentaryType commentaryType;

    @Column(name = "minute")
    private Integer minute;

    @Column(name = "log_date")
    private Instant logDate;

    @ManyToOne
    @JsonIgnoreProperties("matchCommentaries")
    private PlayerStatisticItem playerStatistic;

    @ManyToOne
    @JsonIgnoreProperties("matchCommentaries")
    private MatchStatisticItem matchStatistic;

    @ManyToOne
    @JsonIgnoreProperties("matchCommentaries")
    private Team team;

    @ManyToOne
    @JsonIgnoreProperties("matchCommentaries")
    private Player player;

    @ManyToOne
    //@JsonIgnoreProperties("matchCommentaries")
    @JsonIgnore
    private Match match;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public MatchCommentary title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public MatchCommentary description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommentaryType getCommentaryType() {
        return commentaryType;
    }

    public MatchCommentary commentaryType(CommentaryType commentaryType) {
        this.commentaryType = commentaryType;
        return this;
    }

    public void setCommentaryType(CommentaryType commentaryType) {
        this.commentaryType = commentaryType;
    }

    public Integer getMinute() {
        return minute;
    }

    public MatchCommentary minute(Integer minute) {
        this.minute = minute;
        return this;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Instant getLogDate() {
        return logDate;
    }

    public MatchCommentary logDate(Instant logDate) {
        this.logDate = logDate;
        return this;
    }

    public void setLogDate(Instant logDate) {
        this.logDate = logDate;
    }

    public PlayerStatisticItem getPlayerStatistic() {
        return playerStatistic;
    }

    public MatchCommentary playerStatistic(PlayerStatisticItem playerStatisticItem) {
        this.playerStatistic = playerStatisticItem;
        return this;
    }

    public void setPlayerStatistic(PlayerStatisticItem playerStatisticItem) {
        this.playerStatistic = playerStatisticItem;
    }

    public MatchStatisticItem getMatchStatistic() {
        return matchStatistic;
    }

    public MatchCommentary matchStatistic(MatchStatisticItem matchStatisticItem) {
        this.matchStatistic = matchStatisticItem;
        return this;
    }

    public void setMatchStatistic(MatchStatisticItem matchStatisticItem) {
        this.matchStatistic = matchStatisticItem;
    }

    public Team getTeam() {
        return team;
    }

    public MatchCommentary team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Player getPlayer() {
        return player;
    }

    public MatchCommentary player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Match getMatch() {
        return match;
    }

    public MatchCommentary match(Match match) {
        this.match = match;
        return this;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchCommentary)) {
            return false;
        }
        return id != null && id.equals(((MatchCommentary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MatchCommentary{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", commentaryType='" + getCommentaryType() + "'" +
            ", minute=" + getMinute() +
            ", logDate='" + getLogDate() + "'" +
            "}";
    }
}
