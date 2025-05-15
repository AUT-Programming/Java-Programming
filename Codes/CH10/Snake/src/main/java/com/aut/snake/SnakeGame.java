package com.aut.snake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * A Snake game in JavaFX
 * Controls: Arrow keys to move. Eat food to grow. Avoid walls and self.
 */
public class SnakeGame extends Application {
    private static final int WIDTH = 600, HEIGHT = 600;
    private static final int BLOCK = 20;
    private static final int COLS = WIDTH / BLOCK, ROWS = HEIGHT / BLOCK;
    private static final double SPEED = 5.0; // blocks per second

    private LinkedList<Point> snake;
    private List<Point> prevSnake;
    private Direction dir;
    private Point food;
    private boolean running;
    private int score;

    private double moveTimer;
    private final Random rnd = new Random();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Connected Snake");
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        initGame();

        new AnimationTimer() {
            private long last = 0;
            @Override
            public void handle(long now) {
                if (last == 0) last = now;
                double delta = (now - last) / 1e9;
                last = now;
                if (running) {
                    updateLogic(delta);
                    checkCollisions();
                }
                render(gc);
            }
        }.start();

        Scene scene = new Scene(new StackPane(canvas));
        scene.setOnKeyPressed(this::handleKey);
        stage.setScene(scene);
        stage.show();
    }

    private void initGame() {
        snake = new LinkedList<>();
        for (int i = 0; i < 5; i++) snake.add(new Point(COLS / 2 - i, ROWS / 2));
        prevSnake = new ArrayList<>(snake);
        dir = Direction.RIGHT;
        spawnFood();
        score = 0;
        running = true;
        moveTimer = 0;
    }

    private void spawnFood() {
        Point p;
        do { p = new Point(rnd.nextInt(COLS), rnd.nextInt(ROWS)); }
        while (snake.contains(p));
        food = p;
    }

    private void updateLogic(double delta) {
        moveTimer += delta;
        double interval = 1.0 / SPEED;
        if (moveTimer >= interval) {
            moveTimer -= interval;
            prevSnake = new ArrayList<>(snake);
            Point head = snake.getFirst();
            Point next = switch (dir) {
                case UP -> new Point(head.x, head.y - 1);
                case DOWN -> new Point(head.x, head.y + 1);
                case LEFT -> new Point(head.x - 1, head.y);
                case RIGHT -> new Point(head.x + 1, head.y);
            };
            snake.addFirst(next);
            if (next.equals(food)) { score += 10; spawnFood(); }
            else snake.removeLast();
        }
    }

    private void checkCollisions() {
        Point head = snake.getFirst();
        if (head.x < 0 || head.x >= COLS || head.y < 0 || head.y >= ROWS) gameOver();
        for (int i = 1; i < snake.size(); i++) if (head.equals(snake.get(i))) { gameOver(); break; }
    }

    private void gameOver() {
        running = false;
        new Thread(() -> {
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            javafx.application.Platform.runLater(this::initGame);
        }).start();
    }

    private void render(GraphicsContext gc) {
        // background
        gc.setFill(Color.web("#0d1b2a")); gc.fillRect(0, 0, WIDTH, HEIGHT);
        // grid
        gc.setStroke(Color.web("#1b263b")); gc.setLineWidth(1);
        for (int i = 0; i <= COLS; i++) gc.strokeLine(i * BLOCK, 0, i * BLOCK, HEIGHT);
        for (int i = 0; i <= ROWS; i++) gc.strokeLine(0, i * BLOCK, WIDTH, i * BLOCK);
        // food
        gc.setFill(Color.web("#e63946")); gc.fillOval(food.x * BLOCK + 2, food.y * BLOCK + 2, BLOCK - 4, BLOCK - 4);

        // interpolation factor
        double t = moveTimer * SPEED;
        // compute segment centers
        List<double[]> coords = new ArrayList<>();
        for (int i = 0; i < snake.size(); i++) {
            Point curr = snake.get(i);
            Point prev = i < prevSnake.size() ? prevSnake.get(i) : curr;
            double cx = (prev.x * (1 - t) + curr.x * t) * BLOCK + BLOCK / 2.0;
            double cy = (prev.y * (1 - t) + curr.y * t) * BLOCK + BLOCK / 2.0;
            coords.add(new double[]{cx, cy});
        }

        // draw smooth connected body with rounded joins
        gc.setStroke(Color.web("#457b9d"));
        gc.setLineWidth(BLOCK - 2);
        gc.setLineCap(javafx.scene.shape.StrokeLineCap.ROUND);
        gc.setLineJoin(javafx.scene.shape.StrokeLineJoin.ROUND);
        gc.beginPath();
        for (int i = 0; i < coords.size(); i++) {
            double[] p = coords.get(i);
            if (i == 0) gc.moveTo(p[0], p[1]); else gc.lineTo(p[0], p[1]);
        }
        gc.stroke();

        // draw head
        double[] head = coords.get(0);
        gc.setFill(Color.web("#a8dadc"));
        gc.fillOval(head[0] - BLOCK / 2 + 1, head[1] - BLOCK / 2 + 1, BLOCK - 2, BLOCK - 2);

        // score
        gc.setFill(Color.web("#f1faee")); gc.setFont(Font.font(18));
        gc.fillText("Score: " + score, 10, 20);
    }

    private void handleKey(KeyEvent e) {
        KeyCode c = e.getCode();
        if (c == KeyCode.UP && dir != Direction.DOWN) dir = Direction.UP;
        if (c == KeyCode.DOWN && dir != Direction.UP) dir = Direction.DOWN;
        if (c == KeyCode.LEFT && dir != Direction.RIGHT) dir = Direction.LEFT;
        if (c == KeyCode.RIGHT && dir != Direction.LEFT) dir = Direction.RIGHT;
    }

    public static void main(String[] args) { launch(args); }

    private static class Point { int x, y; Point(int x, int y) { this.x = x; this.y = y; }
        @Override public boolean equals(Object o) { return o instanceof Point p && p.x == x && p.y == y; }
        @Override public int hashCode() { return x * 31 + y; }
    }

    private enum Direction { UP, DOWN, LEFT, RIGHT; }
}