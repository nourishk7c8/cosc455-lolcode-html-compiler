import java.util.LinkedList;

//package edu.towson.cis.cosc455.lab2.Compiler;

public class LolcodeSyntaxAnalyzer implements SyntaxAnalyzer{
	final static String MKAY = "#MKAY";
	final static String OIC = "#OIC";
	final static String HAI = "#HAI";
	final static String BYE = "#KTHXBYE";
	final static String OBTW = "#OBTW";
	final static String TLDR = "#TLDR";
	final static String HEAD = "#MAEK HEAD";
	final static String TITLE = "#GIMMEH TITLE";
	final static String PARA = "#MAEK PARAGRAF";
	final static String BOLD = "#GIMMEH BOLD";
	final static String ITALICS = "#GIMMEH ITALICS";
	final static String LIST = "#MAEK LIST";
	final static String ITEM = "#GIMMEH ITEM";
	final static String NEWLINE = "#GIMMEH NEWLINE";
	final static String SDZ = "#GIMMEH SOUNDZ";
	final static String VIDZ = "#GIMMEH VIDZ";
	final static String DEFINE = "#I HAS A";
	final static String ITZ = "#ITZ";
	final static String USE = "#VISIBLE";
	
	protected LinkedList<Node> tree;
	
	/**
	 * The LolcodeSyntaxAnalyzer constructor creates a new empty list for 
	 * the parse tree
	 */
	public LolcodeSyntaxAnalyzer(){
		tree = new LinkedList<Node>();
	}//end constructor LolcodeSyntaxAnalyzer
	
	/**
	 * This method implements the BNF grammar rule for the document annotation.
	 * @throws CompilerException
	 */
	//<lolcode> ::= #HAI <comment> <variable-define> <head> <body> #KTHXBYE
	public void lolcode() throws CompilerException {
		bgnSrc();
		comment();
		define();
		head();
		body();		
		endSrc();
		eof();	
	}//end lolcode
	
	/**
	 * This method adds the current token to the parse tree if it matches the expected token of "#mkay"
	 * @param n, expected token Node
	 * @throws CompilerException
	 */
	private void mkayCmdEnd(Node n) throws CompilerException{
		if(!ismkay())
			throw new CompilerException("SYNTAX ERROR - The terminal '"+MKAY+"' was expected when '" + Compiler.currentToken + "' was found.");
		validToken(n);
	}//end mkayCmdEnd
	
	/**
	 * This method checks if the current token matches the tag "#mkay"
	 * @return true if match, otherwise false
	 */
	private boolean ismkay(){
		return MKAY.equalsIgnoreCase(Compiler.currentToken);
	}
	 /**
	  * This method adds the current token to the parse tree if it matches the expected token of "#oic"
	  * @param n, expected token Node
	  * @throws CompilerException
	  */
	private void oicCmdEnd(Node n) throws CompilerException{
		if(!isOic())
			throw new CompilerException("SYNTAX ERROR - The terminal '" + OIC +"' was expected when '" + Compiler.currentToken + "' was found.");
		validToken(n);
	}//end endHead

	/**
	 * This method checks if the current token matches the tag "#oic"
	 * @return true if match, otherwise false
	 */
	private boolean isOic() {
		return OIC.equalsIgnoreCase(Compiler.currentToken);
	}
	
	/**
	 * This method adds the current token to the parse tree if it matches the expected token of 
	 * the begin file command
	 * @throws CompilerException 
	 */
	//Adds the token to the parse list if it is the legal command. 
	//Otherwise, it outputs a syntax error - compiler exception
	private void bgnSrc() throws CompilerException{
		if(!(HAI).equalsIgnoreCase(Compiler.currentToken))
			throw new CompilerException("SYNTAX ERROR - The terminal '" + HAI + "' was expected when '" + Compiler.currentToken + "' was found.");
		SrcNode src = new SrcNode();
		validToken(src);
	}//end bgnSrc
	
