package dev.raoulrondinella.reminderpro.tasks

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "subtasks",
    foreignKeys = [
        ForeignKey(
            entity = Task::class,
            parentColumns = ["id"],
            childColumns = ["taskId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Subtask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val taskId: Int,

    val title: String,

    val description: String,

    @TypeConverters(DateConverter::class)
    val dueDate: LocalDate? = null,

    @TypeConverters(TimeConverter::class)
    val dueTime: LocalTime? = null,

    val startTimeSlot: Int? = null,

    val durationSlots: Int? = null,

    val isComplete: Boolean = false
)
