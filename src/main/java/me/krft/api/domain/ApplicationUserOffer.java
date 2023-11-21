package me.krft.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.*;
import javax.validation.constraints.*;

import me.krft.api.service.dto.OfferCard;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ApplicationUserOffer.
 */
@Entity
@Table(name = "application_user_offer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationUserOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "applicationUserOffer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "applicationUserOffer" }, allowSetters = true)
    private Set<Rating> ratings = new HashSet<>();

    @OneToMany(mappedBy = "applicationUserOffer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "applicationUserOffer" }, allowSetters = true)
    private Set<Showcase> showcases = new HashSet<>();

    @OneToMany(mappedBy = "applicationUserOffer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "applicationUserOffer" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "internalUser", "city", "favoriteApplicationUsers", "favoriteOffers", "followers" },
        allowSetters = true
    )
    private ApplicationUser applicationUser;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "machine", "followers" }, allowSetters = true)
    private Offer offer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApplicationUserOffer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public ApplicationUserOffer description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Rating> getRatings() {
        return this.ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        if (this.ratings != null) {
            this.ratings.forEach(i -> i.setApplicationUserOffer(null));
        }
        if (ratings != null) {
            ratings.forEach(i -> i.setApplicationUserOffer(this));
        }
        this.ratings = ratings;
    }

    public ApplicationUserOffer ratings(Set<Rating> ratings) {
        this.setRatings(ratings);
        return this;
    }

    public ApplicationUserOffer addRating(Rating rating) {
        this.ratings.add(rating);
        rating.setApplicationUserOffer(this);
        return this;
    }

    public ApplicationUserOffer removeRating(Rating rating) {
        this.ratings.remove(rating);
        rating.setApplicationUserOffer(null);
        return this;
    }

    public Set<Showcase> getShowcases() {
        return this.showcases;
    }

    public void setShowcases(Set<Showcase> showcases) {
        if (this.showcases != null) {
            this.showcases.forEach(i -> i.setApplicationUserOffer(null));
        }
        if (showcases != null) {
            showcases.forEach(i -> i.setApplicationUserOffer(this));
        }
        this.showcases = showcases;
    }

    public ApplicationUserOffer showcases(Set<Showcase> showcases) {
        this.setShowcases(showcases);
        return this;
    }

    public ApplicationUserOffer addShowcase(Showcase showcase) {
        this.showcases.add(showcase);
        showcase.setApplicationUserOffer(this);
        return this;
    }

    public ApplicationUserOffer removeShowcase(Showcase showcase) {
        this.showcases.remove(showcase);
        showcase.setApplicationUserOffer(null);
        return this;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        if (this.tags != null) {
            this.tags.forEach(i -> i.setApplicationUserOffer(null));
        }
        if (tags != null) {
            tags.forEach(i -> i.setApplicationUserOffer(this));
        }
        this.tags = tags;
    }

    public ApplicationUserOffer tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public ApplicationUserOffer addTag(Tag tag) {
        this.tags.add(tag);
        tag.setApplicationUserOffer(this);
        return this;
    }

    public ApplicationUserOffer removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.setApplicationUserOffer(null);
        return this;
    }

    public ApplicationUser getApplicationUser() {
        return this.applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public ApplicationUserOffer applicationUser(ApplicationUser applicationUser) {
        this.setApplicationUser(applicationUser);
        return this;
    }

    public Offer getOffer() {
        return this.offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public ApplicationUserOffer offer(Offer offer) {
        this.setOffer(offer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public OfferCard toOfferCard() {
        return new OfferCard(this.offer.getMachine().getName(), this.id, this.offer.getName(), "link", this.description, this.tags.stream().map(Tag::getLabel).collect(Collectors.toList()), this.ratings.stream().mapToDouble(Rating::getRate).average().orElse(0.0), this.ratings.size(), 10.00, "link", this.applicationUser.getPseudo(), this.applicationUser.getCity().getName(), false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUserOffer)) {
            return false;
        }
        return id != null && id.equals(((ApplicationUserOffer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUserOffer{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
