package pl.gr.veterinaryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.gr.veterinaryapp.common.VisitStatus;
import pl.gr.veterinaryapp.model.entity.Visit;

import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("select v " +
            "from Visit v " +
            "where :vetId = v.vet.id " +
            "and v.startDateTime  <= :endDateTime " +
            "and (v.startDateTime + v.duration) >= :startDateTime")
    List<Visit> findAllOverlapping(long vetId, OffsetDateTime startDateTime, OffsetDateTime endDateTime);

    @Query("select v from Visit v where " +
            "(v.startDateTime + v.duration) <= :now " +
            "and v.visitStatus = :visitStatus")
    List<Visit> findAllByEndDateAndEndTimeBeforeAndVisitStatus(OffsetDateTime now, VisitStatus visitStatus);

    @Query("select v from Visit v where " +
            "v.startDateTime >= :startDateTime " +
            "and (v.startDateTime + v.duration) <= :endDateTime " +
            "and v.vet.id in :vetIds")
    List<Visit> findAllInDateTimeRangeAndVetIdIn(
            OffsetDateTime startDateTime,
            OffsetDateTime endDateTime,
            Collection<Long> vetIds);

    @Query("select v from Visit v where " +
            "v.startDateTime  <= :endDateTime " +
            "and (v.startDateTime + v.duration) >= :startDateTime")
    List<Visit> findAllOverlappingInDateRange(
            OffsetDateTime startDateTime,
            OffsetDateTime endDateTime);

    @Query("SELECT v FROM Visit v WHERE EXTRACT(YEAR FROM v.startDateTime) = ?1 AND EXTRACT(MONTH FROM v.startDateTime) = ?2")
    List<Visit> findByYearAndMonth(int year, int month);

    default List<Visit> findByYearAndMonth(YearMonth yearMonth) {
        return findByYearAndMonth(yearMonth.getYear(), yearMonth.getMonthValue());
    }

    @Query("SELECT v FROM Visit v WHERE EXTRACT(YEAR FROM v.startDateTime) = ?1 AND EXTRACT(MONTH FROM v.startDateTime) = ?2 AND v.vet.id = ?3")
    List<Visit> findByYearMonthAndVet(int year, int month, Long vetId);

    default List<Visit> findByYearMonthAndVet(YearMonth yearMonth, Long vetId) {
        return findByYearMonthAndVet(yearMonth.getYear(), yearMonth.getMonthValue(), vetId);
    }
}
