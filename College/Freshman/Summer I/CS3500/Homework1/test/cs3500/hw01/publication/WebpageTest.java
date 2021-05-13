package cs3500.hw01.publication;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for webpage.
 * Unit tests ensure that web pages can be cited correctly in MLA and APA style.
 */
public class WebpageTest extends TestCase {
  Publication google;
  Publication wiki;

  @Before
  public void setUp() throws Exception {
    super.setUp();
    this.google = new Webpage("Google", "https://google.com", "May 11, 2021");
    this.wiki = new Webpage("Wikipedia", "https://wikipedia.com/computer_science", "May 8, 2002");
  }

  @Test
  public void testCiteApa() {
    assertEquals("Google. Retrieved May 11, 2021, from https://google.com.", this.google.citeApa());
    assertEquals("Wikipedia. Retrieved May 8, 2002, from https://wikipedia.com/computer_science.",
        this.wiki.citeApa());
  }

  @Test
  public void testCiteMla() {
    assertEquals("\"Google.\" Web. May 11, 2021 <https://google.com>.", this.google.citeMla());
    assertEquals("\"Wikipedia.\" Web. May 8, 2002 <https://wikipedia.com/computer_science>.", this.wiki.citeMla());
  }
}