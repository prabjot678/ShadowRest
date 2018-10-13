package com.shadow.service.response;

public class AttachmentResponse extends CommonResponse {

	private String id;
	private String name;
	private String URL;
	private String size;
	private String attachmentUploadedDate;
	private String attachmentLastModifiedDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getAttachmentUploadedDate() {
		return attachmentUploadedDate;
	}

	public void setAttachmentUploadedDate(String attachmentUploadedDate) {
		this.attachmentUploadedDate = attachmentUploadedDate;
	}

	public String getAttachmentLastModifiedDate() {
		return attachmentLastModifiedDate;
	}

	public void setAttachmentLastModifiedDate(String attachmentLastModifiedDate) {
		this.attachmentLastModifiedDate = attachmentLastModifiedDate;
	}

}
