import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class FirstTest {
  @Test
  public void test1() {

    assertThat(true, is(true));
  }
}
