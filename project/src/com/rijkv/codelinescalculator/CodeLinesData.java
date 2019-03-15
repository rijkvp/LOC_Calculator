package com.rijkv.codelinescalculator;

public class CodeLinesData {
	public int Lines;
	public int CodeLines;
	public int CommentLines;
	
	public CodeLinesData(int lines, int codeLines, int commentLines) {
		Lines = lines;
		CodeLines = codeLines;
		CommentLines = commentLines;
	}
	public void Debug()
	{
		System.out.println(GetAsString());
	}
	public String GetAsString()
	{
		return (CodeLines + " Lines of Code      " + Lines + " Lines in Total      " + CommentLines + " Lines of Comments");
	}
}
