// AIPlayer.java: Класс, представляющий искусственный интеллект игрока
package com.coursework;

public class AIPlayer {
    public static boolean shouldShoot(long now) {
        // Метод, определяющий когда ИИ должен стрелять
        return now % 2000000000 < 10000000;
    }
}
