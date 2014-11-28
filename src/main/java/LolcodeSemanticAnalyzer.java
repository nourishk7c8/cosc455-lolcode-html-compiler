import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Compiler.*;
import java.util.Iterator;
import java.util.LinkedList;

public class LolcodeSemanticAnalyzer {
	//Iterator<Node> iter;
	StringBuilder htmlOutput;
	String htmlName;
	LinkedList<Node> variables = new LinkedList<Node>();
	
	public LolcodeSemanticAnalyzer(){
		//iter = iterator();
		htmlOutput = new StringBuilder();
	}
	public void translate(File f) throws IOException{
		htmlName = f.getName().substring(0, f.getName().length()-3)+"html";
		f = f.getParentFile();
		//Node lastNode = null;
		while(!Compiler.Parser.tree.isEmpty()){//iter.hasNext()){
			Node n = Compiler.Parser.tree.element();//iter.next();
			if(n instanceof SrcNode)	htmlOutput.append("<html>\r\n");
			else if(n instanceof EndSrcNode)	htmlOutput.append("</html>\r\n");
			else if(n instanceof CmtNode)	htmlOutput.append("<!--");
			else if(n instanceof EndCmtNode)	htmlOutput.append("-->\r\n");
			else if(n instanceof HeadNode)	htmlOutput.append("<head>\r\n");
			else if(n instanceof EndHeadNode)	htmlOutput.append("</head>\r\n");
			else if(n instanceof TitleNode)	htmlOutput.append("<title>\n");
			else if(n instanceof EndTitleNode)	htmlOutput.append("</title>\r\n");
			else if(n instanceof ParaNode)	htmlOutput.append("<p>\r\n");
			else if(n instanceof EndParaNode)	htmlOutput.append("</p>\r\n");
			else if(n instanceof BoldNode)	htmlOutput.append("<b>\r\n");
			else if(n instanceof EndBoldNode)	htmlOutput.append("</b>\r\n");
			else if(n instanceof ItalicsNode)	htmlOutput.append("<i>\r\n");
			else if(n instanceof EndItalicsNode)	htmlOutput.append("</i>\r\n");
			else if(n instanceof ListNode)	htmlOutput.append("<ul>\r\n");
			else if(n instanceof EndListNode)	htmlOutput.append("</ul>\r\n");
			else if(n instanceof ItemNode)	htmlOutput.append("<li>\r\n");
			else if(n instanceof EndItemNode)	htmlOutput.append("</li>\r\n");
			else if(n instanceof NewlineNode)	htmlOutput.append("<br>\r\n");
			else if(n instanceof SoundNode)	htmlOutput.append("<audio controls> \r\n\t <sound src =\"");
			else if(n instanceof EndSoundNode)	htmlOutput.append("\"> \r\n </audio>");
			else if(n instanceof VidNode)	htmlOutput.append("<iframe src=\"");
			else if(n instanceof EndVidNode)	htmlOutput.append("\">\r\n");
			/*else if(n instanceof DefineVariableNode){	
				variables.add(lastNode);
				variables.add(n);
			}*/
			else
				htmlOutput.append(n);
			
			//lastNode =n;
			//iter.remove();
			Compiler.Parser.tree.remove();
		}//end while
		
		File newFile = new File(f.getAbsoluteFile(), htmlName);
		newFile.createNewFile();
		PrintWriter pw = new PrintWriter(newFile);
		pw.println(htmlOutput);
		pw.close();
	}//end translate
	
	public Iterator<Node> iterator() {
        return Compiler.Parser.tree.iterator();
   }
	

}//end class LolcodeSemanticAnalyer
