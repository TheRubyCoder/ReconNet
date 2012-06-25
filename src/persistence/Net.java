package persistence;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * This Class represents the information of a petrinet
 *
 */
public	class Net{

	/**
	 * Id of the pertrinet
	 */
	private String id;
	/**
	 * Type of net in case it is part of a rule. May be "L", "K", or "R"
	 */
	private String nettype;
	
	/**
	 * Page with information
	 */
	Page page;
	

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	@XmlAttribute
	public void setNettype(String nettype){
		this.nettype=nettype;
	}
	
	public String getNettype(){
		return this.nettype;
	}
}
