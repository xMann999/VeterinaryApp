package pl.gr.veterinaryapp.factory;

import org.flywaydb.core.internal.util.Pair;
import org.springframework.stereotype.Component;
import pl.gr.veterinaryapp.common.VisitStatus;
import pl.gr.veterinaryapp.model.dto.ReportDto;
import pl.gr.veterinaryapp.model.entity.Vet;
import pl.gr.veterinaryapp.model.entity.Visit;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportFactory {

    private ReportFactory() {
    }

    public ReportDto create(List<Visit> visits, List<Vet> vets) {
        return ReportDto.builder()
                .income(visits.stream()
                        .filter(visit -> !visit.getVisitStatus().equals(VisitStatus.CANCELLED))
                        .map(Visit::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .numberOfVisits(visits.size())
                .numberOfScheduledVisits((int) visits.stream()
                        .filter(visit -> visit.getVisitStatus().equals(VisitStatus.SCHEDULED)).count())
                .numberOfVisitsForEachVet(vets.stream()
                        .map(vet -> Pair.of(vet.getNameAndSurname(), (int) visits.stream()
                                .filter(visit -> visit.getId().equals(vet.getId())).count()))
                        .collect(Collectors.toList()))
                .build();
    }
}
