package lebloism.pmt.shorturl.model;

import javax.persistence.*;

@Entity
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String shortUrl;

    @Column(nullable = false)
    private String longUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((longUrl == null) ? 0 : longUrl.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((shortUrl == null) ? 0 : shortUrl.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShortUrl other = (ShortUrl) obj;
        if (longUrl == null) {
            if (other.longUrl != null)
                return false;
        } else if (!longUrl.equals(other.longUrl))
            return false;
        if (id != other.id)
            return false;
        if (shortUrl == null) {
            if (other.shortUrl != null)
                return false;
        } else if (!shortUrl.equals(other.shortUrl))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ShortUrl [id=" + id + ", shortUrl=" + shortUrl + ", longUrl=" + longUrl + "]";
    }

}
