package samofloinn.github.URLShortener;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Slf4j
@Getter
@Setter
@Entity
public class UrlObject {

    // == fields ==
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String shortUrl;
    private String longUrl;
    int timesClicked;
    private Date dateCreated;
    private Date lastClicked;

    // == constructors ==
    public UrlObject(String shortUrl, String longUrl, Date dateCreated) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.dateCreated = dateCreated;
    }

    public UrlObject() {
    }

    // == functions ==
    public void click() {
        timesClicked++;
        lastClicked = new Date();
    }
}
