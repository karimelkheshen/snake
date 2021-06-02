package com.karimelkheshen;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;

public class MainView extends VBox {

    public final Canvas canvas;
    public final Controller controller;
    public final Affine affine = new Affine();
    public final GraphicsContext gc;

    public MainView() {
        this.canvas = new Canvas(App.WIDTH, App.HEIGHT);
        this.getChildren().add(this.canvas);
        this.controller = new Controller(App.GAME_WIDTH, App.GAME_HEIGHT);
        this.affine.appendScale((float) (App.WIDTH / controller.width), (float) (App.HEIGHT / controller.height));
        this.gc = this.canvas.getGraphicsContext2D();
    }

    // draw the game board and current score
    public void draw() {
        // scale up canvas
        this.gc.setTransform(this.affine);
        // draw background
        this.gc.setFill(Color.BLACK);
        this.gc.fillRect(0, 0, App.WIDTH, App.HEIGHT);
        // draw snake
        this.gc.setFill(Color.DARKGREEN);
        for (Point p : this.controller.snake)
            this.gc.fillRect(p.xVal, p.yVal, 1, 1);
        // draw fruit
        Point fruit = this.controller.fruit;
        this.gc.setFill(Color.RED);
        this.gc.fillRect(fruit.xVal, fruit.yVal, 1, 1);
        // draw score
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Menlo", FontWeight.EXTRA_BOLD, 1.2));
        gc.fillText("Score: " + App.SCORE, 1, 1.8);
        gc.fillText("Highscore: " + App.HIGHSCORE, 1, 3.1);
        gc.fillText("Speed: " + String.format("%.1f", App.GAMESPEED_COUNTER), 1, 4.4);
    }

    public void drawGameover() {
        // draw background
        this.gc.setFill(Color.BLACK);
        this.gc.fillRect(0, 0, App.WIDTH, App.HEIGHT);
        gc.setFill(Color.RED);
        gc.setFont(Font.font("Menlo", FontWeight.EXTRA_BOLD, 20));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(
                "GAMEOVER\n\n" + "Final Score : " + App.SCORE + "\n",
                Math.round(canvas.getWidth()  / 2),
                Math.round(canvas.getHeight() / 2)
        );
        if (App.SCORE > App.HIGHSCORE) {
            gc.setFill(Color.DARKORANGE);
            gc.setFont(Font.font("Menlo", FontWeight.EXTRA_BOLD, 22));
            gc.fillText("\n\n\n\nNEW HIGHSCORE",
                    Math.round(canvas.getWidth()  / 2),
                    Math.round(canvas.getHeight() / 2)
            );
        }
    }

}
