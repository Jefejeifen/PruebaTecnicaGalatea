package com.challenge.elowen.artifact_clue_detector.service;

import org.springframework.stereotype.Service;

/**
 * Nivel 1: Clase que tiene la logica solicitada por Elowen.
 * Descripción: este servicio es el encargado de analizar antiguos manuscritos
 * y determinar si contienen pistas sobre artefactos mágicos.
 */
@Service
public class ArtifactClueDetector {

    //Cantidad mínima de letras consecurtivas
    private static final int SEQUENCE_LENGTH = 4;

    //Variable para guardar una descripción cuando se encuentre una pista
    private String foundClueDescription;

    /**
     * Este es el metodo principal, recibe un array de Strings, donde cada string representa
     * una fila del manuscrito.
     */
    public boolean containsArtifactClue(String[] manuscript) {

        /**
         * Aqui se realiza la validacion incial. Si el manuscrito llega vació o es null, entonces no se analiza.
         * Se envia un mensaje descriptivo y se retorna un false.
         */
        if (manuscript == null || manuscript.length == 0) {
            foundClueDescription = "Manuscrito vacío o nulo.";
            return false; // Validación temprana
        }

        int rows = manuscript.length;
        int cols = manuscript[0].length();
        /**
         * Se crea una matriz bidimensional de caracteres, donde cada celda va representar una posición (fila, columna)
         * Ejemplo: la primera fila que llegue RTHGQW se transforma en ['R', 'T', 'H', 'G', 'Q', 'W']
         */
        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            grid[i] = manuscript[i].toCharArray();
        }

        /**
         * Se recorre cada caracter del manuscrito como posible inicio de una secuencia.
         */
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                /** Revisar en las cuatro direcciones principales , validará cada direccion para
                 * determinar por cual irse
                 */
                if (checkDirection(grid, row, col, 0, 1, "HORIZONTAL →") ||  // Horizontal →
                        checkDirection(grid, row, col, 1, 0, "VERTICAL ↓") ||    // Vertical ↓
                        checkDirection(grid, row, col, 1, 1, "DIAGONAL ↘") ||   // Diagonal ↘
                        checkDirection(grid, row, col, 1, -1, "DIAGONAL ↙"))    // Diagonal ↙
                {
                    return true; // Pista encontrada, cuando finalmente encuentre una pista retornará true
                }
            }
        }

        foundClueDescription = "No se encontró ninguna pista en el manuscrito.";
        return false; // Ninguna pista encontrada
    }

    /**
     * Este metodo verifica si desde una posición inicial (row, col), existen
     * letras iguales consecutivas en la dirección (dx, dy).
     * Si se encuentra, guarda la descripción de la pista.
     */
    private boolean checkDirection(char[][] grid, int row, int col, int dx, int dy, String direction) {
        int rows = grid.length;
        int cols = grid[0].length;
        char firstChar = grid[row][col];

        /** Verifica si hay espacio suficiente ubicandose en la posicion de la ultima letra ya sea en filas o columnas,
         * usando esta formula:
         *  - Para filas: row + dx * (SEQUENCE_LENGTH - 1) --> posición de la última letra
         *  - Para columnas: col + dy * (SEQUENCE_LENGTH - 1) --> posición de la última letra
         * Si la condicion da true se retorna false y termina la condición porque ya no hay mas donde moverse,
         * y continua con la siguiente dirección.
         */
        if (!isWithinBounds(row + dx * (SEQUENCE_LENGTH - 1),
                col + dy * (SEQUENCE_LENGTH - 1),
                rows, cols)) {
            return false;
        }

        /**
         * Si aún hay donde moverse, entonces se comparan las siguientes posiciones en la direccion respectiva
         * En cuanto a una letra no coincida entonces se corta el bucle y se retorna false. Y continua con la
         * siguiente dirección.
         */
        for (int k = 1; k < SEQUENCE_LENGTH; k++) {
            int newRow = row + dx * k;
            int newCol = col + dy * k;
            if (grid[newRow][newCol] != firstChar ) {
                return false;
            }
        }

        /**
         * Si el bucle termina sin retornar false, significa que todas coinciden.
         */
        int endRow = row + dx * (SEQUENCE_LENGTH - 1);
        int endCol = col + dy * (SEQUENCE_LENGTH - 1);
        foundClueDescription = String.format(
                "Pista encontrada: '%c' repetida %d veces en dirección %s desde (%d,%d) hasta (%d,%d).",
                firstChar, SEQUENCE_LENGTH, direction, row, col, endRow, endCol
        );
        return true; //Devuelve true, porque sí encontró una pista
    }

    /** Verifica que una posición esté dentro de los límites del manuscrito (matriz).
     * row >= 0 --> No estoy intentando ir “arriba” del tablero, (row = -1) no es válido
     * row < rows --> No me salgo “abajo” del tablero, (row = 6) en una matriz 6x6 no existe
     * col >= 0 --> No me salgo “a la izquierda” del tablero, (col = -1) no es válido
     * No me salgo “a la derecha” del tablero --> (col = 6) en una matriz 6x6 no existe
     * */
    private boolean isWithinBounds(int row, int col, int rows, int cols) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    /** Devuelve la descripción de la última pista encontrada */
    public String getFoundClueDescription() {
        return foundClueDescription;
    }
}
