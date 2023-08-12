package oauth2Client.client;

public interface RetryPolicy {
	long periodBetweenRetries();

	long maxRetries();

	boolean onException(Throwable t);
}