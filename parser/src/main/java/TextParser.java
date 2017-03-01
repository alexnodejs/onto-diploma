import edu.stanford.nlp.graph.Graph;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.util.CoreMap;

import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.elements.ofGeneralization.Generalization;
import legacy.xmi.model.elements.ofassociation.Association;
import legacy.xmi.model.root.elements.AbstractModelElement;
import legacy.xmi.model.root.elements.ModelItem;
import legacy.xmi.root.elements.XMI;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by svitlanamoiseyenko on 2/28/17.
 */
public class TextParser {

    private StanfordCoreNLP pipeline;
    private PlazmaGraph plazmaGraph = new PlazmaGraph();
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

            buildGraphNodesAndEdges(tree);
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

    private void buildGraphNodesAndEdges(Tree tree)
    {
        List<Tree> nodesNP = buildGraphNodes(tree);
        buildGraphEdges(nodesNP, tree);
    }

    private List<Tree> buildGraphNodes(Tree tree)
    {
        List<Tree> nodesNP = new ArrayList<Tree>();
        TreeHelper.getNPTrees(tree, nodesNP);
        plazmaGraph.addNodes(nodesNP);

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
             List<Tree> connectedNodesNP = new ArrayList<Tree>();
             TreeHelper.investigateAllConnectedNodes(tree, parentNode, tree, connectedNodesNP);
             plazmaGraph.addEdges(parentNode, connectedNodesNP);
        }

        plazmaGraph.printGraph();
    }

    private void buildAbstractModelElementsList() {
        List<Tree> graphNodes = plazmaGraph.getAllNodes();
        for (Tree node: graphNodes) {
            TextParserHelper.addElementToClass(GraphHelper.getXMIRepresentationsNode(node, abslist), abslist);
        }
    }

}
