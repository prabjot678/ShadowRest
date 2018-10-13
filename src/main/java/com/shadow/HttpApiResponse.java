package com.shadow;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HttpApiResponse {
	
	
    private String description;

	
    private String code;
    
    public HttpApiResponse() {
		super();
	}

   
	public HttpApiResponse(String description, String code) {
		super();
		this.description = description;
		this.code = code;
	}

	 @XmlElement
	public String getDescription() {
		return description;
	}


	
	public void setDescription(String description) {
		System.out.println("setting property " + description);
		this.description = description;
	}

	
	 @XmlElement
	public String getCode() {
		return code;
	}


	
	public void setCode(String code) {
		this.code = code;
	}
	
	


	


	@Override
	public String toString() {
		return "HttpApiResponse [description=" + description + ", code=" + code + "]";
	}
	
	
    
    
  

}
