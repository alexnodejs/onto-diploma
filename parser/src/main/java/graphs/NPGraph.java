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
    DirectedGraph<NPNode, NPEdge> graph = new DefaultDirectedGraph<NPNode, NPEdge>(NPEdge.class);
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
        int index = 0;
        for (Tree tree: nodesNP)
        {
            //System.out.println("node: " + node);
            graph.addVertex(new NPNode(index, tree));
            index ++;
        }
    }

    public NPNode getNode(Tree nodeNP)
    {
        for(NPNode graphNode : graph.vertexSet())
        {
            if(graphNode.tree.equals(nodeNP)) {
                return graphNode;
            }
        }
        return null;
    }


    public void addEdges(NPNode parentNode, List<CustomData> nodes)
    {
        //NPNode graphNode1 = parentNode;
        if(parentNode == null) {return;}

        int i = 0;
        for (CustomData data: nodes)
        {
            NPNode graphNode2 = getNode(data.node);
            if(graphNode2 != null) {
                graph.addEdge(parentNode, graphNode2, new NPEdge(i, data.path, parentNode.id, graphNode2.id));
            }
            i ++;
        }
    }

    public List<NPEdge> getAllEdges()
    {
        List<NPEdge> edges = new ArrayList<NPEdge>();
        edges.addAll(graph.edgeSet());
        return edges;
    }

    public List<NPNode> getAllNodes()
    {
        List<NPNode> nodes = new ArrayList<NPNode>();
        for(NPNode node : graph.vertexSet()) {
            //System.out.println("node: " + node);
            nodes.add(node);
        }
        return nodes;
    }


}