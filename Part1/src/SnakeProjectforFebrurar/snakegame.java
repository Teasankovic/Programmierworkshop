package SnakeProjectforFebrurar;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.TextLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class snakegame extends JPanel implements ActionListener {

    private final int width;
    private final int height;
    private final int cellSize;
    private final Random random = new Random();
    private static final int FRAME_RATE = 20;
    // Anzahl der Äpfel im Spiel
    private static final int NUMBER_OF_APPLES = 5;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private int highScore;
    // Liste für mehrere Äpfel
    private final List<GamePoint> foods = new ArrayList<>();
    private Direction direction = Direction.RIGHT;
    private Direction newDirection = Direction.RIGHT;
    private final List<GamePoint> snake = new ArrayList<>();

    public snakegame(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.cellSize = width / (FRAME_RATE * 2);

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
    }

    public void startGame() {
        resetGameData();

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                handleKeyEvent(e.getKeyCode());
            }
        });

        new Timer(1650 / FRAME_RATE, this).start();
    }

    private void handleKeyEvent(final int keyCode) {
        if (!gameStarted) {
            if (keyCode == KeyEvent.VK_SPACE) {
                gameStarted = true;
            }
        } else if (!gameOver) {
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    if (direction != Direction.DOWN) {
                        newDirection = Direction.UP;
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (direction != Direction.UP) {
                        newDirection = Direction.DOWN;
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (direction != Direction.LEFT) {
                        newDirection = Direction.RIGHT;
                    }
                    break;

                case KeyEvent.VK_LEFT:
                    if (direction != Direction.RIGHT) {
                        newDirection = Direction.LEFT;
                    }
                    break;

                default:
                    break;
            }
        } else if (keyCode == KeyEvent.VK_SPACE) {
            gameStarted = false;
            gameOver = false;

            direction = Direction.RIGHT;
            newDirection = Direction.RIGHT;

            resetGameData();
        }
    }

    private void resetGameData() {
        snake.clear();
        foods.clear();

        snake.add(new GamePoint(
                (width / 2 / cellSize) * cellSize,
                (height / 2 / cellSize) * cellSize
        ));

        // Drei Äpfel beim Start des Spiels generieren
        for (int i = 0; i < NUMBER_OF_APPLES; i++) {
            generateFood();
        }
    }

    private void generateFood() {
        GamePoint newFood;

        // Einen neuen Apfel auf einem freien Feld generieren
        do {
            newFood = new GamePoint(
                    random.nextInt(width / cellSize) * cellSize,
                    random.nextInt(height / cellSize) * cellSize
            );
        } while (snake.contains(newFood) || foods.contains(newFood));

        foods.add(newFood);
    }

    @Override
    protected void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);

        // Schachbrettmuster im Hintergrund zeichnen
        drawChessboard(graphics);

        if (!gameStarted) {
            printMessage(graphics, "Press Space Bar to Begin Game");
            return;
        }

        // Essen farbe
        drawFoods(graphics);

        // Schlange erstellen
        Color snakeColor = Color.GREEN;

        for (final GamePoint point : snake) {
            graphics.setColor(snakeColor);

            graphics.fillRect(
                    point.x,
                    point.y,
                    cellSize,
                    cellSize
            );

            final int newGreen =
                    (int) Math.round(snakeColor.getGreen() * 0.98);

            snakeColor = new Color(0, newGreen, 0);
        }

        // Game-Over-Nachricht zeichnen
        if (gameOver) {
            final int currentScore = snake.size() - 1;

            if (currentScore > highScore) {
                highScore = currentScore;
            }

            printMessage(
                    graphics,
                    "Your Score: " + currentScore
                            + "\nHigh Score: " + highScore
                            + "\nPress Space Bar to Reset"
            );
        }
    }

    private void drawFoods(final Graphics graphics) {
        // Alle Äpfel erstellen. Vorlage aus dem internet
        for (final GamePoint food : foods) {
            // Apfel zeichnen
            graphics.setColor(Color.RED);

            graphics.fillOval(
                    food.x + 2,
                    food.y + 2,
                    cellSize - 4,
                    cellSize - 4
            );

            // Blatt des Apfels zeichnen
            graphics.setColor(Color.GREEN);

            graphics.fillOval(
                    food.x + cellSize / 2,
                    food.y,
                    cellSize / 3,
                    cellSize / 4
            );
        }
    }

    private void drawChessboard(final Graphics graphics) {
        final Color lightColor = new Color(0, 31, 53);
        final Color darkColor = new Color(0, 21, 41);

        for (int y = 0; y < height; y += cellSize) {
            for (int x = 0; x < width; x += cellSize) {
                final int column = x / cellSize;
                final int row = y / cellSize;

                if ((row + column) % 2 == 0) {
                    graphics.setColor(lightColor);
                } else {
                    graphics.setColor(darkColor);
                }

                graphics.fillRect(
                        x,
                        y,
                        cellSize,
                        cellSize
                );
            }
        }
    }

    private void printMessage(
            final Graphics graphics,
            final String message
    ) {
        // Mit mathe oder so berechnet wo der text vom spiel anfangen kommen soll

        graphics.setColor(Color.WHITE);
        graphics.setFont(graphics.getFont().deriveFont(30F));

        int currentHeight = height / 3;

        final Graphics2D graphics2D = (Graphics2D) graphics;
        final var fontRenderContext =
                graphics2D.getFontRenderContext();

        for (final String line : message.split("\n")) {
            final TextLayout layout = new TextLayout(
                    line,
                    graphics.getFont(),
                    fontRenderContext
            );

            final var bounds = layout.getBounds();

            final float targetWidth =
                    (float) (width - bounds.getWidth()) / 2;

            layout.draw(
                    graphics2D,
                    targetWidth,
                    currentHeight
            );

            currentHeight += graphics.getFontMetrics().getHeight();
        }
    }

    private void move() {
        direction = newDirection;

        final GamePoint head = snake.getFirst();

        final GamePoint newHead = switch (direction) {
            case UP -> new GamePoint(
                    head.x,
                    head.y - cellSize
            );

            case DOWN -> new GamePoint(
                    head.x,
                    head.y + cellSize
            );

            case LEFT -> new GamePoint(
                    head.x - cellSize,
                    head.y
            );

            case RIGHT -> new GamePoint(
                    head.x + cellSize,
                    head.y
            );
        };

        snake.addFirst(newHead);

        // Eingrenzung des Spielbereichs,
        // damit eine Collision und Game Over entsteht
        if (isCollision()) {
            gameOver = true;
            snake.removeFirst();
            return;
        }

        // Festlegung, dass beim Essen eines Apfels
        // eine neue Zelle generiert wird
        if (foods.contains(newHead)) {
            foods.remove(newHead);

            // Neuer Apfel wird generiert,
            // damit immer drei Äpfel im Spiel vorhanden sind
            generateFood();
        } else {
            snake.removeLast();
        }
    }

    private boolean isCollision() {
        // Eingrenzung des spiel bereichs das eine collision und so ein game over entsteht

        final GamePoint head = snake.getFirst();

        final boolean invalidWidth =
                head.x < 0 || head.x >= width;

        final boolean invalidHeight =
                head.y < 0 || head.y >= height;

        if (invalidWidth || invalidHeight) {
            return true;
        }

        return snake.size() != new HashSet<>(snake).size();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (gameStarted && !gameOver) {
            move();
        }

        repaint();
    }

    private record GamePoint(int x, int y) {
    }

    private enum Direction {
        // Steuereung initialisiert
        UP,
        DOWN,
        RIGHT,
        LEFT
    }
}