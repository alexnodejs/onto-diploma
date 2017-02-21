import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.root.elements.AbstractModelElement;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 2/21/17.
 */
public class SearchUtil {


    public static boolean IsElementExist(List<AbstractModelElement> absME,
                                         Class element) {
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
        Class classElement = null;
        for (AbstractModelElement element: abslist) {
                switch (searchType) {

                    case CLASS_ATTRIBUTE:
                        break;

                    case CLASS_NAME:
                        if (element instanceof Class) {
                            if (element._model_name.equals(word.word())) {
                                classElement = (Class) element;
                            }
                        }
                        break;

                    case CLASS_OPERATION:
                        break;
            }
        }
        return  classElement;
    }
}
