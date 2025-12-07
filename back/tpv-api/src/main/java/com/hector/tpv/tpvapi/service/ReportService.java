package com.hector.tpv.tpvapi.service;

import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    private final DataSource dataSource;

    public ReportService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String generarTicketPdf(Long ticketId) {

        try {
            InputStream jasperStream =
                    getClass().getResourceAsStream("/reports/Ticket.jasper");

            if (jasperStream == null) {
                throw new RuntimeException("No se encuentra Ticket.jasper");
            }

            Map<String, Object> params = new HashMap<>();
            params.put("TICKET_ID", ticketId);

            JasperPrint print = JasperFillManager.fillReport(
                    jasperStream,
                    params,
                    dataSource.getConnection()
            );

            Path carpeta = Paths.get("tickets");
            if (!Files.exists(carpeta)) {
                Files.createDirectories(carpeta);
            }

            String nombreArchivo = "ticket_" + ticketId + ".pdf";
            Path rutaPdf = carpeta.resolve(nombreArchivo);

            JasperExportManager.exportReportToPdfFile(print, rutaPdf.toString());

            return rutaPdf.toAbsolutePath().toString();

        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF de ticket " + ticketId, e);
        }
    }
        public String generarCierreTurnoPdf(Long cierreId) {

        try {
            InputStream jasperStream =
                    getClass().getResourceAsStream("/reports/CierreTurno.jasper");

            if (jasperStream == null) {
                throw new RuntimeException("No se encuentra CierreTurno.jasper");
            }

            Map<String, Object> params = new HashMap<>();
            params.put("CIERRE_ID", cierreId);

            JasperPrint print = JasperFillManager.fillReport(
                    jasperStream,
                    params,
                    dataSource.getConnection()
            );

            Path carpeta = Paths.get("cierres");
            if (!Files.exists(carpeta)) {
                Files.createDirectories(carpeta);
            }

            String nombreArchivo = "cierre_" + cierreId + ".pdf";
            Path rutaPdf = carpeta.resolve(nombreArchivo);

            JasperExportManager.exportReportToPdfFile(print, rutaPdf.toString());

            return rutaPdf.toAbsolutePath().toString();

        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF de cierre " + cierreId, e);
        }
    }

}
