package com.demo.kotlindemoapp.HomeMain.CarouselViewPager.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.demo.kotlindemoapp.HomeMain.CarouselViewPager.SliderData
import com.demo.kotlindemoapp.R
import com.demo.kotlindemoapp.home.HomeFragment

class SliderImageAdapter(

    val context: Context,
    val fragment: Fragment,
    val rvImgSlide: RecyclerView,
    val sliderImgList: ArrayList<SliderData>
)  : RecyclerView.Adapter<SliderImageAdapter.ImageViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        val view  = LayoutInflater.from(parent.context).inflate(
            R.layout.slider_image_model,
            parent,
            false
        )

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {


        holder.txtSlideImg.text = sliderImgList[position].title
        holder.imgSlide.setImageResource(sliderImgList[position].image)

//         if(position  == sliderImgList.size - 1)
//        {
//            rvImgSlide.post(run)
//        }

        if(sliderImgList[position].isChecked){

            holder.sliderLinearParent.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.blue_menu
                )
            )
        }else{
            holder.sliderLinearParent.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.bg_light
                )
            )

        }

        holder.sliderLinearParent.setOnClickListener {


            holder.sliderLinearParent.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.blue_menu
                )
            )

            modifySliderList(sliderImgList[position])

           // getPosition(position)
          // context (getSliderImagePosition(position))

            (fragment as HomeFragment).getSliderImagePosition(position)

        }





    }

    override fun getItemCount(): Int {
       return sliderImgList.size
    }



    fun modifySliderList(MainslideData: SliderData){
        var pos = 0
      for(slideData in sliderImgList){

          if(slideData.title.equals(MainslideData.title)){

              sliderImgList[pos].isChecked = true
          }else{

              sliderImgList[pos].isChecked = false
          }

          pos = pos + 1

      }

     notifyDataSetChanged()

    }
    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val imgSlide : AppCompatImageView = itemView.findViewById(R.id.imgSlide)
        val txtSlideImg : AppCompatTextView = itemView.findViewById(R.id.txtSlideImg)
        val sliderLinearParent :LinearLayout = itemView.findViewById(R.id.sliderLinearParent)
    }


    private val run = object : Runnable {
        override fun run() {

            sliderImgList.addAll(sliderImgList)
            notifyDataSetChanged()
        }

    }
}