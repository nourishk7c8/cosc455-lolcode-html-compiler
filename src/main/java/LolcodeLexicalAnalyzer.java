import java.util.Arrays;
import java.util.List;

public class LolcodeLexicalAnalyzer implements LexicalAnalyzer {
	protected List<String> singleTag = Arrays.asList("#hai","#kthxbye","#obtw","#tldr","#oic","#mkay","#itz","#visible");
	protected List<String> doubleTag = Arrays.asList("#maek head","#gimmeh title","#maek paragraf","#gimmeh bold","#gimmeh italics","#maek list","#gimmeh item","#gimmeh newline","#gimmeh soundz","#gimmeh vidz");
	protected List<String> tripleTag = Arrays.asList("#i has a");
	private List<String> validChar = Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9",",", "","\"",":","?","!","%","/","\n","\t");
	private String sourceFile;
	private String nextToken;
	private String nextCharacter;
	private int currentPosition;
	
	/**
	 * The LolcodeLexicalAnalyzer constructor sets String fields to an empty String
	 * and currentPosition to 0
	 */
	public LolcodeLexicalAnalyzer(){
		nextToken = "";
		nextCharacter = "";
		currentPosition = 0;
	}//end constructor LolcodeLexicalAnalyzer
	
	/**
	 * This method takes a file and gets the first lexeme/token. 
	 * @param file
	 * @throws CompilerException
	 */
	public void start(String file) throws CompilerException{
		sourceFile = file;
		
		getCharacter();		
		getNextToken();		
	}//end start

	/**
	 * This is the public method to be called when the Syntax Analyzer needs a new
	 * token to be parsed.
	 */
	public void getNextToken() throws CompilerException{
		getNonBlank();
		//To prevent an error if after #kthxbye there are spaces but no text/tags,
		//in which case when getNonBlank gets to end of file nextCharacter will be a space
		//So only start processing if nextCharacter is not a space
		if(!Character.isWhitespace(nextCharacter.charAt(0))){
			//if it's a command token
			if("#".equals(nextCharacter)){
				processTag();
				addTag();
			}//end if
			//if it's a plain text token
			else if(validChar.contains( (nextCharacter).toLowerCase()) )
				addText();
			else
				throw new CompilerException("LEXICAL ERROR! '" + nextCharacter + "' is not a valid character!");
			resetToken();
		}//end if
		else
			Compiler.currentToken = "";
	}//end getNextToken
	
	/**
	 * Obtains the next character from the sourceFile string, if there is one,
	 *  and places it in the nextCharacter  class variable
	 */
	public void getCharacter() {
		if (currentPosition < sourceFile.length()){
			nextCharacter = sourceFile.substring(currentPosition++,currentPosition);
		}
		else nextCharacter = "\n";
		
	}//end getCharacter

	/**
     * This method adds the current character to the nextToken.
     */
	public void addCharacter() {
		nextToken+=nextCharacter;		
	}//end addCharacter
	
	/**
	 * This method resets nextToken to an empty string
	 */
	private void resetToken(){
		nextToken = "";
	}//end resetToken
	
	/**
	 * This is method checks the next character to see if it is a space
	 *
	 * @param c the current character
	 * @return true, if is space; otherwise false
	 */
	public boolean isSpace(String c) {
		if (Character.isWhitespace(c.charAt(0))) return true;
		return false;
	}//end isSpace
	
	/**
	 * This method processes the next character in the file until a non-space character is 
	 * found, or the end of file is reached
	 */
	private void getNonBlank(){
		while(isSpace(nextCharacter) && currentPosition < sourceFile.length())
			getCharacter();
	}//end getNonBlank
	
	/**
	 * This method checks to see if the current, possible token is legal in the
	 * defined grammar.
	 *
	 * @return true, if it is a legal token, otherwise false
	 */
	public boolean lookupToken() {
		if(singleTag.contains(nextToken.toLowerCase())|| doubleTag.contains(nextToken.toLowerCase()) || tripleTag.contains(nextToken.toLowerCase()))
			return true;
		return false;
	}//end lookupToken
	
	/**
	 * This is method processes a command token 
	 */
	//process tag (nextToken) until a space is found. If
	//nextToken matches the first part of a two or three part tag, the second part of 
	//the tag (all text before space) is added to nextToken. If the first two parts
	//match the beginning of the three part tag the second part of the tag 
	//(all text before space) is added to nextToken.
	private void processTag(){
		processTagPart();		
		if("#gimmeh".equalsIgnoreCase(nextToken.toLowerCase()) || "#maek".equalsIgnoreCase(nextToken.toLowerCase()) || "#i".equalsIgnoreCase(nextToken.toLowerCase())){
			processTagPart();			
			if("#i has".equalsIgnoreCase(nextToken.toLowerCase())){
				processTagPart();
			}//end else if
		}//end if
	}//end processTag
	
	/**
	 * This is method adds the nextCharacter to nextToken while the 
	 * nextCharacter is not a space while processing a command token
	 *
	 */
	private void processTagText(){
		while(!isSpace(nextCharacter)){
			addCharacter();
			getCharacter();
		}//end while
	}//end processTagText
	
	/**
	 * This method processes individual words in a command token
	 */
	private void processTagPart(){
		//add "#" if first part of tag
		//add the space between words in legal tag otherwise
		addCharacter();
		getCharacter();
		processTagText();
	}//end processTag2
	
	/**
	 * If nextToken is a valid command within the lolcode language, it is added to the compiler's currentoken
	 * @throws CompilerException
	 */
	private void addTag() throws CompilerException{
		if(!lookupToken())
			throw new CompilerException("LEXICAL ERROR! " + nextToken + " is not a valid command!");
		Compiler.currentToken = nextToken;
	}//end addTag
	
	
	/**
	 * This method goes through each nextCharacter in the input and adds it to nextToken
	 * until a "#" is found
	 * @throws CompilerException 
	 * 
	 */
	public void processText() throws CompilerException{
		do{
			if(!( validChar.contains( (nextCharacter).toLowerCase()) || isSpace(nextCharacter)) ){
				throw new CompilerException("LEXICAL ERROR! '" + nextCharacter + "' is not a valid character!");
			}//end if
			addCharacter();
			getCharacter();
		}while(! ("#".equals(nextCharacter) ) && currentPosition < sourceFile.length());//end while
		if(! ("#".equals(nextCharacter) ) )addCharacter();
	}//end processText
	
	/**
	 * This method processes the current text token and sets it to the compiler's current token
	 * @throws CompilerException
	 */
	private void addText() throws CompilerException{
		processText();
		Compiler.currentToken = nextToken;
	}//end addText
	
	/**
	 * This method checks if the end of the source file has been reached
	 * @return true if the end of file has been reached, otherwise false
	 */
	public boolean eof(){
		return currentPosition >= sourceFile.length();
	}//end eof

}//end class LolcodeLexicalAnalyzer
