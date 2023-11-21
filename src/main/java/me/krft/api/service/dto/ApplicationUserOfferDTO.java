package me.krft.api.service.dto;

import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
public class ApplicationUserOfferDTO extends AbstractDTO implements Serializable {
    @SerializedName("id")
    private Long id;

    @SerializedName("description")
    private String description;

    @SerializedName("ratings")
    private Set<RatingDTO> ratings;

    @SerializedName("showcases")
    private Set<ShowcaseDTO> showcases;

    @SerializedName("tags")
    private Set<TagDTO> tags;
}
