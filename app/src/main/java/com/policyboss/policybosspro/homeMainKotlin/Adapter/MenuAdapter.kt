package com.policyboss.policybosspro.homeMainKotlin.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.kotlindemoapp.HomeMain.CarouselViewPager.Adapter.SliderDashboardAdapter
import com.policyboss.policybosspro.R
import magicfinmart.datacomp.com.finmartserviceapi.model.MenuChild
import magicfinmart.datacomp.com.finmartserviceapi.model.MenuHeader

class MenuAdapter (

        var mContext : Fragment,

        var listMenu : MutableList<MenuHeader>
        ): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>(){



    inner  class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var lyParent : LinearLayout
        var imgMenu : ImageView
        var imgArrow : ImageView
        var txtMenuName : TextView
        var menuView : View
        var rvChildMenu : RecyclerView

        init {

            lyParent = itemView.findViewById(R.id.lyParent)
            imgMenu = itemView.findViewById(R.id.imgMenu)
            imgArrow = itemView.findViewById(R.id.imgArrow)
            txtMenuName = itemView.findViewById(R.id.txtMenuName)
            menuView = itemView.findViewById(R.id.menuView)
            rvChildMenu = itemView.findViewById(R.id.rvChildMenu)

            rvChildMenu.layoutManager = LinearLayoutManager(mContext.requireContext(), LinearLayoutManager.VERTICAL, false)
            rvChildMenu.setHasFixedSize(true)
           // rvChildMenu.isNestedScrollingEnabled = false

        }

        fun bind(menuHeader:  MenuHeader){

            txtMenuName.text = menuHeader.headerName
            imgMenu.setImageDrawable(mContext.requireContext().getDrawable(menuHeader.mheaderImg))

            if(menuHeader.isExpanded){

                rvChildMenu.visibility = View.VISIBLE
                menuView.visibility =  View.VISIBLE
                imgArrow.setImageDrawable(ContextCompat.getDrawable(mContext.requireContext(),R.drawable.minus))

            }else{

                rvChildMenu.visibility = View.GONE
                menuView.visibility =  View.GONE
                imgArrow.setImageDrawable(ContextCompat.getDrawable(mContext.requireContext(),R.drawable.pluse))


            }

            if(!menuHeader.headerName.equals("LOG-OUT")){
                rvChildMenu.adapter = MenuChildAdapter(mContext,menuHeader.menuChildSection)

               // imgArrow.visibility =  View.VISIBLE


            }else{

                rvChildMenu.adapter = null

                //imgArrow.visibility =  View.GONE

            }


        }
    }







    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.MenuViewHolder {

        var layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        var view : View

           view = layoutInflater.inflate(R.layout.menu_item,parent,false)
           return MenuViewHolder(view)


    }


    override fun onBindViewHolder(holder: MenuAdapter.MenuViewHolder, position: Int) {
            var  menuHeader:  MenuHeader = listMenu[position]

             holder.bind( menuHeader = menuHeader)

        holder.lyParent.setOnClickListener{

            if(menuHeader.headerName.equals("LOG-OUT")){

            }
            if(menuHeader.isExpanded){


                holder.rvChildMenu.visibility = View.GONE
                holder.menuView.visibility =  View.GONE
                holder.imgArrow.setImageDrawable(ContextCompat.getDrawable(mContext.requireContext(),R.drawable.pluse))


            }

            else{


                holder.rvChildMenu.visibility = View.VISIBLE
                holder.menuView.visibility =  View.VISIBLE
                holder.imgArrow.setImageDrawable(ContextCompat.getDrawable(mContext.requireContext(),R.drawable.minus))



            }

            modifyMenu( menuHeader )
        }



    }

    override fun getItemCount(): Int = listMenu.size


   fun  modifyMenu(    menuHeader:  MenuHeader ){

       for( menu in listMenu){

           if(menu.headerName.toString().equals(menuHeader.headerName, ignoreCase = true)){

              if( !menu.isExpanded) {
                  menu.isExpanded = true
              }else{
                  menu.isExpanded = false
              }

           }else{
               menu.isExpanded = false
           }
       }
       notifyDataSetChanged()



   }



}