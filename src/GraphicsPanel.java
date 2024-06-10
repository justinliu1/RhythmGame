import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GraphicsPanel extends JPanel implements KeyListener {
    private BufferedImage background;
    private Notes noteUp, noteRight, noteDown, noteLeft;
    private final int panelWidth = 960;
    private final int panelHeight = 540;
    private final int centerX = 463;
    private final int centerY = 244;

    private int combo;
    private int maxCombo;
    private int health;
    private int points;
    private final double noteSpeed = 1.0;

    private List<NoteData> notes;
    private long startTime;
    private Timer timer;

    public GraphicsPanel() {
        try {
            background = ImageIO.read(getClass().getResource("/background.png"));
        } catch (IOException e) {
            System.err.println("Background image not found: " + e.getMessage());
        }

        noteUp = new Notes(1);
        noteRight = new Notes(2);
        noteDown = new Notes(3);
        noteLeft = new Notes(4);

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

        // Figure out music
        /*MusicPlayer musicPlayer = new MusicPlayer();
        musicPlayer.playMusic("path/to/music/file.wav");*/
    }

    private void initializeNotes() {
        notes = new ArrayList<>();
        // Manual Charting
        notes.add(new NoteData(1.0, "up", noteUp));
        notes.add(new NoteData(2.0, "right", noteRight));
        notes.add(new NoteData(3.0, "down", noteDown));
        notes.add(new NoteData(4.0, "left", noteLeft));
        notes.add(new NoteData(5.0, "up", noteUp));
        notes.add(new NoteData(6.0, "left", noteLeft));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (background != null) {
            int centerX = (getWidth() - background.getWidth()) / 2;
            int centerY = (getHeight() - background.getHeight()) / 2;
            g.drawImage(background, centerX, centerY, null);
        }

        //Notes
        drawNotes(g, noteUp);
        drawNotes(g, noteRight);
        drawNotes(g, noteDown);
        drawNotes(g, noteLeft);

        //Score, HP, Combo
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Points: " + points, 40, 40);
        g.drawString("Health: " + health, 770, 47);
        g.drawString("Combo: " + combo, 835, 105);

        //HP Bar
        g.setColor(Color.CYAN);
        int healthBarWidth = (int) (230 * (health / 100.0));
        g.fillRect(750, 10, healthBarWidth, 5);
    }

    private void drawNotes(Graphics g, Notes note) {
        if (note.getNoteImage() != null) {
            g.drawImage(note.getNoteImage(), (int) note.getxCoord(), (int) note.getyCoord(), null);
        }
    }

    private void updateNotes() {
        moveNote(noteUp, Direction.UP);
        moveNote(noteRight, Direction.RIGHT);
        moveNote(noteDown, Direction.DOWN);
        moveNote(noteLeft, Direction.LEFT);

        long currentTime = System.currentTimeMillis();
        double elapsedTime = (currentTime - startTime) / 1000.0;

        // Removed notes list
        List<NoteData> notesToRemove = new ArrayList<>();

        // Iterate respawn notes
        for (NoteData noteData : notes) {
            if (noteData.time <= elapsedTime) {
                spawnNote(noteData.lane);
                notesToRemove.add(noteData);
            }
        }

        // Note removal (All currently spawned)
        notes.removeAll(notesToRemove);
    }

    private void moveNote(Notes note, Direction direction) {
        switch (direction) {
            case UP:
                note.setyCoord(note.getyCoord() - noteSpeed);
                break;
            case RIGHT:
                note.setxCoord(note.getxCoord() + noteSpeed);
                break;
            case DOWN:
                note.setyCoord(note.getyCoord() + noteSpeed);
                break;
            case LEFT:
                note.setxCoord(note.getxCoord() - noteSpeed);
                break;
        }

        // Check if notes cross border
        if (note.getxCoord() < 0 || note.getxCoord() > panelWidth ||
                note.getyCoord() < 0 || note.getyCoord() > panelHeight) {
            note.setxCoord(centerX);
            note.setyCoord(centerY);
            health -= 10; // -HP
        }
    }

    public enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    private void spawnNote(String lane) {
        switch (lane) {
            case "up":
                noteUp.setxCoord(centerX);
                noteUp.setyCoord(centerY);
                break;
            case "right":
                noteRight.setxCoord(centerX);
                noteRight.setyCoord(centerY);
                break;
            case "down":
                noteDown.setxCoord(centerX);
                noteDown.setyCoord(centerY);
                break;
            case "left":
                noteLeft.setxCoord(centerX);
                noteLeft.setyCoord(centerY);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                if (noteLeft.getxCoord() < 267 && noteLeft.getxCoord() > 250) {
                    combo++;
                    points += 100;
                    removeNoteFromList(noteLeft);
                    noteLeft.setxCoord(centerX);
                    noteLeft.setyCoord(centerY);
                    repaint();
                } else {
                    combo = 0;
                    points += 50; // Outside box points (Same for the each direction)
                }
                break;
            case KeyEvent.VK_W:
                if (noteUp.getyCoord() < 40) {
                    combo++;
                    points += 100;
                    removeNoteFromList(noteUp);
                    noteUp.setxCoord(centerX);
                    noteUp.setyCoord(centerY);
                    repaint();
                } else {
                    combo = 0;
                    points += 50;
                }
                break;
            case KeyEvent.VK_S:
                if (noteDown.getyCoord() > 476) {
                    combo++;
                    points += 100;
                    removeNoteFromList(noteDown);
                    noteDown.setxCoord(centerX);
                    noteDown.setyCoord(centerY);
                    repaint();
                } else {
                    combo = 0;
                    points += 50;
                }
                break;
            case KeyEvent.VK_D:
                if (noteRight.getxCoord() > 683) {
                    combo++;
                    points += 100;
                    removeNoteFromList(noteRight);
                    noteRight.setxCoord(centerX);
                    noteRight.setyCoord(centerY);
                    repaint();
                } else {
                    combo = 0;
                    points += 50;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    private void removeNoteFromList(Notes noteToRemove) {
        Iterator<NoteData> iterator = notes.iterator();
        while (iterator.hasNext()) {
            NoteData noteData = iterator.next();
            if (noteData.note.equals(noteToRemove)) {
                iterator.remove();
                break;
            }
        }
    }

    private class NoteData {
        private double time;
        private String lane;
        private Notes note;

        NoteData(double time, String lane, Notes note) {
            this.time = time;
            this.lane = lane;
            this.note = note;
        }
    }
}