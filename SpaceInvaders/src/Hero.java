import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;

public class Hero implements KeyListener {
    public boolean left;
    public boolean right;
    public boolean shoot;
    int shootCounter = 0;
    public ImageIcon heroIcon;

    public Hero() {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 37) {
            this.left = true;
        }

        if (code == 39) {
            this.right = true;
        }

        if (code == 32) {
            this.shoot = true;
            ++this.shootCounter;
            System.out.println(this.shootCounter);
            System.out.println("strzał!");
        }

    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 37) {
            this.left = false;
        }

        if (code == 39) {
            this.right = false;
        }

        if (code == 32) {
            this.shoot = false;
        }

    }
}