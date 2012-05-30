package persistence;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public	class Net{
	
	private String id;
	private String nettype;
	
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
