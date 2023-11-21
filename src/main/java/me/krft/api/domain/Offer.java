package me.krft.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Offer.
 */
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
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "category" }, allowSetters = true)
    private Machine machine;

    @ManyToMany(mappedBy = "favoriteOffers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "internalUser", "city", "favoriteApplicationUsers", "favoriteOffers", "followers" },
        allowSetters = true
    )
    private Set<ApplicationUser> followers = new HashSet<>();

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

    public Set<ApplicationUser> getFollowers() {
        return this.followers;
    }

    public void setFollowers(Set<ApplicationUser> applicationUsers) {
        if (this.followers != null) {
            this.followers.forEach(i -> i.removeFavoriteOffer(this));
        }
        if (applicationUsers != null) {
            applicationUsers.forEach(i -> i.addFavoriteOffer(this));
        }
        this.followers = applicationUsers;
    }

    public Offer followers(Set<ApplicationUser> applicationUsers) {
        this.setFollowers(applicationUsers);
        return this;
    }

    public Offer addFollowers(ApplicationUser applicationUser) {
        this.followers.add(applicationUser);
        applicationUser.getFavoriteOffers().add(this);
        return this;
    }

    public Offer removeFollowers(ApplicationUser applicationUser) {
        this.followers.remove(applicationUser);
        applicationUser.getFavoriteOffers().remove(this);
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
        return id != null && id.equals(((Offer) o).id);
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
