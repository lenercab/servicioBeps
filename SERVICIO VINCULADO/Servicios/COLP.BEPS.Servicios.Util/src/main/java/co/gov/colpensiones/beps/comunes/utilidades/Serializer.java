package co.gov.colpensiones.beps.comunes.utilidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis.AxisEngine;
import org.apache.axis.MessageContext;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.RPCElement;
import org.apache.axis.message.SOAPEnvelope;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Serializer {

	
	/**
	 * Deserializa un objeto XML y devuelve un objeto.
	 * @param xml
	 * @param contextPath
	 * @return
	 * @throws JAXBException
	 * @throws ClassNotFoundException
	 */
	public static Object deserializar(String xml, String contextPath)
			throws JAXBException, ClassNotFoundException {
		
			return deserializar(xml, Class.forName(contextPath));
		
	}

	/**
	 * Deserializa un string Xml
	 * @param xml
	 * @param contextClass
	 * @return
	 * @throws JAXBException
	 */
	
	@SuppressWarnings("rawtypes")
	public static Object deserializar(String xml, Class contextClass)
			throws JAXBException {
		Object obj = null;

		JAXBContext context = JAXBContext.newInstance(contextClass);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		obj = unmarshaller.unmarshal(new InputSource(new StringReader(xml)));

		return obj;
	}

	public static String serializar(Object po) throws JAXBException {

		StringWriter stringWriter = new StringWriter();
		
			JAXBContext jc = JAXBContext.newInstance(po.getClass());
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(po, stringWriter);
		

		return stringWriter.toString();
	}
	
	public static String serializar(Object po, boolean  showXmlDeclaration) throws JAXBException {

		StringWriter stringWriter = new StringWriter();
		
			JAXBContext jc = JAXBContext.newInstance(po.getClass());
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty("com.sun.xml.bind.xmlDeclaration",showXmlDeclaration? Boolean.TRUE: Boolean.FALSE);
			m.marshal(po, stringWriter);
		

		return stringWriter.toString();
		
		
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object deserializeAxisObject(String xmlInput, Class javaType)
			throws Exception {
		try {
			Method m = javaType.getMethod("getTypeDesc");
			org.apache.axis.description.TypeDesc typeDesc = (org.apache.axis.description.TypeDesc) m
					.invoke(javaType.newInstance());

			// add the SOAP envelope since we we aren't expecting a SOAP object
			// String soap =
			// "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:per=\"http://www.colpensiones.gov.co/contracts/1.0/personas\" xmlns:per1=\"http://www.colpensiones.gov.co/schemas/1.0/personas\" xmlns:com=\"http://www.colpensiones.gov.co/schemas/1.0/comun\" xmlns:tram=\"http://www.colpensiones.gov.co/schemas/1.0/comun/tramites\">";
			String temp = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" ><soapenv:Header/><soapenv:Body>"
					+ xmlInput + "</soapenv:Body></soapenv:Envelope>";
			org.apache.axis.server.AxisServer server = new org.apache.axis.server.AxisServer();
			server.setOption(AxisEngine.PROP_DOMULTIREFS, true);
			MessageContext msgContext = new MessageContext(server);
			java.io.Reader reader = new java.io.StringReader(temp);
			DeserializationContext dser = new DeserializationContext(
					new org.xml.sax.InputSource(reader), msgContext,
					org.apache.axis.Message.REQUEST);
			dser.parse();
			
			
			SOAPEnvelope env = dser.getEnvelope();
			RPCElement rpcElem = (RPCElement) env.getFirstBody();
			
			MessageElement struct = rpcElem.getRealElement();
			Object result = struct.getValueAsType(typeDesc.getXmlType(),
					javaType);
			return result;
		} catch (Exception e) {
			throw new Exception("Could not deserialize the XML object:"
					+ e.getMessage());
		}
	}
	

	/**
	 * Serializa un objeto y retorna un XML
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String serializeAxisObject(Object object) throws Exception {

		try {

			Method m = object.getClass().getMethod("getTypeDesc");
			TypeDesc typeDesc = (TypeDesc) m.invoke(object);
			MessageElement me = new MessageElement(typeDesc.getXmlType(),
					object);
			String xml = me.getAsString();
			return xml;

		} catch (Exception e) {
			throw new Exception("Could not serialize the object:"
					+ e.getMessage());
		}
	}

	/**
	 * Leer un archivo como XML y devuelve el contenido
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	
	public static String leerArchivo(String filePath) throws IOException {
		InputStream in = Serializer.class.getClassLoader().getResourceAsStream(
				filePath);
		
		String fileData = convertStreamToString(in);
		
		return fileData;
	}
	
	/**
	 * Carga el stream de un archivo XML
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	
	public static InputStream leerStreamArchivo(String filePath) throws IOException {
		InputStream in = Serializer.class.getClassLoader().getResourceAsStream(
				filePath);
		
		return in;
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	
	public static URI obtenerURI(String filePath) throws IOException, URISyntaxException {
		
		return Serializer.class.getClassLoader().getResource(filePath).toURI();
	}

	/**
	 * Convierte un stream a string
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	
	public static String convertStreamToString(InputStream is)throws IOException {

		if (is != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}

	}
	
	/**
	 * Construye un documento a partir de un XML
	 * @param xml
	 * @return
	 */
	
	public static Document buildDocument(String xml) {

		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(
							new InputSource(new StringReader(xml)));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return document;
	}

	/**
	 * Extrae el XML a partir de un documento
	 * @param doc
	 * @return
	 */
	
	public static String outerXml(Document doc) {
		StringWriter sw = new StringWriter();
		try {
			TransformerFactory.newInstance().newTransformer().transform(
					new DOMSource(doc), new StreamResult(sw));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return sw.toString();
	}

	/**
	 * Construye un documento a partir de un ruta de archivo
	 * @param ruta
	 * @return
	 */
	
	public static Document buildDocumentFromPath(String ruta) {

		Document xmlInputStream = null;
		try {
			xmlInputStream = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(new File(ruta));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return xmlInputStream;
	}

	/**
	 * Crea un nuevo objeto de documento 
	 * @return
	 */
	
	public static Document createDocument() {

		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return document;
	}

	public static void writeObjectToFile(Object o, String path) throws Exception{
		BufferedWriter out = new BufferedWriter(new FileWriter(path));
		out.write(serializar(o));
		out.close();			
	}
	
	

}
