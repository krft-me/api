package me.krft.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link me.krft.api.domain.Review} entity.
 */
@Schema(description = "Review entity\nRepresents a user's opinion of a service they have purchased")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReviewDTO implements Serializable {

    private Long id;

    /**
     * The rating of the service from 0.0 to 5.0
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 50)
    @Schema(description = "The rating of the service from 0.0 to 5.0", required = true)
    private Integer rating;

    /**
     * Optional comment about the service or the service provider
     */
    @Schema(description = "Optional comment about the service or the service provider")
    private String comment;

    private ApplicationUserOfferDTO applicationUserOffer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
        if (!(o instanceof ReviewDTO)) {
            return false;
        }

        ReviewDTO reviewDTO = (ReviewDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reviewDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReviewDTO{" +
            "id=" + getId() +
            ", rating=" + getRating() +
            ", comment='" + getComment() + "'" +
            ", applicationUserOffer=" + getApplicationUserOffer() +
            "}";
    }
}
