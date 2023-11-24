package me.krft.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link me.krft.api.domain.Offer} entity.
 */
@Schema(description = "Offer entity representing a service stereotype")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OfferDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1)
    private String name;

    private OfferCategoryDTO category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OfferCategoryDTO getCategory() {
        return category;
    }

    public void setCategory(OfferCategoryDTO category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfferDTO)) {
            return false;
        }

        OfferDTO offerDTO = (OfferDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, offerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfferDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category=" + getCategory() +
            "}";
    }
}
