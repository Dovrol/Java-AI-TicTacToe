import processing.core.PApplet;
import processing.core.PImage;
import processing.sound.*;


public class Game extends PApplet {
    private Grid grid;
    private Menu menu;
    private int gridSize = 700;
    static PApplet processing;
    private String sign;
    static  String difficulty;
    private boolean start = false;
    private boolean restart = false;
    private boolean startingScreen;
    static PImage O;
    static PImage X;
    private SoundFile soundFile;
    private SoundFile buttonCLick;
    private boolean twoPlayers;

    public void settings() {
        size(gridSize, gridSize);
        X = loadImage("X.png");
        O = loadImage("O.png");
        soundFile = new SoundFile(this, "music3.wav");
        buttonCLick = new SoundFile(this, "buttonClick.wav");
        processing = this;
        soundFile.play();
    }

    public void setup() {
        grid = new Grid(gridSize, 3);
        menu = new Menu(gridSize);
        startingScreen = true;
        menu.startingScreen();
        menu.animation();
        twoPlayers = false;
        sign = "X";
    }

    public void draw() {
        // Just starting screen and animation
        if (startingScreen){
            menu.startingScreen();
            menu.update();
            if (mouseX >= menu.xEasy && mouseX <= menu.xEasy+ menu.buttonWidth){
                if (mouseY >= menu.yEasy && mouseY <= menu.yEasy+ menu.buttonHeight) {
                    if (mousePressed){
                        start = true;
                        difficulty = "easy";
                        return;
                    }
                    stroke(0);
                    strokeWeight(3);
                    fill(220,225,233);
                    rect(menu.xEasy, menu.yEasy, menu.buttonWidth, menu.buttonHeight);
                    fill(0);
                    textSize(80);
                    text("Easy", menu.xEasy + menu.buttonWidth/2, (menu.yEasy+ menu.buttonHeight/2)+15);
                    noFill();
                }
            }

            if (mouseX >= menu.xHard && mouseX <= menu.xHard+ menu.buttonWidth){
                if (mouseY >= menu.yHard && mouseY <= menu.yHard+ menu.buttonHeight) {
                    if (mousePressed){
                        start = true;
                        difficulty = "hard";
                        return;
                    }
                    stroke(0);
                    strokeWeight(3);
                    fill(220,225,233);
                    rect(menu.xHard, menu.yHard, menu.buttonWidth, menu.buttonHeight);
                    fill(0);
                    textSize(80);
                    text("Hard", menu.xHard + menu.buttonWidth/2, (menu.yHard+ menu.buttonHeight/2)+15);
                    noFill();
                }
            }

            if (mouseX >= menu.xStart && mouseX <= menu.xStart+ menu.buttonWidth){
                if (mouseY >= menu.yStart && mouseY <= menu.yStart+ menu.buttonHeight) {
                    if (mousePressed){
                        start = true;
                        twoPlayers = true;
                        return;
                    }
                    stroke(0);
                    strokeWeight(3);
                    fill(220,225,233);
                    rect(menu.xStart, menu.yStart, menu.buttonWidth, menu.buttonHeight);
                    fill(0);
                    textSize(80);
                    text("Start", menu.xStart + menu.buttonWidth/2, (menu.yStart+ menu.buttonHeight/2)+15);
                    noFill();
                }
            }

            textSize(64);
        }

        // Start game
        if (startingScreen && start){
            background(255);
            fill(255);
            stroke(0);
            strokeWeight(3);
            grid.draw_grid();
            startingScreen = false;
        }

        // End game screen
        if (restart && keyPressed) {
            this.setup();
            restart = false;
//            delay(100);
        }
    }

    void endGame(String status) {
        // Drawing score
        if (!status.equals("")) {
            background(255);
            fill(0);
            textAlign(CENTER);
            textSize(100);
            if (status.equals("Draw")) {
                text("DRAW", gridSize / 2, gridSize / 2);
            } else {
                text(status + " WON", gridSize / 2, gridSize / 2);
            }
            textSize(50);
            text("Press any key to restart !", gridSize / 2, gridSize - (gridSize / 3));
            restart = true;
            start = false;
        }
    }


    public void mouseClicked() {
        if (!restart && !startingScreen && start) {
            stroke(255);
            strokeWeight(5);
            fill(0);
            if (grid.draw_figure(mouseX, mouseY, sign).equals("Valid")){
                buttonCLick.play();
                if (!twoPlayers){
                    grid.makeMove();
                } else{
                    if (sign.equals("X")) sign = "O"; else sign = "X";
                }
                String status = grid.checkWinner();
                endGame(status);
            }
        }
        noStroke();
    }

    public static void main(String[] args) {
        PApplet.main("Game");
    }

}
