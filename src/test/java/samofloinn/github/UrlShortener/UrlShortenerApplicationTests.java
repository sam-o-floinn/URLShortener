package samofloinn.github.UrlShortener;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import samofloinn.github.UrlShortener.controllers.UrlShortenerController;
import samofloinn.github.UrlShortener.entities.UrlMapping;
import samofloinn.github.UrlShortener.repositories.UrlRepository;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class UrlShortenerApplicationTests {

    @Autowired
    private UrlShortenerController urlShortenerController;
    @Autowired
    private UrlRepository urlRepository;

	@Test
	void contextLoads() {
	    assertThat(urlShortenerController).isNotNull();
	}

	// == Tests to shorten URL ==

    /**
     * A working link should throw no exception if used as a 'longUrl' arg, and appear in the DB just fine
     * @throws IOException
     */
    @Test
    public void shortenUrl_ActualUrl_ReturnsTrue()
            throws IOException {
        // == Given ==
        final String longUrl = "http://www.stackoverflow.com";
        final String shortUrl = "test";
        String routeShortUrl = "http://localhost:8080/shorten?longUrl=" + longUrl + "&shortUrl=test";
        urlShortenerController.routeShortUrl(longUrl, shortUrl);

        // == When ==
        UrlMapping mapping = urlRepository.findOneByShortUrl("test");
        if (mapping == null)
            log.info("Mapping is null for shortUrl " + shortUrl);
        else
            log.info("Mapping is not null: " + mapping.getLongUrl());

        // == Then ==
        assertThat(mapping).isNotNull();
    }

    /**
     * Verifies that a broken link will not work.
     * @throws IOException
     */
    @Test
    public void shortenUrl_NotUrl_HttpHostConnectException()
            throws HttpHostConnectException {
        // == Given ==
        final String notAUrl = "https://stackoverlfow.com/";
        String routeShortUrl = "http://localhost:8080/shorten?longUrl=" + notAUrl;
        HttpUriRequest request = new HttpGet(notAUrl);

        // == Assert ==
        Assertions.assertThrows(HttpHostConnectException.class, () -> {
           HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        });
    }


    /**
     * A code that does not exist in the DB will return a bad code
     * @throws IOException
     */
	@Test
	public void goToUrl_BadShortUrl_Returns500()
			throws IOException {
		// == Given ==
		String shortUrl = "http://localhost:8080/go/fake_";
        HttpUriRequest request = new HttpGet(shortUrl);
        // == When ==
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

		// == Then ==
        assertThat(httpResponse.getStatusLine().getStatusCode() == (500));
	}

    /**
     * goodCode_Returns200: A code that DOES exist will work as normal
     * @throws IOException
     */
    @Test
    public void goToUrl_GoodShortUrl_Returns200()
            throws IOException {
        // == Given ==
        String shortUrl = "http://localhost:8080/go/co21W"; //see data.sql, should match the one default item in the table
        HttpUriRequest request = new HttpGet(shortUrl);

        // == When ==
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // == Then ==
        assertThat(httpResponse.getStatusLine().getStatusCode() == (200));
    }

}
