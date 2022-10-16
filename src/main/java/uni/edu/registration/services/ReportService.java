package uni.edu.registration.services;

import org.springframework.stereotype.Service;
import uni.edu.registration.controllers.dto.ReportDto;
import uni.edu.registration.models.Report;
import uni.edu.registration.repositories.ReportRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by rasoolzadeh
 */
@Service
public class ReportService {
    private ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> findAll(HttpServletRequest request){
        return reportRepository.findAll();
    }

    public Report save(ReportDto reportDto, HttpServletRequest request){
        return reportRepository.save(Report.builder()
                .body(reportDto.getBody())
                .title(reportDto.getTitle())
                .user(reportDto.getUser())
                .build());
    }
}
