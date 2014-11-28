class Node {
	String token;
	
	public Node(){
		token = "";
	}//end default Node constructor
	
	public Node(String t){
		token = t;
	}//end values Node constructor
	
	public String getToken(){
		return token;
	}//end getToken
	
	public String toString(){
		return token;
	}

}//end class Node

class MkayNode extends Node {
	final static String MKAY = "#MKAY";
	
	public MkayNode(){
		super(MKAY);
	}//end default MkayNode constructor
}//end class MkayNode

class OicNode extends Node {
	final static String OIC = "#OIC";
	
	public OicNode(){
		super(OIC);
	}//end default BgnSrcNode constructor
}//end class EndHeadNode

class SrcNode extends Node {
	final static String HAI = "#HAI";
	Node end = new EndSrcNode();
	
	public SrcNode(){
		super(HAI);
	}//end default SrcNode constructor
	
	public Node getEnd(){
		return end;
	}
}//end class SrcNode

class EndSrcNode extends Node {
	final static String BYE = "#KTHXBYE";
	
	public EndSrcNode(){
		super(BYE);
	}//end default EndSrcNode constructor
}//end class EndSrcNode

class CmtNode extends Node {
	final static String OBTW = "#OBTW";
	Node end = new EndCmtNode();
	
	public CmtNode(){
		super(OBTW);
	}//end default CmtNode constructor
	
	public Node getEnd(){
		return end;
	}
}//end class CmtNode

class EndCmtNode extends Node {
	final static String TLDR = "#TLDR";
	
	public EndCmtNode(){
		super(TLDR);
	}//end default EndCmtNode constructor
}//end class EndCmtNode

class HeadNode extends Node {
	final static String HEAD = "#MAEK HEAD";
	Node end = new EndHeadNode();
	
	public HeadNode(){
		super(HEAD);
	}//end default HeadNode constructor
	
	public Node getEnd(){
		return end;
	}
}//end class HeadNode

class EndHeadNode extends OicNode {
	public EndHeadNode(){
		super();
	}//end EndHeadNode
}//end class EndHeadNode

class TitleNode extends Node {
	final static String TITLE = "#GIMMEH TITLE";
	Node end = new EndTitleNode();
	
	public TitleNode(){
		super(TITLE);
	}//end default TitleNode constructor
	
	public Node getEnd(){
		return end;
	}
}//end class TitleNode

class EndTitleNode extends MkayNode {
	public EndTitleNode(){
		super();
	}//end default EndTitleNode constructor
}//end class EndTitleNode

class ParaNode extends Node {
	final static String PARA = "#MAEK PARAGRAF";
	Node end = new EndParaNode();
	
	public ParaNode(){
		super(PARA);
	}//end default ParaNode constructor
	
	public Node getEnd(){
		return end;
	}
}//end class ParaNode

class EndParaNode extends OicNode {
	public EndParaNode(){
		super();
	}//end default EndParaNode constructor
}//end class EndParaNode

class BoldNode extends Node {
	final static String BOLD = "#GIMMEH BOLD";
	Node end = new EndBoldNode();
	
	public BoldNode(){
		super(BOLD);
	}//end default BoldNode constructor
	
	public Node getEnd(){
		return end;
	}
}//end class BoldNode

class EndBoldNode extends MkayNode {
	public EndBoldNode(){
		super();
	}//end default EndBoldNode constructor
}//end class EndBoldNode

class ItalicsNode extends Node {
	final static String ITALICS = "#GIMMEH ITALICS";
	Node end = new EndItalicsNode();
	
	public ItalicsNode(){
		super(ITALICS);
	}//end default ItalicsNode constructor
	
	public Node getEnd(){
		return end;
	}
}//end class ItalicsNode

class EndItalicsNode extends MkayNode {
	public EndItalicsNode(){
		super();
	}//end default ItalicsNode constructor
}//end class ItalicsNode

class ListNode extends Node {
	final static String LIST = "#MAEK LIST";
	Node end = new EndListNode();
	
	public ListNode(){
		super(LIST);
	}//end default ListNode constructor
	
	public Node getEnd(){
		return end;
	}
}//end class ListNode

