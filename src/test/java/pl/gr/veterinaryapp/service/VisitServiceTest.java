package pl.gr.veterinaryapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import pl.gr.veterinaryapp.common.OperationType;
import pl.gr.veterinaryapp.common.VisitType;
import pl.gr.veterinaryapp.exception.FailedOperationException;
import pl.gr.veterinaryapp.model.dto.VisitRequestDto;
import pl.gr.veterinaryapp.model.entity.Animal;
import pl.gr.veterinaryapp.model.entity.Vet;
import pl.gr.veterinaryapp.model.entity.Visit;
import pl.gr.veterinaryapp.repository.AnimalRepository;
import pl.gr.veterinaryapp.repository.VetRepository;
import pl.gr.veterinaryapp.repository.VisitRepository;
import pl.gr.veterinaryapp.service.impl.VisitServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {

    @Mock
    private VisitRepository visitRepository;
    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private VetRepository vetRepository;

    @InjectMocks
    private VisitServiceImpl visitService;

    private static final long VISIT_ID = 1L;
    private static final long ANIMAL_ID = 1L;
    private static final long VET_ID = 1L;


    @Test
    void deleteVisitById_WithCorrectId_Deleted() {
        Visit visit = new Visit();

        when(visitRepository.findById(VISIT_ID)).thenReturn(Optional.of(visit));

        visitService.deleteVisitById(VISIT_ID);

        verify(visitRepository).findById(eq(VISIT_ID));
        verify(visitRepository).delete(eq(visit));
        verifyNoMoreInteractions(visitRepository, animalRepository, vetRepository);
    }

    @Test
    void deleteVisit_VisitNotFound_ThrownException() {

        when(visitRepository.findById(VISIT_ID)).thenReturn(Optional.empty());

        FailedOperationException thrown =
                catchThrowableOfType(() -> visitService.deleteVisitById(VISIT_ID), FailedOperationException.class);

        assertThat(thrown)
                .hasMessage("Wrong id.");
        assertThat(thrown.getHttpStatus())
                .isNotNull()
                .isEqualTo(HttpStatus.NOT_FOUND);

        verify(visitRepository).findById(eq(VISIT_ID));
        verifyNoMoreInteractions(visitRepository, animalRepository, vetRepository);
    }

    @Test
    void getAllVisits_ReturnVisitsList_Returned() {
        List<Visit> visits = new ArrayList<>();

        when(visitRepository.findAll()).thenReturn(visits);

        var result = visitService.getAllVisits();

        assertThat(result)
                .isNotNull()
                .isEqualTo(visits);

        verify(visitRepository).findAll();
        verifyNoMoreInteractions(visitRepository);
        verifyNoInteractions(animalRepository, vetRepository);
    }

    @Test
    void createVisit_WithCorrectData_Created() {
        List<Visit> visits = new ArrayList<>();
        Animal animal = new Animal();
        animal.setId(ANIMAL_ID);
        Vet vet = new Vet();
        vet.setId(VET_ID);

        LocalDate startDate = LocalDate.of(2020, 5, 3);
        LocalDate endDate = LocalDate.of(2020, 5, 3);
        LocalTime endTime = LocalTime.of(13, 30);
        LocalTime startTime = LocalTime.of(13, 30);

        VisitRequestDto request = prepareVisitRequestDto(endDate, startDate, startTime, endTime);

        Visit visit = new Visit();
        visit.setAnimal(animal);
        visit.setVet(vet);
        visit.setStartTime(request.getStartTime());
        visit.setEndTime(request.getEndTime());
        visit.setStartDate(request.getStartDate());
        visit.setEndDate(request.getEndDate());
        visit.setPrice(request.getPrice());
        visit.setOperationType(request.getOperationType());
        visit.setVisitType(request.getVisitType());
        visit.setPrice(request.getPrice());
        visit.setId(VISIT_ID);

        when(visitRepository.overlaps
                (anyLong(), any(LocalDate.class), any(LocalTime.class), any(LocalTime.class)))
                .thenReturn(visits);
        when(animalRepository.findById(anyLong())).thenReturn(Optional.of(animal));
        when(vetRepository.findById(anyLong())).thenReturn(Optional.of(vet));
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        var result = visitService.createVisit(request);

        assertThat(result)
                .isNotNull()
                .isEqualTo(visit);

        verify(vetRepository).findById(eq(VET_ID));
        verify(animalRepository).findById(eq(ANIMAL_ID));
        verify(visitRepository).overlaps(eq(VET_ID), eq(request.getStartDate()), eq(request.getStartTime()), eq(request.getEndTime()));
        verifyNoMoreInteractions(animalRepository, vetRepository, visitRepository);
    }

    @Test
    void createVisit_WithWrongEndDate_ExceptionThrown() {
        Animal animal = new Animal();
        animal.setId(ANIMAL_ID);
        Vet vet = new Vet();
        vet.setId(VET_ID);

        LocalDate startDate = LocalDate.of(2020, 6, 3);
        LocalDate endDate = LocalDate.of(2020, 5, 3);
        LocalTime endTime = LocalTime.of(13, 30);
        LocalTime startTime = LocalTime.of(13, 30);

        VisitRequestDto request = prepareVisitRequestDto(endDate, startDate, startTime, endTime);

        FailedOperationException thrown =
                catchThrowableOfType(() -> visitService.createVisit(request), FailedOperationException.class);

        assertThat(thrown)
                .hasMessage("Start date and time after end date and time.");
        assertThat(thrown.getHttpStatus())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);

        verifyNoInteractions(animalRepository, vetRepository, visitRepository);
    }

    @Test
    void createVisit_WithOverlappingDate_ExceptionThrown() {
        List<Visit> visits = new ArrayList<>();
        visits.add(new Visit());
        Animal animal = new Animal();
        animal.setId(ANIMAL_ID);
        Vet vet = new Vet();
        vet.setId(VET_ID);

        LocalDate startDate = LocalDate.of(2020, 5, 3);
        LocalDate endDate = LocalDate.of(2020, 5, 3);
        LocalTime endTime = LocalTime.of(13, 30);
        LocalTime startTime = LocalTime.of(13, 30);

        VisitRequestDto request = prepareVisitRequestDto(endDate, startDate, startTime, endTime);

        when(visitRepository.overlaps
                (anyLong(), any(LocalDate.class), any(LocalTime.class), any(LocalTime.class)))
                .thenReturn(visits);

        FailedOperationException thrown =
                catchThrowableOfType(() -> visitService.createVisit(request), FailedOperationException.class);

        assertThat(thrown)
                .hasMessage("This date is not available.");
        assertThat(thrown.getHttpStatus())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);

        verify(visitRepository).overlaps(eq(VET_ID), eq(startDate), eq(startTime), eq(endTime));
        verifyNoMoreInteractions(visitRepository);
        verifyNoInteractions(animalRepository, vetRepository);
    }

    @Test
    void createVisit_WithWrongAnimalId_ExceptionThrown() {
        List<Visit> visits = new ArrayList<>();
        Animal animal = new Animal();
        animal.setId(ANIMAL_ID);
        Vet vet = new Vet();
        vet.setId(VET_ID);

        LocalDate startDate = LocalDate.of(2020, 5, 3);
        LocalDate endDate = LocalDate.of(2020, 5, 3);
        LocalTime endTime = LocalTime.of(13, 30);
        LocalTime startTime = LocalTime.of(13, 30);

        VisitRequestDto request = prepareVisitRequestDto(endDate, startDate, startTime, endTime);

        when(visitRepository.overlaps
                (anyLong(), any(LocalDate.class), any(LocalTime.class), any(LocalTime.class)))
                .thenReturn(visits);
        when(animalRepository.findById(ANIMAL_ID)).thenReturn(Optional.empty());

        FailedOperationException thrown =
                catchThrowableOfType(() -> visitService.createVisit(request), FailedOperationException.class);

        assertThat(thrown)
                .hasMessage("Wrong animal id.");
        assertThat(thrown.getHttpStatus())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);

        verify(animalRepository).findById(eq(ANIMAL_ID));
        verify(visitRepository).overlaps(eq(VET_ID), eq(request.getStartDate()), eq(request.getStartTime()), eq(request.getEndTime()));
        verifyNoMoreInteractions(animalRepository, visitRepository);
        verifyNoInteractions(vetRepository);
    }

    @Test
    void createVisit_WithWrongVetId_ExceptionThrown() {
        List<Visit> visits = new ArrayList<>();
        Animal animal = new Animal();
        animal.setId(ANIMAL_ID);
        Vet vet = new Vet();
        vet.setId(VET_ID);

        LocalDate startDate = LocalDate.of(2020, 5, 3);
        LocalDate endDate = LocalDate.of(2020, 5, 3);
        LocalTime endTime = LocalTime.of(13, 30);
        LocalTime startTime = LocalTime.of(13, 30);

        VisitRequestDto request = prepareVisitRequestDto(endDate, startDate, startTime, endTime);

        when(visitRepository.overlaps
                (anyLong(), any(LocalDate.class), any(LocalTime.class), any(LocalTime.class)))
                .thenReturn(visits);
        when(animalRepository.findById(ANIMAL_ID)).thenReturn(Optional.of(animal));
        when(vetRepository.findById(VET_ID)).thenReturn(Optional.empty());

        FailedOperationException thrown =
                catchThrowableOfType(() -> visitService.createVisit(request), FailedOperationException.class);

        assertThat(thrown)
                .hasMessage("Wrong vet id.");
        assertThat(thrown.getHttpStatus())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);

        verify(animalRepository).findById(eq(ANIMAL_ID));
        verify(visitRepository).overlaps(eq(VET_ID), eq(request.getStartDate()), eq(request.getStartTime()), eq(request.getEndTime()));
        verify(vetRepository).findById(eq(VET_ID));
        verifyNoMoreInteractions(vetRepository, visitRepository, animalRepository);
    }

    private VisitRequestDto prepareVisitRequestDto
            (LocalDate endDate, LocalDate startDate, LocalTime startTime, LocalTime endTime) {
        VisitRequestDto request = new VisitRequestDto();
        request.setAnimalId(ANIMAL_ID);
        request.setEndDate(endDate);
        request.setStartDate(startDate);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        request.setOperationType(OperationType.OPERATION);
        request.setVisitType(VisitType.REMOTE);
        request.setPrice(BigDecimal.ONE);
        request.setVetId(VET_ID);
        return request;
    }
}
