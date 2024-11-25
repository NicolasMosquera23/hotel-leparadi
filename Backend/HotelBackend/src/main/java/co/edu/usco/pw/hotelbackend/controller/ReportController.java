package co.edu.usco.pw.hotelbackend.controller;

import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.usco.pw.hotelbackend.dto.UserDTO;
import co.edu.usco.pw.hotelbackend.dto.ReservationDTO;
import co.edu.usco.pw.hotelbackend.entity.UserEntity;
import co.edu.usco.pw.hotelbackend.entity.ReservationEntity;
import co.edu.usco.pw.hotelbackend.enums.UserRole;
import co.edu.usco.pw.hotelbackend.repository.UserRepository;
import co.edu.usco.pw.hotelbackend.repository.ReservationRepository;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Controller
public class ReportController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Operation(summary = "Generar reporte", description = "Genera un reporte en formato PDF, ya sea de clientes o reservas, según el tipo especificado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Tipo de reporte no soportado"),
            @ApiResponse(responseCode = "500", description = "Error al generar el reporte")
    })
    @GetMapping("/report")
    public void generateReport(@RequestParam("reportType") String reportType, HttpServletResponse response) throws Exception {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + reportType + "_report.pdf\"");

        String reportName;
        List<?> data;

        if ("clients".equalsIgnoreCase(reportType)) {
            reportName = "clients_report";
            List<UserEntity> userEntities = userRepository.findByRole(UserRole.CLIENT);
            data = userEntities.stream().map(UserDTO::new).toList();
        } else if ("reservations".equalsIgnoreCase(reportType)) {
            reportName = "reservations_report";
            List<ReservationEntity> reservationEntities = reservationRepository.findAll();
            data = reservationEntities.stream().map(ReservationDTO::new).toList();
        } else {
            throw new IllegalArgumentException("Tipo de reporte no soportado: " + reportType);
        }

        JasperReport jasperReport = JasperCompileManager
                .compileReport(resourceLoader.getResource("classpath:reports/" + reportName + ".jrxml").getInputStream());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Reporte de " + reportType);

        try (Connection connection = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            try (OutputStream outputStream = response.getOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al generar el reporte: " + e.getMessage());
        }
    }
}
