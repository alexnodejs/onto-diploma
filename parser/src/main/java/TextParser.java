import edu.stanford.nlp.graph.Graph;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import legacy.xmi.root.elements.XMI;


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
        props.put("annotators", "tokenize, ssplit, pos, lemma,parse");
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

    private void ParseDocument(Annotation document) {

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {

            // this is the parse tree of the current sentence
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);

            System.out.println("tree: " + tree);
            buildGraphNodes(tree);
        }
    }

    private void buildGraphNodes(Tree tree) {
        List<Tree> nodesNP = new ArrayList<Tree>();
        TreeHelper.getNPTrees(tree, nodesNP);
        plazmaGraph.addNodes(nodesNP);
        //plazmaGraph.printGraph();
    }


}
