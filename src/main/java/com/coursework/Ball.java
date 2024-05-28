// Ball.java: Класс, представляющий шар
package com.coursework;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball {
    // Поля класса
    private Circle circle;
    private double velocityX;
    private double velocityY;
    private static final double SPEED = 5;

    public Ball(double x, double y, double radius) {
        // Создание объекта шара с указанными параметрами
        circle = new Circle(x, y, radius);
        circle.setFill(Color.RED);
        velocityX = 0;
        velocityY = 0;
    }
    // Геттеры и сеттеры
    public Circle getCircle() {
        return circle;
    }

    public void shoot(double angle) {
        velocityX = SPEED * Math.cos(Math.toRadians(angle));
        velocityY = -SPEED * Math.sin(Math.toRadians(angle));
    }

    public void update() {
        circle.setCenterX(circle.getCenterX() + velocityX);
        circle.setCenterY(circle.getCenterY() + velocityY);
    }

    public void reverseX() {
        velocityX = -velocityX;
    }

    public void reverseY() {
        velocityY = -velocityY;
    }

    public void reset(double x, double y) {
        circle.setCenterX(x);
        circle.setCenterY(y);
        velocityX = 0;
        velocityY = 0;
    }
}
