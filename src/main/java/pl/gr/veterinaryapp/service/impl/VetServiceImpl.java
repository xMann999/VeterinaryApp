package pl.gr.veterinaryapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.gr.veterinaryapp.exception.FailedOperationException;
import pl.gr.veterinaryapp.mapper.VetMapper;
import pl.gr.veterinaryapp.model.dto.VetRequestDto;
import pl.gr.veterinaryapp.model.entity.Vet;
import pl.gr.veterinaryapp.repository.VetRepository;
import pl.gr.veterinaryapp.service.VetService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final VetMapper mapper;

    public Vet getVetById(long id) {
        return vetRepository.findById(id)
                .orElseThrow(() -> new FailedOperationException("Wrong id.", HttpStatus.NOT_FOUND));
    }

    public List<Vet> getAllVets() {
        return vetRepository.findAll();
    }

    public Vet saveVet(VetRequestDto vetRequestDTO) {
        if (vetRequestDTO.getSurname() == null || vetRequestDTO.getName() == null) {
            throw new FailedOperationException("Name and Surname should not be null.", HttpStatus.BAD_REQUEST);
        }
        return vetRepository.save(mapper.map(vetRequestDTO));
    }

    public void deleteVet(long id) {
        Vet result = vetRepository.findById(id)
                .orElseThrow(() -> new FailedOperationException("Wrong id.", HttpStatus.NOT_FOUND));
        vetRepository.delete(result);
    }
}
