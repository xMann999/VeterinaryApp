package pl.gr.veterinaryapp.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.gr.veterinaryapp.model.dto.AnimalRequestDto;
import pl.gr.veterinaryapp.model.entity.Animal;
import pl.gr.veterinaryapp.service.AnimalService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnimalRestController.class)
class AnimalRestControllerTest {

    @MockBean
    private AnimalService animalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addAnimal_CorrectData_Created() throws Exception {
        AnimalRequestDto animalRequest = new AnimalRequestDto();
        animalRequest.setSpecies("Cat");

        Animal animal = new Animal();
        animal.setSpecies(animalRequest.getSpecies());

        when(animalService.createAnimal(any(AnimalRequestDto.class))).thenReturn(animal);

        mockMvc.perform(post("/api/animals")
                .content(objectMapper.writeValueAsString(animalRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.species").value(animalRequest.getSpecies()));

        verify(animalService).createAnimal(animalRequest);
        verifyNoMoreInteractions(animalService);
    }
}
