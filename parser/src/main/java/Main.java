import legacy.xmi.output.XMI_output;
import legacy.xmi.root.elements.XMI;

import config.*;

public class Main {

    public static void main(String[] args) {

       /* File dir = new File(Constants.resourcesDir);
        if (dir.exists()) {
            System.out.println("A folder with name 'new folder' is already exist in the path " + Constants.resourcesDir);
        } else {
            dir.mkdir();
        }
        XMI p = TextProcessor.Processing();//new XMI(file);
        XMI_output.WriteToXMIFile(Constants.resourcesDir + "outputparser" + ".xmi", p);*/

        //OldParserManager oldParserManager = new OldParserManager();
        //XMI xmi = oldParserManager.Processing("14.txt"); // 4 -? , 9
//      XMI_output.WriteToXMIFile(Constants.resourcesDir + "test_outputparser" + ".xmi", xmi);

        TextParser textParser = new TextParser();
        XMI xmi = textParser.Processing("14.txt");
        //XMI_output.WriteToXMIFile(Constants.resourcesDir + "test_outputparser" + ".xmi", xmi);

    }


}