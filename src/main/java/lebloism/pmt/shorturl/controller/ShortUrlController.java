package lebloism.pmt.shorturl.controller;

import lebloism.pmt.shorturl.dao.ShortUrlRepository;
import lebloism.pmt.shorturl.dto.ShortUrlCreationDto;
import lebloism.pmt.shorturl.dto.ShortUrlUpdateDto;
import lebloism.pmt.shorturl.exception.ShortUrlNotFoundException;
import lebloism.pmt.shorturl.model.ShortUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/**
 * To manage ShortUrl objects
 */
@RestController
@RequestMapping("/short_url")
public class ShortUrlController {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    /**
     * Retrieve all ShortUrl objects
     * @return array containing all existing ShortUrl objects
     */
    @GetMapping
    public Iterable<ShortUrl> findAll() {
        return shortUrlRepository.findAll();
    }

    /**
     * Get a ShortUrl object by its id
     * @param id the id of the ShortUrl object
     * @return the ShortUrl object
     * @throws ShortUrlNotFoundException if not found
     */
    @GetMapping("/{id}")
    public ShortUrl findOne(@PathVariable Long id) {
        return shortUrlRepository.findById(id)
                .orElseThrow(ShortUrlNotFoundException::new);
    }

    /**
     * Get the long url associated to a short url
     * @param shortUrl the short url (string)
     * @return the long url
     * @throws ShortUrlNotFoundException if not found
     */
    @GetMapping("/destination/{shortUrl}")
    public String getDestination(@PathVariable String shortUrl) {
        return shortUrlRepository.findByShortUrl(shortUrl)
                .orElseThrow(ShortUrlNotFoundException::new).getLongUrl();
    }

    /**
     * Create a ShortUrl object for the provided long url
     * @param dto json containing the field "longUrl"
     * @return the ShortUrl object
     * @throws MalformedURLException if longUrl is not a valid URL
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShortUrl create(@RequestBody ShortUrlCreationDto dto) throws MalformedURLException {
        URL longUrl = new URL(dto.getLongUrl());
        String shortUrlString = randomAlphabetic(8).toUpperCase();
        ShortUrl shortUrl = new ShortUrl(shortUrlString, longUrl.toExternalForm());
        return shortUrlRepository.save(shortUrl);
    }

    /**
     * Delete a ShortUrl object
     * @param id the id of the ShortUrl object
     * @throws ShortUrlNotFoundException if not found
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        shortUrlRepository.findById(id)
                .orElseThrow(ShortUrlNotFoundException::new);
        shortUrlRepository.deleteById(id);
    }

    /**
     * Update a ShortUrl object. The fields are modified only if provided.
     * @param id the id of the ShortUrl object
     * @param dto json containing the fields "longUrl" and/or "shortUrl"
     * @throws ShortUrlNotFoundException if not found
     * @throws MalformedURLException if longUrl is not a valid URL
     */
    @PutMapping("/{id}")
    public ShortUrl updateShortUrl(@RequestBody ShortUrlUpdateDto dto, @PathVariable Long id) throws MalformedURLException {

        ShortUrl shortUrl = shortUrlRepository.findById(id)
                .orElseThrow(ShortUrlNotFoundException::new);
        if (dto.getShortUrl() != null) {
            shortUrl.setShortUrl(dto.getShortUrl());
        }
        if (dto.getLongUrl() != null) {
            URL longUrl = new URL(dto.getLongUrl());
            shortUrl.setLongUrl(longUrl.toExternalForm());
        }
        return shortUrlRepository.save(shortUrl);
    }
}
