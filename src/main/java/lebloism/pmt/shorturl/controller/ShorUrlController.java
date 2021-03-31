package lebloism.pmt.shorturl.controller;

import lebloism.pmt.shorturl.dao.ShortUrlRepository;
import lebloism.pmt.shorturl.exception.ShortUrlIdMismatchException;
import lebloism.pmt.shorturl.exception.ShortUrlNotFoundException;
import lebloism.pmt.shorturl.model.ShortUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/short_url")
public class ShorUrlController {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @GetMapping
    public Iterable findAll() {
        return shortUrlRepository.findAll();
    }

    @GetMapping("/{id}")
    public ShortUrl findOne(@PathVariable Long id) {
        return shortUrlRepository.findById(id)
                .orElseThrow(ShortUrlNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShortUrl create(@RequestBody ShortUrl shortUrl) {
        return shortUrlRepository.save(shortUrl);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        shortUrlRepository.findById(id)
                .orElseThrow(ShortUrlNotFoundException::new);
        shortUrlRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ShortUrl updateShortUrl(@RequestBody ShortUrl shortUrl, @PathVariable Long id) {
        if (shortUrl.getId() != id) {
            throw new ShortUrlIdMismatchException();
        }
        shortUrlRepository.findById(id)
                .orElseThrow(ShortUrlNotFoundException::new);
        return shortUrlRepository.save(shortUrl);
    }
}
