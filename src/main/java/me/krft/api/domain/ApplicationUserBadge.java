package me.krft.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ApplicationUserBadge.
 */
@Entity
@Table(name = "application_user_badge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationUserBadge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "obtention_date", nullable = false)
    private Instant obtentionDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "city", "favoriteApplicationUsers", "favoriteOffers", "followers" }, allowSetters = true)
    private ApplicationUser applicationUser;

    @ManyToOne
    private Badge badge;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApplicationUserBadge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getObtentionDate() {
        return this.obtentionDate;
    }

    public ApplicationUserBadge obtentionDate(Instant obtentionDate) {
        this.setObtentionDate(obtentionDate);
        return this;
    }

    public void setObtentionDate(Instant obtentionDate) {
        this.obtentionDate = obtentionDate;
    }

    public ApplicationUser getApplicationUser() {
        return this.applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public ApplicationUserBadge applicationUser(ApplicationUser applicationUser) {
        this.setApplicationUser(applicationUser);
        return this;
    }

    public Badge getBadge() {
        return this.badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public ApplicationUserBadge badge(Badge badge) {
        this.setBadge(badge);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUserBadge)) {
            return false;
        }
        return id != null && id.equals(((ApplicationUserBadge) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUserBadge{" +
            "id=" + getId() +
            ", obtentionDate='" + getObtentionDate() + "'" +
            "}";
    }
}
