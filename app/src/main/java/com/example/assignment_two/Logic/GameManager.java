package com.example.assignment_two.Logic;

import java.util.Random;

public class GameManager {

    private static final int WON = 9999;
    private static final int ROWS = 11;
    private static final int COLS = 5;
    private final int[][] obstacleBoard;
    private final int[] dodgeBoard;

    private final int life;
    private int hitNum;
    private int score;
    private int cycleCount;
    private final Random random;

    public GameManager() {
        this(3);
    }

    public GameManager(int life) {

        obstacleBoard = new int[ROWS][COLS];
        for (int i = 0; i < obstacleBoard.length; i++) {
            for (int j = 0; j < obstacleBoard[0].length; j++)
                obstacleBoard[i][j] = 0;
        }

        score = 0;
        hitNum = 0;
        cycleCount = 0;
        random = new Random();
        this.life = life;
        dodgeBoard = new int[]{0, 0, 1, 0, 0};
    }


    public void updateGameLogic() {

        for (int col = 0; col < obstacleBoard[0].length; col++) //clears the last line from obstacles
            obstacleBoard[obstacleBoard.length - 1][col] = 0;

        for (int row = obstacleBoard.length - 1; row > 0; row--) { //takes each obstacle row down
            for (int col = 0; col < obstacleBoard[0].length; col++) {
                if (obstacleBoard[row - 1][col] == 1) {
                    obstacleBoard[row - 1][col] = 0;
                    obstacleBoard[row][col] = 1;
                } else if (obstacleBoard[row - 1][col] == 2) {
                    obstacleBoard[row - 1][col] = 0;
                    obstacleBoard[row][col] = 2;
                }
            }
        }

        if (cycleCount % 3 == 0 || cycleCount % 6 == 0) { // every 3 cycles new obstacle appear
            addNewObstacleOrCoin();
        }

        cycleCount++;
    }

    private void addNewObstacleOrCoin() {
        for (int i = 0; i < 2; i++) {
            int currentCol = random.nextInt(COLS);
            int obstacleOrCoin = random.nextInt(2) + 1;
            obstacleBoard[0][currentCol] = obstacleOrCoin;
        }
    }

    public void dodgeMovement(int direction) {

        boolean flag = false;
        int newPosition;
        int currentPosition = -1;

        for (int i = 0; i < dodgeBoard.length && !flag; i++) {
            if (dodgeBoard[i] == 1) {
                currentPosition = i;
                flag = true;
            }
        }

        newPosition = currentPosition + direction;
        if (newPosition >= dodgeBoard.length || newPosition < 0 || currentPosition == -1)
            return;

        for (int i = 0; i < dodgeBoard.length; i++) {
            if (i == newPosition)
                dodgeBoard[i] = 1;
            else
                dodgeBoard[i] = 0;
        }
    }

    public boolean isGameLost() {
        return getHitNum() == getLife();
    }

    public boolean gameWon() {
        return score == WON;
    }


    public int collisionDetection(int col) {
        if (obstacleBoard[obstacleBoard.length - 3][col] == 1 && dodgeBoard[col] == 1)
            return 1;
        else if (obstacleBoard[obstacleBoard.length - 3][col] == 2 && dodgeBoard[col] == 1) {
            return 2;
        } else
            return 0;
    }

    public void updateHits() {
        if (hitNum < life)
            hitNum++;
    }

    public int getLife() {
        return life;
    }

    public int[][] getObstacleBoard() {
        return obstacleBoard;
    }

    public int[] getDodgeBoard() {
        return dodgeBoard;
    }

    public int getHitNum() {
        return hitNum;
    }

    public void coinHitUpdate() {
        if (score <= 8999)
            score += 100;
    }

    public void updateScoreRegular() {
        if (score <= 9989)
            score += 10;

    }

    public void collisionScore() {
        if (score >= 50)
            score -= 50;
    }

    public int getScore() {
        return score;
    }

    public int getCols() {
        return COLS;
    }

    public int getRows() {
        return ROWS;
    }

}
