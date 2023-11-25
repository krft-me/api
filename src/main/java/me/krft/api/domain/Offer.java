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
 * Offer entity representing a service stereotype
 */
@Schema(description = "Offer entity representing a service stereotype")
@Entity
@Table(name = "offer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "offer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "reviews", "showcases", "orders", "tags", "provider", "offer" }, allowSetters = true)
    private Set<ApplicationUserOffer> userOffers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "offers", "category" }, allowSetters = true)
    private Machine machine;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "offers" }, allowSetters = true)
    private OfferCategory category;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Offer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Offer name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ApplicationUserOffer> getUserOffers() {
        return this.userOffers;
    }

    public void setUserOffers(Set<ApplicationUserOffer> applicationUserOffers) {
        if (this.userOffers != null) {
            this.userOffers.forEach(i -> i.setOffer(null));
        }
        if (applicationUserOffers != null) {
            applicationUserOffers.forEach(i -> i.setOffer(this));
        }
        this.userOffers = applicationUserOffers;
    }

    public Offer userOffers(Set<ApplicationUserOffer> applicationUserOffers) {
        this.setUserOffers(applicationUserOffers);
        return this;
    }

    public Offer addUserOffers(ApplicationUserOffer applicationUserOffer) {
        this.userOffers.add(applicationUserOffer);
        applicationUserOffer.setOffer(this);
        return this;
    }

    public Offer removeUserOffers(ApplicationUserOffer applicationUserOffer) {
        this.userOffers.remove(applicationUserOffer);
        applicationUserOffer.setOffer(null);
        return this;
    }

    public Machine getMachine() {
        return this.machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Offer machine(Machine machine) {
        this.setMachine(machine);
        return this;
    }

    public OfferCategory getCategory() {
        return this.category;
    }

    public void setCategory(OfferCategory offerCategory) {
        this.category = offerCategory;
    }

    public Offer category(OfferCategory offerCategory) {
        this.setCategory(offerCategory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offer)) {
            return false;
        }
        return getId() != null && getId().equals(((Offer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Offer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
