package magicfinmart.datacomp.com.finmartserviceapi.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.realm.Case;
import io.realm.Realm;
import magicfinmart.datacomp.com.finmartserviceapi.PrefManager;
import magicfinmart.datacomp.com.finmartserviceapi.R;
import magicfinmart.datacomp.com.finmartserviceapi.dynamic_urls.model.Home_bankdetailEntity;
import magicfinmart.datacomp.com.finmartserviceapi.express_loan.model.KotakPLEmployerNameEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.AccountDtlEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.BikeMasterEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.CarMasterEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.CityMasterEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.ConstantEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.ContactlistEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.DashBoardItemEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.DocsEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.GeneralinsuranceEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.HealthinsuranceEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.InsuranceSubtypeEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.LifeinsuranceEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.LoginResponseEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.MultiLangEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.RblCityEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.SalesProductEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.UserConstantEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.UserHideEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.ZohoCategoryEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.ZohoClassificationEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.ZohoSubcategoryEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.ZohoTicketCategoryEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.InsurerResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.UserHideResponse;
import magicfinmart.datacomp.com.finmartserviceapi.healthcheckup.model.HealthPackDEntity;
import magicfinmart.datacomp.com.finmartserviceapi.healthcheckup.model.HealthPackDetailsDBean;
import magicfinmart.datacomp.com.finmartserviceapi.model.DashboardEntity;
import magicfinmart.datacomp.com.finmartserviceapi.model.DashboardMultiLangEntity;
import magicfinmart.datacomp.com.finmartserviceapi.model.HealthSumAssured;
import magicfinmart.datacomp.com.finmartserviceapi.model.MenuChild;
import magicfinmart.datacomp.com.finmartserviceapi.model.MenuHeader;
import magicfinmart.datacomp.com.finmartserviceapi.model.PropertyInfoEntity;
import magicfinmart.datacomp.com.finmartserviceapi.model.TermSelectionEntity;


public class DBPersistanceController {

    public static final String EXTERNAL_LPG = "External LPG";
    public static final String EXTERNAL_CNG = "External CNG";
    Map<String, Integer> hashmapKotakPLCity;
    final ArrayList<String> stringArrayList = new ArrayList<>();
    final int a = 10;
    //HashMap<String, String> hashMapAddons;
    Map<String, Integer> hashMapInsurence, hashmapPremTerm;
    HashMap<String, String> hashMapAddons, hdfcpersonalloanbankbranch;
    HashMap<Integer, Integer> hasMapCarInsuranceImage;
    HashMap<String, Integer> hashmapCity;
    HashMap<String, Integer> hashmapLoanCity;
    Context mContext;
    Realm realm;
    PrefManager prefManager;
    List<DashBoardItemEntity> dashBoardItemEntities;
    SharedPreferences.Editor editor;

    public static final String INSURER_LIST = "insurer_list";
    public static final String FOS_DETAIL = "fos_detail";


    public DBPersistanceController(Context mContext) {

        if (mContext != null) {
            this.mContext = mContext;
            realm = Realm.getDefaultInstance();
            prefManager = new PrefManager(mContext);
            editor = prefManager.editor;
        }

    }


    //region FOS Detail

    public boolean storeFOSDetail(UserHideResponse response) {

        UserHideEntity hideEntity = new UserHideEntity();
        hideEntity.setHidesubuser(response.getMasterData().getHidesubuser());
        hideEntity.setHideloan("Y");

        UserHideResponse hideResponse = new UserHideResponse();
        hideResponse.setMasterData(hideEntity);

        return editor.putString(FOS_DETAIL, new Gson().toJson(hideResponse)).commit();
    }

    private UserHideResponse getFOSInfo() {
        Gson gson = new Gson();
        if (gson.fromJson(prefManager.pref.getString(FOS_DETAIL, ""), UserHideResponse.class) != null)
            return gson.fromJson(prefManager.pref.getString(FOS_DETAIL, ""), UserHideResponse.class);

        return null;
    }

    public boolean isHideLoan() {

        if (getFOSInfo() != null) {
            UserHideResponse response = getFOSInfo();
            if (response.getMasterData().getHideloan().toLowerCase().equals("y"))
                return true;
        }

        return false;
    }

    //region Rbl City Master

    public List<String> getRblCity() {
        List<String> listCity = new ArrayList<>();
        // List<ModelMasterEntity> listModelMaster = dbController.getMasterModel();
        List<RblCityEntity> list = realm.where(RblCityEntity.class).findAll();

        for (int i = 0; i < list.size(); i++) {
            RblCityEntity entity = list.get(i);
            String cityName = entity.getCityName();
            listCity.add(cityName);
        }

        return listCity;
    }


    public int getRblCityCode(String RblCityName) {
        RblCityEntity entity = realm.where(RblCityEntity.class).equalTo("CityName", RblCityName).findFirst();
        if (entity != null) {
            return entity.getCityCode();
        }
        return 0;
    }
    //endregion


    //region RTO

    public ArrayList<String> getRTOListNames() {
        List<CityMasterEntity> list_Make = realm.where(CityMasterEntity.class).findAll();
        ArrayList listCity = new ArrayList();
        for (int i = 0; i < list_Make.size(); i++) {
            //listCity.add(list_Make.get(i).getRTO_CodeDiscription());
            //listCity.add(list_Make.get(i).getRTO_City());
            listCity.add(list_Make.get(i).getVehicleCity_RTOCode() + " - " + list_Make.get(i).getRTO_City());
        }
        return listCity;
    }


    public String getCityID(String cityName) {

        CityMasterEntity entity = realm.where(CityMasterEntity.class)
                .equalTo("RTO_City", cityName).findFirst();
        if (entity != null)
            return entity.getVehicleCity_Id();
        else
            return "";

    }

    public String getCityID(String cityName, String VehicleCity_RTOCode) {

        CityMasterEntity entity = realm.where(CityMasterEntity.class)
                .equalTo("VehicleCity_RTOCode", VehicleCity_RTOCode)
                .equalTo("RTO_City", cityName).findFirst();
        if (entity != null)
            return entity.getVehicleCity_Id();
        else
            return "";

    }

    public String getRTOName(String VehicleCity_Id) {

        CityMasterEntity entity = realm.where(CityMasterEntity.class)
                .equalTo("VehicleCity_Id", VehicleCity_Id).findFirst();

        if (entity != null)
            return entity.getRTO_City();
        else
            return "";

    }

    public String getRTOCityName(String VehicleCity_Id) {

        CityMasterEntity entity = realm.where(CityMasterEntity.class)
                .equalTo("VehicleCity_Id", VehicleCity_Id).findFirst();

        if (entity != null)
            return entity.getVehicleCity_RTOCode() + " - " + entity.getRTO_City();
        else
            return "";

    }

    public String getBikeRTOName(String VehicleCity_Id) {

        CityMasterEntity entity = realm.where(CityMasterEntity.class)
                .equalTo("VehicleCity_Id", VehicleCity_Id).findFirst();

        if (entity != null)
            return entity.getRTO_City();
        else
            return "";

    }

    public CityMasterEntity getVehicleCity_Id(String RTO_City) {

        CityMasterEntity entity = realm.where(CityMasterEntity.class)
                .equalTo("RTO_City", RTO_City).findFirst();

        if (entity != null)
            return entity;
        else
            return null;

    }
    //endregion

    //region master car

    public List<String> getCarMakeModel() {
        List<String> listCarModel = new ArrayList<>();
        // List<ModelMasterEntity> listModelMaster = dbController.getMasterModel();
        List<CarMasterEntity> list = realm.where(CarMasterEntity.class).distinct("Model_ID");

        for (int i = 0; i < list.size(); i++) {
            CarMasterEntity entity = list.get(i);
            String carModel = entity.getMake_Name() + " , " + entity.getModel_Name();
            listCarModel.add(carModel);
        }

        return listCarModel;
    }

    public List<String> getMake() {
        List<String> listCarMake = new ArrayList<>();
        List<CarMasterEntity> list = realm.where(CarMasterEntity.class).distinct("Make_Name");
        for (int i = 0; i < list.size(); i++) {
            CarMasterEntity entity = list.get(i);
            String carModel = entity.getMake_Name();
            listCarMake.add(carModel);
        }

        return listCarMake;
    }

    public List<String> getModel(String makeName) {

        List<String> listCarModel = new ArrayList<>();
        List<CarMasterEntity> list = realm.where(CarMasterEntity.class).equalTo("Make_Name", makeName.trim())
                .distinctValues("Model_Name")
                .findAll();
        for (int i = 0; i < list.size(); i++) {
            CarMasterEntity entity = list.get(i);
            String carModel = entity.getModel_Name();
            listCarModel.add(carModel);
        }
        return listCarModel;
    }

    public String getModelID(String makeName, String modelName) {
        CarMasterEntity entity = realm.where(CarMasterEntity.class)
                .equalTo("Make_Name", makeName.trim())
                .equalTo("Model_Name", modelName.trim())
                .findFirst();
        if (entity != null)
            return entity.getModel_ID();
        else
            return "";
    }

    public List<String> getVariant(String make, String model, String fuelname) {

        List<String> listCarVariant = new ArrayList<>();
        List<CarMasterEntity> list = new ArrayList<>();
        listCarVariant.add("Variant");
        String Fuel_ID = "";
        if (fuelname.equals(EXTERNAL_LPG) || fuelname.equals(EXTERNAL_CNG)) {
            CarMasterEntity carMasterEntity = realm.where(CarMasterEntity.class)
                    .equalTo("Make_Name", make.trim())
                    .equalTo("Model_Name", model.trim())
                    .equalTo("Fuel_Name", "Petrol", Case.INSENSITIVE).findFirst();
            if (carMasterEntity != null)
                Fuel_ID = carMasterEntity.getFuel_ID();
        } else {
            CarMasterEntity carMasterEntity = realm.where(CarMasterEntity.class)
                    .equalTo("Make_Name", make.trim())
                    .equalTo("Model_Name", model.trim())
                    .equalTo("Fuel_Name", fuelname.trim()).findFirst();
            if (carMasterEntity != null)
                Fuel_ID = carMasterEntity.getFuel_ID();
        }

        list = realm.where(CarMasterEntity.class)
                .equalTo("Make_Name", make.trim())
                .equalTo("Model_Name", model.trim())
                .equalTo("Fuel_ID", Fuel_ID.trim())
                .distinct("Variant_ID");

        /*if (fuelname.toLowerCase().equals("petrol") || fuelname.toLowerCase().equals("diesel") || fuelname.toLowerCase().equals("cng") || fuelname.toLowerCase().equals("lpg")) {

            list = realm.where(CarMasterEntity.class)
                    .equalTo("Make_Name", make.trim())
                    .equalTo("Model_Name", model.trim())
                    .equalTo("Fuel_Name", fuelname.trim())
                    .distinct("Variant_ID");

        } else {
            list = realm.where(CarMasterEntity.class)
                    .equalTo("Make_Name", make.trim())
                    .equalTo("Model_Name", model.trim())
                    .equalTo("Fuel_Name", "Petrol", Case.INSENSITIVE)
                    .distinct("Variant_ID");
        }*/

        for (int i = 0; i < list.size(); i++) {
            CarMasterEntity entity = list.get(i);
            String variant = entity.getVariant_Name() + " (" + entity.getCubic_Capacity() + "cc)";
            listCarVariant.add(variant);
        }
        return listCarVariant;

    }

    public List<String> getVariantbyModelID(String modelID) {

        List<String> listCarVariant = new ArrayList<>();
        listCarVariant.add("Variant");
        List<CarMasterEntity> list = realm.where(CarMasterEntity.class)
                .equalTo("Model_ID", modelID)
                .distinct("Variant_ID");

        for (int i = 0; i < list.size(); i++) {
            CarMasterEntity entity = list.get(i);
            String variant = entity.getVariant_Name() + " (" + entity.getCubic_Capacity() + "cc)";
            listCarVariant.add(variant);
        }

        return listCarVariant;

    }

    public String getVarientCC(String make, String model, String varientName) {
        CarMasterEntity entity = realm.where(CarMasterEntity.class).equalTo("Make_Name", make.trim())
                .equalTo("Model_Name", model.trim())
                .equalTo("Variant_Name", varientName.trim()).findFirst();
        if (entity != null)
            return entity.getCubic_Capacity() + "CC";
        else
            return "";
    }

    public String getVariantID(String variantName, String modelName, String makeName) {
        CarMasterEntity entity = realm.where(CarMasterEntity.class)
                .equalTo("Model_Name", modelName.trim())
                .equalTo("Variant_Name", variantName.trim())
                .equalTo("Make_Name", makeName.trim()).findFirst();

        if (entity != null)
            return entity.getVariant_ID();
        else
            return "";
    }

    public CarMasterEntity getVarientDetails(String varientId) {
        CarMasterEntity entity = realm.where(CarMasterEntity.class)
                .equalTo("Variant_ID", varientId).findFirst();

        if (entity != null)
            return entity;
        else
            return null;
    }


    public int getCarIcon(int InsurerID) {
        return 0;
    }

    //endregion

    //region fuel

    public List<String> getFuelTypeByModelId(String modelID) {
        List<String> fuelType = new ArrayList<>();
        fuelType.add("Fuel Type");
        List<CarMasterEntity> list = realm.where(CarMasterEntity.class)
                .equalTo("Model_ID", modelID)
                .distinct("Fuel_Name");

        for (int i = 0; i < list.size(); i++) {
            CarMasterEntity entity = list.get(i);
            String fuelName = "" + entity.getFuel_Name();
            fuelType.add(fuelName);
        }

        boolean isAddExternal = false;
        for (int i = 0; i < fuelType.size(); i++) {
            if (fuelType.get(i).toLowerCase().equals("petrol")) {
                isAddExternal = true;
                break;
            }
        }

        if (isAddExternal) {
            fuelType.add(EXTERNAL_LPG);
            fuelType.add(EXTERNAL_CNG);
        }

        return fuelType;
    }


    //endregion

    //region master Bike

    public List<String> getBikeMakeModel() {
        List<String> listCarModel = new ArrayList<>();
        // List<ModelMasterEntity> listModelMaster = dbController.getMasterModel();
        List<BikeMasterEntity> list = realm.where(BikeMasterEntity.class).distinct("Model_ID");

        for (int i = 0; i < list.size(); i++) {
            BikeMasterEntity entity = list.get(i);
            String carModel = entity.getMake_Name() + " , " + entity.getModel_Name();
            listCarModel.add(carModel);
        }

        return listCarModel;
    }

