package com.premiumminds.internship.snail;

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by aamado on 05-05-2023.
 */
class ParallelSnailShellPattern implements ISnailShellPattern {

    private ExecutorService parallelExecutor = Executors.newFixedThreadPool(8);
    private ExecutorService executor = Executors.newSingleThreadExecutor();


    /**
     * Method to get snailshell pattern
     *
     * @param matrix matrix of numbers to go through
     * @return order array of values thar represent a snail shell pattern
     */
    public Future<int[]> getSnailShell(int[][] matrix) {
        /*
         * le nSteps valores de cada linha/coluna da matrix,
         * na primeira linha le n valores, na ultima coluna
         * le n-1(pois o primeiro valor foi lido na 1a linha)
         * depois na ultima linha le n-1 de novo, depois n-2
         * depois destas leituras o programa teria de ler uma
         * matrix (n-2)*(n-2) pois retirou a camada de fora da
         * aplicando a mesma logica
         * entao o nSteps seria: n,n-1,n-1,n-2,n-2,n-3,n-3...
         */
        return executor.submit(()->{
            if(matrix[0].length == 0){
                return new int[0];
            }
            int size = matrix.length;
            int[] response = new int[size * size];
            Stack<Future<?>> futureList = new Stack<>(); // list of threads
            int y = 0;
            int x = -1; // starts at -1 to read 0 on first iteration
            int index = 0;
            int nSteps = size;
            // percorre a primeira linha
            futureList.push(readRowFuture(response, nSteps, matrix, x, y, 1, index));
            index += nSteps;
            x += +nSteps;
            nSteps--;

            while(nSteps != 0){
                // percorrer y em sentido positivo
                futureList.push(readColFuture(response, nSteps, matrix, x, y, 1, index));

                // set variaveis nos valores corretos depois desta funcao
                index += nSteps;
                y += nSteps;

                // percorrer x em sentido negativo
                futureList.push(readRowFuture(response, nSteps, matrix, x, y, -1, index));

                // set variaveis nos valores corretos depois desta funcao
                index += nSteps;
                x += -nSteps;

                nSteps--;
                if(nSteps == 0){
                    break;
                }

                // percorrer y em sentido negativo
                futureList.push(readColFuture(response, nSteps, matrix, x, y, -1, index));

                // set variaveis nos valores corretos depois desta funcao
                index += nSteps;
                y += -nSteps;

                // percorrer x em sentido positivo
                futureList.push(readRowFuture(response, nSteps, matrix, x, y, 1, index));

                // set variaveis nos valores corretos depois desta funcao
                index += nSteps;
                x += +nSteps;

                nSteps--;
            }
            // espera os futures acabarem
            while(!futureList.empty()){
                futureList.pop().get();
            }
            return response;
        });
    }
    // reads a row in a given order(dx)
    void readRow(int[] response,int nSteps, int[][] matrix, int x, int y, int dx, int index){
        for(int i = nSteps; i != 0; i--){
            x = x+dx;
            response[index++] = matrix[y][x];
        }
        return;
    }
    // reads a col in a given order(dy)
    void readCol(int[] response,int nSteps, int[][] matrix,int x, int y, int dy, int index){
        for(int i = nSteps; i != 0; i--){
            y = y+dy;
            response[index++] = matrix[y][x];
        }
        return;
    }

    // returns the future of a row
    Future<?> readRowFuture(int[] response,int nSteps, int[][] matrix, int x, int y, int dx, int index){
        return parallelExecutor.submit(()->{
            readRow(response,nSteps, matrix, x, y, dx, index);
            return;
        });
    }
    // returns the future of a col
    Future<?> readColFuture(int[] response,int nSteps, int[][] matrix, int x, int y, int dy, int index){
        return parallelExecutor.submit(()->{
            readCol(response,nSteps, matrix, x, y, dy, index);
            return;
        });
    }

}