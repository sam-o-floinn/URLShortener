package samofloinn.github.UrlShortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import samofloinn.github.UrlShortener.entities.UrlMapping;

/**
 * UrlRepository: a repository of UrlMapping objects.
 * Has a method to return a single object when given a shortUrl
 *
 * @author Sam O'Floinn (samofloinn@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@RepositoryRestResource(collectionResourceRel = "urls", path = "urls")
public interface UrlRepository extends JpaRepository<UrlMapping, Long> {
    /**
     * returns a UrlMapping object from the repository, if given its matching shortUrl. Returns null if no shortUrl found
     * @param shortUrl a shortUrl that matches the longUrl of an UrlMapping object in the repo
     * @return
     */
    UrlMapping findOneByShortUrl(@Param("shortUrl") String shortUrl);
}
