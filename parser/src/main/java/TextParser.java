import classes.CustomData;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.util.CoreMap;

import graphs.NPGraph;
import graphs.XMIGraph;
import graphs.XMINode;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.elements.ofGeneralization.Generalization;
import legacy.xmi.model.elements.ofassociation.Association;
import legacy.xmi.model.root.elements.AbstractModelElement;
import legacy.xmi.model.root.elements.ModelItem;
import legacy.xmi.root.elements.XMI;


import java.util.*;

/**
 * Created by svitlanamoiseyenko on 2/28/17.
 */
public class TextParser {

    private StanfordCoreNLP pipeline;
    private NPGraph npGraph = new NPGraph();
    private XMIGraph xmiGraph = new XMIGraph();
    private List<AbstractModelElement> abslist = new ArrayList<AbstractModelElement>();


    public XMI Processing(String filename)//File _file
    {
        String fileText = FileManager.readFile(filename); //"The director is 65 years old";

         Properties props = new Properties();
         props.put("annotators", "tokenize, ssplit, pos, lemma, parse");//, ner, dcoref
         pipeline = new StanfordCoreNLP(props);

        System.out.println("pipeline:  " + pipeline);
        Annotation document;
        document = new Annotation(fileText);
        pipeline.annotate(document);
        System.out.println(" pipeline annotation" + document);
        XMI xmiStructure = null;

        ParseDocument(document);
        //buildAbstractModelElementsList();
        xmiStructure = buiildXMI(abslist);

        return xmiStructure;
    }

    private void ParseDocument(Annotation document)
    {
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for (CoreMap sentence : sentences)
        {
            // this is the parse tree of the current sentence
            Tree tree = sentence.get(TreeAnnotation.class);
            System.out.println("tree: ");
            TreePrint treePrint = new TreePrint("penn");// latexTree
            treePrint.printTree(tree);

            buildNPGraph(tree);
            buildXMIGraph();
            buildAbstractModelElementsList();


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

    private void buildNPGraph(Tree tree)
    {
        List<Tree> nodesNP = buildGraphNodes(tree);
        buildGraphEdges(nodesNP, tree);
    }

    private List<Tree> buildGraphNodes(Tree tree)
    {
        List<Tree> nodesNP = new ArrayList<Tree>();
        TreeHelper.getNPTrees(tree, nodesNP);
        npGraph.addNodes(nodesNP);

        return nodesNP;
    }

    //Take Node from graph
    //Find this node in Tree and check if has VP sibling
    //If yes - find all NP (does not have child phrases) and does not have VP sibling
    private void buildGraphEdges(List<Tree> nodesNP, Tree tree)
    {
        System.out.println("buildGraphEdges: ");
        for (Tree node: nodesNP)
        {
             Tree parentNode = node;
             System.out.println("parentNode: " + parentNode);
             List<CustomData> connectedItems = new ArrayList<CustomData>();
             TreeHelper.investigateAllConnectedNodes(tree, parentNode, tree, connectedItems);
            npGraph.addEdges(parentNode, connectedItems);
        }

        npGraph.printGraph();
    }

    private void buildXMIGraph() {
        List<Tree> graphNodes = npGraph.getAllNodes();
        for (Tree node: graphNodes) {
            xmiGraph.addNode(XMIGraphHelper.getXMINode(node));
        }
        xmiGraph.printGraph();
    }

//    private void buildAbstractModelElementsList() {
//        List<Tree> graphNodes = plazmaGraph.getAllNodes();
//        for (Tree node: graphNodes) {
//            TextParserHelper.addElementToClass(GraphHelper.getXMIRepresentationsNode(node, abslist), abslist);
//        }
//    }

    private void buildAbstractModelElementsList() {
        List<XMINode> graphNodes = xmiGraph.getAllNodes();
        for (XMINode node: graphNodes) {
            int index = XMIHelper.generateIndex(abslist);
            Class element = XMIHelper.getClassElementFromNode(node, index);
            XMIHelper.addElementToClass(element,abslist);
            //return ElementBuilderUtil.classElementsBuilder(xmiNodeNode.name, index);
            //TextParserHelper.addElementToClass(GraphHelper.getXMIRepresentationsNode(node, abslist), abslist);
        }
    }

}
