package com.hector.tpv.tpvapi.controller;

import com.hector.tpv.tpvapi.model.CierreTurno;
import com.hector.tpv.tpvapi.service.CierreTurnoService;
import com.hector.tpv.tpvapi.service.ReportService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cierres")
@CrossOrigin
public class CierreTurnoController {

    private final CierreTurnoService cierreService;
    private final ReportService reportService;

    public CierreTurnoController(CierreTurnoService cierreService,
            ReportService reportService) {
        this.cierreService = cierreService;
        this.reportService = reportService;
    }

    @PostMapping("/turno")
    public ResponseEntity<CierreTurno> cerrarTurno() {
        CierreTurno cierre = cierreService.cerrarTurno();
        return ResponseEntity.ok(cierre);
    }

    @GetMapping("/{cierreId}/pdf")
    public ResponseEntity<Resource> getCierreTurnoPdf(@PathVariable Long cierreId) {
        try {
            String pdfPathStr = reportService.generarCierreTurnoPdf(cierreId);

            Path pdfPath = Paths.get(pdfPathStr);
            byte[] data = Files.readAllBytes(pdfPath);
            ByteArrayResource resource = new ByteArrayResource(data);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                    ContentDisposition
                            .builder("attachment")
                            .filename(pdfPath.getFileName().toString())
                            .build()
            );

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
