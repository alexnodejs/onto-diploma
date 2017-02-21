import edu.stanford.nlp.ling.IndexedWord;
import legacy.xmi.model.elements.ofGeneralization.*;
import legacy.xmi.model.elements.ofassociation.Association;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.elements.ofmethod.Attribute;
import legacy.xmi.model.elements.ofmethod.Operation;

import java.util.Arrays;

/**
 * Created by svitlanamoiseyenko on 2/21/17.
 */
public class ElementBuilderUtil {

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


    public static Generalization genearlizationElementBuilder (Class child_class,
                                              Class parent_class,
                                              int index)
    {
        System.out.println("======  genearlizationElementBuildergenearlizationElementBuilder child_class =====" + child_class._model_name);
        System.out.println("======  genearlizationElementBuilder parent_class =====" + parent_class._model_name);
        Generalization generalizationModel = new Generalization
                (new GeneralizationChild(
                        new GeneralizationChildClass(child_class._model_id)),
                        new GeneralizationParent(
                                new GeneralizationParentClass(parent_class._model_id)),
                        "gen"+ child_class._model_name + parent_class._model_name + index,
                        null,
                        false);
        //index++;
        child_class.set_generalizableElementGeneralization(generalizationModel._generalization_id);
        //_absME.add(child_class);
        //abslist.add(generalizationModel); //TODO
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
//        if(!Arrays.asList(abslist).contains(association)) {
//            System.out.println("inversigateGraph no assoc class:" + association._model_name);
//            abslist.add(association);
//        }
    }


    /*private static void associationElementBuilder(boolean flag,
                                                         SemanticGraph dep,
                                                         IndexedWord word,
                                                         List<AbstractModelElement> absME,
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

        if(!Arrays.asList(absME).contains(association)) {
            System.out.println("inversigateGraph no assoc class:" + association._model_name);
            absME.add(association);
        }
    }*/
}
