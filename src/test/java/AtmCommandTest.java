package cashmachine.atmcommand;

import cashmachine.exceptions.CommandValidationException;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class AtmCommandTest {
  @Test
  public void createPutCashCommandTest() throws Exception {
    AtmCommand command = AtmCommand.createCommand("+", "USD", "100", "5");
    assertThat(command instanceof PutCashCommand, is(true));
  }

  @Test
  public void createGetCashCommandTest() throws Exception {
    AtmCommand command = AtmCommand.createCommand("-", "USD", "200");
    assertThat(command instanceof GetCashCommand, is(true));
  }

  @Test
  public void createPrintCashCommandTest() throws Exception {
    AtmCommand command = AtmCommand.createCommand("?");
    assertThat(command instanceof PrintCashCommand, is(true));
  }

  @Test
  public void correctMoneyValuesTest() throws Exception {
    AtmCommand.createCommand("+", "USD", "1", "2");
    AtmCommand.createCommand("+", "EUR", "5", "2");
    AtmCommand.createCommand("+", "USD", "10", "2");
    AtmCommand.createCommand("+", "OLO", "50", "2");
    AtmCommand.createCommand("+", "USD", "100", "2");
    AtmCommand.createCommand("+", "USD", "500", "2");
    AtmCommand.createCommand("+", "USD", "1000", "2");
    AtmCommand.createCommand("+", "USD", "5000", "2");
    assertThat(true, is(true));
  }

  @Test
  public void PutCashCommandTest() throws Exception {
    PutCashCommand command = (PutCashCommand)AtmCommand.createCommand("+", "USD", "5000", "2");
    assertThat(command.getMethod(), is("putCash"));
    assertThat(command.moneyPack.getValue(), is(5000));
    assertThat(command.moneyPack.getCurrency(), is("USD"));
    assertThat(command.moneyPack.getAmount(), is(2));
  }

  @Test
  public void GetCashCommandTest() throws Exception {
    GetCashCommand command = (GetCashCommand)AtmCommand.createCommand("-", "XXX", "700");
    assertThat(command.getMethod(), is("getCash"));
    assertThat(command.amount, is(700));
    assertThat(command.currency, is("XXX"));
  }

  @Test
  public void PrintCashCommandTest() throws Exception {
    PrintCashCommand command = (PrintCashCommand)AtmCommand.createCommand("?");
    assertThat(command.getMethod(), is("printCash"));
  }

  @Test (expected = CommandValidationException.class)
  public void validationTest1() throws Exception {
    AtmCommand.createCommand("?", "100");
  }

  @Test (expected = CommandValidationException.class)
  public void validationTest2() throws Exception {
    AtmCommand.createCommand("+", "USSD", "50", "1");
  }

  @Test (expected = CommandValidationException.class)
  public void validationTest3() throws Exception {
    AtmCommand.createCommand("+", "USD", "30", "2");
  }

  @Test (expected = CommandValidationException.class)
  public void validationTest4() throws Exception {
    AtmCommand.createCommand("+", "USD", "30000", "2");
  }

  @Test (expected = CommandValidationException.class)
  public void validationTest5() throws Exception {
    AtmCommand.createCommand("+", "USD", "500s", "2");
  }

  @Test (expected = CommandValidationException.class)
  public void validationTest6() throws Exception {
    AtmCommand.createCommand("+", "USD", "500", "2.5");
  }

  @Test (expected = CommandValidationException.class)
  public void validationTest7() throws Exception {
    AtmCommand.createCommand("*", "USD", "500", "25");
  }
}
