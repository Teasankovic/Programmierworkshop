package BlackJack;
//Video 35:14 min
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BlackjackGame {
        private class Card {
            String value;
            String type;

            Card(String vlaue, String type) {
                this.value = vlaue;
                this.type = type;
            }
            public String toString(){
                return value + "-" + type;
            }
            public int getValue(){
                if ("AJQK".contains(value)) { // A,J,Q,K
                    if(value == "A") {
                        return 11;
                    }
                    return 10;
                }
                return Integer.parseInt(value); // 2 -10
            }
            public boolean isAce(){
              return value == "A";
            }
        }
        ArrayList<Card> deck;
        Random random = new Random(); // shuffeling the deck

    //dealer
    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;

    //player
    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    //window
    int boardWidth = 600;
    int boardHeight = boardWidth;

    //card größe
    int cardWidth = 110; // ratio should be 1/1.4
    int cardHeigth = 154;

    JFrame frame = new JFrame("Black Jack");
    JPanel gamePanel = new JPanel() {
    @Override
        public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Image hiddenCardImg = new ImageIcon(getClass().getResource("./Cards/BACK.png")).getImage();
        graphics.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeigth, null);

    }

    };
    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");


        BlackjackGame() {
        startGame();

        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel);

        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }
    public void startGame() {
            //deck
            buildDeck ();
            shuffelDeck();

            // dealer
        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount = 0;

       hiddenCard = deck.remove(deck.size()-1);
       dealerSum += hiddenCard.getValue();
       dealerAceCount += hiddenCard.isAce() ? 1 : 0;

       Card card = deck.remove(deck.size() -1);
       dealerSum += card.getValue();
       dealerAceCount += card.isAce() ? 1 : 0;
       dealerHand.add(card);

       System.out.println("DEALER: ");
       System.out.println(hiddenCard);
       System.out.println(dealerHand);
       System.out.println(dealerSum);
       System.out.println(dealerAceCount);

       //player
        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAceCount = 0;

        for (int i = 0; i < 2; i++){
            card = deck.remove(deck.size()-1);
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }
        System.out.println("PLAYER: ");
        System.out.println(playerHand);
        System.out.println(playerSum);
        System.out.println(playerAceCount);

    }



public void buildDeck() {
            deck = new ArrayList<Card>();
            String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
            String[] types = {"C", "D", "H", "S"};

            for (int i = 0; i < types.length; i++) {
                for (int j = 0; j < values.length; j++){
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }
        }
    System.out.println("BUILD DECK: ");
    System.out.println(deck);
    }
    public void shuffelDeck() {
            for (int i = 0; i< deck.size(); i++) {
                int j = random.nextInt(deck.size());
                Card currCard = deck.get(i);
                Card randomCard = deck.get (j);
                deck.set(i, randomCard);
                deck.set(j, currCard);
            }
            System.out.println("AFTER SHUFFEL: ");
            System.out.println(deck);
    }
}