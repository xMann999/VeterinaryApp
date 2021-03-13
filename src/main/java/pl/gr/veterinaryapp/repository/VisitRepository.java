package pl.gr.veterinaryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.gr.veterinaryapp.model.entity.Visit;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("select v " +
            "from Visit v " +
            "where (:startDate = v.startDate and :vetId = v.vet.id) " +
            "and ( (v.startTime < :endTime ) " +
            "and (v.endTime > :startTime))")
            List<Visit> overlaps(long vetId, LocalDate startDate, LocalTime startTime, LocalTime endTime);


}
