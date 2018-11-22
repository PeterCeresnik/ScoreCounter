package ua.napps.scorekeeper.counters;

import android.app.Application;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.room.Database;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import io.reactivex.schedulers.TestScheduler;
import ua.napps.scorekeeper.storage.DatabaseHolder;

import static org.junit.Assert.*;

//@RunWith(MockitoJUnitRunner.class)
public class CountersUsingViewModelTest {

/*
    private TestScheduler testScheduler;

    private Application applicationMock;
    private DatabaseHolder database;
    private CountersDao countersDao;
    private CountersRepository repositary;
    private CountersViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        applicationMock = Mockito.mock(Application.class);
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getTargetContext(), DatabaseHolder.class).build();
        countersDao = database.countersDao();
        repositary = new CountersRepository(countersDao);
        viewModel = new CountersViewModel(applicationMock,repositary);


    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testVieModelItemCreation(){
        LifecycleOwner lifecycleOwnerMock = Mockito.mock(LifecycleOwner.class);
        Lifecycle lifecycle = new LifecycleRegistry(lifecycleOwnerMock);
        ((LifecycleRegistry) lifecycle).handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        viewModel.getCounters().observe(lifecycleOwnerMock, counters -> {
             assertThat(counters.size(),Is.is(1));
        });
        viewModel.addCounter();
    }*/
}