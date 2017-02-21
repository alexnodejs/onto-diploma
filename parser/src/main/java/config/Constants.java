package config;

public final class Constants {
  public static String resourcesDir = "./dump/";
  public static String outputXML = "simpleoutput.xml";
  public static String outputparserfilename = "outputparser";


  public static final String nounPOS[] = new String[] {"NN", "NNP", "PRP"};
  public static final String verbPOS[] = new String[] {"VBZ"};
  public static final String adjectivePOS[] = new String[] {"JJ"};//, "RB"

  //public static final String propertyRelationSet[] = new String[] {"xcomp" };
  public static final String classMemberRelationSet[] = new String[] {"nsubj","xcomp"};
  public static final String classConnectioRelationSet[] = new String[] {"ccomp"};

  //nn Los Angeles class with 2 names
//  public static final String classSet[] = new String[] {"amod" };
//  public static final String relationsAssociationSet[] = new String[] {"nsubj"};
//  public static final String relationsGeneralizationSet[] = new String[] {"dobj"};
}
