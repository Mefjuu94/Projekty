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
    JButton goToShop;
    Panel panel;

    ArrayList<String> namesList = new ArrayList<String>();
    String[][] loadData = new String[99][11];

    JButton setFullscreen = new JButton("Fullscreen Mode");

    public MENU(TalentPoints talentPoints, Hero hero, Panel panel, JButton save, JButton load, JButton exit,JButton goToShop) {
        this.talentPoints = talentPoints;
        this.hero = hero;
        this.panel = panel;
        this.save = save;
        this.load = load;
        this.exit = exit;
        this.goToShop = goToShop;

        save.addMouseListener(this);
        load.addMouseListener(this);
        exit.addMouseListener(this);
        goToShop.addMouseListener(this);
        setFullscreen.addMouseListener(this);
        panel.add(setFullscreen);
        setFullscreen.setVisible(false);
    }

    public void Draw(Graphics2D g2d, JButton start, JButton exit) {

        if (y > 0) {
            y -= 15;
        } else {
            y = 0;
        }
        if (panel.fullScreenMode || panel.resizeMode){
        logo.paintIcon(panel, g2d, panel.getWidth()/2-400, y);
        }else {
            logo.paintIcon(panel, g2d, 0, y);
        }

        if (y == 0) {
            mainMenu(start, exit);
            drawCurrentStatsAndName(g2d);
        }

    }

    public void pause(Graphics2D g2d) {

        //TODO ogarnąć temat pauzy
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

            goToShop.setBounds(panel.getWidth()-150, 750, 150, 30);
            goToShop.setSize(150, 30);
            goToShop.setFocusable(false);
            goToShop.setVisible(true);

        }
    }

    public void mainMenu(JButton start, JButton exit) {

        start.setBounds(panel.getWidth()/2 - 100, 350, 150, 50);
        start.setSize(200, 76);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play = true;
                //panel.makeArrayToCheckReflect();
                Panel.State = Panel.STATE.GAME;
                start.setVisible(false);
                exit.setVisible(false);
                load.setVisible(false);
                save.setVisible(false);
                goToShop.setVisible(false);
                setFullscreen.setVisible(false);
            }
        });


        load.setBounds(panel.getWidth()/2 - 100, 450, 150, 50);
        load.setSize(200, 76);
        load.setVisible(true);


        // TODO//dokonczyc save button!!!!!!
        save.setBounds(panel.getWidth()/2 - 100, 550, 150, 50);
        save.setSize(200, 76);
        save.setVisible(true);


        start.setVisible(true);

        exit.setBounds(panel.getWidth()/2 - 100, 650, 150, 50);
        exit.setSize(200, 76);
        exit.setVisible(true);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        goToShop.setBounds(panel.getWidth() - 180, panel.getHeight() - 50, 150, 30);
        goToShop.setSize(150, 30);
        goToShop.setVisible(true);


        setFullscreen.setBounds(50, panel.getHeight() - 50, 150, 30);
        setFullscreen.setSize(150, 30);
        setFullscreen.setFocusable(false);
        setFullscreen.setVisible(true);

    }

    private void drawCurrentStatsAndName(Graphics2D g2d) {

//        Font statsFont = new Font("Arial", Font.BOLD, 22);
//        g2d.setFont(statsFont);
//
//
//        g2d.setColor(Color.ORANGE);
//
//        g2d.drawRect(0, 350, 250, 380);
//
//        g2d.drawString("All bullets counter :", 10, 380);
//        g2d.drawString(String.valueOf(panel.allShootsBulletsnumber), 10, 410);
//
//        g2d.drawString("Enemies Killed :", 10, 480);
//        g2d.drawString(String.valueOf(panel.allEnemiesKilled), 10, 510);
//
//        g2d.drawString("Bullets missed :", 10, 580);
//        g2d.drawString(String.valueOf(panel.bulletsMissed), 10, 610);
//
//        double ratio = 0;
//        String ratioString = "";
//        if (panel.allEnemiesKilled > 0 && panel.allShootsBulletsnumber > 0) {
//            ratio = (double) panel.allEnemiesKilled / panel.allShootsBulletsnumber;
//            DecimalFormat df = new DecimalFormat("#.##");
//            ratioString = df.format(ratio);
//        }
//        g2d.drawString("ratio: ", 10, 680);
//        g2d.drawString(ratioString, 10, 710);
//
//        g2d.drawRect(panel.getWidth()-200, 500, 180, 220);
//
//        g2d.drawString("current level :", panel.getWidth()-190, 540);
//        g2d.drawString(String.valueOf(talentPoints.LEVEL), panel.getWidth()-190, 570);
//
//        g2d.drawString("Score points :", panel.getWidth()-190, 640);
//        g2d.drawString(String.valueOf(talentPoints.score), panel.getWidth()-190, 670);


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

        if (e.getSource() == goToShop){
            if (panel.allEnemiesKilled == 0 && talentPoints.LEVEL < 2 && panel.scorePoints < 1) {
                int dialog = JOptionPane.showConfirmDialog(panel, "first Load game or get some points!", "SHOP Warning", 0);
            }else {
                panel.start.setVisible(false);
                load.setVisible(false);
                save.setVisible(false);
                exit.setVisible(false);
                goToShop.setVisible(false);
                setFullscreen.setVisible(false);

                talentPoints.AddPanel(panel.fullScreenMode,panel.resizeMode);
                talentPoints.setButtons(true);
                talentPoints.turnOnOffShopButtons(true);

                Panel.State = Panel.STATE.TalentPoints;
            }

        }

        if (e.getSource() == setFullscreen){
            System.out.println("klik na fullSCREEN!");

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenHeight = screenSize.height;
            int screenWidth = screenSize.width;
            System.out.println(screenHeight);
            System.out.println(screenWidth);
            System.out.println("panel " + panel.getHeight());
            System.out.println("panel: " + panel.getWidth());

            if (panel.getHeight() < screenHeight && panel.getWidth() < screenWidth ){

                //panel.setMaximumSize(new Dimension());
                System.out.println("jest mniejsze powinienem powiększyć");
                panel.ramka.setExtendedState(panel.ramka.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                panel.fullScreenMode = !panel.fullScreenMode;
                setFullscreen.setText("SmallScreen mode");
                panel.firstaid = new Help(panel.ramka.getWidth());
                panel.ammo = new Ammo(panel.ramka.getWidth());
                hero.x = panel.ramka.getWidth()/2-50;
            }else if (panel.getHeight() == screenHeight -71 && panel.getWidth() == screenWidth){
                System.out.println("jest większe powienien zmienjszyc");
                panel.ramka.setSize(800,830);
                panel.firstaid = new Help(panel.ramka.getWidth());
                panel.ammo = new Ammo(panel.ramka.getWidth());
                setFullscreen.setText("Fullscreen Mode");
                hero.x = panel.ramka.getWidth()/2-50;
                panel.fullScreenMode = !panel.fullScreenMode;
            }
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
            //panel.makeArrayToCheckReflect();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
