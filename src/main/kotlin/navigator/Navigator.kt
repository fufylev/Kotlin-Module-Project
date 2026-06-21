package navigator

import data.Action
import data.ScannerSingleton

private data class NavigatorItem(val path: String, val action: Action)

// Название класса дебильное но изначально прочитал строку в ТЗ:
// "Можно заметить, что у экранов выбора общая навигация и ввод. Именно это и надо вынести в отдельный класс:"
// и так и оставил это навигатором ))
class Navigator(
    private val title: String
) {
    private val items: MutableList<NavigatorItem> = mutableListOf()

    fun addItem(path: String, action: Action) {
        items.add(NavigatorItem(path, action))
    }

    fun graspLine(): String = ScannerSingleton.scanner.nextLine().trim()

    fun show(): Boolean {
        while (true) {

            println("\n$title")

            items.forEachIndexed { index, item ->
                println("$index. ${item.path}")
            }

            val chosenNumber =
                ScannerSingleton.scanner.nextLine().trim().toIntOrNull()

            when {
                chosenNumber == null -> {
                    // Ввели не цифру
                    println("Ошибка: нужно вводить цифру.")
                }

                chosenNumber < 0 || chosenNumber >= items.size -> {
                    // Цифра есть, но такого пункта не существует
                    println("Ошибка: такого пункта нет. Введите число от 0 до ${items.size - 1}.")
                }

                else -> {
                    // Ввод корректный — выполняем действие выбранного пункта
                    items[chosenNumber].action.execute()
                    // true, если выбран последний пункт (Выход/Назад)
                    return chosenNumber == items.size - 1
                }
            }
        }
    }
}