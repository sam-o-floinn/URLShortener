package samofloinn.github.URLShortener;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UrlShortenerApplicationTests {

	@Test
	void contextLoads() {
	}

	// == Tests to shorten URL ==

    @Test
    public void actualUrlReturns200()
            throws IOException {
        // == Given ==
        String shortUrl = "https://www.chesterfield.co.uk/events/extreme-bike-battle-derbyshire";
        HttpUriRequest request = new HttpGet(shortUrl);

        // == When ==
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // == Then ==
        assertThat(httpResponse.getStatusLine().getStatusCode() == (200));
    }

    @Test
    public void notUrlReturns500()
            throws IOException {
        // == Given ==
        String notAUrl = "hi";
        HttpUriRequest request = new HttpGet(notAUrl);

        // == Assert ==
        Assertions.assertThrows(ClientProtocolException.class, () -> {
           HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        });
    }


	// == Tests to resolve URL code ==

	@Test
	public void badCodeReturns500()
			throws IOException {
		// == Given ==
		String shortUrl = "http://localhost:8080/go/fake_";
        HttpUriRequest request = new HttpGet(shortUrl);
        // == When ==
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

		// == Then ==
        assertThat(httpResponse.getStatusLine().getStatusCode() == (500));
	}

    @Test
    public void goodCodeReturns200()
            throws IOException {
        // == Given ==
        String shortUrl = "http://localhost:8080/go/co21W"; //see data.sql, should match the one default item in the table
        HttpUriRequest request = new HttpGet(shortUrl);
        System.out.println("shortUrl = " + shortUrl);

        // == When ==
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // == Then ==
        assertThat(httpResponse.getStatusLine().getStatusCode() == (200));
    }



}
