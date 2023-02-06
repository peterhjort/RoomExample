package com.example.roomexample

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.viewModelScope
import com.example.roomexample.databinding.ActivityFeedbackNoteBinding
import kotlinx.coroutines.launch

class FeedbackNoteActivity : AppCompatActivity() {
    lateinit var viewModel: FeedbackNoteActivityViewModel
    lateinit var binding: ActivityFeedbackNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFeedbackNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get()

        binding.button.setOnClickListener {
            val noteText = binding.editTextTextPersonName.text.toString()
            val amount: Int = binding.seekBar.progress
            viewModel.insertOpsLogEntry(amount, noteText)
            binding.editTextTextPersonName.text.clear()
            binding.seekBar.progress = binding.seekBar.min
        }
        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.valueView.text = progress.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }
}

class FeedbackNoteActivityViewModel: ViewModel() {
    private val repository = OpsLogRepository

    fun insertOpsLogEntry(amount: Int, noteText: String) {
        viewModelScope.launch {
            repository.newOpsLogEntry(amount, noteText)
        }
    }
}