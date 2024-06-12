import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EndFrame extends JPanel implements ActionListener {
    private BufferedImage endBackground;
    private JButton playAgainButton;
    private JButton mainMenuButton;
    private Runnable playAgainAction;
    private Runnable mainMenuAction;
    private int points;
    private int maxCombo;

    public EndFrame(JFrame frame, int points, int maxCombo) {
        this.points = points;
        this.maxCombo = maxCombo;
        try {
            endBackground = ImageIO.read(new File("src/endBackground.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        setLayout(null);
        playAgainButton = new JButton("Retry");
        mainMenuButton = new JButton("Home");

        playAgainButton.addActionListener(this);
        mainMenuButton.addActionListener(this);

        playAgainButton.setBounds(225, 365, 100, 45);
        mainMenuButton.setBounds(340, 365, 100, 45);

        add(playAgainButton);
        add(mainMenuButton);
    }

    public void setPlayAgainAction(Runnable action) {
        playAgainAction = action;
    }

    public void setMainMenuAction(Runnable action) {
        mainMenuAction = action;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (endBackground != null) {
            g.drawImage(endBackground, 0, 0, getWidth(), getHeight(), null);
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.drawString("Points: " + points, 240, 250);
        g.drawString("Max Combo: " + maxCombo, 240, 290);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playAgainButton && playAgainAction != null) {
            playAgainAction.run();
        } else if (e.getSource() == mainMenuButton && mainMenuAction != null) {
            mainMenuAction.run();
        }
    }
}