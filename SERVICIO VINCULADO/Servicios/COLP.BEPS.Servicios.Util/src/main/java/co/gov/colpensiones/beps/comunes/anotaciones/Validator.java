package co.gov.colpensiones.beps.comunes.anotaciones;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBElement;

import co.gov.colpensiones.beps.comunes.utilidades.StringUtil;

/**
 * 
 * <b>Descripcion:</b> Clase utilitaria para la validación de datos de entrada del servicio<br/>
 * <b>Caso de Uso:</b> BEPS - Transversal<br/>
 * 
 * @author Yenny Ñustez Arévalo <ynustez@heinsohn.com.co>
 */
public class Validator {

    /**
     * Listado de errores obtenidos luego de las validaciones
     */
    private ArrayList<String> builder;

    /**
     * Constructor de la clase
     */
    public Validator() {
        builder = new ArrayList<String>();
    }

    /**
     * Comprueba si un objeto es válido.
     * 
     * @param object
     *            Objeto a validar
     * @return true Si el objeto es válido, false En caso contrario
     */
    public boolean isValid(Object object) {
        return errorList(object).size() == 0;
    }

    /**
     * Evalúa todos los métodos anotados de un objeto
     * 
     * @param object
     *            Objeto a validar
     * @return Listado de errores obtenidos luego de las validaciones
     */
    public ArrayList<String> errorList(Object object) {
        if (object != null) {
            Method[] classMethods = object.getClass().getMethods();

            try {
                for (Method method : classMethods) {
                    ValidateMethod(method, object);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return builder;
    }

    /**
     * Esta función valida un método en específico de acuerdo a sus anotaciones
     * 
     * @param metodo
     *            Método a validar
     * @param object
     *            Objeto a validar
     * @throws Exception
     */
    private void ValidateMethod(Method metodo, Object object) throws Exception {

        Annotation[] annotations = metodo.getAnnotations();
        boolean ignoreNulls = false;
        boolean ignoreEmpty = false;

        for (Annotation annotation : annotations) {
            if (annotation instanceof IgnoreNullsValidator) {
                IgnoreNullsValidator ignoreNullsValidator = (IgnoreNullsValidator) annotation;
                ignoreNulls = ignoreNullsValidator.ignoreNulls();
            } else if (annotation instanceof IgnoreEmptyValidator) {
                if (metodo.getReturnType().toString().equals("void")) {
                    builder.add(getNonValidReturnTypeMessage(metodo, annotation));
                } else {
                    IgnoreEmptyValidator a = (IgnoreEmptyValidator) annotation;
                    if (metodo.invoke(object) == null) {
                        builder.add(StringUtil.Format(a.message(), getFieldName(metodo)));
                        ignoreNulls = true;
                    } else if (metodo.invoke(object).toString().length() == 0) {
                        ignoreEmpty = true;
                    }
                }
            } else if(annotation instanceof OptionalFieldValidator) {
                if (metodo.getReturnType().toString().equals("void")) {
                    builder.add(getNonValidReturnTypeMessage(metodo, annotation));
                } else {
                    //OptionalFieldValidator a = (OptionalFieldValidator) annotation;
                    if (metodo.invoke(object) == null) {
                        //builder.add(StringUtil.Format(a.message(), getFieldName(metodo)));
                        ignoreNulls = true;
                    } else if (metodo.invoke(object).toString().length() == 0) {
                        ignoreEmpty = true;
                    }
                }
            }
        }

        for (Annotation annotation : annotations) {
            if (annotation instanceof NotNullValidator) {
                validateNotNullValidator(metodo, object, annotation);
            } else if (annotation instanceof RegexValidator) {
                validateRegexValidator(metodo, object, annotation, ignoreNulls, ignoreEmpty);
            } else if (annotation instanceof StringLengthValidator) {
                validateStringLengthValidator(metodo, object, annotation, ignoreNulls, ignoreEmpty);
            } else if (annotation instanceof RangeValidator) {
                validateRangeValidator(metodo, object, annotation, ignoreNulls, ignoreEmpty);
            } else if (annotation instanceof ObjectValidator) {
                validateObjectValidator(metodo, object, annotation, ignoreNulls);
            } else if (annotation instanceof DateValidator) {
                validateDateValidator(metodo, object, annotation, ignoreNulls, ignoreEmpty);
            }
        }
    }

    /**
     * Método que retorna el valor de un objeto de entrada
     * 
     * @param object
     *            Objeto a validar
     * @return valor del objeto validado
     */
    @SuppressWarnings("rawtypes")
    private Object getObjectValue(Object object) {
        if (object instanceof JAXBElement) {
            JAXBElement element = (JAXBElement) object;
            return element == null ? object : element.getValue();
        }
        return object;
    }

    /**
     * Método para la validación de fechas. Anotación DateValidator.
     * 
     * @param metodo
     *            Método a validar
     * @param object
     *            Objeto a validad
     * @param annotation
     *            Anotación del método
     * @param ignoreNulls
     *            true Si se ignora el valor nulo del objeto, false en caso contrario
     * @param ignoreEmpty
     *            true Si se ignora el valor vacío del objeto, false en caso contrario
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private void validateDateValidator(Method metodo, Object object, Annotation annotation, boolean ignoreNulls, boolean ignoreEmpty)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        DateValidator a = (DateValidator) annotation;

        if (metodo.getReturnType().toString().equals("void")) {
            builder.add(getNonValidReturnTypeMessage(metodo, annotation));
        } else {
            Object instance = getObjectValue(metodo.invoke(object));
            if (instance == null && !ignoreNulls) {
                builder.add(StringUtil.Format(a.message(), getFieldName(metodo)));
            } else if (instance != null && !ignoreEmpty) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    sdf.setLenient(false);
                    Date fecha = sdf.parse(instance.toString());

                    if (a.validarFechaActual() && fecha.compareTo(new Date()) > 0) {
                        builder.add(StringUtil.Format(a.messageValidacionFechaActual(), getFieldName(metodo)));
                    }
                } catch (ParseException e) {
                    builder.add(StringUtil.Format(a.message(), getFieldName(metodo)));
                }
            }
        }
    }

    /**
     * Método para la validación de datos obligatorios. Anotación NotNullValidator. Genera error si el valor del objeto validado es nulo.
     * 
     * @param metodo
     *            Método a validar
     * @param object
     *            Objeto a validar
     * @param annotation
     *            Anotación del método
     * @throws Exception
     */
    private void validateNotNullValidator(Method metodo, Object object, Annotation annotation) throws Exception {

        NotNullValidator a = (NotNullValidator) annotation;

        if (metodo.getReturnType().toString().equals("void")) {
            builder.add(getNonValidReturnTypeMessage(metodo, annotation));
        } else if (metodo.invoke(object) == null) {
            builder.add(StringUtil.Format(a.message(), getFieldName(metodo)));
        }
    }

    /**
     * Método para la validación de datos expresiones regulares. Anotación RegexValidator. Genera error si el valor del objeto validado no
     * corresponde al formato de la expresión regular.
     * 
     * @param metodo
     *            Método a validar
     * @param object
     *            Objeto a validar
     * @param annotation
     *            Anotación del método
     * @param ignoreNulls
     *            true Si se ignora el valor nulo del objeto, false en caso contrario
     * @param ignoreEmpty
     *            true Si se ignora el valor vacío del objeto, false en caso contrario
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private void validateRegexValidator(Method metodo, Object object, Annotation annotation, boolean ignoreNulls, boolean ignoreEmpty)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        RegexValidator a = (RegexValidator) annotation;

        if (metodo.getReturnType().toString().equals("void")) {
            builder.add(getNonValidReturnTypeMessage(metodo, annotation));
        } else {
            /* Toma el objeto ingresado */
            Object instance = getObjectValue(metodo.invoke(object));
            if (instance == null && !ignoreNulls) {
                builder.add(StringUtil.Format(a.message(), getFieldName(metodo), a.pattern()));
            } else if (instance != null && !ignoreEmpty) {
                if (!Pattern.compile(a.pattern()).matcher(instance.toString()).matches()) {
                    builder.add(StringUtil.Format(a.message(), getFieldName(metodo), a.pattern()));
                }
            }
        }
    }

    /**
     * Método para la validación de longitud de cadenas de caracteres. Anotación StringLengthValidator.
     * 
     * @param metodo
     *            Método a validar
     * @param object
     *            Objeto a validar
     * @param annotation
     *            Anotación del método
     * @param ignoreNulls
     *            true Si se ignora el valor nulo del objeto, false en caso contrario
     * @param ignoreEmpty
     *            true Si se ignora el valor vacío del objeto, false en caso contrario
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private void validateStringLengthValidator(Method metodo, Object objeto, Annotation annotation, boolean ignoreNulls, boolean ignoreEmpty)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        StringLengthValidator a = (StringLengthValidator) annotation;

        if (metodo.getReturnType().toString().equals("void")) {
            builder.add(getNonValidReturnTypeMessage(metodo, annotation));
        } else {
            Object instance = getObjectValue(metodo.invoke(objeto));
            if (instance == null && !ignoreNulls) {
                builder.add(StringUtil.Format(a.message(), getFieldName(metodo), a.min(), a.max()));
            } else if (instance != null && !ignoreEmpty) {
                if (instance.toString().length() < a.min() || instance.toString().length() > a.max())
                    builder.add(StringUtil.Format(a.message(), getFieldName(metodo), a.min(), a.max()));
            }
        }
    }

    /**
     * Método para la validación de rangos numéricos. Anotación RangeValidator.
     * 
     * @param metodo
     *            Método a validar
     * @param object
     *            Objeto a validar
     * @param annotation
     *            Anotación del método
     * @param ignoreNulls
     *            true Si se ignora el valor nulo del objeto, false en caso contrario
     * @param ignoreEmpty
     *            true Si se ignora el valor vacío del objeto, false en caso contrario
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */

    private void validateRangeValidator(Method metodo, Object o, Annotation annotation, boolean ignoreNulls, boolean ignoreEmpty)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        RangeValidator a = (RangeValidator) annotation;

        if (metodo.getReturnType().toString().equals("void")) {
            builder.add(getNonValidReturnTypeMessage(metodo, annotation));
        } else {

            Object instance = getObjectValue(metodo.invoke(o));
            if (instance == null && !ignoreNulls) {
                builder.add(StringUtil.Format(a.message(), getFieldName(metodo), a.min(), a.max()));

            } else if (instance != null && !ignoreEmpty) {

                if ((metodo.getReturnType() == int.class) || (metodo.getReturnType() == Integer.class)) {
                    if (Integer.parseInt(instance.toString()) < a.min() || Integer.parseInt(instance.toString()) > a.max()) {
                        builder.add(StringUtil.Format(a.message(), getFieldName(metodo), a.min(), a.max()));
                    }
                } else if ((metodo.getReturnType() == float.class) || (metodo.getReturnType() == Float.class)) {
                    if (Float.parseFloat(instance.toString()) < a.min() || Float.parseFloat(instance.toString()) > a.max()) {
                        builder.add(StringUtil.Format(a.message(), getFieldName(metodo), a.min(), a.max()));
                    }
                } else if ((metodo.getReturnType() == double.class) || (metodo.getReturnType() == Double.class)) {
                    if (Double.parseDouble(instance.toString()) < a.min() || Double.parseDouble(instance.toString()) > a.max()) {
                        builder.add(StringUtil.Format(a.message(), getFieldName(metodo), a.min(), a.max()));
                    }
                }
            }
        }
    }

