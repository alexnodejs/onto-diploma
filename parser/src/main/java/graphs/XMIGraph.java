package graphs;

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
            System.out.println("XMI: " + graph.getEdgeSource(e).name + " -->" + String.valueOf(e.name) + " -> "+ graph.getEdgeTarget(e).name);
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

    public void addEdge(XMIEdge edge) {
        if(edge == null) {
            return;
        }
        XMINode xmiParentNode = getNode(edge.parentNodeId);
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
