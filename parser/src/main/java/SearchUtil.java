import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.LabeledWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import legacy.xmi.model.elements.ofGeneralization.Generalization;
import legacy.xmi.model.elements.ofassociation.Association;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.root.elements.AbstractModelElement;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 2/21/17.
 */
public class SearchUtil {

    public static Class getClassElement(LabeledWord word,
                                        SearchType searchType,
                                        List<AbstractModelElement> abslist) {

        System.out.println("  getClassElement " + word.word());
        //Class classElement = null;
        for (AbstractModelElement element: abslist) {
            if (element instanceof Class) {
                switch (searchType) {

                    case CLASS_ATTRIBUTE:
                        System.out.println("  getClassElement CLASS_ATTRIBUTE CLASS: " + String.valueOf(((Class)element)._model_name));
                        if (((Class)element).getClassAttributeByName(word.word().toLowerCase()) != null) {
                            //  classElement = (Class) element;
                            return  (Class) element;
                        }
                        break;

                    case CLASS_NAME:
                        System.out.println("  getClassElement CLASS_NAME CLASS: " + String.valueOf(((Class)element)._model_name));
                        System.out.println("  getClassElement word: " + String.valueOf(word.word()));
                        if (element._model_name.toLowerCase().equals(word.word().toLowerCase())) {
                            //classElement = (Class) element;
                            return  (Class) element;
                        }

                        break;

                    case CLASS_OPERATION:
                        System.out.println("  getClassElement CLASS_OPERATION CLASS: " + String.valueOf(((Class)element)._model_name));
                        if (((Class)element).getClassOperationByName(word.word().toLowerCase()) != null) {
                            // classElement = (Class) element;
                            return  (Class) element;
                        }
                        break;
                }
            }
        }
        return  null;
    }

    // Old
    public static boolean IsElementExist(List<AbstractModelElement> absME,
                                          Class element) {
        if(!Arrays.asList(absME).contains(element)) {
            return false;
        }
        return true;
    }

    public static boolean IsGeneralizationExist(List<AbstractModelElement> absME,
                                         Generalization element) {
        if(!Arrays.asList(absME).contains(element)) {
            return false;
        }
        return true;
    }

    public static boolean IsAssociationExist(List<AbstractModelElement> absME,
                                                Association element) {
        if(!Arrays.asList(absME).contains(element)) {
            return false;
        }
        return true;
    }

    public static IndexedWord getNearestNoun(SemanticGraph dep, IndexedWord word) {
        System.out.println(" getNearestNoun: " + word.word());
        IndexedWord parent = dep.getParent(word);

        System.out.println("PARENT getNearestNoun: " + parent);
        if (parent != null) {
            if (POSUtil.isNoun(parent)) {
                System.out.println("PARENT getNearestNoun: " + word.word());
                return parent;
            }
        }
        Collection<IndexedWord> word_children = dep.getChildList(word);
        if (word_children.isEmpty()) {
            System.out.println("CHILD getNearestNoun NOT CHILDS: " + word.word());
            return null;
        }
        for (IndexedWord child : word_children) {
            if(POSUtil.isNoun(child)) {
                System.out.println("CHILD getNearestNoun: " + word.word());
                return child;
            }
        }
        return null;
    }

    public static Class getClassElement(IndexedWord word,
                                        SearchType searchType,
                                        List<AbstractModelElement> abslist) {

        System.out.println(" getClassElement " + word.word());
        //Class classElement = null;
        for (AbstractModelElement element: abslist) {
            if (element instanceof Class) {
                switch (searchType) {

                    case CLASS_ATTRIBUTE:
                        System.out.println(" getClassElement CLASS_ATTRIBUTE CLASS: " + String.valueOf(((Class)element)._model_name));

                        if (((Class)element).getClassAttributeByName(word.word().toLowerCase()) != null) {
                          //  classElement = (Class) element;
                            return  (Class) element;
                        }
                        break;

                    case CLASS_NAME:
                        System.out.println(" getClassElement CLASS_NAME CLASS: " + String.valueOf(((Class)element)._model_name));
                        System.out.println(" getClassElement word: " + String.valueOf(word.word()));
                        if (element._model_name.toLowerCase().equals(word.word().toLowerCase())) {
                            //classElement = (Class) element;
                            return  (Class) element;
                        }

                        break;

                    case CLASS_OPERATION:
                        System.out.println(" getClassElement CLASS_OPERATION CLASS: " + String.valueOf(((Class)element)._model_name));
                        if (((Class)element).getClassOperationByName(word.word().toLowerCase()) != null) {
                           // classElement = (Class) element;
                            return  (Class) element;
                        }
                        break;
                }
            }
        }
        return  null;
    }
}
