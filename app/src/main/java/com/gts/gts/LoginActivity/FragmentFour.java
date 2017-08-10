package com.gts.gts.LoginActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gts.gts.NetworkRequests.SignupRequest;
import com.gts.gts.R;
import com.gts.gts.Utility.RequestSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentFour.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FragmentFour extends Fragment {
    private static String TAG = "GTS";

    private Vibrator vib;
    public Animation animShake;
    private OnFragmentInteractionListener mListener;
    public Spinner codeSpinner;
    public Button signupBtn;

    public ArrayList<String> dial_codes;
    public EditText adminphone, ename, adminEmail;
    public TextInputLayout nameLayout, emailLayout,phoneLayout;


    public String current_dial_codes;


    // editText Strings
    public String nameTxt;
    public String phoneTxt;
    public String emailText;



    public FragmentFour() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_fragment_four, container, false);

        codeSpinner = (Spinner) v.findViewById(R.id.codespinnerAdmin);
        signupBtn = (Button) v.findViewById(R.id.signupbtn);
        ename = (EditText) v.findViewById(R.id.nameEditText);
        adminphone = (EditText) v.findViewById(R.id.adminPhone);
        adminEmail = (EditText) v.findViewById(R.id.emailEditText);

        nameLayout = (TextInputLayout) v.findViewById(R.id.estateInputLayout);
        emailLayout = (TextInputLayout) v.findViewById(R.id.emailInputLayout);
        phoneLayout = (TextInputLayout) v.findViewById(R.id.phoneInputLayout);

        animShake= AnimationUtils.loadAnimation(getActivity(),R.anim.shake);
        vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);



        codeSpinner.setSelection(0);
        codeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*Toast.makeText(getContext(), dial_codes.get(position), Toast.LENGTH_LONG).show();*/
                current_dial_codes = dial_codes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> items = getCountries("Countrycodes.json");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, R.id.spinnertext,items );
        codeSpinner.setAdapter(adapter);

        signupBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                validateForm();
            }
        });




        return v;
    }

    public void getEditTexts(){
        nameTxt = ename.getText().toString().trim();
        phoneTxt = sanitizePhone(adminphone.getText().toString().trim());
        emailText = adminEmail.getText().toString().trim();
    }
    public void validateForm(){
     getEditTexts();
        if (!checkName()){
            ename.setAnimation(animShake);
            ename.startAnimation(animShake);
            vib.vibrate(120);
            Log.i(TAG, "name is " +nameTxt);
            return;
        }

        if(!checkEmail()){
            adminEmail.setAnimation(animShake);
            adminEmail.startAnimation(animShake);
            vib.vibrate(120);
            Log.i(TAG, "email is " +emailText);
            return;
        }

        if(!checkPhone()){
            adminphone.setAnimation(animShake);
            adminphone.startAnimation(animShake);
            vib.vibrate(120);
            Log.i(TAG, " i am in check phone is " +phoneTxt);

            return;
        }
        Log.i(TAG, "phone is " +phoneTxt);
        nameLayout.setErrorEnabled(false);
        emailLayout.setErrorEnabled(false);
        phoneLayout.setErrorEnabled(false);
        Toast.makeText(getActivity(), "You have been Validated", Toast.LENGTH_SHORT).show();
        //Todo: communicate to server
        signUp();
    }

    public void signUp(){
        getEditTexts();
        Log.i(TAG, "i am in signup");
        Response.Listener<String> successListener= new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    String message = jsonResponse.getString("message");
                    Log.i(TAG, "Success Status: "+ success);
                    Log.i(TAG, "Messaage Status: "+ message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };//end of listener

        Response.ErrorListener failureListener  = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Network issues: cannot reach server");
                Log.e(TAG, error.toString());
            }
        };

                SignupRequest request = new SignupRequest(nameTxt,phoneTxt,emailText,successListener, failureListener);
        RequestSingleton.getmInstance(getActivity()).addToRequestQueue(request);

    }


    private boolean checkName(){
        if(nameTxt.isEmpty()|| nameTxt.length()<=2){
            nameLayout.setErrorEnabled(true);
            nameLayout.setError("Please Enter a Name");
            ename.setError("Valid Input Required");
            Log.i(TAG, "iam in  name empty");
            return false;

        }
        else {
            nameLayout.setErrorEnabled(false);
            return true;
        }

    }

    private boolean checkEmail(){
        String regEx = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
        if(!emailText.isEmpty() && emailText.matches(regEx)){
            emailLayout.setErrorEnabled(false);
             return true;

        }
        else {
            emailLayout.setErrorEnabled(true);
            emailLayout.setError("Please Enter an Email");
            adminEmail.setError("Valid Input Required");
            return false;
        }

    }

    private boolean checkPhone(){
        if(phoneTxt.isEmpty() ){
            phoneLayout.setErrorEnabled(true);
            phoneLayout.setError("Please Enter a Number");
            adminphone.setError("Valid Input Required");
            return false;
           /* */
        }else if(phoneTxt.length()<=5){
            phoneLayout.setErrorEnabled(true);
            phoneLayout.setError("Please Enter a Number");
            adminphone.setError("Valid Input Required");
            return false;
        }
        else {
            phoneLayout.setErrorEnabled(false);
            return true;
        }
    }
    public String sanitizePhone(String mobile){
        /*if (mobile.isEmpty()){
            return "";
        }
        else*/ if(!mobile.isEmpty() && String.valueOf(mobile.charAt(0)).equals("0")) {
            mobile = mobile.substring(1,mobile.length());
            Log.i(TAG, "sanitizing");
            mobile= current_dial_codes+mobile;

        } else{ mobile= current_dial_codes+mobile;}


        return mobile;
    }

    public  ArrayList<String> getCountries(String fileName){

        JSONArray jsonArray = null;
        dial_codes = new ArrayList<String>();
        ArrayList<String> clist = new ArrayList<String>();

        try {
            InputStream is = getResources().getAssets().open(fileName);
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            is.close();
            String json = new String(data,"UTF-8");
            jsonArray = new JSONArray(json);

            if(jsonArray !=null){
                for (int i = 0; i<= jsonArray.length(); i++){
                    String dial_code = jsonArray.getJSONObject(i).getString("dial_code");
                    String country = jsonArray.getJSONObject(i).getString("name");
                    String item = country+" ("+dial_code+")"; //132
                    dial_codes.add(dial_code);
                    clist.add(item);
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return clist;
    } // end getCountries

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int value) {
        if (mListener != null) {
            mListener.onFragmentInteraction4(value);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction4(int value);
    }
}
