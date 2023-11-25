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
 * City entity
 */
@Schema(description = "City entity")
@Entity
@Table(name = "city")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * The city's name
     */
    @Schema(description = "The city's name", required = true)
    @NotNull
    @Size(min = 1)
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The city's zipcode
     */
    @Schema(description = "The city's zipcode", required = true)
    @NotNull
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "internalUser", "offers", "badges", "orders", "city" }, allowSetters = true)
    private Set<ApplicationUser> users = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "cities", "country" }, allowSetters = true)
    private Region region;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public City id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public City name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public City zipCode(String zipCode) {
        this.setZipCode(zipCode);
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Set<ApplicationUser> getUsers() {
        return this.users;
    }

    public void setUsers(Set<ApplicationUser> applicationUsers) {
        if (this.users != null) {
            this.users.forEach(i -> i.setCity(null));
        }
        if (applicationUsers != null) {
            applicationUsers.forEach(i -> i.setCity(this));
        }
        this.users = applicationUsers;
    }

    public City users(Set<ApplicationUser> applicationUsers) {
        this.setUsers(applicationUsers);
        return this;
    }

    public City addUsers(ApplicationUser applicationUser) {
        this.users.add(applicationUser);
        applicationUser.setCity(this);
        return this;
    }

    public City removeUsers(ApplicationUser applicationUser) {
        this.users.remove(applicationUser);
        applicationUser.setCity(null);
        return this;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public City region(Region region) {
        this.setRegion(region);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof City)) {
            return false;
        }
        return getId() != null && getId().equals(((City) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "City{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            "}";
    }
}
