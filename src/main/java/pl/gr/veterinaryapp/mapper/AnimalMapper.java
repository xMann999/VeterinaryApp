package pl.gr.veterinaryapp.mapper;

import org.mapstruct.Mapper;
import pl.gr.veterinaryapp.model.dto.AnimalRequestDto;
import pl.gr.veterinaryapp.model.entity.Animal;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    Animal map(AnimalRequestDto animalRequestDto);
}
