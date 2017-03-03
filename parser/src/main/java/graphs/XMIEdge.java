package graphs;

import java.util.List;

/**
 * Created by svitlanamoiseyenko on 3/3/17.
 */
public class XMIEdge {
    public  int index;
    public String name;
    public String type;
    public String startName;
    public String endName;

    public XMIEdge(int index, String name, String type) {
        this.index = index;
        this.name = name;
        this.type = type;
    }
}
