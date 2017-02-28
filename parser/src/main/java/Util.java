import edu.stanford.nlp.ling.LabeledWord;
import edu.stanford.nlp.trees.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 2/25/17.
 */
public class Util {

    public static List<LabeledWord> findSiblingClasses(Tree tree, Tree root) {

        System.out.println(" findSiblingClasses  siblings:" + tree.siblings(root));
        List<Tree> siblings = tree.siblings(root);
        for (Tree sibling : siblings) {

            if (PhraseUtil.isRelationNP(sibling)) { //TODO maybe find noun at once
                List<LabeledWord> list = findLabeledWords(sibling, TagType.NOUN_ALL);
                if (list != null) {
                    return list;
                }
            }
        }
        return null;
    }

    public static boolean ifHasPhrases(Tree tree) {


        List<Tree> children = tree.getChildrenAsList();
        System.out.println("findVPChildClasses" + children);
        for (Tree child : children) {
            if(child.isPhrasal()) {
                return true;
            }
        }
        return false;

    }

    public static List<LabeledWord> findVPChildClasses(Tree tree) {
        System.out.println("findVPChildClasses tree" + tree);
        List<LabeledWord> labeledList = new ArrayList<LabeledWord>();
        List<Tree> children = tree.getChildrenAsList();
        System.out.println("findVPChildClasses" + children);
        for (Tree child : children) {

            //if (child.value().equals("NP")) {

              if (PhraseUtil.isRelationNP(child)) {
                   System.out.println(" ============= ");
                   System.out.println(" findVPChildClasses child: " + child);
                   System.out.println("find IS NP depth: " + child.depth());
                   System.out.println("find IS NP leaf: " + child.isLeaf());
                   System.out.println("find IS NP phrase: " + child.isPhrasal());
                   if (ifHasPhrases(child)) {
                       findVPChildClasses(child);
                   } else {
                       List<LabeledWord> list = findLabeledWords(child, TagType.NOUN_NN);

                      System.out.println("find IS LIST" + list);
                      if (list != null) {
                          labeledList.addAll(list);
                      }
                   }
             }
        }
        System.out.println("find IS NP labeledList: " + labeledList);
        return labeledList;
    }


//    public static List<LabeledWord> findChildClasses(Tree tree) {
//
//        List<LabeledWord> labeledList = new ArrayList<LabeledWord>();
//        List<Tree> children = tree.getChildrenAsList();
//        //System.out.println(" findChildClasses children:" + children);
//        for (Tree child : children) {
//
//            //System.out.println(" findChildClasses child:" + child);
//            //if (POSUtil.isNoun(child)) {
//                List<LabeledWord> list = findLabeledWords(child, TagType.NOUN_NN);
//                //System.out.println(" findChildClasses list:" + list);
//                if (list != null) {
//                    labeledList.addAll(list);
//                }
//            //}
//        }
//        return labeledList;
//    }

    public static List<LabeledWord> findChildClassesForAssociation(Tree tree) {

        List<LabeledWord> labeledList = new ArrayList<LabeledWord>();
        List<Tree> children = tree.getChildrenAsList();
        System.out.println("findChildClassesForAssociation" + children);
        for (Tree child : children) {

            System.out.println(" findChildClassesForAssociation child:" + child.value());
            if (!PhraseUtil.isRelationPrepositionalPhraseTags(child)) {
            List<LabeledWord> list = findLabeledWords(child, TagType.NOUN_NN);
               if (list != null) {
                  labeledList.addAll(list);
               }
            }
        }
        return labeledList;
    }



    public static String getAssosiationName(Tree wordLevelTree) {

        List<Tree> leaves = wordLevelTree.getLeaves();
        if(leaves.isEmpty()) {
            return "";
        }
       return leaves.get(0).value().toString();
    }


