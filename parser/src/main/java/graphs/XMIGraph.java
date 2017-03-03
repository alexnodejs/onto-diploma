package graphs;

import classes.CustomData;
import edu.stanford.nlp.trees.Tree;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 3/3/17.
 */
public class XMIGraph {

    DirectedGraph<XMINode, XMIEdge> graph = new DefaultDirectedGraph<XMINode, XMIEdge>(XMIEdge.class);

    public  void printGraph() {
        for(XMIEdge e : graph.edgeSet()){
            System.out.println("XMI: " + graph.getEdgeSource(e) + " -->" + String.valueOf(e.name) + " -> "+ graph.getEdgeTarget(e));
        }
    }

    public void addNode(XMINode node)
    {
            graph.addVertex(node);
    }

    public void addNodes(List<XMINode> nodes)
    {
        for (XMINode node: nodes)
        {
            //System.out.println("node: " + node);
            graph.addVertex(node);
        }
    }

    public XMINode getNode(int id)
    {
        for(XMINode graphNode : graph.vertexSet())
        {
            if(graphNode.id == id) {
                return graphNode;
            }
        }
        return null;
    }

//    public Tree getNode(XMINode node)
//    {
//        for(XMINode graphNode : graph.vertexSet())
//        {
//            if(graphNode.equals(node)) {
//                return graphNode;
//            }
//        }
//        return null;
//    }


//    public void addEdges(Tree parentNode, List<CustomData> nodes)
//    {
//        Tree graphNode1 = getNode(parentNode);
//        if(graphNode1 == null) {return;}
//
//        int i = 0;
//        for (CustomData data: nodes)
//        {
//            Tree graphNode2 = getNode(data.node);
//            if(graphNode2 != null) {
//                graph.addEdge(graphNode1, graphNode2, new NPEdge(i, data.path));
//            }
//            i ++;
//        }
//    }

    public void addEdge(XMIEdge edge) {

        System.out.println("===edge==== " + edge.name);
        if(edge == null) {
            return;
        }
        XMINode xmiParentNode = getNode(edge.padentNodeId);
        XMINode xmiChildNode = getNode(edge.childNodeId);
        if(xmiParentNode == null) {return;}
        if(xmiChildNode == null) {return;}

        graph.addEdge(xmiParentNode, xmiChildNode, edge);
    }


    public List<XMIEdge> getAllEdges()
    {
        List<XMIEdge> edges = new ArrayList<XMIEdge>();
        edges.addAll(graph.edgeSet());
        return edges;
    }

    public List<XMINode> getAllNodes()
    {
        List<XMINode> nodes = new ArrayList<XMINode>();
        for(XMINode node : graph.vertexSet()) {
            nodes.add(node);
        }
        return nodes;
    }
}
