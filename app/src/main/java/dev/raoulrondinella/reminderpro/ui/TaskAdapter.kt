package dev.raoulrondinella.reminderpro.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import dev.raoulrondinella.reminderpro.R
import dev.raoulrondinella.reminderpro.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter

class TaskAdapter(
    var tasks: List<Task>,
    private val onTaskClickListener: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // Define the view holder
    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
        val durationTextView: TextView = view.findViewById(R.id.durationTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.titleTextView.text = task.title

        // Format the LocalDate to a user-friendly string
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        holder.dateTextView.text = task.dueDate?.format(formatter)

        // Convert the duration (number of slots) to a user-friendly string
        val hours = task.durationSlots?.div(60)
        val minutes = task.durationSlots?.rem(60)
        holder.durationTextView.text = "${hours}h ${minutes}m"

        // Set OnClickListener for the item
        holder.itemView.setOnClickListener {
            onTaskClickListener.invoke(task)
        }
    }

    override fun getItemCount() = tasks.size
}
