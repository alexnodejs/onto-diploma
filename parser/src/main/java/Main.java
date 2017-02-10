import java.io.File;

import xmi.output.XMI_output;
import xmi.root.elements.XMI;

public class Main {
	public static void main(String[] args) { 
		 /*TextParser textparser = new TextParser();
		 String text = textparser.readFile("input.txt");
		 //"Stanford University is located in California. It is a great university, founded in 1891.";
		 textparser.parseText(text); */  
		 
		 File dir=new File(Constants.resourcesDir);
	     if(dir.exists()){
	         System.out.println("A folder with name 'new folder' is already exist in the path "+Constants.resourcesDir);
	     }else{
	         dir.mkdir();
	     }
	     XMI p=TextProcessor.Processing();//new XMI(file);//dir
	       //serialize		
	       
	     // if (p!=null)
	     XMI_output.WriteToXMIFile(Constants.resourcesDir + "outputparser" + ".xmi", p);

		 
		 
	 }
}
