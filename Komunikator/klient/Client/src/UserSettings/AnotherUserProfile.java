package UserSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Base64;


public class AnotherUserProfile extends JPanel implements ActionListener {


    private JLabel nameLabel;
    private JLabel ageLabel;
    private JLabel locationLabel;

    JPanel informationsAboutUser = new JPanel();

    JPanel avatarPlace = new JPanel();
    ImageIcon defaultAvatar = new ImageIcon("resourceFilesImgGif/Default_avatar.png");
    JLabel avatar = new JLabel();
    JButton addToFavorites = new JButton("Add Friend");


    JPanel favorites = new JPanel();
    JScrollPane scrollFavorites = new JScrollPane(favorites);


    public AnotherUserProfile(String name, String age, String location
            , String avatarImageBase64) throws IOException {

        //defaultAvatar = new ImageIcon(Base64.getDecoder().decode(avatarImageBase64));

        avatarPlace.setBorder(BorderFactory.createTitledBorder("Avatar"));

        scaleAvatar(defaultAvatar);
        avatar.setIcon(defaultAvatar);
        avatarPlace.add(avatar);
        avatarPlace.setPreferredSize(new Dimension(320, 250));
        this.add(avatarPlace, BorderLayout.CENTER);


        avatarPlace.add(avatar, BorderLayout.CENTER);
        this.add(avatarPlace, BorderLayout.WEST);

        informationsAboutUser.setLayout(new BoxLayout(informationsAboutUser, BoxLayout.PAGE_AXIS));
        informationsAboutUser.setSize(new Dimension(200, 150));
        informationsAboutUser.setBorder(BorderFactory.createTitledBorder("Informations"));
        informationsAboutUser.add(new JLabel("Name: "));
        informationsAboutUser.add(nameLabel = new JLabel(name));
        informationsAboutUser.add(new JLabel("Age: "));
        informationsAboutUser.add(ageLabel = new JLabel(age));
        informationsAboutUser.add(new JLabel("Location: "));
        informationsAboutUser.add(locationLabel = new JLabel(location), BorderLayout.EAST);

        this.add(informationsAboutUser);

        JScrollPane scrollFavorites = new JScrollPane(favorites);
        scrollFavorites.setBorder(BorderFactory.createTitledBorder("Favorites"));


        favorites.setPreferredSize(new Dimension(300, 100));
        favorites.setLayout(new BoxLayout(favorites, BoxLayout.Y_AXIS));
        addToFavorites.setVisible(true);
        addToFavorites.addActionListener(this);
        favorites.add(addToFavorites);

        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/friends.txt"));
        String allFriends = bufferedReader.readLine();
        if (allFriends != null) {
            String[] splitFriends = allFriends.split(";");
            for (int i = 0; i < splitFriends.length; i++) {
                if (splitFriends[i].equals(name)) {
                    addToFavorites.setText("remove friend");
                }
            }
        }
        this.add(scrollFavorites, BorderLayout.SOUTH);
    }

    private ImageIcon scaleAvatar(ImageIcon newAvatarSize) throws IOException {
        Image temp = newAvatarSize.getImage().getScaledInstance(300, 220, Image.SCALE_SMOOTH);
        return defaultAvatar = new ImageIcon(temp);

    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == addToFavorites) {
            if (!addToFavorites.getText().equals("remove friend")) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader("src/friends.txt"));
                    String allFriends = bufferedReader.readLine();
                    FileWriter writer = new FileWriter("src/friends.txt");
                    if (allFriends == null){
                        writer.append(nameLabel.getText());
                    }else {
                        writer.append(allFriends).append(";").append(nameLabel.getText());
                    }
                    writer.close();

                    System.out.println("Użytkownik został dodany do ulubionych!");
                    addToFavorites.setText("remove friend");

                    this.repaint();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (addToFavorites.getText().equals("remove friend")) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader("src/friends.txt"));
                    String allFriends = bufferedReader.readLine();
                    String[] splitFriends = allFriends.split(";");
                    for (int i = 0; i < splitFriends.length; i++) {
                        if (splitFriends[i].equals(nameLabel.getText())) {
                            splitFriends[i] = "";
                        }
                    }
                    FileWriter writer = new FileWriter("src/friends.txt");
                    for (int i = 0; i < splitFriends.length; i++) {
                        writer.append(splitFriends[i]).append(";");
                    }

                    writer.close();
                    System.out.println("Użytkownik został usunięty z listy!");
                    addToFavorites.setText("add Friend");
                    // usunięcie średników

                    bufferedReader = new BufferedReader(new FileReader("src/friends.txt"));
                    allFriends = bufferedReader.readLine();
                    String replace = allFriends.replace(";;",";");

                    writer = new FileWriter("src/friends.txt");
                    writer.append(replace);

                    writer.close();

                    this.repaint();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }



        }
    }
}
