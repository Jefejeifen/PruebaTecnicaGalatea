package com.challenge.elowen.artifact_clue_detector.service;

import com.challenge.elowen.artifact_clue_detector.model.Manuscript;
import com.challenge.elowen.artifact_clue_detector.repository.ManuscriptRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ArtifactClueService {

    private final ArtifactClueDetector detector;
    private final ManuscriptRepository repository;

    public ArtifactClueService(ArtifactClueDetector detector, ManuscriptRepository repository) {
        this.detector = detector;
        this.repository = repository;
    }

    public Manuscript analyzeAndSave(String[] manuscriptArray) {
        /**
         * Une todas las líneas del manuscrito en un solo texto.
         * Genera un hash SHA-256 que actúa como identificador único
         */
        String manuscriptText = String.join("", manuscriptArray);
        String hash = generateHash(manuscriptText);

        /**
         * Verificar la base de datos si ya existe un manuscrito con este mismo hash (para no duplicar)
         * Si sí, entonces lo devuelve directamente pero no guarda
         * Si no, entonces sigue el flujo del analisis
         */
        Optional<Manuscript> existing = repository.findByManuscriptHash(hash);
        if (existing.isPresent()) {
            return existing.get();
        }

        /**
         *  Analizar el manuscrito , se usa la clase ArtifactClueDetector, que busca 4 letras iguales consecutivas
         *  (en horizontal, vertical o diagonal)
         *  Si se encuentra una pista entonces --> hasClue = true
         */

        boolean hasClue = detector.containsArtifactClue(manuscriptArray);

        Manuscript manuscript = new Manuscript();
        manuscript.setManuscriptHash(hash);
        manuscript.setHasClue(hasClue);

        //Si hay pista, llenar detalles
        if (hasClue) {
            String description = detector.getFoundClueDescription();

            Pattern pattern = Pattern.compile(
                    "Pista encontrada: '([A-Z])'.*dirección ([^ ]+).*desde \\((\\d+,\\d+)\\) hasta \\((\\d+,\\d+)\\)"
            );
            Matcher matcher = pattern.matcher(description);

            if (matcher.find()) {
                manuscript.setClueCharacter(matcher.group(1));
                manuscript.setClueDirection(matcher.group(2));
                manuscript.setFromPosition(matcher.group(3));
                manuscript.setToPosition(matcher.group(4));
            }
        } else {
            //Registrar igualmente aunque no tenga pista
            manuscript.setClueCharacter(null);
            manuscript.setClueDirection(null);
            manuscript.setFromPosition(null);
            manuscript.setToPosition(null);
        }

        //Guardar en base de datos
        return repository.save(manuscript);
    }

    //Genera hash SHA-256
    private String generateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar hash del manuscrito", e);
        }
    }
}
