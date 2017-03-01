import edu.stanford.nlp.trees.Tree;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 2/28/17.
 */
public class PlazmaGraph {


    DirectedGraph<Tree, DefaultEdge> graph = new DefaultDirectedGraph<Tree, DefaultEdge>(DefaultEdge.class);

    public PlazmaGraph()
    {
    } // ensure non-instantiability.


    public  void printGraph() {
        for(DefaultEdge e : graph.edgeSet()){
            System.out.println(graph.getEdgeSource(e) + " --> " + graph.getEdgeTarget(e));
        }
    }

    public void addNodes(List<Tree> nodesNP)
    {
        for (Tree node: nodesNP)
        {
            //System.out.println("node: " + node);
            graph.addVertex(node);
        }
    }

    public Tree getNode(Tree nodeNP)
    {
        for(Tree graphNode : graph.vertexSet())
        {
            if(graphNode.equals(nodeNP)) {
                return graphNode;
            }
        }
        return null;
    }

    public void addEdges(Tree parentNode, List<Tree> nodesNP)
    {
        Tree graphNode1 = getNode(parentNode);
        if(graphNode1 == null) {return;}

        for (Tree node: nodesNP)
        {
            Tree graphNode2 = getNode(node);
            if(graphNode2 != null) {
                graph.addEdge(graphNode1, graphNode2);
            }
        }
    }

    public List<Tree> getAllNodes()
    {
        List<Tree> nodes = new ArrayList<Tree>();
        for(Tree node : graph.vertexSet()) {
            //System.out.println("node: " + node);
            nodes.add(node);
        }
        return nodes;
    }
}
