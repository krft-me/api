package me.krft.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link me.krft.api.domain.Tag} entity.
 */
@Schema(description = "Tag entity\nRepresents a preset keyword for an offer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TagDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1)
    private String label;

    private ApplicationUserOfferDTO applicationUserOffer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
        if (!(o instanceof TagDTO)) {
            return false;
        }

        TagDTO tagDTO = (TagDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tagDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", applicationUserOffer=" + getApplicationUserOffer() +
            "}";
    }
}
