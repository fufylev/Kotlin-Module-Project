import data.Action
import data.Note
import data.Screen
import navigator.Navigator

// Экран просмотра конкретной заметки.
class NoteViewScreenTest(
    private val note: Note
) : Screen {

    override fun show() {
        // Здесь список пунктов не меняется, но цикл нужен, чтобы после показа текста
        // меню появлялось снова, пока пользователь не выберет «Назад».
        while (true) {

            val navigator = Navigator("Заметка: \"${note.title}\"")

            // Пункт для просмотра текста заметки
            navigator.addItem("Показать текст заметки") {
                println("\n--- Текст заметки \"${note.title}\" ---")
                println(note.content)
                println("---------------------")
            }

            navigator.addItem("Назад", Action { })

            // «Показать текст» → show() вернёт false → цикл повторится, меню покажется снова.
            // «Назад» → show() вернёт true → выходим обратно на экран заметок.
            if (navigator.show()) return
        }
    }
}
