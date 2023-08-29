package pl.gr.veterinaryapp.service;

import pl.gr.veterinaryapp.model.dto.ReportDto;

import java.time.YearMonth;

public interface ReportService {

    ReportDto getReport(YearMonth yearMonth);
}
