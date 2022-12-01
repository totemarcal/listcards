package com.example.listcards.presenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listcards.R
import com.example.listcards.databinding.FragmentFirstBinding
import com.example.listcards.databinding.ItemEventBinding
import com.example.listcards.presenter.model.CardsUiModel

import com.squareup.picasso.Picasso

class CardsAdapter(
    private val cards : List<CardsUiModel>,
    private val onCLickEvent: OnCLickEvent
) : RecyclerView.Adapter<CardsAdapter.CardViewHolder>() {

    private var _binding: ItemEventBinding? = null
    private val binding get() = _binding!!


    class CardViewHolder(itemView : ItemEventBinding) : RecyclerView.ViewHolder(itemView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        _binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]

        holder.itemView.apply {
            binding.tvTitle.text = card.name
            binding.tvCardSet.text = card.cardSet
            Picasso.get().load(card.img).into(binding.imgEvent)
            binding.clRoot.setOnClickListener { onCLickEvent.onClickEvent(card) }
        }
    }

    override fun getItemCount(): Int {
        return cards.size
    }
}