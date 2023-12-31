package oauth2Client;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.json.JSONObject;


import oauth2Client.client.TokenChangeObserver;
import oauth2Client.commons.util.Observable;
import oauth2Client.http.TokenServiceHttpClient;
import oauth2Client.oauth2.AccessTokenGrantRequest;

public class MyClient implements TokenServiceHttpClient,Observable<TokenChangeObserver<MyToken>>{
	//private static final Logger logger = LoggerFactory.getLogger(MyClient.class);
	 private static final Logger logger = 	            LogManager.getLogger( MyClient.class );
	protected static Client client;
	protected static WebTarget webTarget;
	@SuppressWarnings("rawtypes")
	private Class tokenClass;
	private static final int DEFAULT_CONNECT_TIMEOUT = 600;
	private static final int DEFAULT_READ_TIMEOUT = 6000;
	private static int readTimeout = DEFAULT_READ_TIMEOUT;
	private static int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
	private List<TokenChangeObserver<AccessToken>> listeners = new ArrayList<>();
	private TokenChangeObserver tokenUser;
	
	static{
		
		//client=ClientBuilder.newClient();
		//webTarget=client.target(Constants.getBaseurl());
		FileInputStream myKeys;
		try {
			
			// Get hold of the default trust manager
			
		//	SSLContext sslContext = SSLContext.getInstance("TLS");
			myKeys = new FileInputStream("C:/Users/User/eclipse-workspace/mystore.jks");
		

		// Do the same with your trust store this time
		// Adapt how you load the keystore to your needs
		KeyStore myTrustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		myTrustStore.load(myKeys, "changeit".toCharArray());

		myKeys.close();

		TrustManagerFactory tmf = TrustManagerFactory
		    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(myTrustStore);
		
		// Get hold of the default trust manager
		X509TrustManager myTm = null;
		for (TrustManager tm : tmf.getTrustManagers()) {
		    if (tm instanceof X509TrustManager) {
		        myTm = (X509TrustManager) tm;
		        break;
		    }
		}
		X509TrustManager defaultTm = null;
		for (TrustManager tm : tmf.getTrustManagers()) {
		    if (tm instanceof X509TrustManager) {
		        defaultTm = (X509TrustManager) tm;
		        break;
		    }
		}
		// Wrap it in your own class.
		final X509TrustManager finalDefaultTm = defaultTm;
		final X509TrustManager finalMyTm = myTm;
		X509TrustManager customTm = new X509TrustManager() {
		    @Override
		    public X509Certificate[] getAcceptedIssuers() {
		        // If you're planning to use client-cert auth,
		        // merge results from "defaultTm" and "myTm".
		        return finalDefaultTm.getAcceptedIssuers();
		    }

		    @Override
		    public void checkServerTrusted(X509Certificate[] chain,
		            String authType) throws CertificateException {
		        try {
		            finalMyTm.checkServerTrusted(chain, authType);
		        } catch (CertificateException e) {
		            // This will throw another CertificateException if this fails too.
		            finalDefaultTm.checkServerTrusted(chain, authType);
		        }
		    }

		    @Override
		    public void checkClientTrusted(X509Certificate[] chain,
		            String authType) throws CertificateException {
		        // If you're planning to use client-cert auth,
		        // do the same as checking the server.
		        finalDefaultTm.checkClientTrusted(chain, authType);
		    }
		};

		ClientConfig config = new ClientConfig();
		
		config.property(ClientProperties.CONNECT_TIMEOUT, connectTimeout);
		config.property(ClientProperties.READ_TIMEOUT, readTimeout);
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, new TrustManager[] { customTm }, null);

		// You don't have to set this as the default context,
		// it depends on the library you're using.
		SSLContext.setDefault(sslContext);
		//HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
		HostnameVerifier allHostsValid = (hostname, session) -> true;
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

		ClientBuilder builder = ClientBuilder.newBuilder().withConfig(config);
		builder.sslContext(sslContext);
		client = builder.build();

	}
	 catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException | KeyManagementException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	@SuppressWarnings("unchecked")
	public  <T extends AccessToken> MyClient(Class<T> tokenClass) {
		this.tokenClass = tokenClass;
	}
	@Override
	public <T extends AccessToken> T post(String path, AccessTokenGrantRequest payload) {
		//construct request path
		ClientConfig clientConfig = new ClientConfig();
		logger.info("post_called");

		
		
		  String secret =  "i55V7tldP1kyetOCTnHzNRzBLidoCTxl" + ":" + "Mgw4jSf1yV5ouRE3";
	
		 Charset charset = Charset.forName("UTF-8");

		    byte[] byteArrray = secret.getBytes(charset);
		   
		   
		    
		    String[] resultInts  = new String[byteArrray.length];
		    StringBuilder stringE = new StringBuilder(); 
		    for (int i=0;i<byteArrray.length;i++) {
		    	resultInts[i]=String.valueOf(byteArrray[i]);
		    	
		    	stringE.append(resultInts[i]+",");
		    }
		    String resultString = stringE.toString();
		    resultString =  resultString.substring(0,resultString.length()-1);
		  
		  Response response = (Response) client.target(UriBuilder.fromUri("http://192.168.243.142:8080/iiq/oauth2/generateToken").build()).request("application/json"). // JSON Request Type
				accept("application/json"). // Response access type - application/scim+json
				header("Authorization", "Basic "+resultString) // Authorization header goes here
				.post(Entity.json(null)); // body with grant type
			String token2 = response.readEntity(String.class); // reading response as string format
			
			JSONObject jsonObject = new JSONObject(token2); 
			logger.info("token:"+token2);
				T token = (T) new MyToken(jsonObject.getString("access_token"),jsonObject.getLong("expires_in"),jsonObject.getString("access_token"),null,null);
				
				return token;
	}
	
	
	@Override
	public void notify(Consumer algorithm) {
		// TODO Auto-generated method stub
		logger.info("notify");
		this.listeners.forEach(algorithm);
		
		
	}
	@Override
	public TokenChangeObserver attach(TokenChangeObserver observer) {
		logger.info("attach_token_change_observer");
		listeners.add(observer);
		return observer;
	}
	@Override
	public void detach(TokenChangeObserver observer) {
		// TODO Auto-generated method stub
		
	}
	private static StringBuilder getMimeBuffer() {
	    StringBuilder buffer = new StringBuilder();
	    for (int count = 0; count < 10; ++count) {
	        buffer.append(UUID.randomUUID().toString());
	    }
	    return buffer;
	}
}
