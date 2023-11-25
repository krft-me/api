package me.krft.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Badge entity\nRepresents a certification (example: 100 completed orders)
 */
@Schema(description = "Badge entity\nRepresents a certification (example: 100 completed orders)")
@Entity
@Table(name = "badge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Badge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "label", nullable = false, unique = true)
    private String label;

    /**
     * The badge's icon, should be a blob later
     */
    @Schema(description = "The badge's icon, should be a blob later", required = true)
    @NotNull
    @Column(name = "picture", nullable = false, unique = true)
    private String picture;

    @OneToMany(mappedBy = "badge")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "badge" }, allowSetters = true)
    private Set<ApplicationUserBadge> applicationUserBadges = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Badge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public Badge label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPicture() {
        return this.picture;
    }

    public Badge picture(String picture) {
        this.setPicture(picture);
        return this;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Set<ApplicationUserBadge> getApplicationUserBadges() {
        return this.applicationUserBadges;
    }

    public void setApplicationUserBadges(Set<ApplicationUserBadge> applicationUserBadges) {
        if (this.applicationUserBadges != null) {
            this.applicationUserBadges.forEach(i -> i.setBadge(null));
        }
        if (applicationUserBadges != null) {
            applicationUserBadges.forEach(i -> i.setBadge(this));
        }
        this.applicationUserBadges = applicationUserBadges;
    }

    public Badge applicationUserBadges(Set<ApplicationUserBadge> applicationUserBadges) {
        this.setApplicationUserBadges(applicationUserBadges);
        return this;
    }

    public Badge addApplicationUserBadge(ApplicationUserBadge applicationUserBadge) {
        this.applicationUserBadges.add(applicationUserBadge);
        applicationUserBadge.setBadge(this);
        return this;
    }

    public Badge removeApplicationUserBadge(ApplicationUserBadge applicationUserBadge) {
        this.applicationUserBadges.remove(applicationUserBadge);
        applicationUserBadge.setBadge(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Badge)) {
            return false;
        }
        return id != null && id.equals(((Badge) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Badge{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", picture='" + getPicture() + "'" +
            "}";
    }
}
