
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstado;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.gov.colpensiones.beps.schemas._1_0.personas package. 
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

    private final static QName _TipoInformacionBasicaSisbenNivel_QNAME = new QName("http://www.colpensiones.gov.co/beps/schemas/1.0/personas", "nivel");
    private final static QName _TipoInformacionBasicaSolicitanteNombresApellidos_QNAME = new QName("http://www.colpensiones.gov.co/beps/schemas/1.0/personas", "nombresApellidos");


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.gov.colpensiones.beps.schemas._1_0.personas
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TipoRespuestaCalculoNivelSisbenDTO }
     * 
     */
    public TipoRespuestaCalculoNivelSisbenDTO createTipoRespuestaCalculoNivelSisbenDTO() {
        return new TipoRespuestaCalculoNivelSisbenDTO();
    }

    /**
     * Create an instance of {@link TipoInformacionBasicaSisben }
     * 
     */
    public TipoInformacionBasicaSisben createTipoInformacionBasicaSisben() {
        return new TipoInformacionBasicaSisben();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoEstado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.colpensiones.gov.co/beps/schemas/1.0/personas", name = "nivel", scope = TipoInformacionBasicaSisben.class)
    public JAXBElement<TipoEstado> createTipoInformacionBasicaSisbenNivel(TipoEstado value) {
        return new JAXBElement<TipoEstado>(_TipoInformacionBasicaSisbenNivel_QNAME, TipoEstado.class, TipoInformacionBasicaSisben.class, value);
    }

    
    /**
     * Create an instance of {@link TipoInformacionComplementariaPersonaNatural }
     * 
     */
    public TipoInformacionComplementariaPersonaNatural createTipoInformacionComplementariaPersonaNatural() {
        return new TipoInformacionComplementariaPersonaNatural();
    }

    /**
     * Create an instance of {@link TipoInformacionNacimientoPersonaNatural }
     * 
     */
    public TipoInformacionNacimientoPersonaNatural createTipoInformacionNacimientoPersonaNatural() {
        return new TipoInformacionNacimientoPersonaNatural();
    }

    /**
     * Create an instance of {@link TipoCiudad }
     * 
     */
    public TipoCiudad createTipoCiudad() {
        return new TipoCiudad();
    }

    /**
     * Create an instance of {@link TipoDocumentoPersonaNatural }
     * 
     */
    public TipoDocumentoPersonaNatural createTipoDocumentoPersonaNatural() {
        return new TipoDocumentoPersonaNatural();
    }

    /**
     * Create an instance of {@link TipoInformacionBasicaPersonaNatural }
     * 
     */
    public TipoInformacionBasicaPersonaNatural createTipoInformacionBasicaPersonaNatural() {
        return new TipoInformacionBasicaPersonaNatural();
    }

    /**
     * Create an instance of {@link TipoDepartamento }
     * 
     */
    public TipoDepartamento createTipoDepartamento() {
        return new TipoDepartamento();
    }

    /**
     * Create an instance of {@link TipoInformacionBasicaSolicitante }
     * 
     */
    public TipoInformacionBasicaSolicitante createTipoInformacionBasicaSolicitante() {
        return new TipoInformacionBasicaSolicitante();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoInformacionBasicaPersonaNatural }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.colpensiones.gov.co/beps/schemas/1.0/personas", name = "nombresApellidos", scope = TipoInformacionBasicaSolicitante.class)
    public JAXBElement<TipoInformacionBasicaPersonaNatural> createTipoInformacionBasicaSolicitanteNombresApellidos(TipoInformacionBasicaPersonaNatural value) {
        return new JAXBElement<TipoInformacionBasicaPersonaNatural>(_TipoInformacionBasicaSolicitanteNombresApellidos_QNAME, TipoInformacionBasicaPersonaNatural.class, TipoInformacionBasicaSolicitante.class, value);
    }


    /**
     * Create an instance of {@link TipoAutorizacion }
     * 
     */
    public TipoAutorizacion createTipoAutorizacion() {
        return new TipoAutorizacion();
    }

    /**
     * Create an instance of {@link TipoInformacionPrevinculado }
     * 
     */
    public TipoInformacionPrevinculado createTipoInformacionPrevinculado() {
        return new TipoInformacionPrevinculado();
    }

 
    /**
     * Create an instance of {@link TipoDireccionesCorreoElectronico }
     * 
     */
    public TipoDireccionesCorreoElectronico createTipoDireccionesCorreoElectronico() {
        return new TipoDireccionesCorreoElectronico();
    }

    /**
     * Create an instance of {@link TipoInformacionGeneralVinculado }
     * 
     */
    public TipoInformacionGeneralVinculado createTipoInformacionGeneralVinculado() {
        return new TipoInformacionGeneralVinculado();
    }

    /**
     * Create an instance of {@link TipoTelefonos }
     * 
     */
    public TipoTelefonos createTipoTelefonos() {
        return new TipoTelefonos();
    }

    /**
     * Create an instance of {@link TipoCorreoElectronico }
     * 
     */
    public TipoCorreoElectronico createTipoCorreoElectronico() {
        return new TipoCorreoElectronico();
    }

    /**
     * Create an instance of {@link TipoInformacionUbicacionPersona }
     * 
     */
    public TipoInformacionUbicacionPersona createTipoInformacionUbicacionPersona() {
        return new TipoInformacionUbicacionPersona();
    }

    /**
     * Create an instance of {@link TipoInformacionEconomica }
     * 
     */
    public TipoInformacionEconomica createTipoInformacionEconomica() {
        return new TipoInformacionEconomica();
    }

    /**
     * Create an instance of {@link TipoDatoTelefono }
     * 
     */
    public TipoDatoTelefono createTipoDatoTelefono() {
        return new TipoDatoTelefono();
    }

    /**
     * Create an instance of {@link TipoInformacionDireccionPersonaNatural }
     * 
     */
    public TipoInformacionDireccionPersonaNatural createTipoInformacionDireccionPersonaNatural() {
        return new TipoInformacionDireccionPersonaNatural();
    }

    /**
     * Create an instance of {@link TipoInformacionViabilidad }
     * 
     */
    public TipoInformacionViabilidad createTipoInformacionViabilidad() {
        return new TipoInformacionViabilidad();
    }

     /**
     * Create an instance of {@link TipoInformacionExtendidaSolicitante }
     * 
     */
    public TipoInformacionExtendidaSolicitante createTipoInformacionExtendidaSolicitante() {
        return new TipoInformacionExtendidaSolicitante();
    }

    /**
     * Create an instance of {@link TipoPatrocinador }
     * 
     */
    public TipoPatrocinador createTipoPatrocinador() {
        return new TipoPatrocinador();
    }

    /**
     * Create an instance of {@link TipoCausalNoViabilidad }
     * 
     */
    public TipoCausalNoViabilidad createTipoCausalNoViabilidad() {
        return new TipoCausalNoViabilidad();
    }

    /**
     * Create an instance of {@link TipoInformacionDivulgacion }
     * 
     */
    public TipoInformacionDivulgacion createTipoInformacionDivulgacion() {
        return new TipoInformacionDivulgacion();
    }

    /**
     * Create an instance of {@link TipoInformacionCuentaBeps }
     * 
     */
    public TipoInformacionCuentaBeps createTipoInformacionCuentaBeps() {
        return new TipoInformacionCuentaBeps();
    }

    /**
     * Create an instance of {@link TipoRespuestaInformacionSolicitanteDTO }
     * 
     */
    public TipoRespuestaInformacionSolicitanteDTO createTipoRespuestaInformacionSolicitanteDTO() {
        return new TipoRespuestaInformacionSolicitanteDTO();
    }

 
    /**
     * Create an instance of {@link TipoInformacionGeneralSolicitanteDTO }
     * 
     */
    public TipoInformacionGeneralSolicitanteDTO createTipoInformacionGeneralSolicitanteDTO() {
        return new TipoInformacionGeneralSolicitanteDTO();
    }
    
    /**
     * Create an instance of {@link TipoInformacionDatosGeneralesVinculado }
     * 
     */
    public TipoInformacionDatosGeneralesVinculado createTipoInformacionDatosGeneralesVinculado() {
        return new TipoInformacionDatosGeneralesVinculado();
    }
    
    /**
     * Create an instance of {@link TipoFiltrosConsulta }
     * 
     */
    public TipoFiltrosConsulta createTipoFiltrosConsulta() {
        return new TipoFiltrosConsulta();
    }

    /**
     * Create an instance of {@link TipoRespuestaConsultaMovimientosSaldoDTO }
     * 
     */
    public TipoRespuestaConsultaMovimientosSaldoDTO createTipoRespuestaConsultaMovimientosSaldoDTO() {
        return new TipoRespuestaConsultaMovimientosSaldoDTO();
    }

    /**
     * Create an instance of {@link TipoInformacionTransacciones }
     * 
     */
    public TipoInformacionTransacciones createTipoInformacionTransacciones() {
        return new TipoInformacionTransacciones();
    }

    /**
     * Create an instance of {@link TipoInformacionAportesPesos }
     * 
     */
    public TipoInformacionAportesPesos createTipoInformacionAportesPesos() {
        return new TipoInformacionAportesPesos();
    }

    /**
     * Create an instance of {@link TipoInformacionTransaccionMovimientos }
     * 
     */
    public TipoInformacionTransaccionMovimientos createTipoInformacionTransaccionMovimientos() {
        return new TipoInformacionTransaccionMovimientos();
    }


    /**
     * Create an instance of {@link TipoConsultaMovimientosSaldo }
     * 
     */
    public TipoConsultaMovimientosSaldo createTipoConsultaMovimientosSaldo() {
        return new TipoConsultaMovimientosSaldo();
    }

    /**
     * Create an instance of {@link TipoInformacionMovimiento }
     * 
     */
    public TipoInformacionMovimiento createTipoInformacionMovimiento() {
        return new TipoInformacionMovimiento();
    }

    /**
     * Create an instance of {@link TipoInformacionAportesUnidades }
     * 
     */
    public TipoInformacionAportesUnidades createTipoInformacionAportesUnidades() {
        return new TipoInformacionAportesUnidades();
    }

    /**
     * Create an instance of {@link TipoInformacionCuenta }
     * 
     */
    public TipoInformacionCuenta createTipoInformacionCuenta() {
        return new TipoInformacionCuenta();
    }
  }
