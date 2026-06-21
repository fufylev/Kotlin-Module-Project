import data.Action
import data.Archive
import data.Screen
import navigator.Navigator
import java.util.Scanner

// Экран выбора и создания архивов — первый экран программы.
class ArchiveScreen(
    private val scanner: Scanner,
    private val archives: MutableList<Archive>
) : Screen {

    override fun show() {
        // Каждая итерация цикла ЗАНОВО пересобирает меню — поэтому только что созданный архив
        // сразу появится в списке.
        while (true) {

            val menu = Navigator("Список архивов:", scanner)

            // Пункт создания нового архива.
            menu.addItem("Создать архив", object : Action {
                override fun execute() {
                    print("Введите название архива: ")
                    val title = menu.graspLine()

                    if (title.isEmpty()) {
                        println("Ошибка: название не может быть пустым.")
                        return
                    }

                    archives.add(Archive(title))  // добавляем архив в общий список
                    println("Архив \"$title\" создан.")
                }
            })

            for (archive in archives) {
                menu.addItem(archive.title) {
                    // TODO - Добавление заметок
                }
            }

            menu.addItem("Выход", Action { })

            // show() вернёт true ТОЛЬКО при выборе «Выход» — тогда выходим из экрана (и из программы).
            // При любом другом выборе show() вернёт false, цикл повторится и меню пересоберётся.
            if (menu.show()) return
        }
    }
}
