package com.antonvinicius.keepnotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.antonvinicius.keepnotes.R
import com.antonvinicius.keepnotes.database.AppDatabase
import com.antonvinicius.keepnotes.databinding.FragmentNoteCreateOrEditBinding
import com.antonvinicius.keepnotes.repository.NoteRepository
import com.antonvinicius.keepnotes.retrofit.ApiResult
import com.antonvinicius.keepnotes.retrofit.RetrofitInstance
import com.antonvinicius.keepnotes.retrofit.WebClient
import com.antonvinicius.keepnotes.ui.viewmodel.NoteCreateOrEditViewModel
import com.antonvinicius.keepnotes.ui.viewmodel.NoteCreateOrEditViewModelFactory
import com.antonvinicius.keepnotes.util.setTitle
import com.antonvinicius.keepnotes.util.showErrorMessage
import com.antonvinicius.keepnotes.util.showSuccessMessage
import kotlinx.coroutines.launch

class NoteCreateOrEditFragment : Fragment() {
    private var _binding: FragmentNoteCreateOrEditBinding? = null
    private val binding get() = _binding!!

    private val noteId by lazy {
        arguments?.getString(NoteDetailFragment.NOTE_ID_KEY)
    }

    private val viewModel by viewModels<NoteCreateOrEditViewModel>(factoryProducer = {
        NoteCreateOrEditViewModelFactory(
            NoteRepository(
                WebClient(
                    RetrofitInstance()
                ), AppDatabase.getDatabase(requireContext()).noteDao()
            ), noteId
        )
    })

    override fun onResume() {
        super.onResume()
        viewModel.findNote()?.observe(viewLifecycleOwner) { result ->
            result?.let {
                binding.title.setText(it.title)
                binding.content.setText(it.content)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleCreateOrUpdateEvents(whenCreate = {
            setTitle(getString(R.string.add))
            binding.save.text = getString(R.string.add)
        }, whenUpdate = {
            setTitle(getString(R.string.update))
            binding.save.text = getString(R.string.update)
        })

        binding.save.setOnClickListener {
            lifecycleScope.launch {
                viewModel.save(
                    content = binding.content.text.toString(), title = binding.title.text.toString()
                ).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is ApiResult.Error -> {
                            showErrorMessage(result.error)
                        }

                        is ApiResult.Loading -> {

                        }

                        is ApiResult.Success -> {
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteCreateOrEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}