package com.shadow;

public class Recipients {
	
	private Recipient recipient;

    public Recipient getRecipient ()
    {
        return recipient;
    }

    public void setRecipient (Recipient recipient)
    {
        this.recipient = recipient;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [recipient = "+recipient+"]";
    }

}