    public static List<LabeledWord> findWordLevelTagParentClasses(Tree tree, Tree root) {


        Tree parent = tree.parent(root);
        System.out.println(" findWordLevelTagParentClasses parent:" + parent);
        List<Tree> siblings = parent.siblings(root);
        System.out.println(" findWordLevelTagParentClasses siblings:" + siblings);

//      if (siblings == null) {
//        findParentClasses(root, parent);
//      }
        for (Tree sibling : siblings) {
            if (PhraseUtil.isRelationNP(sibling)) {
                System.out.println(" findWordLevelTagParentClasses siblings:" + siblings);
                List<LabeledWord> list = findLabeledWords(sibling, TagType.NOUN_NN);//TODO+CHECK
                if (list != null) {
                    return list;
                }
            }
        }
        return null;
    }


    public static List<LabeledWord> findWordLevelTagChildClasses(Tree tree) {
        System.out.println("findWordLevelTagChildClasses tree" + tree);
        List<LabeledWord> labeledList = new ArrayList<LabeledWord>();
        List<Tree> children = tree.getChildrenAsList();
        System.out.println("findWordLevelTagChildClasses" + children);
        for (Tree child : children) {

            System.out.println(" findWordLevelTagChildClasses child:" + child.value());
            if (!PhraseUtil.isRelationTag(child)) {
                List<LabeledWord> list = findLabeledWords(child, TagType.NOUN_NN);
                if (list != null) {
                    labeledList.addAll(list);
                }
            }
        }
        return labeledList;
    }


    public static boolean isHasTheSameTagChilds(Tree tree, String tag) {
        List<Tree> childList = tree.getChildrenAsList();
        for (Tree childTree : childList) {
           if (tag.equals("NP")) {
               if (PhraseUtil.isRelationNP(childTree)) {
                   return true;
               }
           }
           isHasTheSameTagChilds(childTree, tag);
        }
        return false;
    }

    public static LabeledWord findLabeledWord(Tree tree, TagType tagType) {

        System.out.println(" findLabeledWord:" + tree.labeledYield());
        List<LabeledWord> childList = tree.labeledYield();
        for (LabeledWord labeledWord: childList) {

            switch (tagType) {
                case NOUN_ALL:
                    System.out.println(" findLabeledWord:" + labeledWord.word());
                    if(WordUtil.isNoun(labeledWord)) {
                        System.out.println(" findLabeledWord NOUN :" + labeledWord.word());
                        return labeledWord;
                    }
                case NOUN_NN:
                    System.out.println(" findLabeledWord:" + labeledWord.word());
                    if(WordUtil.isNounNNTag(labeledWord)) {
                        System.out.println(" findLabeledWord NOUN :" + labeledWord.word());
                        return labeledWord;
                    }
                case NOUN_OTHER:
                    System.out.println(" findLabeledWord:" + labeledWord.word());
                    if(WordUtil.isOtherNounTag(labeledWord)) {
                        System.out.println(" findLabeledWord NOUN :" + labeledWord.word());
                        return labeledWord;
                    }
                case ADJ:
                    break;
                case VERB:
                    if(WordUtil.isVerbTag(labeledWord)) {
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
                case NOUN_ALL:
                    //System.out.println(" findLabeledWords:" + labeledWord.word());
                    if(WordUtil.isNoun(labeledWord)) {
                        list.add(labeledWord);
                    }
                    break;
                case NOUN_NN:
                    if(WordUtil.isNounNNTag(labeledWord)) {
                        list.add(labeledWord);
                    }
                    break;
                case NOUN_OTHER:
                    if(WordUtil.isOtherNounTag(labeledWord)) {
                        list.add(labeledWord);
                    }
                    break;
                case ADJ:
                    break;
                case VERB:
                    if(WordUtil.isVerbTag(labeledWord)) {
                        list.add(labeledWord);
                    }
            }
        }
        return list;
    }
}

//    public static String getAssosiationName(Tree tree) {
//
//        List<LabeledWord>  labeledWordList = tree.labeledYield();
//        //System.out.println("  getAssosiatioName tree leaves:" + tree.getLeaves()); // in case if only one
//        for (LabeledWord labeledWord: labeledWordList) {
//            if(labeledWord.tag().toString().equals(tree.value().toString())) {
//                return labeledWord.word();
//            }
//        }
//        return "";
//    }

