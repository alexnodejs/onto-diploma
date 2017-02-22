import java.util.*;

import config.Constants;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import legacy.xmi.model.elements.ofGeneralization.*;
import legacy.xmi.model.elements.ofmethod.Attribute;
import legacy.xmi.model.elements.ofmethod.Operation;
import legacy.xmi.model.root.elements.ModelItem;
import legacy.xmi.root.elements.XMI;


import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import legacy.xmi.model.elements.ofassociation.Association;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.root.elements.AbstractModelElement;


public class ParseManager {

    private StanfordCoreNLP pipeline;
    private List<AbstractModelElement> abslist = new ArrayList<AbstractModelElement>();

    private int generateIndex() {
        int index = abslist.size() > 0 ? abslist.size() + 1 : 0;
        return index;
    }

    private void attachOperation(SemanticGraph dep, IndexedWord attrWord) {
        IndexedWord noun = SearchUtil.getNearestNoun(dep, attrWord);
        System.out.println("==== attachOperation noun ====" + String.valueOf(noun));
        if(noun == null) { return; }
        Class classElement = SearchUtil.getClassElement(noun, SearchType.CLASS_NAME, abslist);
        System.out.println("==== attachOperation classElement ====" + String.valueOf(classElement));
        if(classElement == null) { return; }
        Operation operation = ElementBuilderUtil.operationBuilder(attrWord, classElement, generateIndex());
        System.out.println("==== attachOperation operation ====" + String.valueOf(operation._name));
        if(operation == null) { return; }
        classElement.addOperation(operation);
    }

    private void attachAttribute(SemanticGraph dep, IndexedWord attrWord) {
        IndexedWord noun = SearchUtil.getNearestNoun(dep, attrWord);
        if(noun == null) { return; }
        Class classElement = SearchUtil.getClassElement(noun, SearchType.CLASS_NAME, abslist);
        Attribute attribute = ElementBuilderUtil.attributeBuilder(attrWord, classElement, generateIndex());
        classElement.addAttribute(attribute);
    }




    private void attachClassElement(IndexedWord classWord) {
        Class classElement = ElementBuilderUtil.classElementsBuilder(classWord, generateIndex());
        addElementToClass(classElement);
    }

    private void attachGeneralizationElement(IndexedWord parent, IndexedWord child) {
        System.out.println("==== attachGeneralizationElement parent ====" + parent.word() +" tag: " + parent.tag());
        System.out.println("==== attachGeneralizationElement parent ====" + child.word() +" tag: " + child.tag());

        Class parentClassElement = null;
        Class childClassElement = null;
        if(POSUtil.isVerb(parent)) {
            System.out.println("==== attachGeneralizationElement parent isVerb ====");
            parentClassElement = SearchUtil.getClassElement(parent, SearchType.CLASS_OPERATION, abslist);
        } else if(POSUtil.isAdjective(parent)) {
            System.out.println("==== attachGeneralizationElement parent isVerb ====");
            parentClassElement = SearchUtil.getClassElement(parent, SearchType.CLASS_ATTRIBUTE, abslist);
        }else if(POSUtil.isNoun(child)) {
            System.out.println("==== attachGeneralizationElement parentClassElement noun ");
            parentClassElement = SearchUtil.getClassElement(parent, SearchType.CLASS_NAME, abslist);
        }

        if(POSUtil.isVerb(child)) {
            System.out.println("==== attachGeneralizationElement child isVerb ====");

            childClassElement = SearchUtil.getClassElement(child, SearchType.CLASS_OPERATION, abslist);
        } else if(POSUtil.isAdjective(child)) {
            System.out.println("==== attachGeneralizationElement child isAdjective ");

            childClassElement = SearchUtil.getClassElement(child, SearchType.CLASS_ATTRIBUTE, abslist);
        } else if(POSUtil.isNoun(child)) {
            System.out.println("==== attachGeneralizationElement child noun: " + child.word());
            childClassElement = SearchUtil.getClassElement(child, SearchType.CLASS_NAME, abslist);
        }


        if(parentClassElement == null || childClassElement == null) {
            System.out.println("==== attachGeneralizationElement NULL ==== parent: "
                    + String.valueOf(parentClassElement) + " child:" + String.valueOf(childClassElement));
            //TODO: Find nearest in graph
            return;
        }

         Generalization generalizationElement = ElementBuilderUtil.genearlizationElementBuilder(parentClassElement, childClassElement, generateIndex());
        addGeneralizationToABSList(generalizationElement);
    }

