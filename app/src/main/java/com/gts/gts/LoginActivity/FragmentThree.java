package com.gts.gts.LoginActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gts.gts.Helper.PrefManager;
import com.gts.gts.MainActivity;
import com.gts.gts.NetworkRequests.LoginRequest;
import com.gts.gts.NetworkRequests.OTPverify;
import com.gts.gts.R;
import com.gts.gts.Utility.CustomUtility;
import com.gts.gts.Utility.RequestSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentThree.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentThree extends Fragment implements View.OnClickListener {

    private Vibrator vib;
    public Animation animShake;

    private EditText otp;
    private Button verifyBtn;
    private TextView requestLink;

    public PrefManager pref;
    public ProgressDialog pd;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentThree() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentThree.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentThree newInstance(String param1, String param2) {
        FragmentThree fragment = new FragmentThree();
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
        View v = inflater.inflate(R.layout.fragment_fragment_three, container, false);
        otp = (EditText) v.findViewById(R.id.otpEditText);
        verifyBtn = (Button) v.findViewById(R.id.verifybtn);
        requestLink = (TextView) v.findViewById(R.id.resendTV);
        pref = new PrefManager(getActivity());
        pd= new ProgressDialog(getActivity());

        animShake= AnimationUtils.loadAnimation(getActivity(),R.anim.shake);
        vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        verifyBtn.setOnClickListener(this);
        requestLink.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verifybtn:
                validateForm();
                break;

            case R.id.resendTV:
                resendOtp();
                break;
        }

    }

    public void validateForm(){
        if (otp.getText().toString().isEmpty()) {
            otp.setError("This field cannot be empty");
            shakeField(otp);
            return;
        }
        if (otp.getText().toString().length() < 6) {
            otp.setError("provided OTP is too short ");
            shakeField(otp);
            return;
        }

        verifyOTP();
    }

    public void verifyOTP(){
        CustomUtility.PROGRESSDIALOG(pd,"Verify");
        Log.i(TAG, "i am in verifyOtp function");

        Response.Listener<String> successListener= new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    String message = jsonResponse.getString("message");
                    Log.i(TAG, "Success Status: "+ success);
                    Log.i(TAG, "Message Status: "+ message);


                    // TODO: 8/11/2017  login condition
                    pd.dismiss();
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    if(success){
                        //navigate to main app
                        pref.setIsLoggedIn(true);
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                    }else {
                        shakeField(otp);
                    }


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

            public void displayMessage(String toastString){
                Toast.makeText(getActivity(), toastString, Toast.LENGTH_LONG).show();
            }



        }; //end error listener
        String eid = pref.getEid();
        String phone = pref.getPhone();
        OTPverify request = new OTPverify(eid ,phone ,otp.getText().toString(), successListener,failureListener);
        RequestSingleton.getmInstance(getActivity()).addToRequestQueue(request);

    }// end verification process


    public void resendOtp(){
        final String eid = pref.getEid();
        final String phone = pref.getPhone();
        final String code = pref.getDialCode();

        CustomUtility.PROGRESSDIALOG(pd,"Login");
        Log.i(TAG, "i am in resendOtp function");

        Response.Listener<String> successListener= new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    String message = jsonResponse.getString("message");
                    Log.i(TAG, "Success Status: "+ success);
                    Log.i(TAG, "Message Status: "+ message);
                    Log.i(TAG, "pref Phone: "+ phone);
                    Log.i(TAG, "pref eid: "+ eid);
                    Log.i(TAG, "dial code: "+ code);

                    // TODO: 8/11/2017  login condition
                    pd.dismiss();
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                   /* pd.dismiss();*/
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
                pd.dismiss();
            }
        }; //end error listener

        LoginRequest request = new LoginRequest(eid ,phone ,successListener,failureListener);
        RequestSingleton.getmInstance(getActivity()).addToRequestQueue(request);





    }

  /*  public void displayMessage(String toastString){
        Toast.makeText(getActivity(), toastString, Toast.LENGTH_LONG).show();
    }*/
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction3(uri);
        }
    }

    public void shakeField(EditText et){
        et.setAnimation(animShake);
        et.startAnimation(animShake);
        vib.vibrate(120);

    };

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
        void onFragmentInteraction3(Uri uri);
    }
}
