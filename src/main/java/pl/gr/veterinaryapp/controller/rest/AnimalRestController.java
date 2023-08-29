package pl.gr.veterinaryapp.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gr.veterinaryapp.model.dto.AnimalDto;
import pl.gr.veterinaryapp.model.dto.MessageDto;
import pl.gr.veterinaryapp.model.entity.Animal;
import pl.gr.veterinaryapp.service.AnimalService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/animals")
public class AnimalRestController {

    private final AnimalService animalService;

    @GetMapping("/{id}")
    public AnimalDto getAnimal(@PathVariable long id) {
        return animalService.getAnimalById(id);
    }

    @PostMapping
    public AnimalDto createAnimal(@RequestBody AnimalDto animalDTO) {
        return animalService.createAnimal(animalDTO);
    }

    @DeleteMapping("/{id}")
    public MessageDto delete(@PathVariable long id) {
        return animalService.deleteAnimal(id);
    }

    @GetMapping
    public List<AnimalDto> getAllAnimals() {
        return animalService.getAllAnimals();
    }
}
