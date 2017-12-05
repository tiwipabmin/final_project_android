package com.tiwipabmin.eatallday.controller.activity;


import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tiwipabmin.eatallday.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<SignUpActivity> mActivityTestRule = new ActivityTestRule<>(SignUpActivity.class);

    @Test
    public void inputNameInvalid() {

        onView(withId(R.id.et_name)).perform(replaceText("สมชัย"), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(replaceText("test@test.com"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(replaceText("secret1234"), closeSoftKeyboard());
        onView(withId(R.id.et_verify_password)).perform(replaceText("secret1234"), closeSoftKeyboard());
        onView(withId(R.id.et_facebook)).perform(replaceText("facebook"), closeSoftKeyboard());
        onView(withId(R.id.btn_verify)).perform(click());
        onView(withId(R.id.et_name)).check(matches(hasErrorText("your name can't be special character or numeric.")));
        SystemClock.sleep(1000);

    }

    @Test
    public void inputEmailInvalid() {

        onView(withId(R.id.et_name)).perform(replaceText("test"), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(replaceText("tes#@t@test.com"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(replaceText("secret1234"), closeSoftKeyboard());
        onView(withId(R.id.et_verify_password)).perform(replaceText("secret1234"), closeSoftKeyboard());
        onView(withId(R.id.et_facebook)).perform(replaceText("facebook"), closeSoftKeyboard());
        onView(withId(R.id.btn_verify)).perform(click());
        onView(withId(R.id.et_email)).check(matches(hasErrorText("you can't be this email please fill in new email.")));
    }

    @Test
    public void inputPasswordInvalid() {

        onView(withId(R.id.et_name)).perform(replaceText("test"), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(replaceText("test@test.com"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(replaceText("s1234"), closeSoftKeyboard());
        onView(withId(R.id.et_verify_password)).perform(replaceText("s1234"), closeSoftKeyboard());
        onView(withId(R.id.et_facebook)).perform(replaceText("facebook"), closeSoftKeyboard());
        onView(withId(R.id.btn_verify)).perform(click());
        onView(withId(R.id.et_password)).check(matches(hasErrorText("your password can't be special character and there are 8 character up.")));
        SystemClock.sleep(1000);

    }

    @Test
    public void inputVerifyPasswordInvalid() {

        onView(withId(R.id.et_name)).perform(replaceText("test"), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(replaceText("test@test.com"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(replaceText("secret1234"), closeSoftKeyboard());
        onView(withId(R.id.et_verify_password)).perform(replaceText("s1234"), closeSoftKeyboard());
        onView(withId(R.id.et_facebook)).perform(replaceText("facebook"), closeSoftKeyboard());
        onView(withId(R.id.btn_verify)).perform(click());
        onView(withId(R.id.et_verify_password)).check(matches(hasErrorText("your password verify don't pass.")));
        SystemClock.sleep(1000);

    }

    @Test
    public void inputFacebookInvalid() {

        onView(withId(R.id.et_name)).perform(replaceText("test"), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(replaceText("test@test.com"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(replaceText("secret1234"), closeSoftKeyboard());
        onView(withId(R.id.et_verify_password)).perform(replaceText("secret1234"), closeSoftKeyboard());
        onView(withId(R.id.et_facebook)).perform(replaceText(""), closeSoftKeyboard());
        onView(withId(R.id.btn_verify)).perform(click());
        onView(withId(R.id.et_facebook)).check(matches(hasErrorText("your facebook invalid.")));
        SystemClock.sleep(1000);

    }

//    @Test
//    public void signUpSuccessful() {
//
//        onView(withId(R.id.et_name)).perform(replaceText("test"), closeSoftKeyboard());
//        onView(withId(R.id.et_email)).perform(replaceText("test@test.com"), closeSoftKeyboard());
//        onView(withId(R.id.et_password)).perform(replaceText("secret1234"), closeSoftKeyboard());
//        onView(withId(R.id.et_verify_password)).perform(replaceText("secret1234"), closeSoftKeyboard());
//        onView(withId(R.id.et_facebook)).perform(replaceText("testtesttest"), closeSoftKeyboard());
//        onView(withId(R.id.btn_verify)).perform(click());
//        SystemClock.sleep(3000);
//
//    }

    @Test
    public void goToSignIn() {

        closeSoftKeyboard();
        onView(withId(R.id.tv_sign_in)).perform(click());
        SystemClock.sleep(3000);

    }

}
