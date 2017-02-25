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
  public static final String posVerbsSet[] = new String[] {"VBP"};

  public static final String relationClassSet[] = new String[] {"NP"};
  public static final String relationAssociationSet[] = new String[] {"VP", "V"};
  public static final String relationConnectionSet[] = new String[] {"PP"};
}
