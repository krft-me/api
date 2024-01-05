package me.krft.api.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationUserDTO extends AbstractDTO implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private UUID profilePictureId;
    private UserDTO internalUser;
    private Set<ApplicationUserOfferDTO> offers;
    private Set<ApplicationUserBadgeDTO> badges;
    private Set<OrderDTO> orders;
    private CityDTO city;
}
