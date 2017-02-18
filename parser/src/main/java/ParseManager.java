import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import config.Constants;
import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.pipeline.XMLOutputter;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import legacy.GramRelationsEnum;
import legacy.xmi.model.elements.ofGeneralization.*;
import legacy.xmi.model.root.elements.ModelItem;
import legacy.xmi.root.elements.XMI;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import legacy.xmi.model.elements.ofassociation.Association;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.root.elements.AbstractModelElement;
import nu.xom.Document;

public class ParseManager {
	static StanfordCoreNLP pipeline;


    public static void genearlizationElementBuilder (Class child_class,
                                                     Class parent_class,
                                                     List<AbstractModelElement> _absME,
                                                     int index)
    {
        System.out.println("======  genearlizationElementBuildergenearlizationElementBuilder child_class =====" + child_class._model_name);
        System.out.println("======  genearlizationElementBuilder parent_class =====" + parent_class._model_name);
        Generalization generalizationModel = new Generalization
                (new GeneralizationChild(
                        new GeneralizationChildClass(child_class._model_id)),
                        new GeneralizationParent(
                                new GeneralizationParentClass(parent_class._model_id)),
                        "gen"+child_class._model_name+parent_class._model_name + index,
                        null,
                        false);
        //index++;
        child_class.set_generalizableElementGeneralization(generalizationModel._generalization_id);
        //_absME.add(child_class);
        _absME.add(generalizationModel);

    }

    private static void associationElementBuilder(boolean flag,
                                                         SemanticGraph dep,
                                                         IndexedWord word,
                                                         List<AbstractModelElement> absME,
                                                         Class parent,
                                                         Class child,
                                                         int index)
    {

        System.out.println("== associationElementBuilder ===");
        String assoc_name = word.originalText();
        Association association = null;

        //1
        association = new Association(assoc_name + "_AssociationID" + index,
                assoc_name,
                parent._model_id,
                child._model_id,
                index);

        if(!Arrays.asList(absME).contains(association)) {
            System.out.println("inversigateGraph no assoc class:" + association._model_name);
            absME.add(association);
        }
    }

    private static Class classElementsBuilder(SemanticGraph dep,
                                              IndexedWord word,
                                              IndexedWord child,
                                              Class parentElement,
                                              List<AbstractModelElement> absME,
                                              int index) {

        String className = (Character.toUpperCase(word.originalText().charAt(0))) + word.originalText().substring(1);
        System.out.println("=== classElementsBuilder === " + className);
        Class element = new Class(className + "_ClassID" + index, className);

        if (element == null) { return null; }

        if(!IsElementExist(absME, element)){
            absME.add(element);
        }

        if (child != null ) {
            inversigateGraph(dep, child, parentElement, absME);
        }

        return element;
    }

    private static boolean IsElementExist(List<AbstractModelElement> absME, Class element) {
        if(!Arrays.asList(absME).contains(element)) {
            return false;
        }
        return true;
    }

    private static void inversigateGraph(SemanticGraph dep,
                                IndexedWord word,
                                Class parentElement,
                                List<AbstractModelElement> absME) {

        System.out.println("inversigateGraph");
        Collection<IndexedWord> word_children = dep.getChildList(word);

        for (IndexedWord child : word_children) {

            System.out.println("inversigateGraph IndexedWord:" + dep.getEdge(word, child).getRelation().toString());
            System.out.println("inversigateGraph child.word():" + child.word());
            System.out.println("inversigateGraph word.word():" + word.word());

            String relationType = dep.getEdge(word, child).getRelation().toString();
            System.out.println("inversigateGraph relationType:" + relationType);

            System.out.println("inversigateGraph child TAG---" + child.tag());
            System.out.println("inversigateGraph word TAG---" + word.tag());


            if (Arrays.asList(Constants.relationsAssociationSet).contains(relationType)) {
                    Class childElement = classElementsBuilder(dep, child, null, parentElement, absME, 0);
                    associationElementBuilder(false, dep, child, absME, parentElement, childElement, 0);

            } else if (Arrays.asList(Constants.relationsGeneralizationSet).contains(relationType)) {
                Class childElement = classElementsBuilder(dep, child, null, parentElement, absME, 0);
                genearlizationElementBuilder(parentElement, childElement, absME,0);
            }

        }
    }


	public static XMI Processing(String filename)//File _file
	{
		String fileText = FileManager.readFile(filename); //"The director is 65 years old";//FileManager.readFile(filename);

		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma,parse");
		pipeline = new StanfordCoreNLP(props);
		System.out.println("pipeline:  " + pipeline);
		Annotation document;
		document = new Annotation(fileText);
		pipeline.annotate(document);
		System.out.print("pipeline annotation" + document);


        List<AbstractModelElement> abstractModelElementList = ParseDocument(document);
        XMI xmiStructure = buiildXMI(abstractModelElementList);

	    return xmiStructure;
    }

	private static List<AbstractModelElement> ParseDocument(Annotation document) {
		// Go through sentences



        List<AbstractModelElement> abslist = new ArrayList<AbstractModelElement>();
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for(CoreMap sentence: sentences) {
            System.out.println(" sentence---" + sentence);
            // this is the parse tree of the current sentence
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            System.out.println("tree---" + tree);

            // this is the Stanford dependency graph of the current sentence
            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
            System.out.println("SemanticGraph---" + dependencies);

            IndexedWord firstRoot = dependencies.getFirstRoot();
            System.out.println("firstRoot---" + firstRoot);

            // Build XMI elements
            Class element = classElementsBuilder(dependencies, firstRoot, null, null, abslist, 0);
            inversigateGraph(dependencies, firstRoot, element, abslist);

            System.out.println("firstRoot TAG---" + firstRoot.tag());


        }
        return abslist;
	}

	private  static XMI buiildXMI(List<AbstractModelElement> abstractModelElementList) {

        XMI xmiStructure;
        ModelItem _xmi_modelItem=new ModelItem();

        System.out.println("buiildXMI!!!!! " + abstractModelElementList.size() );
        for (AbstractModelElement modelElement: abstractModelElementList)
        {
            if (modelElement instanceof Class) {
                _xmi_modelItem._classList.add((Class)modelElement);
                System.out.println(" XMI Class: "+ modelElement._model_name);
            }
            else if (modelElement instanceof  Association){
                _xmi_modelItem._associationList.add((Association)modelElement);
                System.out.println(" XMI Assoc "+ modelElement._model_name);
            }
            else if (modelElement instanceof  Generalization){
                _xmi_modelItem._generalizationList.add((Generalization)modelElement);
                System.out.println(" XMI Generalization "+ modelElement._model_name);
            }

        }
        xmiStructure = new XMI(_xmi_modelItem);
        return  xmiStructure;
    }
}
