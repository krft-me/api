package me.krft.api.service.mapper;

import me.krft.api.domain.Badge;
import me.krft.api.service.dto.BadgeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Badge} and its DTO {@link BadgeDTO}.
 */
@Mapper(componentModel = "spring")
public interface BadgeMapper extends EntityMapper<BadgeDTO, Badge> {}
