package pl.gr.veterinaryapp.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.gr.veterinaryapp.model.dto.VisitResponseDto;
import pl.gr.veterinaryapp.model.entity.Animal;
import pl.gr.veterinaryapp.model.entity.Vet;
import pl.gr.veterinaryapp.model.entity.Visit;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-13T15:50:12+0100",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.8.2.jar, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class VisitMapperImpl implements VisitMapper {

    @Override
    public VisitResponseDto map(Visit visit) {
        if ( visit == null ) {
            return null;
        }

        VisitResponseDto visitResponseDto = new VisitResponseDto();

        Long id = visitVetId( visit );
        if ( id != null ) {
            visitResponseDto.setVetId( id );
        }
        Long id1 = visitAnimalId( visit );
        if ( id1 != null ) {
            visitResponseDto.setAnimalId( id1 );
        }
        if ( visit.getId() != null ) {
            visitResponseDto.setId( visit.getId() );
        }
        visitResponseDto.setStartTime( visit.getStartTime() );
        visitResponseDto.setEndTime( visit.getEndTime() );
        visitResponseDto.setStartDate( visit.getStartDate() );
        visitResponseDto.setEndDate( visit.getEndDate() );
        visitResponseDto.setPrice( visit.getPrice() );
        visitResponseDto.setVisitType( visit.getVisitType() );
        visitResponseDto.setOperationType( visit.getOperationType() );

        return visitResponseDto;
    }

    @Override
    public List<VisitResponseDto> mapAsList(Collection<Visit> visit) {
        if ( visit == null ) {
            return null;
        }

        List<VisitResponseDto> list = new ArrayList<VisitResponseDto>( visit.size() );
        for ( Visit visit1 : visit ) {
            list.add( map( visit1 ) );
        }

        return list;
    }

    private Long visitVetId(Visit visit) {
        if ( visit == null ) {
            return null;
        }
        Vet vet = visit.getVet();
        if ( vet == null ) {
            return null;
        }
        Long id = vet.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long visitAnimalId(Visit visit) {
        if ( visit == null ) {
            return null;
        }
        Animal animal = visit.getAnimal();
        if ( animal == null ) {
            return null;
        }
        Long id = animal.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
