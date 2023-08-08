import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class BlockPanel extends JPanel implements KeyListener, ActionListener {


    ArrayList<String> playerDetails =new ArrayList<>(5);
    Random random =new Random();

    private boolean play=false;
    int no_of_Bricks,levelNo,sc;
    private Timer timer;
    private int BallX,BallY;
    int BallXvel=2, BallYvel =3;
    private int PlayerX=270;
    private BlockMap map;
    int count=0;
    int current_score;
    int[] Score=new int[5];
    String[] Player=new String[5];

    Level level=new Level();


    public BlockPanel(int level,int score){

        this.levelNo=level;
        this.current_score=score;

        setPreferredSize(new Dimension(700,700));
        BallX= random.nextInt(500);
        BallY= random.nextInt(200)+280;

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer=new Timer(5,this);
        timer.start();

        if(levelNo==1) {
            setValue(16, new BlockMap(2, 8));
        }

        else if(levelNo==2){
            setValue(32,new BlockMap(4,8));
        }
    }

    public void setValue(int no_of_Bricks, BlockMap map) {
        this.no_of_Bricks = no_of_Bricks;
        this.map=map;
    }

    @Override
    public void paint(Graphics g) {

        //black Canvas
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 690, 690);

        //LevelNo
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Times new Roman", Font.BOLD, 20));
        g.drawString("Level No." + levelNo, 50, 40);

        //Paddle
        g.setColor(Color.ORANGE);
        g.fillRoundRect(PlayerX, 650, 100, 10, 10, 10);

        //Ball
        g.setColor(new Color(220, 70, 90));
        g.fillOval(BallX, BallY, 25, 25);

        //bricks
        map.draw((Graphics2D) g);

        //score
        g.setColor(Color.yellow);
        g.setFont(new Font("Times new Roman", Font.BOLD, 20));
        g.drawString("Score: " + current_score, 550, 40);

        //gameOver

        if (BallY > 650) {

            play = false;
            BallXvel = 0;
            BallYvel = 0;

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 690, 690);
            g.setFont(new Font("Cambria", Font.BOLD, 25));

            g.setColor(Color.CYAN);
            g.drawString("*Game Over! Total Score: " + current_score, 100, 100);
            g.drawString("*Press 'R' to restart ", 100, 150);
            g.drawString("*Press 'S' to save score", 100, 200);

            try {
                readFile();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            drawLeaderBoard((Graphics2D) g);
        }

        //Level1 Clear
        if (no_of_Bricks <= 0 && levelNo==1) {

                play = false;
                BallXvel = 0;
                BallYvel = 0;

                g.setColor(Color.GREEN);
                g.setFont(new Font("Cambria", Font.BOLD, 30));
                g.drawString("Congratulations! Score: " + current_score, 120, 350);
                g.drawString("Press 'Enter' to continue to next level", 120, 380);

                sc = score(current_score);
        }

        else if(levelNo==2 && no_of_Bricks<=0) {

                play = false;
                BallXvel = 0;
                BallYvel = 0;

                g.setFont(new Font("Comic Sans", Font.BOLD, 30));
                g.drawString("Congratulations! Game completed!",120, 350);
                g.drawString("Total Score:"+current_score,120,390);
                g.drawString("Press 'R' to restart", 120, 430);
                g.drawString("*Press 'S' to save score", 120, 470);

        }
        }

    private void moveLeft() {
        play=true;
        PlayerX-=30;
    }

    private void moveRight() {
        play=true;
        PlayerX+=30;
    }

    public void actionPerformed(ActionEvent e) {

        if(play){

            if(BallX<=0){
                BallXvel=-(BallXvel);
            }
            if(BallX>660){
                BallXvel=-(BallXvel);
            }
            if(BallY<=0){
                BallYvel =-(BallYvel);
            }

            Shape circle=new Ellipse2D.Float(BallX,BallY,25,25);
            Rectangle paddle=new Rectangle(PlayerX,650,100,10);

            if(circle.intersects(paddle)){
                BallYvel =-(BallYvel);
                count++;

                if(count%3==0) {
                    BallYvel--;
                    if (BallXvel > 0)
                        BallXvel++;
                    else
                        BallXvel--;
                }
            }


            L:for(int i=0;i<map.map.length;i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {

                        int width = map.brickWidth;
                        int height = map.brickHeight;
                        int brickX = 80 + j * width;
                        int brickY = 60 + i * height;

                        Rectangle block = new Rectangle(brickX, brickY, width, height);

                        if (circle.intersects(block)) {
                            map.setBrick(0, i, j);
                            no_of_Bricks--;
                            current_score+=5;
                            sc =score(current_score);

                            if (BallX + 19 <= brickX || BallX + 1 >= brickX + width) {
                                BallXvel = -BallXvel;
                            } else{
                                BallYvel = -BallYvel;
                        }
                        break L;
                        }

                    }
                }
            }
            BallX=BallX+BallXvel;
            BallY=BallY+ BallYvel;
        }
        repaint();

    }

    int score(int current_score){
        return current_score;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (PlayerX <= 10)
                PlayerX = 0;
            else
                moveLeft();
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (PlayerX >= 560)
                PlayerX = 580;
            else
                moveRight();
        }

        if (no_of_Bricks == 0 && levelNo==1) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                this.setVisible(false);
                level.main(2, sc);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (!play) {
                play = true;
                current_score = 0;
                levelNo=1;
                BallX = random.nextInt(500);
                BallY = random.nextInt(200) + 300;
                BallXvel = 1;
                BallYvel = 2;
                PlayerX = 270;

                no_of_Bricks = 16;
                map = new BlockMap(2, 8);
            }


        }



        if (e.getKeyCode() == KeyEvent.VK_S) {
            String a = JOptionPane.showInputDialog(this, "Enter your name");

            for (int i = 0; i < Player.length; i++) {

                if (Score[i] == 0) {
                    Player[i] = a;
                    Score[i] = current_score;
                    break;
                }

                else if(Player[i].equals(a)){
                    if(current_score>Score[i]){
                        Score[i]=current_score;
                        break;
                    }
                }

                else if (current_score > Score[i] && i!=4) {
                    for (int j = 3; j >= i; j--) {
                        Player[j + 1] = Player[j];
                        Score[j + 1] = Score[j];
                    }
                    Player[i]=a;
                    Score[i]=current_score;
                    break;
                }

                else if(current_score>Score[4] &&Player[4]!=null) {
                    Player[4]=a;
                    Score[4]=current_score;
                }
            }

            writeInFile();

        }
        repaint();

    }

    public void writeInFile(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("LeaderBoard.txt"));

            for (int i = 0; i < Player.length; i++) {
                bw.write(Player[i] + ":" + Score[i]);
                bw.newLine();
            }
            bw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void readFile(){
        playerDetails.clear();
        try{
            BufferedReader br=new BufferedReader(new FileReader("LeaderBoard.txt"));
             String line = br.readLine();
             while (line != null) {
                 playerDetails.add(line);
                line = br.readLine();
             }
             br.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void drawLeaderBoard(Graphics2D g) {

        int boxWidth = 250;
        int boxHeight = 200;


        for (int i = 0; i < Score.length; i++) {
            g.setColor(new Color(250,150,128));
            g.drawString("LEADERBOARD", 240, 330);

            g.setColor(Color.CYAN);
            g.drawString(playerDetails.get(i), 210, i * 25 + 365);
        }
        g.setColor(Color.WHITE);
        g.drawLine(200,337,450,337);
        g.drawRect(200, 300, boxWidth, boxHeight);
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }
}

