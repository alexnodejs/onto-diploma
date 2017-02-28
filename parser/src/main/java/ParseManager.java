
import config.Constants;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.LabeledWord;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotator;
import edu.stanford.nlp.naturalli.NaturalLogicRelation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import legacy.xmi.model.elements.ofGeneralization.Generalization;
import legacy.xmi.model.elements.ofassociation.Association;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.root.elements.AbstractModelElement;
import legacy.xmi.model.root.elements.ModelItem;
import legacy.xmi.root.elements.XMI;
import java.util.*;

import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;



/**
 * Created by svitlanamoiseyenko on 2/23/17.
 */
public class ParseManager {

    private StanfordCoreNLP pipeline;
    private List<AbstractModelElement> abslist = new ArrayList<AbstractModelElement>();

    private int generateIndex() {
        int index = abslist.size() > 0 ? abslist.size() + 1 : 0;
        return index;
    }

    private void addElementToClass(Class element) {
        if (element == null) {
            return;
        }

        if (!SearchUtil.IsElementExist(abslist, element)) {
            abslist.add(element);
        }
    }


    private void attachClassElement(String name) {
        Class classElement = ElementBuilderUtil.classElementsBuilder(name, generateIndex());
        addElementToClass(classElement);
    }


    private void attachAssociation(LabeledWord labeledWord, List<LabeledWord> parents, List<LabeledWord> children) {

        for (LabeledWord parent: parents) {
            Class parentClass =  SearchUtil.getClassElement(parent, SearchType.CLASS_NAME, abslist);

            for (LabeledWord child: children) {
                Class childClass =  SearchUtil.getClassElement(child, SearchType.CLASS_NAME, abslist);

                Association associationElemet = ElementBuilderUtil.associationElementBuilder(labeledWord, parentClass, childClass, generateIndex());
                if (!SearchUtil.IsAssociationExist(abslist, associationElemet)) {
                    abslist.add(associationElemet);
                }
            }
        }
    }

    private void attachAggregation(List<LabeledWord> parents, List<LabeledWord> children, String parentEndName) {

        for (LabeledWord parent: parents) {
            Class parentClass =  SearchUtil.getClassElement(parent, SearchType.CLASS_NAME, abslist);

            for (LabeledWord child: children) {
                Class childClass =  SearchUtil.getClassElement(child, SearchType.CLASS_NAME, abslist);

                if(parentClass != null && childClass != null) {
                    Association associationElemet = ElementBuilderUtil.aggregationElementBuilder("", parentClass, childClass, parentEndName, "", generateIndex());
                    if (!SearchUtil.IsAssociationExist(abslist, associationElemet)) {
                        abslist.add(associationElemet);
                    }
                }
            }
        }
    }

    private void attachGeneralization(List<LabeledWord> parents, List<LabeledWord> children) {//LabeledWord labeledWord,

        for (LabeledWord parent: parents) {
            Class parentClass =  SearchUtil.getClassElement(parent, SearchType.CLASS_NAME, abslist);

            for (LabeledWord child: children) {
                Class childClass =  SearchUtil.getClassElement(child, SearchType.CLASS_NAME, abslist);

                Generalization generalizationElement = ElementBuilderUtil.genearlizationElementBuilder(parentClass, childClass, generateIndex());

                if (!SearchUtil.IsGeneralizationExist(abslist, generalizationElement)) {
                 abslist.add(generalizationElement);
                }
            }
        }
    }


    // NOUNS
    private void investigateClassElements(Tree tree) {
        String result = null;
        List<LabeledWord> childListYields = tree.labeledYield();
         for (LabeledWord labeledWord: childListYields) {
              if(WordUtil.isNounNNTag(labeledWord)) {
                     if (result != null) {
                             result += " " + labeledWord.word().toString();
                     } else {
                             result = labeledWord.word().toString();
                     }
               } else if (WordUtil.isOtherNounTag(labeledWord)) {
                          attachClassElement(labeledWord.word().toString());
               }
         }
         if (result != null) {
                     attachClassElement(result);
         }
    }

    private void investigateNPTags(Tree tree) {

        List<Tree> childList = tree.getChildrenAsList();
        for (Tree childTree : childList) {
            if (PhraseUtil.isRelationNP(childTree)) {
                if(!Util.isHasTheSameTagChilds(childTree, "NP")) {
                    System.out.println("  investigateNounTags:" + childTree);
                    investigateClassElements(childTree);
                }
            }
            investigateNPTags(childTree);
        }
    }

