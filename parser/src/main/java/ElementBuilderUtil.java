import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.LabeledWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import legacy.xmi.model.elements.ofGeneralization.*;
import legacy.xmi.model.elements.ofassociation.Association;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.elements.ofmethod.Attribute;
import legacy.xmi.model.elements.ofmethod.Operation;
import legacy.xmi.model.root.elements.AbstractModelElement;

import java.util.Arrays;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 2/21/17.
 */
public class ElementBuilderUtil {

    public static Class classElementsBuilder(String word, int index) {
        String className = (Character.toUpperCase(word.charAt(0))) + word.substring(1);
        System.out.println("=== classElementsBuilder === " + className);
        Class element = new Class(className + "_ClassID" + index, className);


        if (element == null) { return null; }

        return element;
    }


//    public static Class classElementsBuilder(String word, int index) {
//        String className = (Character.toUpperCase(word.charAt(0))) + word.substring(1);
//        System.out.println("=== classElementsBuilder === " + className);
//        Class element = new Class(className + "_ClassID" + index, className);
//
//        if (element == null) { return null; }
//
//        return element;
//    }

    public static Association associationElementBuilder(LabeledWord word,
                                                 Class parent,
                                                 Class child,
                                                 int index) {

        System.out.println("== associationElementBuilder ===");
        String assoc_name = word.word();
        Association association = null;

        //1
        association = new Association(assoc_name + "_AssociationID" + index,
                assoc_name,
                parent._model_id,
                child._model_id,
                index);

        return association;
    }

    public static Association aggregationElementBuilder(String name,
                                                        Class parent,
                                                        Class child,
                                                        String parentEndName,
                                                        String childEndName,
                                                        int index) {

        System.out.println("== associationElementBuilder ===");
        System.out.println("== associationElementBuilder parent ===" + parent);
        System.out.println("== associationElementBuilder child ===" + child);
        System.out.println("== associationElementBuilder parentEndName ===" + parentEndName);
        System.out.println("== associationElementBuilder childEndName ===" + childEndName);
        String assoc_name = name;
        Association association = null;

        //1
        association = new Association(assoc_name + "_AssociationID" + index,
                assoc_name,
                parent._model_id,
                child._model_id,
                "aggregate",
                "none",
                parentEndName,
                "",
                index);

        return association;
    }

    public static Association compositionElementBuilder(String name,
                                                        Class parent,
                                                        Class child,
                                                        int index) {

        System.out.println("== associationElementBuilder ===");
        String assoc_name = name;
        Association association = null;

        //1
        association = new Association(assoc_name + "_AssociationID" + index,
                assoc_name,
                parent._model_id,
                child._model_id,
                "composition",
                "none",
                "",
                "",
                index);

        return association;
    }


    // Old
    public static Class classElementsBuilder(IndexedWord word, int index) {
        String className = (Character.toUpperCase(word.originalText().charAt(0))) + word.originalText().substring(1);
        System.out.println("=== classElementsBuilder === " + className);
        Class element = new Class(className + "_ClassID" + index, className);

        if (element == null) { return null; }

        return element;
    }

    public static  Attribute attributeBuilder(IndexedWord word, Class classElement, int index) {
        String xmiID = word.word() + String.valueOf(index);
        Attribute attribute = new Attribute(xmiID, word.word());
        return attribute;
    }

    public static Operation operationBuilder(IndexedWord word, Class classElement, int index) {
        String xmiID = word.word() + String.valueOf(index);
        Operation operation = new Operation(xmiID, word.word());
        return operation;
    }


    public static Generalization genearlizationElementBuilder (
                                              Class child_class,
                                              Class parent_class,
                                              int index)
    {
       Generalization generalizationModel = new Generalization
                (new GeneralizationChild(
                        new GeneralizationChildClass(child_class._model_id)),
                        new GeneralizationParent(
                                new GeneralizationParentClass(parent_class._model_id)),
                        "gen"+ child_class._model_name + parent_class._model_name + index,
                        null,
                        false);

        child_class.set_generalizableElementGeneralization(generalizationModel._generalization_id);
        return  generalizationModel;
    }

    public Association associationElementBuilder(IndexedWord word,
                                           Class parent,
                                           Class child,
                                           int index)
    {

        System.out.println("== associationElementBuilder ===");
        String assoc_name = word.originalText();
        Association association = null;

        //1
        association = new Association(assoc_name + "_AssociationID" + index,
                assoc_name,
                parent._model_id,
                child._model_id,
                index);

        return association;
    }

}
