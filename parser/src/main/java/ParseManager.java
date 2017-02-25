
import config.Constants;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.LabeledWord;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotator;
import edu.stanford.nlp.naturalli.NaturalLogicRelation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import legacy.xmi.model.elements.ofGeneralization.Generalization;
import legacy.xmi.model.elements.ofassociation.Association;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.root.elements.AbstractModelElement;
import legacy.xmi.model.root.elements.ModelItem;
import legacy.xmi.root.elements.XMI;

import edu.stanford.nlp.trees.TreePrint;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;

import javax.swing.text.AbstractDocument;

/**
 * Created by svitlanamoiseyenko on 2/23/17.
 */
public class ParseManager {

    private StanfordCoreNLP pipeline;
    private List<AbstractModelElement> abslist = new ArrayList<AbstractModelElement>();

    private int generateIndex() {
        int index = abslist.size() > 0 ? abslist.size() + 1 : 0;
        return index;
    }

    private void addElementToClass(Class element) {
        if (element == null) {
            return;
        }

        if (!SearchUtil.IsElementExist(abslist, element)) {
            abslist.add(element);
        }
    }


    private void attachClassElement(LabeledWord labeledWord) {
        Class classElement = ElementBuilderUtil.classElementsBuilder(labeledWord.word(), generateIndex());
        addElementToClass(classElement);
    }


    private void attachRelation(LabeledWord labeledWord, List<LabeledWord> parents, List<LabeledWord> children) {

        for (LabeledWord parent: parents) {

            Class parentClass =  SearchUtil.getClassElement(parent, SearchType.CLASS_NAME, abslist);

            for (LabeledWord child: children) {
                Class childClass =  SearchUtil.getClassElement(child, SearchType.CLASS_NAME, abslist);


            }
        }
    }



    private void investigateClassElements(Tree tree) {

        System.out.println("==== investigateClassElements ====");
        List<LabeledWord> childList = tree.labeledYield();

        for (LabeledWord labeledWord: childList) {


            System.out.println(" investigateClassElements child.labeledYield():" + labeledWord.word());
            System.out.println(" investigateClassElements child.yield():" + labeledWord.tag());
            if(POSUtil.isNoun(labeledWord)) {
                 attachClassElement(labeledWord);
            }
        }
    }

    private void investigateRelationsElements(Tree tree) {

        List<LabeledWord> parentsList = null;
        List<LabeledWord> childrenList = null;
        List<Tree> childList = tree.getChildrenAsList();
        for (Tree childTree : childList) {

            if (DEPUtil.isRelationClass(childTree)) {

                System.out.println(" ======= ======= ====== ");
                System.out.println(" ======= R investigateRelationsElements root: ====== " + tree);
                System.out.println(" ======= R investigateRelationsElements childTree: ====== " + childTree);
                System.out.println(" ======= R investigateRelationsElements getLeaves: ====== " + childTree.getLeaves());
                System.out.println(" ======= R investigateRelationsElements labeledYield: ====== " + childTree.labeledYield());
                //childrenList = Util.findChildClasses(childTree);
//                System.out.println(" ======= R investigateRelationsElements childTree siblings: ====== " + childTree.firstChild());
//                System.out.println(" ======= R investigateRelationsElements childTree yield: ====== " + tree.firstChild());
//
//                System.out.println(" R investigateRelationsElements childTree value:" + childTree.value());
//                System.out.println(" investigateRelationsElements getLeaves:" + childTree.getLeaves());
//                System.out.println(" investigateRelationsElements childrenList:" + childrenList);
            }

//            if (DEPUtil.isRelationAssociation(childTree)) {
//                System.out.println(" R investigateRelationsElements value:" + childTree.value());
//                System.out.println(" R  investigateRelationsElements depth:" + childTree.depth());
//                System.out.println(" R  investigateRelationsElements labeledYield:" + childTree.labeledYield());
//                LabeledWord relationLabeledWord = Util.findLabeledWord(childTree, TagType.VERB);
//                if (relationLabeledWord != null) {
//                    System.out.println(" VVV relationLabeledWord:" + relationLabeledWord.word());
//                    parentsList = Util.findParentClasses(childTree, relationLabeledWord, tree);
//                    childrenList = Util.findChildClasses(childTree);
//                    System.out.println(" investigateRelationsElements childs:" + childrenList);
//                    if (parentsList != null && childList != null) {
//                        attachRelation(relationLabeledWord, parentsList, childrenList);
//                    }
//                }
//            }
              investigateRelationsElements(childTree);
        }
    }


