package kmitl.it13.millibear.eatallday.controller.activity;


import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kmitl.it13.millibear.eatallday.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class KitchenActivityTest {

    @Rule
    public ActivityTestRule<SignInActivity> mActivityTestRule = new ActivityTestRule<>(SignInActivity.class);

    @Test
    public void goToAddMenu() {

        signIn();
        onView(withText("ห้องครัว")).perform(click());
        onView(withId(R.id.btn_add)).perform(click());
        SystemClock.sleep(3000);
        onView(withId(R.id.iv_back)).perform(click());
        SystemClock.sleep(2000);
    }

    @Test
    public void viewMenu() {

        signIn();
        onView(withText("ห้องครัว")).perform(click());
        onView(withId(R.id.rv_menu)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, TabBarActivityTest.MyViewAction.clickChildViewWithId(R.id.item)));
        SystemClock.sleep(3000);
        onView(withId(R.id.iv_cancel)).perform(click());
        SystemClock.sleep(2000);
    }

    @Test
    public void goToEditMenu() {

        signIn();
        onView(withText("ห้องครัว")).perform(click());
        onView(withId(R.id.rv_menu)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, TabBarActivityTest.MyViewAction.clickChildViewWithId(R.id.iv_config)));
        SystemClock.sleep(3000);
        onView(withId(R.id.item_edit_menu)).perform(click());
        onView(withId(R.id.iv_back)).perform(click());
        SystemClock.sleep(2000);
    }

    @Test
    public void deleteMenu() {

        signIn();
        onView(withText("ห้องครัว")).perform(click());
        onView(withId(R.id.rv_menu)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, TabBarActivityTest.MyViewAction.clickChildViewWithId(R.id.iv_config)));
        SystemClock.sleep(3000);
        onView(withId(R.id.item_delete_menu)).perform(click());
        onView(withText("ลบรายการอาหารเรียบร้อยแล้วขอรับ.")).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(2000);
    }

    private void signIn(){

        closeSoftKeyboard();
        onView(withId(R.id.et_email)).perform(replaceText("test@test.com"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(replaceText("secret1234"), closeSoftKeyboard());
        onView(withId(R.id.btn_sign_in)).perform(click());
        SystemClock.sleep(3000);
    }

}
