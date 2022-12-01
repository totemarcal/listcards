package com.example.listcards.presenter.view.fragment

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.listcards.R
import com.example.listcards.databinding.FragmentSecondBinding
import com.example.listcards.presenter.model.CardsUiModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.item_event.view.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private var cardUiModel : CardsUiModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    private fun showLoading(show: Boolean) {
        progressBarSec.visibility = if(show) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        cardUiModel = arguments?.getSerializable("cardUiModel") as CardsUiModel?
        Picasso.get().load(cardUiModel?.img).into(img)
        tvDetails.text = cardUiModel?.name
        textType.text = "Type: " + cardUiModel?.type
        textAttack.also {
            it.text = "Attack: " + cardUiModel?.attack
            it.visibility = if(cardUiModel?.attack == null) View.GONE else View.VISIBLE
        }
        textCardSet.also {
            it.text = "Card Set: " + cardUiModel?.cardSet
            it.visibility = if(cardUiModel?.cardSet == null) View.GONE else View.VISIBLE
        }
        textText.also {
            it.text = cardUiModel?.text?.let { it2 ->
                HtmlCompat.fromHtml(it2, HtmlCompat.FROM_HTML_MODE_LEGACY);
            }
            it.visibility = if(cardUiModel?.text == null) View.GONE else View.VISIBLE
        }
        textCost.also {
            it.text = "Cost: " + cardUiModel?.cost
            it.visibility = if(cardUiModel?.cost == null) View.GONE else View.VISIBLE
        }
        textRarity.also {
            it.text = "Rarity: " + cardUiModel?.rarity
            it.visibility = if(cardUiModel?.rarity == null) View.GONE else View.VISIBLE
        }
        textHealth.also {
            it.text = "Health: " + cardUiModel?.health
            it.visibility = if(cardUiModel?.health == null) View.GONE else View.VISIBLE
        }
        textFaction.also {
            it.text = "Faction: " + cardUiModel?.faction
            it.visibility = if(cardUiModel?.faction == null) View.GONE else View.VISIBLE
        }

        showLoading(false)
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}