import classes.NodeTreeData;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.util.CoreMap;

import graphs.*;
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
    private XMIHelper xmiHelper = XMIHelper.getInstance();



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
        xmiStructure = buiildXMI(xmiHelper.abstractModelElements);

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
        buildGraphNodes(tree);
        buildGraphEdges(tree);
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
    private void buildGraphEdges(Tree tree)
    {
        System.out.println("buildGraphEdges: ");
        List<NPNode> graphNodes = npGraph.getAllNodes();
        for (NPNode node: graphNodes)
        {
             NPNode parentNode = node;
             System.out.println("parentNode: " + parentNode);
             List<NodeTreeData> connectedItems = new ArrayList<NodeTreeData>();
             TreeHelper.investigateAllConnectedNodes(tree, parentNode.tree, tree, connectedItems);
             npGraph.addEdges(parentNode, connectedItems);
        }

        npGraph.printGraph();
    }

    private void buildXMIGraph() {
        List<NPNode> graphNodes = npGraph.getAllNodes();
        for (NPNode node: graphNodes) {
            xmiGraph.addNode(XMIGraphHelper.getXMINode(node));
        }
        List<NPEdge> graphEdges = npGraph.getAllEdges();
        for (NPEdge edge: graphEdges) {
            xmiGraph.addEdge(XMIGraphHelper.getXMIEdge(edge));
           // xmiGraph.addNode(XMIGraphHelper.getXMINode(node));
        }
        xmiGraph.printGraph();
    }


    private void buildAbstractModelElementsList() {
        List<XMINode> graphNodes = xmiGraph.getAllNodes();
        for (XMINode node: graphNodes) {
            Class element = xmiHelper.getClassElementFromNode(node);
            xmiHelper.addElementToClass(element);
        }
        List<XMIEdge> graphEdges = xmiGraph.getAllEdges();
        for (XMIEdge edge: graphEdges) {
            String parentName = xmiGraph.getNode(edge.parentNodeId).name;
            String childName = xmiGraph.getNode(edge.childNodeId).name;
            Association association = xmiHelper.getAssociationElementFromEdge(edge, parentName, childName);
            xmiHelper.addElementToClass(association);
        }
    }

}
