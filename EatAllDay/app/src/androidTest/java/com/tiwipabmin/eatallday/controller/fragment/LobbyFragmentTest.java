package com.tiwipabmin.eatallday.controller.fragment;


import android.os.SystemClock;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import org.hamcrest.Matcher;
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
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LobbyFragmentTest {

    private SignInActivityTest mSignInActivityTest = new SignInActivityTest();

    @Rule
    public ActivityTestRule<SignInActivity> mActivityTestRule = new ActivityTestRule<>(SignInActivity.class);

    @Before
    public void signIn(){
        mSignInActivityTest.signInSuccessful();
    }

    @Test
    public void editProfile() {

        onView(withId(R.id.iv_edit_profile)).perform(click());
        onView(withId(R.id.et_facebook)).perform(replaceText("test facebook"), closeSoftKeyboard());
        onView(withId(R.id.et_description)).perform(replaceText("test description"), closeSoftKeyboard());
        onView(withId(R.id.et_name)).perform(replaceText("test name"), closeSoftKeyboard());
        onView(withId(R.id.iv_verify)).perform(click());
        onView(withText("แก้ไขประวัติเรียบร้อย.")).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(1000);
    }

    @Test
    public void goToKitchen() {

        onView(withText("ห้องครัว")).perform(click());
        SystemClock.sleep(2000);
    }

    @Test
    public void deleteRandomMenuHistory() {

        randomMenu();
        onView(withId(R.id.rv_profile)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, MyViewAction.clickChildViewWithId(R.id.iv_delete)));
        SystemClock.sleep(2000);
        onView(withText("ใช่, ฉันต้องการลบ")).perform(click());
        onView(withText("ลบประวัติเรียบร้อยแล้วจ้า.")).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    @Test
    public void deleteProfileImage() {

        onView(withId(R.id.iv_image)).perform(click());
        onView(withId(R.id.item_delete_profile_display)).perform(click());
        onView(withText("ลบรูปภาพประจำตัวเรียบร้อยแล้วขอรับ.")).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(2000);
    }

    @Test
    public void goToRandomAndBackToLobby() {

        onView(withText("ห้องครัว")).perform(click());
        onView(withId(R.id.btn_random)).perform(click());
        onView(withId(R.id.iv_back)).perform(click());
        onView(withText("หน้ากระดาน")).perform(click());
        SystemClock.sleep(2000);
    }

    private void randomMenu(){

        onView(withText("ห้องครัว")).perform(click());
        addMenu();
        onView(withId(R.id.btn_random)).perform(click());
        onView(withId(R.id.iv_item1)).perform(click());
        onView(withId(R.id.rv_foodItem)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.btn_add)));
        SystemClock.sleep(2000);
        onView(withId(R.id.btn_random_now)).perform(click());
        onView(withText("จำนวนในการสุ่มน้อยเกินไป.")).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(2000);
        onView(withId(R.id.iv_item2)).perform(click());
        onView(withId(R.id.rv_foodItem)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.btn_add)));
        SystemClock.sleep(2000);
        onView(withId(R.id.iv_item3)).perform(click());
        onView(withId(R.id.rv_foodItem)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.btn_add)));
        SystemClock.sleep(2000);
        onView(withId(R.id.iv_item4)).perform(click());
        onView(withId(R.id.rv_foodItem)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.btn_add)));
        SystemClock.sleep(2000);
        onView(withId(R.id.btn_random_now)).perform(click());
        SystemClock.sleep(4000);
        onView(withId(R.id.btn_accept)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.et_topics)).perform(replaceText("test"), closeSoftKeyboard());
        onView(withId(R.id.btn_ok)).perform(click());
        onView(withText("บันทึกประวัติเรียบร้อยแล้วจ้า.")).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(1000);
        onView(withText("หน้ากระดาน")).perform(click());
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
        SystemClock.sleep(5000);
    }

    public static class MyViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }
}
