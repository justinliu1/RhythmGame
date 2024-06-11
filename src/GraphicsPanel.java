import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphicsPanel extends JPanel implements KeyListener {
    private BufferedImage background;
    private Clip songClip;
    private final int panelWidth = 960;
    private final int panelHeight = 540;
    private final int centerX = 463;
    private final int centerY = 244;

    private int combo;
    private int maxCombo;
    private int health;
    private int points;
    private final double noteSpeed = 1.6;

    private List<NoteData> notes;
    private long startTime;
    private Timer timer;

    public GraphicsPanel() {
        try {
            background = ImageIO.read(getClass().getResource("/background.png"));
        } catch (IOException e) {
            System.err.println("Background image not found: " + e.getMessage());
        }

        combo = 0;
        maxCombo = 0;
        health = 100;
        points = 0;

        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        initializeNotes();
        startTime = System.currentTimeMillis();

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateNotes();
                repaint();
            }
        });
        timer.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playMusic();
    }

    private class NoteData {
        private double time;
        private String lane;
        private Notes note;
        private boolean stopped;

        NoteData(double time, String lane, Notes note) {
            this.time = time;
            this.lane = lane;
            this.note = note;
            this.stopped = false;
        }
    }

    private void initializeNotes() {
        notes = new ArrayList<>();
        notes.add(new NoteData(1.0, "up", new Notes(1)));
        notes.add(new NoteData(2.0, "right", new Notes(2)));
        notes.add(new NoteData(3.0, "up", new Notes(1)));
        notes.add(new NoteData(3.0, "down", new Notes(3)));
        notes.add(new NoteData(4.0, "left", new Notes(4)));
        notes.add(new NoteData(5.0, "up", new Notes(1)));
        notes.add(new NoteData(6.0, "right", new Notes(2)));
        notes.add(new NoteData(7.0, "down", new Notes(3)));
        notes.add(new NoteData(8.0, "left", new Notes(4)));
        notes.add(new NoteData(9.0, "up", new Notes(1)));
        notes.add(new NoteData(10.0, "right", new Notes(2)));
        notes.add(new NoteData(11.0, "down", new Notes(3)));
        notes.add(new NoteData(12.0, "left", new Notes(4)));
        notes.add(new NoteData(13.0, "up", new Notes(1)));
        notes.add(new NoteData(14.0, "right", new Notes(2)));
        notes.add(new NoteData(15.0, "down", new Notes(3)));
        notes.add(new NoteData(16.0, "left", new Notes(4)));
    }

    private void updateNotes() {
        long currentTime = System.currentTimeMillis();
        double elapsedTime = (currentTime - startTime) / 1000.0;

        for (NoteData noteData : notes) {
            if (!noteData.stopped) {
                if (noteData.time <= elapsedTime) {
                    moveNote(noteData);
                    if (isNoteOutOfBounds(noteData.note)) {
                        health -= 10;
                        combo = 0;
                        noteData.stopped = true;
                        if (health <= 0) {
                            endGame("Game Over! Your health reached 0.");
                        }
                    }
                }
            }
        }
    }

    private void moveNote(NoteData noteData) {
        Notes note = noteData.note;
        if (!noteData.stopped) {
            switch (noteData.lane) {
                case "up":
                    note.setyCoord(note.getyCoord() - noteSpeed);
                    break;
                case "right":
                    note.setxCoord(note.getxCoord() + noteSpeed);
                    break;
                case "down":
                    note.setyCoord(note.getyCoord() + noteSpeed);
                    break;
                case "left":
                    note.setxCoord(note.getxCoord() - noteSpeed);
                    break;
            }
        }
    }

    private boolean isNoteOutOfBounds(Notes note) {
        return note.getxCoord() < 0 || note.getxCoord() > panelWidth ||
                note.getyCoord() < 0 || note.getyCoord() > panelHeight;
    }

    private void handleKeyPress(Direction direction) {
        for (NoteData noteData : notes) {
            if (!noteData.stopped) {
                Notes note = noteData.note;
                switch (direction) {
                    case UP:
                        if (noteData.lane.equals("up") && note.getyCoord() < 100 && note.getyCoord() > 0) {
                            updateCombo(noteData);
                            return;
                        }
                        break;
                    case RIGHT:
                        if (noteData.lane.equals("right") && note.getxCoord() > 850 && note.getxCoord() < 970) {
                            updateCombo(noteData);
                            return;
                        }
                        break;
                    case DOWN:
                        if (noteData.lane.equals("down") && note.getyCoord() > 440 && note.getyCoord() < 540) {
                            updateCombo(noteData);
                            return;
                        }
                        break;
                    case LEFT:
                        if (noteData.lane.equals("left") && note.getxCoord() < 110 && note.getxCoord() > -10) {
                            updateCombo(noteData);
                            return;
                        }
                        break;
                }
            }
        }
        combo = 0;  // Reset combo if no note was hit correctly
    }

    private void updateCombo(NoteData noteData) {
        points += 100 + combo * 10;
        combo++;
        noteData.stopped = true;
        noteData.note.setxCoord(1000);
        noteData.note.setyCoord(1000);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);

        // Draw all notes in the notes list
        for (NoteData noteData : notes) {
            if (!noteData.stopped && noteData.note.getNoteImage() != null) {
                g.drawImage(noteData.note.getNoteImage(), (int) noteData.note.getxCoord(), (int) noteData.note.getyCoord(), null);
            }
        }

        // Score, HP, Combo
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Points: " + points, 40, 40);
        g.drawString("Health: " + health, 770, 47);
        g.drawString("Combo: " + combo, 835, 105);

        // HP Bar
        g.setColor(Color.CYAN);
        int healthBarWidth = (int) (230 * (health / 100.0));
        g.fillRect(750, 15, healthBarWidth, 5);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                handleKeyPress(Direction.LEFT);
                break;
            case KeyEvent.VK_W:
                handleKeyPress(Direction.UP);
                break;
            case KeyEvent.VK_S:
                handleKeyPress(Direction.DOWN);
                break;
            case KeyEvent.VK_D:
                handleKeyPress(Direction.RIGHT);
                break;
        }
    }

    private void playMusic() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/freedomdive.wav").getAbsoluteFile());
            songClip = AudioSystem.getClip();
            songClip.open(audioInputStream);
            songClip.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void endGame(String message) {
        timer.stop();
        songClip.stop();
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}