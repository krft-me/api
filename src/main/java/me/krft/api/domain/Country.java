package me.krft.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Country entity
 */
@Schema(description = "Country entity")
@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * The country's name in english
     */
    @Schema(description = "The country's name in english")
    @Column(name = "name")
    private String name;

    /**
     * ISO 3166-1 alpha-2
     */
    @Schema(description = "ISO 3166-1 alpha-2", required = true)
    @NotNull
    @Size(max = 3)
    @Column(name = "iso_code", length = 3, nullable = false, unique = true)
    private String isoCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cities", "country" }, allowSetters = true)
    private Set<Region> regions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Country id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Country name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoCode() {
        return this.isoCode;
    }

    public Country isoCode(String isoCode) {
        this.setIsoCode(isoCode);
        return this;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public Set<Region> getRegions() {
        return this.regions;
    }

    public void setRegions(Set<Region> regions) {
        if (this.regions != null) {
            this.regions.forEach(i -> i.setCountry(null));
        }
        if (regions != null) {
            regions.forEach(i -> i.setCountry(this));
        }
        this.regions = regions;
    }

    public Country regions(Set<Region> regions) {
        this.setRegions(regions);
        return this;
    }

    public Country addRegions(Region region) {
        this.regions.add(region);
        region.setCountry(this);
        return this;
    }

    public Country removeRegions(Region region) {
        this.regions.remove(region);
        region.setCountry(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return getId() != null && getId().equals(((Country) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isoCode='" + getIsoCode() + "'" +
            "}";
    }
}
