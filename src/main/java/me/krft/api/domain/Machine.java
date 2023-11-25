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
 * Machine entity
 */
@Schema(description = "Machine entity")
@Entity
@Table(name = "machine")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Machine implements Serializable {

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

    @OneToMany(mappedBy = "machine")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userOffers", "machine", "category" }, allowSetters = true)
    private Set<Offer> offers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "machines" }, allowSetters = true)
    private MachineCategory machineCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Machine id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Machine name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Offer> getOffers() {
        return this.offers;
    }

    public void setOffers(Set<Offer> offers) {
        if (this.offers != null) {
            this.offers.forEach(i -> i.setMachine(null));
        }
        if (offers != null) {
            offers.forEach(i -> i.setMachine(this));
        }
        this.offers = offers;
    }

    public Machine offers(Set<Offer> offers) {
        this.setOffers(offers);
        return this;
    }

    public Machine addOffers(Offer offer) {
        this.offers.add(offer);
        offer.setMachine(this);
        return this;
    }

    public Machine removeOffers(Offer offer) {
        this.offers.remove(offer);
        offer.setMachine(null);
        return this;
    }

    public MachineCategory getMachineCategory() {
        return this.machineCategory;
    }

    public void setMachineCategory(MachineCategory machineCategory) {
        this.machineCategory = machineCategory;
    }

    public Machine machineCategory(MachineCategory machineCategory) {
        this.setMachineCategory(machineCategory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Machine)) {
            return false;
        }
        return id != null && id.equals(((Machine) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Machine{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
