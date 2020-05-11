package com.example.mapfragmenttest.ui.map;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mapfragmenttest.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // 지도 객체 받아오기
        com.naver.maps.map.MapFragment mapFragment = (com.naver.maps.map.MapFragment) getChildFragmentManager().findFragmentById(R.id.MarkerMap);
        if (mapFragment == null) {
            mapFragment = com.naver.maps.map.MapFragment.newInstance();
            getChildFragmentManager().beginTransaction().add(R.id.MarkerMap, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        return v;
    }

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        //마커 클릭 리스너
        Overlay.OnClickListener listener = new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                overlay.setMap(null);   //마커 삭제
                return true;
            }
        };

        // 마커 객체 생성
        Marker marker = new Marker();

        // 마커 위치 지정
        marker.setPosition(new LatLng(37.5670135, 126.9783740));

        // 마커 객체를 지도에 표시
        marker.setMap(naverMap);

        // 마커 클릭 이벤트
        marker.setOnClickListener(listener);

        // 지도 클릭 이벤트
        naverMap.setOnMapClickListener((point, coord) -> {
            Marker m = new Marker(new LatLng(coord.latitude, coord.longitude));
            m.setMap(naverMap);
            m.setOnClickListener(listener);
        });
    }
}
