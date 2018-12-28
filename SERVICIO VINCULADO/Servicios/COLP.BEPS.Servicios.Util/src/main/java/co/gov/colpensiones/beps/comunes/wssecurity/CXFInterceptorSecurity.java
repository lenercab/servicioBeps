package co.gov.colpensiones.beps.comunes.wssecurity;

import java.security.Principal;
import java.security.acl.Group;
import java.util.Iterator;
import java.util.List;

import javax.security.auth.Subject;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.security.DefaultSecurityContext;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.security.SecurityContext;
import org.apache.ws.security.WSUsernameTokenPrincipal;
import org.jboss.wsf.spi.deployment.Endpoint;
import org.jboss.wsf.spi.security.SecurityDomainContext;
import org.jboss.wsf.stack.cxf.security.authentication.SubjectCreator;
import org.jboss.wsf.stack.cxf.security.nonce.NonceStore;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Clase definida como interceptor basado en el framework CXF, para la implementación de WSSecurity
 * 
 * @author Fidel Vargas <fvargas@heinsohn.com.co>
 * 
 */
public class CXFInterceptorSecurity extends AbstractSoapInterceptor {

	/** Interfaz mediante la cual se genera la instancia del usuario autenticado */
	protected SubjectCreator helper = new SubjectCreator();

	/**
	 * Constructor de la clase
	 */
	public CXFInterceptorSecurity() {
		super(Phase.PRE_INVOKE);
		helper.setPropagateContext(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message.Message)
	 */
	@Override
	public void handleMessage(SoapMessage message) throws Fault {

		String username = "";
		String password = "";
		List<Header> list = message.getHeaders();
		for (Iterator<Header> i = list.iterator(); i.hasNext();) {
			Header h = i.next();
			Element n = (Element) h.getObject();
			NodeList nl = n.getElementsByTagNameNS("*", "Username");
			username = nl.item(0).getTextContent();
			nl = n.getElementsByTagNameNS("*", "Password");
			password = nl.item(0).getTextContent();
		}

		Endpoint ep = message.getExchange().get(Endpoint.class);
		SecurityDomainContext sdc = ep.getSecurityDomainContext();
		SecurityContext context = message.get(SecurityContext.class);
		Principal principalTemporal = context.getUserPrincipal();

		if (context == null || principalTemporal == null) {
			principalTemporal = new Principal() {

				@Override
				public String getName() {
					return "";
				}
			};
		}

		WSUsernameTokenPrincipal up = new WSUsernameTokenPrincipal(username, false);
		up.setPassword(password);
		up.setCreatedTime("");
		up.setNonce("");
		Subject subject = createSubject(sdc, up.getName(), up.getPassword(), up.isPasswordDigest(), up.getNonce(), up.getCreatedTime());
		Principal principal = getPrincipal(principalTemporal, subject);
		message.put(SecurityContext.class, createSecurityContext(principal, subject));
	}

	/**
	 * Método que genera la instancia del usuario autenticado, de acuerdo a las credenciales obtenidas.
	 * 
	 * @param sdc
	 * @param name
	 * @param password
	 * @param isDigest
	 * @param nonce
	 * @param creationTime
	 * @return subject Instancia del usuario autenticado
	 * @throws Fault
	 */
	protected Subject createSubject(SecurityDomainContext sdc, String name, String password, boolean isDigest, String nonce,
			String creationTime) throws Fault {
		Subject subject = null;
		try {
			subject = helper.createSubject(sdc, name, password, isDigest, nonce, creationTime);
		} catch (Exception ex) {
			throw new Fault(new Exception("Credenciales no válidas"));
		}
		if (subject == null || subject.getPrincipals().size() == 0) {
			throw new Fault(new Exception("Subject no válido"));
		}
		return subject;
	}

	/**
	 * 
	 * @param originalPrincipal
	 * @param subject
	 * @return
	 */
	protected Principal getPrincipal(Principal originalPrincipal, Subject subject) {
		Principal[] ps = subject.getPrincipals().toArray(new Principal[] {});
		if (ps != null && ps.length > 0 && !(ps[0] instanceof Group)) {
			return ps[0];
		} else {
			return originalPrincipal;
		}
	}

	/**
	 * 
	 * @param p
	 * @param subject
	 * @return
	 */
	protected SecurityContext createSecurityContext(Principal p, Subject subject) {
		return new DefaultSecurityContext(p, subject);
	}

	/**
	 * 
	 * @param propagateContext
	 */
	public void setPropagateContext(boolean propagateContext) {
		this.helper.setPropagateContext(propagateContext);
	}

	/**
	 * 
	 * @param timestampThreshold
	 */
	public void setTimestampThreshold(int timestampThreshold) {
		this.helper.setTimestampThreshold(timestampThreshold);
	}

	/**
	 * 
	 * @param nonceStore
	 */
	public void setNonceStore(NonceStore nonceStore) {
		this.helper.setNonceStore(nonceStore);
	}

	/**
	 * 
	 * @param decodeNonce
	 */
	public void setDecodeNonce(boolean decodeNonce) {
		this.helper.setDecodeNonce(decodeNonce);
	}
}
