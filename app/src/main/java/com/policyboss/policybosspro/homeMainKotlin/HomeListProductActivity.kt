package com.policyboss.policybosspro.homeMainKotlin


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.kotlindemoapp.HomeMain.CarouselViewPager.Adapter.SliderDashboardAdapter
import com.policyboss.policybosspro.BaseActivity
import com.policyboss.policybosspro.R
import com.policyboss.policybosspro.databinding.ActivityHomeListProductBinding
import magicfinmart.datacomp.com.finmartserviceapi.PrefManager
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController
import magicfinmart.datacomp.com.finmartserviceapi.finmart.APIResponse
import magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.register.RegisterController
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.LoginResponseEntity
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.ProductURLShareResponse
import magicfinmart.datacomp.com.finmartserviceapi.model.DashboardMultiLangEntity

class HomeListProductActivity : BaseActivity() , IResponseSubcriber, SliderDashboardAdapter.IDashboardAdapterCallBack{


    lateinit var binding: ActivityHomeListProductBinding
    lateinit var toolbar: ActionBar

    lateinit var db: DBPersistanceController
    lateinit var prefManager: PrefManager
    lateinit var loginResponseEntity: LoginResponseEntity

    var dashboardShareEntity: DashboardMultiLangEntity? = null



    lateinit var madapter: SliderDashboardAdapter
    lateinit var shareProdDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        toolbar = supportActionBar!!



        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("Home")
        }

        init()

    }

    private fun init(){

        db = DBPersistanceController(this)
        loginResponseEntity = db.userData

        prefManager = PrefManager(this)


        madapter = SliderDashboardAdapter(this, db.insurProductLangList, 1, this)

        binding.rvProduct.layoutManager = LinearLayoutManager(this)
        binding.rvProduct.setHasFixedSize(true)

        binding.rvProduct.adapter = madapter

    }

    fun shareDashbordProduct(dashboardMultiLangEntity: DashboardMultiLangEntity) {

        dashboardShareEntity = dashboardMultiLangEntity
        showDialog()
        //loginResponseEntity.getFBAId()
        RegisterController(this).getProductShareUrl(
                loginResponseEntity.fbaId,
                Integer.valueOf(loginResponseEntity.pospNo),
                dashboardMultiLangEntity.productId,
                0,
                this)
    }


    override fun shareProductPopUp(shareEntity: DashboardMultiLangEntity) {

        if (this::shareProdDialog.isInitialized  && shareProdDialog.isShowing()) {
            return
        }
        val builder = AlertDialog.Builder(this)
        val txtTitle: TextView
        val txtMessage: TextView
        val btnShare: Button
        val ivCross: ImageView
        val inflater = this.layoutInflater

        val dialogView = inflater.inflate(R.layout.layout_share_popup, null)
        builder.setView(dialogView)
        shareProdDialog = builder.create()
        // set the custom dialog components - text, image and button
        txtTitle = dialogView.findViewById<View>(R.id.txtTitle) as TextView
        txtMessage = dialogView.findViewById<View>(R.id.txtMessage) as TextView
        btnShare = dialogView.findViewById<View>(R.id.btnShare) as Button
        ivCross = dialogView.findViewById<View>(R.id.ivCross) as ImageView
        txtTitle.text = "" + shareEntity.title
        txtMessage.text = "" + shareEntity.popupmsg
        btnShare.setOnClickListener {
            shareDashbordProduct(shareEntity)
            shareProdDialog.dismiss()
        }
        ivCross.setOnClickListener { shareProdDialog.dismiss() }
        shareProdDialog.setCancelable(true)
        shareProdDialog.show()

    }

    override fun infoProductPopUp(dashEntity: DashboardMultiLangEntity) {

        openWebViewPopUp(binding.rvProduct, dashEntity.info, true, "")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }



    override fun OnSuccess(response: APIResponse?, message: String?) {

        cancelDialog()

        if (response is ProductURLShareResponse) {
            if (response.getStatusNo() == 0) {
                if (response.masterData != null) {
                    val shareEntity = response.masterData
                    if (dashboardShareEntity != null) {
                        datashareList(this, dashboardShareEntity?.getTitle()
                                ?: "", shareEntity.msg, shareEntity.url)
                    }
                }
            }
        }
    }


    override fun OnFailure(t: Throwable?) {
        cancelDialog()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()

    }

}