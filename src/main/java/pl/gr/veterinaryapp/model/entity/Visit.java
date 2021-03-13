package pl.gr.veterinaryapp.model.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import pl.gr.veterinaryapp.common.OperationType;
import pl.gr.veterinaryapp.common.VisitType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "visits")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;
    @ManyToOne
    @JoinColumn(name = "vet_id", referencedColumnName = "id", nullable = false)
    private Vet vet;
    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false)
    private Animal animal;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private BigDecimal price;
    @NotNull
    private VisitType visitType;
    private OperationType operationType;
}
