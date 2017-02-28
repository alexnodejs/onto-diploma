import edu.stanford.nlp.trees.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 2/28/17.
 */
public class TreeHelper {

    public static boolean isNP(Tree tree) {
        if (tree.value().toString().equals("NP")) {
            return true;
        }
        return false;
    }

    public static boolean isHasPhrases(Tree tree) {
        List<Tree> children = tree.getChildrenAsList();
        //System.out.println("ifHasPhrases: " + children);
        for (Tree child : children) {
            if(child.isPhrasal()) {
                return true;
            }
        }
        return false;
    }

    public static void getNPTrees(Tree root, List<Tree> nodesNP) {
        System.out.println("====getNPTrees====");
        //List<Tree> result = new ArrayList<Tree>();
        List<Tree> subtrees = root.getChildrenAsList();
        for (Tree subtree: subtrees) {

            if(isNP(subtree) && !isHasPhrases(subtree)) {
                System.out.println("subtree: " + subtree);
                nodesNP.add(subtree);
            }
            getNPTrees(subtree, nodesNP);
        }

        //return result;

    }
}
