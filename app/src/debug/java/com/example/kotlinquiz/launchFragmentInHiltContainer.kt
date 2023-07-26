package com.example.kotlinquiz

import android.R
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    themeResId: Int = androidx.fragment.testing.manifest.R.style.FragmentScenarioEmptyFragmentActivityTheme,
    fragmentFactory: FragmentFactory? = null,
    crossinline action: T.() -> Unit = {}
) {
    Log.i("@Testr", "@Testr: started ")
    val mainActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    ).putExtra(
        "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
        themeResId
    )

    Log.i("@Testr", "@Testr: ,iddle ")
    Log.i("@Testr", "${fragmentFactory}")
  extracted(mainActivityIntent, fragmentFactory, fragmentArgs, action)
}

inline fun <reified T : Fragment> extracted(
    mainActivityIntent: Intent,
    fragmentFactory: FragmentFactory?,
    fragmentArgs: Bundle?,
    crossinline action: T.() -> Unit
) {
    ActivityScenario.launch<HiltTestActivity>(mainActivityIntent).onActivity { activity ->
        Log.i("@Testr", "${fragmentFactory}")
        fragmentFactory?.let {
            Log.i("@Testr", "@Testr: ,iddle 1")
            activity.supportFragmentManager.fragmentFactory = it
        }
        Log.i("@Testr", "@Testr: ,iddle 2")
        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )
        Log.i("@Testr", "@Testr: ,iddle 3")
        fragment.arguments = fragmentArgs
        Log.i("@Testr", "@Testr: ,iddle 4")
        activity.supportFragmentManager.beginTransaction()
            .add(R.id.content, fragment, "")
            .commit()
        Log.i("@Testr", "@Testr: ended 676 ")
        (fragment as T).action()
    }


}




