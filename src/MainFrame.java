import javax.swing.*;

public class MainFrame implements Runnable {

    private GraphicsPanel panel;

    public MainFrame() {
        // Ensuring the frame is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rhythm Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(960, 540);
            frame.setLocationRelativeTo(null);

            panel = new GraphicsPanel();
            frame.add(panel);

            frame.setVisible(true);
        });

        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {
            // Google said to do this for "Schedule the repaint on the Event Dispatch Thread"
            SwingUtilities.invokeLater(() -> panel.repaint());

            // Add a delay to reduce CPU usage
            try {
                Thread.sleep(16); // approximately 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}