package oauth2Client.client;



import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import oauth2Client.AccessToken;
import oauth2Client.http.TokenServiceHttpClient;
import oauth2Client.oauth2.AccessTokenGrantRequest;

public class OAuthTokenServiceDelegate implements TokenService{
	//private static final Logger logger = LoggerFactory.getLogger(OAuthTokenServiceDelegate.class);
	 private static final Logger logger = 	            LogManager.getLogger( OAuthTokenServiceDelegate.class );
	private AccessTokenGrantRequest grant;
	private TokenServiceHttpClient client;
	//private RefreshTokenGrantRequest refreshTokenGrantRequest;
	private String pathToTokenEndpoint;
	public OAuthTokenServiceDelegate(final AccessTokenGrantRequest grant,TokenServiceHttpClient client,  String pathToTokenEndpoint) {
		this.grant = grant;
		this.client = client;
		
		this.pathToTokenEndpoint = pathToTokenEndpoint;
	}
	@Override
	public <T extends AccessToken> T fetch() {
		// TODO Auto-generated method stub
		logger.info("fetch_called");
		T token = client.post(pathToTokenEndpoint, grant);
		return token;
	}

	@Override
	public <T extends AccessToken> T refresh(String refreshTokenString) {
		logger.info("refresh_called");
		T token = client.post(pathToTokenEndpoint, grant);
		return  token;
	}

}
