import data.Action
import data.Archive
import data.Note
import data.Screen
import navigator.Navigator

class NoteScreen(
    private val archive: Archive
) : Screen {

    override fun show() {
        // Меню пересобирается на каждой итерации — созданные заметки сразу появляются в списке.
        while (true) {

            val title = if (archive.notes.isEmpty()) {
                "Архив: \"${archive.title}\" — создайте хотя бы одну заметку"
            } else {
                "Архив: \"${archive.title}\" — выберите заметку:"
            }

            val navigator = Navigator(title)

            // Пункт создания новой заметки
            navigator.addItem("Создать заметку", object : Action {
                override fun execute() {
                    print("Введите название заметки: ")
                    val title = navigator.graspLine()

                    if (title.isEmpty()) {
                        println("Ошибка: название не может быть пустым.")
                        return
                    }

                    print("Введите текст заметки: ")
                    val content = navigator.graspLine()

                    if (content.isEmpty()) {
                        println("Ошибка: текст заметки не может быть пустым.")
                        return
                    }

                    archive.notes.add(Note(title, content))
                    println("Заметка \"$title\" создана.")
                }
            })

            // Добавляем в меню все существующие заметки этого архива.
            for (note in archive.notes) {
                navigator.addItem(note.title) {
                    NoteViewScreenTest(note).show()
                }
            }

            navigator.addItem("Назад", Action { })

            // «Назад» → show() вернёт true → выходим из экрана заметок обратно к экрану архивов.
            if (navigator.show()) return
        }
    }
}
