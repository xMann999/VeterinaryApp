package pl.gr.veterinaryapp.service;

import pl.gr.veterinaryapp.model.dto.VisitRequestDto;
import pl.gr.veterinaryapp.model.entity.Visit;

import java.util.List;

public interface VisitService {

    void deleteVisitById(long id);

    List<Visit> getAllVisits();

    Visit createVisit(VisitRequestDto visitRequestDto);
}
