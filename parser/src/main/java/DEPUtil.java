import config.Constants;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.trees.Tree;

import java.util.Arrays;

/**
 * Created by svitlanamoiseyenko on 2/22/17.
 */
public class DEPUtil {

    public static boolean isGeneralization(String relationType) {
        if(Arrays.asList(Constants.relationsGeneralizationSet).contains(relationType)) {
            return true;
        }
        return false;
    }

    public static boolean isModifeierRelation(String relationType, IndexedWord word) {
        if(Arrays.asList(Constants.relationsModifierSet).contains(relationType)) {
            // && Arrays.asList(Constants.adjectiveModifierPOS).contains(word.tag())
            return true;
        }
        return false;
    }


    //NEW
    public static boolean isRelationAssociation(Tree tree) {
        if( !tree.isLeaf() && Arrays.asList(Constants.relationAssociationSet).contains(tree.value())) {
            return true;
        }
        return false;
    }

    public static boolean isRelationClass(Tree tree) {
        if( !tree.isLeaf() && Arrays.asList(Constants.relationClassSet).contains(tree.value())) {
            return true;
        }
        return false;
    }
}
