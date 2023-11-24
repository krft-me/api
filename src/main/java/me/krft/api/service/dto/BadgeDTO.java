package me.krft.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link me.krft.api.domain.Badge} entity.
 */
@Schema(description = "Badge entity\nRepresents a certification (example: 100 completed orders)")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BadgeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1)
    private String label;

    @Lob
    private byte[] picture;

    private String pictureContentType;

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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BadgeDTO)) {
            return false;
        }

        BadgeDTO badgeDTO = (BadgeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, badgeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BadgeDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", picture='" + getPicture() + "'" +
            "}";
    }
}
