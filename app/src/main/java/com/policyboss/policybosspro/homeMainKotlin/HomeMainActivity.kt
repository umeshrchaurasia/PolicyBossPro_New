package com.policyboss.policybosspro.homeMainKotlin

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.demo.kotlindemoapp.HomeMain.CarouselViewPager.Adapter.SliderDashboardAdapter
import com.demo.kotlindemoapp.HomeMain.CarouselViewPager.Adapter.SliderImageAdapter
import com.demo.kotlindemoapp.HomeMain.CarouselViewPager.CarouselTransformer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.policyboss.policybosspro.BaseActivity
import com.policyboss.policybosspro.BaseActivity.PermissionListener
import com.policyboss.policybosspro.BuildConfig
import com.policyboss.policybosspro.MyApplication
import com.policyboss.policybosspro.R
import com.policyboss.policybosspro.databinding.ActivityHomeMainBinding
import com.policyboss.policybosspro.homeMainKotlin.Adapter.MenuAdapter
import com.policyboss.policybosspro.knowledgeguru.KnowledgeGuruActivity
import com.policyboss.policybosspro.myaccount.MyAccountActivity
import com.policyboss.policybosspro.notification.NotificationActivity
import com.policyboss.policybosspro.salesmaterial.SalesMaterialActivity
import com.policyboss.policybosspro.switchuser.SwitchUserActivity
import com.policyboss.policybosspro.utility.CircleTransform
import com.policyboss.policybosspro.utility.Constants
import com.policyboss.policybosspro.utility.ReadDeviceID
import magicfinmart.datacomp.com.finmartserviceapi.PrefManager
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController
import magicfinmart.datacomp.com.finmartserviceapi.finmart.APIResponse
import magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.login.LoginController
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.masters.MasterController
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.register.RegisterController
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.LoginResponseEntity
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.MenuMasterResponse
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.UserConstantEntity
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.LoginRequestEntity
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.LoginResponse
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.ProductURLShareResponse
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.UserConstatntResponse
import magicfinmart.datacomp.com.finmartserviceapi.model.DashboardMultiLangEntity
import java.util.*

