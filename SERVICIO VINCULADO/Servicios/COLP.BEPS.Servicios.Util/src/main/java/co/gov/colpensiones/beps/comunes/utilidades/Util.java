package co.gov.colpensiones.beps.comunes.utilidades;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import co.gov.colpensiones.beps.dal.utilidades.DataTable;

/**
 * Clase encargada de las funciones comunes a los servicios
 * 
 * @author Helen Acero <hacero@heinsohn.com.co>
 * 
 */
public class Util {

    /** Variable para formateo de fechas */
    private static SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_FECHA_AAAAMMDD);

    /**
     * Método que valida el formato enviado para un tipo de dato BigDecimal
     * 
     * @param formato
     *            , expresion regular para validar el dato de entrada
     * @param valor
     *            , dato a ser evaluado
     * @return true si cumple con el formato, false en caso contrario
     */
    public static boolean validarFormatoDatoBigDecimal(String formato, BigDecimal valor) {
        return Pattern.matches(formato, valor.doubleValue() + "");
    }

    /**
     * Método que retorna el arreglo de llaves existentes en un archivo de propiedades, agrupadas por un prefijo.
     * 
     * @param nombreArchivoPropiedades
     * @param prefijoLlave
     * @return arreglo de llaves coincidentes con el prefijo ingresado por parámetro.
     */
    public static ArrayList<String> getResourcePropertyArray(String nombreArchivoPropiedades, String prefijoLlave) {
        ResourceBundle bundle = ResourceBundle.getBundle(nombreArchivoPropiedades);
        Enumeration<String> keys = bundle.getKeys();
        ArrayList<String> temp = new ArrayList<String>();

        for (Enumeration<String> e = keys; keys.hasMoreElements();) {
            String key = e.nextElement();
            if (key.startsWith(prefijoLlave)) {
                temp.add(key.replace(prefijoLlave, ""));
            }
        }

        return temp;
    }

    /**
     * Metodo que retorna el valor correspondiente a una llave definida dentro de un archivo de propiedades
     * 
     * @param nombreArchivoPropiedades
     * @param llave
     * @return valor asignado a la llave. Nulo en caso de no encontrar la llave.
     */
    public static String getResourceProperty(String nombreArchivoPropiedades, String llave) {
        String valorLlave = null;
        try {
            ResourceBundle boundle = ResourceBundle.getBundle(nombreArchivoPropiedades);
            valorLlave = boundle.getString(llave);

        } catch (Exception e) {
            valorLlave = null;
        }
        return valorLlave;
    }

    /**
     * Método que retorna una fecha a partir de una cadena que representa una fecha en formato yyyMMdd.
     * 
     * @param fecha
     *            cadena que contiene la fecha.
     * @return Date Fecha.
     */
    public static Date obtenerFechaString(String fecha) {
        try {
            sdf.setLenient(false);
            return new Date(sdf.parse(fecha.toString()).getTime());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método que permite obtener a partir de una cadena su valor correspondiente a un BIT para ser almacenado en la BD.
     * 
     * @param valor
     *            Cadena utilizada para obtener el BIT
     * @return Integer 1 corresponde a SI 0 corresponde a no.
     */
    public static Integer convertirStringBit(String valor) {
        return Constantes.SI.equals(valor) ? 1 : 0;
    }

    /**
     * Método que obtiene el valor para un campo de tipo BIT.
     * 
     * @param valor
     *            String a convertir, se recibe 'True' o 'False'
     * @return Cadena con los posibles valores 'S' o 'N'.
     */
    public static String parseBitToString(Boolean valor) {
        return (valor.equals(Boolean.TRUE)) ? Constantes.SI : Constantes.NO;
    }

    /**
     * Método que permite calcular la edad a partir de la fecha de nacimiento
     * 
     * @param fechaNacimiento
     *            Fecha de nacimiento
     * @param formatoFecha
     *            Formato en el cual se recibe la fecha de nacimiento
     * @return Integer Edad calculada.
     */
    public static Integer calcularEdad(String fechaNacimiento, String formatoFecha) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha);
            java.util.Date dateNacimiento = sdf.parse(fechaNacimiento);
            GregorianCalendar fNacimiento = new GregorianCalendar();
            fNacimiento.setTime(dateNacimiento);
            GregorianCalendar fechaActual = new GregorianCalendar();
            fechaActual.setTime(new java.util.Date());

            /* Se calcula la edad */
            int edad = fechaActual.get(Calendar.YEAR) - fNacimiento.get(Calendar.YEAR);
            if (fNacimiento.get(Calendar.MONTH) > fechaActual.get(Calendar.MONTH)
                    || (fechaActual.get(Calendar.MONTH) == fNacimiento.get(Calendar.MONTH) && fNacimiento.get(Calendar.DATE) > fechaActual
                            .get(Calendar.DATE))) {
                edad--;
            }
            return edad;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método que retorna una fecha a partir de una cadena que representa una fecha en formato ingresado por parámetro.
     * 
     * @param fecha
     *            cadena que contiene la fecha.
     * @formato formato de fecha en el que se recibe la cadena
     * @return Date Fecha.
     */
    public static Date obtenerFecha(String fecha, String formato) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            sdf.setLenient(false);
            return new Date(sdf.parse(fecha.toString()).getTime());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método que retorna el valor de un Objeto contenido en un DataTable.
     * 
     * @param data
     *            objeto de tipo DataTable de donde se obtiene la información.
     * @param rowIndex
     *            índice de la fila de la cual se obtiene la información
     * @param atributo
     *            atributo ó columna contenido en la fila de la cual se obtiene la información
     * @return valor del objeto mapeado. Null si no contiene información.
     */
    public static String dataObjectToString(DataTable data, int rowIndex, String atributo) {
        Object auxValue = data.getRows().get(rowIndex).getValue(atributo);
        String valorObjeto = (auxValue != null) ? auxValue.toString() : null;
        return valorObjeto;
    }

}
