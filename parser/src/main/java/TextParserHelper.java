import legacy.xmi.model.root.elements.AbstractModelElement;

import java.util.Arrays;
import java.util.List;
import legacy.xmi.model.elements.ofclass.Class;
/**
 * Created by svitlanamoiseyenko on 2/28/17.
 */
public class TextParserHelper {

    public static int generateIndex(List<AbstractModelElement> abstractModelElements)
    {
        int index = abstractModelElements.size() > 0 ? abstractModelElements.size() + 1 : 0;
        return index;
    }

    public static boolean isElementExist(List<AbstractModelElement> abstractModelElements,
                                         Class element) {
        if(!Arrays.asList(abstractModelElements).contains(element)) {
            return false;
        }
        return true;
    }

    public static void addElementToClass(Class element, List<AbstractModelElement> abstractModelElements)
    {
        if (element == null) {
            return;
        }

        if (! isElementExist(abstractModelElements, element)) {
            abstractModelElements.add(element);
        }
    }

}
