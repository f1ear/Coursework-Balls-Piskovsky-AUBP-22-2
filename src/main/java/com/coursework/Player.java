// Player.java: Класс, представляющий управление игрока
package com.coursework;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class Player {
    // Поля класса
    private boolean isShooting;

    public Player(Scene scene) {
        // Обработка событий нажатия клавиш игроком
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                isShooting = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                isShooting = false;
            }
        });
    }

    // Геттеры и сеттеры
    public boolean isShooting() {
        return isShooting;
    }

    // Добавьте этот метод, чтобы контролировать, когда игрок стреляет
    public void setShooting(boolean isShooting) {
        this.isShooting = isShooting;
    }
}
