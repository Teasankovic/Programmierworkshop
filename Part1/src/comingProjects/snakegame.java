package comingProjects;
//Snake tutorial video: 14:22min
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.TextLayout;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class snakegame extends JPanel implements ActionListener{

    private final int width;
    private final int heigth;
    private final int cellSize;
    private static final int FRAME_RATE = 20;
    private boolean gameStarted = false;
    private final List<GamePoint> snake = new ArrayList<>();

    public snakegame(final int width, final int height) {
        super();
        this.width = width;
        this.heigth = height;
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
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    gameStarted = true;
                }
            }
        });
        new Timer(1000 / FRAME_RATE, this).start();
    }

    private void resetGameData() {
            snake.clear();
            snake.add(new GamePoint(width / 2, heigth / 2));

        }

    @Override
    protected void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);

        if (!gameStarted) {
            graphics.setColor(Color.WHITE);
            graphics.setFont(graphics.getFont().deriveFont(30F));
            int currentHeigth = heigth / 3;
            final var graphics2G = (Graphics2D) graphics;
            final var frc = graphics2G.getFontRenderContext();
            final String message = "Press Space Bar to start the game";
            for (final var line : message.split("\n")) {
                final var layout = new TextLayout(line, graphics.getFont(), frc);
                final var bounds = layout.getBounds();
                final var targetWidth = (float) (width - bounds.getWidth()) / 2;
                layout.draw(graphics2G, targetWidth, currentHeigth);
                currentHeigth += graphics.getFontMetrics().getHeight();
            }
        } else {
            graphics.setColor(Color.GREEN);
            for (final var point : snake) {
                graphics.fillRect(point.x, point.y , cellSize, cellSize);
            }
        }
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        repaint();
    }
    private record GamePoint(int x, int y) {}
}