    public String getBikeVarientCC(String make, String model, String varientName) {
        BikeMasterEntity entity = realm.where(BikeMasterEntity.class)
                .equalTo("Make_Name", make.trim())
                .equalTo("Model_Name", model.trim())
                .equalTo("Variant_Name", varientName.trim()).findFirst();
        if (entity != null)
            return entity.getCubic_Capacity() + "CC";
        else
            return "";
    }

    public String getBikeVarient(String varientName, String model, String make) {
        BikeMasterEntity entity = realm.where(BikeMasterEntity.class)
                .equalTo("Make_Name", make.trim())
                .equalTo("Model_Name", model.trim())
                .equalTo("Variant_Name", varientName.trim()).findFirst();
        if (entity != null)
            return entity.getVariant_ID();
        else
            return "";
    }

    public BikeMasterEntity getBikeVarientDetails(String varientId) {
        BikeMasterEntity entity = realm.where(BikeMasterEntity.class)
                .equalTo("Variant_ID", varientId).findFirst();

        if (entity != null)
            return entity;
        else
            return null;
    }

    public String getBikeModelID(String makeName, String modelName) {
        BikeMasterEntity entity = realm.where(BikeMasterEntity.class)
                .equalTo("Make_Name", makeName.trim())
                .equalTo("Model_Name", modelName.trim())
                .findFirst();
        if (entity != null)
            return entity.getModel_ID();
        else
            return "";
    }

    public List<String> getBikeVariantbyModelID(String modelID, String Make_Name) {

        List<String> listCarVariant = new ArrayList<>();
        listCarVariant.add("Variant");
        List<BikeMasterEntity> list = realm.where(BikeMasterEntity.class)
                .equalTo("Model_ID", modelID)
                .equalTo("Make_Name", Make_Name.trim())
                .distinct("Variant_ID");

        for (int i = 0; i < list.size(); i++) {
            BikeMasterEntity entity = list.get(i);
            String variant = entity.getVariant_Name() + " (" + entity.getCubic_Capacity() + "cc)";
            listCarVariant.add(variant);
        }

        return listCarVariant;

    }

    //endregion

    //region master insurance

    public ArrayList<String> getHealthListNames() {
        List<HealthinsuranceEntity> list_Make = realm.where(HealthinsuranceEntity.class).findAll();
        ArrayList listCity = new ArrayList();
        for (int i = 0; i < list_Make.size(); i++) {
            listCity.add(list_Make.get(i).getInsuShorName());
        }
        return listCity;
    }

    public List<HealthinsuranceEntity> getHealthList() {
        return realm.where(HealthinsuranceEntity.class).findAll();
    }

    public String getHealthListId(List<String> strings) {
        String text = "";
        for (String s : strings) {
            HealthinsuranceEntity entity = realm.where(HealthinsuranceEntity.class).equalTo("InsuShorName", s.trim()).findFirst();
            if (text.isEmpty()) {
                text = text + entity.getInsuID();
            } else {
                text = text + "," + entity.getInsuID();
            }
        }
        return text;
    }


    public ArrayList<String> getGeneralListNames() {
        List<GeneralinsuranceEntity> list_Make = realm.where(GeneralinsuranceEntity.class).findAll();
        ArrayList listCity = new ArrayList();
        for (int i = 0; i < list_Make.size(); i++) {
            listCity.add(list_Make.get(i).getInsuShorName());
        }
        return listCity;
    }

    public List<GeneralinsuranceEntity> getGeneralList() {
        return realm.where(GeneralinsuranceEntity.class).findAll();
    }

    public String getGeneralListId(List<String> strings) {
        String text = "";
        for (String s : strings) {
            GeneralinsuranceEntity entity = realm.where(GeneralinsuranceEntity.class).equalTo("InsuShorName", s.trim()).findFirst();
            if (text.isEmpty()) {
                text = text + entity.getInsuID();
            } else {
                text = text + "," + entity.getInsuID();
            }
        }
        return text;
    }


    public ArrayList<String> getLifeListNames() {
        List<LifeinsuranceEntity> list_Make = realm.where(LifeinsuranceEntity.class).findAll();
        ArrayList listCity = new ArrayList();
        for (int i = 0; i < list_Make.size(); i++) {
            listCity.add(list_Make.get(i).getInsuShorName());
        }
        return listCity;
    }

    public String getlifeListId(List<String> strings) {
        String text = "";
        for (String s : strings) {
            LifeinsuranceEntity entity = realm.where(LifeinsuranceEntity.class).equalTo("InsuShorName", s.trim()).findFirst();
            if (text.isEmpty()) {
                text = text + entity.getInsuID();
            } else {
                text = text + "," + entity.getInsuID();
            }
        }
        return text;
    }

    //endregion

    //region term selection
    public List<TermSelectionEntity> getTermCompanyList() {

        List<TermSelectionEntity> term = new ArrayList<TermSelectionEntity>();
        term.add(new TermSelectionEntity("COMPARE TERM INSURANCE", 0, ""));
        // term.add(new TermSelectionEntity("EDELWEISS TOKIO LIFE INSURANCE", 43, ""));
        term.add(new TermSelectionEntity("HDFC LIFE INSURANCE", 28, ""));
        term.add(new TermSelectionEntity("ICICI PRUDENTIAL LIFE INSURANCE", 39, ""));

        //term.add(new TermSelectionEntity("TATA AIA LIFE INSURANE", 1, ""));

        return term;
    }

    public List<TermSelectionEntity> getUltraLakshaList() {

        List<TermSelectionEntity> term = new ArrayList<TermSelectionEntity>();
        term.add(new TermSelectionEntity("ULTRA LAKSHYA COMBO", 0, ""));
        return term;
    }


    //endregion

    //region Dashboard list - Not in Used

    public List<DashboardEntity> getInsurProductList() {
        List<DashboardEntity> dashboardEntities = new ArrayList<DashboardEntity>();

        //region Now Insurance Dashboard Dynamically added from server

        if (new DBPersistanceController(mContext).getUserConstantsData() != null &&
                new DBPersistanceController(mContext).getUserConstantsData().getUltralakshyaenabled() != null
                && new DBPersistanceController(mContext).getUserConstantsData().getUltralakshyaenabled().equalsIgnoreCase("1")) {
            dashboardEntities.add(new DashboardEntity("INSURANCE", 49, "FINMART EXCLUSIVES", "Unique innovative solutions to help you grow your business rapidly.", R.drawable.finmart_exclusive));

        }

        dashboardEntities.add(new DashboardEntity("INSURANCE", 1, "PRIVATE CAR", "Best quotes for Private Car Insurance of your customers with instant policy.", R.drawable.private_car));
        dashboardEntities.add(new DashboardEntity("INSURANCE", 10, "TWO WHEELER", "Best quotes for Two Wheeler Insurance of your customers with instant policy.", R.drawable.two_wheeler));
        dashboardEntities.add(new DashboardEntity("INSURANCE", 12, "COMMERCIAL VEHICLE", "Best quotes for CV Insurance of your customers with instant policy.", R.drawable.commercial_vehicle));

        dashboardEntities.add(new DashboardEntity("INSURANCE", 3, "HEALTH INSURANCE", "Get quotes, compare benefits and buy online from top Health Insurance companies.", R.drawable.health_insurance));
        dashboardEntities.add(new DashboardEntity("INSURANCE", 18, "TERM INSURANCE", "Get quotes, compare benefits and buy online from top Life Insurance companies.", R.drawable.life_insurance));
        dashboardEntities.add(new DashboardEntity("INSURANCE", 16, "REQUEST OFFLINE QUOTES", "Get offline quotes.", R.drawable.offlineportal));

        //endregion

        if (prefManager.getMenuDashBoard() != null) {
            dashBoardItemEntities = prefManager.getMenuDashBoard().getMasterData().getDashboard();
            if (dashboardEntities != null && dashboardEntities.size() > 0) {
                for (DashBoardItemEntity dashBoardItemEntity : dashBoardItemEntities) {
                    if (dashBoardItemEntity.getDashboard_type() == 1 && dashBoardItemEntity.getIsActive() == 1) {
                        DashboardEntity dashboardEntity = new DashboardEntity("INSURANCE", Integer.parseInt(dashBoardItemEntity.getSequence()), "" + dashBoardItemEntity.getMenuname(), "" + dashBoardItemEntity.getDescription(), -1);
                        dashboardEntity.setServerIcon(dashBoardItemEntity.getIconimage());
                        dashboardEntity.setLink(dashBoardItemEntity.getLink());
                        dashboardEntities.add(dashboardEntity);
                    }
                }
            }
        }

        return dashboardEntities;
    }

    public List<DashboardEntity> getUltraLaksh() {
        List<DashboardEntity> dashboardEntities = new ArrayList<DashboardEntity>();


        dashboardEntities.add(new DashboardEntity("ULTRALAKSHA", 40, "FINMART EXCLUSIVE", "Contain required", R.drawable.finmart_logo));


        if (prefManager.getMenuDashBoard() != null) {
            dashBoardItemEntities = prefManager.getMenuDashBoard().getMasterData().getDashboard();
            if (dashboardEntities != null && dashboardEntities.size() > 0) {
                for (DashBoardItemEntity dashBoardItemEntity : dashBoardItemEntities) {
                    if (dashBoardItemEntity.getDashboard_type() == 1 && dashBoardItemEntity.getIsActive() == 1) {
                        DashboardEntity dashboardEntity = new DashboardEntity("INSURANCE", Integer.parseInt(dashBoardItemEntity.getSequence()), "" + dashBoardItemEntity.getMenuname(), "" + dashBoardItemEntity.getDescription(), -1);
                        dashboardEntity.setServerIcon(dashBoardItemEntity.getIconimage());
                        dashboardEntity.setLink(dashBoardItemEntity.getLink());
                        dashboardEntities.add(dashboardEntity);
                    }
                }
            }
        }

        return dashboardEntities;
    }

    public List<DashboardEntity> getLoanProductList() {
        List<DashboardEntity> dashboardEntities = new ArrayList<DashboardEntity>();
//        dashboardEntities.add(new DashboardEntity("LOANS", 4, "HOME LOAN", "Home loan at best interest rates from over 20+ banks & NBFCs.", R.drawable.home_loan));
//        dashboardEntities.add(new DashboardEntity("LOANS", 5, "PERSONAL LOAN", "Provide Instant approval for your customers at attractive interest rates.", R.drawable.personal_loan));
//        dashboardEntities.add(new DashboardEntity("LOANS", 6, "LOAN AGAINST PROPERTY", "Maximum loan amount at competitive interest rate against the property.", R.drawable.loan_against_property));
//        dashboardEntities.add(new DashboardEntity("LOANS", 7, "CREDIT CARD", "Get instant Credit card approvals with amazing offers & deals.", R.drawable.credit_card));
//        dashboardEntities.add(new DashboardEntity("LOANS", 8, "BUSINESS LOAN", "Maximum loan amount at competitive interest rate .", R.drawable.balance_transfer));

        dashboardEntities.add(new DashboardEntity("LOANS", 4, "CREDIT CARD", "Get instant Credit card approvals with amazing offers & deals.", R.drawable.credit_card));

        dashboardEntities.add(new DashboardEntity("LOANS", 5, "PERSONAL LOAN", "Provide Instant approval for your customers at attractive interest rates.", R.drawable.personal_loan));
        dashboardEntities.add(new DashboardEntity("LOANS", 6, "BUSINESS LOAN", "Maximum loan amount at competitive interest rate .", R.drawable.balance_transfer));
        dashboardEntities.add(new DashboardEntity("LOANS", 7, "HOME LOAN", "Home loan at best interest rates from over 20+ banks & NBFCs.", R.drawable.home_loan));

        dashboardEntities.add(new DashboardEntity("LOANS", 8, "LOAN AGAINST PROPERTY", "Maximum loan amount at competitive interest rate against the property.", R.drawable.loan_against_property));

        if (prefManager.getMenuDashBoard() != null) {
            dashBoardItemEntities = prefManager.getMenuDashBoard().getMasterData().getDashboard();
            if (dashboardEntities != null && dashboardEntities.size() > 0) {
                for (DashBoardItemEntity dashBoardItemEntity : dashBoardItemEntities) {
                    if (dashBoardItemEntity.getDashboard_type() == 2 && dashBoardItemEntity.getIsActive() == 1) {
                        DashboardEntity dashboardEntity = new DashboardEntity("LOANS", Integer.parseInt(dashBoardItemEntity.getSequence()), "" + dashBoardItemEntity.getMenuname(), "" + dashBoardItemEntity.getDescription(), -1);
                        dashboardEntity.setServerIcon(dashBoardItemEntity.getIconimage());
                        dashboardEntity.setLink(dashBoardItemEntity.getLink());
                        dashboardEntities.add(dashboardEntity);
                    }
                }
            }
        }

        return dashboardEntities;
    }

    public List<DashboardEntity> getMoreProductList() {
        List<DashboardEntity> dashboardEntities = new ArrayList<DashboardEntity>();

        if (new DBPersistanceController(mContext).getUserConstantsData() != null &&
                new DBPersistanceController(mContext).getUserConstantsData().getEnablencd() != null
                && new DBPersistanceController(mContext).getUserConstantsData().getEnablencd().equalsIgnoreCase("1")) {
            dashboardEntities.add(new DashboardEntity("MORE SERVICES", 15,
                    "OTHER INVESTMENT PRODUCTS", " NCDs (Secured/unsecured Debentures)",
                    R.drawable.investment_icon));
        }
        // dashboardEntities.add(new DashboardEntity("MORE SERVICES", 2, "FIN-PEACE", "A must for all your customers. A unique BEYOND LIFE services for your customer's peace of mind", R.drawable.fin_peace));
        // dashboardEntities.add(new DashboardEntity("MORE SERVICES", 11, "HEALTH CHECK UP PLANS", "Offer a wide array of health check up plans from reputed diagnostics labs at discounted prices and free home collection", R.drawable.health_checkup_plan));

        if (prefManager.getMenuDashBoard() != null) {
            dashBoardItemEntities = prefManager.getMenuDashBoard().getMasterData().getDashboard();
            if (dashboardEntities != null) {
                for (DashBoardItemEntity dashBoardItemEntity : dashBoardItemEntities) {
                    if (dashBoardItemEntity.getDashboard_type() == 3 && dashBoardItemEntity.getIsActive() == 1) {
                        DashboardEntity dashboardEntity = new DashboardEntity("MORE SERVICES", Integer.parseInt(dashBoardItemEntity.getSequence()), "" + dashBoardItemEntity.getMenuname(), "" + dashBoardItemEntity.getDescription(), -1);
                        dashboardEntity.setServerIcon(dashBoardItemEntity.getIconimage());
                        dashboardEntity.setLink(dashBoardItemEntity.getLink());
                        dashboardEntities.add(dashboardEntity);
                    }
                }
            }
        }

        return dashboardEntities;
    }

