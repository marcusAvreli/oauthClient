package oauth2Client.client;

public class NoRetryPolicy implements RetryPolicy{
	public long periodBetweenRetries(){
		return 0L;
	}
	public long maxRetries(){
		return 15;
	}
	public boolean onException(Throwable t){
		return false;
	}
}