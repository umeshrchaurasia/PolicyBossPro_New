package com.policyboss.policybosspro.homeMainKotlin.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.policyboss.policybosspro.R
import com.policyboss.policybosspro.homeMainKotlin.BottomSheetDialogMenuFragment
import com.policyboss.policybosspro.homeMainKotlin.HomeMainActivity
import com.policyboss.policybosspro.myaccount.MyAccountActivity
import magicfinmart.datacomp.com.finmartserviceapi.model.MenuChild

class MenuChildAdapter(

        var mContext : Fragment,
        var menuChildList: MutableList<MenuChild>
) : RecyclerView.Adapter<MenuChildAdapter.ChildMenuHolder>() {

    inner class ChildMenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var lyParent : LinearLayout
        var imgMenu : ImageView
        var imgArrow : ImageView
        var txtMenuName : TextView
        var txtBottom : TextView

        init {
            lyParent  = itemView.findViewById(R.id.lyParent)
            imgMenu = itemView.findViewById(R.id.imgMenu)
            imgArrow = itemView.findViewById(R.id.imgArrow)
            txtMenuName = itemView.findViewById(R.id.txtMenuName)
            txtBottom = itemView.findViewById(R.id.txtBottom)

            imgArrow.visibility = View.GONE

        }

        fun bind(menuChild: MenuChild){

            txtMenuName.text = menuChild.getmChildName()
            imgMenu.setImageDrawable(mContext.requireContext().getDrawable(menuChild.getmImg()))

          if(  menuChild.getmId().equals("nav_logout")){

              txtBottom.visibility = View.INVISIBLE

            }else{
              txtBottom.visibility = View.GONE
          }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuChildAdapter.ChildMenuHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item_child, parent, false)
        return  ChildMenuHolder(view)
    }

    override fun onBindViewHolder(holder: MenuChildAdapter.ChildMenuHolder, position: Int) {

        holder.bind(menuChild = menuChildList[position])


        holder.lyParent.setOnClickListener {

           // navigateMenu(menuChild = menuChildList[position])

           (mContext as BottomSheetDialogMenuFragment).getNavigationMenu(menuChildList[position])

        }

    }

    override fun getItemCount(): Int  = menuChildList.size




}