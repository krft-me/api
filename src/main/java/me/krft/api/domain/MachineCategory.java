package me.krft.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * MachineCategory entity
 * Represents a preset machine type
 */
@Schema(description = "MachineCategory entity\nRepresents a preset machine type")
@Entity
@Table(name = "machine_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MachineCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "label", nullable = false, unique = true)
    private String label;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "offers", "category" }, allowSetters = true)
    private Set<Machine> machines = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MachineCategory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public MachineCategory label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Machine> getMachines() {
        return this.machines;
    }

    public void setMachines(Set<Machine> machines) {
        if (this.machines != null) {
            this.machines.forEach(i -> i.setCategory(null));
        }
        if (machines != null) {
            machines.forEach(i -> i.setCategory(this));
        }
        this.machines = machines;
    }

    public MachineCategory machines(Set<Machine> machines) {
        this.setMachines(machines);
        return this;
    }

    public MachineCategory addMachines(Machine machine) {
        this.machines.add(machine);
        machine.setCategory(this);
        return this;
    }

    public MachineCategory removeMachines(Machine machine) {
        this.machines.remove(machine);
        machine.setCategory(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MachineCategory)) {
            return false;
        }
        return getId() != null && getId().equals(((MachineCategory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MachineCategory{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
