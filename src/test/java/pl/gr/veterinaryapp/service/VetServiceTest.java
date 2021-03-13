package pl.gr.veterinaryapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import pl.gr.veterinaryapp.exception.FailedOperationException;
import pl.gr.veterinaryapp.mapper.VetMapper;
import pl.gr.veterinaryapp.model.dto.VetRequestDto;
import pl.gr.veterinaryapp.model.entity.Vet;
import pl.gr.veterinaryapp.repository.VetRepository;
import pl.gr.veterinaryapp.service.impl.VetServiceImpl;

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
public class VetServiceTest {

    @Mock
    private VetRepository vetRepository;

    @Mock
    private VetMapper mapper;

    @InjectMocks
    private VetServiceImpl vetService;


    private static final long VET_ID = 1L;

    @Test
    void getVetById_WithCorrectId_Returned() {
        Vet vet = new Vet();

        when(vetRepository.findById(anyLong())).thenReturn(Optional.of(vet));

        var result = vetService.getVetById(VET_ID);

        assertThat(result)
                .isNotNull()
                .isEqualTo(vet);

        verify(vetRepository).findById(eq(VET_ID));
        verifyNoInteractions(mapper);
        verifyNoMoreInteractions(vetRepository);
    }

    @Test
    void getVetById_WithWrongId_ExceptionThrown() {

        when(vetRepository.findById(anyLong())).thenReturn(Optional.empty());

        FailedOperationException thrown =
                catchThrowableOfType(() -> vetService.getVetById(VET_ID), FailedOperationException.class);

        assertThat(thrown)
                .hasMessage("Wrong id.");
        assertThat(thrown.getHttpStatus())
                .isNotNull()
                .isEqualTo(HttpStatus.NOT_FOUND);

        verify(vetRepository).findById(eq(VET_ID));
        verifyNoInteractions(mapper);
        verifyNoMoreInteractions(vetRepository);
    }

    @Test
    void getAllVets_ReturnVets_Returned() {
        List<Vet> vets = new ArrayList<>();

        when(vetRepository.findAll()).thenReturn(vets);

        var result = vetService.getAllVets();

        assertThat(result)
                .isNotNull()
                .isEqualTo(vets);

        verify(vetRepository).findAll();
        verifyNoInteractions(mapper);
        verifyNoMoreInteractions(vetRepository);
    }

    @Test
    void saveVet_CorrectData_saved() {
        VetRequestDto request = new VetRequestDto();
        request.setName("test");
        request.setSurname("test");
        Vet vet = new Vet();
        vet.setName("test");
        vet.setSurname("test");

        when(mapper.map(any(VetRequestDto.class))).thenReturn(vet);
        when(vetRepository.save(any(Vet.class))).thenReturn(vet);

        var result = vetService.saveVet(request);

        assertThat(result)
                .isNotNull()
                .isEqualTo(vet);

        verify(vetRepository).save(eq(vet));
        verify(mapper).map(request);
        verifyNoMoreInteractions(mapper, vetRepository);
    }

    @Test
    void saveVet_NameNull_ExceptionThrown() {
        VetRequestDto request = new VetRequestDto();
        request.setSurname("test");

        FailedOperationException thrown =
                catchThrowableOfType(() -> vetService.saveVet(request), FailedOperationException.class);

        assertThat(thrown)
                .hasMessage("Name and Surname should not be null.");
        assertThat(thrown.getHttpStatus())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);

        verifyNoInteractions(mapper);
        verifyNoMoreInteractions(vetRepository);
    }

    @Test
    void saveVet_SurnameNull_ExceptionThrown() {
        VetRequestDto request = new VetRequestDto();
        request.setName("test");

        FailedOperationException thrown =
                catchThrowableOfType(() -> vetService.saveVet(request), FailedOperationException.class);

        assertThat(thrown)
                .hasMessage("Name and Surname should not be null.");
        assertThat(thrown.getHttpStatus())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);

        verifyNoInteractions(mapper, vetRepository);
    }

    @Test
    void deleteVet_ExistsVet_Deleted() {
        Vet vet = new Vet();

        when(vetRepository.findById(VET_ID)).thenReturn(Optional.of(vet));

        vetService.deleteVet(VET_ID);

        verify(vetRepository).findById(eq(VET_ID));
        verify(vetRepository).delete(eq(vet));
        verifyNoMoreInteractions(vetRepository);
        verifyNoInteractions(mapper);
    }

    @Test
    void deleteVet_VetNotFound_ThrownException() {
        when(vetRepository.findById(VET_ID)).thenReturn(Optional.empty());

        FailedOperationException thrown =
                catchThrowableOfType(() -> vetService.deleteVet(VET_ID), FailedOperationException.class);

        assertThat(thrown)
                .hasMessage("Wrong id.");
        assertThat(thrown.getHttpStatus())
                .isNotNull()
                .isEqualTo(HttpStatus.NOT_FOUND);

        verify(vetRepository).findById(eq(VET_ID));
        verifyNoMoreInteractions(vetRepository);
        verifyNoInteractions(mapper);
    }
}
