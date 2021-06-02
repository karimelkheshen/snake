package com.karimelkheshen;

import java.util.LinkedList;

public class Controller {

    public int height;
    public int width;
    public Point fruit;
    public LinkedList<Point> snake = new LinkedList<>();

    public Controller(int width, int height) {
        this.width = width;
        this.height = height;
        // Initialise the first positions of the snake and the fruit
        initialiseRandomSnakeFruitPositions();
        Point head = this.snake.get(0);
        this.snake.add(new Point(head.xVal+1, head.yVal));
        this.snake.add(new Point(head.xVal+2, head.yVal));
        this.snake.add(new Point(head.xVal+2, head.yVal));
    }

    public void initialiseRandomSnakeFruitPositions() {
        Point snakeHead = new Point();
        snakeHead.setRandxyVal(15, this.width - 15 - 1); // width=height
        Point fruit = new Point();
        do {
            fruit.setRandxyVal(1, width - 1 - 1);
        } while (snakeFruitOverlap());
        this.snake.add(snakeHead);
        this.fruit = fruit;
    }

    private boolean snakeFruitOverlap() {
        for (Point p : this.snake) {
            if (p.isEqualTo(this.fruit))
                return true;
        }
        return false;
    }

    // checks if snake self-overlaps or hits boundary
    private boolean isGameover() {
        Point head = this.snake.get(0);
        for (int i = 1; i < this.snake.size(); i++) {
            if (this.snake.get(i).isEqualTo(head))
                return true;
        }
        if (head.xVal == 0 && App.currentDirection == App.Direction.LEFT)
            return true;
        else if (head.xVal == this.width - 1 && App.currentDirection == App.Direction.RIGHT)
            return true;
        else if (head.yVal == 0 && App.currentDirection == App.Direction.UP)
            return true;
        else
            return head.yVal == this.height - 1 && App.currentDirection == App.Direction.DOWN;
    }

    private boolean isGamepoint() {
        Point p = this.snake.get(0);
        if (p.isEqualTo(this.fruit)) {
            // change the position of the fruit
            do {
                fruit.setRandxyVal(1, width - 1 - 1);
            } while (snakeFruitOverlap());
            return true;
        }
        return false;
    }

    // check for gameover or gamepoint states and update snake position wrt current
    // App direction
    public void update() {
        if (this.isGameover()) {
            App.GAMEOVER = true;
            return;
        }
        if (this.isGamepoint()) {
            App.SCORE++;
            App.GAMESPEED_COUNTER+=0.1;
            App.GAMESPEED = App.SCORE * App.INCREMENT_FACTOR + 100000L;
            App.FRAME_INTERVAL -= App.GAMESPEED;
            // head insert new snake element
            LinkedList<Point> newSnake = new LinkedList<>();
            Point currentSnakeHead = new Point(this.snake.get(0));
            if (App.currentDirection == App.Direction.UP)
                newSnake.add(new Point(currentSnakeHead.xVal, currentSnakeHead.yVal - 1));
            else if (App.currentDirection == App.Direction.DOWN)
                newSnake.add(new Point(currentSnakeHead.xVal, currentSnakeHead.yVal + 1));
            else if (App.currentDirection == App.Direction.RIGHT)
                newSnake.add(new Point(currentSnakeHead.xVal + 1, currentSnakeHead.yVal));
            else
                newSnake.add(new Point(currentSnakeHead.xVal - 1, currentSnakeHead.yVal));
            for (Point p : this.snake)
                newSnake.add(p);
            this.snake = newSnake;
            return;
        }
        // update the position of the snake
        LinkedList<Point> newSnake = new LinkedList<>();
        for (int i = 0; i < this.snake.size(); i++) {
            if (i == 0) {
                Point currentPoint = new Point(this.snake.get(i));
                if (App.currentDirection == App.Direction.UP)
                    newSnake.add(new Point(currentPoint.xVal, currentPoint.yVal - 1));
                else if (App.currentDirection == App.Direction.DOWN)
                    newSnake.add(new Point(currentPoint.xVal, currentPoint.yVal + 1));
                else if (App.currentDirection == App.Direction.RIGHT)
                    newSnake.add(new Point(currentPoint.xVal + 1, currentPoint.yVal));
                else
                    newSnake.add(new Point(currentPoint.xVal - 1, currentPoint.yVal));
                continue;
            }
            newSnake.add(this.snake.get(i - 1));
        }
        this.snake = newSnake;
    }

}