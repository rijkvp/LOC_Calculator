package com.rijkvanputten.codelinescalculator;

import com.rijkvanputten.codelinescalculator.CodeLinesData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Calculator {
	private static ArrayList<File> currentFiles = new ArrayList<File>();
	
	public static void GetFilesInFolder(final File directory, String[] exstensions) {
	    for (final File fileEntry : directory.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	GetFilesInFolder(fileEntry, exstensions);
	        } else {
	            if (Arrays.stream(exstensions).anyMatch(GetFileExtension(fileEntry)::equals))
	            {
	            	currentFiles.add(fileEntry);
	            }
	        }
	    }
	}
	
	public static String GetFileExtension(File file) {
	    String name = file.getName();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; 
	    }
	    return name.substring(lastIndexOf);
	}

	public static CodeLinesData Calculate(final File directory, final String[] extensions)
	{
		currentFiles.clear();
		GetFilesInFolder(directory, extensions);
		
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
		return new CodeLinesData(totalLineCounter, totalCodeLineCounter, totalCommentCounter);
	}
}
