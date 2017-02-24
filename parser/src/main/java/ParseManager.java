
import edu.stanford.nlp.ling.CoreAnnotations;
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


    private void attachClassElement(Tree treeNode) {
        Class classElement = ElementBuilderUtil.classElementsBuilder(treeNode.value(), generateIndex());
        addElementToClass(classElement);
    }

    private void investigateClassElements(Tree tree) {


        System.out.println("==== investigateClassElements ====");
        List<Tree> childList = tree.getChildrenAsList();
        for (Tree childTree : childList) {

//          System.out.println(" investigateClassElements child.nodeString():" + childTree.nodeString());
//          System.out.println(" investigateClassElements child.value():" + childTree.value());
//          System.out.println(" investigateClassElements child.leaves():" + childTree.getLeaves());
//          System.out.println(" investigateClassElements child.depth():" + childTree.depth());
//          System.out.println(" investigateClassElements child.isLeaf():" + childTree.isLeaf());
//          System.out.println(" investigateClassElements child.label():" + childTree.label());
            if(!childTree.isLeaf() && POSUtil.isNoun(childTree.value())) {
                List<Tree> leaves = childTree.getLeaves();
                System.out.println(" investigateClassElements leaves:" + leaves);
                for (Tree leave : leaves) {
                     attachClassElement(leave);
                }
            }
            investigateClassElements(childTree);
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


            System.out.println(" investigateClassElements root:" + tree.value());

            //TODO: move to investigateClassElements
//          if (POSUtil.isNoun(firstRoot)) {
//              attachClassElement(firstRoot);
//          }


//            investigateClassElements(dependencies, firstRoot);
//            investigateAdjectives(dependencies, firstRoot);
//            investigateOperation(dependencies, firstRoot);
//            investigateClassConnections(dependencies, firstRoot);
//            investigateRelations(dependencies, firstRoot);
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
