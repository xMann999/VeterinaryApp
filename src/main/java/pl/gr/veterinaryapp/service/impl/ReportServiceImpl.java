package pl.gr.veterinaryapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.gr.veterinaryapp.factory.ReportFactory;
import pl.gr.veterinaryapp.model.dto.ReportDto;
import pl.gr.veterinaryapp.model.entity.Vet;
import pl.gr.veterinaryapp.model.entity.Visit;
import pl.gr.veterinaryapp.repository.VetRepository;
import pl.gr.veterinaryapp.repository.VisitRepository;
import pl.gr.veterinaryapp.service.ReportService;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final VisitRepository visitRepository;

    private final VetRepository vetRepository;

    private final ReportFactory reportFactory;

    public ReportDto getReport(YearMonth yearMonth) {
        log.debug("Fetching all visits for given month");
        List<Visit> allVisits = visitRepository.findByYearAndMonth(yearMonth);
        log.debug("Fetching all vets");
        List<Vet> allVets = vetRepository.findAll();
        log.debug("Generating report");
        return reportFactory.create(allVisits, allVets);
    }
}
