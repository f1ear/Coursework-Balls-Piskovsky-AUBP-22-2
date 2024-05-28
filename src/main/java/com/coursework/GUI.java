// GUI.java: Класс для вывода графического меню
package com.coursework;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI {
    private static final String BACKGROUND_COLOR = "#394963";
    private static final String LABEL_GAME = "the BALLS";

    public GUI(Stage menuStage) {
        // Создание элементов интерфейса и установка обработчиков событий для кнопок
        Text nameLabel = new Text(LABEL_GAME);
        nameLabel.setFont(new Font("Segoe Print", 28));
        nameLabel.setFill(Color.WHITE);

        Button playAI = new Button("ИИ");
        Button playPlayer = new Button("Игрок");

        // Установка свойств кнопок
        playAI.setPrefWidth(200);
        playAI.setPrefHeight(50);
        playAI.setFont(new Font("Calibri", 16));

        playPlayer.setPrefWidth(200);
        playPlayer.setPrefHeight(50);
        playPlayer.setFont(new Font("Calibri", 16));

        // Обработка событий кнопок и запуск игры
        playAI.setOnAction(e -> startGame(menuStage, true));
        playPlayer.setOnAction(e -> startGame(menuStage, false));

        // Создание компоновки элементов интерфейса и их размещение на главной сцене
        VBox nameLabelBox = new VBox(10, nameLabel);
        nameLabelBox.setAlignment(Pos.CENTER);
        nameLabelBox.setPadding(new Insets(10, 0, 0, 0));

        VBox buttonBox = new VBox(10, playAI, playPlayer);
        buttonBox.setAlignment(Pos.CENTER);

        BorderPane rootPane = new BorderPane();
        rootPane.setTop(nameLabelBox);
        rootPane.setCenter(buttonBox);
        rootPane.setAlignment(nameLabel, Pos.TOP_CENTER);

        // Установка стиля фона и отображение главной сцены
        Scene mainScene = new Scene(rootPane, 500, 600);
        rootPane.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        // Установка названия окна приложения и его сцены
        menuStage.setTitle(LABEL_GAME);
        menuStage.setScene(mainScene);
        menuStage.show();
    }

    private void startGame(Stage stage, boolean isAI) {
        // Запуск игрового окна с заданным режимом игры
        new GameBoard(stage, isAI);
    }
}
