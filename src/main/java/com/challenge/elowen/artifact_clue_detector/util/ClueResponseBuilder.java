package com.challenge.elowen.artifact_clue_detector.util;

import com.challenge.elowen.artifact_clue_detector.model.Manuscript;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClueResponseBuilder {

    public static Map<String, Object> buildResponse(Manuscript result) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("hasClue", result.isHasClue());

        if (result.isHasClue()) {
            response.put("character", result.getClueCharacter());
            response.put("direction", result.getClueDirection());
            response.put("from", result.getFromPosition());
            response.put("to", result.getToPosition());
        } else {
            response.put("message", "No se encontró ninguna pista en el manuscrito.");
        }

        return response;
    }
}
