package cashmachine.money;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MoneyPackTest {
  MoneyPack mp1, mp2, mp3, mp4;
  MoneyPack mpu1, mpu2, mpu3, mpu4;
  MoneyPack mpe1, mpe2, mpe3, mpe4;

  ArrayList<MoneyPack> moneySafe, uahSafe, euroSafe;
  HashMap<String, ArrayList<MoneyPack>> moneyStorage;

  @Before
  public void initialize() {
    mp1 = new MoneyPack("USD", 500, 14);
    mp2 = new MoneyPack("USD", 100, 12);
    mp3 = new MoneyPack("USD", 50, 10);
    mp4 = new MoneyPack("USD", 10, 8);

    mpu1 = new MoneyPack("UAH", 500, 14);
    mpu2 = new MoneyPack("UAH", 100, 12);
    mpu3 = new MoneyPack("UAH", 50, 10);
    mpu4 = new MoneyPack("UAH", 10, 8);

    mpe1 = new MoneyPack("EUR", 500, 14);
    mpe2 = new MoneyPack("EUR", 100, 12);
    mpe3 = new MoneyPack("EUR", 50, 10);
    mpe4 = new MoneyPack("EUR", 10, 8);

    moneySafe = new ArrayList<>();
    uahSafe = new ArrayList<>();
    euroSafe = new ArrayList<>();

    moneyStorage = new HashMap<>();

    moneySafe.add(mp4);
    moneySafe.add(mp2);
    moneySafe.add(mp3);
    moneySafe.add(mp1);

    uahSafe.add(mpu4);
    uahSafe.add(mpu2);
    uahSafe.add(mpu1);
    uahSafe.add(mpu3);

    euroSafe.add(mpe2);
    euroSafe.add(mpe3);
    euroSafe.add(mpe1);
    euroSafe.add(mpe4);

/*
    for (MoneyPack pack : moneySafe) {
      System.out.println(pack.toString());
    }
*/

  }

  @Test
  public void testMoneyPackCreation() throws Exception {

    assertThat(moneySafe.size(), is(4));
    assertThat(moneySafe.get(0), is(mp4));
    assertThat(moneySafe.get(1), is(mp2));
    assertThat(moneySafe.get(2), is(mp3));
    assertThat(moneySafe.get(3), is(mp1));

  }

  @Test
  public void testMoneyPackSort() throws Exception {
    moneySafe.sort(new MoneyPackSortByValueDesc());

    assertThat(moneySafe.size(), is(4));
    assertThat(moneySafe.get(0), is(mp1));
    assertThat(moneySafe.get(1), is(mp2));
    assertThat(moneySafe.get(2), is(mp3));
    assertThat(moneySafe.get(3), is(mp4));

  }

  @Test
  public void testHashMapWithMoneyPacks() throws Exception {
    moneySafe.sort(new MoneyPackSortByValueDesc());
    uahSafe.sort(new MoneyPackSortByValueDesc());
    euroSafe.sort(new MoneyPackSortByValueDesc());

    moneyStorage.put("USD", moneySafe);
    moneyStorage.put("UAH", uahSafe);
    moneyStorage.put("EUR", euroSafe);

    ArrayList<MoneyPack> euros = moneyStorage.get("EUR");

    euros.get(1).setAmount(123);

/*
    for (String key : moneyStorage.keySet()) {
      System.out.println("Key: " + key + ", Value: " + moneyStorage.get(key));
    }
*/

    assertThat(moneyStorage.get("USD").get(2).getCurrency(), is("USD"));
    assertThat(moneyStorage.get("USD").get(2).getValue(), is(50));
    assertThat(moneyStorage.get("USD").get(2).getAmount(), is(10));
    assertThat(moneyStorage.get("RUB"), is(nullValue()));
    assertThat(euros instanceof ArrayList, is(true));
    assertThat(euros.get(0) instanceof MoneyPack, is(true));

  }

}
