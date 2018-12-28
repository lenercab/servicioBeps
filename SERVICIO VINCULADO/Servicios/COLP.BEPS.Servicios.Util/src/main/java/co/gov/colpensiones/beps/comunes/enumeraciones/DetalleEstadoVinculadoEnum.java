
package co.gov.colpensiones.beps.comunes.enumeraciones;

/**
 * Enumeración que representa los códigos de detalle de estado de un vinculado.
 * 
 * @author Yenny Ñustez Arévalo <ynustez@heinsohn.com.co>
 *
 */
public enum DetalleEstadoVinculadoEnum {

    /** Activo - En estudio de otorgamiento */
    A01,
    
    /** Suspendido - Fallecimiento */
    S01, 
    
    /** Suspendido - Cancelada intento doble cedulación */
    S02,
    
    /** Suspendido - Derechos Políticos */
    S03, 
    
    /** Suspendido - Doble cedulación */
    S04, 
    
    /** Suspendido - Falsa identidad */
    S05, 
    
    /** Suspendido - Interdicción por demencia */
    S06, 
    
    /** Suspendido - Mala elaboración */
    S07,
    
    /** Suspendido - Reasignación de sexo */
    S08, 
    
    /** Suspendido - Pensión de invalidez */
    S09, 
    
    /** Suspendido - Pensión de sobrevivencia */
    S10,
    
    /** Suspendido - SARLAFT */
    S11,
    
    /** Retirado - Fallecimiento */
    R01,
    
    /** Retirado - Orden Judicial */
    R02,
    
    /** Retirado - Otorgamiento */
    R03,
    
    /** Retirado - Pensión de vejez */
    R04,
    
    /** Retirado - Pensión familiar */
    R05, 
    
    /** Retirado - Voluntario  */
    R06  
}
