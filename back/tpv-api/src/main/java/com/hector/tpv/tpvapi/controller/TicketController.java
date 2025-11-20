package com.hector.tpv.tpvapi.controller;

import com.hector.tpv.tpvapi.service.ReportService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final ReportService reportService;

    public TicketController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{ticketId}/pdf")
    public ResponseEntity<Resource> getTicketPdf(@PathVariable Long ticketId) {
        try {
            String pdfPathStr = reportService.generarTicketPdf(ticketId);

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
