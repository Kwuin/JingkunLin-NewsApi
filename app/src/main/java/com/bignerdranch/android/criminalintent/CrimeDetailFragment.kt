package com.bignerdranch.android.criminalintent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.criminalintent.databinding.FragmentNewsDetailBinding
import com.bignerdranch.android.criminalintent.api.Article
import com.bignerdranch.android.criminalintent.api.NewsApi
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import java.util.UUID
private const val TAG = "CrimeDetailFragment"

class CrimeDetailFragment : Fragment() {

    private var _binding: FragmentNewsDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val args: CrimeDetailFragmentArgs by navArgs()

    private val crimeDetailViewModel: CrimeDetailViewModel by viewModels {
        CrimeDetailViewModelFactory(args.crimeId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val crime = Crime(
            id = UUID.randomUUID(),
            title = "",
            date = Date(),
            isSolved = false
        )

        Log.d(TAG, "The crime ID is: ${args.crimeId}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchNews()


    }
    private fun fetchNews() {
        val retrofit = getRetrofit()
        val newsApi = retrofit.create(NewsApi::class.java)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val response = newsApi.fetchNews()
                if (response.status == "ok" && response.articles.isNotEmpty()) {
                    updateUi(response.articles.first()) // Displaying the first article for simplicity
                } else {
                    // Handle the case where the fetch was not successful or no articles were found
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/") // Ensure this is the correct base URL for your API
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun updateUi(article: Article) {
        binding.apply {
//            Glide.with(this@CrimeDetailFragment)
//                .load(article.urlToImage)
//                .into(imageViewArticle)
            textViewTitle.text = article.title
            textViewAuthor.text = article.author ?: "Unknown Author"
            textViewDescription.text = article.description
            textViewPublishedAt.text = formatDate(article.publishedAt)

            buttonOpenUrl.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(intent)
            }
        }
    }

    private fun formatDate(dateString: String): String {
        // Simplify for example. Implement actual date formatting
        return dateString.substring(0, 10)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
