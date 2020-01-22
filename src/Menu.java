import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Menu {
    PFont indieFlower;
    PApplet processing;
    int gridSize;
    PImage Ximg;
    PImage Oimg;
    int startingX;
    int startingY;
    int speedX;
    int speedY;
    int[][] starting_points;
    int xEasy;
    int yEasy;
    int xHard;
    int yHard;
    int xStart;
    int yStart;
    int buttonWidth;
    int buttonHeight;


    Menu(int gridSize) {
        this.gridSize = gridSize;
        processing = Game.processing;
        indieFlower = processing.createFont("IndieFlower-Regular.ttf", 64);
        Ximg = Game.X;
        Oimg = Game.O;
        buttonHeight = 100;
        buttonWidth = 200;
        xEasy = 150;
        yEasy = gridSize - (gridSize / 2);
        xHard = gridSize - 50 -  buttonWidth;
        yHard = gridSize - (gridSize / 2);
        xStart = xEasy+ buttonWidth;
        yStart = yEasy+buttonHeight+40;


    }

    void startingScreen() {
        processing.background(255);
        processing.textFont(indieFlower);
        processing.textAlign(processing.CENTER);
        processing.fill(0);
        processing.text("Tic Tac Toe", gridSize / 2, gridSize / 7);
        processing.textSize(50);
        processing.text("Choose opponent and \ndifficulty level !", gridSize / 2, gridSize / 4);
        processing.textSize(64);
        processing.fill(255);
        processing.stroke(0);
        processing.strokeWeight(3);
        processing.rect(xEasy, yEasy, buttonWidth, buttonHeight);
        processing.rect(xHard, yHard, buttonWidth, buttonHeight);
        processing.rect(xStart, yStart, buttonWidth, buttonHeight);
        processing.fill(80);
        processing.text("AI:" , xEasy-80, yEasy+(buttonHeight/2)+15);
        processing.textAlign(processing.CENTER);
        processing.text("2 players:" , xEasy, yEasy+2*buttonHeight+10);
        processing.fill(0);
        processing.text("Easy", xEasy + buttonWidth/2, (yEasy+buttonHeight/2)+15);
        processing.text("Hard", xHard + buttonWidth/2, (yHard+buttonHeight/2)+20);
        processing.text("Start", xEasy+ 2*buttonWidth - buttonWidth/2, yEasy+2*buttonHeight+10);

        processing.textSize(15);
        processing.text("Made by Wiktor Kubis", gridSize / 2, gridSize - 35);
        processing.noFill();
        processing.noStroke();

    }

    void animation() {
        startingX = 120;
        startingY = 150;
        speedX = 3;
        speedY = 3;
        starting_points = new int[10][4];

        for (int i = 0; i < 10; i++) {
            starting_points[i][0] = (int) processing.random(0, gridSize-35);
            starting_points[i][1] = (int) processing.random(0, gridSize-35);
            starting_points[i][2] = (int) processing.random(1, 4);
            starting_points[i][3] = (int) processing.random(1, 4);
        }


    }


    void update() {
        for (int i = 0; i < 10; i++) {
            starting_points[i][0] += starting_points[i][2];
            starting_points[i][1] += starting_points[i][3];
            if (i < 5){
                processing.image(Ximg, starting_points[i][0], starting_points[i][1], 30, 30);
            } else {
                processing.image(Oimg, starting_points[i][0], starting_points[i][1], 30, 30);
            }
            if (starting_points[i][0] + 30 >= gridSize || starting_points[i][0] <= 0) {
                starting_points[i][2] *= -1;
            }
            if (starting_points[i][1] + 60 >= gridSize || starting_points[i][1] <= 0) {
                starting_points[i][3] *= -1;
            }
        }
    }

}
