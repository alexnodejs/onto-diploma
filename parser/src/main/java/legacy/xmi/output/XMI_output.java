package legacy.xmi.output;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import legacy.xmi.root.elements.XMI;

public class XMI_output {

	public static void WriteToXMIFile(String _filepath,XMI _xmi)
	{
		
		
		try {
			 
			File file = new File(_filepath);
			JAXBContext jaxbContext = JAXBContext.newInstance(XMI.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	 
			jaxbMarshaller.marshal(_xmi, file);
			jaxbMarshaller.marshal(_xmi, System.out);
	 
		      } catch (JAXBException e) {
			e.printStackTrace();
		      }
	}
	
	
}
