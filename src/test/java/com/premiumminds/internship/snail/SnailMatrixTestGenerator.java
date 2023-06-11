package com.premiumminds.internship.snail;

public class SnailMatrixTestGenerator {
    /* creates matrixes sizexsize that with a snail pattern are sorted
    *  used to create automatic tests that are easily verifiable */
    public SnailMatrixTestGenerator(){
    }
    public int[][] generateTest(int size){
        // edge case
        if(size==0){
            return new int[1][0];
        }
        int[][] response = new int[size][size];
        int val = 0;
        int x = 0;
        int y = 0;
        int steps = size;
        // do first line
        for(int i = 0; i != steps;i++){
            response[y][x] = val++;
            x++;
        }

        // steps is the amount of numbers it has to go through
        x--;
        steps--;
        while(steps != 0){
            // do right side of matrix
            for(int i = 0; i != steps;i++){
                y++;
                response[y][x] = val++;
            }
            // do bottom side of matrix
            for(int i = 0; i != steps;i++){
                x--;
                response[y][x] = val++;
            }
            steps--;

            if(steps == 0){
                break;
            }
            // do left side of matrix
            for(int i = 0; i != steps;i++){
                y--;
                response[y][x] = val++;
            }
            // do top of matrix
            for(int i = 0; i != steps;i++){
                x++;
                response[y][x] = val++;
            }
            steps--;
        }
        return response;
    }
}
