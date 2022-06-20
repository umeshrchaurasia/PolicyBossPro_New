package com.policyboss.policybosspro.register.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.policyboss.policybosspro.R
import com.policyboss.policybosspro.register.RegisterActivity
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.RegisterPospAmountModel

/**
 * Created by Umesh on 17-06-2022.
 */
class RegisterPospAmountAdapter (

    val mContext: Context,
    val pospAmountList: List<RegisterPospAmountModel>
) : RecyclerView.Adapter<RegisterPospAmountAdapter.PospAmountHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PospAmountHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_posp_amount,
            parent,
            false
        )
        return PospAmountHolder(view)
    }

    override fun onBindViewHolder(holder: PospAmountHolder, position: Int) {

        val pospAmntEntity  = pospAmountList[position]

        holder.txtPospAmount.text = pospAmntEntity.posp_name
        holder.txtPospAmount.tag = pospAmntEntity.posp_amount

        if(pospAmntEntity.isCheck){

            holder.imgChk.setImageResource(R.drawable.roundshape_hollow_primary)

            (mContext as RegisterActivity).PospAmountAlert(
                pospAmntEntity.posp_header_desc,
                pospAmntEntity.posp_sub_header_desc,
                getDescList(pospAmntEntity.posp_desc))


        }else{
            holder.imgChk.setImageResource(R.drawable.roundshape_hollow_gray)
        }

        holder.lyAmnt.setOnClickListener{

            if(pospAmntEntity.isCheck){

                holder.imgChk.setImageResource(R.drawable.roundshape_hollow_primary)
                // showDialog(pospAmntEntity.posp_header_desc, pospAmntEntity.posp_desc)


            }else{
                holder.imgChk.setImageResource(R.drawable.roundshape_hollow_gray)
            }



            updateList(pospAmntEntity, position)

        }

        holder.imgInfo.setOnClickListener {

            (mContext as RegisterActivity).PospAmountAlert(
                pospAmntEntity.posp_header_desc,
                pospAmntEntity.posp_sub_header_desc,
                getDescList(pospAmntEntity.posp_desc))

        }
    }

    override fun getItemCount(): Int {
        return  pospAmountList.size
    }

    inner class PospAmountHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        val lyAmnt  :LinearLayout = itemView.findViewById(R.id.lyAmnt)
        val txtPospAmount :TextView =itemView.findViewById(R.id.txtPospAmount)
        val imgInfo : AppCompatImageView = itemView.findViewById(R.id.imgInfo)
        val imgChk : AppCompatImageView = itemView.findViewById(R.id.imgChk)

    }

    private fun updateList(curEntity: RegisterPospAmountModel, position: Int) {
        for (i in 0 until pospAmountList.size) {
            if (pospAmountList.get(i).id.equals(curEntity.id)) {
                if (curEntity.isCheck) {
                    pospAmountList.get(i).isCheck = false
                } else {
                    pospAmountList.get(i).isCheck = true
                }
            } else {
                pospAmountList.get(i).isCheck = false
            }
        }
        notifyDataSetChanged()


    }

    private fun getDescList(desc : String ) : List<String>{

        val lstDescription: List<String> = desc.split("|").map { it -> it.trim() }
        return lstDescription
    }


    private fun showDialog(title: String, desc : String) {


        val lstDescription: List<String> = desc.split("|").map { it -> it.trim() }
        val builder =  AlertDialog.Builder(mContext,R.style.CustomDialog)
            .create()
        val view = (mContext as RegisterActivity).layoutInflater.inflate(R.layout.layout_posp_amount_popup,null)
        builder.setView(view)
        val  btnClose = view.findViewById<Button>(R.id.btnClose)
        val  txtTitle = view.findViewById<TextView>(R.id.txtTitle)
        val  rvDetails = view.findViewById<RecyclerView>(R.id.rvDetails)
        txtTitle.text = title

        rvDetails.setLayoutManager(LinearLayoutManager(mContext))
        rvDetails.setHasFixedSize(true)
        rvDetails.setNestedScrollingEnabled(false)
        val PospAmountDetailAdapter = PospAmountDetailAdapter(mContext, lstDescription)
        rvDetails.setAdapter(PospAmountDetailAdapter)


        btnClose.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(true)
        builder.show()

        //layout_posp_amount_detail_item
    }
}