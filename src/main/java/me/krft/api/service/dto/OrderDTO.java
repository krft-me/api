package me.krft.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;
import me.krft.api.domain.enumeration.State;

/**
 * A DTO for the {@link me.krft.api.domain.Order} entity.
 */
@Schema(description = "Order entity\nRepresents an order placed by a customer for an offer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDTO implements Serializable {

    private Long id;

    /**
     * Date the order was placed
     */
    @NotNull
    @Schema(description = "Date the order was placed", required = true)
    private Instant date;

    /**
     * State of the order
     */
    @NotNull
    @Schema(description = "State of the order", required = true)
    private State state;

    private ApplicationUserOfferDTO offer;

    private ApplicationUserDTO customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public ApplicationUserOfferDTO getOffer() {
        return offer;
    }

    public void setOffer(ApplicationUserOfferDTO offer) {
        this.offer = offer;
    }

    public ApplicationUserDTO getCustomer() {
        return customer;
    }

    public void setCustomer(ApplicationUserDTO customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        OrderDTO orderDTO = (OrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", state='" + getState() + "'" +
            ", offer=" + getOffer() +
            ", customer=" + getCustomer() +
            "}";
    }
}
