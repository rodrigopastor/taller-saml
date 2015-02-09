package test.saml.util;

import java.io.ByteArrayOutputStream;

import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Artifact;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.saml2.core.impl.AuthnRequestBuilder;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.saml2.core.impl.NameIDPolicyBuilder;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;

public class TestSAML {

	public static final String SAML2_NAME_ID_PERSISTENT = "urn:oasis:names:tc:SAML:2.0:nameid-format:persistent";

	/** The Constant objectBuilderFactory. */
	private static final XMLObjectBuilderFactory XMLBUILDER = Configuration
			.getBuilderFactory();

	/** The Constant artifactBuilder. */
	private static final SAMLObjectBuilder ARTBUILDER = makeSamlObjectBuilder(Artifact.DEFAULT_ELEMENT_NAME);
	
	public static byte[] nodeToByteArray(Element element)
			throws TransformerException {
		return nodeToByteArray(element, false);

	}

	public static byte[] nodeToByteArray(Element element, boolean prettyPrint)
			throws TransformerException {
		Transformer transformer = getTransformer(prettyPrint);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		StreamResult result = new StreamResult(outputStream);
		transformer.transform(new DOMSource(element), result);
		return outputStream.toByteArray();
	}

	private static Transformer getTransformer(boolean formatXml)
			throws TransformerConfigurationException {
		TransformerFactory tf;

		tf = getTransformerFactory();

		Transformer transformer = tf.newTransformer();

		if (formatXml) {
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");
		}

		return transformer;
	}

	public static TransformerFactory getTransformerFactory() {
		TransformerFactory tf = null;
		try {
			tf = TransformerFactory.newInstance();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return tf;
	}

	private static SAMLObjectBuilder makeSamlObjectBuilder(QName name) {

		// Hay que comprobar si las librerías estan cargadas o no
		// Si no lo están puede ser porque el servicio SAML esté en modo Lazy
		// con lo que habrá que arrancar las liberrías desde aquí
		if (!DefaultBootstrapWrapper.isOpenSAMLBooted()) {
			try {
				DefaultBootstrap.bootstrap();
			} catch (ConfigurationException e) {
				throw new IllegalStateException(e.getMessage());
			}
		}
		return (SAMLObjectBuilder) XMLBUILDER.getBuilder(name);
	}

	public static class DefaultBootstrapWrapper {
		/**
		 * Checks if OpenSAML library is initialized.
		 * 
		 * @return boolean result
		 */
		public static boolean isOpenSAMLBooted() {
			boolean booted = true;
			// LOG.debugI18N(CLASS, "CHECKING_OPENSAML_LIBRARY_INITIALIZED");

			// Si no hay buidelrs registrados, es que no se ha inicializado la
			// librería
			if (Configuration.getBuilderFactory().getBuilders().size() == 0) {
				booted = false;
			}
			// LOG.debugI18N(CLASS, "IS_OPENSAML_LIBRARY_INITIALIZED",
			// String.valueOf(booted));
			return booted;
		}

	}

}
