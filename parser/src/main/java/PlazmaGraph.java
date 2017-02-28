import edu.stanford.nlp.trees.Tree;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 2/28/17.
 */
public class PlazmaGraph {


    DirectedGraph<Tree, DefaultEdge> graph = new DefaultDirectedGraph<Tree, DefaultEdge>(DefaultEdge.class);

    public PlazmaGraph()
    {
    } // ensure non-instantiability.

    /**
     * The starting point for the demo.
     *
     * @param args ignored.
     */
    public void buildGraph()
    {

        // create a graph based on URL objects
        DirectedGraph<URL, DefaultEdge> hrefGraph = createHrefGraph();

        // note directed edges are printed as: (<v1>,<v2>)
        System.out.println(hrefGraph.toString());
    }

    public  void printGraph(DirectedGraph<URL, DefaultEdge> graph) {
        for(DefaultEdge e : graph.edgeSet()){
            System.out.println(graph.getEdgeSource(e) + " --> " + graph.getEdgeTarget(e));
        }
    }

    /**
     * Creates a toy directed graph based on URL objects that represents link structure.
     *
     * @return a graph based on URL objects.
     */
    private static DirectedGraph<URL, DefaultEdge> createHrefGraph()
    {
        DirectedGraph<URL, DefaultEdge> g = new DefaultDirectedGraph<URL, DefaultEdge>(DefaultEdge.class);

        try {
            URL amazon = new URL("http://www.amazon.com");
            URL yahoo = new URL("http://www.yahoo.com");
            URL ebay = new URL("http://www.ebay.com");

            // add the vertices
            g.addVertex(amazon);
            g.addVertex(yahoo);
            g.addVertex(ebay);

            // add edges to create linking structure
            g.addEdge(yahoo, amazon);
            g.addEdge(yahoo, ebay);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return g;
    }


    public void addNodes(List<Tree> nodesNP) {
        for (Tree node: nodesNP){
            System.out.println("node: " + node);
            graph.addVertex(node);
        }
    }
}
