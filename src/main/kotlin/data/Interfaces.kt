package data

fun interface Screen {
    // Показывает экран и работает, пока пользователь не уйдёт на предыдущий экран.
    fun show()
}

fun interface Action {
    fun execute()
}


