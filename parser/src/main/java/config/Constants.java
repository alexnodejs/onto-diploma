package config;

public final class Constants {
  public static String resourcesDir = "./dump/";
  public static String outputXML = "simpleoutput.xml";
  public static String outputparserfilename = "outputparser";


  public static final String nounPOS[] = new String[] {"NN", "NNP", "PRP", "NNS"};
  public static final String verbPOS[] = new String[] {"VBZ"};
  public static final String adjectivePOS[] = new String[] {"JJ", "VBN"};

  public static final String relationsModifierSet[] = new String[] {"advmod", "compound",};
  public static final String relationsGeneralizationSet[] = new String[] {"ccomp", "dobj"};


  //================= New ==========

  public static final String posNounSet[] = new String[] {"NN", "NNP", "PRP", "NNS"};
  public static final String posVerbsSet[] = new String[] {"VBP", "VBZ"}; // VBD maybe adj , "VBD" , "VBG"

  public static final String classSet[] = new String[] {"NP"};

  public static final String relationAssociationSet[] = new String[] {"VP", "V"}; // PP
  public static final String relationGeneralizationSet[] = new String[] {"PP"};
  public static final String relationAggregationSet [] = new String[] {"IN"};
  //public static final String relationCompositionSet[] = new String[] {"IN"};
}


/*
i. Subsumption (Generalization) – a triangular ar
row
ii. Meronymy (part-whole) – a diamond (black or white)
1. Black is for aggregation (all possible parts belong to the same class)
2. White is for composition (different parts of the same whole may have different sense – like Tail and Leg in my example below)
iii. Association (all the rest between a class and a class) – solid line
iiii. Instanciation – (an object is the instance of a class) – a dashed line

 */