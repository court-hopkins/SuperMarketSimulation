package PJ3;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class GUI extends JFrame {
	

	    public GUI() {
	        
	        initUI();
	    }
	    
	    private void initUI() {

	        add(new Board());
	        
	        setResizable(false);
	        pack();
	        
	        setTitle("Grocery Store Simulation");
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }

	    public static void main(String[] args) {
	        
	        EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {                
	                JFrame ex = new GUI();
	                ex.setVisible(true); 
	            }
	        });
	    }
	}