    public XMI Processing(String filename)//File _file
    {
        String fileText = FileManager.readFile(filename); //"The director is 65 years old";//FileManager.readFile(filename);

        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma,parse");
        pipeline = new StanfordCoreNLP(props);
        System.out.println("pipeline:  " + pipeline);
        Annotation document;
        document = new Annotation(fileText);
        pipeline.annotate(document);
        System.out.print(" pipeline annotation" + document);
        XMI xmiStructure = null;



        
        ParseDocument(document);
        xmiStructure = buiildXMI(abslist);

        return xmiStructure;
    }

    private void ParseDocument(Annotation document) {

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {

            // this is the parse tree of the current sentence
            Tree tree = sentence.get(TreeAnnotation.class);

            System.out.println("tree---" + tree);
            investigateClassElements(tree);
            //investigateRelationsElements(tree);

            //System.out.println(" print tree---");
            //TreePrint treePrint = new TreePrint("penn,latexTree");
            //treePrint.printTree(tree);

        }
    }

    private XMI buiildXMI(List<AbstractModelElement> abstractModelElementList) {

        XMI xmiStructure;
        ModelItem _xmi_modelItem = new ModelItem();

        System.out.println(" buiildXMI!!!!! " + abstractModelElementList.size());
        for (AbstractModelElement modelElement : abstractModelElementList) {
            if (modelElement instanceof Class) {
                _xmi_modelItem._classList.add((Class) modelElement);
                System.out.println(" XMI Class: " + modelElement._model_name);
            } else if (modelElement instanceof Association) {
                _xmi_modelItem._associationList.add((Association) modelElement);
                System.out.println(" XMI Assoc " + modelElement._model_name);
            } else if (modelElement instanceof Generalization) {
                _xmi_modelItem._generalizationList.add((Generalization) modelElement);
                System.out.println("  XMI Generalization " + modelElement._model_name);
            }

        }
        xmiStructure = new XMI(_xmi_modelItem);
        return xmiStructure;
    }
}

//    private void investigateRelationsElements(Tree tree) {
//
//        List<LabeledWord> parentsList = null;
//        List<LabeledWord> childrenList = null;
//        List<Tree> childList = tree.getChildrenAsList();
//        for (Tree childTree : childList) {
//             if (DEPUtil.isRelationAssociation(childTree)) {
//                System.out.println(" R investigateRelationsElements value:" + childTree.value());
//                System.out.println(" R  investigateRelationsElements depth:" + childTree.depth());
//                System.out.println(" R  investigateRelationsElements labeledYield:" + childTree.labeledYield());
//                LabeledWord relationLabeledWord = Util.findLabeledWord(childTree, TagType.VERB);
//                if (relationLabeledWord != null) {
//                    System.out.println(" VVV relationLabeledWord:" + relationLabeledWord.word());
//                    parentsList = Util.findParentClasses(childTree, relationLabeledWord, tree);
//                    childrenList = Util.findChildClasses(childTree);
//                    System.out.println(" investigateRelationsElements childs:" + childrenList);
//                    if (parentsList != null && childList != null) {
//                        attachRelation(relationLabeledWord, parentsList, childrenList);
//                    }
//                }
//             }
//            investigateRelationsElements(childTree);
//        }
//    }


//
//    private void attachAssociation(LabeledWord labeledWord, List<LabeledWord> parents, List<LabeledWord> children) {
//
//        for (LabeledWord parent: parents) {
//            Class parentClass =  SearchUtil.getClassElement(parent, SearchType.CLASS_NAME, abslist);
//
//            for (LabeledWord child: children) {
//                Class childClass =  SearchUtil.getClassElement(child, SearchType.CLASS_NAME, abslist);
//
//                Association associationElemet = ElementBuilderUtil.associationElementBuilder(labeledWord, parentClass, childClass, generateIndex());
//                if (!SearchUtil.IsAssociationExist(abslist, associationElemet)) {
//                    abslist.add(associationElemet);
//                }
//            }
//        }
//    }
