import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Vector;
import javax.swing.*;
import javax.swing.border.LineBorder;
public class Ui extends JFrame implements Observe,ActionListener{
    Game simon;
    JPanel colorButtonsPanel, controlButtonsPanel, scoreBoard;
    JButton quitButton, restartButton;
    JButton redButton, blueButton, greenButton, yellowButton;
    Vector<JButton> colorButtons;
    JLabel labelScore, score;
    Vector<Flash> buttonsPressed;
    int countUserInput;
    int sequenceLength;
    boolean flashButtonInProcess = false;

    public Ui(Game simongame) {
        simon = simongame;
        getContentPane().setLayout(new GridLayout(0, 1));
        colorButtonsPanel = new JPanel();
        controlButtonsPanel = new JPanel();
        scoreBoard = new JPanel();
        getContentPane().add(colorButtonsPanel);
        getContentPane().add(controlButtonsPanel);
        getContentPane().add(scoreBoard);
        colorButtons = new Vector<JButton>();
        buttonsPressed = new Vector<Flash>();

        redButton = new JButton();
        putBottonOnPanel(redButton,Color.red);

        blueButton = new JButton();
        putBottonOnPanel(blueButton,Color.blue);

        greenButton = new JButton();
        putBottonOnPanel(greenButton,Color.green);

        yellowButton = new JButton();
        putBottonOnPanel(yellowButton,Color.yellow);
        /*
        Кнопки и их знач.
         */
        for(int i = 0; i < colorButtons.size() ; i++)
            colorButtonsPanel.add(colorButtons.get(i));
        /*
            Размер.
         */
             quitButton = new JButton("EXIT");
             quitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
        /*
            Кнопка Выхода.
         */
        restartButton = new JButton("Попробовать снова");
        restartButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                restart();
            }
        });
        /*
            Перезапуск. АККУРАТНО ЕСЛИ ЧАСТО НАЖИМАТЬ КАПУТ МАШИНА
         */
        controlButtonsPanel.add(quitButton);
        controlButtonsPanel.add(restartButton);

        //Табло
        labelScore = new JLabel("SCORE:");
        score = new JLabel("0");
        scoreBoard.add(labelScore);
        scoreBoard.add(score);
        /*
            Кнопочки и надписиси
         */

        setSize(600,350);
        setTitle("S I M O N");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Положение окна на экране. ( ей богу наобум положение)
    }

    public void nextColorSequence(final Vector<Flash> sequenceColors)
    {
        Thread thread = new Thread("Новый поток")
        {
            public void run()
            {
                flashButtonInProcess = true;
                displayColorSequence(sequenceColors);
                flashButtonInProcess = false;
            }
        };
        /*
            Новый поток, чтобы ниче не блокировал урод один и не будет машина работать.
         */
        thread.start();
    }
    private void putBottonOnPanel(JButton button,Color color)
    {
        button.setPreferredSize(new Dimension(70, 70));
        button.setBorder(new LineBorder(color,2));
        button.addActionListener(this);
        colorButtons.add(button);
    }
    //Кнопки, размер и функция
    private void displayColorSequence(Vector<Flash> sequenceColors)
    {
        sequenceLength = sequenceColors.size();
        for(int i = 0; i < sequenceLength; i++)
        {
            Flash flashcard = sequenceColors.get(i);
            if(flashcard == Flash.Red)
                flashButton(redButton,Color.red);
            else if(flashcard == Flash.Blue)
                flashButton(blueButton,Color.blue);
            else if(flashcard == Flash.Green)
                flashButton(greenButton,Color.green);
            else if(flashcard == Flash.Yellow)
                flashButton(yellowButton,Color.yellow);

        }
        /*
               Типа если красная кнопка то это красныый цвет клево.
               Последовательность, заданная заранее.
         */
    }

    private void flashButton(JButton button,Color color)
    {
        button.setBackground(color);
        try
        {
            Thread.sleep(1000);
        }
        /*
            Мигание кнопки и если мигнуло дважды одна то там 500
         */
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        button.setBackground(null);
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void restart()
    {
        buttonsPressed.clear();
        countUserInput = 0;
        simon.restart();
    }
    /*
        рестартик
     */
    public void actionPerformed(ActionEvent e)
    {
        if(!flashButtonInProcess)
        {
            JButton button = (JButton) e.getSource();

            if(button == redButton)
                buttonsPressed.add(Flash.Red);
            else if(button == blueButton)
                buttonsPressed.add(Flash.Blue);
            else if(button == greenButton)
                buttonsPressed.add(Flash.Green);
            else if(button == yellowButton)
                buttonsPressed.add(Flash.Yellow);

            countUserInput = countUserInput + 1;
            if(countUserInput == sequenceLength)
                simon.checkUserInput(buttonsPressed);
        }
    }
    /*
     ЦЙвет выбранной кнопки пользователем и заданная.ВХОД
     */

    public void turnOver()
    {
        buttonsPressed.clear();
        countUserInput = 0;
    }
    /*
        Исходное значение счета ввода
     */
    public void gameOver(boolean isGameOver)
    {
        if(isGameOver)
        {
            JOptionPane.showMessageDialog(this,"Вы проиграли");
            restart();
        }
    }
    /*
        рЫЖИЙ ПРОИГРАЛ
     */
    public void updateScore(int score)
    {
        this.score.setText(Integer.valueOf(score).toString());
    }
}
/*
        Тщетная попытка обновление
 */
