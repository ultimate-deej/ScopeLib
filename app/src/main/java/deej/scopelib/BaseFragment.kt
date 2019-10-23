package deej.scopelib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import deej.scopelib.core.toothpick.scope.AndroidToothpickScopeArguments

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.also {
            ButterKnife.bind(this, it)
        }
    }

    var scopeArguments: AndroidToothpickScopeArguments? by FragmentArgumentsDelegates.parcelable()
}
