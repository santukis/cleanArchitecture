package com.santukis.cleanarchitecture.game.ui.binding

import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.OnItemClickListener
import com.santukis.cleanarchitecture.databinding.ElementGameAnswerBinding
import com.santukis.cleanarchitecture.game.domain.model.Answer

class AnswerViewHolder(binding: ElementGameAnswerBinding): ItemsAdapter.ItemBindingViewHolder<Answer, ElementGameAnswerBinding>(binding) {

    override fun bind(value: Answer, position: Int) {
        binding.answer = value
        binding.option.text = listOf("A", "B", "C").getOrNull(position) ?: ""
    }

    override fun setOnItemClickListener(onItemClickListener: OnItemClickListener?, item: Answer, position: Int) {
        binding.root.setOnClickListener { onItemClickListener?.onItemClick(binding.root, item) }
    }
}