package graphs;

import edu.stanford.nlp.trees.Tree;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.ArrayList;
import java.util.List;
import classes.CustomData;



/**
 * Created by svitlanamoiseyenko on 2/28/17.
 */
public class NPGraph {

    //DirectedGraph<Tree, DefaultEdge> graph = new DefaultDirectedGraph<Tree, DefaultEdge>(DefaultEdge.class);
    DirectedGraph<Tree, NPEdge> graph = new DefaultDirectedGraph<Tree, NPEdge>(NPEdge.class);
    public NPGraph()
    {
    }

    //    public  void printGraph() {
    //        for(DefaultEdge e : graph.edgeSet()){
    //            System.out.println(graph.getEdgeSource(e) + " --> " + graph.getEdgeTarget(e));
    //        }
    //    }

    public  void printGraph() {
        System.out.println("===printGraph==" + graph.edgeSet());
        for(NPEdge e : graph.edgeSet()){
            System.out.println(graph.getEdgeSource(e) + " -->" + String.valueOf(e.path) + " -> "+ graph.getEdgeTarget(e));
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


    public void addEdges(Tree parentNode, List<CustomData> nodes)
    {
        Tree graphNode1 = getNode(parentNode);
        if(graphNode1 == null) {return;}

        int i = 0;
        for (CustomData data: nodes)
        {
            Tree graphNode2 = getNode(data.node);
            if(graphNode2 != null) {
                graph.addEdge(graphNode1, graphNode2, new NPEdge(i, data.path));
            }
            i ++;
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