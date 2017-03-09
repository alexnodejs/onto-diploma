package graphs;

import utils.tree.BaseTreeUtil;

/**
 * Created by svitlanamoiseyenko on 3/3/17.
 */
public class XMIEdge {
    public int id;
    public int parentNodeId;
    public int childNodeId;
    public String name;

    public XMIRelationType XMIRelationType;
    public String startName;
    public String endName;


    public XMIEdge(int id) {
        this.id = id;
        this.XMIRelationType = XMIRelationType.ASSOCIATION;
        this.startName = "";
        this.endName = "";
    }

    public void setType(String tag, String word) {

        if (this.XMIRelationType != XMIRelationType.ASSOCIATION) {
            return;
        }

        if (BaseTreeUtil.isAggregation(tag)) {
            //this.startName = word;
            this.XMIRelationType = XMIRelationType.AGGREGATION;
        } else if (BaseTreeUtil.isGeneralization(tag)) {
            this.XMIRelationType = XMIRelationType.GENERALIZATION;
        } else {
            this.XMIRelationType = XMIRelationType.ASSOCIATION;
        }
    }

    public void seNodesIDS(int parentNodeId, int childNodeId) {
        this.parentNodeId = parentNodeId;
        this.childNodeId = childNodeId;
    }

}
