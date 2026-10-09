package com.adacorp.task_managementV2.controller;

import com.adacorp.task_managementV2.model.State;
import com.adacorp.task_managementV2.repository.StateRepository;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperCompileManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RapportController {

    private final StateRepository stateRepository;

    public RapportController(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @GetMapping("/rapport/state")
    public void generateStateReport(@RequestParam("id") Long id, HttpServletResponse response) throws Exception {

        // 1. Récupérer le State
        State state = stateRepository.findById(id).orElseThrow(() -> new RuntimeException("State introuvable avec l'id : " + id));

        // 2. Charger le fichier Jasper
        InputStream reportStream = new ClassPathResource("reports/state_report.jrxml").getInputStream();

        // 3. Compiler le rapport
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // 4. Envoyer les données au rapport
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("STATE_ID", state.getId());
        parameters.put("STATE_CODE", state.getCode());
        parameters.put("STATE_DESCRIPTION", state.getDescription());

        // 5. Générer le rapport
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        parameters,
                        new net.sf.jasperreports.engine.JREmptyDataSource()
                );

        // 6. Configurer la réponse HTTP
        response.setContentType("application/pdf");

        response.setHeader(
                "Content-Disposition",
                "inline; filename=state_" + id + ".pdf"
        );

        // 7. Envoyer le PDF au navigateur
        JasperExportManager.exportReportToPdfStream(
                jasperPrint,
                response.getOutputStream()
        );
    }
}