    //endregion




    //region Dashboard MultilangEntity list

    public List<DashboardMultiLangEntity> getInsurProductLangList() {
        List<DashboardMultiLangEntity> dashboardEntities = new ArrayList<DashboardMultiLangEntity>();

        //region previous Logic
        //  DBPersistanceController db = new DBPersistanceController(mContext);


//        if (db.getUserConstantsData() != null &&
//                db.getUserConstantsData().getUltralakshyaenabled() != null
//                && db.getUserConstantsData().getUltralakshyaenabled().equalsIgnoreCase("1")) {
//            dashboardEntities.add(new DashboardMultiLangEntity("INSURANCE", 17, "FINMART EXCLUSIVES", "Unique innovative solutions to help you grow your business rapidly.", R.drawable.finmart_exclusive, "FinmartExclutitle", "FinmartExcludesc"));
//
//        }

//        dashboardEntities.add(new DashboardMultiLangEntity("INSURANCE", 1, "PRIVATE CAR", "Best quotes for Private Car Insurance of your customers with instant policy.", R.drawable.private_car, "PrivateCartitle", "PrivateCardesc"));
//        dashboardEntities.add(new DashboardMultiLangEntity("INSURANCE", 10, "TWO WHEELER", "Best quotes for Two Wheeler Insurance of your customers with instant policy.", R.drawable.two_wheeler, "TwoWheelertitle", "TwoWheelerdesc"));
//        dashboardEntities.add(new DashboardMultiLangEntity("INSURANCE", 12, "COMMERCIAL VEHICLE", "Best quotes for CV Insurance of your customers with instant policy.", R.drawable.commercial_vehicle, "CommVehtitle", "CommVehdesc"));
//
//        dashboardEntities.add(new DashboardMultiLangEntity("INSURANCE", 3, "HEALTH INSURANCE", "Get quotes, compare benefits and buy online from top Health Insurance companies.", R.drawable.health_insurance, "HealthInsTitle", "HealthInsdesc"));
//
//        if (db.getUserConstantsData() != null
//                && db.getUserConstantsData().getInvestmentEnabled().equalsIgnoreCase("1")) {
//            dashboardEntities.add(new DashboardMultiLangEntity("INSURANCE", 5, "INVESTMENT PLANS", "Get investment options offering returns as per end needs, financial security, tax benefits & more.", R.drawable.invest, "InvestInsTitle", "InvestInsdesc"));
//        }
//
//        dashboardEntities.add(new DashboardMultiLangEntity("INSURANCE", 18, "TERM INSURANCE", "Get quotes, compare benefits and buy online from top Life Insurance companies.", R.drawable.life_insurance, "LifeInsTitle", "LifeInsdesc"));
//        dashboardEntities.add(new DashboardMultiLangEntity("INSURANCE", 16, "REQUEST OFFLINE QUOTES", "Get offline quotes.", R.drawable.offlineportal, "OfflineQTitle", "OfflineQdesc"));

        //endegion
        //endregion

        if (prefManager.getMenuDashBoard() != null) {
            dashBoardItemEntities = prefManager.getMenuDashBoard().getMasterData().getDashboard();
            //     if (dashboardEntities != null && dashboardEntities.size() > 0) {
            for (DashBoardItemEntity dashBoardItemEntity : dashBoardItemEntities) {
                if (dashBoardItemEntity.getDashboard_type() == 1 && dashBoardItemEntity.getIsActive() == 1) {
                    DashboardMultiLangEntity dashboardEntity = new DashboardMultiLangEntity("INSURANCE", Integer.parseInt(dashBoardItemEntity.getSequence()), "" + dashBoardItemEntity.getMenuname(), "" + dashBoardItemEntity.getDescription(), -1, "Insurance", "");
                    dashboardEntity.setServerIcon(dashBoardItemEntity.getIconimage());
                    dashboardEntity.setLink(dashBoardItemEntity.getLink());

                    dashboardEntity.setProductNameFontColor(dashBoardItemEntity.getProductNameFontColor());
                    dashboardEntity.setProductDetailsFontColor(dashBoardItemEntity.getProductDetailsFontColor());
                    dashboardEntity.setProductBackgroundColor(dashBoardItemEntity.getProductBackgroundColor());

                    dashboardEntity.setIsExclusive(dashBoardItemEntity.getIsExclusive());
                    dashboardEntity.setIsNewprdClickable(dashBoardItemEntity.getIsNewprdClickable());
                    dashboardEntity.setIsSharable(dashBoardItemEntity.getIsSharable());

                    dashboardEntity.setTitle(dashBoardItemEntity.getTitle());
                    dashboardEntity.setInfo(dashBoardItemEntity.getInfo());

                    dashboardEntity.setPopupmsg(dashBoardItemEntity.getPopupmsg());
                    dashboardEntities.add(dashboardEntity);
                }
            }
            //     }
        }

        return dashboardEntities;
    }


    public List<DashboardMultiLangEntity> getLoanProductLangList() {
        List<DashboardMultiLangEntity> dashboardEntities = new ArrayList<DashboardMultiLangEntity>();
        DBPersistanceController db = new DBPersistanceController(mContext);

        if (db.getUserConstantsData() != null && db.getUserConstantsData().getKotakEliteEnabled() != null) {


            int visibility = Integer.parseInt(db.getUserConstantsData().getKotakEliteEnabled());
            if (visibility == 1){
                DashboardMultiLangEntity objDashboard = new DashboardMultiLangEntity("LOANS", 23, "Kotak Group health Care", "Exclusive Health Insurance plan for Elite Members. Best in class features @ lower premium.", R.drawable.kotak_elite, "KOTAKTitle", "KOTAKdesc");
                objDashboard.setIsExclusive("Y");
                objDashboard.setIsSharable("Y");
                objDashboard.setInfo("http://origin-cdnh.policyboss.com/fmweb/GroupHealthCare/update.html");
                objDashboard.setTitle("Kotak Group health Care");
                objDashboard.setPopupmsg("Exclusive Health Insurance plan for Elite Members. Best in class features @ lower premium.");
                dashboardEntities.add(objDashboard);
            }
            else{

            }


        }



        if (!isHideLoan()) {
            /////////////////////////
            dashboardEntities.add(new DashboardMultiLangEntity("LOANS", 4, "CREDIT CARD", "Get instant Credit card approvals with amazing offers & deals.", R.drawable.credit_card, "CCTitle", "CCdesc"));

            dashboardEntities.add(new DashboardMultiLangEntity("LOANS", 19, "PERSONAL LOAN", "Provide Instant approval for your customers at attractive interest rates.", R.drawable.personal_loan, "PlTitle", "Pldesc"));
            dashboardEntities.add(new DashboardMultiLangEntity("LOANS", 6, "BUSINESS LOAN", "Maximum loan amount at competitive interest rate .", R.drawable.balance_transfer, "BLTitle", "BLdesc"));
            dashboardEntities.add(new DashboardMultiLangEntity("LOANS", 7, "HOME LOAN", "Home loan at best interest rates from over 20+ banks & NBFCs.", R.drawable.home_loan, "HlTitle", "Hldesc"));

            dashboardEntities.add(new DashboardMultiLangEntity("LOANS", 8, "LOAN AGAINST PROPERTY", "Maximum loan amount at competitive interest rate against the property.", R.drawable.loan_against_property, "LAPTitle", "LAPdesc"));
            dashboardEntities.add(new DashboardMultiLangEntity("LOANS", 81, "CAR LOAN TOP UP", "Sell car loan Top-Up, upto 200% of the car value of your customer!", R.drawable.carloan, "LAPTitle", "LAPdesc"));

        }
        if (prefManager.getMenuDashBoard() != null) {
            dashBoardItemEntities = prefManager.getMenuDashBoard().getMasterData().getDashboard();
            if (dashboardEntities != null && dashboardEntities.size() > 0) {
                for (DashBoardItemEntity dashBoardItemEntity : dashBoardItemEntities) {
                    if (dashBoardItemEntity.getDashboard_type() == 2 && dashBoardItemEntity.getIsActive() == 1) {
                        DashboardMultiLangEntity dashboardEntity = new DashboardMultiLangEntity("LOANS", Integer.parseInt(dashBoardItemEntity.getSequence()), "" + dashBoardItemEntity.getMenuname(), "" + dashBoardItemEntity.getDescription(), -1, "Loans", "");
                        dashboardEntity.setServerIcon(dashBoardItemEntity.getIconimage());
                        dashboardEntity.setLink(dashBoardItemEntity.getLink());
                        dashboardEntities.add(dashboardEntity);
                    }
                }
            }
        }

        return dashboardEntities;
    }

    public List<DashboardMultiLangEntity> getMoreProductLangList() {
        List<DashboardMultiLangEntity> dashboardEntities = new ArrayList<DashboardMultiLangEntity>();
        DBPersistanceController db = new DBPersistanceController(mContext);

        if (db.getUserConstantsData() != null &&
                db.getUserConstantsData().getEnablencd() != null
                && db.getUserConstantsData().getEnablencd().equalsIgnoreCase("1")) {
            dashboardEntities.add(new DashboardMultiLangEntity("MORE SERVICES", 15,
                    "OTHER INVESTMENT PRODUCTS", " NCDs (Secured/unsecured Debentures)",
                    R.drawable.investment_icon, "OtherInvProTitle", "OtherInvProdesc"));
        }
        // dashboardEntities.add(new DashboardEntity("MORE SERVICES", 2, "FIN-PEACE", "A must for all your customers. A unique BEYOND LIFE services for your customer's peace of mind", R.drawable.fin_peace));
        // dashboardEntities.add(new DashboardEntity("MORE SERVICES", 11, "HEALTH CHECK UP PLANS", "Offer a wide array of health check up plans from reputed diagnostics labs at discounted prices and free home collection", R.drawable.health_checkup_plan));

        if (prefManager.getMenuDashBoard() != null) {
            dashBoardItemEntities = prefManager.getMenuDashBoard().getMasterData().getDashboard();
            if (dashboardEntities != null) {
                for (DashBoardItemEntity dashBoardItemEntity : dashBoardItemEntities) {
                    if (dashBoardItemEntity.getDashboard_type() == 3 && dashBoardItemEntity.getIsActive() == 1) {
                        DashboardMultiLangEntity dashboardEntity = new DashboardMultiLangEntity("MORE SERVICES", Integer.parseInt(dashBoardItemEntity.getSequence()), "" + dashBoardItemEntity.getMenuname(), "" + dashBoardItemEntity.getDescription(), -1, "MoreServices", "");
                        dashboardEntity.setServerIcon(dashBoardItemEntity.getIconimage());
                        dashboardEntity.setLink(dashBoardItemEntity.getLink());
                        dashboardEntities.add(dashboardEntity);
                    }
                }
            }
        }

        return dashboardEntities;
    }

    //endregion

    //region previous insurer

    public boolean storeInsurerMaster(InsurerResponse insurerResponse) {
        Gson gson = new Gson();
        editor.remove(INSURER_LIST);
        editor.putString(INSURER_LIST, gson.toJson(insurerResponse));
        return editor.commit();
    }

    public InsurerResponse getInsurerMaster() {
        if (new Gson().fromJson(prefManager.pref.getString(INSURER_LIST, "")
                , InsurerResponse.class) != null) {
            return new Gson().fromJson(prefManager.pref.getString(INSURER_LIST, "")
                    , InsurerResponse.class);
        }
        return null;
    }

    public List<String> getInsurerMasterList() {
        ArrayList<String> insurenceList = new ArrayList<>();
        InsurerResponse master = getInsurerMaster();
        if (master != null) {
            for (int i = 0; i < master.getMasterData().size(); i++) {
                insurenceList.add(master.getMasterData().get(i).getInsurer_name());
            }
            insurenceList.add(0, "Present Insurer");
        } else {
            return getInsurerList();
        }
        return insurenceList;
    }

    public String getInsurerMasterID(String insurerName) {
        InsurerResponse master = getInsurerMaster();
        if (master != null) {
            for (int i = 0; i < master.getMasterData().size(); i++) {

                if (insurerName.toLowerCase().equalsIgnoreCase(
                        master.getMasterData().get(i).getInsurer_name().toLowerCase()
                )) {
                    return master.getMasterData().get(i).getInsurer_id();
                }
            }
        } else {

            return "" + getInsurenceID(insurerName);
        }

        return "0";
    }

    public String getInsurerNameMaster(String insurerID) {
        InsurerResponse master = getInsurerMaster();
        if (master != null) {
            for (int i = 0; i < master.getMasterData().size(); i++) {

                if (insurerID.equalsIgnoreCase(master.getMasterData().get(i).getInsurer_id())) {
                    // return master.getMasterData().get(i).getInsurer_id();
                    return getInsurername(Integer.valueOf(master.getMasterData().get(i).getInsurer_id()));
                }
            }
        } else {
            return getInsurername(Integer.parseInt(insurerID));
        }
        return "0";
    }


    public List<String> getInsurerList() {
        MapInsurence();
        ArrayList<String> insurenceList = new ArrayList<String>(hashMapInsurence.keySet());
        insurenceList.add(0, "Present Insurer");
        return insurenceList;

    }

