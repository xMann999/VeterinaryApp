package pl.gr.veterinaryapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gr.veterinaryapp.exception.IncorrectDataException;
import pl.gr.veterinaryapp.exception.ResourceNotFoundException;
import pl.gr.veterinaryapp.mapper.AnimalMapper;
import pl.gr.veterinaryapp.model.dto.AnimalDto;
import pl.gr.veterinaryapp.model.dto.MessageDto;
import pl.gr.veterinaryapp.model.entity.Animal;
import pl.gr.veterinaryapp.repository.AnimalRepository;
import pl.gr.veterinaryapp.service.AnimalService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper mapper;

    @Override
    public AnimalDto getAnimalById(long id) {
        log.debug("Searching for animal with id: {}", id);
        return mapper.toDto(animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wrong id.")));
    }

    @Transactional
    @Override
    public AnimalDto createAnimal(AnimalDto animalDto) {
        log.debug("Checking if the given data is correct");
        Optional<Animal> animal = animalRepository.findBySpecies(animalDto.getSpecies());
        if (animal.isPresent()) {
            throw new IncorrectDataException("Species exists.");
        }
        log.debug("Saving a new animal");
        animalRepository.save(mapper.toEntity(animalDto));
        return animalDto;
    }

    @Transactional
    @Override
    public MessageDto deleteAnimal(long id) {
        log.debug("Searching for animal with id: {}", id);
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wrong id."));
        log.debug("Attempting to delete animal with id: {}", id);
        animalRepository.delete(animal);
        return new MessageDto(HttpStatus.OK, "Resource has been successfully removed");
    }

    @Override
    public List<AnimalDto> getAllAnimals() {
        log.debug("Fetching all animals");
        return animalRepository.findAll().stream()
                .map(animal -> mapper.toDto(animal)).collect(Collectors.toList());
    }
}
