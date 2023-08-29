package pl.gr.veterinaryapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gr.veterinaryapp.exception.IncorrectDataException;
import pl.gr.veterinaryapp.exception.ResourceNotFoundException;
import pl.gr.veterinaryapp.mapper.VetMapper;
import pl.gr.veterinaryapp.model.dto.MessageDto;
import pl.gr.veterinaryapp.model.dto.VetRequestDto;
import pl.gr.veterinaryapp.model.dto.VetResponseDto;
import pl.gr.veterinaryapp.model.entity.Vet;
import pl.gr.veterinaryapp.repository.VetRepository;
import pl.gr.veterinaryapp.service.VetService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final VetMapper mapper;

    @Override
    public VetResponseDto getVetById(long id) {
        log.debug("Searching for vet with id: {}", id);
        return mapper.toResponse(vetRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Wrong id.")));
    }

    @Override
    public List<VetResponseDto> getAllVets() {
        log.trace("Fetching all vets");
        return vetRepository.findAll().stream()
                .map(vet -> mapper.toResponse(vet))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public VetResponseDto createVet(VetRequestDto vetRequestDTO) {
        log.debug("Checking if all fields are filled");
        if (vetRequestDTO.getSurname() == null || vetRequestDTO.getName() == null) {
            throw new IncorrectDataException("Name and Surname cannot be null.");
        }
        log.debug("Saving a new vet: {}", vetRequestDTO);
        return mapper.toResponse(vetRepository.save(mapper.toEntity(vetRequestDTO)));
    }

    @Transactional
    @Override
    public MessageDto deleteVet(long id) {
        log.debug("Searching for vet with id: {}", id);
        Vet result = vetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wrong id."));
        vetRepository.delete(result);
        log.debug("Attempting to delete vet with id: {}", id);
        return new MessageDto(HttpStatus.OK, "Vet has been successfully removed");
    }
}
