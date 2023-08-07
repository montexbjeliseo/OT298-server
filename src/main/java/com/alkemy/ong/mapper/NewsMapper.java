package com.alkemy.ong.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.alkemy.ong.model.News;
import com.alkemy.ong.dto.NewsDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    NewsDto toDto(News news);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    News toEntity(NewsDto newsDto);

    News updateNewsFromDto(NewsDto dto, @MappingTarget News news);

    List<NewsDto> listNewsToNewsDto(List<News> news);

}