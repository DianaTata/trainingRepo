package com.epam.rd.java.basic.finalProject.mapper;

public interface Mapper<DTO, ENTITY> {
    ENTITY mapToEntity(DTO dto);

    DTO mapToDTO(ENTITY entity);
}
