package pl.gr.veterinaryapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.gr.veterinaryapp.exception.FailedOperationException;
import pl.gr.veterinaryapp.model.dto.VisitRequestDto;
import pl.gr.veterinaryapp.model.entity.Animal;
import pl.gr.veterinaryapp.model.entity.Vet;
import pl.gr.veterinaryapp.model.entity.Visit;
import pl.gr.veterinaryapp.repository.AnimalRepository;
import pl.gr.veterinaryapp.repository.VetRepository;
import pl.gr.veterinaryapp.repository.VisitRepository;
import pl.gr.veterinaryapp.service.VisitService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final VetRepository vetRepository;
    private final AnimalRepository animalRepository;


    public void deleteVisitById(long id) {
        Visit result = visitRepository.findById(id)
                .orElseThrow(() -> new FailedOperationException("Wrong id.", HttpStatus.NOT_FOUND));
        visitRepository.delete(result);
    }

    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    public Visit createVisit(VisitRequestDto visitRequestDto) {
        var startDateTime = LocalDateTime.of(visitRequestDto.getStartDate(), visitRequestDto.getStartTime());
        var endDateTime = LocalDateTime.of(visitRequestDto.getEndDate(), visitRequestDto.getEndTime());
        if (startDateTime.isAfter(endDateTime)) {
            throw new FailedOperationException("Start date and time after end date and time.", HttpStatus.BAD_REQUEST);
        }

        var vetId = visitRequestDto.getVetId();
        var startDate = visitRequestDto.getStartDate();
        var startTime = visitRequestDto.getStartTime();
        var endTime = visitRequestDto.getEndTime();

        if (visitRepository.overlaps(vetId, startDate, startTime, endTime).size() != 0) {
            throw new FailedOperationException("This date is not available.", HttpStatus.BAD_REQUEST);
        }

        Animal animal = animalRepository.findById(visitRequestDto.getAnimalId())
                .orElseThrow(() -> new FailedOperationException("Wrong animal id.", HttpStatus.BAD_REQUEST));
        Vet vet = vetRepository.findById(vetId)
                .orElseThrow(() -> new FailedOperationException("Wrong vet id.", HttpStatus.BAD_REQUEST));

        var newVisit = new Visit();
        newVisit.setAnimal(animal);
        newVisit.setVet(vet);
        newVisit.setStartDate(visitRequestDto.getStartDate());
        newVisit.setEndDate(visitRequestDto.getEndDate());
        newVisit.setStartTime(visitRequestDto.getStartTime());
        newVisit.setEndTime(visitRequestDto.getEndTime());
        newVisit.setPrice(visitRequestDto.getPrice());
        newVisit.setVisitType(visitRequestDto.getVisitType());
        newVisit.setOperationType(visitRequestDto.getOperationType());

        return visitRepository.save(newVisit);
    }
}
