package pl.gr.veterinaryapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.gr.veterinaryapp.model.dto.AnimalDto;
import pl.gr.veterinaryapp.model.entity.Animal;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnimalMapper {

    Animal toEntity(AnimalDto animalDto);
    AnimalDto toDto(Animal animal);
}
