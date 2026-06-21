import data.Archive
import data.Screen

fun main(args: Array<String>) {
    val archives: MutableList<Archive> = mutableListOf()
    val firstScreen: Screen = ArchiveScreen(archives)
    firstScreen.show()

    // выполнится только после выхода из программы
    println("До свидания!")
}
