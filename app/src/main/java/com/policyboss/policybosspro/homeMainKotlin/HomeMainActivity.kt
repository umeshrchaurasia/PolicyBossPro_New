package com.policyboss.policybosspro.homeMainKotlin

import android.app.Dialog
import android.content.*
import android.content.pm.LabeledIntent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.pm.PackageInfoCompat
import androidx.core.view.get
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.demo.kotlindemoapp.HomeMain.CarouselViewPager.Adapter.SliderDashboardAdapter
import com.demo.kotlindemoapp.HomeMain.CarouselViewPager.Adapter.SliderImageAdapter
import com.demo.kotlindemoapp.HomeMain.CarouselViewPager.CarouselTransformer
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.policyboss.policybosspro.BaseActivity
import com.policyboss.policybosspro.BaseActivity.PermissionListener
import com.policyboss.policybosspro.BuildConfig
import com.policyboss.policybosspro.MyApplication
import com.policyboss.policybosspro.R
import com.policyboss.policybosspro.databinding.ActivityHomeMainBinding
import com.policyboss.policybosspro.home.adapter.CallingDetailAdapter
import com.policyboss.policybosspro.knowledgeguru.KnowledgeGuruActivity
import com.policyboss.policybosspro.myaccount.MyAccountActivity
import com.policyboss.policybosspro.notification.NotificationActivity
import com.policyboss.policybosspro.salesmaterial.SalesMaterialActivity
import com.policyboss.policybosspro.switchuser.SwitchUserActivity
import com.policyboss.policybosspro.utility.CircleTransform
import com.policyboss.policybosspro.utility.Constants
import com.policyboss.policybosspro.utility.ReadDeviceID
import io.ak1.BubbleTabBar
import magicfinmart.datacomp.com.finmartserviceapi.PrefManager
import magicfinmart.datacomp.com.finmartserviceapi.Utility
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController
import magicfinmart.datacomp.com.finmartserviceapi.finmart.APIResponse
import magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.login.LoginController
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.masters.MasterController
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.register.RegisterController
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.LoginResponseEntity
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.MenuMasterResponse
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.UserCallingEntity
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.UserConstantEntity
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.LoginRequestEntity
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.LoginResponse
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.ProductURLShareResponse
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.UserCallingResponse
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

    lateinit var callingDetailAdapter: CallingDetailAdapter

   // lateinit var sliderHandler: Handler

    var sliderHandler = Handler()
    lateinit var sliderRun : Runnable



    ////////////////////////////////////////////

    lateinit var db: DBPersistanceController
    lateinit var prefManager: PrefManager
    lateinit var loginResponseEntity: LoginResponseEntity
    lateinit var userConstantEntity: UserConstantEntity

    var menuMasterResponse: MenuMasterResponse? = null
    var dashboardShareEntity: DashboardMultiLangEntity? = null

    var isNetworkConnected : Boolean = false


    lateinit var bottomSheetDialog : BottomSheetDialog
    lateinit var ivProfile : ImageView
    lateinit var shareProdDialog: AlertDialog
    lateinit var  callingDetailDialog: AlertDialog

    lateinit var txtDetails : TextView
    lateinit var txtEntityName :TextView

    lateinit var llSwitchUser: LinearLayout

    var BottomType : String = ""

    var FBAID : String = ""
    var versionNAme: String = ""
    lateinit var pinfo: PackageInfo

    lateinit var notificationBadges : View


    //region broadcast receiver
    var mHandleMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action != null) {
//                if (intent.action.equals(Utility.PUSH_BROADCAST_ACTION, ignoreCase = true)) {
//                    val notifyCount = prefManager.notificationCounter
//                    if (notifyCount == 0) {
//                        textNotifyItemCount.setVisibility(View.GONE)
//                    } else {
//                        textNotifyItemCount.setVisibility(View.VISIBLE)
//                        textNotifyItemCount.setText("" + notifyCount.toString())
//                    }
//                }

                    if (intent.action.equals(Utility.USER_PROFILE_ACTION, ignoreCase = true)) {
                    val PROFILE_PATH = intent.getStringExtra("PROFILE_PATH")
                    Glide.with(this@HomeMainActivity)
                            .load(Uri.parse(PROFILE_PATH))
                            .placeholder(R.drawable.finmart_user_icon)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .override(64, 64)
                            .transform(CircleTransform(this@HomeMainActivity)) // applying the image transformer
                            .into(ivProfile)
                }
            }
        }
    }


    //endregion



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        init()

        setListener()
        registerPopUp(this)

        bindData()

        checkMarketingPopup()

        updateBadgeCount(2)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, androidx.lifecycle.Observer { isConnected ->

            if (isConnected) {


                isNetworkConnected = true
                //  Toast.makeText(this,"Connected",Toast.LENGTH_SHORT).show()
                binding.includedHomeMain.lyNotConnected.visibility = View.GONE


                if (loginResponseEntity != null) {

                        loadApi()

                }


            } else {

                isNetworkConnected = false
                binding.includedHomeMain.lyNotConnected.visibility = View.VISIBLE

            }


        })


        // getPackage Info
        try {
            pinfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0)



        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }



       // binding.bubbleTabBar.setSelected(0)
      //  binding.bubbleTabBar.setSelectedWithId(R.id.nav_home, true)

        binding.bubbleTabBar.addBubbleListener({


            when (it) {

                R.id.nav_home -> {

                    // called Api
                    if (isNetworkConnected) {

                        loadApi()
                    }


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


        if (isNetworkConnected) {

            loadApi()
        }


        LocalBroadcastManager.getInstance(this@HomeMainActivity).registerReceiver(mHandleMessageReceiver, IntentFilter(Utility.PUSH_BROADCAST_ACTION))

        LocalBroadcastManager.getInstance(this@HomeMainActivity)
                .registerReceiver(mHandleMessageReceiver, IntentFilter(Utility.USER_PROFILE_ACTION))


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

        binding.includedHomeMain.ivSupport.setOnClickListener(this)



    }

    private fun bindData(){

        binding.includedHomeMain.lyNotConnected.visibility = View.GONE


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
                        .placeholder(R.drawable.profile_photo)
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
                        .load<Any>(R.drawable.profile_photo)
                        .placeholder(R.drawable.profile_photo)
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

    private fun loadApi(){

        // called Api

        binding.shimmerDashboard.visibility = View.VISIBLE
        binding.includedHomeMain.lyDashboardParent.visibility = View.GONE
        binding.shimmerDashboard.startShimmerAnimation()


        if (loginResponseEntity != null) {

            MasterController(this).geUserConstant(1, this)
            MasterController(this).getMenuMaster(this)

        }

    }



    private fun updateBadgeCount(count: Int = 1 ){



        val itemView = binding.bubbleTabBar.getChildAt(2) as? BottomNavigationMenuView


         notificationBadges = LayoutInflater.from(this).
                                    inflate(R.layout.notification_text,itemView,false)


        var notify_badge : TextView = notificationBadges.findViewById(R.id.notify_badge)

        notify_badge.text =  count.toString()


       // binding.bubbleTabBar?.resources(this, R.menu.bottom_navigation_tab_menu)

        binding.bubbleTabBar?.removeViewAt(1)
        binding.bubbleTabBar?.addView(notificationBadges,1)









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



        sliderRun = Runnable {

            Log.d("VIEWPAGER", "viewPager Current Item position " + viewPager2.currentItem)

            if (viewPager2.currentItem == listInsur.size - 1 ) {
                viewPager2.setCurrentItem(0, false)


            }else{
                viewPager2.setCurrentItem(viewPager2.currentItem + 1, true)

            }


        }

        viewPager2.registerOnPageChangeCallback(

                object : ViewPager2.OnPageChangeCallback() {

                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)

                        sliderHandler.removeCallbacks(sliderRun)
                        sliderHandler.postDelayed(sliderRun, 3000)


                    }


                }

        )

    }


    fun stopViewPager()  {

        if (sliderRun != null) {

            sliderHandler.removeCallbacks(sliderRun)

        }



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


    private fun openAppMarketPlace() {
        val appPackageName: String = this@HomeMainActivity.getPackageName() // getPackageName() from Context or Activity object
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (anfe: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
        }
        // new TrackingController(getActivity()).sendData(new TrackingRequestEntity(new TrackingData("Update : User open marketplace  "), "Update"), null);
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

    fun CallingDetailsPopUp(lstCallingDetail: List<UserCallingEntity>) {

        if (this::callingDetailDialog.isInitialized  && callingDetailDialog.isShowing()) {
            return
        }
        val builder = AlertDialog.Builder(this, R.style.CustomDialog)
        val txtHdr: TextView
        val txtMessage: TextView
        val ivCross: ImageView
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.calling_user_detail_dialog, null)
        builder.setView(dialogView)
        callingDetailDialog = builder.create()
        // set the custom dialog components - text, image and button
        txtHdr = dialogView.findViewById(R.id.txtHdr)
        txtMessage = dialogView.findViewById(R.id.txtMessage)
        val rvCalling: RecyclerView = dialogView.findViewById(R.id.rvCalling)
        ivCross = dialogView.findViewById<View>(R.id.ivCross) as ImageView
        rvCalling.layoutManager = LinearLayoutManager(this)
        rvCalling.setHasFixedSize(true)
        rvCalling.isNestedScrollingEnabled = false
        callingDetailAdapter = CallingDetailAdapter(this@HomeMainActivity, lstCallingDetail)
        rvCalling.adapter = callingDetailAdapter
        rvCalling.visibility = View.VISIBLE
        txtMessage.text = resources.getString(R.string.RM_Calling)
        ivCross.setOnClickListener { callingDetailDialog.dismiss() }
        callingDetailDialog.setCancelable(false)
        callingDetailDialog.show()
    }

    fun shareCallingData(userCallingEntity: UserCallingEntity) {
        val intentCalling = Intent(Intent.ACTION_DIAL)
        intentCalling.data = Uri.parse("tel:" + userCallingEntity.mobileNo)
        startActivity(intentCalling)
    }

    fun shareEmailData(userCallingEntity: UserCallingEntity) {
       // shareMailSmsList(this@HomeMainActivity, "", "Dear Sir/Madam,", userCallingEntity.emailId, userCallingEntity.mobileNo)


        var subject = "Help-Desk"
        var toMailID =   userCallingEntity.emailId

        var mobileNo = userCallingEntity.mobileNo
        var message =   "Dear Sir/Madam,"




        try {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND

            shareIntent.type = "text/plain"
            val pm: PackageManager = this.getPackageManager()
            val resInfo = pm.queryIntentActivities(shareIntent, 0)
            val intentList: MutableList<LabeledIntent> = ArrayList()
            for (i in resInfo.indices) {
                // Extract the label, append it, and repackage it in a LabeledIntent
                val ri = resInfo[i]
                val packageName = ri.activityInfo.packageName
                val processName = ri.activityInfo.processName
                val AppName = ri.activityInfo.name
                if (packageName.contains("android.email") || packageName.contains("mms") || packageName.contains("messaging") || packageName.contains("android.gm") || packageName.contains("com.google.android.apps.plus")) {
                    shareIntent.component = ComponentName(packageName, ri.activityInfo.name)
                    if ((packageName.contains("android.email")) || (packageName.contains("android.gm")) ){

                        shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(toMailID))

                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                        shareIntent.putExtra(Intent.EXTRA_TEXT, message)

                        shareIntent.setType("message/rfc822");

                        shareIntent.setPackage(packageName)
                    } else if (packageName.contains("mms")) {
                        shareIntent.type = "vnd.android-dir/mms-sms"
                        shareIntent.data = Uri.parse("sms:$mobileNo")
                        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
                        shareIntent.setPackage(packageName)
                    }  else if (packageName.contains("messaging")) {
                        shareIntent.type = "vnd.android-dir/mms-sms"
                        shareIntent.data = Uri.parse("sms:$mobileNo")
                        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
                        shareIntent.setPackage(packageName)
                    }
                    intentList.add(LabeledIntent(shareIntent, packageName, ri.loadLabel(pm), ri.icon))
                }
            }
            if (intentList.size > 1) {
                intentList.removeAt(intentList.size - 1)
            }
            val openInChooser = Intent.createChooser(shareIntent, "Share Via")
            // convert intentList to array
            val extraIntents = intentList.toTypedArray()
            openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents)
            startActivity(openInChooser)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
//
//        var emailIntent  =  Intent(Intent.ACTION_SEND)
//
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(toMailID))
//
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
//        emailIntent.putExtra(Intent.EXTRA_TEXT, message)
//
//        emailIntent.setType("message/rfc822");

  //      startActivity(Intent.createChooser(emailIntent, "Share  Email"));


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
        dialog!!.cancel()
        openAppMarketPlace()
    }

    override fun onCancelButtonClick(dialog: Dialog?, view: View?) {
        dialog!!.cancel()
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

          R.id.ivSupport -> {

              if (userConstantEntity.mangMobile != null) {
                  if (userConstantEntity.managName != null) {
                      // ConfirmAlert("Manager Support", getResources().getString(R.string.RM_Calling) + " " + userConstantEntity.getManagName());
                      if (this::callingDetailDialog.isInitialized && callingDetailDialog.isShowing()) {
                          return
                      } else {
                          showDialog()
                          RegisterController(this).getUserCallingDetail(loginResponseEntity.fbaId.toString(), this)
                      }
                  }
              }


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


        binding.shimmerDashboard.visibility = View.GONE
        binding.includedHomeMain.lyDashboardParent.visibility = View.VISIBLE
        binding.shimmerDashboard.stopShimmerAnimation()
        if (response is UserConstatntResponse) {

            if (response.getStatusNo() == 0) {
                if (response.masterData != null) {
                    //db.updateUserConstatntData(((UserConstatntResponse) response).getMasterData());
                    userConstantEntity = response.masterData
                    bindData()
                    //init_headers()
                    val VersionCode = PackageInfoCompat.getLongVersionCode(pinfo)

                    userConstantEntity?.androidproversion?.let {


                         if(pinfo != null &&  VersionCode.toInt() < it) {

                             openPopUp(viewPager2, "UPDATE", "New version available on play store!!!! Please update.", "OK", false)
                         }
                     }


                     if (prefManager.popUpCounter == "0") {
                        showMarketingPopup()

                    }

                    //region Not IN Used
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
                    //endregion
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
        }else  if(response is UserCallingResponse){

            if(response.getStatusNo() == 0){

                CallingDetailsPopUp(response.masterData)
            }
        }

        else if (response is LoginResponse) {
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


        binding.shimmerDashboard.visibility = View.GONE
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