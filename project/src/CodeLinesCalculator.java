import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

// c
public class CodeLinesCalculator {
	private JFrame f;
	private JPanel contentPanel;
	private JPanel panel;
	
	private JButton calculateButton;
	
	private JTextField directoryTextField;
	private JTextField extensionTextField;
	
	private JLabel outputLabel;
	
	static ArrayList<File> currentFiles = new ArrayList<File>();
			
	public CodeLinesCalculator(String title) {
		contentPanel = new JPanel();
		contentPanel.setBounds(0, 0, 600, 400);
		
		panel = new JPanel();
		GridLayout gridLayout = new GridLayout(0,1);
		gridLayout.setVgap(10);
        panel.setLayout(gridLayout);
        
        //C:\Users\rijkv\Desktop\Example Folder
        
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
		
		
		for (String comma : CommaStrToArray(extensionInput))
		{
			System.out.println(comma);
		}
		
		currentFiles.clear();
		listFilesForFolder(folder, CommaStrToArray(extensionInput));
		
		int totalLineCounter = 0;
		int totalCodeLineCounter = 0;
		int totalCommentCounter = 0;
		for (File file : currentFiles)
		{
			try(BufferedReader br = new BufferedReader(new FileReader(file))) {
				int lineCounter = 0;
				int codeLineCounter = 0;
				int commentCounter = 0;
			    for(String line; (line = br.readLine()) != null; ) {
			    	lineCounter++;
			    	boolean validFound = false;
			    	boolean isComment = false;
			    	for(char c : line.toCharArray()) {
			    		if (validFound) {
				    	    codeLineCounter++;
			    			break;
			    		}
			    		if (isComment) {
			    			commentCounter++;
			    			break;
			    		}
			    	    switch (c) //ONLY ADD LINE IF SOMETHING ELSE FOUND THEN EMPTY LINES OR COMMENTS
			    	    {
			    	    	case '\n':
			    	    		continue;
			    	    	case '\t':
			    	    		continue;
			    	    	case ' ':
			    	    		continue;
			    	    	case '{':
			    	    		continue;
			    	    	case '}':
			    	    		continue;
			    	    	case '/':
			    	    		isComment = true;
			    	    		continue;
			    	    	default:
			    	    		validFound = true;
			    	    		break;
			    	    }
			    	}
			    }
			    System.out.println("[FILE] " + file.getName() + " CODE LINES: " + codeLineCounter + " LINES: " + lineCounter
			    		+ " COMMENTS: " + commentCounter);
			    totalLineCounter += lineCounter;
			    totalCodeLineCounter += codeLineCounter;
			    totalCommentCounter += commentCounter;
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
		//System.out.println("[TOTAL] LINES: " + totalLineCounter + " CODE LINES: " + totalCodeLineCounter + " COMMENTS: " + totalCommentCounter);
		outputLabel.setText("LINES: " + totalLineCounter + "\n CODE LINES: " + totalCodeLineCounter + " COMMENTS: " + totalCommentCounter);
	}
	
	public static void SetSystemUILook()
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void listFilesForFolder(final File folder, String[] exstensions) {
	    for (final File fileEntry : folder.listFiles()) {
	    	
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry, exstensions);
	        } else {
	        	boolean contains = Arrays.stream(exstensions).anyMatch(getFileExtension(fileEntry)::equals);
	            if (contains)
	            {
	            	currentFiles.add(fileEntry);
	            }
	        }
	    }
	}
	public static String getFileExtension(File file) {
	    String name = file.getName();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; 
	    }
	    return name.substring(lastIndexOf);
	}
}


