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
public class OfferDTO extends AbstractDTO implements Serializable {

    private Long id;
    private String name;
    private Set<ApplicationUserOfferDTO> userOffers;
    private MachineDTO machine;
    private OfferCategoryDTO offerCategory;
}
