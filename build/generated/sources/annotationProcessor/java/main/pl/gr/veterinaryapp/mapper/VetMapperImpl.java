package pl.gr.veterinaryapp.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.gr.veterinaryapp.model.dto.VetRequestDto;
import pl.gr.veterinaryapp.model.entity.Vet;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-13T15:50:12+0100",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.8.2.jar, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class VetMapperImpl implements VetMapper {

    @Override
    public Vet map(VetRequestDto animalRequestDto) {
        if ( animalRequestDto == null ) {
            return null;
        }

        Vet vet = new Vet();

        vet.setName( animalRequestDto.getName() );
        vet.setSurname( animalRequestDto.getSurname() );

        return vet;
    }
}
