package PhishingUniv.Phinocchio.domain.Report.controller;

import PhishingUniv.Phinocchio.domain.Report.dto.ReportDto;
import PhishingUniv.Phinocchio.domain.Report.dto.ReportWithoutDoubtDto;
import PhishingUniv.Phinocchio.domain.Report.dto.SearchRequestDto;
import PhishingUniv.Phinocchio.domain.Report.dto.SearchResponseDto;
import PhishingUniv.Phinocchio.domain.Report.entity.ReportEntity;
import PhishingUniv.Phinocchio.domain.Report.service.ReportService;
import PhishingUniv.Phinocchio.domain.Report.service.SearchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
@Tag(name = "ReportController", description = "신고를 관리하는 컨트롤러")
public class ReportController {
    private final ReportService reportService;
    private final SearchService searchService;

    @PostMapping("/add")
    @Operation(summary = "신고를 추가하는 컨트롤러", description = "신고를 추가하는 컨트롤러입니다.")
    public ResponseEntity<ReportEntity> addReport(@RequestBody ReportDto reportDto)
    //2023-06-10 16:34:21.421  WARN 14608 --- [nio-8080-exec-7] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot deserialize value of type `PhishingUniv.Phinocchio.domain.Report.entity.ReportType` from String "REPORT_TYPE_FRAUsD": not one of the values accepted for Enum class: [REPORT_TYPE_DISGUISE, REPORT_TYPE_FRAUD, REPORT_TYPE_IMPERSONATING, REPORT_TYPE_INDUCE]; nested exception is com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize value of type `PhishingUniv.Phinocchio.domain.Report.entity.ReportType` from String "REPORT_TYPE_FRAUsD": not one of the values accepted for Enum class: [REPORT_TYPE_DISGUISE, REPORT_TYPE_FRAUD, REPORT_TYPE_IMPERSONATING, REPORT_TYPE_INDUCE]<EOL> at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 2, column: 10] (through reference chain: PhishingUniv.Phinocchio.domain.Report.dto.ReportDto["type"])]
    //@RequestBody Enum Parsing 오류 에외처리하기
    {
        ReportEntity reportEntity = reportService.addReport(reportDto);

        return ResponseEntity.ok(reportEntity);


    }
    @PostMapping("/add/withoutDoubt")
    @Operation(summary = "의심이 없는 신고를 추가하는 컨트롤러", description = "의심이 없는 신고를 추가하는 컨트롤러입니다.")
    public ResponseEntity<ReportEntity> addReportWithoutDoubt(@RequestBody ReportWithoutDoubtDto reportDto)
    //2023-06-10 16:34:21.421  WARN 14608 --- [nio-8080-exec-7] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot deserialize value of type `PhishingUniv.Phinocchio.domain.Report.entity.ReportType` from String "REPORT_TYPE_FRAUsD": not one of the values accepted for Enum class: [REPORT_TYPE_DISGUISE, REPORT_TYPE_FRAUD, REPORT_TYPE_IMPERSONATING, REPORT_TYPE_INDUCE]; nested exception is com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize value of type `PhishingUniv.Phinocchio.domain.Report.entity.ReportType` from String "REPORT_TYPE_FRAUsD": not one of the values accepted for Enum class: [REPORT_TYPE_DISGUISE, REPORT_TYPE_FRAUD, REPORT_TYPE_IMPERSONATING, REPORT_TYPE_INDUCE]<EOL> at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 2, column: 10] (through reference chain: PhishingUniv.Phinocchio.domain.Report.dto.ReportDto["type"])]
    //@RequestBody Enum Parsing 오류 에외처리하기
    {
        ReportEntity reportEntity = reportService.addReportWithoutDoubt(reportDto);

        return ResponseEntity.ok(reportEntity);


    }

    @GetMapping("/get")
    @Operation(summary = "신고 리스트를 조회하는 컨트롤러", description = "신고 리스트를 조회하는 컨트롤러입니다.")
    public List<ReportEntity> getReports(){
        List<ReportEntity> reportEntityList = reportService.getReports();
        return ResponseEntity.ok(reportEntityList).getBody();

    }

    @PostMapping("/search")
    @Operation(summary = "전화번호로 신고 횟수를 조회하는 컨트롤러", description = "전화번호로 신고 횟수를 조회하는 컨트롤러입니다.")
    public ResponseEntity<SearchResponseDto> getReportCount(@RequestBody SearchRequestDto searchRequestDto){
        SearchResponseDto searchResponseDto = searchService.getReportCount(searchRequestDto.getPhoneNumber());

        return ResponseEntity.ok(searchResponseDto);
    }

    @PostMapping("/searchList")
    @Operation(summary = "전화번호로 신고 내역을 조회하는 컨트롤러", description = "전화번호로 신고 내역을 조회하는 컨트롤러입니다.")
    public List<ReportEntity> getReportDetail(@RequestBody SearchRequestDto searchRequestDto){
        List<ReportEntity> reportEntityList = searchService.getReportDetail(searchRequestDto.getPhoneNumber());

        return ResponseEntity.ok(reportEntityList).getBody();
    }
}
