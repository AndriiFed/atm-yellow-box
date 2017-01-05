package cashmachine.atmstorage;

import cashmachine.money.MoneyPack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AtmSafeH2DbTest {

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

    mpu1 = new MoneyPack("UAH", 500, 37);
    mpu2 = new MoneyPack("UAH", 100, 19);
    mpu3 = new MoneyPack("UAH", 50, 17);
    mpu4 = new MoneyPack("UAH", 10, 7);
    mpu5 = new MoneyPack("UAH", 1, 13);

    atmStorage = new ATMStorage("test");
  }


  @After
  public void destructor() throws Exception {
    File file = new File("atmdb-test.mv.db");
    File file2 = new File("atmdb-test.trace.db");
    if(file.exists()) {
      file.delete();
    }

    if(file2.exists()) {
      file2.delete();
    }
  }


  @Test
  public void testAtmSafe_H2db_Test1() throws Exception {
    atmStorage.emptyStorage();
    atmStorage.store(mp1);
    atmStorage.store(mp2);
    atmStorage.store(mp3);
    atmStorage.store(mp4);
    atmStorage.store(mpu3);
    atmStorage.store(mpu2);
    atmStorage.store(mpu1);
    atmStorage.store(mpu5);
    atmStorage.store(mpu4);

    AtmSafe atmSafe = new AtmSafeH2Db("jdbc:h2:./atmdb-test");
    atmSafe.saveSafe(atmStorage.getMoneyStorage());

    HashMap<String, ArrayList<MoneyPack>> safe = atmSafe.loadSafe();
    //System.out.println(safe);

    // USD
    assertThat(safe.get("USD").get(0).getAmount(), is(14));
    assertThat(safe.get("USD").get(1).getAmount(), is(12));
    assertThat(safe.get("USD").get(2).getAmount(), is(10));
    assertThat(safe.get("USD").get(3).getAmount(), is(8));
  }

}
