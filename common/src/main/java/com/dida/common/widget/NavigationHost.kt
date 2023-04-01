package com.dida.common.widget

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

/**
 * To be implemented by components that host top-level navigation destinations.
 */
interface NavigationHost {

    /** Called by MainNavigationFragment to setup it's toolbar with the navigation controller. */
    fun registerToolbarWithNavigation(toolbar: Toolbar, navigationIconResId: Int, homeEnable: Boolean) {}

    fun onApplicationRelaunchRequired() {}

    fun onGooglePlayServiceUpdateRequired() {}

    fun setLightToolBar(light: Boolean, toolbar: Toolbar?) {}

    fun setStatusBarColor(colorResId: Int) {}

    fun setSystemUiVisibility(lightStatusBar: Boolean, translucentNavigationBar: Boolean) {}
}

/**
 * To be implemented by main navigation destinations shown by a [NavigationHost].
 */
interface NavigationDestination {

    /** Called by the host when the user interacts with it. */
    fun onUserInteraction() {}

    fun onNavigationHomeButtonClicked() {}
}

/**
 * Fragment representing a main navigation destination. This class handles wiring up the [Toolbar]
 * navigation icon if the fragment is attached to a [NavigationHost].
 */
open class NavigationFragment : Fragment(), NavigationDestination {

    protected var mainToolbar: Toolbar? = null
    protected var navigationHost: NavigationHost? = null

    open var navigationIconResId: Int = -1
    open var navigationHomeButtonEnable : Boolean = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationHost) {
            navigationHost = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigationHost = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // If we have a toolbar and we are attached to a proper navigation host, set up the toolbar
        // navigation icon.
        val host = navigationHost ?: return
        mainToolbar?.apply {
            host.registerToolbarWithNavigation(this, navigationIconResId, navigationHomeButtonEnable)
        }
    }

    fun onApplicationRelaunchRequired() = navigationHost?.onApplicationRelaunchRequired()

    fun onGooglePlayServiceUpdateRequired() = navigationHost?.onGooglePlayServiceUpdateRequired()

    fun setLightToolBar(light: Boolean) = navigationHost?.setLightToolBar(light, mainToolbar)

    fun setStatusBarColor(colorResId: Int) = navigationHost?.setStatusBarColor(colorResId)

    fun setSystemUiVisibility(lightStatusBar: Boolean, translucentNavigationBar: Boolean) = navigationHost?.setSystemUiVisibility(lightStatusBar, translucentNavigationBar)
}
