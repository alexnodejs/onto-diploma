import config.Constants;
import edu.stanford.nlp.ling.IndexedWord;

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
        if(Arrays.asList(Constants.relationsModifierSet).contains(relationType)
                && Arrays.asList(Constants.adjectiveModifierPOS).contains(word.tag())) {
            return true;
        }
        return false;
    }
}
