package com.challenge.elowen.artifact_clue_detector;

import com.challenge.elowen.artifact_clue_detector.service.ArtifactClueDetector;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de Spring Boot.
 * Nivel 1: Se usa CommandLineRunner para ejecutar el análisis al iniciar.
 */
@SpringBootApplication
public class ArtifactClueDetectorApplication implements CommandLineRunner {

	private final ArtifactClueDetector detector;

	public ArtifactClueDetectorApplication(ArtifactClueDetector detector) {
		this.detector = detector;
	}

	public static void main(String[] args) {
		SpringApplication.run(ArtifactClueDetectorApplication.class, args);
	}

	@Override
	public void run(String... args) {
		String[] manuscript = {
				"RTHGQW",
				"XRLQRE",
				"NARURR",
				"REVRAL",
				"EGSILE",
				"BRINDS"
		};

		boolean hasClue = detector.containsArtifactClue(manuscript);
		System.out.println("¿Contiene pista de artefacto? " + hasClue);
		System.out.println(detector.getFoundClueDescription());
	}

}