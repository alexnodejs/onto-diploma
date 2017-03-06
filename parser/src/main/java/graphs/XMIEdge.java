package graphs;

import java.util.List;

/**
 * Created by svitlanamoiseyenko on 3/3/17.
 */
public class XMIEdge {
    public int id;
    public int parentNodeId;
    public int childNodeId;
    public String name;
    public String type = "none";
    public String startName;
    public String endName;


    public XMIEdge(int id) {
        this.id = id;
        //this.name = name;
        //this.type = type;
    }
}
