package cashmachine.atmcommand;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class AtmCommandTest {
  @Test
  public void createCommandTest() throws Exception {
    AtmCommand command = AtmCommand.createCommand("+", "100", "5");
    //assertThat(command instanceof AtmCommand, is(true));
  }
}
