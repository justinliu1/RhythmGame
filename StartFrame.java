import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class StartFrame extends JPanel {

    private BufferedImage startBackground;

    public StartFrame(JFrame frame) {
        try {
            startBackground = ImageIO.read(new File("src/startBackground.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        setLayout(null);
        JButton startButton = new JButton("Start Game") {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setComposite(AlphaComposite.Src);
                    g2.setColor(getBackground());
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        startButton.setContentAreaFilled(false); // Remove default button background
        startButton.setBorderPainted(false); // Remove default button border
        startButton.setForeground(Color.WHITE); // Set text color
        startButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        startButton.setFocusPainted(false); // Remove focus border
        startButton.setBounds(0, 0, getWidth(), getHeight()); // Button covers the entire panel

        // Add mouse listener to the panel
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setContentPane(new GraphicsPanel());
                frame.revalidate();
                frame.repaint();
            }
        });

        add(startButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (startBackground != null) {
            g.drawImage(startBackground, 0, 0, getWidth(), getHeight(), null);
        }
    }
}