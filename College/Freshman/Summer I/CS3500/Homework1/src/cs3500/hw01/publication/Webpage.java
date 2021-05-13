package cs3500.hw01.publication;

/**
 * Represents the bibliographical information of a website.
 */
public class Webpage implements Publication {

  private final String title;
  private final String url;
  private final String retrieved;

  /**
   * Constructs a website.
   *
   * @param title     The website's title.
   * @param url       The URL of the website.
   * @param retrieved The date it was accessed.
   */

  public Webpage(String title, String url, String retrieved) {
    this.title = title;
    this.url = url;
    this.retrieved = retrieved;
  }

  /**
   * Generates an APA citation for this webpage.
   *
   * @return The APA citation generated.
   */
  @Override
  public String citeApa() {
    return this.title + ". Retrieved " + this.retrieved + ", from " + this.url + ".";
  }

  /**
   * Generates an MLA citation for this webpage.
   *
   * @return The MLA citation generated.
   */
  @Override
  public String citeMla() {
    return "\"" + this.title + ".\" Web. " + this.retrieved + " <" + this.url + ">.";
  }
}
