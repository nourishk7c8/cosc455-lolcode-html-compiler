/**
 * 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Nourish
 *
 */
public class Compiler {
	//Global Variables
	protected static String currentToken;
	protected static LolcodeLexicalAnalyzer Lexer;
	protected static LolcodeSyntaxAnalyzer Parser;
	protected static LolcodeSemanticAnalyzer Semantics = new LolcodeSemanticAnalyzer();
	
	public static void main(String[] args) throws IOException, CompilerException {
		Lexer = new LolcodeLexicalAnalyzer();
		Parser = new LolcodeSyntaxAnalyzer();
		String fileName = null;
		FileReader fr;
		BufferedReader br;
		
		//checks if there is an input argument
		if(!(0 < args.length))
			throw new CompilerException("No file was input for compilation!");
		fileName = args[0];
		String extension = fileName.substring(fileName.lastIndexOf('.')+1 ,fileName.length());
		String line;
		StringBuilder sb = new StringBuilder();
		
		//checks if the file extension is valid
		if(!"lol".equalsIgnoreCase(extension))
			throw new CompilerException("The input file is not a .lol file.");
		File file = new File(fileName);
		fr = new FileReader(fileName);
		br = new BufferedReader(fr);
		
		while((line = br.readLine()) != null){
			sb.append(line);
		}//end while
		String source = new String(sb);
		Lexer.start(source);
		Parser.lolcode();
		Semantics.translate(file);
		
		fr.close();
		br.close();
		
		}//end main

}//end class Compiler

