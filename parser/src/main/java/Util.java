import edu.stanford.nlp.ling.LabeledWord;
import edu.stanford.nlp.trees.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 2/25/17.
 */
public class Util {

    public static List<LabeledWord> findParentClasses(Tree tree, LabeledWord labeledWord, Tree root) {

        System.out.println(" findParentClassName  siblings:" + tree.siblings(root));
        List<Tree> siblings = tree.siblings(root);
        for (Tree sibling : siblings) {

            if (DEPUtil.isRelationClass(sibling)) { //TODO maybe find noun at once
                List<LabeledWord> list = findLabeledWords(sibling, TagType.NOUN);
                if (list != null) {
                    return list;
                }
            }
        }
        return null;
    }



    public static List<LabeledWord> findChildClasses(Tree tree) {

        List<LabeledWord> labeledList = new ArrayList<LabeledWord>();
        List<Tree> children = tree.getChildrenAsList();
        //System.out.println(" findChildClasses children:" + children);
        for (Tree child : children) {

            //System.out.println(" findChildClasses child:" + child);
            //if (POSUtil.isNoun(child)) {
                List<LabeledWord> list = findLabeledWords(child, TagType.NOUN);
                //System.out.println(" findChildClasses list:" + list);
                if (list != null) {
                    labeledList.addAll(list);
                }
            //}
        }
        return labeledList;
    }

    public static LabeledWord findLabeledWord(Tree tree, TagType tagType) {

        System.out.println(" findLabeledWord:" + tree.labeledYield());
        List<LabeledWord> childList = tree.labeledYield();
        for (LabeledWord labeledWord: childList) {

            switch (tagType) {
                case NOUN:
                    System.out.println(" findLabeledWord:" + labeledWord.word());
                    if(POSUtil.isNoun(labeledWord)) {
                        System.out.println(" findLabeledWord NOUN :" + labeledWord.word());
                        return labeledWord;
                    }
                case ADJ:
                    break;
                case VERB:
                    if(POSUtil.isVerb(labeledWord)) {
                        return labeledWord;
                    }
                    //break;
            }
        }
        return null;
    }

    public static List<LabeledWord> findLabeledWords(Tree tree, TagType tagType) {

        List<LabeledWord> list = new ArrayList<LabeledWord>();

        //System.out.println(" findLabeledWords:" + tree.labeledYield());
        List<LabeledWord> childList = tree.labeledYield();
        for (LabeledWord labeledWord: childList) {

            switch (tagType) {
                case NOUN:
                    //System.out.println(" findLabeledWords:" + labeledWord.word());
                    if(POSUtil.isNoun(labeledWord)) {
                        //System.out.println(" findLabeledWords NOUN :" + labeledWord.word());
                        list.add(labeledWord);
                    }
                case ADJ:
                    break;
                case VERB:
                    if(POSUtil.isVerb(labeledWord)) {
                        list.add(labeledWord);
                    }
            }
        }
        return list;
    }
}
