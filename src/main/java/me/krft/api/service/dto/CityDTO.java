package me.krft.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link me.krft.api.domain.City} entity.
 */
@Schema(description = "City entity")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CityDTO implements Serializable {

    private Long id;

    /**
     * The city's name
     */
    @NotNull
    @Size(min = 1)
    @Schema(description = "The city's name", required = true)
    private String name;

    /**
     * The city's zipcode
     */
    @NotNull
    @Schema(description = "The city's zipcode", required = true)
    private String zipCode;

    private RegionDTO region;

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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CityDTO)) {
            return false;
        }

        CityDTO cityDTO = (CityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", region=" + getRegion() +
            "}";
    }
}
