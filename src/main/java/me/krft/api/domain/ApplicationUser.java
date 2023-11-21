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
 * A ApplicationUser.
 */
@Entity
@Table(name = "application_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "pseudo", nullable = false)
    private String pseudo;

    @OneToOne
    @JoinColumn(unique = true)
    private User internalUser;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "region" }, allowSetters = true)
    private City city;

    @ManyToMany
    @JoinTable(
        name = "rel_application_user__favorite_application_user",
        joinColumns = @JoinColumn(name = "application_user_id"),
        inverseJoinColumns = @JoinColumn(name = "favorite_application_user_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "internalUser", "city", "favoriteApplicationUsers", "favoriteOffers", "followers" },
        allowSetters = true
    )
    private Set<ApplicationUser> favoriteApplicationUsers = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_application_user__favorite_offer",
        joinColumns = @JoinColumn(name = "application_user_id"),
        inverseJoinColumns = @JoinColumn(name = "favorite_offer_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "machine", "followers" }, allowSetters = true)
    private Set<Offer> favoriteOffers = new HashSet<>();

    @ManyToMany(mappedBy = "favoriteApplicationUsers")
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

    public ApplicationUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public ApplicationUser firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public ApplicationUser lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public ApplicationUser pseudo(String pseudo) {
        this.setPseudo(pseudo);
        return this;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public ApplicationUser internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public ApplicationUser city(City city) {
        this.setCity(city);
        return this;
    }

    public Set<ApplicationUser> getFavoriteApplicationUsers() {
        return this.favoriteApplicationUsers;
    }

    public void setFavoriteApplicationUsers(Set<ApplicationUser> applicationUsers) {
        this.favoriteApplicationUsers = applicationUsers;
    }

    public ApplicationUser favoriteApplicationUsers(Set<ApplicationUser> applicationUsers) {
        this.setFavoriteApplicationUsers(applicationUsers);
        return this;
    }

    public ApplicationUser addFavoriteApplicationUser(ApplicationUser applicationUser) {
        this.favoriteApplicationUsers.add(applicationUser);
        applicationUser.getFollowers().add(this);
        return this;
    }

    public ApplicationUser removeFavoriteApplicationUser(ApplicationUser applicationUser) {
        this.favoriteApplicationUsers.remove(applicationUser);
        applicationUser.getFollowers().remove(this);
        return this;
    }

    public Set<Offer> getFavoriteOffers() {
        return this.favoriteOffers;
    }

    public void setFavoriteOffers(Set<Offer> offers) {
        this.favoriteOffers = offers;
    }

    public ApplicationUser favoriteOffers(Set<Offer> offers) {
        this.setFavoriteOffers(offers);
        return this;
    }

    public ApplicationUser addFavoriteOffer(Offer offer) {
        this.favoriteOffers.add(offer);
        offer.getFollowers().add(this);
        return this;
    }

    public ApplicationUser removeFavoriteOffer(Offer offer) {
        this.favoriteOffers.remove(offer);
        offer.getFollowers().remove(this);
        return this;
    }

    public Set<ApplicationUser> getFollowers() {
        return this.followers;
    }

    public void setFollowers(Set<ApplicationUser> applicationUsers) {
        if (this.followers != null) {
            this.followers.forEach(i -> i.removeFavoriteApplicationUser(this));
        }
        if (applicationUsers != null) {
            applicationUsers.forEach(i -> i.addFavoriteApplicationUser(this));
        }
        this.followers = applicationUsers;
    }

    public ApplicationUser followers(Set<ApplicationUser> applicationUsers) {
        this.setFollowers(applicationUsers);
        return this;
    }

    public ApplicationUser addFollowers(ApplicationUser applicationUser) {
        this.followers.add(applicationUser);
        applicationUser.getFavoriteApplicationUsers().add(this);
        return this;
    }

    public ApplicationUser removeFollowers(ApplicationUser applicationUser) {
        this.followers.remove(applicationUser);
        applicationUser.getFavoriteApplicationUsers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUser)) {
            return false;
        }
        return id != null && id.equals(((ApplicationUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUser{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", pseudo='" + getPseudo() + "'" +
            "}";
    }
}
