package main;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import com.mathworks.engine.*;

public class UI implements ActionListener{
	
	JFrame window = new JFrame();
	JButton close = new JButton();
	JButton Calculate = new JButton();

	JButton[] Material = new JButton[5];
	JButton[] Device = new JButton[7];
	
	JLabel[] Item = new JLabel[9*4];
	JTextField[] input = new JTextField[Item.length];
	
	JLabel Output = new JLabel();
	JLabel State_Number_Question = new JLabel();
	
	JFormattedTextField  State_Number_input;
	
    MatlabEngine matlab;

    String[] Material_Name = {"Ideal Gas", "Water", "Carbon Dioxide", "R410a", "R314a"};
    String[] Device_Name = {"Piston", "Nozzle", "Compressor", "Condenser", "Boiler", "Pump", "Turbine"};
    String[] Item_Name = {"State ","P", "V", "T", "x","v", "u", "h", "s"};
    
    int Material_chosen = -1;
    int Number_of_States = 0;
    int state_counter = 0;
    ArrayList<Integer> Devices_chosen = new ArrayList<>();
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
		Material_Setter();
		Device_Setter();
		State_Number_Question.setText("How meny states are there?");
		State_Number_Question.setLocation(50, 55*6);
		State_Number_Question.setSize(160, 50);
		window.add(State_Number_Question);
		NumberFormat format = NumberFormat.getIntegerInstance();
		format.setGroupingUsed(false);
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setAllowsInvalid(false);		
		State_Number_input = new JFormattedTextField (formatter);
		State_Number_input.setLocation(50, 55*6+50);
		State_Number_input.setSize(160, 50);
		window.add(State_Number_input);
		
		
		
		window.repaint();
	}
	public void Material_Setter() {
		for(int i = 0; i < Material.length; i++) {
			Material[i] = new JButton();
			Material[i].setSize(150, 50);
			Material[i].setText(Material_Name[i]);
			Material[i].setLocation(50, 55*(i+1));
			Material[i].addActionListener(this);
			Material[i].setBackground(Color.gray);
			window.add(Material[i]);
		}
	}
	public void Device_Setter() {
		for(int i = 0; i < Device.length; i++) {
			Device[i] = new JButton();
			Device[i].setSize(150, 50);
			Device[i].setText(Device_Name[i]);
			Device[i].setLocation(250, 55*(i+1));
			Device[i].addActionListener(this);
			Device[i].setBackground(Color.gray);
			window.add(Device[i]);
		}

	}
	public void Inputs() {
		for(int i = 0; i < Number_of_States*Item_Name.length; i++) {
			Item[i] = new JLabel();
			if(i > Item_Name.length) {				
				Item[i].setText(Item_Name[i-Item_Name.length]);
			}
			else {
				Item[i].setText(Item_Name[i] + "<html><sub>"+Math.floor(i/9)+"</sub> =</html>");				
			}
			int x = 300;
			int y = 50+i*10;
			Item[i].setLocation(x,y);
			Item[i].setSize(10, 10);
			window.add(Item[i]);

		}
		window.repaint();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == close) {
			System.exit(0);
		}
		if(e.getSource() == Calculate) {
			
		}
		
		for(int i = 0; i < Material.length; i++) {
			if(e.getSource() == Material[i]) {
				Material_chosen = i;
				System.out.println(Material_Name[i]);
				Material[i].setBackground(Color.green);
			}
			else {
				Material[i].setBackground(Color.gray);
			}
		}
		
		for(int i = 0; i < Device.length; i++) {
			if(e.getSource() == Device[i]) {
				Device[i].setBackground(Color.green);
				Devices_chosen.add(i);
				state_counter++;
				Inputs();
				if(state_counter > Integer.parseInt(State_Number_input.getText())) {
					state_counter = 0;
					Devices_chosen.clear();;
				}
			}
			if(Devices_chosen.contains(i)) {
				Device[i].setBackground(Color.green);
			}
			else {
				Device[i].setBackground(Color.gray);
			}
		}
		
	}
	
	
}
