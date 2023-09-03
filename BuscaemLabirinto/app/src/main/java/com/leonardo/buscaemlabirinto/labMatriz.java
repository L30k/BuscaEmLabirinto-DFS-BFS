package com.leonardo.buscaemlabirinto;

import android.content.Context;
import androidx.core.content.ContextCompat;

public class labMatriz {
    public Cell[][] campo = new Cell[7][6];
    private int[][] adjacencyMatrix;

    public labMatriz() {
        iniciarMatriz();
        gerarAdjacencyMatrix();
    }

    private void iniciarMatriz() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                campo[i][j] = new Cell(i, j, 0); // Inicializa com valor 0 por padrão
            }
        }
    }

    private void gerarAdjacencyMatrix() {
        int vertexCount = 42;
        adjacencyMatrix = new int[vertexCount][vertexCount];

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                adjacencyMatrix[i][j] = 0; // Inicializa todos os caminhos como não conectados
            }
        }

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                int index = i * 6 + j;

                if (campo[i][j].value == 0) { // Se a célula está livre, conecte às células adjacentes
                    if (i > 0 && campo[i - 1][j].value == 0) {
                        adjacencyMatrix[index][index - 6] = 1;
                        adjacencyMatrix[index - 6][index] = 1;
                    }
                    if (i < 6 && campo[i + 1][j].value == 0) {
                        adjacencyMatrix[index][index + 6] = 1;
                        adjacencyMatrix[index + 6][index] = 1;
                    }
                    if (j > 0 && campo[i][j - 1].value == 0) {
                        adjacencyMatrix[index][index - 1] = 1;
                        adjacencyMatrix[index - 1][index] = 1;
                    }
                    if (j < 5 && campo[i][j + 1].value == 0) {
                        adjacencyMatrix[index][index + 1] = 1;
                        adjacencyMatrix[index + 1][index] = 1;
                    }
                }
            }
        }
    }


    public void regerarAdjacencyMatrix() {
        gerarAdjacencyMatrix();
    }

    public int getCampo(int row, int col) {
        return campo[row][col].value;
    }

    public void setCampo(int row, int col, int val) {
        campo[row][col].value = val;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }
}
