package SnakeProjectforFebrurar;

import javax.swing.*;

public class SNAKE {
    //Länge und breite des spiel fensters bestimmen
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static void main(String[] args){
        //new frame instanziieren
      final JFrame frame = new JFrame("Snake Game");
      frame.setVisible(true);
      frame.setSize(WIDTH,HEIGHT); //Größe festlegen
      snakegame game = new snakegame(WIDTH,HEIGHT);
      frame.add(game);
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Das die beendigung vom spiel beim schliessen des fensters erfolgt
      frame.setResizable(false);//Größe des fensters ist nicht veränderbar
    frame.pack();
    game.startGame();
    }
}
