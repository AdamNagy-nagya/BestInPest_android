package com.example.nagya.bestinpest.Game.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

/**
 * Created by nagya on 25/04/2018.
 */

public class BudapestMap extends MapView {


    public BudapestMap(Context context, MapTileProviderBase tileProvider, Handler tileRequestCompleteHandler, AttributeSet attrs) {
        super(context, tileProvider, tileRequestCompleteHandler, attrs);
        setupData();
    }

    public BudapestMap(Context context, MapTileProviderBase tileProvider, Handler tileRequestCompleteHandler, AttributeSet attrs, boolean hardwareAccelerated) {
        super(context, tileProvider, tileRequestCompleteHandler, attrs, hardwareAccelerated);
        setupData();
    }

    public BudapestMap(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupData();
    }

    public BudapestMap(Context context) {
        super(context);
        setupData();
    }

    public BudapestMap(Context context, MapTileProviderBase aTileProvider) {
        super(context, aTileProvider);
        setupData();
    }

    public BudapestMap(Context context, MapTileProviderBase aTileProvider, Handler tileRequestCompleteHandler) {
        super(context, aTileProvider, tileRequestCompleteHandler);
        setupData();
    }

    public void setupData(){
        setTileSource(TileSourceFactory.MAPNIK);
        setBuiltInZoomControls(true);
        setMultiTouchControls(true);
        IMapController mapController = getController();
        mapController.setZoom(15);
        GeoPoint startPoint = new GeoPoint(47.495358, 19.057943);
        mapController.setCenter(startPoint);


    }
}
