package geethika608.nearbyplace;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class GetPlace extends Fragment {


    public GetPlace() {
        // Required empty public constructor
    }

    GPSTracker gps;

    public String TAG = "EventsandLocations";


    CoordinatorLayout root;
    View view;
    NoPaddingCardView showlist;
    int height;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get_place, container, false);


        root = (CoordinatorLayout) view.findViewById(R.id.root);

        showlist = (NoPaddingCardView) view.findViewById(R.id.showlist);
        showlist.setVisibility(View.GONE);
        showlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                showlist.setVisibility(View.GONE);
            }
        });
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        height = size.y;

        CallFrist();
        return view;
    }

    //first time call
    public void CallFrist() {
        int isInternetPresent = NetworkUtil.getConnectivityStatus(getActivity());

        if (isInternetPresent != 0) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("we need location access to show you near by grounds and events");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sv();
                        Getpermission(permissions, 1);
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                setup();
            }
        }
    }

    //send permission request
    private void Getpermission(String[] permissions, int PERMISSION_REQUEST) {
        requestPermissions(permissions,
                PERMISSION_REQUEST);
    }


    //grant permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == 1) {

            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                setup();
            } else {
                new ShowSnackBar(root, "Please grant permission to access location");
            }
        }
    }


    public void setup() {
        gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()) {

            GetLoc(gps.getLatitude(), gps.getLongitude());

        } else {
            Toast.makeText(getActivity(), "Please enable GPS", Toast.LENGTH_LONG).show();

        }
    }

    JSONArray result;

    public void GetLoc(final double latit, final double longit) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("latitude", latit);
            jsonObject.put("longitude", longit);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new RESTRequest(getActivity(), root, "snack").POST("get_nearby_stadium", "/get_nearby_stadium", jsonObject, new RESTRequest.RESTAPI() {
            @Override
            public void onSuccess(JSONObject response, boolean isSuccess) {
                if (isSuccess) {

                    try {
                        result = response.getJSONObject("data").getJSONArray("results");
                        Bundle args = new Bundle();
                        args.putString("lat", String.valueOf(gps.getLatitude()));
                        args.putString("lang", String.valueOf(gps.getLongitude()));
                        args.putString("location", result.toString());
                        FragmentTransaction mTransaction = getChildFragmentManager()
                                .beginTransaction();
                        //-----initialize map fragment------//
                        SupportMapFragment mFRaFragment = new Places();
                        mFRaFragment.setArguments(args);
                        mTransaction.add(R.id.map_layout, mFRaFragment);
                        mTransaction.commit();

                        try {
                            MapsInitializer.initialize(getActivity());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ShowLocationList();
                }

            }
        });
    }

    public void Back() {
        mBottomSheetBehavior.setPeekHeight(0);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        showlist.setVisibility(View.VISIBLE);
    }

    public void Navigate(double latd, double lond) {

        String url = "http://maps.google.com/maps?saddr=" + latd + "," + lond + "&daddr=" + latd + "," + lond;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private BottomSheetBehavior mBottomSheetBehavior;

    //  BottomSheetDialog mBottomSheetDialog;
    public void ShowLocationList() {
       /* mBottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.Material_App_BottomSheetDialog);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.nearby_staduim_list_layout, null);*/
        // LinearLayout list = (LinearLayout) view.findViewById(R.id.list);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.locations);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(new MyBaseAdapter(result));

        mBottomSheetBehavior = BottomSheetBehavior.from(mRecyclerView);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setPeekHeight(0);
                    showlist.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        mBottomSheetBehavior.setPeekHeight(height / 2);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }


    class MyBaseAdapter extends RecyclerView.Adapter<MyBaseAdapter.MyViewHolder> {


        private JSONArray mDataSet;


        MyBaseAdapter(JSONArray array) {
            mDataSet = array;

        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.location_profile, parent, false));
        }


        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            try {
                holder.name.setText(mDataSet.getJSONObject(position).getString("name"));
                if (mDataSet.getJSONObject(position).has("rating")) {
                    holder.ratingBar.setRating(Float.parseFloat(mDataSet.getJSONObject(position).get("rating").toString()));
                    holder.rating_text.setText(mDataSet.getJSONObject(position).get("rating").toString());
                } else {
                    holder.ratingBar.setRating(0);
                    holder.rating_text.setText("0.0");
                }
                holder.address.setText(mDataSet.getJSONObject(position).getString("vicinity"));
                holder.share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            final String sh_string = mDataSet.getJSONObject(position).getString("name") + "\n" + mDataSet.getJSONObject(position).getString("vicinity") +
                                    "\n \n" +
                                    "http://maps.google.com/maps?q=" + mDataSet.getJSONObject(position).getJSONObject("geometry").getJSONObject("location").getString("lat") + "," +
                                    mDataSet.getJSONObject(position).getJSONObject("geometry").getJSONObject("location").getString("lng")
                                    + "\n \n" + "-Shared from Tourneo";

                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, sh_string);
                            sendIntent.setType("text/plain");
                            startActivity(sendIntent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                holder.navigate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Navigate(mDataSet.getJSONObject(position).getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                                    mDataSet.getJSONObject(position).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        public int getItemCount() {
            return mDataSet.length();
        }


        class MyViewHolder extends RecyclerView.ViewHolder {


            TextView name;
            TextView address;
            RatingBar ratingBar;
            TextView rating_text;
            RelativeLayout navigate;
            RelativeLayout share;
            LinearLayout rating_layout;

            MyViewHolder(View v) {
                super(v);

                name = (TextView) v.findViewById(R.id.name);
                address = (TextView) v.findViewById(R.id.address);
                ratingBar = (RatingBar) v.findViewById(R.id.rating);
                rating_text = (TextView) v.findViewById(R.id.rating_text);
                share = (RelativeLayout) v.findViewById(R.id.share);
                navigate = (RelativeLayout) v.findViewById(R.id.navigate);
                rating_layout = (LinearLayout) v.findViewById(R.id.rating_layout);
            }
        }
    }
}
