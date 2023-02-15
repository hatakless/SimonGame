import java.util.*;
import javax.swing.*;
import java.awt.Color;

public class Game {
    private int sequenceLength = 1;
    private Vector<Flash> sequenceColors;
    private List<Observe> observers;
    int score;
    boolean isGameOver = false;

    /*
    Текущая длинна цветов.
    Цвета
    Сохранение
    Счет
    Конец игры
     */
    public Game() {
        observers = new ArrayList<Observe>();
        sequenceColors = new Vector<Flash>();

    }

    /*
   Объект для Симона
     */
    public void play() {

        for (int i = 0; i < sequenceLength; i++) {
            int randomNumber = new Random().nextInt(0, 4);
            mapColor(randomNumber);
        }
        notifyObservers();
    }

    /*
       Игра начинается тут. Задает, уведомляет, отображает. Рандом 4 цифр(цветов)
     */
    private void mapColor(int randomColor) {
        if (randomColor == 0)
            sequenceColors.add(Flash.Red);
        else if (randomColor == 1)
            sequenceColors.add(Flash.Blue);
        else if (randomColor == 2)
            sequenceColors.add(Flash.Green);
        else if (randomColor == 3)
            sequenceColors.add(Flash.Yellow);
    }

    /*
   Генерация рандома
     */
    private boolean compareUserInput(Vector<Flash> userInput) {
        boolean result = true;
        for (int i = 0; i < userInput.size(); i++) {
            if (userInput.get(i) != sequenceColors.get(i)) {
                result = false;
            }
        }
        return result;
    }

    /*
        Если попал на кнопку правильно то молодец. !!
     */
    private void gameOver() {
        score = 0;
        isGameOver = true;
        notifyObservers();
    }

    /*
        Если не попал то пока
     */
    public void checkUserInput(Vector<Flash> userInput) {
        if (compareUserInput(userInput)) {
            score = score + 1;
            play();
        } else
            gameOver();
    }

    /*
        Счет и проверка.
     */
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            Observe observer = observers.get(i);
            observer.nextColorSequence(sequenceColors);
            observer.turnOver();
            observer.gameOver(isGameOver);
            observer.updateScore(score);
        }
    }

    /*
        Обновление Обсерва. Перезапуск игры.
     */
    public void registerObserver(Observe o) {
        if (!observers.contains(0))
            observers.add(o);
        else
            throw new IllegalArgumentException();
    }

    //Прерывание операции, чтобы пустая строка не нарушала код. Поток.
    public void removeObserver(Observe o) {
        int i = observers.indexOf(o);
        if (i >= 0)
            observers.remove(i);
        else
            throw new IllegalArgumentException();
    }

    public void restart() {
        sequenceLength = 1;
        sequenceColors.clear();
        score = 0;
        isGameOver = false;
        play();
    }
    /*
        для обсерва штука не особо поняла но без нее не рабит.
     */
/*
    private static void displayInstructions() {
        System.out.println("\n" +
                "███████╗██╗███╗   ███╗ ██████╗ ███╗   ██╗\n" +
                "██╔════╝██║████╗ ████║██╔═══██╗████╗  ██║\n" +
                "███████╗██║██╔████╔██║██║   ██║██╔██╗ ██║\n" +
                "╚════██║██║██║╚██╔╝██║██║   ██║██║╚██╗██║\n" +
                "███████║██║██║ ╚═╝ ██║╚██████╔╝██║ ╚████║\n" +
                "╚══════╝╚═╝╚═╝     ╚═╝ ╚═════╝ ╚═╝  ╚═══╝\n" +
                "                                         \n");

 */
}