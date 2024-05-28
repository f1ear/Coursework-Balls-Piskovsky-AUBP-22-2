// App.java: стартовая точка приложения
package com.coursework;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage menuStage) {
        // Создание объекта GUI при запуске приложения
        GUI gui = new GUI(menuStage);
    }

    public static void main(String[] args) {
        // Запуск приложения JavaFX
        launch(args);
    }
}
