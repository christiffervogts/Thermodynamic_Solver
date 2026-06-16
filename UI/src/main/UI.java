package main;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

public class UI implements ActionListener {

    JFrame window = new JFrame();
    JButton close = new JButton("X");
    JButton calculate = new JButton("Calculate");

    JButton[] Material = new JButton[5];
    JLabel State_Number_Question = new JLabel("How many states are there?");
    JFormattedTextField State_Number_input;

    JComboBox<String>[] DeviceDropdowns;   // NEW: device dropdowns

    JPanel materialPanel = new JPanel();
    JPanel devicePanel = new JPanel();
    JPanel inputPanel = new JPanel();

    String[] Material_Name = {"Ideal Gas", "Water", "Carbon Dioxide", "R410a", "R314a"};
    String[] Device_Name = {"Piston", "Nozzle", "Compressor", "Condenser", "Boiler", "Pump", "Turbine"};
    String[] Item_Name = {"State ", "P", "V", "T", "x", "v", "u", "h", "s"};

    int Material_chosen = -1;
    int Number_of_States = 0;

    Timer updateTimer;
    @SuppressWarnings({"unused" })
    public UI() {

        window.setSize(900, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setUndecorated(true);
        window.setLayout(null);
        window.setVisible(true);

        close.setBounds(window.getWidth() - 50, 0, 50, 50);
        close.addActionListener(this);
        window.add(close);

        materialPanel.setLayout(new GridLayout(Material.length, 1, 5, 5));
        materialPanel.setBounds(175, 60, 150, 300);
        window.add(materialPanel);

        for (int i = 0; i < Material.length; i++) {
            Material[i] = new JButton(Material_Name[i]);
            Material[i].addActionListener(this);
            Material[i].setBackground(Color.gray);
            materialPanel.add(Material[i]);
        }

        State_Number_Question.setBounds(10, 60, 200, 30);
        window.add(State_Number_Question);

        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setAllowsInvalid(false);

        State_Number_input = new JFormattedTextField(formatter);
        State_Number_input.setBounds(10, 100, 100, 30);
        State_Number_input.addActionListener(e -> rebuildDeviceDropdowns());
        window.add(State_Number_input);

        devicePanel.setLayout(new GridLayout(0, 1, 5, 5));
        devicePanel.setBounds(350, 60, 200, 300);
        window.add(devicePanel);

        inputPanel.setLayout(new GridLayout(0, 2, 5, 5));
        inputPanel.setBounds(600, 60, 250, 400);
        window.add(inputPanel);

        calculate.setSize(200, 100);
        calculate.setLocation(20, 400);
        window.add(calculate);
        
        updateTimer = new Timer(1000 / 60, e -> window.repaint());
        updateTimer.start();
    }

    @SuppressWarnings("unchecked")
	private void rebuildDeviceDropdowns() {
    	
        String text = State_Number_input.getText();
        
        if (text.isEmpty()) return;

        Number_of_States = Integer.parseInt(text);

        devicePanel.removeAll();
        
        DeviceDropdowns = new JComboBox[Number_of_States];

        for (int i = 0; i < Number_of_States; i++) {
            DeviceDropdowns[i] = new JComboBox<>(Device_Name);
            devicePanel.add(DeviceDropdowns[i]);
        }
        
        rebuildInputFields();
        
        devicePanel.revalidate();
        
        devicePanel.repaint();
    
    }

    private void rebuildInputFields() {
        inputPanel.removeAll();

        for (int s = 0; s < Number_of_States; s++) {
            for (int i = 0; i < Item_Name.length; i++) {
            	if(i != 0) {            		
            		JLabel label = new JLabel(Item_Name[i] + " " + (s + 1));
            		JTextField field = new JTextField();
            		inputPanel.add(label);
            		inputPanel.add(field);
            	}
            }
        }

        inputPanel.revalidate();
        inputPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == close) {
            System.exit(0);
        }
        if(e.getSource() == State_Number_input) {
            State_Number_input.setText(null);
        }
        for (int i = 0; i < Material.length; i++) {
            if (e.getSource() == Material[i]) {
                Material_chosen = i;
            }
            Material[i].setBackground(i == Material_chosen ? Color.green : Color.gray);
        }
        if(e.getSource() == calculate) {
        	
        }
    }
}