	/**
	 * This method adds the current token to the parse tree if it matches the expected 
	 * token of the end file command
	 * @throws CompilerException 
	 */
	//Adds the token to the parse list if it is the legal command. 
	//Otherwise, it outputs a syntax error
	private void endSrc() throws CompilerException{
		if(!(BYE).equalsIgnoreCase(Compiler.currentToken))
			throw new CompilerException("SYNTAX ERROR - The terminal '" + BYE + "' was expected when '" + Compiler.currentToken + "' was found.");
		EndSrcNode bye = new EndSrcNode();
		tree.add(bye);
	}//end endSrc
	
	/**
	 * This method checks if the current token matches the end file command "#kthxbye"
	 * @return true if match, otherwise false
	 */
	private boolean isEndSrc() {
		return BYE.equalsIgnoreCase(Compiler.currentToken);
	}//end isEndSrc
	
	/**
	 * This method checks if there are still tokens left to be read after the end of file command
	 * @throws CompilerException
	 */
	private void eof() throws CompilerException{
		if(!Compiler.Lexer.eof()){
			Compiler.Lexer.getNextToken();
			//curretnToken will be "" only if the last 'token' read when the end of file was reached was a space
			if(!"".equals(Compiler.currentToken)) 
				throw new CompilerException("SYNTAX ERROR! '"+BYE+ "' marks the end of the source file. There may be nothing after it, but '" + Compiler.currentToken +"' was found!");
		}//end if
	}//end eof
	
	/**
	 * This method adds the current token to the parse list and gets the next token
	 * from the compiler's lexical analyzer. 
	 * @throws CompilerException 
	 */
	private void validToken(Node n) throws CompilerException{
		tree.add(n);
		Compiler.Lexer.getNextToken();
	}//end validToken
	
	/**
	 * This method implements the BNF grammar rule for the head annotation.
	 * @throws CompilerException
	 */
	//<comment> ::= #OBTW <text> #TLDR | empty
	public void comment() throws CompilerException {
		if(isCmt()){
			bgnCmt();
			if( !(isText() || isEndCmt()) )
				throw new CompilerException("SYNTAX ERROR - The text terminal was expected when '" + Compiler.currentToken + "' was found.");
			text();
			endCmt();
		}//end if		
	}//end comment
	
	/**
	 * This method checks if the current token matches the tag "#obtw"
	 * @return true if match, otherwise false
	 */
	private boolean isCmt(){
		return OBTW.equalsIgnoreCase(Compiler.currentToken);
	}//end isCmt
	
	/**
	 * This method adds a CmtNode to the parse tree
	 * @throws CompilerException
	 */
	private void bgnCmt() throws CompilerException{
		CmtNode cmt = new CmtNode();
		validToken(cmt);
	}//end bgnCmt
	
	/**
	 * This method adds the current token to the parse tree if it matches the expected token of 
	 * the end comment command 
	 * @throws CompilerException
	 */
	private void endCmt() throws CompilerException{
		if(!isEndCmt())
			throw new CompilerException("SYNTAX ERROR - The terminal '" + TLDR+ "' was expected when '" + Compiler.currentToken + "' was found.");
		EndCmtNode eCmt = new EndCmtNode();
		validToken(eCmt);
	}//end endCmt
	
	/**
	 * This method checks if the current token matches the end comment command
	 * @return true if match, otherwise false
	 */
	private boolean isEndCmt(){
		return TLDR.equalsIgnoreCase(Compiler.currentToken);
	}//end isEndCmt

	/**
	 * This method implements the BNF grammar rule for the head annotation.
	 * @throws CompilerException
	 */
	public void head() throws CompilerException {
		if(isHead()){
			bgnHead();
			title();
			endHead();
		}//end if		
	}//end head
	
	/**
	 * This method adds a HeadNode to the parse tree
	 * @throws CompilerException
	 */
	private void bgnHead() throws CompilerException{
		HeadNode h = new HeadNode();
		validToken(h);
	}//end bgnHead
	
	/**
	 * This method checks if the current token matches the begin head command
	 * @return true if match, otherwise false
	 */
	private boolean isHead(){
		return HEAD.equalsIgnoreCase(Compiler.currentToken);
	}//end isHead
	
	/**
	 * This method adds the current token to the parse tree if it matches the expected token of 
	 * the end head command
	 * @throws CompilerException
	 */
	private void endHead() throws CompilerException{
		EndHeadNode h = new EndHeadNode();
		oicCmdEnd(h);
	}//end endHead
	
