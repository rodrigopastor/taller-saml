

/**
 * Convenience class for encapsulating SAMLAuthnRequestBuilder parameters
 * @author epconcepcion
 *
 */
public class SAMLAuthnRequestBuilderParameters {
	
	private String nameId;
    private String assertionConsumerUrl;
    private String destination;
    private boolean forceAuthn;
    private boolean passive;
    private Integer assertionConsumerServiceIndex;
    private String assertionConsumerServiceURL;
    private String id;
    
	public String getNameId() {
		return nameId;
	}
	
	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getAssertionConsumerUrl() {
		return assertionConsumerUrl;
	}
	
	public void setAssertionConsumerUrl(String assertionConsumerUrl) {
		this.assertionConsumerUrl = assertionConsumerUrl;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public boolean isForceAuthn() {
		return forceAuthn;
	}
	
	public void setForceAuthn(boolean forceAuthn) {
		this.forceAuthn = forceAuthn;
	}
	
	public boolean isPassive() {
		return passive;
	}
	
	public void setPassive(boolean passive) {
		this.passive = passive;
	}
	
	public Integer getAssertionConsumerServiceIndex() {
		return assertionConsumerServiceIndex;
	}
	
	public void setAssertionConsumerServiceIndex(Integer assertionConsumerServiceIndex) {
		this.assertionConsumerServiceIndex = assertionConsumerServiceIndex;
	}
	
	public String getAssertionConsumerServiceURL() {
		return assertionConsumerServiceURL;
	}
	
	public void setAssertionConsumerServiceURL(
			String assertionConsumerServiceURL) {
		this.assertionConsumerServiceURL = assertionConsumerServiceURL;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}


}