    private void addElementToClass(Class element) {
        if (element == null) {
            return;
        }

        if (!SearchUtil.IsElementExist(abslist, element)) {
            abslist.add(element);
        }
    }

    private void addGeneralizationToABSList(Generalization element) {
        if (element == null) {
            return;
        }

        if (!SearchUtil.IsGeneralizationExist(abslist, element)) {
            abslist.add(element);
        }
    }
    private void addAssociationToABSList(Association element) {
        if (element == null) {
            return;
        }

        if (!SearchUtil.IsAssociationExist(abslist, element)) {
            abslist.add(element);
        }
    }


    private void investigateClassElements(SemanticGraph dep,
                                          IndexedWord word) {

        System.out.println("==== investigateClassElements ====");
        Collection<IndexedWord> childList = dep.getChildList(word);
        if (!childList.isEmpty()) {
            for (IndexedWord child : childList) {

                System.out.println("investigateClassElements child.word():" + child.word());
                System.out.println("investigateClassElements child TAG---" + child.tag());

                //String relationType = dep.getEdge(word, child).getRelation().toString();
                //System.out.println("getAllClasses relationType:" + relationType);

                if (Arrays.asList(Constants.nounPOS).contains(child.tag())) {
                    attachClassElement(child);
                }

                investigateClassElements(dep, child);
            }
        } else {
            System.out.println(" the element " + word.word() + "does not have any child");
        }
    }

    private void investigateOperation(SemanticGraph dep,
                                    IndexedWord word) {


        System.out.println("investigateMethods");
        // Check parent
        if (POSUtil.isVerb(word)) {
            System.out.println("parent create method---" + word.word());
            attachOperation(dep,word);
        }

        // Check child
        Collection<IndexedWord> word_children = dep.getChildList(word);
        if (word_children.isEmpty()) {
            System.out.println("investigateMethods NOT CHILDS: " + word.word());
            return;
        }
        for (IndexedWord child : word_children) {
            String relationType = dep.getEdge(word, child).getRelation().toString();
            System.out.println("investigatePropsAndMethodsGraph relationType:" + relationType);

            if (POSUtil.isVerb(child)) {
                System.out.println("create method---" + word.word() + " child: " + child.word());
                attachOperation(dep, child);
            }
            investigateOperation(dep, child);
        }
    }

    private void investigateAdjectives(SemanticGraph dep,
                                       IndexedWord word) {
        System.out.println("investigatePropsGraph");

        // Check parent
        if (POSUtil.isAdjective(word)) {
            System.out.println("create property--- word" + word.word() + " word: " + word.word());
            attachAttribute(dep, word);
        }

        // Check childs
        Collection<IndexedWord> word_children = dep.getChildList(word);
        if (word_children.isEmpty()) {
            System.out.println("investigatePropsGraph NOT CHILDS: " + word.word());
            return;
        }
        for (IndexedWord child : word_children) {
            String relationType = dep.getEdge(word, child).getRelation().toString();
            System.out.println("investigatePropsGraph relationType:" + relationType);

            if (POSUtil.isAdjective(child)) {
                System.out.println("create property--- word " + word.word() + " child: " + child.word());
                attachAttribute(dep, child);
            }

            investigateAdjectives(dep, child);
        }
    }

    private void investigateClassConnections(SemanticGraph dep,
                                             IndexedWord word) {

        System.out.println("investigateClassConnections");
        Collection<IndexedWord> word_children = dep.getChildList(word);
        if (word_children.isEmpty()) {
            System.out.println("investigateClassConnections NOT CHILDS: " + word.word());
            return;
        }
        for (IndexedWord child : word_children) {

            String relationType = dep.getEdge(word, child).getRelation().toString();
            System.out.println("investigateClassConnections relationType:" + relationType);

            if (DEPUtil.isGeneralization(relationType)) {
                System.out.println(" BUILD RELATION GENERALIZATION---" + word.word() + " child: " + child.word());
                attachGeneralizationElement(word, child);
            }

            investigateClassConnections(dep, child);
        }
    }

