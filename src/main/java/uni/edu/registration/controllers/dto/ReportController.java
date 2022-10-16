package uni.edu.registration.controllers.dto;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uni.edu.registration.models.Report;
import uni.edu.registration.services.ReportService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by rasoolzadeh
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<Report> findAll(HttpServletRequest request){
        return reportService.findAll(request);
    }

    @PostMapping
    public Report saveReport(@Valid @RequestBody ReportDto reportDto, HttpServletRequest request){
        return reportService.save(reportDto, request);
    }
}
