package PJ3;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener, KeyListener {


	private static final long serialVersionUID = 1L;
	private Image cashier1, shoppingCart, shop2, isle, shop3, shop4, shop5;
    private int x=0, y=0, xvel=0, yvel=0, winX=980, winY=486;
    Timer timer = new Timer(50, this);

    public Board() {

        initBoard();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    private void loadImage() {
    	/*
    	//for cashier 1
        ImageIcon ii = new ImageIcon("C:/Users/Court/workspace/csc 220/src/images/cashier1.png");
        cashier1 = ii.getImage();
        
        //for shopper 2
        ImageIcon shp2 = new ImageIcon("C:/Users/Court/workspace/csc 220/src/images/shop2.png");
        shop2= shp2.getImage();
        
        //for shopper 3
        ImageIcon shp3 = new ImageIcon("C:/Users/Court/workspace/csc 220/src/images/shop4.png");
        shop3= shp3.getImage();
        
        //for shopper 1
        ImageIcon c1 = new ImageIcon("C:/Users/Court/workspace/csc 220/src/images/cart.png");
        customer1 = c1.getImage();*/
    	

        
        //image background
        ImageIcon is = new ImageIcon("C:/Users/Court/workspace/csc 220/src/images/background.png"); //980x486
        isle= is.getImage();
        
        //for shopper 1
        ImageIcon c1 = new ImageIcon("C:/Users/Court/workspace/csc 220/src/images/cartsmall.png");
        shoppingCart = c1.getImage();
        
    }

    private void initBoard() {
        setPreferredSize(new Dimension(winX, winY)); //dimensions of the popup window
        setDoubleBuffered(true);
        loadImage();
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawShopper2(g);
    }
    
    void drawBackground(Graphics g){
        g.drawImage(isle, 0, 0, null );
    }
    
    private void drawShopper2(Graphics g) {

        g.drawImage(shoppingCart, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }
/*
    private void drawShopper1(Graphics g) {

        g.drawImage(customer1, 300, 100, this);
        Toolkit.getDefaultToolkit().sync();
    }*/

    /*
    private void drawCashier1(Graphics g) {

        g.drawImage(cashier1, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {


    	if (x<0){
    		xvel=0;
    		x=0;
    	}
    	if (x>950){
    		xvel=0;
    		x=950;
    	}
    	if(y<0){
    		yvel=0;
    		x=0;
    	}
    	if(y>430){
    		yvel=0;
    		x=430;
    	}
    	x=x+xvel*10;
    	y=y+yvel*10;
        repaint();
    }
    
    public void keyPressed(KeyEvent e){
    	int c=e.getKeyCode();
    	
    	if(c==KeyEvent.VK_LEFT){
    		xvel=-1;
    		yvel=0;
    	}
    	if(c==KeyEvent.VK_UP){
    		xvel=0;
    		yvel=-1;
    	}
    	if(c==KeyEvent.VK_RIGHT){
    		xvel=1;
    		yvel=0;
    	}
    	if(c==KeyEvent.VK_DOWN){
    		xvel=0;
    		yvel=1;
    	}
    }


	public void keyReleased(KeyEvent e) {
		xvel=0;
		yvel=0;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
    
}
