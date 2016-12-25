package cashmachine.atmstorage;

import cashmachine.money.MoneyPack;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class ATMStorageTest {

  private MoneyPack mp1, mp2, mp3, mp4;
  private MoneyPack mpu1, mpu2, mpu3, mpu4, mpu5;
  private MoneyPack mpe1, mpe2, mpe3, mpe4;

  private ATMStorage atmStorage;

  @Before
  public void initialize() {
    mp1 = new MoneyPack("USD", 500, 14);
    mp2 = new MoneyPack("USD", 100, 12);
    mp3 = new MoneyPack("USD", 50, 10);
    mp4 = new MoneyPack("USD", 10, 8);

    mpe1 = new MoneyPack("EUR", 500, 34);
    mpe2 = new MoneyPack("EUR", 100, 32);
    mpe3 = new MoneyPack("EUR", 50, 30);
    mpe4 = new MoneyPack("EUR", 10, 28);

    mpu1 = new MoneyPack("UAH", 500, 1);
    mpu2 = new MoneyPack("UAH", 100, 1);
    mpu3 = new MoneyPack("UAH", 50, 1);
    mpu4 = new MoneyPack("UAH", 10, 1);
    mpu5 = new MoneyPack("UAH", 1, 1);


    atmStorage = new ATMStorage();
  }

  @Test
  public void testATMStorageAddMoneyPacks() throws Exception {
    atmStorage.store(mpe3);
    atmStorage.store(mp1);
    atmStorage.store(mpe4);
    atmStorage.store(mp2);
    atmStorage.store(mp3);
    atmStorage.store(mpe2);
    atmStorage.store(mp4);
    atmStorage.store(mpe1);

    // USD
    assertThat(atmStorage.getMoneyStorage().get("USD").get(0).getAmount(), is(14));
    assertThat(atmStorage.getMoneyStorage().get("USD").get(1).getAmount(), is(12));
    assertThat(atmStorage.getMoneyStorage().get("USD").get(2).getAmount(), is(10));
    assertThat(atmStorage.getMoneyStorage().get("USD").get(3).getAmount(), is(8));
    // EUR
    assertThat(atmStorage.getMoneyStorage().get("EUR").get(0).getAmount(), is(34));
    assertThat(atmStorage.getMoneyStorage().get("EUR").get(1).getAmount(), is(32));
    assertThat(atmStorage.getMoneyStorage().get("EUR").get(2).getAmount(), is(30));
    assertThat(atmStorage.getMoneyStorage().get("EUR").get(3).getAmount(), is(28));

  }

  @Test
  public void testATMStorageAddMoneyPacksMultiply() throws Exception {

    atmStorage.store(mpu4);
    atmStorage.store(mpu2);
    atmStorage.store(mpu3);
    atmStorage.store(mpu3);
    atmStorage.store(mpu4);
    atmStorage.store(mpu4);
    atmStorage.store(mpu4);
    atmStorage.store(mpu2);
    atmStorage.store(mpu1);
    atmStorage.store(mpu3);

/*    if (atmStorage != null) {
      atmStorage.showContent();
    }*/

    assertThat(atmStorage.getMoneyStorage().get("UAH").get(0).getAmount(), is(1));
    assertThat(atmStorage.getMoneyStorage().get("UAH").get(1).getAmount(), is(2));
    assertThat(atmStorage.getMoneyStorage().get("UAH").get(2).getAmount(), is(3));
    assertThat(atmStorage.getMoneyStorage().get("UAH").get(3).getAmount(), is(4));
  }

  @Test
  public void testATMStorageGetMoneyPacks() throws Exception {
    atmStorage.store(mpu4);
    atmStorage.store(mpu2);
    atmStorage.store(mpu3);
    atmStorage.store(mpu3);
    atmStorage.store(mpu4);
    atmStorage.store(mpu4);
    atmStorage.store(mpu4);
    atmStorage.store(mpu2);
    atmStorage.store(mpu1);
    atmStorage.store(mpu3);
    atmStorage.store(mpu5);
    atmStorage.store(mpu5);
    atmStorage.store(mpu5);
    atmStorage.store(mpu5);
    atmStorage.store(mpu5);
    atmStorage.store(mp1);
    atmStorage.store(mp2);

    assertThat(atmStorage.getMoneyStorage().get("UAH").get(0).getAmount(), is(1));
    assertThat(atmStorage.getMoneyStorage().get("UAH").get(1).getAmount(), is(2));
    assertThat(atmStorage.getMoneyStorage().get("UAH").get(2).getAmount(), is(3));
    assertThat(atmStorage.getMoneyStorage().get("UAH").get(3).getAmount(), is(4));

    List<MoneyPack> mplist = atmStorage.take(mpu1.getCurrency(), 674);

    assertThat(atmStorage.getAmount("UAH"), is(500 * 1 + 100 * 2 + 50 * 3 + 10 * 4 + 1 * 5 - 674));
    assertThat(atmStorage.getMoneyStorage().get("UAH").get(0).getAmount(), is(1));
    assertThat(atmStorage.getMoneyStorage().get("UAH").get(1).getAmount(), is(2));
    assertThat(atmStorage.getMoneyStorage().get("UAH").get(2).getAmount(), is(2));
    assertThat(atmStorage.getMoneyStorage().get("UAH").get(3).getAmount(), is(1));

    mplist = atmStorage.take("UAH", 220);

    assertThat(atmStorage.getAmount("UAH"), is(500 * 1 + 100 * 2 + 50 * 3 + 10 * 4 + 1 * 5 - 674 - 220));
    assertThat(atmStorage.getMoneyStorage().get("UAH").get(0).getAmount(), is(1));

    mplist = atmStorage.take("UAH", 1);

    assertThat(atmStorage.getAmount("UAH"), is(500 * 1 + 100 * 2 + 50 * 3 + 10 * 4 + 1 * 5 - 674 - 220 - 1));
    assertThat(atmStorage.getMoneyStorage().containsKey("UAH"), is(false));
    assertThat(atmStorage.getMoneyStorage().containsKey("USD"), is(true));

    mplist = atmStorage.take("USD", 7100);

    assertThat(atmStorage.getAmount("USD"), is(500 * 14 + 100 * 12 - 7100));
    assertThat(atmStorage.getMoneyStorage().containsKey("USD"), is(true));

    mplist = atmStorage.take("USD", 1100);

    assertThat(atmStorage.getAmount("USD"), is(500 * 14 + 100 * 12 - 7100 - 1100));
    assertThat(atmStorage.getMoneyStorage().containsKey("USD"), is(false));
    assertThat(atmStorage.getAmount("UAH"), is(0));
    assertThat(atmStorage.getAmount("USD"), is(0));

    atmStorage.store(mp4);
    atmStorage.store(mpu4);
    atmStorage.store(mpu4);
    atmStorage.store(mp2);
    atmStorage.store(mpu2);
    //System.out.println(atmStorage.showContent());
    assertThat(atmStorage.getMoneyStorage().containsKey("UAH"), is(true));
    assertThat(atmStorage.getMoneyStorage().containsKey("USD"), is(true));
    assertThat(atmStorage.getAmount("UAH"), is(120));
    assertThat(atmStorage.getAmount("USD"), is(1280));

  }

  @Test
  public void testATMStorageEmptyStorage() throws Exception {
    atmStorage.store(mpu1);
    atmStorage.store(mpu2);
    atmStorage.store(mpu3);
    atmStorage.store(mpu4);
    atmStorage.store(mpu5);
    atmStorage.store(mp1);
    atmStorage.store(mp2);

    assertThat(atmStorage.getMoneyStorage().isEmpty(), is(false));
    assertThat(atmStorage.getMoneyStorage().containsKey("UAH"), is(true));
    assertThat(atmStorage.getMoneyStorage().containsKey("USD"), is(true));
    assertThat(atmStorage.getMoneyStorage().containsKey("EUR"), is(false));
    assertThat(atmStorage.getMoneyStorage().get("UAH").get(0).getAmount(), is(1));
    assertThat(atmStorage.getMoneyStorage().get("UAH").get(1).getAmount(), is(1));
    assertThat(atmStorage.getMoneyStorage().get("UAH").get(2).getAmount(), is(1));
    assertThat(atmStorage.getMoneyStorage().get("UAH").get(3).getAmount(), is(1));
    assertThat(atmStorage.getMoneyStorage().get("UAH").get(4).getAmount(), is(1));

    atmStorage.emptyStorage("UAH");
    assertThat(atmStorage.getMoneyStorage().containsKey("UAH"), is(false));
    assertThat(atmStorage.getMoneyStorage().containsKey("USD"), is(true));

    atmStorage.emptyStorage();
    assertThat(atmStorage.getMoneyStorage().containsKey("UAH"), is(false));
    assertThat(atmStorage.getMoneyStorage().containsKey("USD"), is(false));
  }
}
