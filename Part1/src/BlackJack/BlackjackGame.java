package BlackJack;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BlackjackGame {
private int startPlayerMoney = 1000;
private char currency = '$';
        private class Card {
            String value;
            String type;

            Card(String vlaue, String type) {
                this.value = vlaue;
                this.type = type;
            }
            public String toString(){return value + "-" + type;}
            public int getValue(){
                if ("AJQK".contains(value)) { // A,J,Q,K
                    if(value.equalsIgnoreCase( "A")) { //Man sollte nicht strings mit "==" vergleichen sondern "".equalsIgnoreCase!
                        return 11;
                    }
                    return 10;
                }
                return Integer.parseInt(value); // 2 -10
            }
            public boolean isAce(){
              return value.equalsIgnoreCase( "A");
            }

            public String getImagePath() {
                return "./Cards/" + value + "-" + type + ".png";
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
    int boardHeight = 600;

    //card größe
    int cardWidth = 110; // ratio should be 1/1.4
    int cardHeigth = 154;

    JFrame frame = new JFrame("Black Jack");
    JPanel gamePanel = new JPanel() {
    @Override
        public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        try {

            //Versteckte karte vom DEALER
            Image hiddenCardImg = new ImageIcon(getClass().getResource("./Cards/BACK.png")).getImage();
            if (!stayButton.isEnabled()) {
                hiddenCardImg = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
            }
            graphics.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeigth, null);

            //Draw dealers hand
            for (int i = 0; i < dealerHand.size(); i++) {
                Card card = dealerHand.get(i);
                URL path = getClass().getResource(card.getImagePath());
                if (path == null) {
                   System.out.println("FEHLER" + card.getImagePath());
                }
                Image cardImg = new ImageIcon((getClass().getResource(card.getImagePath()))).getImage();
                graphics.drawImage(cardImg, cardWidth + 30 + (cardWidth + 5) * i, 20, cardWidth, cardHeigth, null);
            }
            // draw player hand
            for (int i = 0; i < playerHand.size(); i++) {
                Card card = playerHand.get(i);
                Image cardImg = new ImageIcon((getClass().getResource(card.getImagePath()))).getImage();
                graphics.drawImage(cardImg, 20 + (cardWidth + 5) * i, 320, cardWidth, cardHeigth, null);
            }
            // Geld für den spieler
            graphics.setFont(new Font("Arial", Font.BOLD, 13));
            graphics.setColor(Color.WHITE);
            String startMoneyP = "Player Money total: " +currency + startPlayerMoney;
            graphics.drawString(startMoneyP, 400, 460);

        // Spieler karten summe anzeigen
            graphics.setFont(new Font("Arial", Font.BOLD, 15));
            graphics.setColor(Color.WHITE);
            String counter = "Player Card total: " + reducePlayerAce();

            graphics.drawString(counter, 400, 500);


            if (!stayButton.isEnabled()) {
                dealerSum = reduceDealerAce();
                playerSum = reducePlayerAce();
                System.out.println("STAY: ");
                System.out.println(dealerSum);
                System.out.println(playerSum);

                String message = "";
                if (playerSum > 21) {
                    message = "You Loose!";
                    startPlayerMoney -= 320;

                } else if (dealerSum > 21) {
                    message = "You Win!";
                    startPlayerMoney += 300;
                }
                //wenn beides nicht zustimmt dann
                else if (playerSum == dealerSum) {
                    message = "Tie!";
                    startPlayerMoney += 150;
                } else if ( playerSum > dealerSum) {
                    message = "You Win!";
                    startPlayerMoney += 300;
                } else if (playerSum < dealerSum) {
                    message = "You Loose!";
                    startPlayerMoney -= 320;
                }

                if (startPlayerMoney < 320) {
                    graphics.setFont(new Font("Arial", Font.BOLD, 14));
                    graphics.setColor(Color.WHITE);
                    graphics.drawString("You don't have enough Money to Loose anymore!", 90, 280);
                    graphics.drawString("Play right, your Money depends on it", 150, 300);
                }

            graphics.setFont(new Font("Arial", Font.BOLD, 30));
                graphics.setColor(Color.WHITE);
                graphics.drawString(message, 200, 250);

            }
            }
                catch(Exception e){
                e.printStackTrace();

            }
        }
    };


    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");
    JButton restartButton = new JButton("New Game");


        BlackjackGame() {
        startGame();

        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(35, 69, 52));
        frame.add(gamePanel);

        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        buttonPanel.setFocusable(false);
        buttonPanel.add(restartButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    Card card = deck.remove(deck.size()-1);
                    playerSum += card.getValue();
                    playerAceCount += card.isAce() ? 1 : 0;
                    playerHand.add(card);
                    if (reducePlayerAce() > 21) { // A + 2 J --> 1 +2 +J
                        hitButton.setEnabled(false);
                        stayButton.setEnabled(false); //hier festgelegt das auch der stay button nicht betätigt werden kann und es ein game over oder win geben wird
                    } else if (reducePlayerAce() == 21) {
                        hitButton.setEnabled(false);
                        stayButton.setEnabled(false); // ja vallah ich war so schlau das geht auch automatiesiert für wenn man 21 glatt hinbekommt WUHUUU
                    }
                    gamePanel.repaint();
                }
        });

        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);

                while (dealerSum < 17) {
                    Card card = deck.remove(deck.size()-1);
                    dealerSum += card.getValue();
                    dealerAceCount += card.isAce() ? 1 : 0;
                    dealerHand.add(card);
                }
        gamePanel.repaint();

            }
        });
            restartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startGame();

                    hitButton.setEnabled(true);
                    stayButton.setEnabled(true);

                    gamePanel.repaint();
                }
            });

        gamePanel.repaint();

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
    public int reducePlayerAce() {
            while ( playerSum > 21 && playerAceCount > 0) {
               playerSum -= 10;
               playerAceCount -= 1;
            }
            return playerSum;

    }
    public int reduceDealerAce() {
        while (dealerSum > 21 && dealerAceCount > 0) {
            dealerSum -= 10;
            dealerAceCount -= 1;
        }

        return dealerSum;
    }

}