package config;

public final class Constants {
  public static String resourcesDir = "./dump/";
  public static String outputXML = "simpleoutput.xml";
  public static String outputparserfilename = "outputparser";


  public static final String nounSet[] = new String[] {"NN", "NNP", "PRP", "NNS"};
  public static final String adjectiveSet[] = new String[] {"JJ"};
  public static final String mainVerbSet[] = new String[] {"VBP", "VBN", "VBG"};
  public static final String joinVerbSet[] = new String[] {"ADJP", "PP", "SBAR"};
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