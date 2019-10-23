package deej.scopelib.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.OnClick
import deej.scopelib.BaseFragment
import deej.scopelib.FragmentArgumentsDelegates
import deej.scopelib.R
import deej.scopelib.core.toothpick.qualifiers.Param
import deej.scopelib.core.toothpick.scope.AndroidToothpickScopeArguments
import deej.scopelib.presentation.navigation.coordinators.RootCoordinator
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import toothpick.config.Module
import java.util.*
import javax.inject.Inject

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    @BindView(R.id.label) lateinit var label: TextView
    @BindView(R.id.input) lateinit var field: EditText

    @Inject @Param lateinit var param: String
    @Inject lateinit var coordinator: RootCoordinator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        label.text = param
    }

    @OnClick(R.id.button)
    fun restart() {
        val newParam = field.text.toString()
        if (newParam.isBlank()) return
        println("QWE RESTARTING with $newParam")
        coordinator.start(newParam)
    }
}

@Parcelize
data class HomeScopeArguments(
    val param: String,
    override val name: @RawValue Any = "Home scope",
    override val instanceId: String = UUID.randomUUID().toString()
) : AndroidToothpickScopeArguments {

    override fun createModules(): Array<Module> = arrayOf(
        ParamModule(param)
    )
}

class ParamModule(param: String) : Module() {
    init {
        bind(String::class.java).withName(Param::class.java).toInstance(param)
    }
}