	/**
	 * This method implements the BNF grammar rule for the title annotation.
	 * @throws CompilerException

	 */
	public void title() throws CompilerException {
		bgnTitle();
		if(! (isText() || ismkay()) )
			throw new CompilerException("SYNTAX ERROR - The text terminal was expected when '" + Compiler.currentToken + "' was found.");
		text();
		endTitle();
	}//end title
	
	/**
	 * This method adds the current token to the parse tree if it matches the expected token of 
	 * the begin title command
	 * @throws CompilerException 
	 */
	// Adds the token to the parse list if it is the legal commend
	// Otherwise, it outputs a syntax error
	private void bgnTitle() throws CompilerException{
		if(!(TITLE).equalsIgnoreCase(Compiler.currentToken))
			throw new CompilerException("SYNTAX ERROR - The terminal '" + TITLE+ "' was expected when '" + Compiler.currentToken + "' was found.");
		TitleNode t = new TitleNode();
		validToken(t);
	}//end bgnTitle
	
	/**
	 * This method adds the current token to the parse tree if it matches the expected token of 
	 * the end title command
	 * @throws CompilerException
	 */
	private void endTitle() throws CompilerException{
		EndTitleNode t = new EndTitleNode();
		mkayCmdEnd(t);
	}//end bgnTitle
	
	/**
	 * This method implements the BNF grammar rule for the body of the file
	 * @throws CompilerException
	 */
	private void body() throws CompilerException{
		while(!isEndSrc() && !Compiler.Lexer.eof() ){
			if(isParagraph())
				paragraph();
			else
				innerText();
		}//end while
	}//end body

	/**
	 * This method adds the current token to the parse list and gets the next token
	 * from the compiler's lexical analyzer 
	 * @throws CompilerException 
	 */
	private void text() throws CompilerException{
		if(isText()){
			Node txt = new Node(Compiler.currentToken);
			validToken(txt);
		}//end if
	}//end if
	
	/**
	 * This method determine if the current token being examined is a text as opposed
	 * to a command tag
	 * 
	 * @return false if the current token begins with "#", true otherwise
	 */
	private boolean isText(){
		if( Compiler.currentToken.length() > 0)
			return !"#".equals(Compiler.currentToken.charAt(0));
		return false;
	}//end isText

	/**
	 * This method implements the BNF grammar rule for the paragraph annotation.
	 * @throws CompilerException
	 */
	public void paragraph() throws CompilerException {
		bgnParagraph();
		comment();
		define();
		while(!(isEndPara() || Compiler.Lexer.eof()) ){
			innerText();
		}//end while
		endParagraph();
	}//end paragraph
	
	/**
	 * This method checks if the current token matches the begin paragraph command
	 * @return true if match, otherwise false
	 */
	private boolean isParagraph(){
		return PARA.equalsIgnoreCase(Compiler.currentToken);
	}//end isParagraph
	
	/**
	 * This method adds a ParaNode to the parse tree
	 * @throws CompilerException
	 */
	private void bgnParagraph() throws CompilerException{
		ParaNode p = new ParaNode();
		validToken(p);
	}//end bgnParagraph
	
	/**
	 * This method checks if the current token matches the end paragraph command
	 * @return true if match, otherwise false
	 */
	private boolean isEndPara(){
		return isOic();
	}//end isEndPara

	/**
	 * This method adds the current token to the parse tree if it matches the expected token of 
	 * the end paragraph command
	 * @throws CompilerException
	 */
	private void endParagraph() throws CompilerException{
		EndParaNode e = new EndParaNode();
		oicCmdEnd(e);
	}//end endParagraph

	/**
	 * This method implements the BNF grammar rule for the bold annotation.
	 * @throws CompilerException
	 */
	//"Within these annotations, only plain text is possible(i.e. no other annotations)
	//<bold> ::= #GIMMEH BOLD <text> #MKAY
	public void bold() throws CompilerException {
		bgnBold();
		if(! (isText() || isEndBold()))
			throw new CompilerException("SYNTAX ERROR - The text terminal was expected when '" + Compiler.currentToken + "' was found.");
		text();
		endBold();		
	}//end bold
	
