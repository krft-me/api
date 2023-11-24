package me.krft.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link me.krft.api.domain.Showcase} entity.
 */
@Schema(description = "Showcase image\nRepresents an image of a service to illustrate it")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShowcaseDTO implements Serializable {

    private Long id;

    @NotNull
    private UUID imageId;

    private ApplicationUserOfferDTO applicationUserOffer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getImageId() {
        return imageId;
    }

    public void setImageId(UUID imageId) {
        this.imageId = imageId;
    }

    public ApplicationUserOfferDTO getApplicationUserOffer() {
        return applicationUserOffer;
    }

    public void setApplicationUserOffer(ApplicationUserOfferDTO applicationUserOffer) {
        this.applicationUserOffer = applicationUserOffer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShowcaseDTO)) {
            return false;
        }

        ShowcaseDTO showcaseDTO = (ShowcaseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, showcaseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShowcaseDTO{" +
            "id=" + getId() +
            ", imageId='" + getImageId() + "'" +
            ", applicationUserOffer=" + getApplicationUserOffer() +
            "}";
    }
}
