package com.rijkvanputten.codelinescalculator;

public class CodeLinesData {
	public int Lines;
	public int CodeLines;
	public int CommentLines;
	
	public CodeLinesData(int lines, int codeLines, int commentLines) {
		Lines = lines;
		CodeLines = codeLines;
		CommentLines = commentLines;
	}
	public void Print()
	{
		System.out.println("LINES: " + Lines + " CODE LINES: " + CodeLines + " COMMENTS: " + CommentLines);
	}
}
