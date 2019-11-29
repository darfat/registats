package com.darfat.registats.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Team entity.
 */
@ApiModel(description = "Team entity.")
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "logo")
    private String logo;

    @Column(name = "manager_name")
    private String managerName;

    @Column(name = "head_coach_name")
    private String headCoachName;

    @OneToMany(mappedBy = "team")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TeamMember> members = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Team name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Team description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public Team logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getManagerName() {
        return managerName;
    }

    public Team managerName(String managerName) {
        this.managerName = managerName;
        return this;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getHeadCoachName() {
        return headCoachName;
    }

    public Team headCoachName(String headCoachName) {
        this.headCoachName = headCoachName;
        return this;
    }

    public void setHeadCoachName(String headCoachName) {
        this.headCoachName = headCoachName;
    }

    public Set<TeamMember> getMembers() {
        return members;
    }

    public Team members(Set<TeamMember> teamMembers) {
        this.members = teamMembers;
        return this;
    }

    public Team addMember(TeamMember teamMember) {
        this.members.add(teamMember);
        teamMember.setTeam(this);
        return this;
    }

    public Team removeMember(TeamMember teamMember) {
        this.members.remove(teamMember);
        teamMember.setTeam(null);
        return this;
    }

    public void setMembers(Set<TeamMember> teamMembers) {
        this.members = teamMembers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }
        return id != null && id.equals(((Team) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", logo='" + getLogo() + "'" +
            ", managerName='" + getManagerName() + "'" +
            ", headCoachName='" + getHeadCoachName() + "'" +
            "}";
    }
}
