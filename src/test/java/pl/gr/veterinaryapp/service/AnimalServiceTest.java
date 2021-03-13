package pl.gr.veterinaryapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import pl.gr.veterinaryapp.exception.FailedOperationException;
import pl.gr.veterinaryapp.mapper.AnimalMapper;
import pl.gr.veterinaryapp.model.dto.AnimalRequestDto;
import pl.gr.veterinaryapp.model.entity.Animal;
import pl.gr.veterinaryapp.repository.AnimalRepository;
import pl.gr.veterinaryapp.service.impl.AnimalServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private AnimalMapper mapper;

    @InjectMocks
    private AnimalServiceImpl animalService;


    private static final long ANIMAL_ID = 1L;

    @Test
    void getAnimalById_WithCorrectId_Returned() {
        Animal animal = new Animal();

        when(animalRepository.findById(anyLong())).thenReturn(Optional.of(animal));

        var result = animalService.getAnimalById(ANIMAL_ID);

        assertThat(result)
                .isNotNull()
                .isEqualTo(animal);

        verify(animalRepository).findById(eq(ANIMAL_ID));
        verifyNoMoreInteractions(animalRepository);
        verifyNoInteractions(mapper);
    }

    @Test
    void getAnimalById_WithWrongId_ExceptionThrown() {
        when(animalRepository.findById(anyLong())).thenReturn(Optional.empty());

        FailedOperationException thrown =
                catchThrowableOfType(() -> animalService.getAnimalById(ANIMAL_ID), FailedOperationException.class);

        assertThat(thrown)
                .hasMessage("Wrong id.");
        assertThat(thrown.getHttpStatus())
                .isNotNull()
                .isEqualTo(HttpStatus.NOT_FOUND);

//        assertThatThrownBy(() -> animalService.getAnimalById(ANIMAL_ID))
//                .isInstanceOf(FailedOperationException.class)
//                .hasMessage("Wrong id.")
//                .matches(exception -> ((FailedOperationException) exception).getHttpStatus() == HttpStatus.NOT_FOUND);

        verify(animalRepository).findById(eq(ANIMAL_ID));
        verifyNoMoreInteractions(animalRepository);
        verifyNoInteractions(mapper);
    }

    @Test
    void createAnimal_NewAnimal_Created() {
        AnimalRequestDto animalDTO = new AnimalRequestDto();
        animalDTO.setSpecies("test");
        Animal animal = new Animal();
        animal.setSpecies("test");

        when(mapper.map(any(AnimalRequestDto.class))).thenReturn(animal);
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);
        when(animalRepository.findBySpecies(anyString())).thenReturn(Optional.empty());

        var result = animalService.createAnimal(animalDTO);

        assertThat(result)
                .isNotNull()
                .isEqualTo(animal);

        verify(animalRepository).save(eq(animal));
        verify(animalRepository).findBySpecies(eq("test"));
        verify(mapper).map(eq(animalDTO));
        verifyNoMoreInteractions(animalRepository, mapper);
    }

    @Test
    void createAnimal_ExistsAnimal_ExceptionThrown() {
        AnimalRequestDto animalDTO = new AnimalRequestDto();
        animalDTO.setSpecies("test");
        Animal animal = new Animal();
        animal.setSpecies("test");

        when(animalRepository.findBySpecies(anyString())).thenReturn(Optional.of(animal));

        FailedOperationException thrown =
                catchThrowableOfType(() -> animalService.createAnimal(animalDTO), FailedOperationException.class);

        assertThat(thrown)
                .hasMessage("Species exists.");
        assertThat(thrown.getHttpStatus())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);

        verify(animalRepository).findBySpecies(eq("test"));
        verifyNoMoreInteractions(animalRepository);
        verifyNoInteractions(mapper);
    }

    @Test
    void deleteAnimal_ExistsAnimal_Deleted() {
        Animal animal = new Animal();

        when(animalRepository.findById(anyLong())).thenReturn(Optional.of(animal));

        animalService.deleteAnimal(ANIMAL_ID);

        verify(animalRepository).findById(eq(ANIMAL_ID));
        verify(animalRepository).delete(eq(animal));
        verifyNoMoreInteractions(animalRepository);
        verifyNoInteractions(mapper);
    }

    @Test
    void deleteAnimal_AnimalNotFound_ThrownException() {

        when(animalRepository.findById(anyLong())).thenReturn(Optional.empty());

        FailedOperationException thrown =
                catchThrowableOfType(() -> animalService.deleteAnimal(ANIMAL_ID), FailedOperationException.class);

        assertThat(thrown)
                .hasMessage("Wrong id.");
        assertThat(thrown.getHttpStatus())
                .isNotNull()
                .isEqualTo(HttpStatus.NOT_FOUND);

        verify(animalRepository).findById(eq(ANIMAL_ID));
        verifyNoMoreInteractions(animalRepository);
        verifyNoInteractions(mapper);
    }

    @Test
    void getAllAnimals_ReturnAnimals_Returned() {
        List<Animal> animals = new ArrayList<>();

        when(animalRepository.findAll()).thenReturn(animals);

        var result = animalService.getAllAnimals();

        assertThat(result)
                .isNotNull();

        verify(animalRepository).findAll();
        verifyNoMoreInteractions(animalRepository);
        verifyNoInteractions(mapper);
    }
}