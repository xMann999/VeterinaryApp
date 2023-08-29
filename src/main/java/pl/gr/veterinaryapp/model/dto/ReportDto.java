package pl.gr.veterinaryapp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.flywaydb.core.internal.util.Pair;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ReportDto {

    private BigDecimal income;
    @JsonProperty(value = "number_of_visits")
    private int numberOfVisits;
    @JsonProperty(value = "number_of_scheduled_visits")
    private int numberOfScheduledVisits;
    @JsonProperty(value = "number_of_visits_for_each_vet")
    private List<Pair<String, Integer>> numberOfVisitsForEachVet;
}
