package me.krft.api.service.mapper;

public interface EntityDTOMapper<E, D> {
    D toDTO(E entity);

    E toEntity(D dto);
}
