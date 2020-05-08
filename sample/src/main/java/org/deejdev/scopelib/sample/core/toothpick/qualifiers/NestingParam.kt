package org.deejdev.scopelib.sample.core.toothpick.qualifiers

import javax.inject.Qualifier
import kotlin.annotation.AnnotationTarget.*

@Qualifier
@Target(FIELD, VALUE_PARAMETER, PROPERTY_SETTER)
annotation class NestingParam
