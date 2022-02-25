package com.demo.kotlindemoapp.HomeMain.CarouselViewPager.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.policyboss.policybosspro.BuildConfig
import com.policyboss.policybosspro.MyApplication
import com.policyboss.policybosspro.R
import com.policyboss.policybosspro.health.HealthQuoteAppActivity
import com.policyboss.policybosspro.healthcheckupplans.HealthCheckUpListActivity
import com.policyboss.policybosspro.homeMainKotlin.HomeMainActivity
import com.policyboss.policybosspro.motor.privatecar.activity.PrivateCarDetailActivity
import com.policyboss.policybosspro.motor.twowheeler.activity.TwoWheelerQuoteAppActivity
import com.policyboss.policybosspro.ncd.NCDActivity
import com.policyboss.policybosspro.offline_quotes.AddNewOfflineQuotesActivity
import com.policyboss.policybosspro.quicklead.QuickLeadActivity
import com.policyboss.policybosspro.term.termselection.TermSelectionActivity
import com.policyboss.policybosspro.ultralaksha.ultra_selection.UltraLakshaSelectionActivity
import com.policyboss.policybosspro.utility.Constants
import com.policyboss.policybosspro.webviews.CommonWebViewActivity
import magicfinmart.datacomp.com.finmartserviceapi.Utility
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController
import magicfinmart.datacomp.com.finmartserviceapi.model.DashboardMultiLangEntity

class SliderImageAdapter(

        val mContext: Context,
        val insurImgList: MutableList<DashboardMultiLangEntity>
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


        holder.txtSlideImg.text = insurImgList[position].productName.toString().toUpperCase()
                                    .replace("INSURANCE", "", false)

        if (insurImgList[position].icon == -1) {
            Glide.with(mContext)
                    .load(insurImgList[position].serverIcon)
                    .into((holder.imgSlide))
        } else {
            holder.imgSlide.setImageResource(insurImgList[position].icon)
        }

        if(insurImgList[position].checked){

            holder.sliderLinearParent.setBackgroundColor(
                    ContextCompat.getColor(
                            mContext,
                            R.color.blue_menu
                    )
            )
        }else{
            holder.sliderLinearParent.setBackgroundColor(
                    ContextCompat.getColor(
                            mContext,
                            R.color.bg_light
                    )
            )

        }

        holder.sliderLinearParent.setOnClickListener {




            modifySliderList(position, insurImgList[position])


            (mContext as HomeMainActivity).getSliderImagePosition(position)

        }





    }

    override fun getItemCount(): Int {
       return insurImgList.size
    }



    fun modifySliderList(position: Int, MainslideData: DashboardMultiLangEntity){
        var pos = 0
      for(slideData in insurImgList){

          if(slideData.productId == MainslideData.productId){

              insurImgList[pos].checked = true
          }else{

              insurImgList[pos].checked= false
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




}