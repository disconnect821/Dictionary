package com.example.dictionary.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.MeaningsListViewItemBinding
import com.example.dictionary.models.Meaning

class WordMeaningAdapter (private var  meaningList  :List<Meaning>) : RecyclerView.Adapter<WordMeaningAdapter.MeaningViewHolder>() {
    class MeaningViewHolder ( private  val binding : MeaningsListViewItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(meaning : Meaning){
            binding.partOfSpeechTextView.text = meaning.partOfSpeech
            binding.meaningTextView.text = meaning.definitions.joinToString("\n\n") {
                val currIndex = meaning.definitions.indexOf(it)
                (currIndex+1).toString()+". "+it.definition.toString()
            }
            if(meaning.synonyms.isEmpty()){
                binding.synonymsTextView.visibility = View.GONE
                binding.synonymTitle.visibility = View.GONE
            }else{
                binding.synonymsTextView.visibility = View.VISIBLE
                binding.synonymTitle.visibility = View.VISIBLE
                binding.synonymsTextView.text = meaning.synonyms.joinToString (", ")
            }
            if(meaning.antonyms.isEmpty()){
                binding.antonymsTextView.visibility = View.GONE
                binding.antonymTitle.visibility = View.GONE
            }else{
                binding.antonymsTextView.visibility = View.VISIBLE
                binding.antonymTitle.visibility = View.VISIBLE
                binding.antonymsTextView.text = meaning.antonyms.joinToString (", ")
            }
        }
    }
    fun updateNewData(newMeaningList : List<Meaning>){
        this.meaningList = newMeaningList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding = MeaningsListViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeaningViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return meaningList.size
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        holder.bind(meaningList[position])
    }
}