    //RELATIONS
    //Base VP tag
    private void investigateVPRelationTags(Tree phraseTree, Tree root) {

        List<Tree> childList = phraseTree.getChildrenAsList();
        for (Tree childTree : childList) {
            if (PhraseUtil.isRelationTag(childTree)) {
                System.out.println("  isRelationVerbPhraseTags:" + childTree.firstChild());

                investigatePhraseRelationTags(childTree,root);
            }


          investigateVPRelationTags(childTree, root);
        }

    }

    // Other relations tags
    private void investigatePhraseRelationTags(Tree phraseTree, Tree root) {

        List<Tree> childList = phraseTree.getChildrenAsList();
        for (Tree childTree : childList) {


            if (PhraseUtil.isRelationPrepositionalPhraseTags(childTree)) {
                System.out.println("  investigatePhraseRelationTags:" + childTree.firstChild());

               //investigateRelationsWordTags(childTree, phraseTree, root);
            } else   {
                List<LabeledWord> childListYields = childTree.labeledYield();
                for (LabeledWord labeledWord: childListYields) {
                    if(WordUtil.isVerbTag(labeledWord) && labeledWord.word().equals("is")) { // && labeledWord.word().equals("is")
                        System.out.println("  VP next :" + labeledWord.word());

                        //TODO: continue
                      List<LabeledWord> parentsList = null;
                      List<LabeledWord> childrenList = null;
                      parentsList = Util.findSiblingClasses(phraseTree, root);
                      //childrenList = Util.findChildClassesForAssociation(phraseTree);
                      //parentsList = Util.findWordLevelTagParentClasses(phraseTree, root);
                      childrenList = Util.findVPChildClasses(phraseTree);
                      System.out.println("  investigateRelationsElements parentsList:" + parentsList);
                      System.out.println("  investigateRelationsElements childrenList:" + childrenList);
                      if (parentsList != null && childrenList != null) {
                       attachAssociation(labeledWord, parentsList, childrenList);
                      }

                    }

                }
            }

//                if (WordUtil.isRelationGeneralization(childTree.firstChild())) { //TO
//                    List<LabeledWord> parentsList = null;
//                    List<LabeledWord> childrenList = null;
//                    System.out.println(" R  isRelationGeneralization  value:" + childTree.value());
//
//                    parentsList = Util.findParentClasses(childTree, root);
//                    childrenList = Util.findChildClassesForisRelationPP(childTree);
//                    System.out.println("  isRelationGeneralization parentsList:" + parentsList);
//                    System.out.println("  isRelationGeneralization childrenList:" + childrenList);
//                    if (parentsList != null && childList != null) {
//                        attachGeneralization(parentsList, childrenList);
//                        //attachAggregation(parentsList, childrenList);
//                    }
//                }


            investigatePhraseRelationTags(childTree, root);
        }

    }

    // Other relations tags
    private void investigateRelationsWordTags(Tree wordLevelTree, Tree phraseLevelTree, Tree root) {

        System.out.println(" R  investigateRelationsWordTags  wordLevelTree:" + wordLevelTree);
        if (WordUtil.isRelationAggregation(wordLevelTree.firstChild())) {
            List<LabeledWord> parentsList = null;
            List<LabeledWord> childrenList = null;
            System.out.println(" R  investigateRelationsWordTags  value:" + wordLevelTree.value());

            parentsList = Util.findWordLevelTagParentClasses(wordLevelTree, root);
            childrenList = Util.findWordLevelTagChildClasses(wordLevelTree);
            System.out.println("  investigateRelationsWordTags parentsList:" + parentsList);
            System.out.println("  investigateRelationsWordTags childrenList:" + childrenList);

            if (parentsList != null && childrenList != null) {
                attachAggregation(parentsList, childrenList, Util.getAssosiationName(wordLevelTree));
            }
         }
    }








    public XMI Processing(String filename)//File _file
    {
        String fileText = FileManager.readFile(filename); //"The director is 65 years old";//FileManager.readFile(filename);

        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma,parse");
        pipeline = new StanfordCoreNLP(props);
        System.out.println("pipeline:  " + pipeline);
        Annotation document;
        document = new Annotation(fileText);
        pipeline.annotate(document);
        System.out.print(" pipeline annotation" + document);
        XMI xmiStructure = null;


        ParseDocument(document);
        xmiStructure = buiildXMI(abslist);

        return xmiStructure;
    }

