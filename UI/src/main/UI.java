package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.mathworks.engine.*;

public class UI implements ActionListener{
	
	JFrame window = new JFrame();
	JButton close = new JButton();
	JButton Calculate = new JButton();

	JButton[] Material = new JButton[5];
	JButton[] Device = new JButton[10];
	
	JLabel[] Iteam = new JLabel[4*3];
	JTextField[] input = new JTextField[Iteam.length];
	
	JLabel Output = new JLabel();
	
    MatlabEngine matlab;

	
	public UI() {
		window.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setUndecorated(true);
		window.setVisible(true);
		close.setSize(50, 50);
		close.setText("X");
		close.setLocation(window.getWidth()-50,0);
		close.addActionListener(this);
		window.add(close);
	}
	public void Materis_Setter() {
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == close) {
			System.exit(0);
		}
		if(e.getSource() == Calculate) {
			
		}
	}
	
	
}
