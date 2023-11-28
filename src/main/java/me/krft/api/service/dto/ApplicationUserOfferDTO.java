package me.krft.api.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationUserOfferDTO extends AbstractDTO implements Serializable {

    private Long id;
    private String description;
    private Double price;
    private Boolean active;
    private Set<ShowcaseDTO> showcases;
    private Set<OrderDTO> orders;
    private Set<TagDTO> tags;
    private ApplicationUserDTO provider;
    private OfferDTO offer;

    /**
     * Following fields are not included in the Entity
     */
    private Double rating;
    private Integer numberOfReviews;
}
