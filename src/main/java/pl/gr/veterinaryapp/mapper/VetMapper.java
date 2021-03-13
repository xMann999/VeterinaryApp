package pl.gr.veterinaryapp.mapper;

import org.mapstruct.Mapper;
import pl.gr.veterinaryapp.model.dto.VetRequestDto;
import pl.gr.veterinaryapp.model.entity.Vet;

@Mapper(componentModel = "spring")
public interface VetMapper {

    Vet map(VetRequestDto animalRequestDto);
}
