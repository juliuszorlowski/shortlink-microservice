package com.microservice.shortlink.mappers;

import com.microservice.shortlink.dtos.LinkDto;
import com.microservice.shortlink.entities.Link;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LinkMapper {
    LinkDto toDto(Link link);
}
