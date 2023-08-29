package pl.gr.veterinaryapp.service;

import pl.gr.veterinaryapp.model.dto.AnimalDto;
import pl.gr.veterinaryapp.model.dto.MessageDto;
import pl.gr.veterinaryapp.model.entity.Animal;

import java.util.List;

public interface AnimalService {

    AnimalDto getAnimalById(long id);

    AnimalDto createAnimal(AnimalDto animalDTO);

    MessageDto deleteAnimal(long id);

    List<AnimalDto> getAllAnimals();
}
