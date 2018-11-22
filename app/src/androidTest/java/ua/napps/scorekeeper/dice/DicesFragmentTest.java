package ua.napps.scorekeeper.dice;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import ua.com.napps.scorekeeper.R;
import ua.napps.scorekeeper.app.MainActivity;
import ua.napps.scorekeeper.settings.LocalSettings;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DicesFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    private Fragment fragment;

    @Before
    public void setUp() throws Exception {
        FragmentManager manager = activityRule.getActivity().getSupportFragmentManager();
        fragment = DicesFragment.newInstance(1, 2);
        manager.beginTransaction().replace(R.id.container, fragment, "DICES_FRAGMENT")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commitAllowingStateLoss();
        Thread.sleep(2000);
    }

    @After
    public void tearDown() throws Exception {
        activityRule.finishActivity();
    }

    @Test
    public void testDiceRoll(){
        DiceViewModel viewModel =  ((DicesFragment)fragment).getViewModel();
        int beforeClickRollCount = viewModel.getRollCount();
        int currentRollBefore = ((DicesFragment)fragment).getCurrentRoll();
        int previousRollBefore = ((DicesFragment)fragment).getPreviousRoll();
        assertThat(currentRollBefore, Is.is(1));
        assertThat(previousRollBefore, Is.is(2));

        onView(withId(R.id.container_dices)).perform(click());

        int afterClickRollCount = viewModel.getRollCount();
        assertThat(afterClickRollCount, Is.is(beforeClickRollCount+1));
        int currentRollAfter = ((DicesFragment)fragment).getCurrentRoll();
        int previousRollAfter = ((DicesFragment)fragment).getPreviousRoll();
        assertThat(currentRollAfter, lessThanOrEqualTo(LocalSettings.getDiceMaxSide()));
    }


}