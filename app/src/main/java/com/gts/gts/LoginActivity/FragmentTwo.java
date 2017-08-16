package com.gts.gts.LoginActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
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

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gts.gts.Helper.PrefManager;
import com.gts.gts.NetworkRequests.LoginRequest;
import com.gts.gts.R;
import com.gts.gts.Utility.CustomUtility;
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
 * {@link FragmentTwo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTwo extends Fragment  {
    private static String TAG = "GTS";

    private Vibrator vib;
    public Animation animShake;
    public ProgressDialog pd;
    public PrefManager pref;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public Spinner spinner;
    public Button loginBtn;
    public  ArrayList<String> dial_codes;
    public EditText phone,eid;
    public String eidText, phoneText;

    public String current_dial_codes;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentTwo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTwo newInstance(String param1, String param2) {
        FragmentTwo fragment = new FragmentTwo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_two, container, false);

        spinner = (Spinner) v.findViewById(R.id.codespinner);
        phone = (EditText) v.findViewById(R.id.poneEditText);
        eid = (EditText) v.findViewById(R.id.eidEditText);

        loginBtn = (Button) v.findViewById(R.id.loginbtn);
        animShake= AnimationUtils.loadAnimation(getActivity(),R.anim.shake);
        vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        pd = new ProgressDialog(getActivity());
        pref = new PrefManager(getActivity());

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //Todo: validate input;
                    String s= "";
                if(!checkPhone()) {
                    shakeField(phone);
                    return;

                }else{
                    s = sanitizePhone(phone.getText().toString());
                    phoneText= s;
                }
                if(eid.getText().toString().isEmpty()){
                    eid.setError("Please provide an Estate ID");
                    shakeField(eid);
                    return;
                }

                    /*Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();*/
                    login();

            }
        });

        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        spinner.setAdapter(adapter);


        return v;
    }

    public void login(){
        CustomUtility.PROGRESSDIALOG(pd,"Login");
        Log.i(TAG, "i am in login function");

        Response.Listener<String> successListener= new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    String message = jsonResponse.getString("message");
                    Log.i(TAG, "Success Status: "+ success);
                    Log.i(TAG, "Message Status: "+ message);
                    Log.i(TAG, "userinput Phone: "+ phoneText);
                    Log.i(TAG, "Userinput eid: "+ eidText);

                    // TODO: 8/11/2017  login condition
                    pd.dismiss();
                    if(success){
                        // save response info
                        pref.setDialCode(current_dial_codes);
                        pref.setEid(eidText);
                        pref.setPhone(phoneText);

                    // navigate to otp fragment
                        mListener.onFragmentInteraction(2);
                    }

                    else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                    }

                   /* pd.dismiss();*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };//end of listener


        Response.ErrorListener failureListener  = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                vib.vibrate(120);
                Log.i(TAG, "Network issues: cannot reach server");
                Log.e(TAG, error.toString());
                pd.dismiss();
                String json = null;

                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    switch(response.statusCode){
                        case 401:
                            json = new String(response.data);
                            json = CustomUtility.trimMessage(json, "message");
                            if(json != null) displayMessage(json);
                            break;
                    }
                    //Additional cases
                }
            }



        }; //end error listener
        getEditText();
        LoginRequest request = new LoginRequest(eidText,phoneText,successListener,failureListener);
        RequestSingleton.getmInstance(getActivity()).addToRequestQueue(request);

    }

    public void getEditText(){
        eidText = eid.getText().toString().trim();

    }

    public void displayMessage(String toastString){
        Toast.makeText(getActivity(), toastString, Toast.LENGTH_LONG).show();
    }

    public void shakeField(EditText et){
        et.setAnimation(animShake);
        et.startAnimation(animShake);
        vib.vibrate(120);

    };

    private boolean checkPhone(){
        if(phone.getText().toString().isEmpty() ){
            phone.setError("Please enter a number");
            return false;
           /* */
        }else if(phone.getText().toString().length()<=5){
            phone.setError("The input is too short");

            return false;
        }
        else {

            return true;
        }
    }
    public String sanitizePhone(String phone){
        String phoneText = phone.trim();

        if (String.valueOf(phoneText.charAt(0)).equals("0")){
            phoneText = phoneText.substring(1,phoneText.length());
            Log.i(TAG, "sanitizing");
        }
        phoneText= current_dial_codes+phoneText;
        return phoneText.trim();
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
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int value) {
        if (mListener != null) {
            mListener.onFragmentInteraction(value);
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
        void onFragmentInteraction(int value);
    }
}
