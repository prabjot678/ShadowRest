package com.shadow;

public class Recipient {
	
	 private String mobileNumber;

	    private String messageId;

	    public String getMobileNumber ()
	    {
	        return mobileNumber;
	    }

	    public void setMobileNumber (String mobileNumber)
	    {
	        this.mobileNumber = mobileNumber;
	    }

	    public String getMessageId ()
	    {
	        return messageId;
	    }

	    public void setMessageId (String messageId)
	    {
	        this.messageId = messageId;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [mobileNumber = "+mobileNumber+", messageId = "+messageId+"]";
	    }

}
