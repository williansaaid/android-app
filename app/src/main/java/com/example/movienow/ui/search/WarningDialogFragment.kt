package com.example.movienow.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.movienow.databinding.DialogWarningBinding

class WarningDialogFragment : DialogFragment() {

    private var _binding: DialogWarningBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogWarningBinding.inflate(inflater, container, false)
        // Hacemos el fondo del dialog transparente para que se vea el CardView
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.okButton.setOnClickListener {
            dismiss() // Cierra el diálogo al presionar el botón
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}