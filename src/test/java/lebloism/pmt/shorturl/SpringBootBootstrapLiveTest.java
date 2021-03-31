package lebloism.pmt.shorturl;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lebloism.pmt.shorturl.model.ShortUrl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringBootBootstrapLiveTest {

    private static final String API_ROOT
            = "http://localhost:8081/short_url";

    private ShortUrl createRandomShortUrl() {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setShortUrl(randomAlphabetic(10));
        shortUrl.setLongUrl(randomAlphabetic(15));
        return shortUrl;
    }

    private String createShortUrlAsUri(ShortUrl shortUrl) {
       Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(shortUrl)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("id");
    }

    @Test
    public void whenGetAllShortUrl_thenOK() {
        Response response = RestAssured.get(API_ROOT);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetCreatedShortUrlById_thenOK() {
        ShortUrl shortUrl = createRandomShortUrl();
        String location = createShortUrlAsUri(shortUrl);
        Response response = RestAssured.get(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(shortUrl.getShortUrl(), response.jsonPath()
                .get("shortUrl"));
    }

    @Test
    public void whenGetNotExistShortUrlById_thenNotFound() {
        Response response = RestAssured.get(API_ROOT + "/" + 654654654);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenCreateNewShortUrl_thenCreated() {
        ShortUrl shortUrl = createRandomShortUrl();
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(shortUrl)
                .post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidShortUrl_thenError() {
        ShortUrl shortUrl = createRandomShortUrl();
        shortUrl.setLongUrl(null);
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(shortUrl)
                .post(API_ROOT);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedShortUrl_thenUpdated() {
        ShortUrl shortUrl = createRandomShortUrl();
        String location = createShortUrlAsUri(shortUrl);
        shortUrl.setId(Long.parseLong(location.split("short_url/")[1]));
        shortUrl.setLongUrl("newLongUrl");
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(shortUrl)
                .put(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("newLongUrl", response.jsonPath()
                .get("longUrl"));
    }

    @Test
    public void whenDeleteCreatedShortUrl_thenOk() {
        ShortUrl shortUrl = createRandomShortUrl();
        String location = createShortUrlAsUri(shortUrl);
        Response response = RestAssured.delete(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
}
