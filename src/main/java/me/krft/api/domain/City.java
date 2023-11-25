package me.krft.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
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

    @OneToMany(mappedBy = "city")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "internalUser", "offers", "badges", "orders", "city" }, allowSetters = true)
    private Set<ApplicationUser> applicationUsers = new HashSet<>();

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

    public Set<ApplicationUser> getApplicationUsers() {
        return this.applicationUsers;
    }

    public void setApplicationUsers(Set<ApplicationUser> applicationUsers) {
        if (this.applicationUsers != null) {
            this.applicationUsers.forEach(i -> i.setCity(null));
        }
        if (applicationUsers != null) {
            applicationUsers.forEach(i -> i.setCity(this));
        }
        this.applicationUsers = applicationUsers;
    }

    public City applicationUsers(Set<ApplicationUser> applicationUsers) {
        this.setApplicationUsers(applicationUsers);
        return this;
    }

    public City addApplicationUser(ApplicationUser applicationUser) {
        this.applicationUsers.add(applicationUser);
        applicationUser.setCity(this);
        return this;
    }

    public City removeApplicationUser(ApplicationUser applicationUser) {
        this.applicationUsers.remove(applicationUser);
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
        return id != null && id.equals(((City) o).id);
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
