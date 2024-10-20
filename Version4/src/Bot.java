package Version4.src;

import java.util.Stack;

class Bot {

    static void makeMove(Piece[][] board, char botColor) {
        int bestScore = botColor == 'W' ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestStartRow = -1, bestStartCol = -1, bestEndRow = -1, bestEndCol = -1;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].colour == botColor) {
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            Piece[][] tempBoard = Functions.makeBoardCopy(board);
                            Stack<Piece[][]> tempHistory = new Stack<>();
                            tempHistory.push(Functions.makeBoardCopy(tempBoard));

                            if (Functions.makeMove(tempBoard, i, j, k, l, botColor, tempHistory)) {
                                int score = minimax(tempBoard, Main.MAX_DEPTH - 1, botColor == 'W' ? 'B' : 'W', Integer.MIN_VALUE, Integer.MAX_VALUE);

                                if ((botColor == 'W' && score > bestScore) || (botColor == 'B' && score < bestScore)) {
                                    bestScore = score;
                                    bestStartRow = i;
                                    bestStartCol = j;
                                    bestEndRow = k;
                                    bestEndCol = l;
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

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].colour == currentPlayer) {
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            Piece[][] tempBoard = Functions.makeBoardCopy(board);
                            Stack<Piece[][]> tempHistory = new Stack<>();
                            tempHistory.push(Functions.makeBoardCopy(tempBoard));

                            if (Functions.makeMove(tempBoard, i, j, k, l, currentPlayer, tempHistory)) {
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
