package com.example.bitirmeprojesi.screens.fragments.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.FragmentSaveCardBinding
import com.example.bitirmeprojesi.screens.fragments.PaymentSuccessBottomSheet
import com.example.bitirmeprojesi.viewmodel.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class SaveCardFragment : Fragment() {

    private lateinit var binding: FragmentSaveCardBinding

    private lateinit var frontCard: View
    private lateinit var backCard: View

    private var isBackVisible = false
    private var isFormatting = false

    private val viewModel: CartViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSaveCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        frontCard = binding.includeCardFront.root
        backCard = binding.includeCardBack.root


        frontCard.visibility = View.VISIBLE
        backCard.visibility = View.GONE

        setupTapGesture()
        setupFormListeners()

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        FirebaseDatabase.getInstance().reference.child("Users").child(uid!!).child("name")
            .get()
            .addOnSuccessListener { snapshot ->
                val userName = snapshot.getValue(String::class.java)
                userName?.let {
                    viewModel.loadCart(it)
                    viewModel.cartItems.observe(viewLifecycleOwner) { items ->
                        val totalPrice = items.sumOf { it.fiyat * it.siparisAdeti }
                        binding.textViewPrice.text = "Toplam: $totalPrice ₺ "
                    }
                }
            }


        binding.buttonSubmit.setOnClickListener {
            val cardNumber = binding.editTextCardNumber.text.toString()
            val cardHolder = binding.editTextName.text.toString()
            val validThru = binding.editTextExpiration.text.toString()
            val cardCvv = binding.editTextCVV.text.toString()

            if (cardNumber.isBlank() || cardHolder.isBlank() || validThru.isBlank() || cardCvv.isBlank()) {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
            }else{
                val bottomSheet = PaymentSuccessBottomSheet()
                bottomSheet.show(parentFragmentManager, "PaymentSuccess")

            }

        }

    }

    private fun setupTapGesture() {
        binding.cardContainer.setOnClickListener {
            flipCard()
        }
    }

    private fun flipCard() {
        val scale = requireContext().resources.displayMetrics.density
        frontCard.cameraDistance = 8000 * scale
        backCard.cameraDistance = 8000 * scale

        if (!isBackVisible) {
            val animatorOut = ObjectAnimator.ofFloat(frontCard, "rotationY", 0f, 90f)
            animatorOut.duration = 300
            animatorOut.interpolator = AccelerateDecelerateInterpolator()

            val animatorIn = ObjectAnimator.ofFloat(backCard, "rotationY", -90f, 0f)
            animatorIn.duration = 300
            animatorIn.interpolator = OvershootInterpolator()

            animatorOut.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    frontCard.visibility = View.GONE
                    backCard.visibility = View.VISIBLE
                    animatorIn.start()
                }
            })

            animatorOut.start()
        } else {
            val animatorOut = ObjectAnimator.ofFloat(backCard, "rotationY", 0f, 90f)
            animatorOut.duration = 300
            animatorOut.interpolator = AccelerateDecelerateInterpolator()

            val animatorIn = ObjectAnimator.ofFloat(frontCard, "rotationY", -90f, 0f)
            animatorIn.duration = 300
            animatorIn.interpolator = OvershootInterpolator()

            animatorOut.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    backCard.visibility = View.GONE
                    frontCard.visibility = View.VISIBLE
                    animatorIn.start()
                }
            })

            animatorOut.start()
        }

        isBackVisible = !isBackVisible
    }

    private fun setupFormListeners() {
        val textViewCardNumber = frontCard.findViewById<TextView>(R.id.textViewCardNumber)
        val textViewCardHolder = frontCard.findViewById<TextView>(R.id.textViewCardHolder)
        val textViewValidThru = frontCard.findViewById<TextView>(R.id.textViewValidThru)
        val textViewCvv = backCard.findViewById<TextView>(R.id.textViewCvv)
        val imageViewCardType = frontCard.findViewById<ImageView>(R.id.imageViewCardType)

        binding.editTextCardNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return
                isFormatting = true

                val digitsOnly = s.toString().replace(" ", "")
                val formatted = StringBuilder()

                for (i in digitsOnly.indices) {
                    formatted.append(digitsOnly[i])
                    if ((i + 1) % 4 == 0 && (i + 1) != digitsOnly.length) {
                        formatted.append(" ")
                    }
                }

                val cursorPosition = binding.editTextCardNumber.selectionStart
                val beforeLength = s.toString().length
                val afterLength = formatted.length

                binding.editTextCardNumber.setText(formatted.toString())

                val newCursorPosition = cursorPosition + (afterLength - beforeLength)
                binding.editTextCardNumber.setSelection(newCursorPosition.coerceAtMost(formatted.length))

                isFormatting = false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val formatted = s.toString().replace(" ", "").chunked(4).joinToString(" ")
                textViewCardNumber.text = formatted

                val pureNumber = s.toString().replace(" ", "")
                when {
                    pureNumber.startsWith("4") -> imageViewCardType.setImageResource(R.drawable.visa)
                    pureNumber.startsWith("5") -> imageViewCardType.setImageResource(R.drawable.mastercard)
                    pureNumber.startsWith("3") -> imageViewCardType.setImageResource(R.drawable.amex)
                    pureNumber.startsWith("9792") -> imageViewCardType.setImageResource(R.drawable.troy)
                    else -> imageViewCardType.setImageResource(R.drawable.baseline_credit_card_24)
                }
            }
        })

        binding.editTextName.addTextChangedListener {
            textViewCardHolder.text = it.toString().uppercase()
        }

        binding.editTextExpiration.filters = arrayOf(InputFilter.LengthFilter(5))
        binding.editTextExpiration.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isFormatting) return
                isFormatting = true

                var input = s.toString().replace("/", "")
                if (input.length > 2) {
                    input = input.substring(0, 2) + "/" + input.substring(2)
                }

                binding.editTextExpiration.setText(input)
                binding.editTextExpiration.setSelection(input.length)

                textViewValidThru.text = input

                isFormatting = false
            }
        })

        binding.editTextCVV.filters = arrayOf(InputFilter.LengthFilter(3))
        binding.editTextCVV.addTextChangedListener {
            textViewCvv.text = it.toString()
        }
    }
}
private fun getCardType(cardNumber: String): String {
    return when {
        cardNumber.startsWith("4") -> "Visa"
        cardNumber.startsWith("5") -> "MasterCard"
        cardNumber.startsWith("34") || cardNumber.startsWith("37") -> "American Express"
        cardNumber.startsWith("9792") -> "Troy"
        else -> "Bilinmiyor"
    }
}