class HomeMainActivity : BaseActivity() , IResponseSubcriber , View.OnClickListener, BaseActivity.PopUpListener,
        BaseActivity.WebViewPopUpListener, PermissionListener , BottomSheetDialogMenuFragment.IBottomMenuCallback, SliderDashboardAdapter.IDashboardAdapterCallBack{

   // lateinit var toolbar: ActionBar
    lateinit var binding :ActivityHomeMainBinding

    lateinit var viewPager2 : ViewPager2
    lateinit var adapter: SliderDashboardAdapter
    lateinit var adapterImgSlider: SliderImageAdapter
    lateinit var adapterMenu : MenuAdapter

    lateinit var sliderHandler: Handler
    lateinit var sliderRun : Runnable



    ////////////////////////////////////////////

    lateinit var db: DBPersistanceController
    lateinit var prefManager: PrefManager
    lateinit var loginResponseEntity: LoginResponseEntity
    lateinit var userConstantEntity: UserConstantEntity

    var menuMasterResponse: MenuMasterResponse? = null
    var dashboardShareEntity: DashboardMultiLangEntity? = null



    lateinit var bottomSheetDialog : BottomSheetDialog
    lateinit var ivProfile : ImageView
    lateinit var shareProdDialog: AlertDialog
    lateinit var  MyUtilitiesDialog: AlertDialog

    lateinit var txtDetails : TextView
    lateinit var txtEntityName :TextView

    lateinit var llSwitchUser: LinearLayout

    var BottomType : String = ""

    var FBAID : String = ""
    var versionNAme: String = ""
    var pinfo: PackageInfo? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        init()

        setListener()

        // region Handling Bottom bar selection



        bindData()

        checkMarketingPopup()



        binding.bubbleTabBar.setSelected(0)
        binding.bubbleTabBar.setSelectedWithId(R.id.nav_home, true)

        binding.bubbleTabBar.addBubbleListener({


            when (it) {

                R.id.nav_home -> {


                }
                R.id.nav_notification -> {
                    startActivity(Intent(this@HomeMainActivity, NotificationActivity::class.java))
                    overridePendingTransition(0, 0)


                }
                R.id.nav_profile -> {

                    startActivity(Intent(this@HomeMainActivity, MyAccountActivity::class.java))
                    overridePendingTransition(0, 0)


                }

                R.id.nav_menu -> {

                    //  showBottomSheetDialog()


                    val bottomSheetDialogMenuFragment = BottomSheetDialogMenuFragment()
                    // bottomSheetDialogMenuFragment.registerCallBack(this@HomeMainActivity)
                    bottomSheetDialogMenuFragment.show(supportFragmentManager, bottomSheetDialogMenuFragment.tag)


                }
            }
        })



    }

    override fun onResume() {
        super.onResume()
       // showDialog()

        binding.includedShimmerHomeMain.lyShimmerDashboardParent.visibility = View.VISIBLE
        binding.shimmerDashboard.visibility = View.VISIBLE
        binding.includedHomeMain.lyDashboardParent.visibility = View.GONE
        binding.shimmerDashboard.startShimmerAnimation()


        if (loginResponseEntity != null) {

            MasterController(this).geUserConstant(1, this)
            MasterController(this).getMenuMaster(this)

        }

    }


    //region Initialize Entity and DB
    private fun init(){


        db = DBPersistanceController(this)
        loginResponseEntity = db.userData

        prefManager = PrefManager(this)

        viewPager2 =  binding.includedHomeMain.viewPager
        ivProfile = binding.includedHomeMain.ivProfile

        txtDetails = binding.includedHomeMain.txtDetails
        txtEntityName = binding.includedHomeMain.txtEntityName

        llSwitchUser = binding.llSwitchUser
        bottomSheetDialog = BottomSheetDialog(this, R.style.bottomSheetDialogMax)




    }

    private fun setListener(){

        binding.includedHomeMain.tvKnowledge.setOnClickListener(this)
        binding.includedHomeMain.tvSalesMat.setOnClickListener(this)

        binding.includedHomeMain.txtknwyour.setOnClickListener(this)

        binding.includedHomeMain.txtSeeALL.setOnClickListener(this)



    }

    private fun bindData(){


        if (loginResponseEntity != null) {

            FBAID =  loginResponseEntity.fbaId.toString()
            txtDetails.text = "" + loginResponseEntity.fullName
            txtEntityName.text = "V"+ BuildConfig.VERSION_NAME

        }else{
            txtDetails.text = ""
        }

        if (this::userConstantEntity.isInitialized) {
            try {

                Glide.with(this@HomeMainActivity)
                        .load(userConstantEntity.loansendphoto)
                        .placeholder(R.drawable.circle_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .override(64, 64)
                        .transform(CircleTransform(this@HomeMainActivity)) // applying the image transformer
                        .into(ivProfile)
            } catch (e: Exception) {
            }
        } else {
            try {

                Glide.with(this@HomeMainActivity)
                        .load<Any>(R.drawable.finmart_user_icon)
                        .placeholder(R.drawable.circle_placeholder)
                        .transform(CircleTransform(this@HomeMainActivity)) // applying the image transformer
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .override(62, 62)
                        .into(ivProfile)
            } catch (e: Exception) {
            }
        }


        switchUserBinding()
    }

    private fun switchUserBinding() {


        //region Switch user Binding
        val outputMap = loadMap()
        if (outputMap != null && outputMap.size > 0) {

            //region add view for switch user
            val layoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater.inflate(R.layout.layout_switch_user, null)
            val txtUserName = view.findViewById<TextView>(R.id.txtSwitchUserName)
            val btnSwitchLogout = view.findViewById<Button>(R.id.btnSwitchLogout)

            val currentChild = outputMap["Child_name"]
            txtUserName.text = "Logged in with  " + currentChild
            btnSwitchLogout.setOnClickListener {

                val loginRequestEntity = LoginRequestEntity()
                val outputMap = loadMap()
                if (outputMap != null && outputMap.size > 0) {
                    loginRequestEntity.userName = outputMap["Parent_UID"]
                    loginRequestEntity.password = outputMap["Parent_PWD"]
                }
                loginRequestEntity.deviceId = "" + ReadDeviceID(this@HomeMainActivity).androidID
                loginRequestEntity.tokenId = prefManager.token
                loginRequestEntity.isChildLogin = "Y"
                val preferences = getSharedPreferences(Constants.SWITCh_ParentDeatils_FINMART, MODE_PRIVATE)
                val editor = preferences.edit()
                editor.clear()
                editor.commit()

                //  new PrefManager(HomeActivity.this).clearAll();
                DBPersistanceController(this@HomeMainActivity).clearSwitchUser()
                showDialog()
                LoginController(this@HomeMainActivity).login(loginRequestEntity, this)
            }
            llSwitchUser.removeAllViews()
            llSwitchUser.addView(view)

            //endregion

        }





        //endregion
    }


    private  fun checkMarketingPopup(){

       var checkfirstmsg_call = prefManager.checkMsgFirst.toInt()
        if (checkfirstmsg_call == 0) {
            var type = ""
            val bundle = intent.extras
            if (bundle != null) {
                if (bundle.getString("MarkTYPE") != null) {
                    type = bundle.getString("MarkTYPE", "")
                    if (type != "FROM_HOME") {
                        showMarketingPopup()
                    }
                }
            } else {
                prefManager.updateCheckMsgFirst("" + 1)
                showMarketingPopup()
            }
        }
    }

    //endregion



    //region Initialize ViewPager2 and recyclerview

    private fun loadViewPager(listInsur: MutableList<DashboardMultiLangEntity>){



        adapter = SliderDashboardAdapter(this, listInsur, 0, this)
        viewPager2.adapter = adapter


        setupCarousel(listInsur)


        adapterImgSlider = SliderImageAdapter(this, listInsur)
        binding.includedHomeMain.rvImgSlide.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.includedHomeMain.rvImgSlide.setHasFixedSize(true)

        binding.includedHomeMain.rvImgSlide.adapter = adapterImgSlider

       // binding.includedHomeMain.rvImgSlide.isNestedScrollingEnabled = false


    }

    private fun setupCarousel(listInsur: MutableList<DashboardMultiLangEntity>){

        viewPager2.offscreenPageLimit = 3
        viewPager2.clipChildren = false
        viewPager2.clipToPadding = false

        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        viewPager2.setPageTransformer(CarouselTransformer(this))


        sliderHandler = Handler()
        sliderRun = Runnable {

            Log.d("VIEWPAGER", "viewPager Current Item position " + viewPager2.currentItem)
            if (viewPager2.currentItem == listInsur.size - 1 ) {
                viewPager2.setCurrentItem(0, false)


            }else{
                viewPager2.setCurrentItem(viewPager2.currentItem + 1, true)

            }

            //  viewPager2.currentItem = viewPager2.currentItem + 1




        }

        viewPager2.registerOnPageChangeCallback(

                object : ViewPager2.OnPageChangeCallback() {

                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)

                        sliderHandler.removeCallbacks(sliderRun)
                        sliderHandler.postDelayed(sliderRun, 2000)


                    }


                }


        )





    }


    fun stopViewPager()  {


        // Toast.makeText(requireContext(),"Pos"+position ,Toast.LENGTH_SHORT).show()

        //viewPager2.setCurrentItem(position, true)
        sliderHandler.removeCallbacks(sliderRun)


    }
    fun getSliderImagePosition(position: Int)  {


        // Toast.makeText(requireContext(),"Pos"+position ,Toast.LENGTH_SHORT).show()

        viewPager2.setCurrentItem(position, true)


    }

    //endregion



    //  region  Method

     fun addDynamicMenu(response: MenuMasterResponse?) {
        menuMasterResponse = response
        val list = response?.masterData?.menu
//        val menu: Menu = navigationView.getMenu()
//
//        //remove menu item from group if exist
//        run {
//            var i = 1
//            while (i <= list.size && list[i - 1].isActive == 1) {
//                var itemId = list[i - 1].sequence.toInt()
//                itemId = itemId * 100 + 1
//                menu.removeItem(itemId)
//                i++
//            }
//        }

        //add dynamic menu
//        var i = 1
//        while (i <= list.size && list[i - 1].isActive == 1) {
//            var itemId = list[i - 1].sequence.toInt()
//            itemId = itemId * 100 + 1
//            val menuItem = menu.add(R.id.dynamic_menu, itemId, itemId, list[i - 1].menuname)
//            Glide.with(this)
//                    .load(list[i - 1].iconimage)
//                    .into( SimpleTarget<GlideDrawable> {
//                        fun onResourceReady(resource: GlideDrawable, glideAnimation: GlideAnimation<in GlideDrawable>) {
//                            menuItem.icon = resource
//                        }
//                    })
//            i++
//        }
    }



    //endregion

    // region PopUp Dialog
     private fun showMarketingPopup() {

        //region popup dashboard
        if (this::userConstantEntity.isInitialized) {
            if (userConstantEntity.marketinghomeenabled != null && userConstantEntity.marketinghomeenabled == "1") {
                val serverPopUpCount = userConstantEntity.marketinghomemaxcount.toInt()
                var localPopupCount = prefManager.popUpCounter.toInt()
                val serverId = userConstantEntity.marketinghomepopupid.toInt()
                val localId = prefManager.popUpId.toInt()
                if (localId == 0) {
                    prefManager.updatePopUpId("" + serverId)
                }
                if (localId == serverId) {
                    prefManager.updatePopUpId("" + serverId)
                    Log.d("COUNTER", "localId -" + localId + "counter - " + localPopupCount)
                    if (localPopupCount < serverPopUpCount) {
                        localPopupCount = localPopupCount + 1
                        prefManager.updatePopUpCounter("" + localPopupCount)
                        openPopUp(ivProfile, userConstantEntity.marketinghometitle, userConstantEntity.marketinghomedesciption, "OK", true)
                    }
                } else {
                    prefManager.updatePopUpId("" + serverId)
                    prefManager.updatePopUpCounter("0")
                    localPopupCount = prefManager.popUpCounter.toInt()
                    Log.d("COUNTER-", "localId -" + localId + "counter - " + localPopupCount)
                    if (localPopupCount < serverPopUpCount) {
                        localPopupCount = localPopupCount + 1
                        prefManager.updatePopUpCounter("" + localPopupCount)
                        openPopUp(ivProfile, userConstantEntity.marketinghometitle, userConstantEntity.marketinghomedesciption, "OK", true)
                    }
                }
            }
        }

        //endregion
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
        val builder = AlertDialog.Builder(this@HomeMainActivity)
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
        //  alertDialog.getWindow().setLayout(900, 600);

        // for user define height and width..
    }

    override fun infoProductPopUp(dashEntity: DashboardMultiLangEntity) {
        openWebViewPopUp(viewPager2, dashEntity.info, true, "")


    }

    override fun ConfirmnMyUtilitiesAlert() {

        if(loginResponseEntity != null){

            ConfirmnUtilitiesAlert(loginResponseEntity)

        }

    }

    //endregion




    // region Listener of Callback
    override fun onPositiveButtonClick(dialog: Dialog?, view: View?) {
        TODO("Not yet implemented")
    }

    override fun onCancelButtonClick(dialog: Dialog?, view: View?) {
        TODO("Not yet implemented")
    }

    override fun onCancelClick(dialog: Dialog?, view: View?) {
        TODO("Not yet implemented")
    }

    override fun onGrantButtonClick(view: View?) {
        TODO("Not yet implemented")
    }

    override fun onClick(view: View?) {

      when(view!!.id){

          //redirect to knowledge guru
          R.id.tvKnowledge -> {
              startActivity(Intent(this, KnowledgeGuruActivity::class.java))

              MyApplication.getInstance().trackEvent(Constants.KNOWLEDGE_GURU, "Clicked", "Knowledge Guru From Dashboard")

          }

          R.id.tvSalesMat -> {

              //redirect to sales
              startActivity(Intent(this, SalesMaterialActivity::class.java))
              MyApplication.getInstance().trackEvent(Constants.SALES_MATERIAL, "Clicked", "CUSTOMER COMM. From Dashboard")

          }


          R.id.txtknwyour -> {

              if (this::userConstantEntity.isInitialized) {
                  openWebViewPopUp(viewPager2, userConstantEntity.notificationpopupurl, true, "")

              }


          }

          R.id.txtSeeALL -> {
              startActivity(Intent(this, HomeListProductActivity::class.java))

          }

      }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.REQUEST_CODE) {
            if (data != null) {
                val Counter = prefManager.notificationCounter
               // textNotifyItemCount.setText("" + Counter)
                //textNotifyItemCount.setVisibility(View.GONE)
            }
        }
        else if (requestCode == Constants.SWITCH_USER_REQUEST_CODE) {
            if (data != null) {
                //switchUserBinding();
                db = DBPersistanceController(this)
                loginResponseEntity = db.userData
                // init_headers();
            }
        }
    }

    // endregion





    override fun OnSuccess(response: APIResponse?, message: String?) {

        cancelDialog()

        binding.includedShimmerHomeMain.lyShimmerDashboardParent.visibility = View.GONE
        binding.shimmerDashboard.visibility = View.VISIBLE
        binding.includedHomeMain.lyDashboardParent.visibility = View.VISIBLE
        binding.shimmerDashboard.stopShimmerAnimation()
        if (response is UserConstatntResponse) {

            if (response.getStatusNo() == 0) {
                if (response.masterData != null) {
                    //db.updateUserConstatntData(((UserConstatntResponse) response).getMasterData());
                    userConstantEntity = response.masterData
                    bindData()
                    //init_headers()
                    if (prefManager.popUpCounter == "0") {
                        showMarketingPopup()
                    }

                    //Notification Url :-1 November
                    val localNotificationenable = prefManager.notificationsetting.toInt()
                    if (userConstantEntity.notificationpopupurltype.toUpperCase() == "SM") {
                        if (userConstantEntity.notificationpopupurl != "") {
                            if (prefManager.isSeasonal) {
                                openWebViewPopUp(viewPager2, userConstantEntity.notificationpopupurl, true, "")
                                prefManager.isSeasonal = false
                            }
                        }
                    } else if (localNotificationenable == 0) {
                        // prefManager.updatePopUpId("" + serverId);
                        if (userConstantEntity.notificationpopupurl != "") {
                            if (prefManager.isSeasonal) {
                                openWebViewPopUp(viewPager2, userConstantEntity.notificationpopupurl, true, "")
                                prefManager.isSeasonal = false
                            }
                        }
                    }
                }

            }
        } else if (response is MenuMasterResponse) {
            if (response.getStatusNo() == 0) {

                prefManager.storeMenuDashboard(response as MenuMasterResponse?)
                addDynamicMenu(response as MenuMasterResponse?)

                loadViewPager(db.insurProductLangList)

            }
        }else if(response is ProductURLShareResponse){
            if (response.getStatusNo() == 0) {
                if (response.masterData != null) {
                    val shareEntity = response.masterData
                    if (dashboardShareEntity != null) {
                        datashareList(this@HomeMainActivity, dashboardShareEntity?.getTitle()
                                ?: "", shareEntity.msg, shareEntity.url)
                    }
                }
            }
        }else if (response is LoginResponse) {
            if (response.getStatusNo() == 0) {

                // prefManager.setIsUserLogin(true);
                val intent = Intent(this@HomeMainActivity, HomeMainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            } else {
                Toast.makeText(this, "" + response.getMessage(), Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun OnFailure(t: Throwable?) {
        cancelDialog()

        binding.includedShimmerHomeMain.lyShimmerDashboardParent.visibility = View.GONE
        binding.includedHomeMain.lyDashboardParent.visibility = View.VISIBLE
        binding.shimmerDashboard.stopShimmerAnimation()
    }

    override fun onClickLogout() {

        //switch user clear
        val preferences = getSharedPreferences(Constants.SWITCh_ParentDeatils_FINMART, MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.commit()


        dialogLogout(this@HomeMainActivity)
    }

    override fun onSwitchUser() {

        startActivityForResult(Intent(this@HomeMainActivity, SwitchUserActivity::class.java), Constants.SWITCH_USER_REQUEST_CODE)
    }


}