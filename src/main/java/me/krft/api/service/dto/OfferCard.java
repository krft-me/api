package me.krft.api.service.dto;

import com.google.gson.annotations.SerializedName;
import me.krft.api.domain.ApplicationUserOffer;

import java.util.List;

public class OfferCard {
    @SerializedName("machine")
    private String machine;

    @SerializedName("offerId")
    private Long offerId;

    @SerializedName("offer")
    private String offer;

    @SerializedName("offerPicture")
    private String offerPicture;

    @SerializedName("description")
    private String description;

    @SerializedName("tags")
    private List<String> tags;

    @SerializedName("rate")
    private Double rate;

    @SerializedName("numberRates")
    private Integer numberRates;

    @SerializedName("minimalPrice")
    private Double minimalPrice;

    @SerializedName("profilePicture")
    private String profilePicture;

    @SerializedName("profileName")
    private String profileName;

    @SerializedName("city")
    private String city;

    @SerializedName("isLiked")
    private Boolean isLiked;

    // All args constructor
    public OfferCard(String machine, Long offerId, String offer, String offerPicture, String description, List<String> tags, Double rate, Integer numberRates, Double minimalPrice, String profilePicture, String profileName, String city, Boolean isLiked) {
        this.machine = machine;
        this.offerId = offerId;
        this.offer = offer;
        this.offerPicture = offerPicture;
        this.description = description;
        this.tags = tags;
        this.rate = rate;
        this.numberRates = numberRates;
        this.minimalPrice = minimalPrice;
        this.profilePicture = profilePicture;
        this.profileName = profileName;
        this.city = city;
        this.isLiked = isLiked;
    }

    // Getters and setters
    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getOfferPicture() {
        return offerPicture;
    }

    public void setOfferPicture(String offerPicture) {
        this.offerPicture = offerPicture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getNumberRates() {
        return numberRates;
    }

    public void setNumberRates(Integer numberRates) {
        this.numberRates = numberRates;
    }

    public Double getMinimalPrice() {
        return minimalPrice;
    }

    public void setMinimalPrice(Double minimalPrice) {
        this.minimalPrice = minimalPrice;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getLiked() {
        return isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }

    // toString
    @Override
    public String toString() {
        return "OfferCard{" +
            "machine='" + machine + '\'' +
            ", offerId=" + offerId +
            ", offer='" + offer + '\'' +
            ", offerPicture='" + offerPicture + '\'' +
            ", description='" + description + '\'' +
            ", tags=" + tags +
            ", rate=" + rate +
            ", numberRates=" + numberRates +
            ", minimalPrice=" + minimalPrice +
            ", profilePicture='" + profilePicture + '\'' +
            ", profileName='" + profileName + '\'' +
            ", city='" + city + '\'' +
            ", isLiked=" + isLiked +
            '}';
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfferCard)) {
            return false;
        }
        return offerId != null && offerId.equals(((OfferCard) o).offerId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
