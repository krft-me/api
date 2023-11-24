package me.krft.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import me.krft.api.domain.enumeration.State;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Order entity\nRepresents an order placed by a customer for an offer
 */
@Entity
@Table(name = "krftme_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * Date the order was placed
     */
    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    /**
     * State of the order
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "reviews", "showcases", "tags", "orders", "machines", "applicationUser" }, allowSetters = true)
    private ApplicationUserOffer offer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "internalUser", "offers", "badges", "orders", "city" }, allowSetters = true)
    private ApplicationUser customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public Order date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public State getState() {
        return this.state;
    }

    public Order state(State state) {
        this.setState(state);
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public ApplicationUserOffer getOffer() {
        return this.offer;
    }

    public void setOffer(ApplicationUserOffer applicationUserOffer) {
        this.offer = applicationUserOffer;
    }

    public Order offer(ApplicationUserOffer applicationUserOffer) {
        this.setOffer(applicationUserOffer);
        return this;
    }

    public ApplicationUser getCustomer() {
        return this.customer;
    }

    public void setCustomer(ApplicationUser applicationUser) {
        this.customer = applicationUser;
    }

    public Order customer(ApplicationUser applicationUser) {
        this.setCustomer(applicationUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
