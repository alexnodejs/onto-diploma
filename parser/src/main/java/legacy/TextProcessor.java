package legacy;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.xml.serialize.XMLSerializer;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import legacy.xmi.model.elements.ofGeneralization.Generalization;
import legacy.xmi.model.elements.ofGeneralization.GeneralizationChild;
import legacy.xmi.model.elements.ofGeneralization.GeneralizationChildClass;
import legacy.xmi.model.elements.ofGeneralization.GeneralizationParent;
import legacy.xmi.model.elements.ofGeneralization.GeneralizationParentClass;
import legacy.xmi.model.elements.ofassociation.Association;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.root.elements.AbstractModelElement;
import legacy.xmi.model.root.elements.ModelItem;
import legacy.xmi.output.XMI_output;
import legacy.xmi.root.elements.Visibility;
import legacy.xmi.root.elements.XMI_Header;
import legacy.xmi.root.elements.XMI;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.Dependencies;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import java.io.Serializable;

import config.*;

public class TextProcessor {

	static StanfordCoreNLP pipeline;
	public static void genearlizationElementBuilder (Class child_class, Class parent_class, List<AbstractModelElement> _absME, int i)
	{
		Generalization _general=new Generalization
				(new GeneralizationChild(
						new GeneralizationChildClass(child_class._model_id)),
				new GeneralizationParent(
						new GeneralizationParentClass(parent_class._model_id)),
				"gen"+child_class._model_name+parent_class._model_name+i,
				null,
				false);
		i++;
		//System.out.println(x);
		child_class.set_generalizableElementGeneralization(_general._generalization_id);
		//_absME.add(child_class);
		_absME.add(_general);
		
	}
	public static Class classElementsBuilder(SemanticGraph dep,IndexedWord word,List<AbstractModelElement> _absME, boolean IsRoot,int i)
	{
		
		String _classname=(Character.toUpperCase(word.originalText().charAt(0)))+word.originalText().substring(1);boolean _generFlag=false;
		Class _classobj1=null, _classobj2=null;
		
		Collection<IndexedWord> _word_children=dep.getChildList(word);
		GramRelationsEnum _gramReltype=null;
		
		for (IndexedWord child : _word_children) {	
			
			System.out.println("IndexedWord:" + dep.getEdge(word, child).getRelation().toString());
			_gramReltype= GramRelationsEnum.getType(dep.getEdge(word, child).getRelation().toString());
			System.out.println(_gramReltype.toString());
			switch (_gramReltype)
			{			
			case REL_JJ:
			case REL_AMOD:
			case REL_NN:
				_classname=(Character.toUpperCase(child.originalText().charAt(0)) + child.originalText().substring(1))+_classname;
				//System.out.println("YAY "+ _classname);
				break;
			case REL_NSUBJ:
			
				
				_classobj1=classElementsBuilder(dep,child, _absME,false,i);
				//System.out.println("class blabla after NSUBJ");
				_generFlag=true;
				break;
			case REL_DEP:
				_classobj2=new Class(_classname+"_ClassID"+i,_classname);
				i++;
				_absME.add(_classobj2);
				System.out.println("YAY added cl2 in the DEP "+_classname);
				_classobj1=classElementsBuilder(dep,child, _absME,false,i);
				//_absME.add(_classobj1);
				//System.out.println("class blabla after dep");
				_generFlag=true;
				genearlizationElementBuilder(_classobj1,_classobj2, _absME,i);
				return _classobj2;
				//break;
				
			case REL_VMOD:
			case REL_PARTMOD:
			case REL_RCMOD:
				System.out.println("YAY RCMOD");
				if (_generFlag) 
					{
					System.out.println("YAY from genFlag");
					associationElementBuilder (false,dep,child, _absME,_classobj1,i);
					_classobj2=new Class(_classname+"_ClassID"+i,_classname);
					i++;
					_absME.add(_classobj2);
					genearlizationElementBuilder(_classobj1,_classobj2, _absME,i);
					System.out.println("YAY cl2 added in the 1st RCMOD "+_classname);
					return null;
					}
				else {
					//System.out.println("YAY from !genFlag");
					_classobj1=new Class(_classname+"_ClassID"+i,_classname);
					i++;
					_absME.add(_classobj1);
					associationElementBuilder(false,dep,child, _absME,_classobj1,i);
					System.out.println("YAY added cl1 in the end RCMOD "+_classname);
					return _classobj1;
				}
				//break;
			default:
				System.out.println(_gramReltype.toString());
				continue;
			}
		}
		
//		if (IsRoot)
//		{
//			return null;
//		}
//		else 
//		if (_generFlag)
//		{
//			genearlizationElementBuilder(_classobj1,_classobj2, _absME);
//			System.out.println("YAY retirn2"+_classname);
//			return _classobj2;
//		}
//		else {
		
			_classobj1=new Class(_classname+"_ClassID"+i,_classname);
			i++;
			_absME.add(_classobj1);
			System.out.println("YAY added in the end "+_classname);
			
			return _classobj1;
			//}
		
	}
	
