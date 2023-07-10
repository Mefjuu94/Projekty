import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class IconButton extends JButton {

    IconButton(ImageIcon icon, int x,int y,JPanel panel){
        super();
        this.addActionListener((ActionListener) panel);
        this.setFocusable(false);
        this.setIcon(icon);
        this.setBounds(new Rectangle(x, y, 40, 40));
        this.setOpaque(false);
        this.setBackground(Color.black);
        this.setForeground(Color.WHITE);
        panel.add(this);
        this.setVisible(true);


    }
}
