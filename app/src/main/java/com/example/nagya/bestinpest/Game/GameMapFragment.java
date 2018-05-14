package com.example.nagya.bestinpest.Game;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nagya.bestinpest.Game.item.BoardTile;
import com.example.nagya.bestinpest.Game.view.BudapestMap;
import com.example.nagya.bestinpest.R;
import com.example.nagya.bestinpest.network.RouteNetwork.RouteApiInteractor;
import com.example.nagya.bestinpest.network.RouteNetwork.item.JunctionRestItem;
import com.example.nagya.bestinpest.network.RouteNetwork.item.JunctionsWrapper;
import com.example.nagya.bestinpest.network.RouteNetwork.item.Route;
import com.example.nagya.bestinpest.network.RouteNetwork.item.RouteWrapper;
import com.example.nagya.bestinpest.network.RouteNetwork.item.Stop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions;
import org.osmdroid.views.overlay.simplefastpoint.SimplePointTheme;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameMapFragment extends Fragment {

    BudapestMap map= null;
    static final int MARKER = 1;
    static final int LINES = 1;

    RouteApiInteractor routeApiInteractor;
    List<BoardTile> tiles;
    List<JunctionRestItem> junctionRestItems;
    List<Polyline> polylines;
    List<Route> routes;

    public GameMapFragment() {
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context ctx = getActivity().getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        View view =  inflater.inflate(R.layout.fragment_game_map, container, false);
        map = view.findViewById(R.id.game_map);
        routeApiInteractor= new RouteApiInteractor(getContext());


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        routeApiInteractor.getRoutes();
        routeApiInteractor.getJunctoins();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onResume(){
        super.onResume();

        map.onResume();
    }

    public void onPause(){
        super.onPause();

        map.onPause();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JunctionsWrapper event) {

        tiles = makeTiles(junctionRestItems = event.junctions);
        if(MARKER==1){
            drawMakersV2();
            drawStops();
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRoutes(RouteWrapper event) {
        routes= event.routes;
        if(LINES==1){
            drawLines();
        }


    }


    private List<BoardTile> makeTiles(List<JunctionRestItem> junctionRestItemList){
        List<BoardTile> returnList= new ArrayList<>();

        for (JunctionRestItem junctionRestItem : junctionRestItemList){
            double lon = 0;
            double lat = 0;

            for (Stop stop: junctionRestItem.getStops()){
                lon+=stop.getLon();
                lat+=stop.getLat();
            }
            returnList.add(new BoardTile(junctionRestItem.getId(),
                    junctionRestItem.getName(),
                    junctionRestItem.getStops().size(),
                    lat/junctionRestItem.getStops().size(),
                    lon/junctionRestItem.getStops().size()
            ));

        }
        return returnList;
    }



    private void drawMakersV2() {
        List<GeoPoint> geoPoints = new ArrayList<>();
        for (BoardTile boardTile : tiles) {
            Marker.ENABLE_TEXT_LABELS_WHEN_NO_IMAGE = true;
            Marker m = new Marker(map);
            Drawable drawable = getResources().getDrawable(R.drawable.criminal_hat);
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            m.setIcon(null);
            m.setTitle(boardTile.getName());

            m.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker, MapView mapView) {
                    Log.e("CLICK!", marker.getTitle());
                    return false;
                }
            });
            m.setTitle(boardTile.getName());
            GeoPoint g = new GeoPoint(boardTile.getLat(),boardTile.getLon());
            m.setPosition(g);
            map.getOverlays().add(m);
            //Log.e("asd","asd");
            geoPoints.add(g);


        }

    }


    private void drawLines(){


        polylines = new ArrayList<>();

        for(Route route : routes){
            //Marker m = new Marker(map);
            //m.setIcon(null);
            GeoPoint departure =new GeoPoint(route.getDeparture().getLat(),route.getDeparture().getLon());
            GeoPoint arrival =new GeoPoint(route.getArrival().getLat(),route.getArrival().getLon());
            List<GeoPoint> geoPoints = new ArrayList<>();
            geoPoints.add(departure);
            geoPoints.add(arrival);
            //m.setPosition(g);
            //map.getOverlays().add(m);
            Log.e("asd","asd");
            //geoPoints.add(g);
            Polyline line = new Polyline();
            line.setPoints(geoPoints);
            line.setColor(Color.parseColor("#"+route.getRelations().get(0).getBackgroundColor()));
            map.getOverlayManager().add(line);
            //polylines.add(line);
        }

    }


    private void drawStops() {
        List<IGeoPoint> geoPoints = new ArrayList<>();


        for (JunctionRestItem junctionRestItem : junctionRestItems) {
            for (Stop stop: junctionRestItem.getStops()){
                geoPoints.add(new LabelledGeoPoint(stop.getLat(), stop.getLon()
                ));

            }

        }

        SimplePointTheme pt = new SimplePointTheme(geoPoints, true);


        Paint textStyle = new Paint();
        textStyle.setStyle(Paint.Style.FILL);
        textStyle.setColor(Color.parseColor("#0000ff"));
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextSize(24);

        SimpleFastPointOverlayOptions opt = SimpleFastPointOverlayOptions.getDefaultStyle()
                .setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MAXIMUM_OPTIMIZATION)
                .setRadius(7).setIsClickable(true).setCellSize(15).setTextStyle(textStyle);
        opt.setSelectedPointStyle(textStyle);
        final SimpleFastPointOverlay sfpo = new SimpleFastPointOverlay(pt, opt);

        sfpo.setOnClickListener(new SimpleFastPointOverlay.OnClickListener() {
            @Override
            public void onClick(SimpleFastPointOverlay.PointAdapter points, Integer point) {
                Toast.makeText(map.getContext()
                        , "You clicked " + ((LabelledGeoPoint) points.get(point)).getLabel()
                        , Toast.LENGTH_SHORT).show();
            }
        });

        map.getOverlays().add(sfpo);

    }



}
