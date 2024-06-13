import javax.swing.*;

public class MainFrame implements Runnable {

    private GraphicsPanel panel;

    public MainFrame() {
        panel = new GraphicsPanel(); // Create instance of GraphicsPanel

        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Rhythm Game");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(960, 540);
            mainFrame.setLocationRelativeTo(null);

            panel = new GraphicsPanel();
            mainFrame.setContentPane(panel);

            mainFrame.setVisible(true);
            panel.requestFocusInWindow();
        });

        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {
            if (panel != null) {
                SwingUtilities.invokeLater(() -> panel.repaint());
            }
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}