class EndListNode extends OicNode {
	public EndListNode(){
		super();
	}//end default EndListNode constructor
}//end class EndListNode


class ItemNode extends Node {
	final static String ITEM = "#GIMMEH ITEM";
	Node end = new EndItemNode();
	
	public ItemNode(){
		super(ITEM);
	}//end default iTEMNode constructor
	
	public Node getEnd(){
		return end;
	}
}//end class ITEMNode


class EndItemNode extends MkayNode {
	public EndItemNode(){
		super();
	}//end default EndItemNode constructor
}//end class EndItemNode


class NewlineNode extends Node {
	final static String LINE = "#GIMMEH NEWLINE";
	
	public NewlineNode(){
		super(LINE);
	}//end default NEWLINENode constructor
}//end class NEWLINENode


class SoundNode extends Node {
	final static String SDZ = "#GIMMEH SOUNDZ";
	Node end = new EndSoundNode();
	
	public SoundNode(){
		super(SDZ);
	}//end default SOUNDZNode constructor
	
	public Node getEnd(){
		return end;
	}
}//end class SOUNDZNode

class EndSoundNode extends MkayNode {
	public EndSoundNode(){
		super();
	}//end default EndSOUNDZNode constructor
}//end class EndSOUNDZNode

class VidNode extends Node {
	final static String VIDZ = "#GIMMEH VIDZ";
	Node end = new EndVidNode();
	
	public VidNode(){
		super(VIDZ);
	}//end default VidNode constructor
	
	public Node getEnd(){
		return end;
	}
}//end class VidNode

class EndVidNode extends MkayNode {
	public EndVidNode(){
		super();
	}//end default EndVidNode constructor
}//end class EndVidNode

class DefineNode extends Node {
	final static String DEFINE = "#I HAS A";
	Node end = new EndDefineNode();
	
	public DefineNode(){
		super(DEFINE);
	}//end default DefineNode constructor
	
	public Node getEnd(){
		return end;
	}
}//end class DefineNode

class ItzNode extends Node {
	final static String ITZ = "#ITZ";
	
	public ItzNode(){
		super(ITZ);
	}//end default itzNode constructor
}//end class itzNode

class EndDefineNode extends MkayNode {
	public EndDefineNode(){
		super();
	}//end default EndDefineNode constructor
}//end class EndDefineNode


class DefineVariableNode extends Node {
	//token in Node is variable name
	//value is the value assigned to the variable
	DefineValueNode value;
	
	public DefineVariableNode(){
		super();
		value = new DefineValueNode();
	}//end default DefineNode constructor
	
	public DefineVariableNode(String token, DefineValueNode val){
		super(token);
		value = val;
	}//end values DefineVariableNode constructor
	
	//token is the name of the variable
	public String getVariableName(){
		return super.getToken();
	}//end getVariableName
	
	public void setValueNode(DefineValueNode val){
		value = val;
	}
	
	public DefineValueNode getValueNode(){
		return value;
	}
	
	public void setValue(String v){
		value.setValue(v);
	}
	
	public String getValue(){
		return value.getValue();
	}
	
}//end class DefineVariableNode

class DefineValueNode extends Node {
	
	public DefineValueNode(){
		super();
	}//end default DefineNode constructor
	
	public DefineValueNode(String value){
		super(value);
	}//end default DefineNode constructor
	
	public void setValue(String v){
		token = v;
	}//end setToken
	
	public String getValue(){
		return getToken();
	}//end getToken
}//end class DefineNode

class VisibleNode extends Node {
	final static String USE = "#VISIBLE";
	Node end = new EndVisibleNode();
	
	public VisibleNode(){
		super(USE);
	}//end default VisibleNode constructor
	
	public Node getEnd(){
		return end;
	}
}//end class VisibleNode

class UseVariableNode extends Node {
	public UseVariableNode(String v){
		super(v);
	}//end default UseVariableNode constructor
}//end class UseVariableNode

class EndVisibleNode extends MkayNode {
	public EndVisibleNode(){
		super();
	}//end default endVisibleNode constructor
}//end class EndVisibleNode
