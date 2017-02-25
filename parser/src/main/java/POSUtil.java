import config.Constants;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.LabeledWord;

import java.util.Arrays;

/**
 * Created by svitlanamoiseyenko on 2/21/17.
 */
public class POSUtil {

    public static boolean isAdjective(IndexedWord word) {
        if(Arrays.asList(Constants.adjectivePOS).contains(word.tag())) {
            return true;
        }
        return false;
    }

    public static boolean isNoun(IndexedWord word) {
        if(Arrays.asList(Constants.nounPOS).contains(word.tag())) {
            return true;
        }
        return false;
    }

    public static boolean isVerb(IndexedWord word) {
        if(Arrays.asList(Constants.verbPOS).contains(word.tag())) {
            return true;
        }
        return false;
    }

    //New
    public static boolean isNoun(LabeledWord labeledWord) {
        if(Arrays.asList(Constants.posNounSet).contains(labeledWord.tag().toString())) {
            return true;
        }
        return false;
    }

    public static boolean isVerb(LabeledWord labeledWord) {
        if(Arrays.asList(Constants.posVerbsSet).contains(labeledWord.tag().toString())) {
            return true;
        }
        return false;
    }



}
