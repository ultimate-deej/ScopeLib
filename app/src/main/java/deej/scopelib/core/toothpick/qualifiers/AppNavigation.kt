package deej.scopelib.core.toothpick.qualifiers

import javax.inject.Qualifier
import kotlin.annotation.AnnotationTarget.*

@Qualifier
@Target(FIELD, VALUE_PARAMETER, PROPERTY_SETTER)
annotation class AppNavigation
