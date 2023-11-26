package me.krft.api.service.mapper;

public interface EntityDTOMapper<E, D> {
    E toEntity(D dto);
    D toDTO(E entity);

    D toDTOId(E entity);
}
