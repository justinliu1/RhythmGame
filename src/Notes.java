import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Notes {
    private int lane;
    private BufferedImage note;
    private double xCoord;
    private double yCoord;

    public Notes(int lane) {
        this.lane = lane;
        this.xCoord = 463.0;
        this.yCoord = 244.0;

        try {
            note = ImageIO.read(getClass().getResource("/note.png"));
        } catch (IOException e) {
            System.err.println("Note image not found: " + e.getMessage());
        }
    }

    public double getxCoord() {
        return xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }

    public int getLane() {
        return lane;
    }

    public void setxCoord(double newX) {
        xCoord = newX;
    }

    public void setyCoord(double newY) {
        yCoord = newY;
    }

    public BufferedImage getNoteImage() {
        return note;
    }

    public void setNoteImage(BufferedImage image) {
        note = image;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Notes other = (Notes) obj;
        return lane == other.lane &&
                Double.compare(other.xCoord, xCoord) == 0 &&
                Double.compare(other.yCoord, yCoord) == 0;
    }

}