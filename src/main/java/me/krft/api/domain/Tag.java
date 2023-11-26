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
 * Tag entity\nRepresents a preset keyword for an offer
 */
@Schema(description = "Tag entity\nRepresents a preset keyword for an offer")
@Entity
@Table(name = "tag")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tag implements Serializable {

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

    @ManyToMany(mappedBy = "tags")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "reviews", "showcases", "orders", "tags", "provider", "offer" }, allowSetters = true)
    private Set<ApplicationUserOffer> offers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tag id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public Tag label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<ApplicationUserOffer> getOffers() {
        return this.offers;
    }

    public void setOffers(Set<ApplicationUserOffer> applicationUserOffers) {
        if (this.offers != null) {
            this.offers.forEach(i -> i.removeTags(this));
        }
        if (applicationUserOffers != null) {
            applicationUserOffers.forEach(i -> i.addTags(this));
        }
        this.offers = applicationUserOffers;
    }

    public Tag offers(Set<ApplicationUserOffer> applicationUserOffers) {
        this.setOffers(applicationUserOffers);
        return this;
    }

    public Tag addOffers(ApplicationUserOffer applicationUserOffer) {
        this.offers.add(applicationUserOffer);
        applicationUserOffer.getTags().add(this);
        return this;
    }

    public Tag removeOffers(ApplicationUserOffer applicationUserOffer) {
        this.offers.remove(applicationUserOffer);
        applicationUserOffer.getTags().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        return id != null && id.equals(((Tag) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
