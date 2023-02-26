package com.game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements ActionListener,KeyListener
{
  private boolean play=false;
  private int score=0;
  private int totalBricks=21;
  private Timer timer;
  private int delay=8;
  private int ballposX=120;
  private int ballposY=350;
  private int ballXdir=-1;
  private int ballYdir=-2;
  private int playerX=350;
  private MapGenerator map;
  
  public GamePlay() {
	  addKeyListener(this);
	  setFocusable(true);
	  setFocusTraversalKeysEnabled(true);
	  timer=new Timer(delay,this);
	  timer.start();
	  map=new MapGenerator(3,7);
  }
  public void paint(Graphics g) {
	  
	  //black Canvas
	  g.setColor(Color.BLACK);
	  g.fillRect(1,1,692,592);
	  
	  //Border
	  g.setColor(Color.YELLOW);
	  g.fillRect(0,0,692,3);
	  g.fillRect(0,3,3,592);
	  g.fillRect(681,3,3,592);
	  
	  //padel
	  g.setColor(Color.GREEN);
	  g.fillRect(playerX, 550,100,8);
	  
	  // ball
	   g.setColor(Color.RED);
	   g.fillOval(ballposX,ballposY,20,20);
	   
	   //brickes
	   map.draw((Graphics2D)g);
	   
	   //score
	   g.setColor(Color.GREEN);
	   g.setFont(new Font("serif",Font.BOLD,20));;
	   g.drawString("Score:"+score, 550,30);
	   
	   //gameOver
	   if(ballposY>=570) {
		   play=false;
		   ballXdir=0;
		   ballYdir=0;
		   g.setColor(Color.RED);
		   g.setFont(new Font("serif",Font.BOLD,40));
		   g.drawString("GameOver!!!",200,300);
		   g.setColor(Color.GREEN);
		   g.drawString("Score:"+score,230,350);
		   
		   g.setFont(new Font("serif",Font.BOLD,20));
		   g.drawString("Press Enter To Restart ",200,400);
	   }
	   
	   //PlayerWon
	   if(totalBricks<=0) {
		   play=false;
		   ballXdir=0;
		   ballYdir=0;
		   g.setColor(Color.BLUE);
		   g.setFont(new Font("serif",Font.BOLD,40));
		   g.drawString("You Won",230,300);
		   g.setColor(Color.GREEN);
		   g.drawString("Score:"+score,230,350);
		   
		   g.setFont(new Font("serif",Font.BOLD,20));
		   g.drawString("Press Enter To Restart ",200,400);
	   }
	   ;
  }
  private void moveLeft() {
	  play=true;
	  playerX-=20;
  }
  private void moveRight() {
	  play=true;
	  playerX+=20;
  }

@Override
public void keyPressed(KeyEvent e) {
	if(e.getKeyCode()==KeyEvent.VK_LEFT) {
	  if(playerX<=0) {
		   playerX=0;
	   }
	  else {  
		  moveLeft();
	   }
	}
	if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
		if(playerX>=600) {
		   playerX=600;
	    }
	   else { 
		  
		  moveRight();
	   }
	}
	
	if(e.getKeyCode()==KeyEvent.VK_ENTER) {
		if(!play){
			score=0;
			totalBricks=21;
			ballposX=120;
			ballposY=350;
			ballXdir=-1;
			ballYdir=-2;
			playerX=350;
			map=new MapGenerator(3,7);
			
		}
	}
	repaint();
	
}

@Override
public void actionPerformed(ActionEvent e) {
	if(play) {
		if(ballposX<=0) {
			ballXdir=-ballXdir;
		}
		if(ballposX>=670) {
			ballXdir=-ballXdir;
		}
		if(ballposY<=0) {
			ballYdir=-ballYdir;
		}
		Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
		Rectangle padelRect=new Rectangle(playerX,550,100,8);
		
		if(ballRect.intersects(padelRect)) {
			ballYdir=-ballYdir;
		}
		A:for(int i=0;i<map.map.length;i++) {
			for(int j=0;j<map.map[0].length;j++) {
				if(map.map[i][j]>0) {
					int width=map.brickWidth;
					int height=map.brickHeight;
					int brickXpos=80+j*width;
					int brickYpos=50+i*height;
					
					Rectangle brickRect=new Rectangle(brickXpos,brickYpos,width,height);
					
					if(ballRect.intersects(brickRect)) {
						map.setBrick(0,i,j);
						score++;
						totalBricks--;
						
						if(ballposX+19<=brickXpos|| ballposX+1>=brickXpos+width) {
							  ballXdir=-ballXdir;
						}
						else {
							ballYdir=-ballYdir;
						}
						break A;
					}
				}
			}
		}
		
		ballposX+=ballXdir;
		ballposY+=ballYdir;
	}
	repaint();
	
}


@Override
public void keyTyped(KeyEvent e){}
@Override
public void keyReleased(KeyEvent e){}

}

