import java.lang.RuntimeException


fun main() {
    val serviceNote = NoteService()
    serviceNote.add(Note(text = "Note1"))
    serviceNote.add(Note(text = "Note2"))
    serviceNote.add(Note(text = "Note3"))
    serviceNote.add(Note(text = "Note4"))
    serviceNote.createComment(Note(1), comment = "Comment1")
    serviceNote.createComment(Note(3), comment = "Comment3")
    serviceNote.createComment(Note(3), comment = "Comment3-2")
    serviceNote.createComment(Note(3), comment = "Comment3-3")
    serviceNote.createComment(Note(4), comment = "Hi")
    serviceNote.deleteComment(Note(3), commentId = 2)
    serviceNote.delete(Note(2))
    println(serviceNote.getById(8))
    println(serviceNote.getNote())
    serviceNote.editComment(Note(1), commentId = 1, message = "Yoops")
    serviceNote.getComment(3)
    println(serviceNote.getComment(3))
    serviceNote.restoreComment(Note(3), commentId = 2)
    println(serviceNote.getComment(3))
    println(serviceNote.getNote())

}

interface CrudServise<A> {
    fun add(elem: A): A                                            // Создание новой заметки
    fun delete(elem: A): Boolean                                    // Удаление заиетки текущего пользователя
    fun edit(elem: A, text: String): A                              // Редактирует заметку текущего пользователя
    fun getNote(): List<A>                                          // Возвращает список заметок пользователя
    fun createComment(elem: A, message: String): Boolean               // Добавляет новый комментарий к заметке
    fun deleteComment(elem: A, commentId: Int): Boolean                             // Удаляет комментарий к заметке
    fun editComment(
        elem: A,
        commentId: Int,
        message: String
    ): Boolean              // Редактирует указанный комментарий у заметки

    fun getById(id: Int): Note?                                        // Возвращает заметку по её id
    fun getComment(noteId: Int): List<String>                      // Возвращает список комментариев
    fun restoreComment(
        elem: A,
        commentId: Int
    ): Boolean                            // Восстанавливает удаленный комментарий
}

data class Note(
    var id: Int = 0,                                                       // id заметки
    var text: String = "",                                                // Текст заметки
    var comments: MutableList<Comments> = mutableListOf<Comments>()      // Комментарий к заметке
) {
}

data class Comments(
    var id: Int = 0,                 // id комментария
    var text: String = "",          // текст комментария
    var flag: Boolean = false       // False - комментарий удален или его нет, True - комментарий есть или восстановлен
)

class PostNotFoundException(message: String) : RuntimeException(message)

class NoteService() : CrudServise<Note> {
    private var notes = mutableListOf<Note>()
    private var countNote: Int = 1


    override fun toString(): String {
        return notes.toString()
    }

    override fun add(elem: Note): Note {
        elem.id = countNote
        countNote++
        notes += elem
        return notes.last()
    }

    override fun delete(elem: Note): Boolean {
        for ((index, deleteNote) in notes.withIndex()) {
            if (deleteNote.id == elem.id) {
                notes.remove(deleteNote)
                return true
            }
        }
        return false
    }

    override fun edit(elem: Note, text: String): Note {
        for ((index, editNote) in notes.withIndex()) {
            if (editNote.id == elem.id) {
                notes[index].text = text
                return notes[index]
            }
        }
        throw PostNotFoundException("Заметки с id = ${elem.id} не существует")

    }

    override fun getNote(): List<Note> {
        return notes
    }

    override fun createComment(elem: Note, comment: String): Boolean {
        for ((index, note) in notes.withIndex()) {
            if (note.id == elem.id) {
                notes[index].comments.add(Comments(note.comments.count() + 1, text = comment, flag = true))
                return true
            }
        }
        return false
    }

    override fun deleteComment(elem: Note, commentId: Int): Boolean {
        for ((index, note) in notes.withIndex()) {
            if (note.id == elem.id) {
                for (i in 0..(notes[index].comments.size - 1)) {
                    if (notes[index].comments[i].id == commentId) {
                        notes[index].comments[i].flag = false
                        return true
                    }
                }
            }
        }
        throw PostNotFoundException("Комментария с таким commentId = ${commentId} не суцествует")
    }

    override fun getById(id: Int): Note? {
        return notes.find { it.id == id }
    }

    override fun getComment(noteId: Int): List<String> {
        var message: List<String> = mutableListOf()
        for ((index, note) in notes.withIndex()) {
            if (note.id == noteId) {
                for ((ind, comment) in note.comments.withIndex()) {
                    if (comment.flag == true)
                        message += comment.text
                }
                return message
            }
        }
        throw PostNotFoundException("Комментариев к заметке $noteId  нет")
    }


    override fun editComment(elem: Note, commentId: Int, message: String): Boolean {
        for ((index, note) in notes.withIndex()) {
            if (note.id == elem.id) {
                for (i in 0..(notes[index].comments.size - 1)) {
                    if (notes[index].comments[i].id == commentId) {
                        notes[index].comments[i].text = message
                        return true
                    }
                }
            }
        }
        throw PostNotFoundException("Комментария с таким commentId = ${commentId} не суцествует, редактирование не возможно")
    }

    override fun restoreComment(elem: Note, commentId: Int): Boolean {
        for ((index, note) in notes.withIndex()) {
            if (note.id == elem.id) {
                for (i in 0..(notes[index].comments.size - 1)) {
                    if (notes[index].comments[i].id == commentId && notes[index].comments[i].flag != true) {
                        notes[index].comments[i].flag = true
                        return true
                    }
                }
            }
        }
        throw PostNotFoundException("Комментария с таким commentId = ${commentId} не суцествует, восстанавливать не чего")
    }

}