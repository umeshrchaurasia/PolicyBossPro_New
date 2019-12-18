package com.datacomp.magicfinmart;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BaseFragment extends Fragment {
    PopUpListener popUpListener;
    ProgressDialog dialog;

    public BaseFragment() {

    }

    public static Date stringToDate(SimpleDateFormat pattern, String dateToconvert) {
        Date date = new Date();
        try {
            date = pattern.parse(dateToconvert);
        } catch (ParseException e) {

        }
        return date;
    }

    public static Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    public static String getYYYYMMDDPattern(String dateCal) {

        String dateSelected = "";
        if (dateCal.equals("")) {
            return "";
        }
        long select_milliseconds = 0;
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");

        Date d = null;
        try {
            d = f.parse(dateCal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        select_milliseconds = d.getTime();

        Date date = new Date(select_milliseconds); //Another date Formate ie yyyy-mm-dd
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        dateSelected = df2.format(date);
        return dateSelected;
    }

    public String getDDMMYYYPattern(String dateCal, String datePattern) {

        String dateSelected = "";
        if (dateCal.equals("")) {
            return "";
        }
        long select_milliseconds = 0;
        SimpleDateFormat f = new SimpleDateFormat(datePattern);

        Date d = null;
        try {
            d = f.parse(dateCal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        select_milliseconds = d.getTime();

        Date date = new Date(select_milliseconds); //Another date Formate ie yyyy-mm-dd
        SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        dateSelected = df2.format(date);
        return dateSelected;
    }


    public void registerPopUp(PopUpListener popUpListener) {
        this.popUpListener = popUpListener;
    }

    public void cancelDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public void showDialog() {
        showDialog("Loading...");
    }

    public void showDialog(String msg) {
        if (dialog == null)
            dialog = ProgressDialog.show(getActivity(), "", msg, true);
        else {
            if (!dialog.isShowing())
                dialog = ProgressDialog.show(getActivity(), "", msg, true);
        }

    }

    public void sendSms(String mobNumber) {
        try {
            mobNumber = mobNumber.replaceAll("\\s", "");
            mobNumber = mobNumber.replaceAll("\\+", "");
            mobNumber = mobNumber.replaceAll("-", "");
            mobNumber = mobNumber.replaceAll(",", "");
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", mobNumber, null)));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Invalid Number", Toast.LENGTH_SHORT).show();
        }

    }

    public void dialNumber(String mobNumber) {
        try {
            mobNumber = mobNumber.replaceAll("\\s", "");
            mobNumber = mobNumber.replaceAll("\\+", "");
            mobNumber = mobNumber.replaceAll("-", "");
            mobNumber = mobNumber.replaceAll(",", "");
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + mobNumber));
            startActivity(callIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Invalid Number", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isValidePhoneNumber(EditText editText) {
        String phoneNumberPattern = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
        String phoneNumberEntered = editText.getText().toString().trim();
        return !(phoneNumberEntered.isEmpty() || !phoneNumberEntered.matches(phoneNumberPattern));
    }

    public static boolean isValidVehicle(EditText editText) {

        String vehiclePattern = "^[A-Z]{2}[0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$";

        String strVehicle = editText.getText().toString().trim();

        return !(strVehicle.isEmpty()) && !strVehicle.matches(vehiclePattern);

    }

    public static boolean isValideEmailID(EditText editText) {
        String emailEntered = editText.getText().toString().trim();
        return !(emailEntered.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailEntered).matches());
    }

    public static boolean isEmpty(EditText editText) {
        String text = editText.getText().toString().trim();
        return !(text.isEmpty());
    }

    public static boolean validatePhoneNumber(EditText editText) {
        String phoneNumberPattern = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
        String phoneNumberEntered = editText.getText().toString().trim();
        if (phoneNumberEntered.isEmpty() || !phoneNumberEntered.matches(phoneNumberPattern)) {
            return false;
        }
        return true;
    }

    public static boolean isValidPan(String Pan) {
//        String rx = "/[A-Z]{5}[0-9]{4}[A-Z]{1}$/";
        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        Matcher matcher = pattern.matcher(Pan);
        if (matcher.matches()) {
            return true;

        } else {
            return false;
        }
    }


    public String getDateFromAge(int age) {
        Calendar cal = Calendar.getInstance();
        int year = age;
        cal.add(Calendar.YEAR, -year);
        return new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
    }

    public String getNumbeFormatCommaRuppee(String strAmount) {
        try {


            //  DecimalFormat formatter  = new DecimalFormat("#,###,###");
            if (strAmount.trim().length() == 0) {
                return strAmount;
            } else if (strAmount.toUpperCase().contains("NIL")) {
                return strAmount;
            } else if (strAmount.toUpperCase().contains("RS.")) {
                String strtemp = strAmount.substring(strAmount.toUpperCase().indexOf(".") + 1, strAmount.length()).toString().trim();

                // return "Rs. "+ formatter.format(Long.valueOf(strtemp.toString().trim()));
                return "\u20B9" + " " + getIndianCurrencyFormat(strtemp);

            } else {
                return "\u20B9" + " " + getIndianCurrencyFormat(strAmount);
            }


        } catch (Exception ex) {
            return strAmount;
        }


    }

    public String getNumbeFormatComma(String strAmount) {
        try {

            if (strAmount.trim().length() == 0) {
                return strAmount;
            }
            if (strAmount.toUpperCase().contains("RS.")) {
                String strtemp = strAmount.substring(strAmount.toUpperCase().indexOf(".") + 1, strAmount.length()).toString().trim();

                return getIndianCurrencyFormat(strtemp);

            } else {
                return getIndianCurrencyFormat(strAmount);
            }


        } catch (Exception ex) {
            return strAmount;
        }


    }

    public String getIndianCurrencyFormat(String amount) {
        StringBuilder stringBuilder = new StringBuilder();
        char amountArray[] = amount.toCharArray();
        int a = 0, b = 0;
        for (int i = amountArray.length - 1; i >= 0; i--) {
            if (a < 3) {
                stringBuilder.append(amountArray[i]);
                a++;
            } else if (b < 2) {
                if (b == 0) {
                    stringBuilder.append(",");
                    stringBuilder.append(amountArray[i]);
                    b++;
                } else {
                    stringBuilder.append(amountArray[i]);
                    b = 0;
                }
            }
        }
        return stringBuilder.reverse().toString();
    }

    public int getAgeFromDate(String birthdate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Calendar bithDate = Calendar.getInstance();
            bithDate.setTime(dateFormat.parse(birthdate));
            Calendar today = Calendar.getInstance();
            int curYear = today.get(Calendar.YEAR);
            int dobYear = bithDate.get(Calendar.YEAR);

            return curYear - dobYear;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public interface PopUpListener {

        void onPositiveButtonClick(Dialog dialog, View view);

        void onCancelButtonClick(Dialog dialog, View view);
    }

    public void openPopUp(final View view, String title, String desc, String positiveButtonName, boolean isCancelable) {
        try {
            final Dialog dialog;
            dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_common_popup);

            TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
            tvTitle.setText(title);
            TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);
            tvOk.setText(positiveButtonName);
            TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
            txtMessage.setText(desc);
            ImageView ivCross = (ImageView) dialog.findViewById(R.id.ivCross);

            dialog.setCancelable(isCancelable);
            dialog.setCanceledOnTouchOutside(isCancelable);

            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = lp.MATCH_PARENT;  // Width
            lp.height = lp.WRAP_CONTENT; // Height
            dialogWindow.setAttributes(lp);

            dialog.show();
            tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    popUpListener.onPositiveButtonClick(dialog, view);
                }
            });

            ivCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    popUpListener.onCancelButtonClick(dialog, view);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAlert(String strBody) {
        try {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
            builder.setTitle("Finmart");

            builder.setMessage(strBody);
            String positiveText = "Ok";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            final android.support.v7.app.AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Please try again..", Toast.LENGTH_SHORT).show();
        }
    }


    public int dateDifferenceInDays(Date startDate, Date endDate) {
        return (int) ((startDate.getTime() - endDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    public void setLanguage( String langType, TextView tv) {

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/english.ttf");


        switch (langType) {

            case "English":
                // English
                typeface = Typeface.createFromAsset(getActivity().getAssets(),
                        "fonts/english.ttf");
                break;

            case "Hindi":
//                typeface = Typeface.createFromAsset(getActivity().getAssets(),
//                        "fonts/hindi.ttf");

                typeface = Typeface.createFromAsset(getActivity().getAssets(),
                        "fonts/aparaj.ttf");
                break;

            case "Marathi":
                typeface = Typeface.createFromAsset(getActivity().getAssets(),
                        "fonts/marathi.ttf");
                break;

            case "Gujrathi":
                typeface = Typeface.createFromAsset(getActivity().getAssets(),
                        "fonts/gujrati.ttf");
                break;


            default:
                typeface = Typeface.createFromAsset(getActivity().getAssets(),
                        "fonts/english.ttf");
        }


        tv.setTypeface(typeface);


    }
}
