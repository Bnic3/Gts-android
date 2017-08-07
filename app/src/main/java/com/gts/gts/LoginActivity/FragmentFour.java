package com.gts.gts.LoginActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.gts.gts.R;

import org.json.JSONArray;
import org.json.JSONException;

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
    private static String TAG = LoginCustomActivity2.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    public Spinner codeSpinner;
    public Button signupBtn;

    public ArrayList<String> dial_codes;
    public EditText adminphone;
    public EditText ename;
    public EditText adminEmail;

    public String current_dial_codes;



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

        signupBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });

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


        return v;
    }

    public void validateForm(){
        String nameTxt = ename.getText().toString().trim();
        String phoneTxt = sanitizePhone(adminphone.getText().toString().trim());
        String adminEmail ;
    }

    public String sanitizePhone(String phone){
        String phoneText = phone.trim();

        if (String.valueOf(phoneText.charAt(0)).equals("0")){
            phoneText = phoneText.substring(1,phoneText.length());
            Log.i(TAG, "sanitizing");
        }
        phoneText= current_dial_codes+phoneText;
        return phoneText;
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
