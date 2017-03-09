package graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 3/3/17.
 */
public class XMINode {
    public  int id;
    public String name;
    public List<String> attributes;

    public XMINode(int id) {
        this.id = id;
        attributes = new ArrayList<String>();
    }

}
