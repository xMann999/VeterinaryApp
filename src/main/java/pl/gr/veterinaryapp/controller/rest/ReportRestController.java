package pl.gr.veterinaryapp.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.gr.veterinaryapp.model.dto.ReportDto;
import pl.gr.veterinaryapp.service.ReportService;

import java.time.YearMonth;

@RestController
@RequiredArgsConstructor
public class ReportRestController {

    private final ReportService reportService;

    @GetMapping("/api/report")
    ReportDto getReport(@RequestBody YearMonth yearMonth) {
        return reportService.getReport(yearMonth);
    }
}
