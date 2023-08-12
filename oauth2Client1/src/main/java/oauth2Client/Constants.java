package oauth2Client;
//https://github.com/SheekhaJ/HPALMJava/blob/b7bc76287578059a84fb444182d7454432eaada9/HPALMJava/src/main/java/com/authentication/Constants.java
public final class Constants {	
	private static String hostURL;	
	private static String baseurl;
	
	private static String clientId;
	private static String clientSecret;
	static{		
		hostURL="http://192.168.243.142:8080/iiq";
		baseurl="http://192.168.243.142:8080/iiq";
		clientId="3waQxUQzgaJjZ9vap6HvD0W5px5DeQzV";
		clientSecret="bXo166Ka4Eq3jiwE";	
	}
	public static String getHostURL() {
		return hostURL;
	}
	
	public static String getClientId() {
		return clientId;
	}

	public static String getClientSecret() {
		return clientSecret;
	}
	public static String getBaseurl() {
		return baseurl;
	}

	
}