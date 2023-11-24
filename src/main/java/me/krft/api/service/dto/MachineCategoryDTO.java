package me.krft.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link me.krft.api.domain.MachineCategory} entity.
 */
@Schema(description = "MachineCategory entity\nRepresents a preset machine type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MachineCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1)
    private String label;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MachineCategoryDTO)) {
            return false;
        }

        MachineCategoryDTO machineCategoryDTO = (MachineCategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, machineCategoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MachineCategoryDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
