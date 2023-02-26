package com.game;
import javax.swing.JFrame;

public class Main{

	public static void main(String[] args) {
		JFrame jf=new JFrame();
		jf.setTitle("Brickk Breaker");
		jf.setSize(700, 600);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setResizable(false);
		GamePlay gamePlay=new GamePlay();
        jf.add(gamePlay);
	}

}