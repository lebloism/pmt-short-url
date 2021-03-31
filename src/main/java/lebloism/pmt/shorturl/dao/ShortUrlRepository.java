package lebloism.pmt.shorturl.dao;

import lebloism.pmt.shorturl.model.ShortUrl;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShortUrlRepository extends CrudRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByShortUrl(String shortUrl);
}