    private void ParseDocument(Annotation document) {

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {

            // this is the parse tree of the current sentence
            Tree tree = sentence.get(TreeAnnotation.class);

            System.out.println("tree---" + tree);
            investigateNPTags(tree);
            investigateVPRelationTags(tree, tree);

            //sentence.
            //List<CoreMap> phrases = document.get(CoreAnnotations.PhraseWordsAnnotation.class);
                       //CoreAnnotations.PhraseWordsAnnotation
            //investigatePhraseRelationTags(tree, tree);
            //investigateGeneralizationsElements(tree, tree);

            //System.out.println(" print tree---");
            //TreePrint treePrint = new TreePrint("penn,latexTree");
            //treePrint.printTree(tree);
        }
    }

    private XMI buiildXMI(List<AbstractModelElement> abstractModelElementList) {

        XMI xmiStructure;
        ModelItem _xmi_modelItem = new ModelItem();

        System.out.println(" buiildXMI!!!!! " + abstractModelElementList.size());
        for (AbstractModelElement modelElement : abstractModelElementList) {
            if (modelElement instanceof Class) {
                _xmi_modelItem._classList.add((Class) modelElement);
                System.out.println(" XMI Class: " + modelElement._model_name);
            } else if (modelElement instanceof Association) {
                _xmi_modelItem._associationList.add((Association) modelElement);
                System.out.println(" XMI Assoc " + modelElement._model_name);
            } else if (modelElement instanceof Generalization) {
                _xmi_modelItem._generalizationList.add((Generalization) modelElement);
                System.out.println("  XMI Generalization " + modelElement._model_name);
            }

        }
        xmiStructure = new XMI(_xmi_modelItem);
        return xmiStructure;
    }
}


//    private void investigateRelationsElements(Tree tree, Tree root) {
//
//
//        List<Tree> childList = tree.getChildrenAsList();
//        for (Tree childTree : childList) {
//
//            if (DEPUtil.isRelationAssociation(childTree)) { //child VBZ
//                System.out.println(" R   investigateRelationsElements  isRelationAssociation value:" + childTree.value());
//                LabeledWord relationLabeledWord = Util.findLabeledWord(childTree, TagType.VERB);
//                if (relationLabeledWord != null) {
//                    List<LabeledWord> parentsList = null;
//                    List<LabeledWord> childrenList = null;
//                    System.out.println("  VVV relationLabeledWord:" + relationLabeledWord.word());
//                    parentsList = Util.findSiblingClasses(childTree, tree);
//                    childrenList = Util.findChildClassesForAssociation(childTree);
//                    System.out.println("  investigateRelationsElements parentsList:" + parentsList);
//                    System.out.println("  investigateRelationsElements childs:" + childrenList);
//                    if (parentsList != null && childList != null) {
//                        attachAssociation(relationLabeledWord, parentsList, childrenList);
//                    }
//                }
//            }
//
//            if (DEPUtil.isRelationPP(childTree)) {
//                System.out.println("  isRelation2:" + childTree.firstChild());
//                if (DEPUtil.isRelationGeneralization(childTree.firstChild())) { //TO
//                    List<LabeledWord> parentsList = null;
//                    List<LabeledWord> childrenList = null;
//                    System.out.println(" R  isRelationGeneralization  value:" + childTree.value());
//
//                    parentsList = Util.findParentClasses(childTree, root);
//                    childrenList = Util.findChildClassesForisRelationPP(childTree);
//                    System.out.println("  isRelationGeneralization parentsList:" + parentsList);
//                    System.out.println("  isRelationGeneralization childrenList:" + childrenList);
//                    if (parentsList != null && childList != null) {
//                        attachGeneralization(parentsList, childrenList);
//                        //attachAggregation(parentsList, childrenList);
//                    }
//                }
//                if (DEPUtil.isRelationAggregation(childTree.firstChild())) {
//                    List<LabeledWord> parentsList = null;
//                    List<LabeledWord> childrenList = null;
//                    System.out.println(" R  isRelationAggregation  value:" + childTree.value());
//
//                    parentsList = Util.findParentClasses(childTree, root);
//                    childrenList = Util.findChildClassesForisRelationPP(childTree);
//                    System.out.println("  isRelationAggregation parentsList:" + parentsList);
//                    System.out.println("  isRelationAggregation childrenList:" + childrenList);
//                    if (parentsList != null && childList != null) {
//                        attachAggregation(parentsList, childrenList, Util.getAssosiationName(childTree.firstChild()));
//                    }
//                }
//            }
//            investigateRelationsElements(childTree, root);
//        }
//    }


