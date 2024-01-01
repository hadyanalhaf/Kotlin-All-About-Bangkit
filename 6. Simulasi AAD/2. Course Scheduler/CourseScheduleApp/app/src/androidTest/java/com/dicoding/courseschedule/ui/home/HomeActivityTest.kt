import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import com.dicoding.courseschedule.ui.home.HomeActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.After
import org.junit.Before

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {
    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        Intents.init()
    }

    @After
    fun destroy() {
        Intents.release()
    }

    @Test
    fun showAddCourseActivity() {
        onView(withId(R.id.action_add)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(AddCourseActivity::class.java.name))
    }
}
