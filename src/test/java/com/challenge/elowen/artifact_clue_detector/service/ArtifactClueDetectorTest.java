package com.challenge.elowen.artifact_clue_detector.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para ArtifactClueDetector.
 * Verifica secuencias, límites y mensajes.
 */
class ArtifactClueDetectorTest {

    private ArtifactClueDetector detector;

    @BeforeEach
    void setUp() {
        detector = new ArtifactClueDetector();
    }

    @Test
    @DisplayName("Debe retornar false si el manuscrito es null")
    void testNullManuscript() {
        boolean result = detector.containsArtifactClue(null);
        assertFalse(result);
        assertEquals("Manuscrito vacío o nulo.", detector.getFoundClueDescription());
    }

    @Test
    @DisplayName("Debe retornar false si el manuscrito está vacío")
    void testEmptyManuscript() {
        boolean result = detector.containsArtifactClue(new String[]{});
        assertFalse(result);
        assertEquals("Manuscrito vacío o nulo.", detector.getFoundClueDescription());
    }

    @Test
    @DisplayName("Debe detectar una pista horizontal")
    void testHorizontalClue() {
        String[] manuscript = {
                "RRRRQW",
                "XRLQRE",
                "XARURR",
                "XEMPAL",
                "XGSILE",
                "BRINDS"
        };
        boolean result = detector.containsArtifactClue(manuscript);
        assertTrue(result, "Debe detectar secuencia horizontal de 'R'");
        assertTrue(detector.getFoundClueDescription().contains("HORIZONTAL"));
    }

    @Test
    @DisplayName("Debe detectar una pista vertical")
    void testVerticalClue() {
        String[] manuscript = {
                "RTHGQW",
                "XRLQRE",
                "XARURR",
                "XEMOAL",
                "XGSILE",
                "BRINDS"
        };
        boolean result = detector.containsArtifactClue(manuscript);
        assertTrue(result, "Debe detectar secuencia vertical de 'X'");
        assertTrue(detector.getFoundClueDescription().contains("VERTICAL"));
    }

    @Test
    @DisplayName("Debe detectar una pista diagonal ↘")
    void testDiagonalClueDownRight() {
        String[] manuscript = {
                "RTHGQW",
                "ARAHRE",
                "NARRRR",
                "REMRAL",
                "EGSILE",
                "BRINDS"
        };
        boolean result = detector.containsArtifactClue(manuscript);
        assertTrue(result, "Debe detectar secuencia diagonal de 'R'");
        assertTrue(detector.getFoundClueDescription().contains("DIAGONAL"));
    }

    @Test
    @DisplayName("Debe detectar una pista diagonal ↙")
    void testDiagonalClueDownLeft() {
        String[] manuscript = {
                "QRRZRQ",
                "ARAHRE",
                "NARRER",
                "REMEAL",
                "EGEILE",
                "BRINDS"
        };
        boolean result = detector.containsArtifactClue(manuscript);
        assertTrue(result, "Debe detectar secuencia diagonal de 'E'");
        assertTrue(detector.getFoundClueDescription().contains("DIAGONAL"));
    }

    @Test
    @DisplayName("Debe retornar false si no hay pista")
    void testNoClueFound() {
        String[] manuscript = {
                "ABCDEF",
                "GHIJKL",
                "MNOPQR",
                "STUVWX",
                "YZABCD",
                "EFGHIJ"
        };
        boolean result = detector.containsArtifactClue(manuscript);
        assertFalse(result, "No debe detectar ninguna pista");
        assertEquals("No se encontró ninguna pista en el manuscrito.", detector.getFoundClueDescription());
    }

}
