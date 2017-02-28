import config.Constants;
import edu.stanford.nlp.trees.Tree;

import java.util.Arrays;

/**
 * Created by svitlanamoiseyenko on 2/27/17.
 */
public class PhraseUtil {

    // Phrase Level
    //Base
    public static boolean isRelationTag(Tree tree) {
        if( !tree.isLeaf() && Arrays.asList(Constants.relationTag).contains(tree.value())) {
            return true;
        }
        return false;
    }

    public static boolean isRelationNP(Tree tree) {
        if( !tree.isLeaf() && Arrays.asList(Constants.relationNPTag).contains(tree.value())) {
            return true;
        }
        return false;
    }


    // Additional
    public static boolean isRelationPrepositionalPhraseTags(Tree tree) {
        if(!tree.isLeaf() && Arrays.asList(Constants.relationPrepositionalPhraseTags).contains(tree.value())) {
            return true;
        }
        return false;
    }
}
