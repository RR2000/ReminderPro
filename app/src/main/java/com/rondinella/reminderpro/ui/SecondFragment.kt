package com.rondinella.reminderpro.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.rondinella.reminderpro.R
import com.rondinella.reminderpro.databinding.FragmentSecondBinding
import com.rondinella.reminderpro.tasks.Task
import com.rondinella.reminderpro.tasks.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddTask.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val description = binding.editTextDescription.text.toString()
            val startTime = LocalTime.parse(
                binding.editTextStartTime.text.toString(),
                DateTimeFormatter.ofPattern("HH:mm")
            )
            val duration = binding.editTextDuration.text.toString().toInt()

            val task = Task(
                title = title,
                description = description,
                dueTime = startTime,
                durationSlots = duration,
            )

            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                val db = Room.databaseBuilder(
                    requireContext(),
                    TaskDatabase::class.java, "task-database"
                ).build()

                db.taskDao().insertTask(task)

                withContext(Dispatchers.Main) {
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                }
            }
        }

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
