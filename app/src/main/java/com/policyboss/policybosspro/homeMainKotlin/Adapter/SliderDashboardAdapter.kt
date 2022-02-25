package com.demo.kotlindemoapp.HomeMain.CarouselViewPager.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.demo.kotlindemoapp.HomeMain.CarouselViewPager.SliderData

import com.google.android.material.card.MaterialCardView
import com.policyboss.policybosspro.R

class SliderAdapter(
    val context: Context,
    val viewPager2 : ViewPager2,
    val sliderList: ArrayList<SliderData>
)
    : RecyclerView.Adapter<SliderAdapter.ModelViewHolder>(){





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {

        val view  = LayoutInflater.from(parent.context).inflate(R.layout.slider_item_model, parent, false)

        return ModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {

        holder.txtTitle.text = sliderList[position].title
        holder.txtDetail.text = sliderList[position].detail

//        Glide.with(context)
//            .load(sliderList[position].image)
//            .into(holder.img)

        holder.img.setImageResource(sliderList[position].image)


//        if(position  == sliderList.size )
//        {
//            viewPager.post(run)
//        }

        holder.cvSlider.setOnClickListener {

          Toast.makeText(context,"Data "+ position,Toast.LENGTH_SHORT).show()


        }

    }

    override fun getItemCount() = sliderList.size



  inner  class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cvSlider : MaterialCardView = itemView.findViewById(R.id.cvSlider)
        val img : AppCompatImageView = itemView.findViewById(R.id.imgModel)
        val txtTitle : AppCompatTextView = itemView.findViewById(R.id.txtTitle)
        val txtDetail : AppCompatTextView =  itemView.findViewById(R.id.txtDetail)


    }




}