    public void MapInsurence() {
        hashMapInsurence = new TreeMap<String, Integer>();
        hashMapInsurence.put("Acko", 45);
        hashMapInsurence.put("Bajaj", 1);
        hashMapInsurence.put("Bharti", 2);
        hashMapInsurence.put("Go Digit", 44);
        hashMapInsurence.put("HDFC", 5);
        hashMapInsurence.put("ICICI", 6);
        hashMapInsurence.put("IFFCO", 7);
        hashMapInsurence.put("Kotak", 30);
        //hashMapInsurence.put("L & T Ins. ", 15);
        hashMapInsurence.put("Liberty Videocon", 33);
        hashMapInsurence.put("Magma", 35);
        hashMapInsurence.put("National ", 8);
        hashMapInsurence.put("New India", 12);
        hashMapInsurence.put("Oriental", 13);
        hashMapInsurence.put("Raheja", 16);
        hashMapInsurence.put("Reliance", 9);
        hashMapInsurence.put("Sundaram", 10);
        hashMapInsurence.put("SBI General ", 17);
        hashMapInsurence.put("Shriram ", 18);
        hashMapInsurence.put("Tata AIG", 11);
        hashMapInsurence.put("United", 14);
        hashMapInsurence.put("Future Gen", 4);
        hashMapInsurence.put("Universal Sompo", 19);
        hashMapInsurence.put("Cholamandalam", 3);




        /*
            Following not shown in FINMART

        hashMapInsurence.put("Star Health Insurance", 26);
        hashMapInsurence.put("Religare Health Insurance", 34);
  */

    }

    public String getInsurername(int insurerID) {
        MapInsurence();
        String insName = "";
        for (Map.Entry<String, Integer> entity : hashMapInsurence.entrySet()) {
            if (entity.getValue() == insurerID) {
                insName = entity.getKey();
                break;
            }

        }
        return insName;
    }

    public int getInsurenceID(String insurenceName) {
        MapInsurence();
        return hashMapInsurence.get(insurenceName);
    }
    //endregion

    //region Addon

    public void MapAddons() {
        hashMapAddons.put("addon_ambulance_charge_cover", "Ambulance Charge Cover");
        hashMapAddons.put("addon_consumable_cover", "Consumable Cover");
        hashMapAddons.put("addon_daily_allowance_cover", "Daily Allowance Cover");
        hashMapAddons.put("addon_engine_protector_cover", "Engine Protection Cover");
        hashMapAddons.put("addon_hospital_cash_cover", "Hospital Cash Cover");
        hashMapAddons.put("addon_hydrostatic_lock_cover", "Hydrostatic Lock Cover");
        hashMapAddons.put("addon_inconvenience_allowance_cover", "Inconvinenience Allowance Cover");
        hashMapAddons.put("addon_invoice_price_cover", "Invoice Price Cover");
        hashMapAddons.put("addon_key_lock_cover", "Key Lock Cover");
        hashMapAddons.put("addon_losstime_protection_cover", "Loss Time Protection");
        hashMapAddons.put("addon_medical_expense_cover", "Medical Expense");
        hashMapAddons.put("addon_ncb_protection_cover", "NCB Protection");
        hashMapAddons.put("addon_passenger_assistance_cover", "Passenger Assistance");
        hashMapAddons.put("addon_personal_belonging_loss_cover", "Personal Belonging-Loss Cover");
        hashMapAddons.put("addon_road_assist_cover", "24X7 RoadSide Assistance");
        hashMapAddons.put("addon_rodent_bite_cover", "Rodent bite Cover");
        hashMapAddons.put("addon_tyre_coverage_cover", "Tyre Coverage");
        hashMapAddons.put("addon_windshield_cover", "Windshield Protection");
        hashMapAddons.put("addon_zero_dep_cover", "Zero Depriciation");

    }

    public String getAddonName(String addonName) {
        hashMapAddons = new HashMap<String, String>();
        MapAddons();
        return hashMapAddons.get(addonName);
    }

    public String getAddonKey(String selectedName) {
        hashMapAddons = new HashMap<String, String>();
        MapAddons();
        String AddOnName = "";
        for (Map.Entry<String, String> item : hashMapAddons.entrySet()) {
            if (item.getValue().matches(selectedName)) {
                AddOnName = item.getKey();
                break;
            }
        }

        return AddOnName;
    }
    //endregion

    //region Property info Loan

    public List<PropertyInfoEntity> getLoanPropertyInfoList() {
        List<PropertyInfoEntity> propertyInfoEntity = new ArrayList<PropertyInfoEntity>();
        propertyInfoEntity.add(new PropertyInfoEntity(1, "Property Identified & ready to occupy"));
        propertyInfoEntity.add(new PropertyInfoEntity(2, "In Search Of Property"));
        propertyInfoEntity.add(new PropertyInfoEntity(3, "Resale Property"));
        propertyInfoEntity.add(new PropertyInfoEntity(4, "For Construction"));
        propertyInfoEntity.add(new PropertyInfoEntity(5, "Property identified - Under Construction"));
        propertyInfoEntity.add(new PropertyInfoEntity(6, "LAP"));
        return propertyInfoEntity;
    }

    public int getPropertyId(String propName) {

        PropertyInfoEntity entity = realm.where(PropertyInfoEntity.class).equalTo("Property_Type", propName).findFirst();
        if (entity != null) {
            return entity.getProperty_Id();
        }
        return 0;
    }
    //endregion

    //region login data

    public void storeUserData(LoginResponseEntity loginResponseEntity) {
        realm.beginTransaction();
        realm.delete(LoginResponseEntity.class);
        realm.copyToRealmOrUpdate(loginResponseEntity);
        realm.commitTransaction();
        Log.d("---Log", "User Stored");
    }

    public void clearUserData() {
        realm.beginTransaction();
        realm.delete(LoginResponseEntity.class);
        realm.delete(UserConstantEntity.class);
        realm.commitTransaction();
    }

    public void logout() {
        realm.beginTransaction();
        realm.delete(LoginResponseEntity.class);
        realm.delete(AccountDtlEntity.class);
        realm.delete(DocsEntity.class);
        realm.delete(UserConstantEntity.class);

        realm.deleteAll();
        realm.commitTransaction();
    }

    public void clearSwitchUser() {
        realm.beginTransaction();
        realm.delete(LoginResponseEntity.class);
        realm.delete(AccountDtlEntity.class);
        realm.delete(DocsEntity.class);
        realm.delete(UserConstantEntity.class);
        realm.commitTransaction();
    }


    public LoginResponseEntity getUserData() {
        LoginResponseEntity entity = realm.where(LoginResponseEntity.class).findFirst();

        if (entity != null) {
            Log.d("---Log", entity.getCustID());
            return entity;
        } else
            return null;
    }


    //endregion

    //region myAccount data

