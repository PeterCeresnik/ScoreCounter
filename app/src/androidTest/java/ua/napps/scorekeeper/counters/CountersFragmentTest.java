package ua.napps.scorekeeper.counters;

import android.util.Log;

import junit.framework.AssertionFailedError;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import ua.com.napps.scorekeeper.R;
import ua.napps.scorekeeper.app.MainActivity;
import ua.napps.scorekeeper.dice.DicesFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CountersFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    private Fragment fragment;

    @Before
    public void setUp() throws Exception {
        FragmentManager manager = activityRule.getActivity().getSupportFragmentManager();
        fragment = CountersFragment.newInstance();
        manager.beginTransaction().replace(R.id.container, fragment, "COUNTERS_FRAGMENT")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commitAllowingStateLoss();
        Thread.sleep(2000);
    }

    @After
    public void tearDown() throws Exception {
        activityRule.finishActivity();
    }

    @Test
    public void checkCounterAddingAndDeleting() throws Exception{
        int itemCountBefore = ((CountersFragment)fragment).getCountersAdapter().getItemCount();
        try {
            onView(withId(R.id.empty_state)).check(matches(isDisplayed()));
            // Empty state
            onView(withId(R.id.empty_state)).perform(click());
            Log.i("Empty state","Clicked");
        } catch (AssertionFailedError e) {
            onView(withId(R.id.menu_add_counter)).perform(click());
            Log.i("Menu item","Clicked");
        }
        int itemCountAfter = ((CountersFragment)fragment).getCountersAdapter().getItemCount();
        assertThat(itemCountAfter, Is.is(itemCountBefore+1));
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        Thread.sleep(2000);
        onView(withText(R.string.menu_remove_all_counters)).perform(click());
        onView(withText(R.string.dialog_yes)).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
        Thread.sleep(2000);
        itemCountAfter = ((CountersFragment)fragment).getCountersAdapter().getItemCount();
        assertThat(itemCountAfter , Is.is(0));
    }
}