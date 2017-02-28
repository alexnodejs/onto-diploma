import config.Constants;
import edu.stanford.nlp.ling.LabeledWord;
import edu.stanford.nlp.trees.Tree;

import java.util.Arrays;

/**
 * Created by svitlanamoiseyenko on 2/27/17.
 */
public class WordUtil {

    public static boolean isRelationGeneralization(Tree tree) {
        if(!tree.isLeaf() && Arrays.asList(Constants.relationGeneralizationTags).contains(tree.value())) {
            return true;
        }
        return false;
    }

    public static boolean isRelationAggregation(Tree tree) {
        if(!tree.isLeaf() && Arrays.asList(Constants.relationAggregationTags).contains(tree.value())) {
            return true;
        }
        return false;
    }

    public static boolean isOtherNounTag(LabeledWord labeledWord) {
        if(Arrays.asList(Constants.wlOtherNounTags).contains(labeledWord.tag().toString())) {
            return true;
        }
        return false;
    }

    public static boolean isNounNNTag(LabeledWord labeledWord) {
        if(Arrays.asList(Constants.wlNNNounTags).contains(labeledWord.tag().toString())) {
            return true;
        }
        return false;
    }

    public static boolean isNoun(LabeledWord labeledWord) {
        if(Arrays.asList(Constants.wlNNNounTags).contains(labeledWord.tag().toString()) ||
                Arrays.asList(Constants.wlOtherNounTags).contains(labeledWord.tag().toString())      ) {
            return true;
        }
        return false;
    }


    public static boolean isVerbTag(LabeledWord labeledWord) {
        if(Arrays.asList(Constants.wlVerbsTags).contains(labeledWord.tag().toString())) {
            return true;
        }
        return false;
    }


}
