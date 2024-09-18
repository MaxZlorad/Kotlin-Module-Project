import kotlin.system.exitProcess

class NotesApp {
    private val archives = mutableListOf<Archive>()

    fun start() {
        while (true) {
            displayArchives()
            val input = readUserInput()
            when {
                input == "0" -> createArchive()
                input.toIntOrNull() in 1..archives.size -> selectArchive(archives[input.toInt() - 1])
                input == "${archives.size + 1}" -> exitProcess(0)
                else -> println("некорректный ввод, введите цифру от 0 до ${archives.size + 1}.")
            }
        }
    }

    private fun displayArchives() {
        println("Список архивов:")
        println("0. Создать архив")
        archives.forEachIndexed { index, archive -> println("${index + 1}. ${archive.name}") }
        println("${archives.size + 1}. Выход")
    }

    private fun createArchive() {
        print("Введите имя архива: ")
        val name = readUserInput()
        if (name.isBlank()) {
            println("Имя архива не может быть пустым.")
            return
        }
        archives.add(Archive(name))
        println("Архив '$name' создан.")
    }

    private fun selectArchive(archive: Archive) {
        while (true) {
            displayNotes(archive)
            val input = readUserInput()
            when {
                input == "0" -> createNote(archive)
                input.toIntOrNull() in 1..archive.notes.size -> viewNote(archive.notes[input.toInt() - 1])
                input == "${archive.notes.size + 1}" -> return
                else -> println("Некорректный ввод, введите цифру от 0 до ${archive.notes.size + 1}.")
            }
        }
    }

    private fun displayNotes(archive: Archive) {
        println("Выбор заметки в архиве '${archive.name}':")
        println("0. Создать заметку")
        archive.notes.forEachIndexed { index, note -> println("${index + 1}. ${note.title}") }
        println("${archive.notes.size + 1}. Назад")
    }

    private fun createNote(archive: Archive) {
        print("Введите заголовок заметки: ")
        val title = readUserInput()
        if (title.isBlank()) {
            println("Заголовок заметки не может быть пустым.\n")
            return
        }

        print("Введите текст заметки: ")
        val content = readUserInput()
        if (content.isBlank()) {
            println("Текст заметки не может быть пустым.\n")
            return
        }

        archive.addNote(Note(title, content))
        println("Заметка '$title' добавлена в архив '${archive.name}'.")
    }

    private fun readUserInput(): String {
        return readlnOrNull()?.trim() ?: ""
    }

    private fun viewNote(note: Note) {
        println("Заголовок: ${note.title}")
        println("Текст: ${note.content}")
        println("Нажмите Enter, чтобы вернуться.")
        readlnOrNull()
    }
}
