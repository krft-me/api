package me.krft.api.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.krft.api.domain.enumeration.State;

@SuperBuilder
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO extends AbstractDTO implements Serializable {

    private Long id;
    private Instant date;
    private State state;
    private ApplicationUserOfferDTO offer;
    private ApplicationUserDTO customer;
}
