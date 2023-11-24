package me.krft.api.service.mapper;

import me.krft.api.domain.ApplicationUserOffer;
import me.krft.api.domain.Machine;
import me.krft.api.domain.MachineCategory;
import me.krft.api.service.dto.ApplicationUserOfferDTO;
import me.krft.api.service.dto.MachineCategoryDTO;
import me.krft.api.service.dto.MachineDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Machine} and its DTO {@link MachineDTO}.
 */
@Mapper(componentModel = "spring")
public interface MachineMapper extends EntityMapper<MachineDTO, Machine> {
    @Mapping(target = "offer", source = "offer", qualifiedByName = "applicationUserOfferId")
    @Mapping(target = "category", source = "category", qualifiedByName = "machineCategoryId")
    MachineDTO toDto(Machine s);

    @Named("applicationUserOfferId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserOfferDTO toDtoApplicationUserOfferId(ApplicationUserOffer applicationUserOffer);

    @Named("machineCategoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MachineCategoryDTO toDtoMachineCategoryId(MachineCategory machineCategory);
}
