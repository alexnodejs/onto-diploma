import java.io.File;

import legacy.xmi.output.XMI_output;
import legacy.xmi.root.elements.XMI;

import config.*;
import legacy.TextProcessor;

public class Main {

    public static void main(String[] args) {
        //TextParser textparser = new TextParser();
        //String text = FileManager.readFile("input.txt");
        //"Stanford University is located in California. It is a great university, founded in 1891.";
        //textparser.parseText(text);

       /* File dir = new File(Constants.resourcesDir);
        if (dir.exists()) {
            System.out.println("A folder with name 'new folder' is already exist in the path " + Constants.resourcesDir);
        } else {
            dir.mkdir();
        }
        XMI p = TextProcessor.Processing();//new XMI(file);
        XMI_output.WriteToXMIFile(Constants.resourcesDir + "outputparser" + ".xmi", p);*/

        ParseManager parseManager = new ParseManager();
        XMI xmi = parseManager.Processing("13.txt"); // 4 -? , 9
        XMI_output.WriteToXMIFile(Constants.resourcesDir + "test_outputparser" + ".xmi", xmi);
    }


}
