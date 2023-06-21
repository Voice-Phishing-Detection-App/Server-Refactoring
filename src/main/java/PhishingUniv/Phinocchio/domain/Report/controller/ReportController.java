package PhishingUniv.Phinocchio.domain.Report.controller;

import PhishingUniv.Phinocchio.domain.Report.dto.ReportDto;
import PhishingUniv.Phinocchio.domain.Report.dto.ReportWithoutDoubtDto;
import PhishingUniv.Phinocchio.domain.Report.dto.SearchRequestDto;
import PhishingUniv.Phinocchio.domain.Report.dto.SearchResponseDto;
import PhishingUniv.Phinocchio.domain.Report.entity.ReportEntity;
import PhishingUniv.Phinocchio.domain.Report.service.ReportService;
import PhishingUniv.Phinocchio.domain.Report.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final SearchService searchService;

    @PostMapping("/add")
    public ResponseEntity<ReportEntity> addReport(@RequestBody ReportDto reportDto)
    //2023-06-10 16:34:21.421  WARN 14608 --- [nio-8080-exec-7] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot deserialize value of type `PhishingUniv.Phinocchio.domain.Report.entity.ReportType` from String "REPORT_TYPE_FRAUsD": not one of the values accepted for Enum class: [REPORT_TYPE_DISGUISE, REPORT_TYPE_FRAUD, REPORT_TYPE_IMPERSONATING, REPORT_TYPE_INDUCE]; nested exception is com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize value of type `PhishingUniv.Phinocchio.domain.Report.entity.ReportType` from String "REPORT_TYPE_FRAUsD": not one of the values accepted for Enum class: [REPORT_TYPE_DISGUISE, REPORT_TYPE_FRAUD, REPORT_TYPE_IMPERSONATING, REPORT_TYPE_INDUCE]<EOL> at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 2, column: 10] (through reference chain: PhishingUniv.Phinocchio.domain.Report.dto.ReportDto["type"])]
    //@RequestBody Enum Parsing 오류 에외처리하기
    {
        ReportEntity reportEntity = reportService.addReport(reportDto);

        return ResponseEntity.ok(reportEntity);


    }
    @PostMapping("/add/withoutDoubt")
    public ResponseEntity<ReportEntity> addReportWithoutDoubt(@RequestBody ReportWithoutDoubtDto reportDto)
    //2023-06-10 16:34:21.421  WARN 14608 --- [nio-8080-exec-7] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot deserialize value of type `PhishingUniv.Phinocchio.domain.Report.entity.ReportType` from String "REPORT_TYPE_FRAUsD": not one of the values accepted for Enum class: [REPORT_TYPE_DISGUISE, REPORT_TYPE_FRAUD, REPORT_TYPE_IMPERSONATING, REPORT_TYPE_INDUCE]; nested exception is com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize value of type `PhishingUniv.Phinocchio.domain.Report.entity.ReportType` from String "REPORT_TYPE_FRAUsD": not one of the values accepted for Enum class: [REPORT_TYPE_DISGUISE, REPORT_TYPE_FRAUD, REPORT_TYPE_IMPERSONATING, REPORT_TYPE_INDUCE]<EOL> at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 2, column: 10] (through reference chain: PhishingUniv.Phinocchio.domain.Report.dto.ReportDto["type"])]
    //@RequestBody Enum Parsing 오류 에외처리하기
    {
        ReportEntity reportEntity = reportService.addReportWithoutDoubt(reportDto);

        return ResponseEntity.ok(reportEntity);


    }

    @GetMapping("/get")
    public List<ReportEntity> getReports(){
        return reportService.getReports();

    }

    @PostMapping("/search")
    public ResponseEntity<SearchResponseDto> getReportCount(@RequestBody SearchRequestDto searchRequestDto){
        SearchResponseDto searchResponseDto = searchService.getReportCount(searchRequestDto.getPhoneNumber());

        return ResponseEntity.ok(searchResponseDto);
    }

    @PostMapping("/searchList")
    public List<ReportEntity> getReportDetail(@RequestBody SearchRequestDto searchRequestDto){
        return searchService.getReportDetail(searchRequestDto.getPhoneNumber());
    }
}
