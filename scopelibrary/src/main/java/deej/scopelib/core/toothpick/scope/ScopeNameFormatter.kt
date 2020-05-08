package deej.scopelib.core.toothpick.scope

internal fun formatScopeName(name: Any): String {
    return when {
        name is Class<*> && name.isAnnotation -> name.simpleName
        name is String -> "\"$name\""
        else -> "Unsupported scope name: $name"
    }
}