	/**
	 * This method adds a BoldNode to the parse tree
	 * @throws CompilerException
	 */
	private void bgnBold() throws CompilerException{
		BoldNode b = new BoldNode();
		validToken(b);
	}//end bgnBold
	
	/**
	 * This method adds the current token to the parse tree if it matches the expected token of 
	 * the end bold command
	 * @throws CompilerException
	 */
	private void endBold() throws CompilerException{
		EndBoldNode b = new EndBoldNode();
		mkayCmdEnd(b);
		
	}//end endBold
	
	/**
	 * This method checks if the current token matches the begin bold command
	 * @return true if match, otherwise false
	 */
	private boolean isBold(){
		return BOLD.equalsIgnoreCase(Compiler.currentToken);
	}//end isBold
	
	/**
	 * This method checks if the current token matches the end bold command
	 * @return true if match, otherwise false
	 */
	private boolean isEndBold(){
		return ismkay();
	}//end isEndBold

	//"Within these annotations, only plain text is possible(i.e. no other annotations)
	//<italics> ::= #GIMMEH ITALICS <text> #MKAY
	public void italics() throws CompilerException{
		bgnItalics();
		if(! (isText() || isEndItalics()))
			throw new CompilerException("SYNTAX ERROR - The text terminal was expected when '" + Compiler.currentToken + "' was found.");
		text();
		endItalics();		
	}//end italics
	
	/**
	 * This method adds a ItalicsNode to the parse tree
	 * @throws CompilerException
	 */
	private void bgnItalics() throws CompilerException{
		ItalicsNode i = new ItalicsNode();
		validToken(i);
	}//end bgnItalics
	
	/**
	 * This method adds the current token to the parse tree if it matches the expected token of 
	 * the end italics command
	 * @throws CompilerException
	 */
	private void endItalics() throws CompilerException{
		EndItalicsNode i = new EndItalicsNode();
		mkayCmdEnd(i);		
	}//end Italics
	
	/**
	 * This method checks if the current token matches the begin italics command
	 * @return true if match, otherwise false
	 */
	private boolean isItalics(){
		return ITALICS.equalsIgnoreCase(Compiler.currentToken);
	}//end isItalics
	
	/**
	 * This method checks if the current token matches the end italics command
	 * @return true if match, otherwise false
	 */
	private boolean isEndItalics(){
		return ismkay();
	}//end isEndItalics

	/**
	 * This method implements the BNF grammar rule for the list annotation.
	 * @throws CompilerException
	 */
	public void list() throws CompilerException {
		bgnList();
		comment();
		define();
		if(!isItem())
			throw new CompilerException("SYNTAX ERROR! The command '" + ITEM + "' was expected when '" + Compiler.currentToken + "' was found!.");
		item();
		while(isItem() && !isEndList()){
			item();
		}//end while
		endList();
	}//end list

	/**
	 * This method adds a ListNode to the parse tree
	 * @throws CompilerException
	 */
	private void bgnList() throws CompilerException{
		ListNode l = new ListNode();
		validToken(l);
	}//end bgnList
	
	/**
	 * This method checks if the current token matches the begin list command
	 * @return true if match, otherwise false
	 */
	private boolean isList(){
		return LIST.equalsIgnoreCase(Compiler.currentToken);
	}//end isList
	
	/**
	 * This method checks if the current token matches the end list command
	 * @return true if match, otherwise false
	 */
	private boolean isEndList(){
		return isOic();
	}//end isEndList
	
	/**
	 * This method adds the current token to the parse tree if it matches the expected token of 
	 * the end list command
	 * @throws CompilerException
	 */
	private void endList() throws CompilerException{
		EndListNode l= new EndListNode();
		oicCmdEnd(l);
	}//end endList
	
	/**
	 * This method implements the BNF grammar rule for the item annotation.
	 * @throws CompilerException
	 */
	public void item() throws CompilerException {
		bgnItem();
		while(!isEndItem()){
			innerText();
		}//end while
		endItem();		
	}//end item
	
	/**
	 * This method adds an ItemNode to the parse tree
	 * @throws CompilerException
	 */
	private void bgnItem() throws CompilerException{
		ItemNode i = new ItemNode();
		validToken(i);
	}//end bgnItem
	
