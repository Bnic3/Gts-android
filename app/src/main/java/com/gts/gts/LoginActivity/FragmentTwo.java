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
import android.widget.Toast;

import com.gts.gts.R;

import org.json.JSONArray;
import org.json.JSONException;

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
    private static String TAG = LoginCustomActivity2.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public Spinner spinner;
    public Button loginBtn;
    public  ArrayList<String> dial_codes;
    public EditText phone;

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
        loginBtn = (Button) v.findViewById(R.id.loginbtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String selection = spinner.getSelectedItem().toString();
               int pos = spinner.getSelectedItemPosition();*/
                //Todo: validate input;
                String s= phone.getText().toString();

                if(s.length()== 0){
                    Toast.makeText(getActivity(), "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    s = sanitizePhone(s);
                    Log.i(TAG, "Navigate to OTP");
                }


                Log.i(TAG, "SELECTION: " +current_dial_codes);
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();

            }
        });

        spinner.setSelection(132);
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
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction2(uri);
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
        void onFragmentInteraction2(Uri uri);
    }
}
