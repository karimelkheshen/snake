package com.karimelkheshen;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

/**
 * JavaFX App
 */
public class App extends Application {

    public enum Direction {
        UP, DOWN, RIGHT, LEFT
    }

    public static long INCREMENT_FACTOR = 10000L; // initially
    public static long GAMESPEED = 0L; // initially
    public static float GAMESPEED_COUNTER = 1f; // for user interface
    public static long FRAME_INTERVAL = 75000000L - GAMESPEED;
    public static int WIDTH = 500;
    public static int HEIGHT = 500;
    public static int GAME_WIDTH = 50;
    public static int GAME_HEIGHT = 50;
    public static boolean GAMEOVER = false;
    public static int SCORE = 0;
    public static int HIGHSCORE = 75;
    public static Direction currentDirection = Direction.LEFT; // initial direction of the snake
    private MainView mainView;

    @Override
    public void start(Stage stage) {

        HIGHSCORE = getHighscore();
        this.mainView = new MainView();
        Scene scene1 = new Scene(mainView, WIDTH, HEIGHT);
        stage.setScene(scene1);

        // key event control
        scene1.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.W && currentDirection != Direction.DOWN) {
                currentDirection = Direction.UP;
            }
            if (key.getCode() == KeyCode.A && currentDirection != Direction.RIGHT) {
                currentDirection = Direction.LEFT;
            }
            if (key.getCode() == KeyCode.S && currentDirection != Direction.UP) {
                currentDirection = Direction.DOWN;
            }
            if (key.getCode() == KeyCode.D && currentDirection != Direction.LEFT) {
                currentDirection = Direction.RIGHT;
            }
        });

        stage.show();

        Timer gameTimer = new Timer(stage);
        gameTimer.start();
    }

    private class Timer extends AnimationTimer {
        private long lastTime = 0;
        public Stage stage;

        public Timer(Stage stage) {
            this.stage = stage;
        }

        @Override
        public void handle(long now) {
            if (lastTime == 0) {
                lastTime = now;
                mainView.draw();
                mainView.controller.update();
                return;
            }
            if (now - lastTime > FRAME_INTERVAL) {
                if (GAMEOVER) {
                    this.stop();
                    MainView mainView2 = new MainView();
                    Scene scene2 = new Scene(mainView2);
                    stage.setScene(scene2);
                    stage.show();
                    mainView2.drawGameover();
                    updateHighscore();
                    return;
                }
                lastTime = now;
                mainView.draw();
                mainView.controller.update();
            }
        }
    }

    int getHighscore() {
       try {
           File fp = new File(".hsc.txt");
           if(fp.createNewFile()) {
               if (fp.setWritable(true) && fp.setReadable(true)) {
                   Writer w = new FileWriter(fp);
                   w.write(String.valueOf(0));
                   w.close();
                   return 0;
               }
           } else {
               Scanner sc = new Scanner(fp);
               if (sc.hasNextInt()) {
                int res = sc.nextInt();
                sc.close();
                return res;
               }
           }
       } catch (Exception e) {
           System.err.println("File operation failed.\n" + e);
       }
       return 0;
    }

    private void updateHighscore() {
        if (SCORE > HIGHSCORE) {
            HIGHSCORE = SCORE;
            try {
                File fp = new File(".hsc.txt");
                Writer w = new FileWriter(fp, false);
                w.write(String.valueOf(HIGHSCORE));
                w.close();
            } catch (Exception e) {
                System.err.println("File operation failed.\n" + e);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }

}