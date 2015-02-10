package test.saml.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.saml2.core.impl.AuthnRequestBuilder;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.saml2.core.impl.NameIDPolicyBuilder;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.util.Base64;

import test.saml.util.TestSAML;

/**
 * Servlet implementation class SamlServlet
 */
@WebServlet("/SamlServlet")
public class SamlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SamlServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		TestSAML saml=new TestSAML();
		AuthnRequest authnRequest = new AuthnRequestBuilder().buildObject();
		Random rdm=new Random();
		authnRequest.setID(String.valueOf(rdm.nextInt(999999)));
		authnRequest.setVersion(SAMLVersion.VERSION_20);
		authnRequest.setDestination("http://localhost:8085/SingleSignOnService");
		authnRequest.setForceAuthn(false);
		authnRequest.setIsPassive(false);
		authnRequest.setAssertionConsumerServiceURL("http://localhost:8080/TEST_SAML/SamlAssertionServlet");
		authnRequest.setProtocolBinding(SAMLConstants.SAML2_POST_BINDING_URI);
		Issuer issuerObj = new IssuerBuilder().buildObject();
		issuerObj.setValue("http://localhost:8080/TEST_SAML/SamlServlet");
		authnRequest.setIssuer(issuerObj);

		NameIDPolicy nameIdPolicy = new NameIDPolicyBuilder().buildObject();
		nameIdPolicy.setFormat(saml.SAML2_NAME_ID_PERSISTENT);
		authnRequest.setNameIDPolicy(nameIdPolicy);
		authnRequest.setIssueInstant(DateTime.now());

		MarshallerFactory marshallerFactory = Configuration
				.getMarshallerFactory();
		Marshaller marshaller = marshallerFactory.getMarshaller(authnRequest);
		byte[] authn = null;
		try {
			authn = saml.nodeToByteArray(marshaller.marshall(authnRequest));
		} catch (TransformerException e) {
			e.printStackTrace();

		} catch (MarshallingException e) {
			e.printStackTrace();

		}

	    PrintWriter out = response.getWriter();
	    out.println("<h1>Taller SAML - SERVICE PROVIDER</h1>");
	    out.println("<br><b>AuthRequest id:</b>"+authnRequest.getID());
	    out.println("<br><b>AuthRequest destination:</b>"+authnRequest.getDestination());
	    out.println("<br><b>AuthRequest issuer:</b>"+authnRequest.getIssuer().getValue());
	    out.println("<br><br><br>");
	    out.println("<form action='http://localhost:8085/SingleSignOnService' method='post'>");
	    out.println("<input type='hidden' name='SAMLRequest' value='"+Base64.encodeBytes(authn)+"' size='100'>");
	    out.println("<textarea rows='10' cols='100'>"+new String(authn)+"</textarea>");
	    out.println("<br><input type='submit'  value='Enviar'>");
	    out.println("</form'>");
	    
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
