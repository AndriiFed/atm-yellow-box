import cashmachine.atmcommand.AtmCommand;
import cashmachine.atmcommand.GetCashCommand;
import cashmachine.atmcommand.PrintCashCommand;
import cashmachine.atmcommand.PutCashCommand;
import cashmachine.exceptions.BadCommandException;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class AtmCommandTest {
  @Test
  public void createPutCashCommandTest() throws Exception {
    AtmCommand command = AtmCommand.createCommand(new String[]{"+", "USD", "100", "5"});
    assertThat(command instanceof PutCashCommand, is(true));
  }

  @Test
  public void createGetCashCommandTest() throws Exception {
    AtmCommand command = AtmCommand.createCommand(new String[]{"-", "USD", "200"});
    assertThat(command instanceof GetCashCommand, is(true));
  }

  @Test
  public void createPrintCashCommandTest() throws Exception {
    AtmCommand command = AtmCommand.createCommand(new String[]{"?"});
    assertThat(command instanceof PrintCashCommand, is(true));
  }

  @Test
  public void correctMoneyValuesTest() throws Exception {
    AtmCommand.createCommand(new String[]{"+", "USD", "1", "2"});
    AtmCommand.createCommand(new String[]{"+", "EUR", "5", "2"});
    AtmCommand.createCommand(new String[]{"+", "USD", "10", "2"});
    AtmCommand.createCommand(new String[]{"+", "OLO", "50", "2"});
    AtmCommand.createCommand(new String[]{"+", "USD", "100", "2"});
    AtmCommand.createCommand(new String[]{"+", "USD", "500", "2"});
    AtmCommand.createCommand(new String[]{"+", "USD", "1000", "2"});
    AtmCommand.createCommand(new String[]{"+", "USD", "5000", "2"});
    assertThat(true, is(true));
  }

  @Test
  public void PutCashCommandTest() throws Exception {
    PutCashCommand command = (PutCashCommand)AtmCommand.createCommand(new String[]{"+", "USD", "5000", "2"});
    assertThat(command.atmMethod, is("putCash"));
    assertThat(command.moneyPack.getValue(), is(5000));
    assertThat(command.moneyPack.getCurrency(), is("USD"));
    assertThat(command.moneyPack.getAmount(), is(2));
  }

  @Test
  public void GetCashCommandTest() throws Exception {
    GetCashCommand command = (GetCashCommand)AtmCommand.createCommand(new String[]{"-", "XXX", "700"});
    assertThat(command.atmMethod, is("getCash"));
    assertThat(command.amount, is(700));
    assertThat(command.currency, is("XXX"));
  }

  @Test
  public void PrintCashCommandTest() throws Exception {
    PrintCashCommand command = (PrintCashCommand)AtmCommand.createCommand(new String[]{"?"});
    assertThat(command.atmMethod, is("printCash"));
  }

  @Test (expected = BadCommandException.class)
  public void validationTest1() throws Exception {
    AtmCommand.createCommand(new String[]{"?", "100"});
  }

  @Test (expected = BadCommandException.class)
  public void validationTest2() throws Exception {
    AtmCommand.createCommand(new String[]{"+", "USSD", "50", "1"});
  }

  @Test (expected = BadCommandException.class)
  public void validationTest3() throws Exception {
    AtmCommand.createCommand(new String[]{"+", "USD", "30", "2"});
  }

  @Test (expected = BadCommandException.class)
  public void validationTest4() throws Exception {
    AtmCommand.createCommand(new String[]{"+", "USD", "30000", "2"});
  }

  @Test (expected = BadCommandException.class)
  public void validationTest5() throws Exception {
    AtmCommand.createCommand(new String[]{"+", "USD", "500s", "2"});
  }

  @Test (expected = BadCommandException.class)
  public void validationTest6() throws Exception {
    AtmCommand.createCommand(new String[]{"+", "USD", "500", "2.5"});
  }

  @Test (expected = BadCommandException.class)
  public void validationTest7() throws Exception {
    AtmCommand.createCommand(new String[]{"*", "USD", "500", "25"});
  }
}
