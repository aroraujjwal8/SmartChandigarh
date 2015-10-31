package com.smartchd.smartchandigarh.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartchd.smartchandigarh.R;
import com.smartchd.smartchandigarh.data.Constants;
import com.smartchd.smartchandigarh.utils.AlertAdapter;
import com.smartchd.smartchandigarh.utils.HttpManager;
import com.smartchd.smartchandigarh.utils.JsonParser;
import com.smartchd.smartchandigarh.utils.RequestPackage;

import java.util.ArrayList;

public class AlertFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private AlertAdapter alertAdapter;
    private ArrayList<String> alertArrayList;

    public static AlertFragment newInstance(String param1, String param2) {
        AlertFragment fragment = new AlertFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AlertFragment() {
        // Required empty public constructor
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
        View layout_view = inflater.inflate(R.layout.fragment_alert, container, false);
        RecyclerView recyclerView = (RecyclerView)layout_view.findViewById(R.id.alertRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        alertArrayList = new ArrayList<>();
        alertAdapter = new AlertAdapter(getActivity(),alertArrayList);
        recyclerView.setAdapter(alertAdapter);
        AlertTask alertTask = new AlertTask();
        alertTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,createRequestPackage());
        return layout_view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private RequestPackage createRequestPackage(){
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri(Constants.BASE_URL+"show_alert.php");
        requestPackage.setMethod("POST");
        return requestPackage;
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
        public void onFragmentInteraction(Uri uri);
    }

    private class AlertTask extends AsyncTask<RequestPackage,Void,ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(RequestPackage... requestPackages) {
            String content = HttpManager.getData(requestPackages[0]);
            return JsonParser.getAllStrings(content);
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            alertArrayList.clear();
            alertArrayList.addAll(strings);
            alertAdapter.notifyDataSetChanged();
        }
    }
}
