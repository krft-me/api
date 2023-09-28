package me.krft.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Showcase.
 */
@Entity
@Table(name = "showcase")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Showcase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "image_id", nullable = false)
    private UUID imageId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ratings", "showcases", "tags", "offer", "applicationUser" }, allowSetters = true)
    private ApplicationUserOffer applicationUserOffer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Showcase id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getImageId() {
        return this.imageId;
    }

    public Showcase imageId(UUID imageId) {
        this.setImageId(imageId);
        return this;
    }

    public void setImageId(UUID imageId) {
        this.imageId = imageId;
    }

    public ApplicationUserOffer getApplicationUserOffer() {
        return this.applicationUserOffer;
    }

    public void setApplicationUserOffer(ApplicationUserOffer applicationUserOffer) {
        this.applicationUserOffer = applicationUserOffer;
    }

    public Showcase applicationUserOffer(ApplicationUserOffer applicationUserOffer) {
        this.setApplicationUserOffer(applicationUserOffer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Showcase)) {
            return false;
        }
        return id != null && id.equals(((Showcase) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Showcase{" +
            "id=" + getId() +
            ", imageId='" + getImageId() + "'" +
            "}";
    }
}
