package main;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.text.NumberFormatter;

import com.mathworks.engine.MatlabEngine;
import com.mathworks.engine.*;
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
    public static List<List<Double>> runMatlabFunction(String location, List<Double> peram1,
    		List<Double> peram2, List<Double> peram3, List<Double> peram4, List<Double> peram5,
    		List<Double> peram6, List<Double> peram7, List<Double> peram8)
    		throws InterruptedException, ExecutionException, MatlabExecutionException,
    		MatlabSyntaxException {
        
    	
    	MatlabEngine engine = MatlabEngine.startMatlab();
        List<List<Double>> results = new ArrayList<>();

        try {
            engine.eval("addpath('" + location + "')");

            engine.putVariable("p1", peram1.stream().mapToDouble(Double::doubleValue).toArray());
            engine.putVariable("p2", peram2.stream().mapToDouble(Double::doubleValue).toArray());
            engine.putVariable("p3", peram3.stream().mapToDouble(Double::doubleValue).toArray());
            engine.putVariable("p4", peram4.stream().mapToDouble(Double::doubleValue).toArray());
            engine.putVariable("p5", peram5.stream().mapToDouble(Double::doubleValue).toArray());
            engine.putVariable("p6", peram6.stream().mapToDouble(Double::doubleValue).toArray());
            engine.putVariable("p7", peram7.stream().mapToDouble(Double::doubleValue).toArray());
            engine.putVariable("p8", peram8.stream().mapToDouble(Double::doubleValue).toArray());

            engine.eval("[o1, o2, o3, o4, o5, o6, o7, o8] = matlabFunction(p1, p2, p3, p4, p5, p6, p7, p8);");

            String[] outputVars = {"o1", "o2", "o3", "o4", "o5", "o6", "o7", "o8"};
            for (String var : outputVars) {
                double[][] matrix = engine.getVariable(var);
                List<Double> resultList = new ArrayList<>();
                for (double[] row : matrix) {
                    for (double val : row) {
                        resultList.add(val);
                    }
                }
                results.add(resultList);
            }
        } finally {
            engine.close();
        }

        return results;
    }
}
