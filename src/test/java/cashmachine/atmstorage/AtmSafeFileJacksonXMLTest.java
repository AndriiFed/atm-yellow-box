package cashmachine.atmstorage;

import cashmachine.money.MoneyPack;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class AtmSafeFileJacksonXMLTest {

  private MoneyPack mp1, mp2, mp3, mp4;
  private MoneyPack mpu1, mpu2, mpu3, mpu4, mpu5;
  private MoneyPack mpe1, mpe2, mpe3, mpe4;
  private ATMStorage atmStorage;

  @Before
  public void initialize() throws Exception {
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

    atmStorage = new ATMStorage("test");
  }

/*  @After
  public void destructor() throws Exception {
    File file = new File("safe-test.xml");
    if(file.exists()) {
      file.delete();
    }
  }*/

  @Test
  public void testAtmSafe_XML_File_Test1() throws IOException {
    atmStorage.emptyStorage();
    atmStorage.store(mpe3);
    atmStorage.store(mp1);
    atmStorage.store(mpe4);
    atmStorage.store(mp2);
    atmStorage.store(mp3);
    atmStorage.store(mpe2);
    atmStorage.store(mp4);
    atmStorage.store(mpe1);

    AtmSafeFileJacksonXML xmlFile = new AtmSafeFileJacksonXML("safe-test.xml");
    xmlFile.saveSafe(atmStorage.getMoneyStorage());
    File file = new File("safe-test.xml");

    assertThat(file.exists(), is(true));
  }

  @Test
  public void testAtmSafe_XML_File_Test2() throws Exception {

    //testAtmSafe_XML_File_Test1();

    atmStorage = new ATMStorage("test");

    //System.out.println(atmStorage.showContent());

    assertThat(atmStorage.getMoneyStorage().isEmpty(), is(false));
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

}
