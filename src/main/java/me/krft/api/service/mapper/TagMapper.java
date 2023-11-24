package me.krft.api.service.mapper;

import me.krft.api.domain.ApplicationUserOffer;
import me.krft.api.domain.Tag;
import me.krft.api.service.dto.ApplicationUserOfferDTO;
import me.krft.api.service.dto.TagDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDTO}.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagDTO, Tag> {
    @Mapping(target = "applicationUserOffer", source = "applicationUserOffer", qualifiedByName = "applicationUserOfferId")
    TagDTO toDto(Tag s);

    @Named("applicationUserOfferId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserOfferDTO toDtoApplicationUserOfferId(ApplicationUserOffer applicationUserOffer);
}
