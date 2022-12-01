package com.example.listcards.presenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listcards.R
import com.example.listcards.presenter.model.CardsUiModel

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_second.view.*
import kotlinx.android.synthetic.main.item_event.view.*

class CardsAdapter(
    private val cards : List<CardsUiModel>,
    private val onCLickEvent: OnCLickEvent
) : RecyclerView.Adapter<CardsAdapter.CardViewHolder>() {

    class CardViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_event,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]

        holder.itemView.apply {
            tvTitle.text = card.name
            tvDetails.text = card.cardSet
            Picasso.get().load(card.img).into(imgEvent)
            clRoot.setOnClickListener { onCLickEvent.onClickEvent(card) }
        }
    }

    override fun getItemCount(): Int {
        return cards.size
    }
}