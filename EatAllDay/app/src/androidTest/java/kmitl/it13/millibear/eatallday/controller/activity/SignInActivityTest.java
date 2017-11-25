package kmitl.it13.millibear.eatallday.controller.activity;


import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kmitl.it13.millibear.eatallday.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignInActivityTest {

    @Rule
    public ActivityTestRule<SignInActivity> mActivityTestRule = new ActivityTestRule<>(SignInActivity.class);

    @Test
    public void goToSignUp() {

        closeSoftKeyboard();
        onView(withId(R.id.tv_sign_up)).perform(click());
        SystemClock.sleep(1000);

    }

    @Test
    public void signInSuccessful() {

        onView(withId(R.id.et_email)).perform(replaceText("tiwipabmin@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(replaceText("min261139"), closeSoftKeyboard());
        onView(withId(R.id.btn_sign_in)).perform(click());
        SystemClock.sleep(1000);
    }

    @Test
    public void signInWithFacebook() {

        onView(withId(R.id.login_button)).perform(click());
        SystemClock.sleep(2000);
        onView(withId(R.id.iv_logout)).perform(click());
        SystemClock.sleep(1000);

    }

    @Test
    public void doNotEnterEmail() {

        onView(withId(R.id.et_email)).perform(replaceText(""), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(replaceText("secret1234"), closeSoftKeyboard());
        onView(withId(R.id.btn_sign_in)).perform(click());
        onView(withText("Please enter your email or password")).check(matches(isDisplayed()));
        SystemClock.sleep(1000);
    }

    @Test
    public void enterEmailOrPasswordInvalid() {

        onView(withId(R.id.et_email)).perform(replaceText("test@testt.com"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(replaceText("secret1234"), closeSoftKeyboard());
        onView(withId(R.id.btn_sign_in)).perform(click());
        onView(withText("Email or password invalid.")).check(matches(isDisplayed()));
        SystemClock.sleep(1000);
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
