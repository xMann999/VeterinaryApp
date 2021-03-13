package pl.gr.veterinaryapp.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gr.veterinaryapp.mapper.VisitMapper;
import pl.gr.veterinaryapp.model.dto.VisitRequestDto;
import pl.gr.veterinaryapp.model.dto.VisitResponseDto;
import pl.gr.veterinaryapp.service.VisitService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/visits")
@RestController
public class VisitRestController {

    private final VisitService visitService;
    private final VisitMapper mapper;

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        visitService.deleteVisitById(id);
    }

    @GetMapping
    public List<VisitResponseDto> getAllVisits() {
        var visits = visitService.getAllVisits();
        return mapper.mapAsList(visits);
    }

    @PostMapping
    public VisitResponseDto createVisit(@RequestBody VisitRequestDto visitRequestDto) {
        var visit = visitService.createVisit(visitRequestDto);
        return mapper.map(visit);
    }
}
