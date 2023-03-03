package com.tech.b2simulator.presentation.exam

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tech.b2simulator.databinding.ViewExamBinding
import com.tech.b2simulator.domain.model.ExamInfo

class ExamListAdapter(val context: Context) :
    RecyclerView.Adapter<ExamListAdapter.ExamListVH>() {
    var data = listOf<ExamInfo>()

    var listener: ExamClickedListener? = null

    class ExamListVH(val binding: ViewExamBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamListVH {
        val binding = ViewExamBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExamListVH(binding)
    }

    override fun onBindViewHolder(holder: ExamListVH, position: Int) {
        val exam = data[position]
        with(holder) {
            binding.exam = exam
            binding.root.setOnClickListener {
                listener?.onExamClicked(position, exam)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface ExamClickedListener {
        fun onExamClicked(position: Int, exam: ExamInfo)
    }
}