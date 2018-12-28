
package co.gov.colpensiones.beps.contracts._1_0.personas;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContexto;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDocumentoPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionBasicaSolicitante;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionGeneralVinculado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaInformacionSolicitanteDTO;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.gov.colpensiones.beps.contracts._1_0.personas package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Resultado_QNAME = new QName("http://www.colpensiones.gov.co/beps/contracts/1.0/personas", "resultado");
    private final static QName _InformacionVinculado_QNAME = new QName("http://www.colpensiones.gov.co/beps/contracts/1.0/personas", "informacionVinculado");
    private final static QName _InformacionSolicitante_QNAME = new QName("http://www.colpensiones.gov.co/beps/contracts/1.0/personas", "InformacionSolicitante");
    private final static QName _Identificacion_QNAME = new QName("http://www.colpensiones.gov.co/beps/contracts/1.0/personas", "identificacion");
    private final static QName _TipoRespuestaSolicitanteDTO_QNAME = new QName("http://www.colpensiones.gov.co/beps/contracts/1.0/personas", "tipoRespuestaSolicitanteDTO");
    private final static QName _InformacionContexto_QNAME = new QName("http://www.colpensiones.gov.co/beps/contracts/1.0/personas/", "InformacionContexto");
    private final static QName _EstadoEjecucion_QNAME = new QName("http://www.colpensiones.gov.co/beps/contracts/1.0/personas/", "EstadoEjecucion");
    private final static QName _InformacionVinculacion_QNAME = new QName("http://www.colpensiones.gov.co/beps/contracts/1.0/personas/", "InformacionVinculacion");

    
    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.gov.colpensiones.beps.contracts._1_0.personas
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoEstadoEjecucion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas", name = "resultado")
    public JAXBElement<TipoEstadoEjecucion> createResultado(TipoEstadoEjecucion value) {
        return new JAXBElement<TipoEstadoEjecucion>(_Resultado_QNAME, TipoEstadoEjecucion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoInformacionBasicaSolicitante }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas", name = "informacionVinculado")
    public JAXBElement<TipoInformacionBasicaSolicitante> createInformacionVinculado(TipoInformacionBasicaSolicitante value) {
        return new JAXBElement<TipoInformacionBasicaSolicitante>(_InformacionVinculado_QNAME, TipoInformacionBasicaSolicitante.class, null, value);
    }
    
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoEstadoEjecucion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas", name = "InformacionSolicitante")
    public JAXBElement<TipoEstadoEjecucion> createInformacionSolicitante(TipoEstadoEjecucion value) {
        return new JAXBElement<TipoEstadoEjecucion>(_InformacionSolicitante_QNAME, TipoEstadoEjecucion.class, null, value);
    }
    
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoInformacionContexto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/", name = "InformacionContexto")
    public JAXBElement<TipoInformacionContexto> createInformacionContexto(TipoInformacionContexto value) {
        return new JAXBElement<TipoInformacionContexto>(_InformacionContexto_QNAME, TipoInformacionContexto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoEstadoEjecucion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/", name = "EstadoEjecucion")
    public JAXBElement<TipoEstadoEjecucion> createEstadoEjecucion(TipoEstadoEjecucion value) {
        return new JAXBElement<TipoEstadoEjecucion>(_EstadoEjecucion_QNAME, TipoEstadoEjecucion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoInformacionGeneralVinculado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/", name = "InformacionVinculacion")
    public JAXBElement<TipoInformacionGeneralVinculado> createInformacionVinculacion(TipoInformacionGeneralVinculado value) {
        return new JAXBElement<TipoInformacionGeneralVinculado>(_InformacionVinculacion_QNAME, TipoInformacionGeneralVinculado.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoDocumentoPersonaNatural }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas", name = "identificacion")
    public JAXBElement<TipoDocumentoPersonaNatural> createIdentificacion(TipoDocumentoPersonaNatural value) {
        return new JAXBElement<TipoDocumentoPersonaNatural>(_Identificacion_QNAME, TipoDocumentoPersonaNatural.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoRespuestaInformacionSolicitanteDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas", name = "tipoRespuestaSolicitanteDTO")
    public JAXBElement<TipoRespuestaInformacionSolicitanteDTO> createTipoRespuestaSolicitanteDTO(TipoRespuestaInformacionSolicitanteDTO value) {
        return new JAXBElement<TipoRespuestaInformacionSolicitanteDTO>(_TipoRespuestaSolicitanteDTO_QNAME, TipoRespuestaInformacionSolicitanteDTO.class, null, value);
    }
}