    public void updateMyAccountData(AccountDtlEntity accountDtlEntity) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(accountDtlEntity);
        realm.commitTransaction();
    }

    public AccountDtlEntity getAccountData() {
        AccountDtlEntity entity = realm.where(AccountDtlEntity.class).findFirst();
        if (entity != null)
            return entity;
        else
            return null;
    }
    //endregion

    //region DOC list

    public void storeDocList(List<DocsEntity> docsEntityList) {
        realm.beginTransaction();
        realm.delete(DocsEntity.class);
        realm.copyToRealm(docsEntityList);
        realm.commitTransaction();
    }


    public List<DocsEntity> getDocList(String compId, String lang) {
        List<DocsEntity> docsEntityList = realm.where(DocsEntity.class).equalTo("company_id", compId).equalTo("language", lang.trim(), Case.INSENSITIVE).findAll();
        if (docsEntityList != null)
            return docsEntityList;
        else
            return null;
    }


    public void storeCompanyList(List<SalesProductEntity> salesProductList) {
        realm.beginTransaction();
        realm.delete(SalesProductEntity.class);
        realm.copyToRealm(salesProductList);
        realm.commitTransaction();
    }

    public List<SalesProductEntity> getCompanyList() {


        List<SalesProductEntity> salesProductList = new ArrayList<>();
//        if (isHideLoan()) {
//            salesProductList = realm.where(SalesProductEntity.class).notEqualTo("Product_Id", 4)
//                    .notEqualTo("Product_Id", 7).findAll();
//        } else {
//            salesProductList = realm.where(SalesProductEntity.class).findAll();
//        }

        salesProductList = realm.where(SalesProductEntity.class).findAll();

        if (salesProductList != null) {
            return salesProductList;
        } else
            return null;
    }

    public void UpdateCompanyList(List<SalesProductEntity> salesCompList) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(salesCompList);
        realm.commitTransaction();
    }

    //endregion

    //region insurance image mapping

    public void MapCarInsuranceImage() {

        hasMapCarInsuranceImage.put(1, R.drawable.car_1);
        hasMapCarInsuranceImage.put(2, R.drawable.car_2);
        hasMapCarInsuranceImage.put(3, R.drawable.car_3);
        hasMapCarInsuranceImage.put(4, R.drawable.car_4);
        hasMapCarInsuranceImage.put(5, R.drawable.car_5);
        hasMapCarInsuranceImage.put(6, R.drawable.car_6);
        hasMapCarInsuranceImage.put(7, R.drawable.car_7);
        hasMapCarInsuranceImage.put(8, R.drawable.car_8);
        hasMapCarInsuranceImage.put(9, R.drawable.car_9);
        hasMapCarInsuranceImage.put(10, R.drawable.car_10);
        hasMapCarInsuranceImage.put(11, R.drawable.car_11);
        hasMapCarInsuranceImage.put(12, R.drawable.car_12);
        hasMapCarInsuranceImage.put(13, R.drawable.car_13);
        hasMapCarInsuranceImage.put(14, R.drawable.car_14);
        hasMapCarInsuranceImage.put(15, R.drawable.car_15);
        hasMapCarInsuranceImage.put(16, R.drawable.car_16);
        hasMapCarInsuranceImage.put(17, R.drawable.car_17);
        hasMapCarInsuranceImage.put(18, R.drawable.car_18);
        hasMapCarInsuranceImage.put(19, R.drawable.car_19);
        hasMapCarInsuranceImage.put(33, R.drawable.car_33);
        hasMapCarInsuranceImage.put(35, R.drawable.car_35);
        hasMapCarInsuranceImage.put(41, R.drawable.car_41);
    }

    public int getInsurerLogo(int insurerID) {

        hasMapCarInsuranceImage = new HashMap<Integer, Integer>();
        MapCarInsuranceImage();
        if (hasMapCarInsuranceImage.get(insurerID) != null)
            return hasMapCarInsuranceImage.get(insurerID);
        else
            return R.drawable.private_car;
    }
    //endregion

    //region Health Data

    public List<String> getHealthCity() {
        hashmapCity = new HashMap<String, Integer>();
        MapHealthCity();
        return new ArrayList<String>(hashmapCity.keySet());
    }

    public int getHealthCityID(String cityName) {
        hashmapCity = new HashMap<String, Integer>();
        MapHealthCity();
        if (hashmapCity.get(cityName) != null) {
            return hashmapCity.get(cityName);
        } else {
            return 0;
        }

    }

    public String getHealthCityName(int cityID) {
        hashmapCity = new HashMap<String, Integer>();
        MapHealthCity();
        String HealthCityName = "";
        for (Map.Entry<String, Integer> item : hashmapCity.entrySet()) {
            if (item.getValue() == cityID) {
                HealthCityName = item.getKey();
                break;
            }
        }

        return HealthCityName;
    }


    public void MapHealthCity() {

        hashmapCity.put("ADILABAD", 3);
        hashmapCity.put("AGARTALA", 6);
        hashmapCity.put("AGRA", 7);
        hashmapCity.put("AHMEDABAD", 9);
        hashmapCity.put("AHMEDNAGAR", 10);
        hashmapCity.put("AIZAWL", 13);
        hashmapCity.put("AJMER", 14);
        hashmapCity.put("AKOLA", 16);
        hashmapCity.put("ALIGARH", 20);
        hashmapCity.put("ALLAHABAD", 23);
        hashmapCity.put("ALMORA", 24);
        hashmapCity.put("ALWAR", 27);
        hashmapCity.put("AMBALA", 31);
        hashmapCity.put("AMBASSA", 32);
        hashmapCity.put("AMRAVATI", 37);
        hashmapCity.put("AMRITSAR", 39);
        hashmapCity.put("ANAND", 42);
        hashmapCity.put("ANANTAPUR", 44);
        hashmapCity.put("ANANTNAG", 45);
        hashmapCity.put("ANGUL", 48);
        hashmapCity.put("ARARIA", 55);
        hashmapCity.put("AURANGABAD", 65);
        hashmapCity.put("AZAMGARH", 67);
        hashmapCity.put("BAGESHWAR", 71);
        hashmapCity.put("BAGPAT", 74);
        hashmapCity.put("BAHRAICH", 78);
        hashmapCity.put("BALAGHAT", 83);
        hashmapCity.put("BALANGIR", 84);
        hashmapCity.put("BALASORE", 85);
        hashmapCity.put("BALRAMPUR", 88);
        hashmapCity.put("BANASKANTHA", 90);
        hashmapCity.put("BANDA", 91);
        hashmapCity.put("BANGALORE", 93);
        hashmapCity.put("BANKA", 95);
        hashmapCity.put("BANKURA", 96);
        hashmapCity.put("BANSWARA", 99);
        hashmapCity.put("BARABANKI", 100);
        hashmapCity.put("BARAMULA", 103);
        hashmapCity.put("BARAN", 104);
        hashmapCity.put("BARDHAMAN", 106);
        hashmapCity.put("BAREILLY", 107);
        hashmapCity.put("BARMER", 109);
        hashmapCity.put("BARPETA", 111);
        hashmapCity.put("BASTAR", 116);
        hashmapCity.put("BASTI", 117);
        hashmapCity.put("BATHINDA", 119);
        hashmapCity.put("BEED", 122);
        hashmapCity.put("BEGUSARAI", 123);
        hashmapCity.put("BELGAUM", 124);
        hashmapCity.put("BELLARY", 125);
        hashmapCity.put("BETUL", 128);
        hashmapCity.put("BHADRAK", 130);
        hashmapCity.put("BHAGALPUR", 132);
        hashmapCity.put("BHANDARA", 134);
        hashmapCity.put("BHARATPUR", 135);
        hashmapCity.put("BHARUCH", 137);
        hashmapCity.put("BHAVNAGAR", 139);
        hashmapCity.put("BHILWARA", 141);
        hashmapCity.put("BHIND", 143);
        hashmapCity.put("BHIWANI", 144);
        hashmapCity.put("BHOJPUR", 145);
        hashmapCity.put("BHOPAL", 147);
        hashmapCity.put("BHUBANESWAR", 148);
        hashmapCity.put("BIDAR", 152);
        hashmapCity.put("BIJAPUR", 153);
        hashmapCity.put("BIJNOR", 154);
        hashmapCity.put("BIKANER", 155);
        hashmapCity.put("BILASPUR", 156);
        hashmapCity.put("BILASPUR", 157);
        hashmapCity.put("BIRBHUM", 158);
        hashmapCity.put("BISHNUPUR", 159);
        hashmapCity.put("BOKARO", 161);
        hashmapCity.put("BOMDILA", 162);
        hashmapCity.put("BONGAIGAON", 163);
        hashmapCity.put("BUDAUN", 165);
        hashmapCity.put("BULDHANA", 169);
        hashmapCity.put("BUNDI", 170);
        hashmapCity.put("BURHANPUR", 172);
        hashmapCity.put("BUXAR", 173);
        hashmapCity.put("CHAIBASA", 176);
        hashmapCity.put("CHAMBA", 179);
        hashmapCity.put("CHAMOLI", 181);
        hashmapCity.put("CHAMPAWAT", 182);
        hashmapCity.put("CHAMPHAI", 183);
        hashmapCity.put("CHANDAULI", 185);
        hashmapCity.put("CHANDEL", 186);
        hashmapCity.put("CHANDIGARH", 187);
        hashmapCity.put("CHANDRAPUR", 188);
        hashmapCity.put("CHATRA", 192);
        hashmapCity.put("CHENNAI", 196);
        hashmapCity.put("CHHINDWARA", 202);
        hashmapCity.put("CHITRADURGA", 210);
        hashmapCity.put("CHURACHANDPUR", 215);
        hashmapCity.put("CHURU", 218);
        hashmapCity.put("COIMBATORE", 220);
        hashmapCity.put("CUDDAPAH", 224);
        hashmapCity.put("CUTTACK", 226);
        hashmapCity.put("DAHOD", 231);
        hashmapCity.put("DAKSHIN DINAJPUR", 232);
        hashmapCity.put("DAKSHINA KANNADA", 233);
        hashmapCity.put("DAMOH", 237);
        hashmapCity.put("DANTEWADA", 239);
        hashmapCity.put("DAPORIJO", 240);
        hashmapCity.put("DARBHANGA", 241);
        hashmapCity.put("DARRANG", 243);
        hashmapCity.put("DATIA", 246);
        hashmapCity.put("DAUSA", 247);
        hashmapCity.put("DAVANAGERE", 248);
        hashmapCity.put("DEHRADUN", 251);
        hashmapCity.put("DEOGARH", 254);
        hashmapCity.put("DEOGHAR", 255);
        hashmapCity.put("DEORIA", 256);
        hashmapCity.put("DEWAS", 260);
        hashmapCity.put("DHALAI", 261);
        hashmapCity.put("DHAMTARI", 262);
        hashmapCity.put("DHANBAD", 263);
        hashmapCity.put("DHAR", 264);
        hashmapCity.put("DHARMAPURI", 269);
        hashmapCity.put("DHARWAD", 270);
        hashmapCity.put("DHEMAJI", 271);
        hashmapCity.put("DHENKANAL", 272);
        hashmapCity.put("DHOLPUR", 273);
        hashmapCity.put("DHUBRI", 274);
        hashmapCity.put("DHULE", 275);
        hashmapCity.put("DIBANG VALLEY", 277);
        hashmapCity.put("DIBRUGARH", 278);
        hashmapCity.put("DIMAPUR", 281);
        hashmapCity.put("DINDIGUL", 282);
        hashmapCity.put("DINDORI", 283);
        hashmapCity.put("DODA", 287);
        hashmapCity.put("DUMKA", 291);
        hashmapCity.put("DUNGARPUR", 292);
        hashmapCity.put("DURG", 293);
        hashmapCity.put("EAST CHAMPARAN", 295);
        hashmapCity.put("EAST GODAVARI", 297);
        hashmapCity.put("EAST SIANG", 300);
        hashmapCity.put("ERNAKULAM", 305);
        hashmapCity.put("ERODE", 306);
        hashmapCity.put("ETAH", 307);
        hashmapCity.put("ETAWAH", 308);
        hashmapCity.put("FAIZABAD", 310);
        hashmapCity.put("FARIDABAD", 311);
        hashmapCity.put("FARIDKOT", 312);
        hashmapCity.put("FARRUKHABAD", 313);
        hashmapCity.put("FATEHABAD", 314);
        hashmapCity.put("FATEHPUR", 317);
        hashmapCity.put("FIROZABAD", 320);
        hashmapCity.put("GADCHIROLI", 323);
        hashmapCity.put("GAJAPATI", 325);
        hashmapCity.put("GANDHINAGAR", 331);
        hashmapCity.put("GANGANAGAR", 332);
        hashmapCity.put("GANGTOK", 333);
        hashmapCity.put("GANJAM", 334);
        hashmapCity.put("GARHWA", 336);
        hashmapCity.put("GAUTAM BUDH NAGAR", 338);
        hashmapCity.put("GAYA", 339);
        hashmapCity.put("GEYZING", 340);
        hashmapCity.put("GHAZIABAD", 342);
        hashmapCity.put("GIRIDIH", 346);
        hashmapCity.put("GOALPARA", 347);
        hashmapCity.put("GODDA", 349);
        hashmapCity.put("GOLAGHAT", 353);
        hashmapCity.put("GONDA", 354);
        hashmapCity.put("GONDIA", 355);
        hashmapCity.put("GOPALGANJ", 356);
        hashmapCity.put("GORAKHPUR", 357);
        hashmapCity.put("GULBARGA", 363);
        hashmapCity.put("GUMLA", 364);
        hashmapCity.put("GUNA", 365);
        hashmapCity.put("GUNTUR", 366);
        hashmapCity.put("GURDASPUR", 367);
        hashmapCity.put("GURGAON", 368);
        hashmapCity.put("GUWAHATI", 370);
        hashmapCity.put("GWALIOR", 371);
        hashmapCity.put("HAMIRPUR", 376);
        hashmapCity.put("HAMIRPUR", 377);
        hashmapCity.put("HANUMANGARH", 380);
        hashmapCity.put("HARDA", 382);
        hashmapCity.put("HARDOI", 383);
        hashmapCity.put("HARIDWAR", 384);
        hashmapCity.put("HASSAN", 385);
        hashmapCity.put("HINGOLI", 392);
        hashmapCity.put("HISSAR", 393);
        hashmapCity.put("HOOGHLY", 396);
        hashmapCity.put("HOSHANGABAD", 397);
        hashmapCity.put("HOWRAH", 400);
        hashmapCity.put("HYDERABAD", 404);
        hashmapCity.put("IDUKKI", 407);
        hashmapCity.put("INDORE", 409);
        hashmapCity.put("ITANAGAR", 411);
        hashmapCity.put("JABALPUR", 412);
        hashmapCity.put("JAGATSINGHPUR", 414);
        hashmapCity.put("JAIPUR", 419);
        hashmapCity.put("JAISALMER", 420);
        hashmapCity.put("JAJPUR", 423);
        hashmapCity.put("JALANDHAR", 425);
        hashmapCity.put("JALAUN", 426);
        hashmapCity.put("JALGAON", 427);
        hashmapCity.put("JALNA", 428);
        hashmapCity.put("JALOR", 429);
        hashmapCity.put("JALPAIGURI", 430);
        hashmapCity.put("JAMMU", 432);
        hashmapCity.put("JAMNAGAR", 433);
        hashmapCity.put("JAMSHEDPUR", 434);
        hashmapCity.put("JAMTARA", 435);
        hashmapCity.put("JAMUI", 436);
        hashmapCity.put("JANJGIR-CHAMPA", 439);
        hashmapCity.put("JASHPUR", 441);
        hashmapCity.put("JAUNPUR", 442);
        hashmapCity.put("JHABUA", 445);
        hashmapCity.put("JHAJJAR", 446);
        hashmapCity.put("JHALAWAR", 447);
        hashmapCity.put("JHANSI", 448);
        hashmapCity.put("JHARSUGUDA", 449);
        hashmapCity.put("JHUNJHUNU", 451);
        hashmapCity.put("JIND", 452);
        hashmapCity.put("JODHPUR", 453);
        hashmapCity.put("JORHAT", 455);
        hashmapCity.put("JUNAGADH", 457);
        hashmapCity.put("KAILASAHAR", 463);
        hashmapCity.put("KAITHAL", 465);
        hashmapCity.put("KAKINADA", 466);
        hashmapCity.put("KANGRA", 480);
        hashmapCity.put("KANKER", 484);
        hashmapCity.put("KANPUR", 487);
        hashmapCity.put("KAPURTHALA", 490);
        hashmapCity.put("KARAULI", 492);
        hashmapCity.put("KARGIL", 494);
        hashmapCity.put("KARIMNAGAR", 496);
        hashmapCity.put("KARNAL", 498);
        hashmapCity.put("KARUR", 502);
        hashmapCity.put("KARWAR", 503);
        hashmapCity.put("KASARAGOD", 504);
        hashmapCity.put("KATHUA", 507);
        hashmapCity.put("KATIHAR", 508);
        hashmapCity.put("KATNI", 509);
        hashmapCity.put("KAUSHAMBI", 510);
        hashmapCity.put("KAWARDHA", 513);
        hashmapCity.put("KENDRAPARA", 517);
        hashmapCity.put("KEONJHAR", 518);
        hashmapCity.put("KHAGARIA", 521);
        hashmapCity.put("KHAMMAM", 523);
        hashmapCity.put("KHARAR", 527);
        hashmapCity.put("KHARGONE", 529);
        hashmapCity.put("KHEDA", 530);
        hashmapCity.put("KHONSA", 531);
        hashmapCity.put("KINNAUR", 535);
        hashmapCity.put("KISHANGANJ", 538);
        hashmapCity.put("KODERMA", 542);
        hashmapCity.put("KOHIMA", 545);
        hashmapCity.put("KOKRAJHAR", 546);
        hashmapCity.put("KOLAR", 547);
        hashmapCity.put("KOLASIB", 548);
        hashmapCity.put("KOLHAPUR", 549);
        hashmapCity.put("KOLKATA", 550);
        hashmapCity.put("KOPPAL", 553);
        hashmapCity.put("KORAPUT", 555);
        hashmapCity.put("KORBA", 556);
        hashmapCity.put("KORIYA", 557);
        hashmapCity.put("KOTA", 560);
        hashmapCity.put("KOTTAYAM", 565);
        hashmapCity.put("KOZHIKODE", 569);
        hashmapCity.put("KRISHNA", 570);
        hashmapCity.put("KUPWARA", 577);
        hashmapCity.put("KURNOOL", 579);
        hashmapCity.put("KURUKSHETRA", 580);
        hashmapCity.put("LAKHIMPUR", 583);
        hashmapCity.put("LAKHISARAI", 585);
        hashmapCity.put("LALITPUR", 587);
        hashmapCity.put("LATEHAR", 589);
        hashmapCity.put("LATUR", 590);
        hashmapCity.put("LAWNGTLAI", 591);
        hashmapCity.put("LEH", 593);
        hashmapCity.put("LOHARDAGA", 595);
        hashmapCity.put("LOHIT", 597);
        hashmapCity.put("LOWER SUBANSIRI", 599);
        hashmapCity.put("LUCKNOW", 600);
        hashmapCity.put("LUDHIANA", 601);
        hashmapCity.put("MADHEPURA", 605);
        hashmapCity.put("MADHUBANI", 606);
        hashmapCity.put("MADIKERI", 607);
        hashmapCity.put("MADURAI", 608);
        hashmapCity.put("MAHARAJGANJ", 610);
        hashmapCity.put("MAHASAMUND", 611);
        hashmapCity.put("MAHENDRAGARH", 614);
        hashmapCity.put("MAHOBA", 616);
        hashmapCity.put("MAINPURI", 617);
        hashmapCity.put("MALEGAON", 622);
        hashmapCity.put("MALKANGIRI", 624);
        hashmapCity.put("MAMIT", 627);
        hashmapCity.put("MANDI", 631);
        hashmapCity.put("MANDLA", 632);
        hashmapCity.put("MANDSAUR", 633);
        hashmapCity.put("MANDYA", 634);
        hashmapCity.put("MANGALORE", 636);
        hashmapCity.put("MANGAN", 637);
        hashmapCity.put("MANSA", 641);
        hashmapCity.put("MATHURA", 647);
        hashmapCity.put("MAU", 649);
        hashmapCity.put("MAYURBHANJ", 652);
        hashmapCity.put("MEDAK", 653);
        hashmapCity.put("MEERUT", 656);
        hashmapCity.put("MEHSANA", 659);
        hashmapCity.put("MIRZAPUR", 662);
        hashmapCity.put("MOGA", 664);
        hashmapCity.put("MOHALI", 665);
        hashmapCity.put("MOKOKCHUNG", 666);
        hashmapCity.put("MON", 668);
        hashmapCity.put("MORADABAD", 670);
        hashmapCity.put("MORENA", 671);
        hashmapCity.put("MUMBAI", 677);
        hashmapCity.put("MUNGER", 678);
        hashmapCity.put("MURSHIDABAD", 679);
        hashmapCity.put("MUZAFFARPUR", 682);
        hashmapCity.put("MYSORE", 683);
        hashmapCity.put("NADIA", 687);
        hashmapCity.put("NAGAON", 690);
        hashmapCity.put("NAGAPATTINAM", 691);
        hashmapCity.put("NAGAUR", 693);
        hashmapCity.put("NAGPUR", 695);
        hashmapCity.put("NAINITAL", 697);
        hashmapCity.put("NALANDA", 701);
        hashmapCity.put("NALBARI", 702);
        hashmapCity.put("NALGONDA", 703);
        hashmapCity.put("NAMAKKAL", 704);
        hashmapCity.put("NAMCHI", 705);
        hashmapCity.put("NANDED", 706);
        hashmapCity.put("NANDURBAR", 708);
        hashmapCity.put("NARMADA", 714);
        hashmapCity.put("NARSINGHPUR", 716);
        hashmapCity.put("NASHIK", 718);
        hashmapCity.put("NAVI MUMBAI", 720);
        hashmapCity.put("NAVSARI", 721);
        hashmapCity.put("NAWADA", 722);
        hashmapCity.put("NAYAGARH", 723);
        hashmapCity.put("NEEMUCH", 725);
        hashmapCity.put("NELLORE", 727);
        hashmapCity.put("NIZAMABAD", 738);
        hashmapCity.put("NOIDA", 739);
        hashmapCity.put("NORTH 24 PARGANAS", 742);
        hashmapCity.put("OSMANABAD", 753);
        hashmapCity.put("PALAMU", 762);
        hashmapCity.put("PALI", 765);
        hashmapCity.put("PANAJI", 767);
        hashmapCity.put("PANCHKULA", 768);
        hashmapCity.put("PANIPAT", 773);
        hashmapCity.put("PANNA", 774);
        hashmapCity.put("PARBHANI", 779);
        hashmapCity.put("PATAN", 784);
        hashmapCity.put("PATHANAMTHITTA", 787);
        hashmapCity.put("PATIALA", 789);
        hashmapCity.put("PATNA", 790);
        hashmapCity.put("PERAMBALUR", 799);
        hashmapCity.put("PHEK", 805);
        hashmapCity.put("PHULBANI", 807);
        hashmapCity.put("PILIBHIT", 809);
        hashmapCity.put("PONDICHERRY", 814);
        hashmapCity.put("PRAKASAM", 819);
        hashmapCity.put("PRATAPGARH", 820);
        hashmapCity.put("PUDUKKOTTAI", 824);
        hashmapCity.put("PULWAMA", 825);
        hashmapCity.put("PUNE", 828);
        hashmapCity.put("PURI", 831);
        hashmapCity.put("PURNIA", 832);
        hashmapCity.put("RAICHUR", 837);
        hashmapCity.put("RAIGAD", 838);
        hashmapCity.put("RAIGARH", 841);
        hashmapCity.put("RAIPUR", 843);
        hashmapCity.put("RAISEN", 844);
        hashmapCity.put("RAJAHMUNDRY", 845);
        hashmapCity.put("RAJAURI", 849);
        hashmapCity.put("RAJGARH", 850);
        hashmapCity.put("RAJKOT", 852);
        hashmapCity.put("RAJNANDGAON", 853);
        hashmapCity.put("RAJSAMAND", 856);
        hashmapCity.put("RAMANATHAPURAM", 859);
        hashmapCity.put("RAMPUR", 863);
        hashmapCity.put("RANCHI", 866);
        hashmapCity.put("RATLAM", 871);
        hashmapCity.put("RATNAGIRI", 872);
        hashmapCity.put("RAYAGADA", 874);
        hashmapCity.put("REWA", 879);
        hashmapCity.put("REWARI", 880);
        hashmapCity.put("ROHTAK", 887);
        hashmapCity.put("ROHTAS", 888);
        hashmapCity.put("RUDRAPRAYAG", 890);
        hashmapCity.put("SABARKANTHA", 893);
        hashmapCity.put("SAGAR", 896);
        hashmapCity.put("SAHARANPUR", 898);
        hashmapCity.put("SAHARSA", 899);
        hashmapCity.put("SALEM", 904);
        hashmapCity.put("SAMASTIPUR", 906);
        hashmapCity.put("SAMBALPUR", 908);
        hashmapCity.put("SANGLI", 911);
        hashmapCity.put("SANGRUR", 912);
        hashmapCity.put("SANT KABIR NAGAR", 914);
        hashmapCity.put("SATARA", 921);
        hashmapCity.put("SATNA", 922);
        hashmapCity.put("SAWAI MADHOPUR", 924);
        hashmapCity.put("SECUNDERABAD", 925);
        hashmapCity.put("SEHORE", 928);
        hashmapCity.put("SENAPATI", 929);
        hashmapCity.put("SEONI", 930);
        hashmapCity.put("SERCHHIP", 933);
        hashmapCity.put("SHAHDOL", 935);
        hashmapCity.put("SHAHJAHANPUR", 937);
        hashmapCity.put("SHAJAPUR", 939);
        hashmapCity.put("SHEOPUR", 942);
        hashmapCity.put("SHIMLA", 945);
        hashmapCity.put("SHIMOGA", 946);
        hashmapCity.put("SHIVPURI", 950);
        hashmapCity.put("SIBSAGAR", 954);
        hashmapCity.put("SIKAR", 958);
        hashmapCity.put("SIMDEGA", 961);
        hashmapCity.put("SINGRAULI", 963);
        hashmapCity.put("SIROHI", 967);
        hashmapCity.put("SIRSA", 968);
        hashmapCity.put("SITAMARHI", 970);
        hashmapCity.put("SITAPUR", 971);
        hashmapCity.put("SIVAGANGA", 972);
        hashmapCity.put("SIWAN", 973);
        hashmapCity.put("SOLAN", 975);
        hashmapCity.put("SOLAPUR", 976);
        hashmapCity.put("SONEPUR", 979);
        hashmapCity.put("SONITPUR", 980);
        hashmapCity.put("SOUTH 24 PARGANAS", 981);
        hashmapCity.put("SOUTH GARO HILLS", 982);
        hashmapCity.put("SRIKAKULAM", 985);
        hashmapCity.put("SRINAGAR", 986);
        hashmapCity.put("SULTANPUR", 990);
        hashmapCity.put("SUNDERGARH", 993);
        hashmapCity.put("SUPAUL", 995);
        hashmapCity.put("SURAT", 996);
        hashmapCity.put("SURENDRANAGAR", 997);
        hashmapCity.put("SURGUJA", 998);
        hashmapCity.put("TAMENGLONG", 1005);
        hashmapCity.put("TAWANG", 1013);
        hashmapCity.put("THANE", 1020);
        hashmapCity.put("THANJAVUR", 1021);
        hashmapCity.put("THENI", 1023);
        hashmapCity.put("THIRUVANANTHAPURAM", 1030);
        hashmapCity.put("THOUBAL", 1036);
        hashmapCity.put("THRISSUR", 1038);
        hashmapCity.put("TIKAMGARH", 1039);
        hashmapCity.put("TINSUKIA", 1040);
        hashmapCity.put("TIRUNELVELI", 1046);
        hashmapCity.put("TIRUVALLUR", 1049);
        hashmapCity.put("TIRUVANNAMALAI", 1050);
        hashmapCity.put("TOHANA", 1054);
        hashmapCity.put("TONK", 1056);
        hashmapCity.put("TRIVANDRUM", 1058);
        hashmapCity.put("TUENSANG", 1059);
        hashmapCity.put("TUMKUR", 1060);
        hashmapCity.put("UDAIPUR", 1066);
        hashmapCity.put("UDAIPUR", 1067);
        hashmapCity.put("UDAIPUR", 1068);
        hashmapCity.put("UDHAMPUR", 1070);
        hashmapCity.put("UDUPI", 1071);
        hashmapCity.put("UJJAIN", 1072);
        hashmapCity.put("UKHRUL", 1073);
        hashmapCity.put("UMARIA", 1076);
        hashmapCity.put("UNA", 1077);
        hashmapCity.put("UTTAR DINAJPUR", 1082);
        hashmapCity.put("UTTARKASHI", 1084);
        hashmapCity.put("VADODARA", 1085);
        hashmapCity.put("VAISHALI", 1087);
        hashmapCity.put("VALSAD", 1089);
        hashmapCity.put("VARANASI", 1091);
        hashmapCity.put("VELLORE", 1097);
        hashmapCity.put("VIDISHA", 1098);
        hashmapCity.put("VIRUDHUNAGAR", 1105);
        hashmapCity.put("VIZIANAGARAM", 1108);
        hashmapCity.put("WARANGAL", 1114);
        hashmapCity.put("WARDHA", 1115);
        hashmapCity.put("WASHIM", 1116);
        hashmapCity.put("WAYANAD", 1117);
        hashmapCity.put("WEST CHAMPARAN", 1118);
        hashmapCity.put("WEST GODAVARI", 1120);
        hashmapCity.put("WOKHA", 1127);
        hashmapCity.put("YADGIR", 1129);
        hashmapCity.put("YAVATMAL", 1132);
        hashmapCity.put("ZUNHEBOTO", 1139);
        hashmapCity.put("ANDAMAN & NICOBAR", 1140);
        hashmapCity.put("ANANTPUR", 1141);
        hashmapCity.put("CHITTOR", 1142);
        hashmapCity.put("KARIM NAGAR", 1143);
        hashmapCity.put("MEHBOOBNAGAR", 1144);
        hashmapCity.put("RAMAGUNDAM", 1145);
        hashmapCity.put("RANGA REDDY", 1146);
        hashmapCity.put("TIRUPATI", 1147);
        hashmapCity.put("VISHAKAPATNAM", 1148);
        hashmapCity.put("ALOG", 1149);
        hashmapCity.put("CHANGALANG", 1150);
        hashmapCity.put("DIBAN VALLEY", 1151);
        hashmapCity.put("EAST KAMENG SEPPA", 1152);
        hashmapCity.put("ROIN", 1153);
        hashmapCity.put("CACHAR", 1154);
        hashmapCity.put("HAILAKANDI", 1155);
        hashmapCity.put("KAMRUP", 1156);
        hashmapCity.put("KARBI-ANGLONG", 1157);
        hashmapCity.put("KARIMGANJ", 1158);
        hashmapCity.put("MORIGAON", 1159);
        hashmapCity.put("N.C.HILLS", 1160);
        hashmapCity.put("ARRAH", 1161);
        hashmapCity.put("BHABUA", 1162);
        hashmapCity.put("BIHAR SHARIF", 1163);
        hashmapCity.put("CHAPRA", 1164);
        hashmapCity.put("JEHANABAD", 1165);
        hashmapCity.put("PURNEA", 1166);
        hashmapCity.put("SEKHPURA", 1167);
        hashmapCity.put("SEOHAR", 1168);
        hashmapCity.put("BHILAI", 1169);
        hashmapCity.put("DAMAN & DIU", 1170);
        hashmapCity.put("NEW DELHI", 729);
        hashmapCity.put("DELHI", 252);
        hashmapCity.put("GOA", 1172);
        hashmapCity.put("AMRELA", 1173);
        hashmapCity.put("DANGS", 1174);
        hashmapCity.put("KUTCH", 1175);
        hashmapCity.put("PANCHMAHALS", 1176);
        hashmapCity.put("PORBANDER", 1177);
        hashmapCity.put("SONIPAT", 1180);
        hashmapCity.put("YAMUNANAGAR", 1181);
        hashmapCity.put("KULLU", 1182);
        hashmapCity.put("LAHAUL-SPITI", 1183);
        hashmapCity.put("SIRMOUR", 1184);
        hashmapCity.put("BADGAN", 1185);
        hashmapCity.put("DADRA & NAGAR", 1186);
        hashmapCity.put("POONCH", 1187);
        hashmapCity.put("HAZARIBAGH", 1188);
        hashmapCity.put("PAKUR", 1189);
        hashmapCity.put("SAHEBGANJ", 1190);
        hashmapCity.put("SARAIKELA", 1191);
        hashmapCity.put("BENGALURU", 1192);
        hashmapCity.put("CHAMRAJNAGAR", 1193);
        hashmapCity.put("CHICKMAGALUR", 1194);
        hashmapCity.put("DAVANAGARE", 1195);
        hashmapCity.put("GADAK", 1196);
        hashmapCity.put("GULBERGA", 1197);
        hashmapCity.put("HAVERI", 1198);
        hashmapCity.put("HOSAPETE", 1199);
        hashmapCity.put("ALAPUZZHA", 1200);
        hashmapCity.put("CALICUT", 1201);
        hashmapCity.put("CANNANORE", 1202);
        hashmapCity.put("KOCHI", 1203);
        hashmapCity.put("KOLLAM", 1204);
        hashmapCity.put("MALLAPURAM", 1205);
        hashmapCity.put("PALGHAT", 1206);
        hashmapCity.put("QUILON", 1207);
        hashmapCity.put("TRICHUR", 1208);
        hashmapCity.put("LAKSHDWEEP", 1209);
        hashmapCity.put("ANOOPPUR", 1210);
        hashmapCity.put("ASHOKNAGAR", 1211);
        hashmapCity.put("BADWANI", 1212);
        hashmapCity.put("CHHATTARPUR", 1213);
        hashmapCity.put("KHANDWA", 1214);
        hashmapCity.put("SINDI", 1215);
        hashmapCity.put("ICHALKARANJI", 1216);
        hashmapCity.put("KOLHPUR", 1217);
        hashmapCity.put("MIRA-BHAYANDAR", 1218);
        hashmapCity.put("PIMRI-CHINCWAD", 1220);
        hashmapCity.put("SHOLAPUR", 1221);
        hashmapCity.put("SINDUDURG", 1222);
        hashmapCity.put("IMPHAL", 1223);
        hashmapCity.put("IMPHAL EAST", 1224);
        hashmapCity.put("IMPHAL WEST", 1225);
        hashmapCity.put("EAST GARO HILL", 1226);
        hashmapCity.put("EAST KHASI HILL", 1227);
        hashmapCity.put("JAINTIA HILL", 1228);
        hashmapCity.put("RI-BHOI DISTRICT", 1229);
        hashmapCity.put("WEST GARO HILL", 1230);
        hashmapCity.put("WEST KHASI HILL", 1231);
        hashmapCity.put("AIZAWAL", 1232);
        hashmapCity.put("CHIMTIPUI DISTRICT", 1233);
        hashmapCity.put("LUGLEI DISTRICT", 1234);
        hashmapCity.put("BARGAH", 1235);
        hashmapCity.put("BOUDH", 1236);
        hashmapCity.put("BRAHMAPUR", 1237);
        hashmapCity.put("CUTTAK", 1238);
        hashmapCity.put("KALHANDI", 1239);
        hashmapCity.put("KHURDA", 1240);
        hashmapCity.put("NAVAPADA", 1241);
        hashmapCity.put("NAVARAGPUR", 1242);
        hashmapCity.put("BHATINDA", 1243);
        hashmapCity.put("FATEHGARH SAHEB", 1244);
        hashmapCity.put("FEROZEPUR", 1245);
        hashmapCity.put("HOSIARPUR", 1246);
        hashmapCity.put("MUKTSAR", 1247);
        hashmapCity.put("NAVANSAHAR", 1248);
        hashmapCity.put("ROPAR", 1249);
        hashmapCity.put("CHITTORGARH", 1250);
        hashmapCity.put("SRI GANGANAGAR", 1251);
        hashmapCity.put("AMBATTUR", 1252);
        hashmapCity.put("AVADI", 1253);
        hashmapCity.put("CUDDALOREI", 1254);
        hashmapCity.put("KANCHEEPURAM", 1255);
        hashmapCity.put("KANNIYAKUMARI", 1256);
        hashmapCity.put("NILGIRIS", 1257);
        hashmapCity.put("THOOTHKUDI", 1258);
        hashmapCity.put("TIRUCHIORAPPALLI", 1259);
        hashmapCity.put("TIRUPUR", 1260);
        hashmapCity.put("TIRUVARUR", 1261);
        hashmapCity.put("TIRUVOTTIYUR", 1262);
        hashmapCity.put("VILLUPURAM", 1263);
        hashmapCity.put("NORTH DISTRICT", 1264);
        hashmapCity.put("SOUTH DISTRICT", 1265);
        hashmapCity.put("WEST DISTRICT", 1266);
        hashmapCity.put("AMBEDKARNAGAR", 1267);
        hashmapCity.put("BALIA", 1268);
        hashmapCity.put("BHADOI", 1269);
        hashmapCity.put("BULANDSHEHAR", 1270);
        hashmapCity.put("GAZIPUR", 1271);
        hashmapCity.put("GORAKPUR", 1272);
        hashmapCity.put("HAPUR", 1273);
        hashmapCity.put("JYOTIBA PHOOLE NAGAR", 1274);
        hashmapCity.put("KANOOJ", 1275);
        hashmapCity.put("KANPUR DEHAT", 1276);
        hashmapCity.put("LAKHIMPUR-KHEDI", 1277);
        hashmapCity.put("LONI", 1278);
        hashmapCity.put("MAHA MAYA NAGAR", 1279);
        hashmapCity.put("MUZAFFAR NAGAR", 1280);
        hashmapCity.put("ORAIYYA", 1281);
        hashmapCity.put("PADRAUNA", 1282);
        hashmapCity.put("RAEBAREILI", 1283);
        hashmapCity.put("SHOOJI MAHARAJ", 1284);
        hashmapCity.put("SHRAVATI", 1285);
        hashmapCity.put("SIDDHARTH NAGAR", 1286);
        hashmapCity.put("SUNBHADRA", 1287);
        hashmapCity.put("UNNAV", 1288);
        hashmapCity.put("GARHWAL", 1289);
        hashmapCity.put("PITORAGARH", 1290);
        hashmapCity.put("TEHRI-GARHWAL", 1291);
        hashmapCity.put("UDHAMSINGH NAGAR", 1292);
        hashmapCity.put("ASANSOL", 1293);
        hashmapCity.put("BALLY", 1294);
        hashmapCity.put("BARANAGAR", 1295);
        hashmapCity.put("BHATPARA", 1296);
        hashmapCity.put("BIDHAN NAGAR", 1297);
        hashmapCity.put("CALCUTTA", 1298);
        hashmapCity.put("COOCHBEHAR", 1299);
        hashmapCity.put("DARJEELING", 1300);
        hashmapCity.put("DURGAPUR", 1301);
        hashmapCity.put("GOPALPUR", 1302);
        hashmapCity.put("KAMARHATI", 1303);
        hashmapCity.put("KULTI", 1305);
        hashmapCity.put("MAHESHTALA", 1306);
        hashmapCity.put("MALDA", 1307);
        hashmapCity.put("MEDINIPUR", 1308);
        hashmapCity.put("NAIHATI", 1309);
        hashmapCity.put("NORTH DUMDUM", 1310);
        hashmapCity.put("PANIHATI", 1311);
        hashmapCity.put("PURULIA", 1312);
        hashmapCity.put("SERAMPORE", 1313);
        hashmapCity.put("SILIGURI", 1314);
        hashmapCity.put("ULUBERIA", 1315);
        hashmapCity.put("ANJAW", 1316);
        hashmapCity.put("PAPUM PARE", 1317);
        hashmapCity.put("TIRAP", 1318);
        hashmapCity.put("UPPER SIANG", 1319);
        hashmapCity.put("UPPER SUBANSIRI", 1320);
        hashmapCity.put("KURUNG KUMEY", 1321);
        hashmapCity.put("WEST SIANG", 1322);
        hashmapCity.put("WEST KAMENG", 1323);
        hashmapCity.put("UPPER SUBANSIRI", 1324);
        hashmapCity.put("UPPER SIANG", 1325);
        hashmapCity.put("NARAYANPUR", 1326);
        hashmapCity.put("EAST SINGHBHUM", 1327);
        hashmapCity.put("RAMGARH", 1328);
        hashmapCity.put("WEST SINGHBHUM", 1329);
        hashmapCity.put("BAGALKOT", 1330);
        hashmapCity.put("KODAGU", 1331);
        hashmapCity.put("RAMANAGAR", 1332);
        hashmapCity.put("UTTARA KANNADA", 1333);
        hashmapCity.put("KANNUR", 1334);
        hashmapCity.put("PALAKKAD", 1335);
        hashmapCity.put("SAIHA", 1336);
        hashmapCity.put("KIPHIRE", 1337);
        hashmapCity.put("LONGLENG", 1338);
        hashmapCity.put("PEREN", 1339);
        hashmapCity.put("BALESWAR", 1340);
        hashmapCity.put("DEBAGARH", 1341);
        hashmapCity.put("KANDHAMAL", 1342);
        hashmapCity.put("BARNALA", 1343);
        hashmapCity.put("ARIYALUR", 1344);
        hashmapCity.put("KRISHNAGIRI", 1345);
        hashmapCity.put("TUTICORIN", 1346);
        hashmapCity.put("AURAIYA", 1347);
        hashmapCity.put("CHITRAKOOT", 1348);
        hashmapCity.put("HATHRAS", 1349);
        hashmapCity.put("KUSHINAGAR", 1350);
        hashmapCity.put("SANT RAVIDAS NAGAR", 1351);
        hashmapCity.put("EAST MIDNAPORE", 1352);
        hashmapCity.put("SOUTH DINAJPUR", 1353);
        hashmapCity.put("WEST MIDNAPORE", 1354);


    }


    public List<HealthSumAssured> getSumAssured() {
        List<HealthSumAssured> list = new ArrayList<HealthSumAssured>();
        list.add(new HealthSumAssured("1 LAC", 100000, false));
        list.add(new HealthSumAssured("2 LACS", 200000, false));
        list.add(new HealthSumAssured("3 LACS", 300000, false));
        list.add(new HealthSumAssured("5 LACS", 500000, false));
        list.add(new HealthSumAssured("6 LACS", 600000, false));
        list.add(new HealthSumAssured("8 LACS", 800000, false));
        list.add(new HealthSumAssured("10 LACS", 1000000, false));
        list.add(new HealthSumAssured("15 LACS", 1500000, false));
        list.add(new HealthSumAssured("20 LACS", 2000000, false));
        list.add(new HealthSumAssured("25 LACS", 2500000, false));
        list.add(new HealthSumAssured("50 LACS", 5000000, false));
        list.add(new HealthSumAssured("100 LACS", 10000000, false));
        return list;
    }


    //endregion

    //region LOan City Data

    public List<String> getLoanCity() {
        hashmapCity = new HashMap<String, Integer>();
        MapHealthCity();
        return new ArrayList<String>(hashmapCity.keySet());
    }

    public int getLoanCityID(String cityName) {
        int i = 0;
        hashmapCity = new HashMap<String, Integer>();
        MapHealthCity();
        if (hashmapCity.get(cityName) != null) {
            i = hashmapCity.get(cityName);
        } else {
            i = 0;
        }
        return i;
    }


    //endregion

    //region health checkup plans

    public HealthPackDEntity getHealthCheckUPPlans() {
        HealthPackDEntity entity = realm.where(HealthPackDEntity.class).findFirst();
        if (entity != null)
            return entity;
        else
            return null;
    }

    public HealthPackDetailsDBean getHealthCheckUPPlansDetails(int packcode) {
        HealthPackDetailsDBean entity = realm.where(HealthPackDetailsDBean.class)
                .equalTo("packcode", packcode).findFirst();

        if (entity != null)
            return entity;
        else
            return null;
    }


    //endregion

    //region Zoho masters

    public ZohoTicketCategoryEntity getZohoCategoryList() {
        ZohoTicketCategoryEntity entity = realm.where(ZohoTicketCategoryEntity.class).findFirst();
        if (entity != null)
            return entity;
        else
            return null;
    }

    public List<String> getCategoryList(ZohoTicketCategoryEntity zohoTicketCategoryEntity) {
        List<String> list = new ArrayList<>();
        list.add("Select Category");
        if (zohoTicketCategoryEntity != null) {
            List<ZohoCategoryEntity> zohoCategoryEntities = zohoTicketCategoryEntity.getCategory();
            if (zohoCategoryEntities != null) {
                for (ZohoCategoryEntity zohoSubcategoryEntity : zohoCategoryEntities) {
                    list.add(zohoSubcategoryEntity.getCateName());
                }
            }
        }
        return list;
    }

    public String getCategoryId(ZohoTicketCategoryEntity zohoTicketCategoryEntity, String categoryNAme) {
        String id = "";
        List<ZohoCategoryEntity> zohoCategoryEntities = zohoTicketCategoryEntity.getCategory();
        if (zohoCategoryEntities != null) {
            for (ZohoCategoryEntity zohoSubcategoryEntity : zohoCategoryEntities) {
                if (zohoSubcategoryEntity.getCateName().equals(categoryNAme)) {
                    return zohoSubcategoryEntity.getCateCode();
                }
            }
        }
        return id;
    }

    public List<String> getSubCategoryList(ZohoTicketCategoryEntity zohoTicketCategoryEntity, String CateCode) {
        List<String> list = new ArrayList<>();
        list.add("Select Sub Category");
        if (zohoTicketCategoryEntity != null) {
            List<ZohoSubcategoryEntity> zohoSubcategoryEntities = zohoTicketCategoryEntity.getSubcategory();
            if (zohoSubcategoryEntities != null) {
                for (ZohoSubcategoryEntity zohoSubcategoryEntity : zohoSubcategoryEntities) {
                    if (zohoSubcategoryEntity.getCateCode().equals(CateCode)) {
                        list.add(zohoSubcategoryEntity.getQuerType());
                    }
                }
            }
        }
        return list;
    }

    public int getSubCategoryId(ZohoTicketCategoryEntity zohoTicketCategoryEntity, String subCategoryNAme) {
        int subCatId = 0;
        if (zohoTicketCategoryEntity != null) {
            List<ZohoSubcategoryEntity> zohoSubcategoryEntities = zohoTicketCategoryEntity.getSubcategory();
            if (zohoSubcategoryEntities != null) {
                for (ZohoSubcategoryEntity zohoSubcategoryEntity : zohoSubcategoryEntities) {
                    if (zohoSubcategoryEntity.getQuerType().equals(subCategoryNAme)) {
                        return zohoSubcategoryEntity.getQuerID();
                    }
                }
            }
        }
        return subCatId;
    }

    public List<String> getClassificationList(ZohoTicketCategoryEntity zohoTicketCategoryEntity, int QuerID) {
        List<String> list = new ArrayList<>();
        list.add("Select Classifications");
        if (zohoTicketCategoryEntity != null) {
            List<ZohoClassificationEntity> zohoClassificationEntities = zohoTicketCategoryEntity.getClassification();
            if (zohoClassificationEntities != null) {
                for (ZohoClassificationEntity zohoClassificationEntity : zohoClassificationEntities) {
                    if (zohoClassificationEntity.getQuerID() == QuerID) {
                        list.add(zohoClassificationEntity.getDescription());
                    }
                }
            }
        }
        return list;
    }

    public int getClassificationId(ZohoTicketCategoryEntity zohoTicketCategoryEntity, String className) {
        int clasId = 0;
        if (zohoTicketCategoryEntity != null) {
            List<ZohoClassificationEntity> zohoClassificationEntities = zohoTicketCategoryEntity.getClassification();
            if (zohoClassificationEntities != null) {
                for (ZohoClassificationEntity zohoClassificationEntity : zohoClassificationEntities) {
                    if (zohoClassificationEntity.getDescription().equals(className)) {
                        return zohoClassificationEntity.getID();
                    }
                }
            }
        }
        return clasId;
    }
    //endregion

    //region constants data

    public ConstantEntity getConstantsData() {
        ConstantEntity entity = realm.where(ConstantEntity.class).findFirst();
        if (entity != null)
            return entity;
        else
            return null;
    }
    //endregion

    //region User constants data

    public UserConstantEntity getUserConstantsData() {
        UserConstantEntity entity = realm.where(UserConstantEntity.class).findFirst();
        if (entity != null)
            return entity;
        else
            return null;
    }

    public void updateUserConstatntData(UserConstantEntity userConstantEntity) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(userConstantEntity);
        realm.commitTransaction();
    }

    public void updateUserConstatntProfile(final String fbaProfileUrl) {

        try {
            final UserConstantEntity entity = realm.where(UserConstantEntity.class).findFirst();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    //loginResponseEntity.setFBAProfileUrl("http://qa.mgfm.in/" + fbaProfileUrl);
                    entity.setLoansendphoto(fbaProfileUrl);
                }
            });
        } catch (Exception ex) {

        }
    }

    //endregion

    //region term

    public List<String> getPremYearList() {
        hashmapPremTerm = new LinkedHashMap<String, Integer>();
        MapPolicyTerm();
        return new ArrayList<String>(hashmapPremTerm.keySet());
    }

    public int getPremYearID(String cityName) {
        hashmapPremTerm = new LinkedHashMap<String, Integer>();
        MapPolicyTerm();
        if (hashmapPremTerm.get(cityName) != null) {
            return hashmapPremTerm.get(cityName);
        } else {
            return 0;
        }

    }

    public String getPremYearName(int cityID) {
        hashmapPremTerm = new LinkedHashMap<String, Integer>();
        MapPolicyTerm();
        String HealthCityName = "";
        for (Map.Entry<String, Integer> item : hashmapPremTerm.entrySet()) {
            if (item.getValue() == cityID) {
                HealthCityName = item.getKey();
                break;
            }
        }

        return HealthCityName;
    }

    public void MapPolicyTerm() {
        hashmapPremTerm.put("5 YEARS", 5);
        hashmapPremTerm.put("6 YEARS", 6);
        hashmapPremTerm.put("7 YEARS", 7);
        hashmapPremTerm.put("8 YEARS", 8);
        hashmapPremTerm.put("9 YEARS", 9);
        hashmapPremTerm.put("10 YEARS", 10);

        hashmapPremTerm.put("11 YEARS", 11);
        hashmapPremTerm.put("12 YEARS", 12);
        hashmapPremTerm.put("13 YEARS", 13);
        hashmapPremTerm.put("14 YEARS", 14);
        hashmapPremTerm.put("15 YEARS", 15);
        hashmapPremTerm.put("16 YEARS", 16);
        hashmapPremTerm.put("17 YEARS", 17);
        hashmapPremTerm.put("18 YEARS", 18);
        hashmapPremTerm.put("19 YEARS", 19);
        hashmapPremTerm.put("20 YEARS", 20);

        hashmapPremTerm.put("21 YEARS", 21);
        hashmapPremTerm.put("22 YEARS", 22);
        hashmapPremTerm.put("23 YEARS", 23);
        hashmapPremTerm.put("24 YEARS", 24);
        hashmapPremTerm.put("25 YEARS", 25);
        hashmapPremTerm.put("26 YEARS", 26);
        hashmapPremTerm.put("27 YEARS", 27);
        hashmapPremTerm.put("28 YEARS", 28);
        hashmapPremTerm.put("29 YEARS", 29);
        hashmapPremTerm.put("30 YEARS", 30);

        hashmapPremTerm.put("31 YEARS", 31);
        hashmapPremTerm.put("32 YEARS", 32);
        hashmapPremTerm.put("33 YEARS", 33);
        hashmapPremTerm.put("34 YEARS", 34);
        hashmapPremTerm.put("35 YEARS", 35);
        hashmapPremTerm.put("36 YEARS", 36);
        hashmapPremTerm.put("37 YEARS", 37);
        hashmapPremTerm.put("38 YEARS", 38);
        hashmapPremTerm.put("39 YEARS", 39);
        hashmapPremTerm.put("40 YEARS", 40);
        hashmapPremTerm.put("Max Policy Term", 50);


    }
    //endregion

    //region KotakPL

    public List<String> getKotakPLCityList() {
        hashmapKotakPLCity = new LinkedHashMap<String, Integer>();
        MapKotakPLCityList();
        return new ArrayList<String>(hashmapKotakPLCity.keySet());
    }

    public int getKotakPLCityCode(String cityName) {
        hashmapKotakPLCity = new LinkedHashMap<String, Integer>();
        MapKotakPLCityList();
        if (hashmapKotakPLCity.get(cityName) != null) {
            return hashmapKotakPLCity.get(cityName);
        } else {
            return 0;
        }

    }

    public String getKotakPLCityName(int cityID) {
        hashmapKotakPLCity = new LinkedHashMap<String, Integer>();
        MapKotakPLCityList();
        String HealthCityName = "";
        for (Map.Entry<String, Integer> item : hashmapKotakPLCity.entrySet()) {
            if (item.getValue() == cityID) {
                HealthCityName = item.getKey();
                break;
            }
        }

        return HealthCityName;
    }

    public void MapKotakPLCityList() {
        hashmapKotakPLCity.put("Office City", 0);
        hashmapKotakPLCity.put("Ahmedabad", 2002);
        hashmapKotakPLCity.put("Bangalore", 2004);
        hashmapKotakPLCity.put("Baroda", 707);
        hashmapKotakPLCity.put("Kolkata", 64);
        hashmapKotakPLCity.put("Chandigarh", 9);
        hashmapKotakPLCity.put("Chennai", 21);
        hashmapKotakPLCity.put("Cochin", 241);
        hashmapKotakPLCity.put("Gurgaon", 7);
        hashmapKotakPLCity.put("New Delhi", 318);
        hashmapKotakPLCity.put("Hyderabad", 15);
        hashmapKotakPLCity.put("Jaipur", 100);
        hashmapKotakPLCity.put("Mumbai", 25);
        hashmapKotakPLCity.put("Pune", 26);
        hashmapKotakPLCity.put("Nagpur", 135);
        hashmapKotakPLCity.put("Surat", 190);


    }
    //endregion

    //region HDFC personal loan bank

    public String gethdfcplbankbranchrList(String addonName) {
        hdfcpersonalloanbankbranch = new LinkedHashMap<String, String>();
        Maphdfcpersonalloanbankbranch();
        return hdfcpersonalloanbankbranch.get(addonName);
    }

    public String gethdfcplbankbranchrListName(String bankName) {
        hdfcpersonalloanbankbranch = new HashMap<String, String>();
        Maphdfcpersonalloanbankbranch();
        String AddOnName = "";
        for (Map.Entry<String, String> item : hdfcpersonalloanbankbranch.entrySet()) {
            if (item.getValue().matches(bankName)) {
                AddOnName = item.getKey();
                break;
            }
        }

        return AddOnName;
    }

    public List<String> gethdfcpersonalloanbankbranchlist() {
        hdfcpersonalloanbankbranch = new LinkedHashMap<String, String>();
        Maphdfcpersonalloanbankbranch();
        return new ArrayList<String>(hdfcpersonalloanbankbranch.keySet());
    }

    public void Maphdfcpersonalloanbankbranch() {
        hdfcpersonalloanbankbranch.put("Branch Location", "");
        hdfcpersonalloanbankbranch.put("Ahmedabad", "AhmedabadOpen - BEU");
        hdfcpersonalloanbankbranch.put("Bangalore-PL", "BangaloreOpen - BEU");
        hdfcpersonalloanbankbranch.put("Bangalore-BL", "BangaloreOpen - BEU");
        hdfcpersonalloanbankbranch.put("Bhuvaneshwar", "bhubeneswaropen-beu");
        hdfcpersonalloanbankbranch.put("Chandigarh", "chandigarhopen_beu");
        hdfcpersonalloanbankbranch.put("Chennai", "Chennai Open BEU");
        hdfcpersonalloanbankbranch.put("Chennai-BL", "Chennai Open BEU");
        hdfcpersonalloanbankbranch.put("Cochin", "CochinOpen - BEU");
        hdfcpersonalloanbankbranch.put("Coimbatore", "Coimbatore open- BEU");
        hdfcpersonalloanbankbranch.put("Delhi-PL", "Delhiopen1-beu");
        hdfcpersonalloanbankbranch.put("Delhi-BL", "DelhiOpen - BEU");
        hdfcpersonalloanbankbranch.put("Hyderabad", "HyderabadOpen - BEU");
        hdfcpersonalloanbankbranch.put("Hyderabad-BL", "HyderabadOpen - BEU");
        hdfcpersonalloanbankbranch.put("Indore", "Indore Open - BEU");
        hdfcpersonalloanbankbranch.put("Jaipur", "JaipurOpen - BEU");
        hdfcpersonalloanbankbranch.put("Kolkata", "kolkattaopen-beu");
        hdfcpersonalloanbankbranch.put("Lucknow", "Lucknow_openbeu");
        hdfcpersonalloanbankbranch.put("Mumbai", "MumbaiOpen - BEU");
        hdfcpersonalloanbankbranch.put("Mumbai-BL", "MumbaiOpen - BEU");
        hdfcpersonalloanbankbranch.put("Nagpur", "Nagpur Open - BEU");
        hdfcpersonalloanbankbranch.put("Nellore", "Nellore Open - BEU");
        hdfcpersonalloanbankbranch.put("Pune", "PuneOpen - BEU");
        hdfcpersonalloanbankbranch.put("Rajahmundry", "Rajahmundry Open - BEU");
        hdfcpersonalloanbankbranch.put("Vijaywada", "Vijayawadaopen-BEU");
        hdfcpersonalloanbankbranch.put("Vizag", "Vizagopen-BEU");


    }

    //endregion

    //region kotak pl employer namer

    public List<String> getEmplyerNAmeList() {
        List<String> listCity = new ArrayList<>();
        // List<ModelMasterEntity> listModelMaster = dbController.getMasterModel();
        List<KotakPLEmployerNameEntity> list = realm.where(KotakPLEmployerNameEntity.class).findAll();

        for (int i = 0; i < list.size(); i++) {
            KotakPLEmployerNameEntity entity = list.get(i);
            String cityName = entity.getEmployername();
            listCity.add(cityName);
        }

        return listCity;
    }


    public String getEmployerCategory(String employername) {
        KotakPLEmployerNameEntity entity = realm.where(KotakPLEmployerNameEntity.class).equalTo("employername", employername).findFirst();
        if (entity != null) {
            return entity.getFinal_category();
        }
        return "";
    }

    //endregion

    //region Insurance subtype

    public List<InsuranceSubtypeEntity> getInsuranceSubTypeList(int vehicle_id, String neworrenew) {

        List<InsuranceSubtypeEntity> list = new ArrayList<>();
        list = realm.where(InsuranceSubtypeEntity.class)
                .equalTo("vehicle_id", vehicle_id)
                .equalTo("neworrenew", neworrenew)
                .distinct("vehicleinsubtypeid");

        return list;
    }


    //endregion

    //region Insurance subtype

    public List<ContactlistEntity> getContactList() {

        List<ContactlistEntity> list = new ArrayList<>();
        list = realm.where(ContactlistEntity.class).findAll();
        return list;
    }


    //endregion


    //region MultoLanguage

    public List<MultiLangEntity> getMultiLangList() {

        List<MultiLangEntity> list = new ArrayList<>();
        list = realm.where(MultiLangEntity.class).findAll();
        return list;
    }

    public boolean isMultiLangExist() {

        MultiLangEntity multiLangEntity = realm.where(MultiLangEntity.class).findFirst();
        if (multiLangEntity != null) {
            return true;
        }
        return false;

    }

    public String getLangData(String langCode, String prodKey) {

        MultiLangEntity entity = realm.where(MultiLangEntity.class).equalTo("Keyname", prodKey).findFirst();

        if (entity == null) {
            return "";
        }

        if (!langCode.isEmpty()) {

            switch (langCode) {

                case "English":
                    // English
                    return entity.getEnglish();


                case "Hindi":

                    return entity.getHindi();

                case "Marathi":
                    return entity.getMarathi();

                case "Gujrathi":

                    return entity.getGujrathi();


                default:
                    return entity.getEnglish();

            }

        }

        return "";
    }


