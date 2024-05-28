// Brick.java: Класс, представляющий блок
package com.coursework;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Brick {
    // Поля класса
    private Rectangle rectangle;
    private Text healthText;
    private int hitPoints;

    public Brick(double x, double y, double size, int hitPoints) {
        // Создание объекта кирпича с указанными параметрами
        this.hitPoints = hitPoints;
        rectangle = new Rectangle(x, y, size, size);
        rectangle.setFill(Color.BLUE);
        
        healthText = new Text(x + size / 2, y + size / 2, String.valueOf(hitPoints));
        healthText.setFill(Color.WHITE);
    }

    // Геттеры и сеттеры
    public Rectangle getRectangle() {
        return rectangle;
    }

    public Text getHealthText() {
        return healthText;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void hit() {
        hitPoints--;
        healthText.setText(String.valueOf(hitPoints));
    }
}
