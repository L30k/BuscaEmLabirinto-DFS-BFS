package com.leonardo.buscaemlabirinto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private Button[][] bt = new Button[7][6];
    private labMatriz matrizLab = new labMatriz();
    private int[][] adjacencyMatrix;
    private boolean[][] visited;
    private Handler handler = new Handler();
    private int[][] parent = new int[7][6];


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bt[0][0] = findViewById(R.id.bt01);
        bt[0][1] = findViewById(R.id.bt02);
        bt[0][2] = findViewById(R.id.bt03);
        bt[0][3] = findViewById(R.id.bt04);
        bt[0][4] = findViewById(R.id.bt05);
        bt[0][5] = findViewById(R.id.bt06);
        bt[1][0] = findViewById(R.id.bt11);
        bt[1][1] = findViewById(R.id.bt12);
        bt[1][2] = findViewById(R.id.bt13);
        bt[1][3] = findViewById(R.id.bt14);
        bt[1][4] = findViewById(R.id.bt15);
        bt[1][5] = findViewById(R.id.bt16);
        bt[2][0] = findViewById(R.id.bt21);
        bt[2][1] = findViewById(R.id.bt22);
        bt[2][2] = findViewById(R.id.bt23);
        bt[2][3] = findViewById(R.id.bt24);
        bt[2][4] = findViewById(R.id.bt25);
        bt[2][5] = findViewById(R.id.bt26);
        bt[3][0] = findViewById(R.id.bt31);
        bt[3][1] = findViewById(R.id.bt32);
        bt[3][2] = findViewById(R.id.bt33);
        bt[3][3] = findViewById(R.id.bt34);
        bt[3][4] = findViewById(R.id.bt35);
        bt[3][5] = findViewById(R.id.bt36);
        bt[4][0] = findViewById(R.id.bt41);
        bt[4][1] = findViewById(R.id.bt42);
        bt[4][2] = findViewById(R.id.bt43);
        bt[4][3] = findViewById(R.id.bt44);
        bt[4][4] = findViewById(R.id.bt45);
        bt[4][5] = findViewById(R.id.bt46);
        bt[5][0] = findViewById(R.id.bt51);
        bt[5][1] = findViewById(R.id.bt52);
        bt[5][2] = findViewById(R.id.bt53);
        bt[5][3] = findViewById(R.id.bt54);
        bt[5][4] = findViewById(R.id.bt55);
        bt[5][5] = findViewById(R.id.bt56);
        bt[6][0] = findViewById(R.id.bt61);
        bt[6][1] = findViewById(R.id.bt62);
        bt[6][2] = findViewById(R.id.bt63);
        bt[6][3] = findViewById(R.id.bt64);
        bt[6][4] = findViewById(R.id.bt65);
        bt[6][5] = findViewById(R.id.bt66);

        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 6; j++){
                bt[i][j].setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.purple_500));
            }
        }

        adjacencyMatrix = matrizLab.getAdjacencyMatrix();
        visited = new boolean[7][6];
    }

    public void onClick(View view){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 6; j++){
                int k = i;
                int h = j;

                bt[i][j].setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View view) {
                        if(matrizLab.getCampo(k,h) == 0){
                            matrizLab.setCampo(k,h, 1);
                            bt[k][h].setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.black));
                        }else{
                            matrizLab.setCampo(k,h, 0);
                            bt[k][h].setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.purple_500));
                        }

                    }
                });
            }
        }
    }

    public void limpar(View view){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 6; j++){
                bt[i][j].setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.purple_500));
                matrizLab.setCampo(i, j, 0);
                parent[i][j] = 0; // Reseta a matriz de pais
            }
        }
    }


    public void largura(View view){
        matrizLab.regerarAdjacencyMatrix();
        adjacencyMatrix = matrizLab.getAdjacencyMatrix();

        resetVisited();

        int startX = 0;
        int startY = 0;
        int endX = 6;
        int endY = 5;

        breadthFirstSearch(startX, startY, endX, endY);

        paintShortestPath(startX, startY, endX, endY);
    }

    private void resetVisited() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                visited[i][j] = false;
            }
        }
    }

    private void breadthFirstSearch(int startX, int startY, int endX, int endY) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startX * 6 + startY);
        visited[startX][startY] = true;

        final int destination = endX * 6 + endY;


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!queue.isEmpty()) {
                    int current = queue.poll();

                    int row = current / 6;
                    int col = current % 6;

                    if (current == destination) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                bt[endX][endY].setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_green_dark));
                                paintShortestPath(startX, startY, endX, endY);
                            }
                        });
                        break;
                    }

                    for (int i = 0; i < 42; i++) {
                        if (adjacencyMatrix[current][i] == 1 && !visited[i / 6][i % 6]) {
                            visited[i / 6][i % 6] = true;
                            queue.add(i);
                            parent[i / 6][i % 6] = current;

                            final int finalI = i;

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    int r = finalI / 6;
                                    int c = finalI % 6;
                                    bt[r][c].setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_blue_dark));
                                }
                            });

                            try {
                                Thread.sleep(500); // Ajuste conforme necessário
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();
    }


    private void paintShortestPath(int startX, int startY, int endX, int endY) {
        int current = endX * 6 + endY;

        while (current != startX * 6 + startY) {
            int row = current / 6;
            int col = current % 6;
            bt[row][col].setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_green_light));

            current = parent[row][col];
        }

        bt[startX][startY].setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_green_light));

        bt[0][0].setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_red_light));
        bt[6][5].setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_red_light));
    }



    public void profundidade(View view){
        matrizLab.regerarAdjacencyMatrix();
        adjacencyMatrix = matrizLab.getAdjacencyMatrix();

        resetVisited();

        int startX = 0;
        int startY = 0;
        int endX = 6;
        int endY = 5;

        depthFirstSearch(startX, startY, endX, endY);
        paintShortestPath(startX, startY, endX, endY); // Pintar o caminho encontrado
    }

    private void depthFirstSearch(int startX, int startY, int endX, int endY) {
        Stack<Integer> stack = new Stack<>();
        stack.push(startX * 6 + startY);
        visited[startX][startY] = true;

        final int destination = endX * 6 + endY;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stack.isEmpty()) {
                    int current = stack.pop();

                    int row = current / 6;
                    int col = current % 6;

                    if (current == destination) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                bt[endX][endY].setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_green_dark));
                                paintShortestPath(startX, startY, endX, endY);
                            }
                        });
                        break;
                    }

                    final int finalRow = row;
                    final int finalCol = col;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            bt[finalRow][finalCol].setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_blue_dark));
                            bt[0][0].setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_green_dark));
                        }
                    });

                    try {
                        Thread.sleep(500); // Ajuste conforme necessário
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < 42; i++) {
                        if (adjacencyMatrix[current][i] == 1 && !visited[i / 6][i % 6]) {
                            visited[i / 6][i % 6] = true;
                            stack.push(i);
                            parent[i / 6][i % 6] = current;
                        }
                    }
                }
            }
        }).start();
    }
}