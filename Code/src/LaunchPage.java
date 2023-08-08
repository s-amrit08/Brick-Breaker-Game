import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaunchPage extends JFrame implements ActionListener{

    //ball's distance from x axis
    private int ballX=250;

    //paddle's distance from x axis
    private int paddleX=210;

    //ball's and paddle's initial x direction
    private int ballXdir=-15;
    private int paddleXdir=-15;

    Timer timer;

    JButton jButton1=new JButton("PLAY NEW GAME");
    JButton jButton2=new JButton("EXIT");
    public Level newLevel = new Level();



    public LaunchPage()
    {

        getContentPane().setBackground(new Color(0,0,30));

        timer=new Timer(60, this);
        timer.start();

        jButton1.setBounds(270,340,150,30);
        jButton1.setBackground(Color.ORANGE);
        jButton1.setForeground(Color.BLACK);
        add(jButton1);
 
        jButton2.setBounds(270,380,150,30);
        jButton2.setBackground(Color.ORANGE);
        jButton2.setForeground(Color.BLACK);
        add(jButton2);

        //PLAY button functionality
        jButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                            dispose();
                            newLevel.main(1,0);
                }

        }) ;

        //exit button functionality
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });



        setTitle("Brick breaker Game");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    @Override
    public void paint(Graphics g) {
        {
            super.paint(g);

            g.setColor(new Color(153,76,0));
            g.setFont(new Font("Times New Roman", Font.BOLD,50));
            g.drawString("Brick Breaker Game",140,170);

            g.setColor(Color.white);
            g.drawRect(130,120,470,70);

            //Paddle
            g.setColor(new Color(76,0,153));
            g.fillRoundRect(paddleX,675,100,15,15,15);

            //Ball
            g.setColor(new Color(255,125,0));
            g.fillOval(ballX,650,25,25);

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       animate();
    }

    public void animate(){
        if(ballX<=40){
            ballXdir=-ballXdir;
        }

        else if(ballX>620){
            ballXdir=-ballXdir;
        }

        if(paddleX<=0){
            paddleXdir=-paddleXdir;
        }

        else if(paddleX>580){
            paddleXdir=-paddleXdir;
        }

        ballX=ballX+ballXdir;
        paddleX=paddleX+paddleXdir;
        repaint();
    }


    public static void main(String[] args)  {
        new LaunchPage();
    }

}