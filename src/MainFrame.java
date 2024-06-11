import javax.swing.*;

public class MainFrame implements Runnable {

    private GraphicsPanel panel;

    public MainFrame() {
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
            SwingUtilities.invokeLater(() -> panel.repaint());
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}