	/**
	 * This method checks if the current token matches the begin item command
	 * @return true if match, otherwise false
	 */
	private boolean isItem(){
		return ITEM.equalsIgnoreCase(Compiler.currentToken);
	}//end isItem
	
	/**
	 * This method checks if the current token matches the end item command
	 * @return true if match, otherwise false
	 */
	private boolean isEndItem(){
		return ismkay();
	}//end endItem
	
	/**
	 * This method adds the current token to the parse tree if it matches the expected token of 
	 * the end item command
	 * @throws CompilerException
	 */
	private void endItem() throws CompilerException{
		EndItemNode i = new EndItemNode();
		mkayCmdEnd(i);
	}//end endItem
	
	/**
	 * This method matches possible parse production rules for body()/paragraph()
	 * depending on the current token
	 * @throws CompilerException
	 */
	private void innerText() throws CompilerException{
		if(isBold()){ 
			bold(); }
		else if(isItalics()){
			italics(); }
		else if(isAudio()){
			audio(); }
		else if(isVideo()){
			video(); }
		else if(isNewline()){
			newline(); }
		else if(isUse()){
			use(); }
		else if(isList()){
			list(); }
		else if(isText()){
			text(); }
	}//end innerText 

	/**
	 * This method implements the BNF grammar rule for the audio annotation.
	 * @throws CompilerException
	 */
	public void audio() throws CompilerException {
		bgnAudio();
		if(! (isText() || ismkay()) )
			throw new CompilerException("SYNTAX ERROR! A text reminal was expected when '" + Compiler.currentToken + "' was found.");
		text();
		endAudio();
	}//end audio
	
	/**
	 * This method checks if the current token matches the begin audio command
	 * @return true if match, otherwise false
	 */
	private boolean isAudio(){
		return SDZ.equalsIgnoreCase(Compiler.currentToken);
	}//end isAudio
	
	/**
	 * This method adds a SoundNode to the parse tree
	 * @throws CompilerException
	 */
	private void bgnAudio() throws CompilerException{
		SoundNode s  = new SoundNode();
		validToken(s);
	}//end bgnAudio
	
	/**
	 * This method adds the current token to the parse tree if it matches the expected token of 
	 * the end audio command
	 * @throws CompilerException
	 */
	private void endAudio() throws CompilerException{
		EndSoundNode s = new EndSoundNode();
		mkayCmdEnd(s);
	}//end endAudio

	/**
	 * This method implements the BNF grammar rule for the video annotation.
	 * @throws CompilerException
	 */
	public void video() throws CompilerException {
		bgnVideo();
		if(! (isText() || ismkay()) ) 
			throw new CompilerException("SYNTAX ERROR! A text reminal was expected when '" + Compiler.currentToken + "' was found.");
		text();
		endVideo();
	}//end video
	
	/**
	 * This method checks if the current token matches the begin video command
	 * @return true if match, otherwise false
	 */
	private boolean isVideo(){
		return VIDZ.equalsIgnoreCase(Compiler.currentToken);
	}//end isAudio
	
	/**
	 * This method adds a VidNode to the parse tree
	 * @throws CompilerException
	 */
	private void bgnVideo() throws CompilerException{
		VidNode v = new VidNode();
		validToken(v);
	}//end bgnVideo
	
	/**
	 * This method adds the current token to the parse tree if it matches the expected token of 
	 * the end video command
	 * @throws CompilerException
	 */
	private void endVideo() throws CompilerException{
		EndVidNode v = new EndVidNode();
		mkayCmdEnd(v);
	}//end endVideo
	
	/**
	 * This method adds a NewlineNode to the parse tree given that there is a newline command
	 * @throws CompilerException
	 */
	public void newline() throws CompilerException{
		if(isNewline()){
			NewlineNode n = new NewlineNode();
			validToken(n);
			newline();
		}//end if		
	}//end newline
	
	/**
	 * This method checks if the current token matches the newline command
	 * @return true if match, otherwise false
	 */
	private boolean isNewline(){
		return NEWLINE.equalsIgnoreCase(Compiler.currentToken);
	}

