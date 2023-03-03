package com.tech.b2simulator.presentation.question

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tech.b2simulator.common.getQuestionScoreColor
import com.tech.b2simulator.databinding.ViewQuestionBinding
import com.tech.b2simulator.domain.common.QuestionScoreType
import com.tech.b2simulator.domain.model.QuestionInfo

class QuestionListAdapter(val context: Context) :
    RecyclerView.Adapter<QuestionListAdapter.QuestionListVH>() {
    var data = listOf<QuestionInfo>()

    var listener: QuestionClickedListener? = null

    class QuestionListVH(val binding: ViewQuestionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionListVH {
        val binding = ViewQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        );
        return QuestionListVH(binding)
    }

    override fun onBindViewHolder(holder: QuestionListVH, position: Int) {
        val question = data[position]
        with(holder) {

            binding.progress.progress = (question.score.toFloat() / QuestionScoreType.MAX) * 100
            binding.progress.progressBarColor = ContextCompat.getColor(
                context,
                getQuestionScoreColor(question.score)
            )

            binding.question = question
            binding.root.setOnClickListener {
                listener?.onQuestionClicked(position, question)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface QuestionClickedListener {
        fun onQuestionClicked(position: Int, question: QuestionInfo)
    }
}