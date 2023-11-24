package me.krft.api.service.mapper;

import me.krft.api.domain.MachineCategory;
import me.krft.api.service.dto.MachineCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MachineCategory} and its DTO {@link MachineCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface MachineCategoryMapper extends EntityMapper<MachineCategoryDTO, MachineCategory> {}
