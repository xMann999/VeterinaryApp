package pl.gr.veterinaryapp.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.gr.veterinaryapp.model.dto.AnimalRequestDto;
import pl.gr.veterinaryapp.model.entity.Animal;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-13T15:50:12+0100",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.8.2.jar, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class AnimalMapperImpl implements AnimalMapper {

    @Override
    public Animal map(AnimalRequestDto animalRequestDto) {
        if ( animalRequestDto == null ) {
            return null;
        }

        Animal animal = new Animal();

        animal.setSpecies( animalRequestDto.getSpecies() );

        return animal;
    }
}
