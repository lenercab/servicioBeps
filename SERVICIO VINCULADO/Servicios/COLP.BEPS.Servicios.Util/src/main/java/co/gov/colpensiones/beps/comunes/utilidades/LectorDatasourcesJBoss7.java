/**
 * 
 */
package co.gov.colpensiones.beps.comunes.utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Caso de Uso:</b> FAB- <br/>
 *
 * @author ddelaroche <ddelaroche@heinsohn.com.co>
 */
public class LectorDatasourcesJBoss7 {
	
	/**
	 * MÃ©todo para obtener los datos de conexiÃ³n a un data source a partir del
	 * archivo de configuraciÃ³n de JBoss, probado en JBoss 7.1.1, no funciona en
	 * versiones anteriores y se desconoce si funcionarÃ¡ en versiones posteriores  
	 *
	 * @param dataSource Nombre con el que aparece el datasource dentro del archivo
	 *        de configuraciÃ³n. Es el nombre corto, por ej.: ColpensionesDS
	 * @param path Ruta donde se encuentra el archivo XML de configuraciÃ³n de
	 *        JBoss que se va a usar, por ejemplo: standalone-full.xml
	 * @return Arreglo con los datos de conexiÃ³n, en el siguiente orden:
	 *         {connection-url, user-name, password}
	 * @throws IOException Si ocurre un error de I/O
	 * @throws SAXException Si ocurre un error al leer el XML
	 * @throws ParserConfigurationException Si ocurre un error al leer el XML
	 */
	public static String[] getConnectionInfo(final String dataSource, final String path)
	throws IOException, SAXException, ParserConfigurationException {

	    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    final DocumentBuilder builder = factory.newDocumentBuilder();

	    try (FileInputStream in = new FileInputStream(new File(path))) {

	    	final Document document = builder.parse(in);
		    Node node = getNodeByName(document.getDocumentElement(), "profile");
		    node = getNodeByAttribute(node, "subsystem", "xmlns", "urn:jboss:domain:datasources:1.0");
		    node = getNodeByName(node, "datasources");
		    node = getNodeByAttribute(node, "datasource", "jndi-name", "java:jboss/datasources/" + dataSource);
	
		    final Node urlNode = getNodeByName(node, "connection-url");
		    final String url = urlNode.getTextContent().trim();
	
		    node = getNodeByName(node, "security");
		    final Node usrNode = getNodeByName(node, "user-name");
		    final String usr = usrNode.getTextContent().trim();
		    final Node pwdNode  = getNodeByName(node, "password");
		    final String pwd = pwdNode.getTextContent();
	
		    return new String[] {url, usr, pwd};

	    }

	}

	/**
	 * Busca un nodo dentro de los hijos de otro nodo, dado el nombre
	 * del nodo hijo buscado
	 *
	 * @param parent Nodo padre sobre el que se estÃ¡ buscando
	 * @param name Nombre del nodo hijo que se estÃ¡ buscando
	 * @return Nodo hijo que se estaba buscando, o null si no lo encontrÃ³
	 */
	private static Node getNodeByName(Node parent, String name) {
		NodeList nodeList = parent.getChildNodes();
		for (int i = 0, n = nodeList.getLength(); i < n; ++i) {
			Node node = nodeList.item(i);
			if (node instanceof Element
				&& node.getNodeName().equals(name))
				return node;
		}
		return null;
	}

	/**
	 * Busca un nodo dentro de los hijos de otro nodo, dados el nombre,
	 * atributo y valor del nodo hijo buscado
	 *
	 * @param parent Nodo padre sobre el que se estÃ¡ buscando
	 * @param name Nombre del nodo hijo que se estÃ¡ buscando
	 * @param attr Nombre de un atributo dentro del nodo hijo que se estÃ¡ buscando
	 * @param value Valor del atributo dentro del nodo hijo que se estÃ¡ buscando
	 * @return Nodo hijo que se estaba buscando, o null si no lo encontrÃ³
	 */
	private static Node getNodeByAttribute(Node parent, String name, String attr, String value) {
		NodeList nodeList = parent.getChildNodes();
		for (int i = 0, n = nodeList.getLength(); i < n; ++i) {
			Node node = nodeList.item(i);
			if (node instanceof Element
				&& node.getNodeName().equals(name)
				&& ((Element) node).getAttribute(attr).equals(value))
				return node;
		}
		return null;
	}

}
