package UserSettings;

import org.fife.ui.rsyntaxtextarea.parser.ToolTipInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class UserProfile extends Panel implements ActionListener {

    private String name = "Myname";
    JTextField myName = new JTextField(name, 12);
    private String age = "125";
    JTextField Myage = new JTextField(age);
    private String location = "Warsaw";
    JTextField myLocation = new JTextField(location);
    int labels = 0;
    ArrayList<JButton> buttons = new ArrayList<>();
    ArrayList<JLabel> labelsFriends = new ArrayList<>();

    JPanel informationsAboutUser = new JPanel();

    JPanel avatarPlace = new JPanel();
    ImageIcon defaultAvatar = new ImageIcon("resourceFilesImgGif/Default_avatar.png");
    JLabel avatar = new JLabel();

    String imageBase = "";

    JPanel favorites = new JPanel();
    JScrollPane scrollFavorites = new JScrollPane(favorites);
    ArrayList<JButton> clientsName;
    public boolean reFresh = false;
    String username;


    public UserProfile(String username, ArrayList<JButton> clientsName) throws IOException {

        this.username = username;
        this.clientsName = clientsName;

        BufferedReader readerInfo = new BufferedReader(new FileReader("src/" + username + ".txt"));
        // Ustawienie layout managera dla avatarPlace
        avatarPlace.setBorder(BorderFactory.createTitledBorder("Avatar"));

        scaleAvatar(defaultAvatar);
        avatar.setIcon(defaultAvatar);
        avatarPlace.add(avatar);
        avatarPlace.setPreferredSize(new Dimension(320, 250));
        this.add(avatarPlace, BorderLayout.CENTER);

// Ustawienie preferowanych rozmiarów dla pól tekstowych
        myName.setPreferredSize(new Dimension(100, 20));
        Myage.setPreferredSize(new Dimension(100, 20));
        myLocation.setPreferredSize(new Dimension(100, 20));

// Dodanie komponentów do avatarPlace, informationsAboutUser i scrollFavorites
        avatarPlace.add(avatar, BorderLayout.CENTER);
        this.add(avatarPlace, BorderLayout.WEST);

        informationsAboutUser.setLayout(new BoxLayout(informationsAboutUser, BoxLayout.PAGE_AXIS));
        informationsAboutUser.setSize(new Dimension(200, 150));
        informationsAboutUser.setBorder(BorderFactory.createTitledBorder("Informations"));
        informationsAboutUser.add(new JLabel("Name: "));

        String s = readerInfo.readLine();
        String[] split = s.split(";");

        myName.setText(username);
        informationsAboutUser.add(myName);
        informationsAboutUser.add(new JLabel("Age: "));
        Myage.setText(split[1]);
        informationsAboutUser.add(Myage);
        informationsAboutUser.add(new JLabel("Location: "));
        myLocation.setText(split[2]);
        informationsAboutUser.add(myLocation, BorderLayout.EAST);

        this.add(informationsAboutUser);

        BufferedReader reader = new BufferedReader(new FileReader("src/friends.txt"));

        buttons.clear();

        String f = reader.readLine();

        if (f != null) {
            String[] friends = f.split(";");
            for (String friend : friends) {
                if (!friend.equals("")) {
                    JLabel label = new JLabel(friend);
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setBorder(BorderFactory.createRaisedSoftBevelBorder());
                    label.setSize(new Dimension(100,40));
                    for (int i = 0; i < clientsName.size(); i++) {
                        if (label.getText().equals(clientsName.get(i).getText())) {
                            System.out.println(label.getText() + " jest online!");
                            label.setToolTipText("jest online!");
                            label.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 0), 3));
                        }
                    }
                    labelsFriends.add(label);

                    JButton button = new JButton("<-- remove user");
                    button.setPreferredSize(new Dimension(100, 40));
                    button.addActionListener(this);
                    button.setVisible(true);
                    buttons.add(button);

                    labels++;
                }
            }
            for (int i = 0; i < buttons.size(); i++) {
                favorites.add(labelsFriends.get(i));
                favorites.add(buttons.get(i));
            }
        } else {
            System.out.println("ni ma przyjaciół ;(");
        }

        favorites.setLayout(new GridLayout(labelsFriends.size(),2,3,3));
        favorites.setBorder(BorderFactory.createTitledBorder("Friends"));

        scrollFavorites.setPreferredSize(new Dimension(350, 180));

        this.add(scrollFavorites);




    }


    private ImageIcon scaleAvatar(ImageIcon newAvatarSize) throws IOException {
        System.out.println(defaultAvatar.getIconHeight() + " H obrazka przed");
        System.out.println(defaultAvatar.getIconWidth() + " W obrazka przed");

        Image temp = newAvatarSize.getImage().getScaledInstance(300, 220, Image.SCALE_SMOOTH);

        System.out.println(defaultAvatar.getIconHeight() + " H obrazka");
        System.out.println(defaultAvatar.getIconWidth() + " W obrazka");

        //byte[] b = Files.readAllBytes(defaultAvatar);   -----> naprawić!!!!
        //imageBase = Base64.getEncoder().encodeToString(b);

        return defaultAvatar = new ImageIcon(temp);
    }

    private void removeFriend(int index) throws IOException {


        BufferedReader reader = new BufferedReader(new FileReader("src/friends.txt"));

        if (index >= 0 && index < labelsFriends.size()) {
            String f = reader.readLine();
            String updateFriends = "";

            FileWriter writer = new FileWriter("src/friends.txt");

            if (f != null && f.contains(labelsFriends.get(index).getText())) {
                System.out.println("jest!!");
                updateFriends = f.replace(labelsFriends.get(index).getText(), "");
            }

            writer.write(updateFriends);
            writer.close();

        }


        scrollFavorites.revalidate();


    }


    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < buttons.size(); i++) {

            if (e.getSource() == buttons.get(i)) {
                System.out.println("Wybrałeś " + labelsFriends.get(i).getText() + " i zostanie on/a usunięty/a ze znajomych");
                scrollFavorites.revalidate();
                scrollFavorites.repaint();
                try {
                    removeFriend(i);
                } catch (IOException ex) {
                    System.out.println("nie udało się usunąć z listy");
                    throw new RuntimeException(ex);
                }
            }
        }


    }
}