	private static Association associationElementBuilder(boolean flag,SemanticGraph dep,IndexedWord word, List<AbstractModelElement> _absME, Class classobj1,int i) 
	{
		
		
		String _assoc_name=word.originalText();;
		Association _association=null;
		Class _classobj2=null;
		
		
		Collection<IndexedWord> _word_children=dep.getChildList(word);
		GramRelationsEnum _gramReltype=null;
		
		for (IndexedWord child : _word_children) {			
			_gramReltype= GramRelationsEnum.getType(dep.getEdge(word, child).getRelation().toString());
			switch (_gramReltype)
			{			
			case REL_AUX:
			case REL_AUXPASS:
				
				_assoc_name=(Character.toUpperCase(child.originalText().charAt(0)))+child.originalText().substring(1)+_assoc_name ;
				System.out.println("Assoc AUXPASS" +_assoc_name);
				break;
			case REL_NSUBJPASS:
				if (child.get(PartOfSpeechAnnotation.class).equals("WDT"))
					break;
			case REL_DOBJ:
				System.out.println("Assoc DOBJ"+_assoc_name);
				if (classobj1!=null) 
				_classobj2=classElementsBuilder(dep,child, _absME,false,i);
				else classobj1=classElementsBuilder(dep,child, _absME,false,i);
				break;
			case REL_XCOMP:
				System.out.println("Assoc XCOMP");
				_association=associationElementBuilder(true,dep,child, _absME, null,i);			
				break;
			case REL_AGENT:
				
				_assoc_name=_assoc_name+"by";
				System.out.println("Assoc AGENT"+_assoc_name);
				_classobj2=classElementsBuilder(dep, child,_absME,false,i);
				break;
			default:
				System.out.println(_gramReltype.toString());
				continue;
			
			}
		}
		if ((flag)&&(classobj1!=null))
		{
			_association= new Association(_assoc_name+"_AssociationID"+i, _assoc_name, "", classobj1._model_id,i);
			i++;
			System.out.println("Return Assoc flag&&cl1 "+_assoc_name);
			return _association;
		}
		else if (_association==null)
		{
			_association= new Association(_assoc_name+"_AssociationID"+i, _assoc_name, classobj1._model_id, _classobj2._model_id,i);
			i++;
			System.out.println("Return Assoc !flag&&cl1 "+_assoc_name);
			_absME.add(_association);
			System.out.println("YAY added assoc in the end "+_assoc_name);
			return null;	
		}
		else 
			{
			_association._model_name=_assoc_name+_association._model_name;
		_association._model_id=_assoc_name+_association._model_id;
		_association.Set_AssociationEnd1(classobj1._model_id,i);
		i++;
		_absME.add(_association);
		System.out.println("Return Assoc ret else "+_association._model_name);
		return null;
			}
	}
	public static void ClassSearch(SemanticGraph dep,IndexedWord word,List<AbstractModelElement> classes)
	{
		String _classname="";
		String pos = word.get(PartOfSpeechAnnotation.class);
        //System.out.println(pos);
		Class tmp=null;
        Collection<IndexedWord> _word_children=dep.getChildList(word);
        
        GrammaticalRelation gr=null;
        
        for (IndexedWord child : _word_children) {
        	gr=dep.getEdge(word, child).getRelation();
			System.out.println(gr.hashCode());
			
			
        	if ((dep.getEdge(word, child).getRelation().toString().equals("amod"))||(dep.getEdge(word, child).getRelation().toString().equals("nn")))
        	{
        		System.out.print(" Relation "+ dep.getEdge(word, child).getRelation().toString()+ " ["+word.originalText()+" "+child.originalText()+"]");
        		_classname=(Character.toUpperCase(child.originalText().charAt(0)) + child.originalText().substring(1));
        	}
        	else ClassSearch(dep,child,classes);
		}
        boolean t=true;
		//System.out.println(GrammaticalRelation.GrammaticalRelationAnnotation.class.getDeclaredClasses().getClass().getName());
		
        if ((pos.equals("NN"))||(pos.equals("NNP"))||(pos.equals("NNS")))
        {
        	
        	//_classname=word.originalText();
        	_classname= _classname+Character.toUpperCase(word.lemma().charAt(0)) + word.lemma().substring(1);
        	
        //	for (Class _class: classes)
        //		if (_class._model_id.equals("classID:"+_classname)) t=false;
        		//System.out.println(_class._model_id);
        		
        //	 if (t)
        //		classes.add(new Class("classID:"+_classname, _classname, false,false,false,false,Visibility.PUBLIC,false));
        	//System.out.println("Class" + _classname);
        }
        
	}
	
