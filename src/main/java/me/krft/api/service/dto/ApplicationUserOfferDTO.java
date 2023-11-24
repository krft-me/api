package me.krft.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link me.krft.api.domain.ApplicationUserOffer} entity.
 */
@Schema(description = "Association entity between user, offer and machine")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationUserOfferDTO implements Serializable {

    private Long id;

    /**
     * Description of the service provided, written by the user providing it
     */
    @NotNull
    @Size(min = 1, max = 512)
    @Schema(description = "Description of the service provided, written by the user providing it", required = true)
    private String description;

    /**
     * The price of the service, set by the user providing it
     */
    @NotNull
    @Min(value = 0)
    @Schema(description = "The price of the service, set by the user providing it", required = true)
    private Integer price;

    /**
     * Active means the offer is visible to the users, we shouldn't delete it
     */
    @NotNull
    @Schema(description = "Active means the offer is visible to the users, we shouldn't delete it", required = true)
    private Boolean active;

    private ApplicationUserDTO applicationUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ApplicationUserDTO getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUserDTO applicationUser) {
        this.applicationUser = applicationUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUserOfferDTO)) {
            return false;
        }

        ApplicationUserOfferDTO applicationUserOfferDTO = (ApplicationUserOfferDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, applicationUserOfferDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUserOfferDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", active='" + getActive() + "'" +
            ", applicationUser=" + getApplicationUser() +
            "}";
    }
}