    private void investigateRelations(SemanticGraph dep,
                                      IndexedWord word) {

        System.out.println("investigateRelations");
        Collection<IndexedWord> word_children = dep.getChildList(word);
        if (word_children.isEmpty()) {
            System.out.println("investigateRelations NOT CHILDS: " + word.word());
            return;
        }
        for (IndexedWord child : word_children) {

            String relationType = dep.getEdge(word, child).getRelation().toString();
            System.out.println("investigateRelations relationType:" + relationType);

            if (DEPUtil.isModifeierRelation(relationType, child)) {
                System.out.println(" EXTEND SOMETHING---" + word.word() + " child: " + child.word());
                ElementBuilderUtil.modifyAttribute(dep, child, abslist);
            }

            investigateRelations(dep, child);
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
        System.out.print("pipeline annotation" + document);
        XMI xmiStructure = null;

        ParseDocument(document);
        xmiStructure = buiildXMI(abslist);

        //List<AbstractModelElement> abstractModelElementList = ParseDocument(document);
        //XMI xmiStructure = buiildXMI(abstractModelElementList);

        return xmiStructure;
    }

    private List<AbstractModelElement> ParseDocument(Annotation document) {

        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            System.out.println(" sentence---" + sentence);
            // this is the parse tree of the current sentence
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            System.out.println("tree---" + tree);

            // this is the Stanford dependency graph of the current sentence
            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
            System.out.println("SemanticGraph---" + dependencies);

            IndexedWord firstRoot = dependencies.getFirstRoot();
            System.out.println("firstRoot---" + firstRoot);

            // Build XMI elements
            //Class element = classElementsBuilder(dependencies, firstRoot, null, null, abslist, 0);
            System.out.println("firstRoot TAG---" + firstRoot.tag());
            if (Arrays.asList(Constants.nounPOS).contains(firstRoot.tag())) {
                ElementBuilderUtil.classElementsBuilder(firstRoot, generateIndex());
            }
            //inversigateGraph(dependencies, firstRoot);

            investigateClassElements(dependencies, firstRoot);
            investigateAdjectives(dependencies, firstRoot);
            investigateOperation(dependencies, firstRoot);
            investigateClassConnections(dependencies, firstRoot);
            investigateRelations(dependencies, firstRoot);
        }
        return abslist;
    }

