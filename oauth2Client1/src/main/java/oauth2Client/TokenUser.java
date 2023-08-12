package oauth2Client;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import oauth2Client.client.TokenChangeObserver;

public class TokenUser  implements TokenChangeObserver<AccessToken>{
//	private static final Logger logger = LoggerFactory.getLogger(TokenUser.class);
	 private static final Logger logger = 	            LogManager.getLogger( TokenUser.class );
	@Override
	public void tokenChanged(TemporalAccessToken<AccessToken> newToken, TemporalAccessToken<AccessToken> oldToken) {
		// TODO Auto-generated method stub
		logger.info("token_changed");
		//logger.info("token_new:"+newToken);
		//logger.info("token_old:"+oldToken);
		
	}

}
