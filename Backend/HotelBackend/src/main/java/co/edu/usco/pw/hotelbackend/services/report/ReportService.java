package co.edu.usco.pw.hotelbackend.services.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    public JasperPrint generateReport(String reportName, List<?> data, Map<String, Object> parameters) throws JRException {
        // Cargar el archivo jrxml
        InputStream reportStream = this.getClass().getResourceAsStream("/reports/" + reportName + ".jrxml");

        // Compilar el archivo jrxml
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Crear la fuente de datos
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        // Rellenar el reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return jasperPrint;
    }
}

