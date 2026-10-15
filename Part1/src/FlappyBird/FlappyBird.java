package FlappyBird;

import javax.swing.*;



public class FlappyBird {
    public static void main(String[] args ) throws Exception{
    int boardWidth = 360;
    int boardHeigth = 640;

    JFrame frame = new JFrame("Flappy Bird");
    frame.setSize(boardWidth,boardHeigth);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    FlappyBirdGame flappyBird = new FlappyBirdGame();
    frame.add(flappyBird);
    frame.pack(); //Dient dazu das die anzeige über dem spiel nicht mit in die größe eingerechnet wird
    flappyBird.requestFocus();
    frame.setVisible(true);

    }
}
