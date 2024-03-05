import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class MENU implements MouseListener, KeyListener {

    ImageIcon logo = new ImageIcon("src/ICONS/chickenLogo.png");

    boolean play = false;
    int y = 800;
    boolean menu = false;

    TalentPoints talentPoints;
    Hero hero;

    JButton load;
    boolean loadStats = false;
    JButton save;
    JButton exit;
    Panel panel;

    ArrayList<String> namesList = new ArrayList<String>();
    String[][] loadData = new String[99][11];


    public MENU(TalentPoints talentPoints, Hero hero, Panel panel, JButton save, JButton load, JButton exit) {
        this.talentPoints = talentPoints;
        this.hero = hero;
        this.panel = panel;
        this.save = save;
        this.load = load;
        this.exit = exit;
        save.addMouseListener(this);
        load.addMouseListener(this);
    }

    public void Draw(Graphics2D g2d, JButton start, JButton exit) {

        if (y > 0) {
            y -= 15;
        } else {
            y = 0;
        }
        logo.paintIcon(panel, g2d, 0, y);

        if (y == 0) {
            mainMenu(start, exit);
            drawCurrentStatsAndName(g2d);
        }

    }

    public void pause(Graphics2D g2d) {

        if (Panel.State == Panel.STATE.Pause) {
            g2d.setColor(Color.darkGray);
            g2d.fillRect(150, 150, 500, 500);

            load.setBounds(300, 250, 150, 50);
            load.setSize(200, 76);
            load.setFocusable(false);
            load.setVisible(true);

            save.setBounds(300, 350, 150, 50);
            save.setSize(200, 76);
            save.setFocusable(false);
            save.setVisible(true);

            exit.setBounds(300, 450, 150, 50);
            exit.setSize(200, 76);
            exit.setFocusable(false);
            exit.setVisible(true);

        }
    }

    public void mainMenu(JButton start, JButton exit) {

        start.setBounds(300, 350, 150, 50);
        start.setSize(200, 76);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play = true;
                panel.makeArrayToCheckReflect();
                Panel.State = Panel.STATE.GAME;
                start.setVisible(false);
                exit.setVisible(false);
                load.setVisible(false);
                save.setVisible(false);
            }
        });


        load.setBounds(300, 450, 150, 50);
        load.setSize(200, 76);
        load.setVisible(true);


        ////dokonczyc save button!!!!!!
        save.setBounds(300, 550, 150, 50);
        save.setSize(200, 76);
        save.setVisible(true);


        start.setVisible(true);

        exit.setBounds(300, 650, 150, 50);
        exit.setSize(200, 76);
        exit.setVisible(true);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    private void drawCurrentStatsAndName(Graphics2D g2d) {

        Font statsFont = new Font("Arial", Font.BOLD, 22);
        g2d.setFont(statsFont);


        g2d.setColor(Color.ORANGE);

        g2d.drawRect(0, 350, 250, 380);

        g2d.drawString("All bullets counter :", 10, 380);
        g2d.drawString(String.valueOf(panel.allShootsBulletsnumber), 10, 410);

        g2d.drawString("Enemies Killed :", 10, 480);
        g2d.drawString(String.valueOf(panel.allEnemiesKilled), 10, 510);

        g2d.drawString("Bullets missed :", 10, 580);
        g2d.drawString(String.valueOf(panel.bulletsMissed), 10, 610);

        double ratio = 0;
        String ratioString = "";
        if (panel.allEnemiesKilled > 0 && panel.allShootsBulletsnumber > 0) {
            ratio = (double) panel.allEnemiesKilled / panel.allShootsBulletsnumber;
            DecimalFormat df = new DecimalFormat("#.##");
            ratioString = df.format(ratio);
        }
        g2d.drawString("ratio: ", 10, 680);
        g2d.drawString(ratioString, 10, 710);

        g2d.drawRect(600, 500, 180, 220);

        g2d.drawString("current level :", 610, 540);
        g2d.drawString(String.valueOf(talentPoints.LEVEL), 610, 570);

        g2d.drawString("Score points :", 610, 640);
        g2d.drawString(String.valueOf(talentPoints.score), 610, 670);


    }


    private String checkIfTalentsAvaible(Boolean boolienToCheck, int points) {

        if (boolienToCheck) {
            return "true;" + points + ";";
        }
        return "false;" + points + ";";
    }

    private String loadFromSaveFile(String name) throws IOException {
        String previousSaveFile = "";

        BufferedReader br = new BufferedReader(new FileReader("save.txt"));

        String line = "";
        while ((line = br.readLine()) != null) {
            String[] split = line.split(";");
            if (split[0].equals(name)) {
                System.out.println("ten gracz już kiedyś grał -> aktualizuję wynik");
                String blank = line;
            } else {
                previousSaveFile = line + "\r\n";
            }
        }



        br.close();
        return previousSaveFile;
    }

    private void loadGame() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("save.txt"));
        namesList.clear();

        String line = "";
        int loadNumber = 0;
        while ((line = br.readLine()) != null) {

            String[] split = line.split(";");

            if (split[0] != null) {
                if (!split[0].equals("")) {
                    namesList.add(split[0]);
                    for (int i = 0; i < split.length; i++) {
                        loadData[loadNumber][i] = split[i];
                        System.out.print(split[i] + ";");
                    }
                    System.out.println();
                    loadNumber++;
                }
            }
        }
        br.close();

    }

    private void LoadStats(String name) {
        for (int i = 0; i < loadData.length; i++) {
            if (loadData[i][0] != null && loadData[i][0].equals(name)) {
                    System.out.println("find name choosen! Lets load Stats!");
                    talentPoints.score = Integer.parseInt(loadData[i][1]);
                    panel.scorePoints = talentPoints.score;
                    System.out.println(talentPoints.score);
                    talentPoints.LEVEL = Integer.parseInt(loadData[i][2]);
                    panel.allEnemiesKilled = Integer.parseInt(loadData[i][3]);
                    System.out.println(panel.allEnemiesKilled);
                    panel.allShootsBulletsnumber = Integer.parseInt(loadData[i][4]);
                    System.out.println(panel.allShootsBulletsnumber);
                    talentPoints.addHealth = Boolean.parseBoolean(loadData[i][5]);
                    System.out.println(talentPoints.addHealth);
                    talentPoints.healthPoints = Integer.parseInt(loadData[i][6]);
                    System.out.println(talentPoints.healthPoints);
                    panel.health = panel.health + talentPoints.healthPoints ;
                    hero.trippeShoots = Boolean.parseBoolean(loadData[i][7]);
                    System.out.println(hero.trippeShoots);
                    talentPoints.trippleShootsPoints = Integer.parseInt(loadData[i][8]);
                    System.out.println(talentPoints.trippleShootsPoints);
                    talentPoints.bigShootEnable = Boolean.parseBoolean(loadData[i][9]);
                    System.out.println(talentPoints.bigShootEnable);
                    talentPoints.bigShootsPoints = Integer.parseInt(loadData[i][10]);
                    System.out.println(talentPoints.bigShootsPoints);
            }
        }
        panel.CreateEnemiesAgain();
        loadStats = true;
    }


    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.getSource() == save) {
            String zapis = "";

            zapis = JOptionPane.showInputDialog("Savename");

            try {

                String prev = loadFromSaveFile(zapis);

                BufferedWriter writer = new BufferedWriter(new FileWriter("save.txt"));

                writer.write(prev);
                writer.write(zapis + ";");    //imie;wynik;zaboci;iloscKul;Zdrowie;pktZdrowie;3xPocisk;3Xpkt;DuzyStrzał;pktDużystrzał
                writer.write(talentPoints.score + ";" + talentPoints.LEVEL + ";"  + panel.allEnemiesKilled + ";" + panel.allShootsBulletsnumber + ";");
                writer.write(checkIfTalentsAvaible(talentPoints.addHealth, talentPoints.healthPoints));
                writer.write(checkIfTalentsAvaible(hero.trippeShoots, talentPoints.trippleShootsPoints));
                writer.write(checkIfTalentsAvaible(talentPoints.bigShootEnable, talentPoints.bigShootsPoints));
                ///tutaj metoda która sprawdza jakie talenty są uruchomione
                writer.close();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }


        if (e.getSource() == load) {

            System.out.println("laod!");

            JFrame load = new JFrame("load");
            JPanel loadPanel = new JPanel();
            loadPanel.setLayout(new FlowLayout());
            load.setLocationRelativeTo(panel);
            load.add(loadPanel);
            String[] a;
            try {
                loadGame();
                a = new String[namesList.size()];
                for (int i = 0; i < namesList.size(); i++) {
                    a[i] = namesList.get(i);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            JComboBox<String> list = new JComboBox<String>(a);

            load.setSize(250, 100);
            load.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            load.setVisible(true);

            JButton okButton = new JButton("Load");
            okButton.setVisible(true);
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String choose = (String) list.getSelectedItem();
                    System.out.println("you choose: " + choose);

                    LoadStats(choose);

                }
            });

            loadPanel.add(list);
            loadPanel.add(okButton);

        }

        if (e.getSource() == exit){
            System.exit(0);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == 27) {
            Panel.STATE State = Panel.STATE.GAME;
            panel.makeArrayToCheckReflect();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