	/**
	 * This method implements the BNF grammar rule for the define annotation.
	 * @throws CompilerException
	 */
	public void define() throws CompilerException {
		if(isDefine()){
			DefineVariableNode var = new DefineVariableNode();
			DefineValueNode data = new DefineValueNode();
			bgnDefine();
			comment();
			//process define variable name
			setVariable(var,data);
			itz();
			//process define variable value
			setValue(var);
			endDefine();
			define();
		}//end if		
	}//end define
	
	/**
	 * This method adds a DefineNode to the parse tree
	 * @throws CompilerException
	 */
	private void bgnDefine() throws CompilerException{
		DefineNode d = new DefineNode();
		validToken(d);
	}//end bgnHead
	
	/**
	 * This method checks if the current token matches the begin define command
	 * @return true if match, otherwise false
	 */
	private boolean isDefine(){
		return DEFINE.equalsIgnoreCase(Compiler.currentToken);
	}//end haz
	
	/**
	 * This method adds a DefineVariableNode to the parse tree given that the it matches a token a single word text
	 * @throws CompilerException
	 */
	private void setVariable(DefineVariableNode var, DefineValueNode d) throws CompilerException{
		if( (isText() && !Compiler.currentToken.contains(" ")) || !isText())
			throw new CompilerException("SYNTAX ERROR! A variable terminal must be a single text word that contains no spaces, but '" + Compiler.currentToken + "' was found!");
		var = new DefineVariableNode(Compiler.currentToken,d);
		validToken(var);
	}//end setVariable
	
	/**
	 * This method adds the current token to the parse tree if it matches the expected token of 
	 * the itz command
	 * @throws CompilerException
	 */
	private void itz() throws CompilerException{
		if(!(ITZ).equalsIgnoreCase(Compiler.currentToken))
			throw new CompilerException("SYNTAX ERROR! - The terminal '"+ITZ+"' was expected when '" + Compiler.currentToken + "' was found.");
		ItzNode i = new ItzNode();
		validToken(i);
	}//end defineTag2
	
	/**
	 * This method adds a DefineVariableNode to the parse tree given that the token is a single word text
	 * @throws CompilerException
	 */
	private void setValue(DefineVariableNode var) throws CompilerException{
		if(!isText())
			throw new CompilerException("SYNTAX ERROR! A value terminal must contain only text, but '" + Compiler.currentToken + "' was found!");
		var.setValue(Compiler.currentToken);
		validToken(var.getValueNode());
	}//end setValue
	
	/**
	 * This method adds the current token to the parse tree if it matches the expected token of 
	 * the end define command
	 * @throws CompilerException
	 */
	private void endDefine() throws CompilerException{
		EndDefineNode e = new EndDefineNode();
		mkayCmdEnd(e);
	}//end endDefine

	/**
	 * This method implements the BNF grammar rule for the use annotation.
	 * @throws CompilerException
	 */
	public void use() throws CompilerException {
		bgnUse();
		useVariable();
		endUse();		
	}//end use
	
	/**
	 * This method checks if the current token matches the begin use command
	 * @return true if match, otherwise false
	 */
	private boolean isUse(){
		return USE.equalsIgnoreCase(Compiler.currentToken);
	}//end isUse

	/**
	 * This method adds a VisibleNode to the parse tree
	 * @throws CompilerException
	 */
	private void bgnUse() throws CompilerException{
		VisibleNode use = new VisibleNode();
		validToken(use);
	}//end bgnUse
	
	/**
	 * This method adds the current token to the parse tree if it matches the expected token of 
	 * a single text word
	 * @throws CompilerException
	 */
	private void useVariable() throws CompilerException{
		if( (isText() && !Compiler.currentToken.contains(" ")) || !isText())
			throw new CompilerException("SYNTAX ERROR! A variable terminal must be a single text word that contains no spaces, but '" + Compiler.currentToken + "' was found!");
		UseVariableNode var = new UseVariableNode(Compiler.currentToken);
		validToken(var);
	}//end useVariable
	
	/**
	 * TThis method adds the current token to the parse tree if it matches the expected token of 
	 * the end use command
	 * @throws CompilerException
	 */
	private void endUse() throws CompilerException{
		EndVisibleNode e = new EndVisibleNode();
		mkayCmdEnd(e);
	}//end endUse
}//end class LolcodeSyntaxAnalyzer
