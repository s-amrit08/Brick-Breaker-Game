import java.awt.*;

public class BlockMap extends Rectangle {

    int brickWidth;
    int brickHeight;
    int[][] map;

    public BlockMap(int row, int col) {

        map=new int[row][col];
        brickWidth= 550/col;
        brickHeight= 200/row;

        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                map[i][j]=1;
            }
        }

    }

    public void setBrick(int value,int r,int c){
        map[r][c]=value;
    }

    public void draw(Graphics2D g){

        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){

                if(map[i][j]>0){
                    if(i==0){
                        g.setColor(new Color(230,50,80));
                        g.fillRect(j*brickWidth+80,i*brickHeight+60,brickWidth,brickHeight);

                    }

                    else if(i==1){
                        g.setColor(new Color(30,140,30));
                        g.fillRect(j*brickWidth+80,i*brickHeight+60,brickWidth,brickHeight);

                    }

                    else if(i==2){
                        g.setColor(new Color(90,100,250));
                        g.fillRect(j*brickWidth+80,i*brickHeight+60,brickWidth,brickHeight);

                    }

                    else if(i==3){
                        g.setColor(new Color(255,215,0));
                        g.fillRect(j*brickWidth+80,i*brickHeight+60,brickWidth,brickHeight);

                    }

                    g.setColor(Color.WHITE);
                    g.setStroke(new BasicStroke(3));
                    g.drawRect(j*brickWidth+80,i*brickHeight+60,brickWidth,brickHeight);
                }
            }
        }


    }
}
