package pl.gr.veterinaryapp.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import pl.gr.veterinaryapp.mapper.PetMapper;
import pl.gr.veterinaryapp.model.dto.MessageDto;
import pl.gr.veterinaryapp.model.dto.PetRequestDto;
import pl.gr.veterinaryapp.model.dto.PetResponseDto;
import pl.gr.veterinaryapp.service.PetService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RequestMapping("api/pets")
@RestController
public class PetRestController {

    private final PetService petService;
    private final PetMapper mapper;

    @DeleteMapping(path = "/{id}")
    public MessageDto delete(@PathVariable int id) {
        return petService.deletePet(id);
    }

    @GetMapping(path = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public PetResponseDto getPet(@AuthenticationPrincipal User user, @PathVariable long id) {
        PetResponseDto pet = mapper.map(petService.getPetById(user, id));
        addLinks(pet);
        return pet;
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public List<PetResponseDto> getAllPets(@AuthenticationPrincipal User user) {
        List<PetResponseDto> pets = mapper.mapAsList(petService.getAllPets(user));

        for (PetResponseDto pet : pets) {
            addLinks(pet);
            var link = linkTo(methodOn(PetRestController.class).getPet(user, pet.getId()))
                    .withSelfRel();
            pet.add(link);
        }

        return pets;
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public PetResponseDto createPet(@AuthenticationPrincipal User user, @RequestBody PetRequestDto petRequestDto) {
        System.out.println(user);

        PetResponseDto pet = mapper.map(petService.createPet(user, petRequestDto));
        addLinks(pet);
        return pet;
    }

    public Link createClientLink(long id) {
        return linkTo(methodOn(ClientRestController.class).getClient(id))
                .withRel("client");
    }

    public Link createAnimalLink(long id) {
        return linkTo(methodOn(AnimalRestController.class).getAnimal(id))
                .withRel("animal");
    }

    public void addLinks(PetResponseDto petResponseDto) {
        petResponseDto.add(createAnimalLink(petResponseDto.getAnimalId()),
                createClientLink(petResponseDto.getClientId()));
    }
}
