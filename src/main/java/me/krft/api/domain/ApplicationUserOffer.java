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
 * Association entity between user, offer and machine
 */
@Entity
@Table(name = "application_user_offer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationUserOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * Description of the service provided, written by the user providing it
     */
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "description", length = 512, nullable = false)
    private String description;

    /**
     * The price of the service, set by the user providing it
     */
    @NotNull
    @Min(value = 0)
    @Column(name = "price", nullable = false)
    private Integer price;

    /**
     * Active means the offer is visible to the users, we shouldn't delete it
     */
    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "applicationUserOffer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "applicationUserOffer" }, allowSetters = true)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "applicationUserOffer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "applicationUserOffer" }, allowSetters = true)
    private Set<Showcase> showcases = new HashSet<>();

    @OneToMany(mappedBy = "applicationUserOffer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "applicationUserOffer" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "offer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "offer", "customer" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "offer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "offer", "category" }, allowSetters = true)
    private Set<Machine> machines = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "internalUser", "offers", "badges", "orders", "city" }, allowSetters = true)
    private ApplicationUser applicationUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApplicationUserOffer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public ApplicationUserOffer description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return this.price;
    }

    public ApplicationUserOffer price(Integer price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getActive() {
        return this.active;
    }

    public ApplicationUserOffer active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Review> getReviews() {
        return this.reviews;
    }

    public void setReviews(Set<Review> reviews) {
        if (this.reviews != null) {
            this.reviews.forEach(i -> i.setApplicationUserOffer(null));
        }
        if (reviews != null) {
            reviews.forEach(i -> i.setApplicationUserOffer(this));
        }
        this.reviews = reviews;
    }

    public ApplicationUserOffer reviews(Set<Review> reviews) {
        this.setReviews(reviews);
        return this;
    }

    public ApplicationUserOffer addReviews(Review review) {
        this.reviews.add(review);
        review.setApplicationUserOffer(this);
        return this;
    }

    public ApplicationUserOffer removeReviews(Review review) {
        this.reviews.remove(review);
        review.setApplicationUserOffer(null);
        return this;
    }

    public Set<Showcase> getShowcases() {
        return this.showcases;
    }

    public void setShowcases(Set<Showcase> showcases) {
        if (this.showcases != null) {
            this.showcases.forEach(i -> i.setApplicationUserOffer(null));
        }
        if (showcases != null) {
            showcases.forEach(i -> i.setApplicationUserOffer(this));
        }
        this.showcases = showcases;
    }

    public ApplicationUserOffer showcases(Set<Showcase> showcases) {
        this.setShowcases(showcases);
        return this;
    }

    public ApplicationUserOffer addShowcases(Showcase showcase) {
        this.showcases.add(showcase);
        showcase.setApplicationUserOffer(this);
        return this;
    }

    public ApplicationUserOffer removeShowcases(Showcase showcase) {
        this.showcases.remove(showcase);
        showcase.setApplicationUserOffer(null);
        return this;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        if (this.tags != null) {
            this.tags.forEach(i -> i.setApplicationUserOffer(null));
        }
        if (tags != null) {
            tags.forEach(i -> i.setApplicationUserOffer(this));
        }
        this.tags = tags;
    }

    public ApplicationUserOffer tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public ApplicationUserOffer addTags(Tag tag) {
        this.tags.add(tag);
        tag.setApplicationUserOffer(this);
        return this;
    }

    public ApplicationUserOffer removeTags(Tag tag) {
        this.tags.remove(tag);
        tag.setApplicationUserOffer(null);
        return this;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.setOffer(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setOffer(this));
        }
        this.orders = orders;
    }

    public ApplicationUserOffer orders(Set<Order> orders) {
        this.setOrders(orders);
        return this;
    }

    public ApplicationUserOffer addOrders(Order order) {
        this.orders.add(order);
        order.setOffer(this);
        return this;
    }

    public ApplicationUserOffer removeOrders(Order order) {
        this.orders.remove(order);
        order.setOffer(null);
        return this;
    }

    public Set<Machine> getMachines() {
        return this.machines;
    }

    public void setMachines(Set<Machine> machines) {
        if (this.machines != null) {
            this.machines.forEach(i -> i.setOffer(null));
        }
        if (machines != null) {
            machines.forEach(i -> i.setOffer(this));
        }
        this.machines = machines;
    }

    public ApplicationUserOffer machines(Set<Machine> machines) {
        this.setMachines(machines);
        return this;
    }

    public ApplicationUserOffer addMachines(Machine machine) {
        this.machines.add(machine);
        machine.setOffer(this);
        return this;
    }

    public ApplicationUserOffer removeMachines(Machine machine) {
        this.machines.remove(machine);
        machine.setOffer(null);
        return this;
    }

    public ApplicationUser getApplicationUser() {
        return this.applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public ApplicationUserOffer applicationUser(ApplicationUser applicationUser) {
        this.setApplicationUser(applicationUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUserOffer)) {
            return false;
        }
        return id != null && id.equals(((ApplicationUserOffer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUserOffer{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", active='" + getActive() + "'" +
            "}";
    }
}
