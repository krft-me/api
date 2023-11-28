package me.krft.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Relationship entity between users and badges
 */
@Schema(description = "Relationship entity between users and badges")
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

    /**
     * Date the user obtained the badge
     */
    @Schema(description = "Date the user obtained the badge", required = true)
    @NotNull
    @Column(name = "obtained_date", nullable = false)
    private Instant obtainedDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "internalUser", "offers", "badges", "orders", "city" }, allowSetters = true)
    private ApplicationUser user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "users" }, allowSetters = true)
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

    public Instant getObtainedDate() {
        return this.obtainedDate;
    }

    public ApplicationUserBadge obtainedDate(Instant obtainedDate) {
        this.setObtainedDate(obtainedDate);
        return this;
    }

    public void setObtainedDate(Instant obtainedDate) {
        this.obtainedDate = obtainedDate;
    }

    public ApplicationUser getUser() {
        return this.user;
    }

    public void setUser(ApplicationUser applicationUser) {
        this.user = applicationUser;
    }

    public ApplicationUserBadge user(ApplicationUser applicationUser) {
        this.setUser(applicationUser);
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
            ", obtainedDate='" + getObtainedDate() + "'" +
            "}";
    }
}
