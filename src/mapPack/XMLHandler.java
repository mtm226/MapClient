package mapPack;

import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class XMLHandler {

	public static ArrayList<String> getLayerNames() throws Exception{
		//Lista johon tallennetaan layerien nimet XML-datasta.
		ArrayList<String> layerNimet = new ArrayList<>();
		
		//XML dokumentin alustus ja lataus.
		Document doc = null;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.parse(new URL("http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetCapabilities").openStream());		

		//Haetaan nodelistaan kaikki elementit joiden nimi on Layer
		NodeList layers = doc.getElementsByTagName("Layer");

		//Kutakin layer-nodea kohti jäsennetään xml-datasta name-noden sisältö listaan
		for(int i = 1; i < layers.getLength(); i++){
			layerNimet.add(layers.item(i).getFirstChild().getNextSibling().getNextSibling().getNextSibling().getTextContent());
		}
		//Palautetaan lista kutsujalle
		return layerNimet;
	}
	
	public static ArrayList<String> getLayerTitles() throws Exception{
		//Lista johon tallennetaan layerien titlet XML-datasta.
		ArrayList<String> layerTitlet = new ArrayList<>();
		
		//XML dokumentin alustus ja lataus.
		Document doc = null;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.parse(new URL("http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetCapabilities").openStream());		

		//Haetaan nodelistaan kaikki elementit joiden nimi on Layer
		NodeList layers = doc.getElementsByTagName("Layer");

		//Kutakin layer-nodea kohti jäsennetään xml-datasta title-noden sisältö listaan
		for(int i = 1; i < layers.getLength(); i++){
			layerTitlet.add(layers.item(i).getFirstChild().getNextSibling().getTextContent());
		}
		//Palautetaan lista kutsujalle
		return layerTitlet;
	}
}
