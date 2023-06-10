package PhishingUniv.Phinocchio.domain.Report.controller;

import PhishingUniv.Phinocchio.domain.Report.dto.ReportDto;
import PhishingUniv.Phinocchio.domain.Report.entity.ReportEntity;
import PhishingUniv.Phinocchio.domain.Report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    //private final SearchService searchService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<ReportEntity> addReport(@RequestBody ReportDto reportDto)
    {
        ReportEntity reportEntity = reportService.addReport(reportDto);

        return ResponseEntity.ok(reportEntity);


    }

}
