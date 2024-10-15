package Version4.src;

import java.util.Stack;

public class Bot {
    private static final int MAX_DEPTH = 3;

    public static void makeMove(Piece[][] board, char botColor) {
        int bestScore = botColor == 'W' ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestStartRow = -1, bestStartCol = -1, bestEndRow = -1, bestEndCol = -1;

        for (int startRow = 0; startRow < 8; startRow++) {
            for (int startCol = 0; startCol < 8; startCol++) {
                if (board[startRow][startCol].COLOUR == botColor) {
                    for (int endRow = 0; endRow < 8; endRow++) {
                        for (int endCol = 0; endCol < 8; endCol++) {
                            Piece[][] tempBoard = Functions.makeBoardCopy(board);
                            Stack<Piece[][]> tempHistory = new Stack<>();
                            tempHistory.push(Functions.makeBoardCopy(tempBoard));

                            if (Functions.makeMove(tempBoard, startRow, startCol, endRow, endCol, botColor, tempHistory)) {
                                int score = minimax(tempBoard, MAX_DEPTH - 1, botColor == 'W' ? 'B' : 'W', Integer.MIN_VALUE, Integer.MAX_VALUE);

                                if ((botColor == 'W' && score > bestScore) || (botColor == 'B' && score < bestScore)) {
                                    bestScore = score;
                                    bestStartRow = startRow;
                                    bestStartCol = startCol;
                                    bestEndRow = endRow;
                                    bestEndCol = endCol;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (bestStartRow != -1 && bestStartCol != -1 && bestEndRow != -1 && bestEndCol != -1) {
            Functions.makeMove(board, bestStartRow, bestStartCol, bestEndRow, bestEndCol, botColor, ChessGUI.boardHistory);
        }
    }

    private static int minimax(Piece[][] board, int depth, char currentPlayer, int alpha, int beta) {
        if (depth == 0 || EndConditions.checkMate(board, currentPlayer, new Stack<>()) || EndConditions.staleMate()) {
            return evaluateBoard(board);
        }

        int bestScore = currentPlayer == 'W' ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int startRow = 0; startRow < 8; startRow++) {
            for (int startCol = 0; startCol < 8; startCol++) {
                if (board[startRow][startCol].COLOUR == currentPlayer) {
                    for (int endRow = 0; endRow < 8; endRow++) {
                        for (int endCol = 0; endCol < 8; endCol++) {
                            Piece[][] tempBoard = Functions.makeBoardCopy(board);
                            Stack<Piece[][]> tempHistory = new Stack<>();
                            tempHistory.push(Functions.makeBoardCopy(tempBoard));

                            if (Functions.makeMove(tempBoard, startRow, startCol, endRow, endCol, currentPlayer, tempHistory)) {
                                int score = minimax(tempBoard, depth - 1, currentPlayer == 'W' ? 'B' : 'W', alpha, beta);

                                if (currentPlayer == 'W') {
                                    bestScore = Math.max(bestScore, score);
                                    alpha = Math.max(alpha, bestScore);
                                } else {
                                    bestScore = Math.min(bestScore, score);
                                    beta = Math.min(beta, bestScore);
                                }

                                if (beta <= alpha) {
                                    return bestScore;
                                }
                            }
                        }
                    }
                }
            }
        }

        return bestScore;
    }

    private static int evaluateBoard(Piece[][] board) {
        return Functions.calculateEvaluation(board);
    }
}