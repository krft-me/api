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
 * User entity extending the inter {@code User} entity\nProvides additional information about the user
 */
@Schema(description = "User entity extending the inter {@code User} entity\nProvides additional information about the user")
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

    /**
     * The user's first name
     */
    @Schema(description = "The user's first name", required = true)
    @NotNull
    @Size(min = 1)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * The user's last name name
     */
    @Schema(description = "The user's last name name", required = true)
    @NotNull
    @Size(min = 1)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * The user's username
     */
    @Schema(description = "The user's username", required = true)
    @NotNull
    @Size(min = 1)
    @Column(name = "username", nullable = false)
    private String username;

    @OneToOne
    @JoinColumn(unique = true)
    private User internalUser;

    @OneToMany(mappedBy = "applicationUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "reviews", "showcases", "tags", "orders", "applicationUser", "offer" }, allowSetters = true)
    private Set<ApplicationUserOffer> offers = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "badge" }, allowSetters = true)
    private Set<ApplicationUserBadge> badges = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "offer", "customer" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "applicationUsers", "region" }, allowSetters = true)
    private City city;

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

    public String getUsername() {
        return this.username;
    }

    public ApplicationUser username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Set<ApplicationUserOffer> getOffers() {
        return this.offers;
    }

    public void setOffers(Set<ApplicationUserOffer> applicationUserOffers) {
        if (this.offers != null) {
            this.offers.forEach(i -> i.setApplicationUser(null));
        }
        if (applicationUserOffers != null) {
            applicationUserOffers.forEach(i -> i.setApplicationUser(this));
        }
        this.offers = applicationUserOffers;
    }

    public ApplicationUser offers(Set<ApplicationUserOffer> applicationUserOffers) {
        this.setOffers(applicationUserOffers);
        return this;
    }

    public ApplicationUser addOffers(ApplicationUserOffer applicationUserOffer) {
        this.offers.add(applicationUserOffer);
        applicationUserOffer.setApplicationUser(this);
        return this;
    }

    public ApplicationUser removeOffers(ApplicationUserOffer applicationUserOffer) {
        this.offers.remove(applicationUserOffer);
        applicationUserOffer.setApplicationUser(null);
        return this;
    }

    public Set<ApplicationUserBadge> getBadges() {
        return this.badges;
    }

    public void setBadges(Set<ApplicationUserBadge> applicationUserBadges) {
        if (this.badges != null) {
            this.badges.forEach(i -> i.setUser(null));
        }
        if (applicationUserBadges != null) {
            applicationUserBadges.forEach(i -> i.setUser(this));
        }
        this.badges = applicationUserBadges;
    }

    public ApplicationUser badges(Set<ApplicationUserBadge> applicationUserBadges) {
        this.setBadges(applicationUserBadges);
        return this;
    }

    public ApplicationUser addBadges(ApplicationUserBadge applicationUserBadge) {
        this.badges.add(applicationUserBadge);
        applicationUserBadge.setUser(this);
        return this;
    }

    public ApplicationUser removeBadges(ApplicationUserBadge applicationUserBadge) {
        this.badges.remove(applicationUserBadge);
        applicationUserBadge.setUser(null);
        return this;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.setCustomer(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setCustomer(this));
        }
        this.orders = orders;
    }

    public ApplicationUser orders(Set<Order> orders) {
        this.setOrders(orders);
        return this;
    }

    public ApplicationUser addOrders(Order order) {
        this.orders.add(order);
        order.setCustomer(this);
        return this;
    }

    public ApplicationUser removeOrders(Order order) {
        this.orders.remove(order);
        order.setCustomer(null);
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
            ", username='" + getUsername() + "'" +
            "}";
    }
}
