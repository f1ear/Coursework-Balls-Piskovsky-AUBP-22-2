// GameBoard.java: Класс, представляющий игровое поле и управление игрой
package com.coursework;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class GameBoard {
    // Поля класса
    private Pane gamePane;
    private boolean isAI;
    private Ball ball;
    private ArrayList<Brick> bricks;
    private Player player;
    private AIPlayer aiPlayer;
    private static final String BACKGROUND_COLOR = "#394963";
    private double launchAngle = 0;
    private Line launchLine;
    private Scene gameScene;
    private AnimationTimer timer;

    public GameBoard(Stage stage, boolean isAI) {
        // Инициализация игрового поля и настройка сцены
        this.isAI = isAI;
        gamePane = new Pane();
        gameScene = new Scene(gamePane, 500, 600);
        gamePane.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");
        stage.setScene(gameScene);
        stage.show();

        // Инициализация игры и запуск игрового цикла
        initializeGame(gameScene);
        startGameLoop();
    }

    private void initializeGame(Scene gameScene) {
        // Инициализация игровых объектов (шара, кирпичей, игрока)
        if (isAI) {
            aiPlayer = new AIPlayer();
        } else {
            player = new Player(gameScene);
        }

        ball = new Ball(250, 550, 5);
        gamePane.getChildren().add(ball.getCircle());

        launchLine = new Line();
        gamePane.getChildren().add(launchLine);
        updateLaunchLine();

        // Обработка нажатий клавиш и запуск уровня
        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    launchAngle = (launchAngle + 5) % 360;
                    updateLaunchLine();
                    System.out.println("Left pressed");
                    break;
                case RIGHT:
                    launchAngle = (launchAngle - 5) % 360;
                    updateLaunchLine();
                    System.out.println("Right pressed");
                    break;
                case SPACE:
                    ball.shoot(launchAngle);
                    System.out.println("Space pressed");
                    break;
                default:
                    break;
            }
        });

        startLevel(1);
    }

    private void startLevel(int level) {
        // Начало нового уровня игры
        gamePane.getChildren().clear();

        // Генерация кирпичей для уровня
        generateBricks(level);
        ball.reset(250, 550);
        gamePane.getChildren().add(ball.getCircle());
        launchLine.setVisible(true);
        launchAngle = 45;
        updateLaunchLine();
    }

    private void generateBricks(int level) {
        // Генерация кирпичей на игровом поле
        bricks = new ArrayList<>();
        Random random = new Random();
        int rows = 3;
        int cols = 5;
        int brickSize = 50;
        int padding = 10;
        int startX = (int) (gamePane.getWidth() - (cols * (brickSize + padding))) / 2;

        for (int row = 0; row < rows; row++) {
            int y = row * (brickSize + padding) + 50 - level * (brickSize + padding);
            for (int col = 0; col < cols; col++) {
                int x = startX + col * (brickSize + padding);
                int hitPoints = random.nextInt(30) + 10;
                Brick brick = new Brick(x, y, brickSize, hitPoints);
                bricks.add(brick);
                gamePane.getChildren().add(brick.getRectangle());
                gamePane.getChildren().add(brick.getHealthText());
            }
        }
    }

    private void updateLaunchLine() {
        // Обновление линии запуска шара
        double ballX = ball.getCircle().getCenterX();
        double ballY = ball.getCircle().getCenterY();
        double endX = ballX + Math.cos(Math.toRadians(launchAngle)) * 50;
        double endY = ballY - Math.sin(Math.toRadians(launchAngle)) * 50;
        System.out.println("Launch Line Start: (" + ballX + ", " + ballY + ")");
        System.out.println("Launch Line End: (" + endX + ", " + endY + ")");
        launchLine.setStartX(ballX);
        launchLine.setStartY(ballY);
        launchLine.setEndX(endX);
        launchLine.setEndY(endY);
        launchLine.setStroke(Color.WHITE);
        gamePane.getChildren().remove(launchLine);
        gamePane.getChildren().add(launchLine);
    }

    private void startGameLoop() {
        // Запуск игрового цикла
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
                if (!isAI) {
                    playerControl();
                } else {
                    aiControl(now);
                }
            }
        };
        timer.start();
    }

    private void updateGame() {
        // Обновление игрового состояния
        ball.update();

        for (Brick brick : bricks) {
            if (brick != null && ball.getCircle().getBoundsInParent().intersects(brick.getRectangle().getBoundsInParent())) {
                System.out.println("Collision with brick!");
                double ballCenterX = ball.getCircle().getCenterX();
                double ballCenterY = ball.getCircle().getCenterY();
                double brickLeft = brick.getRectangle().getX();
                double brickRight = brickLeft + brick.getRectangle().getWidth();
                double brickTop = brick.getRectangle().getY();
                double brickBottom = brickTop + brick.getRectangle().getHeight();

                if (ballCenterX >= brickLeft && ballCenterX <= brickRight) {
                    ball.reverseY();
                }
                if (ballCenterY >= brickTop && ballCenterY <= brickBottom) {
                    ball.reverseX();
                }

                if (Math.pow(ballCenterX - brickLeft, 2) + Math.pow(ballCenterY - brickTop, 2) <= Math.pow(ball.getCircle().getRadius(), 2) ||
                    Math.pow(ballCenterX - brickRight, 2) + Math.pow(ballCenterY - brickTop, 2) <= Math.pow(ball.getCircle().getRadius(), 2) ||
                    Math.pow(ballCenterX - brickLeft, 2) + Math.pow(ballCenterY - brickBottom, 2) <= Math.pow(ball.getCircle().getRadius(), 2) ||
                    Math.pow(ballCenterX - brickRight, 2) + Math.pow(ballCenterY - brickBottom, 2) <= Math.pow(ball.getCircle().getRadius(), 2)) {
                    ball.reverseX();
                    ball.reverseY();
                }

                brick.hit();
                if (brick.getHitPoints() <= 0) {
                    gamePane.getChildren().remove(brick.getRectangle());
                    gamePane.getChildren().remove(brick.getHealthText());
                }
            }
        }

        if (ball.getCircle().getCenterX() <= 0 || ball.getCircle().getCenterX() >= gamePane.getWidth()) {
            ball.reverseX();
        }
        if (ball.getCircle().getCenterY() <= 0 || ball.getCircle().getCenterY() >= gamePane.getHeight()) {
            ball.reverseY();
        }
    }

    private void playerControl() {
        // Управление игроком
        if (player.isShooting()) {
            ball.shoot(launchAngle);
        }
    }

    private void aiControl(long now) {
        // Управление ИИ
        if (AIPlayer.shouldShoot(now)) {
            Random random = new Random();
            double angle = random.nextDouble() * 180;
            ball.shoot(angle);
        }
    }
}
