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
    private static Clip songClip;
    private final int panelWidth = 960;
    private final int panelHeight = 540;

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

    private int getPoints() {
        return points;
    }

    private int getMaxCombo() {
        return maxCombo;
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

    // Freedom Dive Charting
    //up 1, right 2, down 3, left 4
    private void initializeNotes() {
        notes = new ArrayList<>();
        notes.add(new NoteData(3.75, "left", new Notes(4)));
        notes.add(new NoteData(3.8, "right", new Notes(2)));
        notes.add(new NoteData(4.05, "up", new Notes(1)));
        notes.add(new NoteData(4.35, "down", new Notes(3)));
        notes.add(new NoteData(4.55, "up", new Notes(1)));
        notes.add(new NoteData(4.8, "down", new Notes(3)));
        notes.add(new NoteData(5.05, "up", new Notes(1)));
        notes.add(new NoteData(5.35, "down", new Notes(3)));
        notes.add(new NoteData(5.6, "up", new Notes(1)));
        notes.add(new NoteData(5.9, "down", new Notes(3)));
        notes.add(new NoteData(6.05, "left", new Notes(4)));
        notes.add(new NoteData(6.05, "right", new Notes(2)));
        notes.add(new NoteData(6.25, "up", new Notes(1)));
        notes.add(new NoteData(6.5, "down", new Notes(3)));
        notes.add(new NoteData(6.8, "up", new Notes(1)));
        notes.add(new NoteData(7.0, "down", new Notes(3)));
        notes.add(new NoteData(7.25, "up", new Notes(1)));
        notes.add(new NoteData(7.5, "down", new Notes(3)));
        notes.add(new NoteData(7.75, "up", new Notes(1)));
        notes.add(new NoteData(8.0, "down", new Notes(3)));
        notes.add(new NoteData(8.2, "left", new Notes(4)));
        notes.add(new NoteData(8.2, "right", new Notes(2)));
        notes.add(new NoteData(8.45, "up", new Notes(1)));
        notes.add(new NoteData(8.7, "down", new Notes(3)));
        notes.add(new NoteData(8.95, "up", new Notes(1)));
        notes.add(new NoteData(9.15, "down", new Notes(3)));
        notes.add(new NoteData(9.4, "up", new Notes(1)));
        notes.add(new NoteData(9.65, "down", new Notes(3)));
        notes.add(new NoteData(9.9, "up", new Notes(1)));
        notes.add(new NoteData(10.2, "down", new Notes(3)));
        notes.add(new NoteData(10.35, "left", new Notes(4)));
        notes.add(new NoteData(10.35, "right", new Notes(2)));
        notes.add(new NoteData(10.85, "left", new Notes(4)));
        notes.add(new NoteData(10.85, "right", new Notes(2)));
        notes.add(new NoteData(11.4, "left", new Notes(4)));
        notes.add(new NoteData(11.4, "right", new Notes(2)));
        notes.add(new NoteData(11.65, "left", new Notes(4)));
        notes.add(new NoteData(11.7, "right", new Notes(2)));
        notes.add(new NoteData(11.95, "left", new Notes(4)));
        notes.add(new NoteData(11.95, "right", new Notes(2)));
        notes.add(new NoteData(12.75, "left", new Notes(4)));
        notes.add(new NoteData(12.95, "right", new Notes(2)));
        notes.add(new NoteData(12.95, "left", new Notes(4)));
        notes.add(new NoteData(13.2, "left", new Notes(4)));
        notes.add(new NoteData(13.45, "right", new Notes(2)));
        notes.add(new NoteData(13.45, "left", new Notes(4)));
        notes.add(new NoteData(13.75, "left", new Notes(4)));
        notes.add(new NoteData(14.0, "left", new Notes(4)));
        notes.add(new NoteData(14.0, "right", new Notes(2)));
        notes.add(new NoteData(14.25, "left", new Notes(4)));
        notes.add(new NoteData(14.5, "right", new Notes(2)));
        notes.add(new NoteData(14.55, "left", new Notes(4)));
        notes.add(new NoteData(14.85, "up", new Notes(1)));
        notes.add(new NoteData(15.1, "up", new Notes(1)));
        notes.add(new NoteData(15.1, "down", new Notes(3)));
        notes.add(new NoteData(15.1, "right", new Notes(2)));
        notes.add(new NoteData(15.15, "left", new Notes(4)));
        notes.add(new NoteData(15.35, "left", new Notes(4)));
        notes.add(new NoteData(15.65, "left", new Notes(4)));
        notes.add(new NoteData(15.65, "right", new Notes(2)));
        notes.add(new NoteData(15.9, "down", new Notes(3)));
        notes.add(new NoteData(15.9, "up", new Notes(1)));
        notes.add(new NoteData(16.2, "left", new Notes(4)));
        notes.add(new NoteData(16.75, "left", new Notes(4)));
        notes.add(new NoteData(16.75, "right", new Notes(2)));
        notes.add(new NoteData(17.0, "left", new Notes(4)));
        notes.add(new NoteData(17.25, "left", new Notes(4)));
        notes.add(new NoteData(17.25, "right", new Notes(2)));
        notes.add(new NoteData(17.6, "down", new Notes(3)));
        notes.add(new NoteData(17.6, "up", new Notes(1)));
        notes.add(new NoteData(17.8, "down", new Notes(3)));
        notes.add(new NoteData(17.8, "up", new Notes(1)));
        notes.add(new NoteData(18.0, "up", new Notes(1)));
        notes.add(new NoteData(18.0, "down", new Notes(3)));
        notes.add(new NoteData(18.1, "up", new Notes(1)));
        notes.add(new NoteData(18.1, "down", new Notes(3)));
        notes.add(new NoteData(18.35, "left", new Notes(4)));
        notes.add(new NoteData(18.35, "right", new Notes(2)));
        notes.add(new NoteData(18.6, "down", new Notes(3)));
        notes.add(new NoteData(18.85, "left", new Notes(4)));
        notes.add(new NoteData(18.85, "right", new Notes(2)));
        notes.add(new NoteData(19.05, "down", new Notes(3)));
        notes.add(new NoteData(19.15, "down", new Notes(3)));
        notes.add(new NoteData(19.35, "left", new Notes(4)));
        notes.add(new NoteData(19.35, "right", new Notes(2)));
        notes.add(new NoteData(19.45, "left", new Notes(4)));
        notes.add(new NoteData(19.45, "right", new Notes(2)));
        notes.add(new NoteData(19.6, "up", new Notes(1)));
        notes.add(new NoteData(19.8, "up", new Notes(1)));
        notes.add(new NoteData(19.9, "up", new Notes(1)));
        notes.add(new NoteData(20.15, "left", new Notes(4)));
        notes.add(new NoteData(20.2, "right", new Notes(2)));
        notes.add(new NoteData(20.5, "up", new Notes(1)));
        notes.add(new NoteData(20.5, "right", new Notes(2)));
        notes.add(new NoteData(20.75, "left", new Notes(4)));
        notes.add(new NoteData(20.8, "right", new Notes(2)));
        notes.add(new NoteData(21.05, "up", new Notes(1)));
        notes.add(new NoteData(21.3, "left", new Notes(4)));
        notes.add(new NoteData(21.3, "right", new Notes(2)));
        notes.add(new NoteData(21.6, "up", new Notes(1)));
        notes.add(new NoteData(21.85, "left", new Notes(4)));
        notes.add(new NoteData(21.85, "right", new Notes(2)));
        notes.add(new NoteData(22.15, "up", new Notes(1)));
        notes.add(new NoteData(22.4, "left", new Notes(4)));
        notes.add(new NoteData(22.45, "right", new Notes(2)));
        notes.add(new NoteData(22.9, "left", new Notes(4)));
        notes.add(new NoteData(23.1, "right", new Notes(2)));
        notes.add(new NoteData(23.1, "left", new Notes(4)));
        notes.add(new NoteData(23.35, "left", new Notes(4)));
        notes.add(new NoteData(23.6, "right", new Notes(2)));
        notes.add(new NoteData(23.6, "left", new Notes(4)));
        notes.add(new NoteData(23.9, "up", new Notes(1)));
        notes.add(new NoteData(24.2, "down", new Notes(3)));
        notes.add(new NoteData(24.2, "right", new Notes(2)));
        notes.add(new NoteData(24.2, "left", new Notes(4)));
        notes.add(new NoteData(24.2, "up", new Notes(1)));
        notes.add(new NoteData(24.45, "left", new Notes(4)));
        notes.add(new NoteData(24.7, "left", new Notes(4)));
        notes.add(new NoteData(24.7, "up", new Notes(1)));
        notes.add(new NoteData(24.7, "down", new Notes(3)));
        notes.add(new NoteData(24.7, "right", new Notes(2)));
        notes.add(new NoteData(25.0, "left", new Notes(4)));
        notes.add(new NoteData(25.25, "right", new Notes(2)));
        notes.add(new NoteData(25.25, "down", new Notes(3)));
        notes.add(new NoteData(25.25, "left", new Notes(4)));
        notes.add(new NoteData(25.25, "up", new Notes(1)));
        notes.add(new NoteData(25.4, "left", new Notes(4)));
        notes.add(new NoteData(25.4, "up", new Notes(1)));
        notes.add(new NoteData(25.7, "left", new Notes(4)));
        notes.add(new NoteData(25.7, "right", new Notes(2)));
        notes.add(new NoteData(25.95, "left", new Notes(4)));
        notes.add(new NoteData(25.95, "up", new Notes(1)));
        notes.add(new NoteData(26.2, "left", new Notes(4)));
        notes.add(new NoteData(26.2, "right", new Notes(2)));
        notes.add(new NoteData(26.4, "left", new Notes(4)));
        notes.add(new NoteData(26.4, "right", new Notes(2)));
        notes.add(new NoteData(26.55, "right", new Notes(2)));
        notes.add(new NoteData(26.6, "left", new Notes(4)));
        notes.add(new NoteData(26.7, "left", new Notes(4)));
        notes.add(new NoteData(26.7, "right", new Notes(2)));
        notes.add(new NoteData(27.0, "left", new Notes(4)));
        notes.add(new NoteData(27.0, "down", new Notes(3)));
        notes.add(new NoteData(27.25, "right", new Notes(2)));
        notes.add(new NoteData(27.25, "left", new Notes(4)));
        notes.add(new NoteData(27.55, "down", new Notes(3)));
        notes.add(new NoteData(27.55, "left", new Notes(4)));
        notes.add(new NoteData(27.7, "right", new Notes(2)));
        notes.add(new NoteData(27.7, "left", new Notes(4)));
        notes.add(new NoteData(27.95, "down", new Notes(3)));
        notes.add(new NoteData(27.95, "left", new Notes(4)));
        notes.add(new NoteData(28.1, "right", new Notes(2)));
        notes.add(new NoteData(28.1, "left", new Notes(4)));
        notes.add(new NoteData(28.25, "down", new Notes(3)));
        notes.add(new NoteData(28.25, "left", new Notes(4)));
        notes.add(new NoteData(28.5, "left", new Notes(4)));
        notes.add(new NoteData(28.5, "right", new Notes(2)));
        notes.add(new NoteData(28.6, "down", new Notes(3)));
        notes.add(new NoteData(28.65, "left", new Notes(4)));
        notes.add(new NoteData(28.9, "left", new Notes(4)));
        notes.add(new NoteData(28.9, "up", new Notes(1)));
        notes.add(new NoteData(28.9, "right", new Notes(2)));
        notes.add(new NoteData(29.15, "left", new Notes(4)));
        notes.add(new NoteData(29.15, "down", new Notes(3)));
        notes.add(new NoteData(29.45, "right", new Notes(2)));
        notes.add(new NoteData(29.45, "down", new Notes(3)));
        notes.add(new NoteData(29.75, "left", new Notes(4)));
        notes.add(new NoteData(29.75, "up", new Notes(1)));
        notes.add(new NoteData(30.0, "down", new Notes(3)));
        notes.add(new NoteData(30.0, "right", new Notes(2)));
        notes.add(new NoteData(30.25, "left", new Notes(4)));
        notes.add(new NoteData(30.25, "up", new Notes(1)));
        notes.add(new NoteData(30.5, "down", new Notes(3)));
        notes.add(new NoteData(30.5, "right", new Notes(2)));
        notes.add(new NoteData(30.8, "left", new Notes(4)));
        notes.add(new NoteData(30.8, "up", new Notes(1)));
        notes.add(new NoteData(31.1, "down", new Notes(3)));
        notes.add(new NoteData(31.15, "right", new Notes(2)));
        notes.add(new NoteData(31.3, "up", new Notes(1)));
        notes.add(new NoteData(31.4, "left", new Notes(4)));
        notes.add(new NoteData(31.55, "down", new Notes(3)));
        notes.add(new NoteData(31.65, "right", new Notes(2)));
        notes.add(new NoteData(31.8, "up", new Notes(1)));
        notes.add(new NoteData(31.9, "left", new Notes(4)));
        notes.add(new NoteData(32.15, "down", new Notes(3)));
        notes.add(new NoteData(32.35, "right", new Notes(2)));
        notes.add(new NoteData(32.45, "up", new Notes(1)));
        notes.add(new NoteData(32.65, "left", new Notes(4)));
        notes.add(new NoteData(32.85, "down", new Notes(3)));
        notes.add(new NoteData(33.0, "right", new Notes(2)));
        notes.add(new NoteData(33.2, "up", new Notes(1)));
        notes.add(new NoteData(33.35, "left", new Notes(4)));
        notes.add(new NoteData(33.6, "down", new Notes(3)));
        notes.add(new NoteData(33.75, "right", new Notes(2)));
        notes.add(new NoteData(33.9, "up", new Notes(1)));
        notes.add(new NoteData(34.05, "left", new Notes(4)));
        notes.add(new NoteData(34.2, "down", new Notes(3)));
        notes.add(new NoteData(34.35, "right", new Notes(2)));
        notes.add(new NoteData(34.45, "up", new Notes(1)));
        notes.add(new NoteData(34.65, "up", new Notes(1)));
        notes.add(new NoteData(34.8, "right", new Notes(2)));
        notes.add(new NoteData(35.0, "down", new Notes(3)));
        notes.add(new NoteData(35.15, "left", new Notes(4)));
        notes.add(new NoteData(35.35, "up", new Notes(1)));
        notes.add(new NoteData(35.55, "right", new Notes(2)));
        notes.add(new NoteData(35.7, "down", new Notes(3)));
        notes.add(new NoteData(35.85, "left", new Notes(4)));
        notes.add(new NoteData(36.0, "up", new Notes(1)));
        notes.add(new NoteData(36.15, "right", new Notes(2)));
        notes.add(new NoteData(36.25, "down", new Notes(3)));
        notes.add(new NoteData(36.4, "left", new Notes(4)));
        notes.add(new NoteData(36.55, "up", new Notes(1)));
        notes.add(new NoteData(36.7, "right", new Notes(2)));
        notes.add(new NoteData(36.85, "right", new Notes(2)));
        notes.add(new NoteData(37.05, "down", new Notes(3)));
        notes.add(new NoteData(37.15, "down", new Notes(3)));
        notes.add(new NoteData(37.3, "left", new Notes(4)));
        notes.add(new NoteData(37.45, "left", new Notes(4)));
        notes.add(new NoteData(37.6, "up", new Notes(1)));
        notes.add(new NoteData(37.7, "up", new Notes(1)));
        notes.add(new NoteData(37.9, "right", new Notes(2)));
        notes.add(new NoteData(38.0, "right", new Notes(2)));
        notes.add(new NoteData(38.15, "down", new Notes(3)));
        notes.add(new NoteData(38.3, "down", new Notes(3)));
        notes.add(new NoteData(38.45, "left", new Notes(4)));
        notes.add(new NoteData(38.6, "left", new Notes(4)));
        notes.add(new NoteData(38.7, "right", new Notes(2)));
        notes.add(new NoteData(38.95, "up", new Notes(1)));
        notes.add(new NoteData(39.2, "down", new Notes(3)));
        notes.add(new NoteData(39.5, "left", new Notes(4)));
        notes.add(new NoteData(39.55, "right", new Notes(2)));
        notes.add(new NoteData(39.6, "left", new Notes(4)));
        notes.add(new NoteData(39.7, "right", new Notes(2)));
        notes.add(new NoteData(39.8, "left", new Notes(4)));
        notes.add(new NoteData(40.05, "right", new Notes(2)));
        notes.add(new NoteData(40.3, "left", new Notes(4)));
        notes.add(new NoteData(40.6, "up", new Notes(1)));
        notes.add(new NoteData(40.9, "down", new Notes(3)));
        notes.add(new NoteData(41.15, "left", new Notes(4)));
        notes.add(new NoteData(41.4, "right", new Notes(2)));
        notes.add(new NoteData(41.65, "up", new Notes(1)));
        notes.add(new NoteData(41.7, "down", new Notes(3)));
        notes.add(new NoteData(41.8, "up", new Notes(1)));
        notes.add(new NoteData(41.85, "down", new Notes(3)));
        notes.add(new NoteData(42.2, "up", new Notes(1)));
        notes.add(new NoteData(42.3, "right", new Notes(2)));
        notes.add(new NoteData(42.45, "left", new Notes(4)));
        notes.add(new NoteData(42.6, "down", new Notes(3)));
        notes.add(new NoteData(42.7, "up", new Notes(1)));
        notes.add(new NoteData(42.95, "right", new Notes(2)));
        notes.add(new NoteData(43.25, "left", new Notes(4)));
        notes.add(new NoteData(43.85, "left", new Notes(4)));
        notes.add(new NoteData(43.85, "right", new Notes(2)));
        notes.add(new NoteData(44.35, "left", new Notes(4)));
        notes.add(new NoteData(44.35, "right", new Notes(2)));
        notes.add(new NoteData(44.65, "left", new Notes(4)));
        notes.add(new NoteData(44.7, "right", new Notes(2)));
        notes.add(new NoteData(44.85, "right", new Notes(2)));
        notes.add(new NoteData(44.85, "left", new Notes(4)));
        notes.add(new NoteData(45.4, "left", new Notes(4)));
        notes.add(new NoteData(45.4, "right", new Notes(2)));
        notes.add(new NoteData(45.95, "up", new Notes(1)));
        notes.add(new NoteData(46.25, "right", new Notes(2)));
        notes.add(new NoteData(46.5, "left", new Notes(4)));
        notes.add(new NoteData(46.8, "down", new Notes(3)));
        notes.add(new NoteData(47.05, "up", new Notes(1)));
        notes.add(new NoteData(47.3, "right", new Notes(2)));
        notes.add(new NoteData(47.55, "left", new Notes(4)));
        notes.add(new NoteData(47.7, "down", new Notes(3)));
        notes.add(new NoteData(47.85, "up", new Notes(1)));
        notes.add(new NoteData(48.0, "right", new Notes(2)));
        notes.add(new NoteData(48.15, "left", new Notes(4)));
        notes.add(new NoteData(48.2, "left", new Notes(4)));
        notes.add(new NoteData(48.2, "right", new Notes(2)));
        notes.add(new NoteData(48.45, "right", new Notes(2)));
        notes.add(new NoteData(48.45, "up", new Notes(1)));
        notes.add(new NoteData(48.7, "right", new Notes(2)));
        notes.add(new NoteData(48.7, "left", new Notes(4)));
        notes.add(new NoteData(49.0, "up", new Notes(1)));
        notes.add(new NoteData(49.0, "left", new Notes(4)));
        notes.add(new NoteData(49.25, "right", new Notes(2)));
        notes.add(new NoteData(49.25, "left", new Notes(4)));
        notes.add(new NoteData(49.5, "up", new Notes(1)));
        notes.add(new NoteData(49.5, "right", new Notes(2)));
        notes.add(new NoteData(49.75, "left", new Notes(4)));
        notes.add(new NoteData(49.75, "right", new Notes(2)));
        notes.add(new NoteData(50.0, "up", new Notes(1)));
        notes.add(new NoteData(50.05, "left", new Notes(4)));
        notes.add(new NoteData(50.3, "left", new Notes(4)));
        notes.add(new NoteData(50.3, "right", new Notes(2)));
        notes.add(new NoteData(50.55, "up", new Notes(1)));
        notes.add(new NoteData(50.55, "right", new Notes(2)));
        notes.add(new NoteData(50.85, "left", new Notes(4)));
        notes.add(new NoteData(50.85, "right", new Notes(2)));
        notes.add(new NoteData(51.1, "up", new Notes(1)));
        notes.add(new NoteData(51.1, "left", new Notes(4)));
        notes.add(new NoteData(51.4, "down", new Notes(3)));
        notes.add(new NoteData(51.65, "left", new Notes(4)));
        notes.add(new NoteData(51.9, "up", new Notes(1)));
        notes.add(new NoteData(52.2, "right", new Notes(2)));
        notes.add(new NoteData(52.5, "down", new Notes(3)));
        notes.add(new NoteData(52.7, "right", new Notes(2)));
        notes.add(new NoteData(53.0, "up", new Notes(1)));
        notes.add(new NoteData(53.3, "left", new Notes(4)));
        notes.add(new NoteData(53.6, "left", new Notes(4)));
        notes.add(new NoteData(53.6, "right", new Notes(2)));
        notes.add(new NoteData(53.85, "up", new Notes(1)));
        notes.add(new NoteData(53.85, "down", new Notes(3)));
        notes.add(new NoteData(54.05, "left", new Notes(4)));
        notes.add(new NoteData(54.1, "right", new Notes(2)));
        notes.add(new NoteData(54.35, "down", new Notes(3)));
        notes.add(new NoteData(54.35, "up", new Notes(1)));
        notes.add(new NoteData(54.6, "left", new Notes(4)));
        notes.add(new NoteData(54.6, "right", new Notes(2)));
        notes.add(new NoteData(54.9, "up", new Notes(1)));
        notes.add(new NoteData(54.9, "down", new Notes(3)));
        notes.add(new NoteData(55.15, "left", new Notes(4)));
        notes.add(new NoteData(55.15, "right", new Notes(2)));
        notes.add(new NoteData(55.5, "up", new Notes(1)));
        notes.add(new NoteData(55.5, "down", new Notes(3)));
        notes.add(new NoteData(55.6, "left", new Notes(4)));
        notes.add(new NoteData(55.6, "right", new Notes(2)));
        notes.add(new NoteData(55.8, "left", new Notes(4)));
        notes.add(new NoteData(55.8, "right", new Notes(2)));
        notes.add(new NoteData(56.15, "up", new Notes(1)));
        notes.add(new NoteData(56.15, "down", new Notes(3)));
        notes.add(new NoteData(56.4, "up", new Notes(1)));
        notes.add(new NoteData(56.4, "down", new Notes(3)));
        notes.add(new NoteData(56.6, "left", new Notes(4)));
        notes.add(new NoteData(56.6, "right", new Notes(2)));
        notes.add(new NoteData(56.9, "left", new Notes(4)));
        notes.add(new NoteData(56.9, "right", new Notes(2)));
        notes.add(new NoteData(57.1, "up", new Notes(1)));
        notes.add(new NoteData(57.15, "down", new Notes(3)));
        notes.add(new NoteData(57.4, "up", new Notes(1)));
        notes.add(new NoteData(57.4, "down", new Notes(3)));
        notes.add(new NoteData(57.7, "right", new Notes(2)));
        notes.add(new NoteData(57.95, "down", new Notes(3)));
        notes.add(new NoteData(58.25, "left", new Notes(4)));
        notes.add(new NoteData(58.5, "down", new Notes(3)));
        notes.add(new NoteData(58.8, "right", new Notes(2)));
        notes.add(new NoteData(59.05, "down", new Notes(3)));
        notes.add(new NoteData(59.35, "left", new Notes(4)));
        notes.add(new NoteData(59.6, "down", new Notes(3)));
        notes.add(new NoteData(59.85, "right", new Notes(2)));
        notes.add(new NoteData(60.1, "down", new Notes(3)));
        notes.add(new NoteData(60.4, "up", new Notes(1)));
        notes.add(new NoteData(60.65, "down", new Notes(3)));
        notes.add(new NoteData(60.95, "right", new Notes(2)));
        notes.add(new NoteData(61.2, "left", new Notes(4)));
        notes.add(new NoteData(61.45, "up", new Notes(1)));
        notes.add(new NoteData(61.75, "right", new Notes(2)));
        notes.add(new NoteData(62.0, "down", new Notes(3)));
        notes.add(new NoteData(62.3, "left", new Notes(4)));
        notes.add(new NoteData(62.6, "up", new Notes(1)));
        notes.add(new NoteData(62.65, "up", new Notes(1)));
        notes.add(new NoteData(62.85, "right", new Notes(2)));
        notes.add(new NoteData(62.9, "right", new Notes(2)));
        notes.add(new NoteData(63.1, "down", new Notes(3)));
        notes.add(new NoteData(63.15, "down", new Notes(3)));
        notes.add(new NoteData(63.35, "left", new Notes(4)));
        notes.add(new NoteData(63.45, "left", new Notes(4)));
        notes.add(new NoteData(63.65, "up", new Notes(1)));
        notes.add(new NoteData(63.75, "up", new Notes(1)));
        notes.add(new NoteData(63.9, "right", new Notes(2)));
        notes.add(new NoteData(64.0, "right", new Notes(2)));
        notes.add(new NoteData(64.15, "down", new Notes(3)));
        notes.add(new NoteData(64.25, "down", new Notes(3)));
        notes.add(new NoteData(64.45, "left", new Notes(4)));
        notes.add(new NoteData(64.55, "left", new Notes(4)));
        notes.add(new NoteData(65.7, "left", new Notes(4)));
        notes.add(new NoteData(65.75, "right", new Notes(2)));
        notes.add(new NoteData(65.8, "down", new Notes(3)));
        notes.add(new NoteData(65.9, "right", new Notes(2)));
        notes.add(new NoteData(65.95, "up", new Notes(1)));
        notes.add(new NoteData(66.05, "down", new Notes(3)));
        notes.add(new NoteData(66.1, "left", new Notes(4)));
        notes.add(new NoteData(66.15, "right", new Notes(2)));
        notes.add(new NoteData(66.25, "up", new Notes(1)));
        notes.add(new NoteData(66.3, "down", new Notes(3)));
        notes.add(new NoteData(66.35, "left", new Notes(4)));
        notes.add(new NoteData(66.45, "right", new Notes(2)));
        notes.add(new NoteData(66.5, "down", new Notes(3)));
        notes.add(new NoteData(66.6, "down", new Notes(3)));
        notes.add(new NoteData(66.65, "up", new Notes(1)));
        notes.add(new NoteData(66.7, "right", new Notes(2)));
        notes.add(new NoteData(66.8, "left", new Notes(4)));
        notes.add(new NoteData(66.85, "down", new Notes(3)));
        notes.add(new NoteData(66.9, "up", new Notes(1)));
        notes.add(new NoteData(66.95, "right", new Notes(2)));
        notes.add(new NoteData(67.05, "left", new Notes(4)));
        notes.add(new NoteData(67.1, "down", new Notes(3)));
        notes.add(new NoteData(67.15, "up", new Notes(1)));
        notes.add(new NoteData(67.2, "down", new Notes(3)));
        notes.add(new NoteData(67.2, "right", new Notes(2)));
        notes.add(new NoteData(67.3, "right", new Notes(2)));
        notes.add(new NoteData(67.35, "up", new Notes(1)));
        notes.add(new NoteData(67.4, "down", new Notes(3)));
        notes.add(new NoteData(67.45, "right", new Notes(2)));
        notes.add(new NoteData(67.55, "down", new Notes(3)));
        notes.add(new NoteData(67.55, "left", new Notes(4)));
        notes.add(new NoteData(67.6, "right", new Notes(2)));
        notes.add(new NoteData(67.6, "down", new Notes(3)));
        notes.add(new NoteData(67.65, "left", new Notes(4)));
        notes.add(new NoteData(67.7, "right", new Notes(2)));
        notes.add(new NoteData(67.7, "up", new Notes(1)));
        notes.add(new NoteData(67.7, "down", new Notes(3)));
        notes.add(new NoteData(67.75, "up", new Notes(1)));
        notes.add(new NoteData(67.75, "left", new Notes(4)));
        notes.add(new NoteData(67.75, "right", new Notes(2)));
        notes.add(new NoteData(67.75, "down", new Notes(3)));
        notes.add(new NoteData(67.85, "up", new Notes(1)));
        notes.add(new NoteData(67.85, "down", new Notes(3)));
        notes.add(new NoteData(67.85, "right", new Notes(2)));
        notes.add(new NoteData(68.05, "down", new Notes(3)));
        notes.add(new NoteData(68.1, "down", new Notes(3)));
        notes.add(new NoteData(68.15, "down", new Notes(3)));
        notes.add(new NoteData(68.25, "down", new Notes(3)));
        notes.add(new NoteData(68.35, "up", new Notes(1)));
        notes.add(new NoteData(68.45, "up", new Notes(1)));
        notes.add(new NoteData(68.6, "right", new Notes(2)));
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
                            endGame();
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
                        if (noteData.lane.equals("up") && note.getyCoord() < 55 && note.getyCoord() > 25) {
                            updateCombo(noteData);
                            return;
                        }
                        break;
                    case RIGHT:
                        if (noteData.lane.equals("right") && note.getxCoord() > 673 && note.getxCoord() < 703) {
                            updateCombo(noteData);
                            return;
                        }
                        break;
                    case DOWN:
                        if (noteData.lane.equals("down") && note.getyCoord() > 461 && note.getyCoord() < 491) {
                            updateCombo(noteData);
                            return;
                        }
                        break;
                    case LEFT:
                        if (noteData.lane.equals("left") && note.getxCoord() < 267 && note.getxCoord() > 237) {
                            updateCombo(noteData);
                            return;
                        }
                        break;
                }
            }
        }
        combo = 0;
    }

    private void updateCombo(NoteData noteData) {
        points += 100 + combo * 10;
        combo++;
        if (combo > maxCombo) {
            maxCombo = combo;
        }
        health += 2;
        noteData.stopped = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        for (NoteData noteData : notes) {
            if (!noteData.stopped) {
                g.drawImage(noteData.note.getNoteImage(), (int) noteData.note.getxCoord(), (int) noteData.note.getyCoord(), null);
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Points: " + points, 40, 45);
        g.drawString("Health: " + health, 770, 47);
        g.drawString("Combo: " + combo, 835, 115);

        g.setColor(Color.CYAN);
        int healthBarWidth = (int) (190 * (health / 100.0));
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
        if (songClip == null || !songClip.isRunning()) { // Check if the songClip is already running
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/freedomdive.wav").getAbsoluteFile());
                songClip = AudioSystem.getClip();
                songClip.open(audioInputStream);
                songClip.start();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void endGame() {
        timer.stop();
        if (songClip != null && songClip.isRunning()) {
            songClip.stop();
            songClip.close();
        }
        SwingUtilities.invokeLater(() -> {
            JFrame gameFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (gameFrame != null) {
                EndFrame endPanel = new EndFrame(gameFrame, getPoints(), getMaxCombo());
                endPanel.setPlayAgainAction(() -> {
                    gameFrame.setContentPane(new GraphicsPanel());
                    gameFrame.revalidate();
                    gameFrame.repaint();
                });
                endPanel.setMainMenuAction(() -> {
                    gameFrame.setContentPane(new StartFrame(gameFrame));
                    gameFrame.revalidate();
                    gameFrame.repaint();
                });
                gameFrame.setContentPane(endPanel);
                gameFrame.revalidate();
                gameFrame.repaint();
            }
        });
    }
}