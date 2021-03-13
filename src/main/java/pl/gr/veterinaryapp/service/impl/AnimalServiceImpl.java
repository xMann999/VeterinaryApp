package pl.gr.veterinaryapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.gr.veterinaryapp.exception.FailedOperationException;
import pl.gr.veterinaryapp.mapper.AnimalMapper;
import pl.gr.veterinaryapp.model.dto.AnimalRequestDto;
import pl.gr.veterinaryapp.model.entity.Animal;
import pl.gr.veterinaryapp.repository.AnimalRepository;
import pl.gr.veterinaryapp.service.AnimalService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper mapper;

    public Animal getAnimalById(long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new FailedOperationException("Wrong id.", HttpStatus.NOT_FOUND));
    }

    public Animal createAnimal(AnimalRequestDto animalRequestDto) {
        var animal = animalRepository.findBySpecies(animalRequestDto.getSpecies());
//        animal.ifPresent(s -> {
//            throw new FailedOperationException("Species exists.", HttpStatus.BAD_REQUEST);});
        if (animal.isPresent()) {
            throw new FailedOperationException("Species exists.", HttpStatus.BAD_REQUEST);
        }
        return animalRepository.save(mapper.map(animalRequestDto));
    }

    public void deleteAnimal(long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new FailedOperationException("Wrong id.", HttpStatus.NOT_FOUND));
        animalRepository.delete(animal);
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }
}
