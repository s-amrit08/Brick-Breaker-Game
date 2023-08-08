import javax.swing.*;
import java.awt.event.*;

public class Level  {

    JFrame jFrame=new JFrame("Brick Breaker Game");
    BlockPanel blockPanel;
    int initialScore;

    public void main(int levelNum,int score) //method
    {
        initialScore=score;
        blockPanel=new BlockPanel(levelNum,initialScore);

        if(levelNum==2){
            blockPanel.BallXvel=3;
            blockPanel.BallYvel =4;
        }

        jFrame.add(blockPanel);
        jFrame.setSize(700, 700);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setResizable(false);


        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(jFrame,"Are you sure, you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(result==JOptionPane.YES_OPTION) {
                    jFrame.dispose();
                    jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                }
                else{
                    jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                }

            }
        });

    }



}