    /**
     * Este método define la anotacion ObjectValidator que se encarga de validar de manera anidada las anotaciones de un objeto.
     * 
     * @param metodo
     *            Método a validar
     * @param object
     *            Objeto a validar
     * @param annotation
     *            Anotación del método
     * @param ignoreNulls
     *            true Si se ignora el valor nulo del objeto, false en caso contrario
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("rawtypes")
    private void validateObjectValidator(Method metodo, Object object, Annotation annotation, boolean ignoreNulls)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        ObjectValidator a = (ObjectValidator) annotation;

        if (metodo.getReturnType().toString().equals("void")) {
            builder.add(getNonValidReturnTypeMessage(metodo, annotation));
        } else {

            Object instance = getObjectValue(metodo.invoke(object));
            if (instance == null && !ignoreNulls) {
                builder.add(StringUtil.Format(a.message(), getFieldName(metodo)));
            } else {

                if (instance.getClass().isAnnotation() || instance.getClass().isEnum() || instance.getClass().isPrimitive()) {
                    builder.add(getNonValidReturnTypeMessage(metodo, a));
                } else {
                    if (instance.getClass().isArray()) {
                        int length = Array.getLength(instance);
                        if (length > 0) {
                            Object firstObject = Array.get(instance, 0);
                            if (firstObject.getClass().isAnnotation() || firstObject.getClass().isEnum()
                                    || firstObject.getClass().isPrimitive()) {
                                builder.add(getNonValidReturnTypeMessage(metodo, a));
                            } else {
                                for (int i = 0; i < length; i++) {
                                    errorList(Array.get(instance, i));
                                }
                            }
                        }
                    } else if (instance instanceof List) {
                        int length = ((List) instance).size();
                        if (length > 0) {
                            Object firstObject = ((List) instance).get(0);
                            if (firstObject.getClass().isAnnotation() || firstObject.getClass().isEnum()
                                    || firstObject.getClass().isPrimitive()) {
                                builder.add(getNonValidReturnTypeMessage(metodo, a));
                            } else {
                                for (int i = 0; i < length; i++) {
                                    errorList(((List) instance).get(i));
                                }
                            }
                        }
                    } else {
                        errorList(instance);
                    }
                }
            }
        }

    }

    /**
     * Método que obtiene el nombre de un campo a partir de su método get()
     * 
     * @param metodo
     *            Método a validar
     * @return
     */
    private String getFieldName(Method metodo) {
        String fieldName = metodo.getName();
        if (fieldName.startsWith("get"))
            fieldName = fieldName.replace("get", "");
        return fieldName;
    }

    /**
     * Método que retorna el mensaje de error cuando el método de entrada no tiene un tipo de retorno valido
     * 
     * @param metodo
     *            Método a validar
     * @param annotation
     *            Anotación del método
     * @return mensaje de error de validación de tipo de retorno
     */
    private String getNonValidReturnTypeMessage(Method metodo, Annotation annotation) {
        String message = StringUtil.Format("El tipo de dato {0} no tiene la anotacion {1}", metodo.getReturnType().getCanonicalName(),
                annotation.annotationType().getName());

        return message;
    }
    
}
