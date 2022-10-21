package com.policyboss.policybosspro.homeMainKotlin.menuRepository

import com.policyboss.policybosspro.R
import magicfinmart.datacomp.com.finmartserviceapi.PrefManager
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.UserConstantEntity
import magicfinmart.datacomp.com.finmartserviceapi.model.MenuChild
import magicfinmart.datacomp.com.finmartserviceapi.model.MenuHeader
import java.util.*

open class DBMenuRepository() {


    companion object {

        //endregion
        fun getMenuMainList(userConstantEntity: UserConstantEntity, prefManager: PrefManager): MutableList<MenuHeader> {


            val menuEntities: MutableList<MenuHeader> = ArrayList()

            menuEntities.add(MenuHeader("MY ACCOUNT", false, R.drawable.user_menu, getMenuMyAccountList(userConstantEntity, prefManager)))

            menuEntities.add(MenuHeader("MY DOCUMENTS", false, R.drawable.document_menu, getMenuMyDocumentList(userConstantEntity, prefManager)))

            menuEntities.add(MenuHeader("MY TRANSACTIONS", false, R.drawable.transaction_menu, getMenuTransactionList(userConstantEntity, prefManager)))
            menuEntities.add(MenuHeader("MY UTILITIES", false, R.drawable.utility_menu, getMenuUtilityList(userConstantEntity, prefManager)))

            menuEntities.add(MenuHeader("MY LEADS", false, R.drawable.leads_menu, getMenuMyLeadsList(userConstantEntity, prefManager)))
            menuEntities.add(MenuHeader("LEGAL", false, R.drawable.legal_menu, getMenuLegalList(userConstantEntity, prefManager)))



            menuEntities.add(MenuHeader("LOG-OUT", false, R.drawable.log_out, getMenuLegalList(userConstantEntity, prefManager)))



            return menuEntities
        }

        fun getMenuMyAccountList(userConstantEntity: UserConstantEntity, prefManager: PrefManager): MutableList<MenuChild> {
            val menuChild: MutableList<MenuChild> = ArrayList()
            menuChild.add(MenuChild("nav_myaccount", "My Profile", R.drawable.my_profile))

            if((userConstantEntity?.enableenrolasposp ?: "0").toInt() == 1 ){

                menuChild.add(MenuChild("nav_pospenrollment", "Enrol as POSP", R.drawable.enrol_as_posp))

            }


            //todo : check key from userconstant to hide add posp
          if((userConstantEntity?.addPospVisible ?: "0").toInt() == 1 && (  prefManager?.fosUser?:"" != "Y")) {

                     menuChild.add(MenuChild("nav_addposp", "Add Sub User", R.drawable.enrol_as_posp))            // 05 changeable


             }


            menuChild.add(MenuChild("nav_raiseTicket", "Raise a Ticket", R.drawable.raise_ticket))
            menuChild.add(MenuChild("nav_changepassword", "Change Password", R.drawable.change_password1))


            return menuChild
        }



        fun getMenuMyDocumentList(userConstantEntity: UserConstantEntity, prefManager: PrefManager): MutableList<MenuChild> {
            val menuChild: MutableList<MenuChild> = ArrayList()
            if(userConstantEntity?.pospletterEnabled?: "0" != "0") {

                menuChild.add(MenuChild("nav_AppointmentLetter", "POSP Appointment Letter", R.drawable.posp_appointment_letter))
            }
            if(userConstantEntity?.pospappformEnabled?: "0" != "0") {

                menuChild.add(MenuChild("nav_Certificate", "POSP Application Form", R.drawable.posp_application_form))

            }


            return menuChild
        }

        fun getMenuTransactionList(userConstantEntity: UserConstantEntity, prefManager: PrefManager): MutableList<MenuChild> {
            val menuChild: MutableList<MenuChild> = ArrayList()


            //todo : check key from userconstant to hide my business
            if((userConstantEntity?.showmyinsurancebusiness?: "0").toInt() > 0){

                menuChild.add(MenuChild("nav_mybusiness_insurance", "My Insurance Business", R.drawable.business_loan_ic))
            }
            if(userConstantEntity?.myTransactionsEnabled?: "0" != "0") {

                menuChild.add(MenuChild("nav_transactionhistory", "My Transactions", R.drawable.my_transaction))

            }

            menuChild.add(MenuChild("nav_MessageCentre", "My Messages", R.drawable.my_message))


            if(userConstantEntity?.policyByCRNEnabled?: "0" != "0"){

                menuChild.add(MenuChild("c", "Get  Policy by CRN", R.drawable.my_transaction))


            }



            return menuChild
        }

        fun getMenuUtilityList(userConstantEntity: UserConstantEntity, prefManager: PrefManager): MutableList<MenuChild> {
            val menuChild: MutableList<MenuChild> = ArrayList()
            menuChild.add(MenuChild("nav_MYUtilities", "Utilities", R.drawable.utilities))
            return menuChild
        }

        fun getMenuMyLeadsList(userConstantEntity: UserConstantEntity, prefManager: PrefManager): MutableList<MenuChild> {
            val menuChild: MutableList<MenuChild> = ArrayList()
            menuChild.add(MenuChild("nav_contact", "Create Lead from Contact", R.drawable.create_lead_from_contact))

            if(userConstantEntity?.generateMotorLeadsEnabled?: "" != "0") {

                menuChild.add(MenuChild("nav_generateLead", "Generate Motor Leads", R.drawable.leads_menu))
            }
              menuChild.add(MenuChild("nav_leaddetail", "Lead Dashboard", R.drawable.lead_dashboard))

            if(userConstantEntity?.smsTemplatesEnabled?: "" != "0") {

                menuChild.add(MenuChild("nav_sendSmsTemplate", "Sms Templates", R.drawable.sms_template))

            }

            return menuChild
        }

        fun getMenuLegalList(userConstantEntity: UserConstantEntity, prefManager: PrefManager): MutableList<MenuChild> {
            val menuChild: MutableList<MenuChild> = ArrayList()
            menuChild.add(MenuChild("nav_demo", "Demo", R.drawable.disclosure1))
            menuChild.add(MenuChild("nav_disclosure", "Disclosure", R.drawable.disclosure1))
            menuChild.add(MenuChild("nav_policy", "Privacy Policy", R.drawable.privacy_policy))

            return menuChild
        }



    }
}