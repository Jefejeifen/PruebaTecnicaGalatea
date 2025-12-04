package com.challenge.elowen.artifact_clue_detector.controller;

import com.challenge.elowen.artifact_clue_detector.model.Manuscript;
import com.challenge.elowen.artifact_clue_detector.service.ArtifactClueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/clue")
public class ClueController {

    private final ArtifactClueService service;

    public ClueController(ArtifactClueService service) {
        this.service = service;
    }

    // Acepta tanto /clue como /clue/
    @PostMapping({"", "/"})
    public ResponseEntity<?> analyze(@RequestBody Map<String, String[]> request) {
        String[] manuscript = request.get("manuscript");

        if (manuscript == null || manuscript.length == 0) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "El campo 'manuscript' no puede estar vacío."
            ));
        }

        Manuscript result = service.analyzeAndSave(manuscript);

        if (result.isHasClue()) {
            Map<String, Object> orderedResponse = new LinkedHashMap<>();
            orderedResponse.put("hasClue", true);
            orderedResponse.put("character", result.getClueCharacter());
            orderedResponse.put("direction", result.getClueDirection());
            orderedResponse.put("from", result.getFromPosition());
            orderedResponse.put("to", result.getToPosition());

            return ResponseEntity.ok(orderedResponse);
        } else {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("hasClue", false);
            response.put("message", "No se encontró ninguna pista en el manuscrito.");
            return ResponseEntity.status(403).body(response);
        }
    }
}
