import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;

public class CreateNewprivChat {

    private Panel panel;
    private String name;
    JPanel chatTextPane;
    HTMLEditorKit editorKit;

    CreateNewprivChat(Panel panel, String name) {
        this.panel = panel;

        this.name = name;

        this.editorKit = editorKit;


        JPanel chatpanel = new JPanel();
        chatpanel.setLayout(new BorderLayout());
        editorKit = new HTMLEditorKit();

        JEditorPane privTextPanel = new JEditorPane();
        privTextPanel.setLayout(new BoxLayout(privTextPanel, BoxLayout.PAGE_AXIS));
        privTextPanel.setEditable(false);
        privTextPanel.setContentType("text/html");
        privTextPanel.setEditorKit(editorKit);


        JScrollPane scrollPane = new JScrollPane(privTextPanel);
        chatpanel.add(scrollPane, BorderLayout.CENTER);

        panel.tabbedPane.addTab(name, chatpanel);

    }


}
