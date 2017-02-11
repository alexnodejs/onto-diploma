package legacy.xmi.model.root.elements;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import legacy.xmi.model.elements.ofGeneralization.Generalization;
import legacy.xmi.model.elements.ofassociation.Association;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.root.elements.Visibility;

import nu.xom.Serializer;


public class ModelItem implements Serializable{
	@XmlElement(name="Class",namespace="org.omg.xmi.namespace.UML")
	public List<Class> _classList =new ArrayList <Class>() ;
	@XmlElement(name="Association",namespace="org.omg.xmi.namespace.UML")
	public List<Association> _associationList =new ArrayList <Association>() ;
	@XmlElement(name="Generalization",namespace="org.omg.xmi.namespace.UML")
	public List<Generalization> _generalizationList =new ArrayList <Generalization>() ;
	//public List<AbstractModelElement> abslist=new ArrayList<AbstractModelElement>();//=new ArrayList<AbstractModelElement>;
	public ModelItem() {
		//abslist.add(new Class());
	//	if (abslist.get(0) instanceof Class) 
				//_classList.add(new Class("asd1","Class1",false,false,false,false,Visibility.PUBLIC,false));
				//_classList.add(new Class("asd2","Class2",false,false,false,false,Visibility.PUBLIC,false));
				//_associationList.add(new Association("as1","_myAssoc",false,false,false,false, "ass1", "as2", Visibility.PUBLIC, Visibility.PUBLIC, 
					//	false, false, false, false, "unordered", "unordered", "none", "none", "instance", "instance", "changeable", "changeable", "asd1", "asd2"));
		//TextProcessor.Processing();
		//List<String> _ClassesNames=TextProcessor.Processing(_file);
		/*int j=0;
		for (String NN_word:_ClassesNames)
			{
			_classList.add(new Class(NN_word+j,NN_word,false,false,false,false,Visibility.PUBLIC,false));
			j++;
			}
		for (int iter=0;iter<_ClassesNames.size()-1;iter++)
			_associationList.add(new Association("as_id"+(iter+1),"_myAssoc"+(iter+1),false,false,false,false, 
					"assocEnd"+((iter+1)*2-1), "assocEnd"+((iter+1)*2), Visibility.PUBLIC, Visibility.PUBLIC, 
					false, false, false, false, "unordered", "unordered", "none", "none", 
					"instance", "instance", "changeable", "changeable", 
					_ClassesNames.get(iter).toString(),_ClassesNames.get(iter+1).toString()));
		*/
		//System.out.print(_ClassesNames.get(iter));
	
	}
}
