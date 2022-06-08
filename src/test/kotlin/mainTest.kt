import org.junit.Test
import junit.framework.Assert.assertEquals

class mainTest {
    @Test
    fun addFalse() {
        val service = NoteService()
        val note = service.add(Note(text = "Note1"))
        assertEquals(2, note.id)
    }

    @Test
    fun addTrue() {
        val service = NoteService()
        val note = service.add(Note(text = "Note1"))
        assertEquals(1, note.id)
    }

    @Test
    fun createComment() {
        val service = NoteService()
        val comment = service.createComment(Note(1), comment = "Comment")
        assertEquals(true, true)
    }

    @Test
    fun createCommentFalse() {
        val service = NoteService()
        val comment = service.createComment(Note(2), comment = "Comment")
        assertEquals(true, false)
    }

    @Test

    fun deleteTrue() {
        val service = NoteService()
        service.add(Note(text = "Note1"))
        service.add(Note(text = "Note1"))
        service.add(Note(text = "Note1"))
        service.delete(Note(2))
        assert(true)

    }

    @Test
    fun deleteFalse() {
        val service = NoteService()
        service.add(Note(text = "Note1"))
        service.add(Note(text = "Note1"))
        service.add(Note(text = "Note1"))
        service.delete(Note(5))
        assertEquals(true, false)

    }

    @Test
    fun editNoteTrue() {
        val service = NoteService()
        service.add(Note(text = "Note1"))
        service.add(Note(text = "Note1"))
        service.edit(Note(1), text = "777")
        assertEquals("777", "777")
    }

    @Test
    fun editNoteTrueFalse() {
        val service = NoteService()
        service.add(Note(text = "Note1"))
        service.add(Note(text = "Note1"))
        service.edit(Note(1), text = "777")
        assertEquals("888", "777")
    }

    @Test
    fun deleteCommentTrue() {
        val service = NoteService()
        service.add(Note(text = "Note1"))
        service.add(Note(text = "Note1"))
        service.createComment(Note(1), comment = "Hi")
        service.deleteComment(Note(1), commentId = 1)
        assertEquals(true, true)
    }

    @Test(expected = PostNotFoundException::class)
    fun deleteCommentExeption() {
        val service = NoteService()
        service.add(Note(text = "Note1"))
        service.add(Note(text = "Note1"))
        service.createComment(Note(1), comment = "Hi")
        service.deleteComment(Note(1), commentId = 2)

    }

    @Test(expected = PostNotFoundException::class)
    fun deleteCommentExeptionFalse() {
        val service = NoteService()
        service.add(Note(text = "Note1"))
        service.add(Note(text = "Note1"))
        service.createComment(Note(1), comment = "Hi")
        service.deleteComment(Note(1), commentId = 2)
    }

    @Test
    fun editCommentTrue() {
        val service = NoteService()
        service.add(Note(text = "Note1"))
        service.add(Note(text = "Note1"))
        service.createComment(Note(1), comment = "Hi")
        service.editComment(Note(1), commentId = 1, message = "Yoops")
        assertEquals(true, true)
    }

    @Test(expected = PostNotFoundException::class)
    fun editCommentExeption() {
        val service = NoteService()
        service.add(Note(text = "Note1"))
        service.add(Note(text = "Note1"))
        service.createComment(Note(1), comment = "Hi")
        service.editComment(Note(1), commentId = 3, message = "Yoops")
    }

    @Test
    fun restoreComment() {
        val service = NoteService()
        service.add(Note(text = "Note1"))
        service.add(Note(text = "Note1"))
        service.createComment(Note(1), comment = "Hi")
        service.createComment(Note(1), comment = "Hello")
        service.deleteComment(Note(1), commentId = 2)
        service.restoreComment(Note(1), commentId = 2)
        assertEquals(true, true)
    }

    @Test(expected = PostNotFoundException::class)
    fun restoreCommentExeption() {
        val service = NoteService()
        service.add(Note(text = "Note1"))
        service.add(Note(text = "Note1"))
        service.createComment(Note(1), comment = "Hi")
        service.createComment(Note(1), comment = "Hello")
        service.deleteComment(Note(1), commentId = 2)
        service.restoreComment(Note(1), commentId = 5)

    }
}
