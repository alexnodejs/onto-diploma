import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import legacy.xmi.model.elements.ofGeneralization.Generalization;
import legacy.xmi.model.elements.ofassociation.Association;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.root.elements.AbstractModelElement;
import legacy.xmi.model.root.elements.ModelItem;
import legacy.xmi.root.elements.XMI;

import config.*;


public class TextParser {

	public void parseText(String text) {
		
		Properties props = new Properties();
		//This props maybe should be configurable?
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma,parse"); //"tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        
        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        System.out.println("document annotation:  " + document);
        //saveToDisk(pipeline, document);
	}
	
	public String readFile(String filename) {
		
		String inputText = "";
		//create file object
	    File file = new File(Constants.resourcesDir + filename);
	   
	    int ch;
	    StringBuffer strContent = new StringBuffer("");
	    FileInputStream fin = null;
	   
	    try
	    {
	      /*
	       * Create new FileInputStream object. Constructor of FileInputStream throws
	       * FileNotFoundException if the agrument File does not exist.
	       */
	      fin = new FileInputStream(file);
	     
	      /*
	       * To read bytes from stream use,
	       * int read() method of FileInputStream class.
	       *
	       * This method reads a byte from stream. This method returns next byte of data
	       * from file or -1 if the end of the file is reached.
	       *
	       * Read method throws IOException in case of any IO errors.
	       */
	      while( (ch = fin.read()) != -1)
	        strContent.append((char)ch);
	 
	      /*
	       * To close the FileInputStream, use
	       * void close() method of FileInputStream class.
	       *
	       * close method also throws IOException.
	       */
	      fin.close();
	     
	    }
	    catch(FileNotFoundException e)
	    {
	      System.out.println("File " + file.getAbsolutePath() +
	                             " could not be found on filesystem");
	    }
	    catch(IOException ioe)
	    {
	      System.out.println("Exception while reading the file" + ioe);
	    }
	   
	    System.out.println("File contents :");
	    System.out.println(strContent);
	    inputText = strContent.toString();
		return inputText;
	}
	
	private void saveToDisk(StanfordCoreNLP pipeline, Annotation document) {
		 try{   
	       FileOutputStream os = new FileOutputStream(new File(Constants.resourcesDir, Constants.outputXML));
	       pipeline.xmlPrint(document, os); 
	     } catch (IOException e) {
	       e.printStackTrace(); 
	     }
	}
	
	
}
