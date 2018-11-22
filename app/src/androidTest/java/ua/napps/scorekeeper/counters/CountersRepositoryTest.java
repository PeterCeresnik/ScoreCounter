package ua.napps.scorekeeper.counters;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import ua.napps.scorekeeper.storage.DatabaseHolder;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CountersRepositoryTest {

    CountersDao countersDao;
    Counter counter1;
    Counter counter2;
    private static DatabaseHolder database;

    @Before
    public void setUp() throws Exception {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getTargetContext(), DatabaseHolder.class).build();
        countersDao = database.countersDao();
        counter1 = new Counter("counter1", "black", 0);
        counter1.setStep(5);
        counter1.setDefaultValue(0);
        counter1.setValue(-2);
        counter2 = new Counter("counter2", "white", 1);
        counter2.setDefaultValue(-5);
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void createGetAndDeleteCounter() {
        countersDao.insert(counter1);
        assertThat(countersDao.count(), Is.is(1));

        Counter counterFromDB = countersDao.loadAllCountersSync().get(0);
        assertThat(counterFromDB.getName(), Is.is("counter1"));
        assertThat(counterFromDB.getColor(), Is.is("black"));
        assertThat(counterFromDB.getPosition(), Is.is(0));

        countersDao.deleteCounter(countersDao.loadAllCountersSync().get(0));
        assertThat(countersDao.count(), Is.is(0));
    }

    @Test
    public void deleteAllCounters() {
        countersDao.insert(counter1);
        countersDao.insert(counter1);
        assertThat(countersDao.count(), Is.is(2));

        countersDao.deleteAll();
        assertThat(countersDao.count(), Is.is(0));
    }

    @Test
    public void resetCounter() {
        countersDao.insert(counter2);
        countersDao.resetValues();

        Counter counterFromDB = countersDao.loadAllCountersSync().get(0);
        assertThat(counterFromDB.getName(), Is.is("counter2"));
        assertThat(counterFromDB.getColor(), Is.is("white"));
        assertThat(counterFromDB.getValue(), Is.is(counter2.getDefaultValue()));

        countersDao.deleteAll();
    }

    @Test
    public void modifyCounter() {
        countersDao.insert(counter1);
        Counter counterFromDB = countersDao.loadAllCountersSync().get(0);

        countersDao.modifyPosition(counterFromDB.getId(), 1);
        counterFromDB = countersDao.loadAllCountersSync().get(0);
        assertThat(counterFromDB.getPosition(), Is.is(1));

        countersDao.modifyColor(counterFromDB.getId(), "#0000FF");
        counterFromDB = countersDao.loadAllCountersSync().get(0);
        assertThat(counterFromDB.getColor(), Is.is("#0000FF"));

        countersDao.modifyName(counterFromDB.getId(), "newName");
        counterFromDB = countersDao.loadAllCountersSync().get(0);
        assertThat(counterFromDB.getName(), Is.is("newName"));

        countersDao.modifyDefaultValue(counterFromDB.getId(), -10);
        counterFromDB = countersDao.loadAllCountersSync().get(0);
        assertThat(counterFromDB.getDefaultValue(), Is.is(-10));

        countersDao.modifyStep(counterFromDB.getId(), 2);
        counterFromDB = countersDao.loadAllCountersSync().get(0);
        assertThat(counterFromDB.getStep(), Is.is(2));

        int value = counterFromDB.getValue();
        countersDao.modifyValue(counterFromDB.getId(), 55 );
        counterFromDB = countersDao.loadAllCountersSync().get(0);
        assertThat(counterFromDB.getValue(), Is.is(value+55));
    }

}