package cashmachine.atmstorage;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class AtmSafePropertiesTest {


  @Test
  public void testAtmSafeProperties() throws Exception {
    AtmStorageProperties properties = new AtmStorageProperties("test");
    AtmStorageProperties properties2 = new AtmStorageProperties();

    assertThat(properties.getProperty("storageType"),  anyOf(containsString("memory"), containsString("object"), containsString("xml"), containsString("json"), containsString("h2")));
    assertThat(properties2.getProperty("storageType"),  anyOf(containsString("memory"), containsString("object"), containsString("xml"), containsString("json"), containsString("h2")));

  }

}
