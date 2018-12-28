/**
 * 
 */
package co.gov.colpensiones.beps.comunes.utilidades;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Caso de Uso:</b> FAB- <br/>
 *
 * @author ddelaroche <ddelaroche@heinsohn.com.co>
 */
public class AdministradorArchivosRecursos {
	
    private static AdministradorArchivosRecursos administradorArchivosRecursos;
    private Map<String,Properties> archivosCargados;
    
    /**
     * Construcctor privado para iniciar el mapa de archivos cargados
     */
    private AdministradorArchivosRecursos(){
        archivosCargados = new HashMap<String,Properties>();
    }
    
    /**
     * MÃ©todo para obtener la Ãºnica instancia de la clase
     * @return Ãºnica instancia de la clase
     */
    public static synchronized AdministradorArchivosRecursos getInstance(){
        if(administradorArchivosRecursos==null){
            administradorArchivosRecursos = new AdministradorArchivosRecursos();
        }
        return administradorArchivosRecursos;
    }
    
    /**
     * Permite obtener la propiedad con nombre llave que se encuentra en el archivo
     * de recursos con nombreArchivo.
     * @param nombreArchivo nombre del archivo 
     * @param llave nombre de la propiedad
     * @return valor de la propiedad en el archivo
     * @throws IOException
     */
    public String getPropiedad(String nombreArchivo, String llave) throws IOException {
        if(archivosCargados.get(nombreArchivo)==null){
            Properties archivo = cargarArchivoRecursosCompartido(nombreArchivo);
            archivosCargados.put(nombreArchivo, archivo);
        }
        
        return archivosCargados.get(nombreArchivo).getProperty(llave);
    }
    
    /**
     * Permite cargar un archivo de recursos que se encuentra como mÃ³dulo compartido
     * con el nombre que ingresa como parÃ¡metro.
     * @param nombreArchivo nombre del archivo a cargar
     * @return Archivo de propiedades cargado
     * @throws IOException 
     * @throws Exception en caso que se presente error al cargar el archivo
     */
    private Properties cargarArchivoRecursosCompartido(String nombreArchivo) throws IOException {
        Properties propertiesFile = new Properties();
        InputStream archivo = null;
        try{
            archivo = this.getClass().getResourceAsStream(nombreArchivo);
        
            propertiesFile.load(archivo);
        }finally{
            if(archivo != null)
                archivo.close();
        }
        return propertiesFile;
    }
    

}
