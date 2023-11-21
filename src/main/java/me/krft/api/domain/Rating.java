package me.krft.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Rating.
 */
@Entity
@Table(name = "rating")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "rate", nullable = false)
    private Double rate;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "showcases", "tags", "applicationUser", "offer" }, allowSetters = true)
    private ApplicationUserOffer applicationUserOffer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rating id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRate() {
        return this.rate;
    }

    public Rating rate(Double rate) {
        this.setRate(rate);
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getComment() {
        return this.comment;
    }

    public Rating comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ApplicationUserOffer getApplicationUserOffer() {
        return this.applicationUserOffer;
    }

    public void setApplicationUserOffer(ApplicationUserOffer applicationUserOffer) {
        this.applicationUserOffer = applicationUserOffer;
    }

    public Rating applicationUserOffer(ApplicationUserOffer applicationUserOffer) {
        this.setApplicationUserOffer(applicationUserOffer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rating)) {
            return false;
        }
        return id != null && id.equals(((Rating) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rating{" +
            "id=" + getId() +
            ", rate=" + getRate() +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