    private XMI buiildXMI(List<AbstractModelElement> abstractModelElementList) {

        XMI xmiStructure;
        ModelItem _xmi_modelItem = new ModelItem();

        System.out.println("buiildXMI!!!!! " + abstractModelElementList.size());
        for (AbstractModelElement modelElement : abstractModelElementList) {
            if (modelElement instanceof Class) {
                _xmi_modelItem._classList.add((Class) modelElement);
                System.out.println(" XMI Class: " + modelElement._model_name);
            } else if (modelElement instanceof Association) {
                _xmi_modelItem._associationList.add((Association) modelElement);
                System.out.println(" XMI Assoc " + modelElement._model_name);
            } else if (modelElement instanceof Generalization) {
                _xmi_modelItem._generalizationList.add((Generalization) modelElement);
                System.out.println(" XMI Generalization " + modelElement._model_name);
            }

        }
        xmiStructure = new XMI(_xmi_modelItem);
        return xmiStructure;
    }
}





   /* private static void inversigateGraph(SemanticGraph dep,
                                IndexedWord word,
                                Class parentElement,
                                List<AbstractModelElement> absME) {

        System.out.println("inversigateGraph");
        Collection<IndexedWord> word_children = dep.getChildList(word);

        for (IndexedWord child : word_children) {

            System.out.println("inversigateGraph IndexedWord:" + dep.getEdge(word, child).getRelation().toString());
            System.out.println("inversigateGraph child.word():" + child.word());
            System.out.println("inversigateGraph word.word():" + word.word());

            String relationType = dep.getEdge(word, child).getRelation().toString();
            System.out.println("inversigateGraph relationType:" + relationType);

            System.out.println("inversigateGraph child TAG---" + child.tag());
            System.out.println("inversigateGraph word TAG---" + word.tag());


            if (Arrays.asList(Constants.relationsAssociationSet).contains(relationType)) {
                    Class childElement = classElementsBuilder(dep, child, null, parentElement, absME, 0);
                    associationElementBuilder(false, dep, child, absME, parentElement, childElement, 0);

            } else if (Arrays.asList(Constants.relationsGeneralizationSet).contains(relationType)) {
                Class childElement = classElementsBuilder(dep, child, null, parentElement, absME, 0);
                genearlizationElementBuilder(parentElement, childElement, absME,0);
            }

        }
    }*/


    /* private static Class classElementsBuilder(SemanticGraph dep,
                                              IndexedWord word,
                                              IndexedWord child,
                                              Class parentElement,
                                              List<AbstractModelElement> absME,
                                              int index) {

        String className = (Character.toUpperCase(word.originalText().charAt(0))) + word.originalText().substring(1);
        System.out.println("=== classElementsBuilder === " + className);
        Class element = new Class(className + "_ClassID" + index, className);

        if (element == null) { return null; }

        if(!IsElementExist(absME, element)){
            absME.add(element);
        }

        if (child != null ) {
            inversigateGraph(dep, child, parentElement, absME);
        }

        return element;
    }



    private void inversigateClassElements(SemanticGraph dep,
                                         List<IndexedWord> classElements) {

        System.out.println("inversigateClassElements");


        for (IndexedWord element : classElements) {

            Class absElement = classElementsBuilder(element, 0);
            Collection<IndexedWord> word_children = dep.getChildList(element);

            for (IndexedWord child : word_children) {

                System.out.println("inversigateClassElements IndexedWord:" + dep.getEdge(element, child).getRelation().toString());
                System.out.println("inversigateClassElements child.word():" + child.word());
                System.out.println("inversigateClassElements word.word():" + element.word());

                String relationType = dep.getEdge(element, child).getRelation().toString();
                System.out.println("inversigateClassElements relationType:" + relationType);

                System.out.println("inversigateClassElements child TAG---" + child.tag());
                System.out.println("inversigateClassElements element TAG---" + element.tag());

                if (Arrays.asList(Constants.associationRelationSet).contains(relationType)) {
                    Class absChildElement = classElementsBuilder(child,0);
                    associationElementBuilder(child, absElement, absChildElement, 0);
                }

//             if (Arrays.asList(Constants.associationRelationSet).contains(relationType)) {
//                Class absChildElement = classElementsBuilder(child,0);
//                associationElementBuilder(child, absElement, absChildElement, 0);
//             }
             //build property

             //else if (Arrays.asList(Constants.relationsGeneralizationSet).contains(relationType)) {
//                Class childElement = classElementsBuilder(dep, child, null, parentElement, absME, 0);
//                genearlizationElementBuilder(parentElement, childElement, absME,0);
//            }

            }
        }
    }

//            if (Arrays.asList(Constants.associationRelationSet).contains(relationType)) {
//
//            }

//            Class childElement = null;
//            if(Arrays.asList(Constants.nounPOS).contains(child.tag())) {
//                childElement = classElementsBuilder(child);
//            }
//
//            if (Arrays.asList(Constants.methodRelationSet).contains(relationType)) {
//                //add method to childElement
//            }
//
//            if(Arrays.asList(Constants.adjectivePOS).contains(child.tag())) {
//                childElement = classElementsBuilder(child);
//            }

                //associationElementBuilder(false, dep, child, absME, parentElement, childElement, 0);
            //}
 //           else if (Arrays.asList(Constants.relationsGeneralizationSet).contains(relationType)) {
//                Class childElement = classElementsBuilder(dep, child, null, parentElement, absME, 0);
//                genearlizationElementBuilder(parentElement, childElement, absME,0);
//            }




//    private void getAllVerbsElements(SemanticGraph dep,
//                                     IndexedWord word,
//                                     List<IndexedWord> classElements) {
//        System.out.println("====getAllVerbsElements ====");
//
//
//        Collection<IndexedWord> childList = dep.getChildList(word);
//        if (!childList.isEmpty()) {
//            for (IndexedWord child : childList) {
//
//                System.out.println("getAllVerbsElements child.word():" + child.word());
//                System.out.println("getAllVerbsElements child TAG---" + child.tag());
//                //String relationType = dep.getEdge(word, child).getRelation().toString();
//                //System.out.println("getAllClasses relationType:" + relationType);
//
//
//                if (Arrays.asList(Constants.verbPOS).contains(child.tag())) {
//                    verbsElements.add(child);
//                    //TODO: get relations and add method to class
//                }
//
//                getAllVerbsElements(dep, child, classElements);
//
//            }
//        } else {
//            System.out.println(" the element " + word.word() + "does not have any child");
//        }
//    }


    */
