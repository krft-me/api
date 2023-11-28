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
public class CityDTO extends AbstractDTO implements Serializable {

    private Long id;
    private String name;
    private String zipCode;
    private Set<ApplicationUserDTO> users;
    private RegionDTO region;
}
