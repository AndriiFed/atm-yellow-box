package cashmachine.atmstorage;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class AtmSafePropertiesTest {


  @Test
  public void testAtmSafeProperties() throws Exception {

    assertThat(AtmStorageProperties.getStorageType(), is("object"));
  }

}