	public static XMI Processing()//File _file
	{
		File _file = new File(Constants.resourcesDir + "input.txt");
		XMI _xmiStructure=null;
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma,parse");
			 pipeline = new StanfordCoreNLP(props);

		//List <String> classes_list=new ArrayList<String>();
		
		String text=null;//Text container for each file
		
	        String _filename = Constants.outputparserfilename; //_file.getName().substring(0, _file.getName().lastIndexOf("."));
	        String _destinationFolder = Constants.resourcesDir;//"newout";//_file.getAbsolutePath().substring(0, _file.getAbsolutePath().lastIndexOf("."))+"//";
	                   
	        
	        FileInputStream fis;
			try {
				ModelItem _xmi_modelItem=new ModelItem();
                
				
				
				fis = new FileInputStream(_file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
				text=reader.readLine();
				reader.close();
				Annotation annotation;
				annotation = new Annotation(text);
				
				pipeline.annotate(annotation);
				//System.out.print(pipeline.);
				List<CoreMap> sentences = annotation.get(SentencesAnnotation.class);
				Tree tree = null;
				
				
				PrintWriter Fileout = new PrintWriter(_destinationFolder+_filename+".txt");
				List<AbstractModelElement> abslist=new ArrayList<AbstractModelElement>();
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
		                  
		                /* if (relation.toString().equals("amod"))
		                	 _xmi_modelItem._classList.add(new Class("classID:"+, _className, _isSpecification, _isRoot, _isLeaf, _isAbstract, visibility, isActive))
		                	 classes_list.add(dependent+"_"+governor);*/
		                  System.out.println("No:"+j+" Relation: "+relation.toString()+" Dependent ID: "+dependent_index+" Dependent: "+dependent.toString()+" Governor ID: "+governor_index+" Governor: "+governor.toString());

		            }
		           // ClassSearch(dependencies, rootword, abslist);
		           if (rootword.tag().equalsIgnoreCase("NN"))
		           {
		        	   classElementsBuilder(dependencies, rootword, abslist,true,0);
		           }
		           else 
		           {
		        	   associationElementBuilder(false, dependencies, rootword, abslist, null,0);
		           }
		            
		           System.out.println("DonE!!!!! "+abslist.size() );
		            for (AbstractModelElement c1:abslist)
		            {
		            	if (c1 instanceof Class) {		            		
		            		_xmi_modelItem._classList.add((Class)c1);
		            		System.out.println("Class"+c1._model_name);
		            	}	else 
		            		if (c1 instanceof  Association){
		            			_xmi_modelItem._associationList.add((Association)c1);
		            			System.out.println("Assoc "+c1._model_name);
		            		}
		            		else 
			            		if (c1 instanceof  Generalization){
			            			_xmi_modelItem._generalizationList.add((Generalization)c1);
			            			System.out.println("Assoc "+c1._model_name);
			            		}

		            }
		        }
				
	//				String classname;
	//		    for(CoreMap sentence: sentences) {
			      // traversing the words in the current sentence
			      // a CoreLabel is a CoreMap with additional token-specific methods
	//		      for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
			        // this is the text of the token
	//		        String word = token.get(TextAnnotation.class);
			        //System.out.print(word);
			        // this is the POS tag of the token
	//		        String pos = token.get(PartOfSpeechAnnotation.class);
			        //System.out.println(pos);
		//	        if ((pos.equals("NN"))||(pos.equals("NNP"))||(pos.equals("NNS")))
		//	        {
		//	        	classname= Character.toUpperCase(token.lemma().charAt(0)) + token.lemma().substring(1);
	//		        	_xmi_modelItem._classList.add(new Class("classID:"+token.originalText(), classname, false,false,false,false,Visibility.PUBLIC,false));
			        	//classes_list.add(word);
			        	//NN_word_list.add(word);
			        	//System.out.println(word);
	//		        Fileout.println(word);
	//		        }
			        // this is the NER label of the token
			        //String ne = token.get(NamedEntityTagAnnotation.class);  
			       // System.out.print(ne);
	//		      }
	//		      tree = sentence.get(TreeAnnotation.class);
//String ds1=tree.parent(tree).toString();
//System.out.println(ds1);
			      // this is the Stanford dependency graph of the current sentence
			      //SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
			    
	//		      }
			    
			   Fileout.close();
			   PrintWriter out1=new PrintWriter(_destinationFolder+_filename+".txt");
				pipeline.prettyPrint(annotation, out1);
				out1.close();
				PrintWriter xmlOut=new PrintWriter(_destinationFolder+_filename+".xml");
				pipeline.xmlPrint(annotation, xmlOut);
				xmlOut.close();
				//System.out.print("HIIIIIXML!"+_destinationFolder+_filename+".smt");
				
				_xmiStructure=new XMI(_xmi_modelItem);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		

	    return _xmiStructure;
}
}
