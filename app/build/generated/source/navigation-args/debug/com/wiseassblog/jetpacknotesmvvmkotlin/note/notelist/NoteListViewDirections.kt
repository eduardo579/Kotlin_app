package com.wiseassblog.jetpacknotesmvvmkotlin.note.notelist

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.wiseassblog.jetpacknotesmvvmkotlin.R
import kotlin.Int
import kotlin.String

class NoteListViewDirections private constructor() {
    private data class ActionNoteListViewToNoteDetailView(val noteId: String = "\"\"") :
            NavDirections {
        override fun getActionId(): Int = R.id.action_noteListView_to_noteDetailView

        override fun getArguments(): Bundle {
            val result = Bundle()
            result.putString("noteId", this.noteId)
            return result
        }
    }

    companion object {
        fun actionNoteListViewToNoteDetailView(noteId: String = "\"\""): NavDirections =
                ActionNoteListViewToNoteDetailView(noteId)

        fun actionNoteListViewToLoginActivity(): NavDirections =
                ActionOnlyNavDirections(R.id.action_noteListView_to_loginActivity)
    }
}
