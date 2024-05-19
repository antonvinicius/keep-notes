package com.antonvinicius.keepnotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.antonvinicius.keepnotes.R
import com.antonvinicius.keepnotes.database.AppDatabase
import com.antonvinicius.keepnotes.databinding.FragmentNotesBinding
import com.antonvinicius.keepnotes.model.NoteDto
import com.antonvinicius.keepnotes.repository.NoteRepository
import com.antonvinicius.keepnotes.retrofit.ApiResult
import com.antonvinicius.keepnotes.retrofit.RetrofitInstance
import com.antonvinicius.keepnotes.retrofit.WebClient
import com.antonvinicius.keepnotes.ui.adapter.NoteAdapter
import com.antonvinicius.keepnotes.ui.viewmodel.NotesViewModel
import com.antonvinicius.keepnotes.ui.viewmodel.NotesViewModelFactory
import com.antonvinicius.keepnotes.util.setTitle
import com.antonvinicius.keepnotes.util.showErrorMessage
import com.antonvinicius.keepnotes.util.showSuccessMessage
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        NoteAdapter { handleNotePressed(it) }
    }

    private fun handleNotePressed(note: NoteDto) {
        findNavController().navigate(
            R.id.action_FirstFragment_to_NoteDetailFragment, bundleOf(
                NoteDetailFragment.NOTE_ID_KEY to note.id
            )
        )
    }

    private val viewModel: NotesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(getString(R.string.notes))

        setupRecyclerView()

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_NoteCreateOrEditFragment)
        }
    }

    private fun setupRecyclerView() {
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.postsList.addItemDecoration(divider)
        binding.postsList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.resultLiveData.observe(viewLifecycleOwner) { result ->
            binding.progressBar.visibility = View.GONE
            result.data?.let {
                adapter.fillList(result.data)
            }

            when (result) {
                is ApiResult.Error -> {
                    showErrorMessage(result.error)
                }

                is ApiResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is ApiResult.Success -> {}
            }
        }

        lifecycleScope.launch {
            viewModel.fetchNotes()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}