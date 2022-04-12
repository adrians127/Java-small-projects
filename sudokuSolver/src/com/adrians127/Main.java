package com.adrians127;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static final int SIZE = 9;

    public static void main(String[] args) {
        int[][] board = new int[SIZE][SIZE];
        insertBoard(board);
        System.out.println("SUDOKU TO SOLVE:");
        printBoard(board);
        if(solve(board)){
            System.out.println("SUCCEEDED!");
            printBoard(board);
            saveBoard(board);
        } else {
            System.out.println("Couldn't solve your sudoku :C");
        }
    }
    public static void saveBoard(int[][] board){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("sudoku_solved.txt"));
            for (int i = 0; i < SIZE; i++){
                for (int j = 0; j < SIZE; j++){
                    Integer temp = board[i][j];
                    String c = temp.toString();
                    writer.write(c);
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void insertBoard(int[][] board) {
        try {
            Scanner scanner = new Scanner(new File("sudoku.txt"));
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    board[i][j] = scanner.nextInt();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Couldnt find sudoku.txt file");
            e.printStackTrace();
        }
    }

    public static void printBoard(int[][] board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean isNumberInRow(int[][] board, int number, int row) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNumberInColumn(int[][] board, int number, int column) {
        for (int i = 0; i < SIZE; i++) {
            if (board[i][column] == number) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNumberInBox(int[][] board, int number, int row, int column) {
        int localBoxRow = row - row % 3;
        int localBoxColumn = column - column % 3;
        for (int i = localBoxRow; i < localBoxRow + 3; i++) {
            for (int j = localBoxColumn; j < localBoxColumn + 3; j++) {
                if (board[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isValidPlacement(int[][] board, int number, int row, int column) {
        return !isNumberInRow(board, number, row)
                && !isNumberInColumn(board, number, column)
                && !isNumberInBox(board, number, row, column);
    }

    private static boolean solve(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                if (board[row][column] == 0) {
                    for (int numberToGuess = 1; numberToGuess <= SIZE; numberToGuess++) {
                        if (isValidPlacement(board, numberToGuess, row, column)) {
                            board[row][column] = numberToGuess;

                            if (solve(board)) {
                                return true;
                            } else {
                                board[row][column] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
