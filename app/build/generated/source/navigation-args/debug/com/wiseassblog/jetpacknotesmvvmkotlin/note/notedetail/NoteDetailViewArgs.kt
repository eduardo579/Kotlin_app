package com.wiseassblog.jetpacknotesmvvmkotlin.note.notedetail

import android.os.Bundle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

data class NoteDetailViewArgs(val noteId: String = "\"\"") : NavArgs {
    fun toBundle(): Bundle {
        val result = Bundle()
        result.putString("noteId", this.noteId)
        return result
    }

    companion object {
        @JvmStatic
        fun fromBundle(bundle: Bundle): NoteDetailViewArgs {
            bundle.setClassLoader(NoteDetailViewArgs::class.java.classLoader)
            val __noteId : String?
            if (bundle.containsKey("noteId")) {
                __noteId = bundle.getString("noteId")
                if (__noteId == null) {
                    throw IllegalArgumentException("Argument \"noteId\" is marked as non-null but was passed a null value.")
                }
            } else {
                __noteId = "\"\""
            }
            return NoteDetailViewArgs(__noteId)
        }
    }
}