//    public String getLanguageData(String langCode, String langKey) {
//
//        // RblCityEntity entity = realm.where(RblCityEntity.class).equalTo("CityName", RblCityName).findFirst();
//        if (!langCode.isEmpty()) {
//
//            DashboardEntity entity = realm.where(DashboardEntity.class).equalTo("Kname", langKey).findFirst();
//
//            if (entity != null) {
//
//                if (langCode.equals("En")) {
//
//                    return entity.getETitle();
//                } else if (langCode.equals("Hi")) {
//
//                    return entity.getHTitle();
//                }
//                if (langCode.equals("Ma")) {
//
//                    return entity.getMTitle();
//                }
//                if (langCode.equals("Gu")) {
//
//                    return entity.getGTitle();
//                }
//
//            }
//        }
//
//        return "";
//    }
//
//
//    public boolean saveMultiLanguage(MultilanguageResponse response) {
//
//
//        if (response != null && response.getMasterData() != null) {
//
//            List<MultilanguageResponse.MasterDataBean.DashboardLanguageEntity> list = response.getMasterData().getDashboard();
//
//            List<DashboardEntity> dashboardEntities = new ArrayList<DashboardEntity>();
//
//            for (int i = 0; i < list.size(); i++) {
//
//                MultilanguageResponse.MasterDataBean.DashboardLanguageEntity mItem = list.get(i);
//
//                String type = "";
//                if (mItem.getKname().equals("FinmartExclutitle") || mItem.getKname().equals("PrivateCartitle")
//                        || mItem.getKname().equals("TwoWheelertitle") || mItem.getKname().equals("CommVehtitle")) {
//                    type = "INSURANCE";
//                }
//
//
////                dashboardEntities.add(new DashboardEntity("INSURANCE", 1, mContext.getResources().getString(R.string.dash_private_car), "Best quotes for Private Car Insurance of your customers with instant policy.", R.drawable.private_car));
////                dashboardEntities.add(new DashboardEntity("INSURANCE", 10, mContext.getResources().getString(R.string.dash_two_wheeler), "Best quotes for Two Wheeler Insurance of your customers with instant policy.", R.drawable.two_wheeler));
////                dashboardEntities.add(new DashboardEntity("INSURANCE", 3, "HEALTH INSURANCE", "Get quotes, compare benefits and buy online from top Health Insurance companies.", R.drawable.health_insurance));
////                dashboardEntities.add(new DashboardEntity("INSURANCE", 12, "LIFE INSURANCE", "Get quotes, compare benefits and buy online from top Life Insurance companies.", R.drawable.life_insurance));
////                dashboardEntities.add(new DashboardEntity("INSURANCE", 16, "REQUEST OFFLINE QUOTES", "Get offline quotes.", R.drawable.offlineportal));
////
//
//                int productID = 0;
//
//                switch (mItem.getKname()) {
//
//                    case "FinmartExclutitle":
//                        productID = 17;
//                        break;
//                    case "PrivateCartitle":
//                        productID = 1;
//                        break;
//                    case "TwoWheelertitle":
//                        productID = 10;
//                        break;
//                    case "CommVehtitle":
//                        productID = 12;
//                        break;
//
//                }
//
//
//                DashboardEntity entity = new DashboardEntity(type, productID, mItem.getETitle(), mItem.getEDesc(),
//                        R.drawable.private_car, mItem.getKname(), mItem.getETitle(), mItem.getHTitle(),
//                        mItem.getMTitle(), mItem.getGTitle(), mItem.getEDesc(), mItem.getHDesc(),
//                        mItem.getMDesc(), mItem.getGDesc());
//
//                dashboardEntities.add(entity);
//
//            }
//
//        }
//
//        return false;
//    }

    //endregion
}
