package graphs;

import java.util.List;
import classes.NodeTreeData;

import utils.TreeHelper;
/**
 * Created by svitlanamoiseyenko on 3/3/17.
 */
public class XMIEdge {
    public int id;
    public int parentNodeId;
    public int childNodeId;
    public String name;

    public ConnectionType connectionType;
    public String startName;
    public String endName;


    public XMIEdge(int id) {
        this.id = id;
        this.connectionType = connectionType.ASSOCIATION;
        this.startName = "";
        this.endName = "";
    }

    public void setType(String tag, String word) {

        if (this.connectionType != ConnectionType.ASSOCIATION) {
            return;
        }

        System.out.println("===setType==== " + this.name);
        if (TreeHelper.isAggregation(tag)) {
            //this.startName = word;
            this.connectionType = ConnectionType.AGGREGATION;
        } else if (TreeHelper.isGeneralization(tag)) {
            this.connectionType = ConnectionType.GENERALIZATION;
        } else {
            this.connectionType = ConnectionType.ASSOCIATION;
        }
    }

    public void seNodesIDS(int parentNodeId, int childNodeId) {
        this.parentNodeId = parentNodeId;
        this.childNodeId = childNodeId;
    }

}
