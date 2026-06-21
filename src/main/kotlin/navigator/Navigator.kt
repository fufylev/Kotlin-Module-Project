package navigator

import data.Action
import java.util.Scanner

private data class NavigatorItem(val path: String, val action: Action)

/** !!! Люблю делать такие комменты чтобы понять самому что я хочу сделать
 * Навигатор — общий класс для всех экранов-меню.
 * Каждый экран не пишет свой цикл ввода/вывода, а использует Navigator:
 * наполняет его пунктами через [addItem] и запускает через [show].
 *
 * Состав Navigator (общий код, переиспользуемый всеми экранами):
 *
 *     ┌─────────────────────── Navigator ───────────────────────
 *     │  title    : String           — заголовок меню
 *     │  scanner  : Scanner          — единый ввод (читает graspLine)
 *     │  items    : [(path, action)] — пункты меню
 *     │  addItem(path, action)       — добавить пункт
 *     │  graspLine() : String        — прочитать строку ввода
 *     │  show() : Boolean            — отрисовать меню (цикл ниже)
 *     └─────────────────────────────────────────────────────────
 *
 *     Цикл внутри show()  (вывод → ввод → действие):
 *
 *     ┌──►  1. ВЫВОД:  печатаем title и пункты с номерами
 *     │                   │
 *     │                   ▼
 *     │         2. ВВОД:  читаем строку → number
 *     │                   │
 *     │             ┌─────┴──────┐
 *     │           неверно      верно
 *     │             │            │
 *     │             ▼            ▼
 *     │       «Ошибка…»    3. ДЕЙСТВИЕ: action.execute()
 *     └─── (повтор)                │
 *                                  ▼
 *                       return true  ← выбран последний пункт
 *                                       («Выход» / «Назад»)
 */
class Navigator(
    private val title: String,
    private val scanner: Scanner
) {
    private val items: MutableList<NavigatorItem> = mutableListOf()

    fun addItem(path: String, action: Action) {
        items.add(NavigatorItem(path, action))
    }

    fun graspLine(): String = scanner.nextLine().trim()

    fun show(): Boolean {
        while (true) {

            println("\n$title")

            items.forEachIndexed { index, item ->
                println("$index. ${item.path}")
            }

            val chosenNumber =
                scanner.nextLine().trim().toIntOrNull()

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