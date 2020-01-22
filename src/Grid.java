import java.lang.*;
import java.util.Dictionary;
import java.util.Hashtable;

class Grid {
    private int gridSize, M;
    private int gapSize, offset, figure_size;
    String[][] array;
    Dictionary scores;

    Grid(int gridSize, int M) {
        this.gridSize = gridSize;
        this.M = M;
        this.array = new String[M][M];
        gapSize = (int) Math.ceil(this.gridSize / this.M);
        offset = gapSize / 2;
        this.figure_size = 30;
        this.scores = new Hashtable();
        scores.put("X", -10);
        scores.put("O", 10);
        scores.put("Draw", 0);


        for (int i = 0; i < this.M; i++) {
            for (int k = 0; k < this.M; k++) {
                array[i][k] = "NULL";
            }
        }
    }

    void draw_grid() {
        for (int i = gapSize; i <= this.gridSize; i += gapSize) {
            Game.processing.line(0, i, this.gridSize, i);
            Game.processing.line(i, 0, i, this.gridSize);
        }
    }

    void makeMove() {
        double bestScore = Double.NEGATIVE_INFINITY;
        int[] bestMove = new int[2];
        for (int i = 0; i < this.M; i++) {
            for (int j = 0; j < this.M; j++) {
                if (this.array[i][j].equals("NULL")) {
                    this.array[i][j] = "O";
                    int score = this.minimax(this.array, false, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                    this.array[i][j] = "NULL";
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }

                }
            }
        }
        draw_figure((bestMove[0] * gapSize) + 1, (bestMove[1] * gapSize) + 1, "O");
    }

    private int minimax(String[][] array, boolean isMax, int depth, double alpha, double beta) {
        double bestScore;
        if (!checkWinner().equals("")) {
            return (int) this.scores.get(checkWinner());
        }
        if (Game.difficulty.equals("easy")){
            if (depth == 1){
                return 0;
            }
        }

        if (isMax) bestScore = Double.NEGATIVE_INFINITY; else bestScore = Double.POSITIVE_INFINITY;

        boolean flag = false;
        for (int i = 0; i < this.M; i++) {
            if (flag) break;
            for (int j = 0; j < this.M; j++) {
                if (array[i][j].equals("NULL")) {
                    if (isMax){
                        array[i][j] = "O";
                        int score = this.minimax(array, false, depth+1, alpha, beta);
                        bestScore = Math.max(score, bestScore);
                        alpha = Math.max(alpha, score);
                        if (beta <= alpha){
                            flag = true;
                            break;
                        }
                    } else{
                        array[i][j] = "X";
                        int score = this.minimax(array, true, depth+1, alpha, beta);
                        bestScore = Math.min(score, bestScore);
                        beta = Math.max(beta, score);
                        if (beta <= alpha){
                            flag = true;
                            break;
                        }
                    }
                    array[i][j] = "NULL";
                }
            }
        }
        return (int) bestScore;
    }

    String draw_figure(int x, int y, String sign) {
        for (int i = 0; i <= this.gridSize; i += gapSize) {
            for (int k = 0; k <= this.gridSize; k += gapSize) {
                int x_index = i / gapSize;
                int y_index = k / gapSize;
                if ((x - i) > 0 && (x - i) < gapSize && (y - k) > 0 && (y - k) < gapSize && this.array[x_index][y_index].equals("NULL")) {
                    if (sign.equals("O")) {
//                        Game.processing.ellipse(i + this.offset, k + this.offset, gapSize - figure_size - 20, gapSize - figure_size - 20);
                        Game.processing.image(Game.O, i + 20, k + 20, gapSize - figure_size - 20, gapSize - figure_size - 20);
                        this.array[x_index][y_index] = "O";
                        return "Valid";
                    } else {
//                        Game.processing.line(i + figure_size, k + figure_size, i + gapSize - figure_size, k + gapSize - figure_size);
//                        Game.processing.line(i + gapSize - figure_size, k + figure_size, i + figure_size, k + gapSize - figure_size);
                        Game.processing.image(Game.X, i +20, k+20, gapSize - figure_size - 20, gapSize - figure_size - 20);
                        this.array[x_index][y_index] = "X";
                        return "Valid";
                    }
                }
            }
        }
        return "Invalid";
    }

    String checkWinner() {
        String[] signs = {"X", "O"};
        String[] combinations = {"Horizontal", "Vertical", "Cross_one", "Cross_two"};
        String checkingValue;
        int streak;

        // Checking Winner
        for (String sign : signs) {
            for (String combination : combinations) {
                for (int i = 0; i < this.M; i++) {
                    streak = 0;
                    for (int k = 0; k < this.M; k++) {
                        switch (combination) {
                            case "Horizontal":
                                checkingValue = this.array[i][k];
                                break;
                            case "Vertical":
                                checkingValue = this.array[k][i];
                                break;
                            case "Cross_one":
                                checkingValue = this.array[k][k];
                                break;
                            case "Cross_two":
                                checkingValue = this.array[k][this.M - k - 1];
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + combination);
                        }
                        if (checkingValue.equals(sign)) {
                            streak++;
                            if (streak == this.M) return sign;
                        } else {
                            streak = 0;
                        }
                    }
                }
            }
        }

        // Checking if any field is empty
        for (int i = 0; i < this.M; i++) {
            for (int k = 0; k < this.M; k++) {
                if (this.array[i][k].equals("NULL")) {
                    return "";
                }
            }
        }
        return "Draw";
    }
}
