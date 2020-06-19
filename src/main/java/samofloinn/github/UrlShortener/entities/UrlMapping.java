package samofloinn.github.UrlShortener.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * UrlMapping. An object that contains info on a longUrl/shortUrl pair.
 * Also includes additional statistics like the times a link has been clicked, the date created on, etc.
 *
 * @author Sam O'Floinn (samofloinn@gmail.com)
 * @version 1.0
 * @since 1.0
 */

@Data
@Entity
public class UrlMapping {

    // == fields ==
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String shortUrl;
    private String longUrl;
    private int timesClicked;
    private Date dateCreated;
    private Date lastClicked;

    // == constructors ==

    /**
     * Constructor to initialise UrlMapping
     * @param shortUrl the shortUrl that, when visited, will redirect to the longUrl
     * @param longUrl the URL that the shortUrl will redirect to
     * @param dateCreated the date this object was created on
     */
    public UrlMapping(String shortUrl, String longUrl, Date dateCreated) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.dateCreated = dateCreated;
    }

    public UrlMapping() {
    }

    // == functions ==

    /**
     * click: updates fields as they should be when this UrlMapping's shortUrl is visited by some user
     */
    public void click() {
        timesClicked++;
        lastClicked = new Date();
    }
}
