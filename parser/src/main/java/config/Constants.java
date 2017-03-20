package config;

public final class Constants {
  public static String resourcesDir = "./dump/";
  public static String outputXML = "simpleoutput.xml";
  public static String outputparserfilename = "outputparser";


  public static final String singleNounSet[] = new String[] {"NN", "NNP", "PRP"};
  public static final String pluralNounSet[] = new String[] {"NNS"};

  public static final String adjectiveSet[] = new String[] {"JJ", "CD", "RB"};
  public static final String verbSet[] = new String[] {"VBP", "VBN", "VBG", "IN", "TO", "VBZ", "ADVP", "VB"};
  public static final String joinVerbSet[] = new String[] {"ADJP", "PP", "SBAR"};
  public static final String conjVerbSet[] = new String[] {"CC", ","};

  public static final String aggregationSet[] = new String[] {"IN"};
  public static final String generalizationSet[] = new String[] {"TO"};
  //public static final String aggregationSet[] = new String[] {"IN"}; //with
  //public static final String generalizationSet[] = new String[] {"IN"}; // of

  public static final String generalizationSetWords[] = new String[] {"of"};
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