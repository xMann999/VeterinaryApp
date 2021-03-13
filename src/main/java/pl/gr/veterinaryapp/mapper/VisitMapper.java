package pl.gr.veterinaryapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.gr.veterinaryapp.model.dto.VisitResponseDto;
import pl.gr.veterinaryapp.model.entity.Visit;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VisitMapper {

    @Mappings({
            @Mapping(source = "vet.id", target = "vetId"),
            @Mapping(source = "animal.id", target = "animalId")
    })
    VisitResponseDto map(Visit visit);

    @Mappings({
            @Mapping(source = "vet.id", target = "vetId"),
            @Mapping(source = "animal.id", target = "animalId")
    })
    List<VisitResponseDto> mapAsList(Collection<Visit> visit);
}
