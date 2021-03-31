package lebloism.pmt.shorturl;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lebloism.pmt.shorturl.dto.ShortUrlCreationDto;
import lebloism.pmt.shorturl.dto.ShortUrlUpdateDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringBootBootstrapLiveTest {

    private static final String API_ROOT
            = "http://localhost:8081/short_url";

    private ShortUrlCreationDto createRandomShortUrlCreationDto() {
        ShortUrlCreationDto dto = new ShortUrlCreationDto();
        dto.setLongUrl(randomAlphabetic(15));
        return dto;
    }

    private String createShortUrlAsUri(ShortUrlCreationDto dto) {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dto)
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
        ShortUrlCreationDto dto = createRandomShortUrlCreationDto();
        String location = createShortUrlAsUri(dto);
        Response response = RestAssured.get(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(dto.getLongUrl(), response.jsonPath()
                .get("longUrl"));
    }

    @Test
    public void whenGetDestination_thenOK() {
        ShortUrlCreationDto dto = createRandomShortUrlCreationDto();

        Response creationResponse = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dto)
                .post(API_ROOT);
        String shortUrl = creationResponse.jsonPath().get("shortUrl");


        Response destinationResponse = RestAssured.get(API_ROOT+"/destination/"+shortUrl);

        assertEquals(HttpStatus.OK.value(), destinationResponse.getStatusCode());
        assertEquals(dto.getLongUrl(), destinationResponse.getBody().print());
    }

    @Test
    public void whenGetNotExistShortUrlById_thenNotFound() {
        Response response = RestAssured.get(API_ROOT + "/" + 654654654);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenCreateNewShortUrl_thenCreated() {
        ShortUrlCreationDto dto = createRandomShortUrlCreationDto();
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dto)
                .post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidShortUrl_thenError() {
        ShortUrlCreationDto dto = createRandomShortUrlCreationDto();
        dto.setLongUrl(null);
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dto)
                .post(API_ROOT);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedShortUrl_thenUpdated() {
        ShortUrlCreationDto dto = createRandomShortUrlCreationDto();
        String location = createShortUrlAsUri(dto);
        ShortUrlUpdateDto updateDto = new ShortUrlUpdateDto();
        updateDto.setLongUrl("newLongUrl");
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateDto)
                .put(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("newLongUrl", response.jsonPath()
                .get("longUrl"));
    }

    @Test
    public void whenDeleteCreatedShortUrl_thenOk() {
        ShortUrlCreationDto dto = createRandomShortUrlCreationDto();
        String location = createShortUrlAsUri(dto);
        Response response = RestAssured.delete(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
}
