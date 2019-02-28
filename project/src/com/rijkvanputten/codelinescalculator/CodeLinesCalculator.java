package com.rijkvanputten.codelinescalculator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class CodeLinesCalculator {
	private JFrame f;
	private JPanel contentPanel;
	private JPanel panel;
	
	private JButton calculateButton;
	
	private JTextField directoryTextField;
	private JTextField extensionTextField;
	
	private JLabel outputLabel;
			
	public CodeLinesCalculator(String title) {
		contentPanel = new JPanel();
		contentPanel.setBounds(0, 0, 600, 400);
		
		panel = new JPanel();
		GridLayout gridLayout = new GridLayout(0,1);
		gridLayout.setVgap(10);
        panel.setLayout(gridLayout);
        
        ShowLabel("Directory");
        directoryTextField = new JTextField();
		panel.add(directoryTextField);
		
		ShowLabel("Extensions (seperate with ,)");
		extensionTextField = new JTextField();
		panel.add(extensionTextField);
		
        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  Calculate(directoryTextField.getText(), extensionTextField.getText());
          }
        });
        panel.add(calculateButton);
        
        outputLabel = new JLabel("");
        panel.add(outputLabel);
        
		contentPanel.add(panel);
		
		f = new JFrame(title);
		f.setVisible(true);
		f.setSize(600, 400);
		f.add(contentPanel);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void ShowLabel(String text)
	{
		JLabel label = new JLabel(text);
		panel.add(label);
	}
	
	public static void main(String[] args) {
		SetSystemUILook();
		
		@SuppressWarnings("unused")
		CodeLinesCalculator test = new CodeLinesCalculator("Code Lines Calculator");
	}
	
	public String[] CommaStrToArray(String input)
	{
		return input.replaceAll("\\s+","").split(",");
	}
	
	public void Calculate(String directory, String extensionInput)
	{
		final File folder = new File(directory);
		if (!folder.exists()) {
			outputLabel.setText("Folder does not exists!");
			return;
		}
		if (extensionInput == null || extensionInput == "")
		{
			outputLabel.setText("No extensions set!");
			return;
		}
		CodeLinesData data = Calculator.Calculate(folder, CommaStrToArray(extensionInput));
		outputLabel.setText(data.GetAsString());
	}
	
	public static void SetSystemUILook()
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}