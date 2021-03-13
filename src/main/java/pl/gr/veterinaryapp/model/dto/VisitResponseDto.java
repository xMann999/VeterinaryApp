package pl.gr.veterinaryapp.model.dto;

import lombok.Data;
import pl.gr.veterinaryapp.common.OperationType;
import pl.gr.veterinaryapp.common.VisitType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class VisitResponseDto {
    private long id;
    private long vetId;
    private long animalId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private VisitType visitType;
    private OperationType operationType;
}
