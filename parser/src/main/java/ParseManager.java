import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import legacy.xmi.model.root.elements.ModelItem;
import legacy.xmi.root.elements.XMI;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
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

public class ParseManager {
	static StanfordCoreNLP pipeline;
	public static XMI Processing(String filename)//File _file
	{
		String text = FileManager.readFile(filename);
		 
		
		//TODO define globally 
		//TODO add ability to choose ask?
		XMI _xmiStructure=null;
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma,parse"); 
		pipeline = new StanfordCoreNLP(props);
		System.out.println("pipeline:  " + pipeline);
		Annotation annotation;
		annotation = new Annotation(text);
		pipeline.annotate(annotation);
		System.out.print("pipeline annotation" + annotation);
 
		 
		ModelItem _xmi_modelItem=new ModelItem();
		List<CoreMap> sentences = annotation.get(SentencesAnnotation.class);
		Tree tree = null;
		List<AbstractModelElement> abslist = new ArrayList<AbstractModelElement>();
		//Go by each sentence
		for (CoreMap sentence : sentences) {
		            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
		            //System.out.println(dependencies.toList());
		            //System.out.println(dependencies.);
		             
		            //System.out.println(dependencies.toString("plain")); //here
		            IndexedWord rootword=dependencies.getFirstRoot();
		            //System.out.println(rootword.toString());
		            //System.out.println(dependencies.getNodeByIndex(3));
		            //Set<SemanticGraphEdge> edge_set1 = dependencies.getEdgeSet();
		            Iterable<SemanticGraphEdge> edge_set1 = dependencies.edgeIterable(); 
		            System.out.println(dependencies.getChildList(rootword));
		            int j=0;
		            for(SemanticGraphEdge edge : edge_set1)
		            {
		                 j++;	                      
    
		                 System.out.println("------EDGE DEPENDENCY: "+j);
		                 Iterator<SemanticGraphEdge> it = edge_set1.iterator();
		                      
		                 IndexedWord dep = edge.getDependent();
		                 String dependent = dep.word();
		                 int dependent_index = dep.index();
		                 IndexedWord gov = edge.getGovernor();
		                 String governor = gov.word();
		                 int governor_index = gov.index();
		                 GrammaticalRelation relation = edge.getRelation();
		                  
//		                 if (relation.toString().equals("amod"))
//		                	 _xmi_modelItem._classList.add(new Class("classID:"+, _className, _isSpecification, _isRoot, _isLeaf, _isAbstract, visibility, isActive))
//		                	 classes_list.add(dependent+"_"+governor);
		                  System.out.println("No:"+j+" Relation: "+relation.toString()+" Dependent ID: "+dependent_index+" Dependent: "+dependent.toString()+" Governor ID: "+governor_index+" Governor: "+governor.toString());

		            }
		}
		//TODO 
            
		_xmiStructure=new XMI(_xmi_modelItem);
	    return _xmiStructure;
    }
}
