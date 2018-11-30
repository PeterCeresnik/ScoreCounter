package ua.napps.scorekeeper.counters;

import android.app.Application;
import android.content.res.Resources;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.room.Database;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import io.reactivex.schedulers.TestScheduler;
import ua.com.napps.scorekeeper.R;
import ua.napps.scorekeeper.storage.DatabaseHolder;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CountersUsingViewModelTest {


    private TestScheduler testScheduler;

    private Application applicationMock;
    private DatabaseHolder database;
    private CountersDao countersDao;
    private CountersRepository repositary;
    private CountersViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        applicationMock = Mockito.mock(Application.class);
        Resources resources = Mockito.mock(Resources.class);
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getTargetContext(), DatabaseHolder.class).build();
        countersDao = database.countersDao();
        repositary = new CountersRepository(countersDao);
        Mockito.when(applicationMock.getResources()).thenReturn(resources);
        Mockito.when(applicationMock.getResources().getStringArray(R.array.dark)).thenReturn(new String[]{"#556270"});
        Mockito.when(applicationMock.getResources().getStringArray(R.array.light)).thenReturn(new String[]{"#556270"});
        viewModel = new CountersViewModel(applicationMock,repositary);
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testVieModelItemCreation() throws Exception{
        LifecycleOwner lifecycleOwnerMock = Mockito.mock(LifecycleOwner.class);
        Lifecycle lifecycle = new LifecycleRegistry(lifecycleOwnerMock);
        Mockito.when(lifecycleOwnerMock.getLifecycle()).thenReturn(lifecycle);
        ((LifecycleRegistry) lifecycle).handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        viewModel.addCounter();
        Thread.sleep(2000);
        viewModel.getCounters().observe(lifecycleOwnerMock, counters -> {
             assertThat(counters.size(),Is.is(1));
        });
        viewModel.getCounters().removeObservers(lifecycleOwnerMock);
    }
    @Test
    public void testVieModelItemDeletion() throws Exception{
        LifecycleOwner lifecycleOwnerMock = Mockito.mock(LifecycleOwner.class);
        Lifecycle lifecycle = new LifecycleRegistry(lifecycleOwnerMock);
        Mockito.when(lifecycleOwnerMock.getLifecycle()).thenReturn(lifecycle);
        ((LifecycleRegistry) lifecycle).handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        //viewModel.addCounter();
        //Thread.sleep(2000);
        viewModel.removeAll();
        Thread.sleep(2000);
        viewModel.getCounters().observe(lifecycleOwnerMock, counters -> {
            assertThat(counters.size(),Is.is(0));
        });
        viewModel.getCounters().removeObservers(lifecycleOwnerMock);
    }
}