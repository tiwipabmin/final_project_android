package com.tiwipabmin.eatallday.controller.fragment;


import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tiwipabmin.eatallday.R;
import com.tiwipabmin.eatallday.controller.activity.SignInActivity;
import com.tiwipabmin.eatallday.controller.activity.SignInActivityTest;

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
public class KitchenFragmentTest {

    private SignInActivityTest mSignInActivityTest = new SignInActivityTest();

    @Rule
    public ActivityTestRule<SignInActivity> mActivityTestRule = new ActivityTestRule<>(SignInActivity.class);

    @Before
    public void signIn(){
        mSignInActivityTest.signInSuccessful();
    }

    @Test
    public void goToAddMenu() {

        onView(withText("ห้องครัว")).perform(click());
        onView(withId(R.id.btn_add)).perform(click());
        SystemClock.sleep(2000);
        onView(withId(R.id.iv_back)).perform(click());
        SystemClock.sleep(1000);
    }

    @Test
    public void viewMenu() {

        onView(withText("ห้องครัว")).perform(click());
        onView(withId(R.id.rv_menu)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, LobbyFragmentTest.MyViewAction.clickChildViewWithId(R.id.cv_item)));
        SystemClock.sleep(2000);
        onView(withId(R.id.iv_cancel)).perform(click());
        SystemClock.sleep(1000);
    }

    @Test
    public void goToEditMenu() {

        onView(withText("ห้องครัว")).perform(click());
        onView(withId(R.id.rv_menu)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, LobbyFragmentTest.MyViewAction.clickChildViewWithId(R.id.iv_config)));
        SystemClock.sleep(2000);
        onView(withId(R.id.item_edit_menu)).perform(click());
        onView(withId(R.id.iv_back)).perform(click());
        SystemClock.sleep(1000);
    }

    @Test
    public void deleteMenu() {

        onView(withText("ห้องครัว")).perform(click());
        addMenu();
        onView(withId(R.id.rv_menu)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, LobbyFragmentTest.MyViewAction.clickChildViewWithId(R.id.iv_config)));
        SystemClock.sleep(2000);
        onView(withId(R.id.item_delete_menu)).perform(click());
        onView(withText("ลบรายการอาหารเรียบร้อยแล้วขอรับ.")).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(1000);
    }

    @After
    public void logout(){

        mSignInActivityTest.logout();
    }

    private void addMenu() {

        onView(withId(R.id.btn_add)).perform(click());
        onView(withId(R.id.et_name)).perform(replaceText("test name") ,closeSoftKeyboard());
        onView(withId(R.id.et_cost)).perform(replaceText("10") ,closeSoftKeyboard());
        onView(withId(R.id.et_currency)).perform(replaceText("บาท") ,closeSoftKeyboard());
        onView(withId(R.id.et_amount)).perform(replaceText("1") ,closeSoftKeyboard());
        onView(withId(R.id.et_unit)).perform(replaceText("จาน") ,closeSoftKeyboard());
        onView(withId(R.id.et_description)).perform(replaceText("test description") ,closeSoftKeyboard());
        onView(withId(R.id.iv_add)).perform(click());
        onView(withText("เพิ่มรายการอาหารเรียบร้อยแล้วจ้า.")).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

}
