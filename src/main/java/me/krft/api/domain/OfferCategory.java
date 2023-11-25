package me.krft.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * OfferCategory entity
 * Represents a preset offer type
 */
@Schema(description = "OfferCategory entity\nRepresents a preset offer type")
@Entity
@Table(name = "offer_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OfferCategory implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userOffers", "machine", "category" }, allowSetters = true)
    private Set<Offer> offers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OfferCategory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public OfferCategory label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Offer> getOffers() {
        return this.offers;
    }

    public void setOffers(Set<Offer> offers) {
        if (this.offers != null) {
            this.offers.forEach(i -> i.setCategory(null));
        }
        if (offers != null) {
            offers.forEach(i -> i.setCategory(this));
        }
        this.offers = offers;
    }

    public OfferCategory offers(Set<Offer> offers) {
        this.setOffers(offers);
        return this;
    }

    public OfferCategory addOffers(Offer offer) {
        this.offers.add(offer);
        offer.setCategory(this);
        return this;
    }

    public OfferCategory removeOffers(Offer offer) {
        this.offers.remove(offer);
        offer.setCategory(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfferCategory)) {
            return false;
        }
        return getId() != null && getId().equals(((OfferCategory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfferCategory{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
