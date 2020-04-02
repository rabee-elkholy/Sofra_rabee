package com.androdu.sofra.utils;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Spinner;

import com.androdu.sofra.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yehia on 08/04/2018.
 */

public class Validation {

    private static String STRING_PATTERN = "^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$";
    private static String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static void cleanError(TextInputLayout... textInputLayoutList) {

        for (int i = 0; i < textInputLayoutList.length; i++) {
            textInputLayoutList[i].setErrorEnabled(false);
        }

    }

    public static boolean registerValidator1(Activity activity,
                                             int regionsLength,
                                             EditText... fields) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getText().toString().trim().isEmpty() && i != 4) {
                ToastCreator.onCreateErrorToast(activity, "تأكد من ادخال جميع الحقول!", R.drawable.cancel);
                return false;
            } else if (fields[i].getText().toString().trim().isEmpty() && i == 4 && regionsLength != 0){
                ToastCreator.onCreateErrorToast(activity, "تأكد من ادخال جميع الحقول!", R.drawable.cancel);
                return false;
            }
        }
        if (!validationEmail(fields[1])) {
            ToastCreator.onCreateErrorToast(activity, "تأكد من ادحال بريد صحيح!", R.drawable.cancel);
            return false;
        } else if (fields[5].getText().toString().trim().length() < 6){
            ToastCreator.onCreateErrorToast(activity, "يجب ان تكون كلمة المرور اطول من 5!", R.drawable.cancel);
            return false;
        } else if (!fields[5].getText().toString().trim().equals(fields[6].getText().toString().trim())){
            ToastCreator.onCreateErrorToast(activity, "كلمة المرور غير متطابقة!", R.drawable.cancel);
            return false;
        }
        return true;

    }

    public static boolean validationLength(Activity activity, String text, String errorText) {

        if (text.length() <= 0) {
            ToastCreator.onCreateErrorToast(activity, errorText);
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationLength(EditText text, String errorText) {
        if (text.getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationLength(TextInputLayout text, String errorText) {
        if (text.getEditText().length() <= 0) {
            text.setError(errorText);
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationLength(Activity activity, String text, String errorText, int length) {

        if (text.length() <= length) {
            ToastCreator.onCreateErrorToast(activity, errorText);
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationLength(EditText text, String errorText, int length) {
        if (text.length() <= length) {
            text.setError(errorText);
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationLength(TextInputLayout text, String errorText, int length) {
        if (text.getEditText().length() < length) {
            text.setError(errorText);
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationStringIsCharAndNumber(Activity activity, String text, String errorText) {

        if (!text.matches(STRING_PATTERN)) {
            ToastCreator.onCreateErrorToast(activity, errorText);
            return false;
        } else {
            return true;
        }

    }

    public static boolean validationStringIsCharAndNumber(EditText text, String errorText) {

        if (!text.getText().toString().matches(STRING_PATTERN)) {
            text.setError(errorText);
            return false;
        } else {
            return true;
        }

    }

    public static boolean validationStringIsCharAndNumber(TextInputLayout text, String errorText) {

        if (!text.getEditText().getText().toString().matches(STRING_PATTERN)) {
            text.setError(errorText);
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationStringIsNumber(Activity activity, String text, String errorText) {

        try {
            int convert = Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            ToastCreator.onCreateErrorToast(activity, errorText);
            return false;
        }

    }

    public static boolean validationStringIsNumber(EditText text, String errorText) {

        try {
            int convert = Integer.parseInt(text.getText().toString());
            return true;
        } catch (Exception e) {
            text.setError(errorText);
            return false;
        }

    }

    public static boolean validationStringIsNumber(TextInputLayout text, String errorText) {

        try {
            int convert = Integer.parseInt(text.getEditText().getText().toString());
            return true;
        } catch (Exception e) {
            text.getEditText().setError(errorText);
            return false;
        }
    }

    public static boolean validationEditTextsEmpty(EditText... editTexts) {


        for (int i = 0; i < editTexts.length; i++) {
            if (editTexts[i].getText().toString().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean validationTextInputLayoutListEmpty(String errorText, TextInputLayout... textInputLayoutList) {

        List<Boolean> booleans = new ArrayList<>();

        for (int i = 0; i < textInputLayoutList.length; i++) {
            if (!validationLength(textInputLayoutList[i], errorText)) {
                booleans.add(false);
            } else {
                booleans.add(true);
            }
        }

        if (booleans.contains(false) && !booleans.contains(true)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationSpinnersEmpty(List<Spinner> spinners) {

        List<Boolean> booleans = new ArrayList<>();

        for (int i = 0; i < spinners.size(); i++) {
            if (spinners.get(i).getSelectedItemPosition() == 0) {
                booleans.add(false);
            } else {
                booleans.add(true);
            }
        }

        if (booleans.contains(false) && !booleans.contains(true)) {
            return false;
        } else {
            return true;
        }
    }

//    public static boolean validationAllEmpty(EditText... editTexts, TextInputLayout... textInputLayouts, List<Spinner> spinners, String errorText) {
//
//        if (validationEditTextsEmpty(editTexts, errorText) && validationTextInputLayoutListEmpty(textInputLayouts, errorText)
//                && validationSpinnersEmpty(spinners)) {
//            return true;
//        } else {
//            return false;
//        }
//    }


    public static boolean validationEmail(Activity activity, String email) {

        if (!email.matches(EMAIL_PATTERN)) {
            ToastCreator.onCreateErrorToast(activity, activity.getString(R.string.invalid_email));
            return false;
        } else {
            return true;
        }

    }

    public static boolean validationEmail(EditText email) {

        if (!email.getText().toString().matches(EMAIL_PATTERN)) {
            return false;
        } else {
            return true;
        }

    }

    public static boolean validationEmail(Activity activity, TextInputLayout email) {

        if (!email.getEditText().getText().toString().matches(EMAIL_PATTERN)) {
            email.getEditText().setError(activity.getString(R.string.invalid_email));
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationPassword(Activity activity, String password, int length, String errorText) {

        validationLength(activity, password, errorText, length);
        validationStringIsCharAndNumber(activity, password, errorText);

        return true;
    }

    public static boolean validationPassword(EditText password, int length, String errorText) {


        validationLength(password, errorText, length);
        validationStringIsCharAndNumber(password, errorText);

        return true;
    }

    public static boolean validationPassword(TextInputLayout password, int length, String errorText) {

        if (validationLength(password, errorText, length)) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean validationConfirmPassword(Activity activity, String password, String confirmPassword) {

        if (password.equals(confirmPassword)) {
            return true;
        } else {
            ToastCreator.onCreateErrorToast(activity, activity.getString(R.string.invalid_confirm_password));
            return false;
        }
    }

    public static boolean validationConfirmPassword(Activity activity, EditText password, EditText confirmPassword) {

        if (password.getText().toString().equals(confirmPassword.getText().toString())) {
            return true;
        } else {
            ToastCreator.onCreateErrorToast(activity, "كلمة المرور غير متطابقة", R.drawable.cancel);
            return false;
        }

    }

    public static boolean validationConfirmPassword(Activity activity, TextInputLayout password, TextInputLayout confirmPassword) {

        if (password.getEditText().getText().toString().equals(confirmPassword.getEditText().getText().toString())) {
            return true;
        } else {
            confirmPassword.getEditText().setError(activity.getString(R.string.invalid_confirm_password));
            return false;
        }

    }

}
