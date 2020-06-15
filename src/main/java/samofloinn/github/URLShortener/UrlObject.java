package samofloinn.github.URLShortener;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Slf4j
@Entity
public class UrlObject {

    // == fields ==
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String shortUrl;
    private String longUrl;
    int timesClicked;

    // == constructors ==
    public UrlObject(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

    public UrlObject() {
    }

    // == getters ==
    public long getId() { return id; }
    public String getShortUrl() { return shortUrl; }
    public String getLongUrl() { return longUrl; }
    public int getTimesClicked() { return timesClicked; }

    // == setters ==
    public void setId(long newId) { id = newId; }
    public void setShortUrl(String newShort) { shortUrl = newShort; }
    public void setTimesClicked(int times) { timesClicked = times; }
    public void setLongUrl(String newLong) { longUrl = newLong; }

    // == functions ==
    public void click() { timesClicked++; }
}
