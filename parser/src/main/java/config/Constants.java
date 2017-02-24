package config;

public final class Constants {
  public static String resourcesDir = "./dump/";
  public static String outputXML = "simpleoutput.xml";
  public static String outputparserfilename = "outputparser";


  public static final String nounPOS[] = new String[] {"NN", "NNP", "PRP", "NNS"};
  public static final String verbPOS[] = new String[] {"VBZ"};
  public static final String adjectivePOS[] = new String[] {"JJ", "VBN"};//, "RB"
  //public static final String adjectiveModifierPOS[] = new String[] {"RB"};

  public static final String relationsModifierSet[] = new String[] {"advmod", "compound",};

  //public static final String propertyRelationSet[] = new String[] {"xcomp" };
  //public static final String classMemberRelationSet[] = new String[] {"nsubj","xcomp"};
  public static final String relationsGeneralizationSet[] = new String[] {"ccomp", "dobj"};

  //nn Los Angeles class with 2 names
//  public static final String classSet[] = new String[] {"amod" };
//  public static final String relationsAssociationSet[] = new String[] {"nsubj"};
//  public static final String relationsGeneralizationSet[] = new String[] {"dobj"};

  public static final String nounsPOS[] = new String[] {"NN", "NNP", "PRP", "NNS"};
}
