import javax.swing.*;

public class ButtonsModules {

    JButton app = new JButton("Aplications");
    JButton games = new JButton("Games");
    JButton anim = new JButton("Animaions");
    public JButton returnToMenu = new JButton("return");


    ButtonsModules(){
        returnToMenu.setBounds(100,300,150,30);
        returnToMenu.setVisible( true);

        app.setBounds(200,30,150,30);
        app.setVisible( true);

        games.setBounds(520,30,150,30);
        games.setVisible( true);

        anim.setBounds(200,80,150,30);
        anim.setVisible( true);

    }
}
