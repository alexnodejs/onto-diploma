import edu.stanford.nlp.graph.Graph;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;
import legacy.xmi.root.elements.XMI;
import org.jgrapht.graph.DefaultEdge;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by svitlanamoiseyenko on 2/28/17.
 */
public class TextParser {

    private StanfordCoreNLP pipeline;
    private PlazmaGraph plazmaGraph = new PlazmaGraph();

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
        //xmiStructure = buiildXMI(abslist);

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

            buildGraphNodes(tree);
        }
    }

    private void buildGraphNodes(Tree tree)
    {
        List<Tree> nodesNP = new ArrayList<Tree>();
        TreeHelper.getNPTrees(tree, nodesNP);
        plazmaGraph.addNodes(nodesNP);

        buildGraphEdges(nodesNP, tree);
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

}
