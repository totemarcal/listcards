package com.example.listcards.presenter.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listcards.R
import com.example.listcards.presenter.adapter.CardsAdapter
import com.example.listcards.presenter.adapter.OnCLickEvent
import com.example.listcards.databinding.FragmentFirstBinding
import com.example.listcards.presenter.features.card.CardIntent
import com.example.listcards.presenter.features.card.CardState
import com.example.listcards.presenter.model.CardsUiModel
import com.example.listcards.presenter.viewmodel.CardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel



/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), OnCLickEvent {

    private var _binding: FragmentFirstBinding? = null
    private val viewModel : CardViewModel by viewModel()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        viewModel.liveData.observe(this) { state ->
            when (state) {
                is CardState.ResultAllCards -> {
                    updateEvents(state.data)
                }
                is CardState.Error -> {
                    Toast.makeText(context, state.error?.message, Toast.LENGTH_LONG).show()
                }
                is CardState.Loading -> {
                    if (state.loading) {
                        showLoading(true)
                    } else {
                        showLoading(false)
                    }
                }
            }
        }
        viewModel.dispatchIntent(CardIntent.LoadAllCards)
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if(show) View.VISIBLE else View.GONE
        binding.recycler.visibility = if(show) View.GONE else View.VISIBLE
    }

    private fun updateEvents(cards: List<CardsUiModel>) {
        binding.recycler.adapter = CardsAdapter(cards = cards,this)
        binding.recycler.layoutManager = LinearLayoutManager(context)
    }

    override fun onClickEvent(cardUiModel: CardsUiModel) {
        val bundle = bundleOf("cardUiModel" to cardUiModel)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}