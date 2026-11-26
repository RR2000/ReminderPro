package dev.raoulrondinella.reminderpro.tasks

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Task::class, Subtask::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, TimeConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun subtaskDao(): SubtaskDao
}
