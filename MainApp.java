

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class MainApp {
    private JFrame frame;
    private GameBoard gameBoard;
    private JPanel boardPanel;
    private JLabel scoreLabel;
    private SaveScore saveScore;

    public MainApp() {
        saveScore = new SaveScore();
        initializeFrame();
        setupMenu();
        initializeGame(3);
        frame.setVisible(true);
    }

    private void initializeFrame() {
        frame = new JFrame("2048");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateBoard();
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> resetGame());
        JMenuItem viewScores = new JMenuItem("View Scores");
        viewScores.addActionListener(e -> showScores());
        gameMenu.add(viewScores);

        JMenu sizeMenu = new JMenu("Size");
        for (int size : new int[]{3, 4, 5}) {
            JMenuItem sizeItem = new JMenuItem(size + "x" + size);
            sizeItem.addActionListener(e -> initializeGame(size));
            sizeMenu.add(sizeItem);
        }

        gameMenu.add(newGame);
        gameMenu.add(sizeMenu);
        menuBar.add(gameMenu);
        frame.setJMenuBar(menuBar);
    }

    private void initializeGame(int size) {
        gameBoard = new GameBoard(size);
        setupScorePanel();
        setupBoardPanel();
        updateBoard();
    }

    private void setupScorePanel() {
        if (scoreLabel != null) frame.remove(scoreLabel);
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(scoreLabel, BorderLayout.NORTH);
    }

    private void setupBoardPanel() {
        if (boardPanel != null) frame.remove(boardPanel);
        boardPanel = new JPanel(new GridLayout(gameBoard.getSize(), gameBoard.getSize()));
        frame.add(boardPanel, BorderLayout.CENTER);
    }

    private void updateBoard() {
        boardPanel.removeAll();
        int size = gameBoard.getSize();
        int cellSize = Math.min(
                frame.getContentPane().getWidth() / size,
                frame.getContentPane().getHeight() / size
        );

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JPanel cell = new JPanel(new BorderLayout());
                int value = gameBoard.getGrid()[i][j];
                cell.setBackground(getColor(value));
                cell.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                cell.setPreferredSize(new Dimension(cellSize, cellSize));
                if (value != 0) {
                    JLabel label = new JLabel(String.valueOf(value), SwingConstants.CENTER);
                    label.setFont(new Font("Arial", Font.BOLD, 30));
                    cell.add(label, BorderLayout.CENTER);
                }
                boardPanel.add(cell);
            }
        }
        scoreLabel.setText("Score: " + gameBoard.getScore());
        frame.revalidate();
        frame.repaint();
    }

    private Color getColor(int value) {
        switch (value) {
            case 2: return new Color(0xF8C471);
            case 4: return new Color(0xF39C12);
            case 8: return new Color(0xE67E22);
            case 16: return new Color(0xD35400);
            case 32: return new Color(0xE74C3C);
            case 64: return new Color(0xC0392B);
            case 128: return new Color(0x8E44AD);
            case 256: return new Color(0x2980B9);
            case 512: return new Color(0x16A085);
            case 1024: return new Color(0x27AE60);
            case 2048: return new Color(0xF1C40F);
            default: return new Color(0x34495E);
        }
    }

    private void handleKeyPress(KeyEvent e) {
        GameBoard.Direction direction = null;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: direction = GameBoard.Direction.LEFT; break;
            case KeyEvent.VK_RIGHT: direction = GameBoard.Direction.RIGHT; break;
            case KeyEvent.VK_UP: direction = GameBoard.Direction.UP; break;
            case KeyEvent.VK_DOWN: direction = GameBoard.Direction.DOWN; break;
        }
        if (direction != null && !gameBoard.isGameOver()) {
            boolean moved = gameBoard.move(direction);
            if (moved) {
                gameBoard.addNewTile();
                updateBoard();
                checkGameOver();
            }
        }
    }

    private void checkGameOver() {
        if (gameBoard.isGameOver()) {
            saveScore.saveScore(gameBoard.getSize(), gameBoard.getScore());

            JOptionPane.showMessageDialog(frame, "Game Over! Final Score: " + gameBoard.getScore());
            resetGame();
        }
    }

    private void resetGame() {
        gameBoard.reset();
        updateBoard();
    }

    private void showScores() {
        List<String> scores = saveScore.loadScores();
        if (scores.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No scores available yet!", "Scores", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Create a JTextArea to display scores
            StringBuilder sb = new StringBuilder();
            for (String score : scores) {
                sb.append(score).append("\n");
            }

            JTextArea scoreTextArea = new JTextArea(sb.toString());
            scoreTextArea.setEditable(false); // Prevent editing the score list
            scoreTextArea.setFont(new Font("Arial", Font.PLAIN, 14));

            // Add the JTextArea to a JScrollPane for scrolling
            JScrollPane scrollPane = new JScrollPane(scoreTextArea);
            scrollPane.setPreferredSize(new Dimension(400, 300)); // Set preferred size for the scroll pane

            // Show the scores inside the scrollable dialog
            JOptionPane.showMessageDialog(frame, scrollPane, "Scores", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::new);
    }

}
