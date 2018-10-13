package com.shadow;

	//
	// Example of sending messages through the mGage Connect message delivery API with Java.
	//
	import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
	public class DirectTEXTSend
	{
	public static void main(String[] args)
	{
	try {
	final String user = "conduent_bw2018";
	final String pass = "bw2018";
	String baseURL = "https://secure-mrr.air2web.com/a2w_preRouter/httpApiRouter?";
	String replyTo = "12345";
	String recipient = "+919716890616";
	String messageBody = "Test message sent via mGage DirectTEXT using Java";
	// URL encode message body
	messageBody = URLEncoder.encode(messageBody, "UTF-8");
	// Construct URL
	StringBuffer URI = new StringBuffer();
	URI.append(baseURL);
	URI.append("reply_to=" + replyTo);
	URI.append("&recipient=" + recipient);
	URI.append("&body=" + messageBody);
	// Create authenticator for http request
	String result = "";
	Authenticator.setDefault(new Authenticator() {
	protected PasswordAuthentication getPasswordAuthentication() {
	return new PasswordAuthentication (user, new
	String(pass).toCharArray());
	}
	});
	// Open connection and send request
	URL url = new URL(URI.toString());
	URLConnection conn = url.openConnection ();
	// Get the response
	StringBuffer data = new StringBuffer();
	BufferedReader rd = new BufferedReader(new
	InputStreamReader(conn.getInputStream()));
	StringBuffer sb = new StringBuffer();
	String line;
	while ((line = rd.readLine()) != null) {
	sb.append(line);
	}
	rd.close();
	
	// Print results
	result = sb.toString();
	
	
	 StringReader reader = new StringReader(result);
     
	 JAXBContext jaxbContext = JAXBContext.newInstance(HttpApiResponse.class);
     Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
     HttpApiResponse httpApiResponse = (HttpApiResponse) unmarshaller.unmarshal(reader);
     System.out.println("Result---->" + httpApiResponse.getDescription());
	
	
	
	String responseCode = "";
	String responseMessage = "";

	System.out.println("result : "+ result);
	Matcher matcher =Pattern.compile("<code>(\\d+)<\\/code>").matcher(result);
	if (matcher.find()) {
	
	String code = matcher.group(1);
	System.out.println("code : "+ code);
	}
			
	matcher= Pattern.compile("<description>([\\s\\S]+)<\\/description>").matcher(result);
	if (matcher.find()) {
	
	String description = matcher.group(1);
	System.out.println("description : "+ description);
	}
	
	matcher= Pattern.compile("<mobileNumber>([\\s\\S]+)<\\/mobileNumber>").matcher(result);
	if (matcher.find()) {
	
	String mobileNumber = matcher.group(1);
	System.out.println("mobileNumber : "+ mobileNumber);
	}
	
	matcher= Pattern.compile("<messageId>([\\s\\S]+)<\\/messageId>").matcher(result);
	if (matcher.find()) {
	
	String messageId = matcher.group(1);
	System.out.println("messageId : "+ messageId);
	}
	
	if (responseCode.equals("100")) {
	System.out.println("Message sent successfully!\n" + result +
	"\n");
	}
	else {
	System.out.println("Error occurred: " + responseCode + ", " +
	responseMessage + "\n" + result + "\n");
	}
		}
	catch (Exception e) {
	e.printStackTrace();
	}
	}
	
	
	}


