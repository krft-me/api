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
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ratings", "showcases", "tags", "offer", "applicationUser" }, allowSetters = true)
    private ApplicationUserOffer provider;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "internalUser", "city", "favoriteApplicationUsers", "favoriteOffers", "followers" },
        allowSetters = true
    )
    private ApplicationUser client;

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

    public ApplicationUserOffer getProvider() {
        return this.provider;
    }

    public void setProvider(ApplicationUserOffer applicationUserOffer) {
        this.provider = applicationUserOffer;
    }

    public Order provider(ApplicationUserOffer applicationUserOffer) {
        this.setProvider(applicationUserOffer);
        return this;
    }

    public ApplicationUser getClient() {
        return this.client;
    }

    public void setClient(ApplicationUser applicationUser) {
        this.client = applicationUser;
    }

    public Order client(ApplicationUser applicationUser) {
        this.setClient(applicationUser);
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
