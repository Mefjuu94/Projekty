import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PrimaryButton extends JButton {

    PrimaryButton(String napis, int x,int y,JPanel panel){
        super(napis); //wywołuje kostruktor z Jbuttona --> nowa wersja Jbuttona na moich argumentach
        this.addActionListener((ActionListener) panel);
        this.setFocusable(false);
        this.setBounds(new Rectangle(x, y, 150, 40));
        this.setOpaque(false);
        this.setBackground(Color.black);
        this.setForeground(Color.WHITE);
        panel.add(this);
        this.setVisible(true);
    }

}
