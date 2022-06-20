package com.policyboss.policybosspro.register.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.policyboss.policybosspro.R
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.RegisterPospAmountModel
/**
 * Created by Umesh on 17-06-2022.
 */
class PospAmountDetailAdapter (

    val mcontetx : Context,
    val lstDescription: List<String>
) : RecyclerView.Adapter<PospAmountDetailAdapter.PospDetailHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PospDetailHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_posp_amount_detail_item,
            parent,
            false
        )
        return PospDetailHolder(view)
    }

    override fun onBindViewHolder(holder: PospDetailHolder, position: Int) {

        holder.txtDetails.text = lstDescription[position]
    }

    override fun getItemCount() =  lstDescription.size


    inner class PospDetailHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val txtDetails = itemView.findViewById<TextView>(R.id.txtDetails)

    }
}