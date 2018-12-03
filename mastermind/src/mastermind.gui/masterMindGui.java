package mastermind.gui;

import mastermind.MasterMindGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import mastermind.MasterMindGame.*;

public class masterMindGui extends JFrame {

    private static final int TIMES = 20;
    private static final int SIZE = 6;
    private int turnTaken;
    private int chanceTaken;
    MasterMindGame masterMindGame;
    private final java.util.List<Color> colorIndex = new ArrayList<>(Arrays.asList(Color.blue,Color.GREEN,Color.cyan,Color.orange,
            Color.pink,Color.RED,Color.YELLOW,Color.WHITE,Color.darkGray,Color.magenta));
    private java.util.List<java.util.List<ColorShowCell>> chooseColors = new ArrayList<>();
    private java.util.List<java.util.List<ColorShowCell>> resultColors = new ArrayList<>();
    private java.util.List<Integer> guessColors;

    public void setInitialFrame(){
        setTitle("Game GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.frameInit();
        masterMindGame = new MasterMindGame();
        java.util.List<Integer> randomColors = masterMindGame.selectRandomDistinctColorIndices(10, 6, (int)(System.currentTimeMillis() / 1000));
        turnTaken = 0;
        chanceTaken = 0;
        masterMindGame.setColorSelectionIndices(randomColors);
        guessColors = new ArrayList<>();
        setLayout(new BorderLayout());

    }


    public masterMindGui()
    {
        setInitialFrame();
        JPanel colorPanel = addColorPoolPanel();
        setColorPanel(colorPanel);
        JButton giveUpButton = new JButton("Give Up");
        giveUpButton.addActionListener(new giveUpClickedHandler());
        getContentPane().add(giveUpButton, BorderLayout.NORTH);
        JPanel resultPanel = addResultPanel();
        JPanel choosePanel = addChoosePanel();

        for (int i = 0; i < TIMES; i++) {
            java.util.List<ColorShowCell> rowColors = new ArrayList<>();
            java.util.List<ColorShowCell> rowResultColors = new ArrayList<>();
            for (int j = 0; j < SIZE; j++) {
                ColorShowCell colorShowCell = new ColorShowCell(i, j);
                rowColors.add(colorShowCell);
                choosePanel.add(colorShowCell);

                ColorShowCell colorShowCellResult = new ColorShowCell(i, j);
                rowResultColors.add(colorShowCellResult);
                resultPanel.add(colorShowCellResult);
            }
            chooseColors.add(rowColors);
            resultColors.add(rowResultColors);
        }
    }

     public static void main(String[] args)
     {
         JFrame frame = new masterMindGui();
         frame.setSize(450,600);
         frame.setVisible(true);
     }

     public JPanel addColorPoolPanel()
     {
         JPanel colorPanel = new JPanel();
         add(colorPanel, BorderLayout.SOUTH);
         colorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
         return colorPanel;
     }

     public void setColorPanel(JPanel colorPanel)

     {

        JLabel colorPool = addJlabel(colorPanel,"Color:");

         for(int i=0; i<10; i++)
         {
             ColorButton colorButton = new ColorButton(i, colorIndex.get(i));
             colorPanel.add(colorButton);
             colorButton.addActionListener(new colorClickedHandler());
         }
     }

     public JLabel addJlabel(JPanel panel,String s)
     {
         JLabel newLable = new JLabel(s);
         newLable.setFont(new Font("Tahoma", Font.PLAIN, 15));
         newLable.setAlignmentX(SwingConstants.CENTER);
         panel.add(newLable);
         return newLable;

     }


    public JPanel addResultPanel()
    {
        JPanel Panel = new JPanel();
        add(Panel, BorderLayout.EAST);
        Panel.setLayout(new GridLayout(21,6,3,3));
        return Panel;
    }


    public JPanel addChoosePanel()
    {
        JPanel Panel = new JPanel();
        add(Panel, BorderLayout.WEST);
        Panel.setLayout(new GridLayout(21,6,3,3));
        return Panel;
    }

    private class colorClickedHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            turnTaken++;
            ColorButton colorButton = (ColorButton) actionEvent.getSource();
            Color theColor = colorIndex.get(colorButton.index);
            guessColors.add(colorButton.index);
            chooseColors.get(chanceTaken).get(turnTaken - 1).changeColor(theColor);
            if (turnTaken == 6) {
                Map<Result, Integer> guessResult = masterMindGame.guess(guessColors);
                Status status = masterMindGame.getStatus();
                if (status == Status.WON) {
                    JOptionPane.showMessageDialog(colorButton, "You Win!");
                    setInitialFrame();
                }
                else if (status == Status.LOST) {
                    JOptionPane.showMessageDialog(colorButton, "You Lost!");
                    setInitialFrame();
                }
                else {
                    int numBlack = guessResult.get(Result.INPOSITION);
                    int numSilver = guessResult.get(Result.MATCH);
                    for (int i = 0; i < numBlack; i++) {
                        resultColors.get(chanceTaken).get(i).changeColor(Color.BLACK);
                    }
                    for (int i = numBlack; i < numBlack + numSilver; i++) {
                        resultColors.get(chanceTaken).get(i).changeColor(Color.LIGHT_GRAY);
                    }
                    turnTaken = 0;
                    guessColors.clear();
                    chanceTaken++;
                }
            }

        }
    }

    private class giveUpClickedHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JButton giveUpButton = (JButton) actionEvent.getSource();
            JOptionPane.showMessageDialog(giveUpButton, "You Lost!");
            setInitialFrame();
        }
    }
}
