package com.example.demo.custommapdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.CustomMapStyleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.demo.R;
import com.example.demo.jumoanimationdemo.SecondActivity;
import com.example.demo.mapdemo.MainActivity;
import com.example.demo.rippleanimationdemo.AppContext;
import com.example.demo.rippleanimationdemo.RipplePosition;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MapActivity extends AppCompatActivity implements AMapLocationListener,
        AMap.OnMarkerClickListener, LocationSource, AMap.OnMapClickListener, GeocodeSearch.OnGeocodeSearchListener {
    //请求权限码
    private static final int REQUEST_PERMISSIONS = 9527;
    //地图
    private MapView mapView;
    //地图控制器
    private AMap aMap = null;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //位置更改监听
    private OnLocationChangedListener mListener;
    //地图的各种UI组件
    private UiSettings mUiSettings;
    //定位图标ui
    private MyLocationStyle myLocationStyle = new MyLocationStyle();;

    //城市
    private String city;
    //城市码
    private String cityCode = null;

    //地理编码搜索
    private GeocodeSearch geocodeSearch;
    //解析成功标识码
    private static final int PARSE_SUCCESS_CODE = 1000;

    //标点列表
    private List<Marker> markerList = new ArrayList<>();

    private List<String> addressList = new ArrayList<>();

    private List<LatLng> latLngList = new ArrayList<>();

    //对应点的坐标
    private float x, y;

    private double startLatitude,startLongitude,endLatitude,endLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //进行合规检查
        MapsInitializer.updatePrivacyShow(this, true, true);
        MapsInitializer.updatePrivacyAgree(this, true);


        try {
            //初始化定位
            initLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addToList();
        try {
            //初始化地图控制器
            initMap(savedInstanceState);
        }catch (AMapException e){
            e.printStackTrace();
            Log.e("报错", e.toString());
        }

        //检查安卓版本
        checkingAndroidVersion();

    }

    private void getLatlng(String s) throws AMapException {
        // name表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode
        GeocodeQuery query = new GeocodeQuery(s, "中国");
        geocodeSearch.getFromLocationNameAsyn(query);
    }

    private void addToList(){
        addressList.add("北京市");
        addressList.add("广东省广州市");
        addressList.add("陕西省西安市");
        addressList.add("福建省福州市");
        addressList.add("重庆市");
    }

    /**
            * 初始化定位
     */
    private void initLocation() throws Exception {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置定位请求超时时间，单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //关闭缓存机制，高精度定位会产生缓存。
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    /**
     * 初始化地图
     *
     * @param savedInstanceState
     */
    private void initMap(Bundle savedInstanceState) throws AMapException {
        mapView = findViewById(R.id.map_view);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
        aMap = mapView.getMap();

        //设置自定义样式
        setCustomMap(this, aMap);
        //设置最小缩放等级为16 ，缩放级别范围为[3, 20]
        aMap.setMinZoomLevel(4);
        aMap.setMaxZoomLevel(10);

        //实例化UiSettings类对象
        mUiSettings = aMap.getUiSettings();
        //隐藏缩放按钮和logo
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setLogoBottomMargin(-150);

        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.img_point));
        // 自定义精度范围的圆形边框颜色  都为0则透明
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        // 自定义精度范围的圆形边框宽度  0 无宽度
        myLocationStyle.strokeWidth(0);
        // 设置圆形的填充颜色  都为0则透明
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));

//        设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);

        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);

        //设置点击监听
        aMap.setOnMapClickListener(this);
        //设置Marker点击事件
        aMap.setOnMarkerClickListener(this);


        //地址解码部分
        //构造对象
        geocodeSearch = new GeocodeSearch(this);
        //设置监听
        geocodeSearch.setOnGeocodeSearchListener(this);

        try {
            for (int i=0;i<addressList.size();i++){
                getLatlng(addressList.get(i));
            }
        }catch (AMapException aMapException){
            aMapException.printStackTrace();
        }
        latLngList.add(new LatLng(39.90403,116.407526));
        latLngList.add(new LatLng(23.129162,113.264434));
        latLngList.add(new LatLng(34.341568,108.940174));
        latLngList.add(new LatLng(26.074507,119.296494));
        latLngList.add(new LatLng(29.563009,106.551556));
//        for (int i=0;i<latLngList.size();i++){
//            if ()
//        }
        addPolyLine(latLngList);

//        Log.e("TAG", latLngList.toString()+latLngList.size());
//
//        initMarker();

//        getLatlng("广东省广州市");


    }


    private void initMarker(){
        for (int i =0;i<latLngList.size();i++){
            addMarker(latLngList.get(i));
        }
    }

    //设置自定义样式
    private void setCustomMap(Context context, AMap aMap) {
        CustomMapStyleOptions customMapStyleOptions = new CustomMapStyleOptions();
        customMapStyleOptions.setEnable(true);
        customMapStyleOptions.setStyleData(getAssetsStyle(context,"style.data"));
        customMapStyleOptions.setStyleExtraData(getAssetsStyle(context,"style_extra.data"));
        aMap.setCustomMapStyle(customMapStyleOptions);
    }
    //读取assets文件夹内容
    private byte[] getAssetsStyle(Context context,String fileName) {
        byte[] buffer = null;
        InputStream is = null;
        try {
            is = context.getAssets().open(fileName);
            int lenght1 = is.available();
            buffer = new byte[lenght1];
            is.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer;
    }

    /**
     * 接收异步返回的定位结果
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //地址
                String address = aMapLocation.getAddress();
                //城市赋值
                city = aMapLocation.getCity();
                double latitude = aMapLocation.getLatitude();
                double longitude = aMapLocation.getLongitude();

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("纬度：" + latitude + "\n");
                stringBuffer.append("经度：" + longitude + "\n");
                stringBuffer.append("地址：" + address + "\n");

                Log.d("MainActivity", stringBuffer.toString());
                showMsg(address);
                mLocationClient.stopLocation();
                if (mListener != null) {
                    mListener.onLocationChanged(aMapLocation);
                }

                cityCode = aMapLocation.getCityCode();

            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();

        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 检查Android版本
     */
    private void checkingAndroidVersion() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Android6.0及以上先获取权限再定位
            requestPermission();
        }else {
            //Android6.0以下直接定位
            mLocationClient.startLocation();
        }
    }

    /**
     * 动态请求权限
     */
    @AfterPermissionGranted(REQUEST_PERMISSIONS)
    private void requestPermission() {
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        if (EasyPermissions.hasPermissions(this, permissions)) {
            //true 有权限 开始定位
            showMsg("已获得权限，可以定位啦！");
            //启动定位
            mLocationClient.startLocation();
        } else {
            //false 无权限
            EasyPermissions.requestPermissions(this, "需要权限", REQUEST_PERMISSIONS, permissions);
        }
    }

    /**
     * 请求权限结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //设置权限请求结果
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void showMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        AppContext app = (AppContext) getApplication();
        Intent intent = new Intent(MapActivity.this, SecondActivity.class);
        RipplePosition ripplePosition = new RipplePosition(x,y);
        Log.e("TAG", x+","+y);
        app.setPosition(ripplePosition);
        startActivity(intent);
        // 取消系统默认的 Activity 跳转动画
        overridePendingTransition(0, 0);
        return true;
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            mLocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        latlonToAddress(latLng);
        //添加地图标点
//        addMarker(latLng);
        updateMapCenter(latLng);
        Point point = aMap.getProjection().toScreenLocation(latLng);
        Log.e("TAG", point.toString());
        x = (float) point.x;
        y = (float) point.y;

    }

    /**
     * 坐标转地址
     *
     * @param regeocodeResult
     * @param i
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == PARSE_SUCCESS_CODE) {
            RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
            showMsg("地址：" + regeocodeAddress.getFormatAddress());
        } else {
            showMsg("获取地址失败");
        }
    }

    /**
     * 地址转坐标
     *
     * @param geocodeResult
     * @param i
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        if (i == PARSE_SUCCESS_CODE) {
            List<GeocodeAddress> geocodeAddressList = geocodeResult.getGeocodeAddressList();
            if (geocodeAddressList != null && geocodeAddressList.size() > 0) {
                LatLonPoint latLonPoint = geocodeAddressList.get(0).getLatLonPoint();
                LatLng latLng = new LatLng(latLonPoint.getLatitude(),latLonPoint.getLongitude());
                latLngList.add(latLng);
                addMarker(latLng);
                Log.e("添加成功", latLng.toString());
            }
            else {
                showMsg("获取坐标失败");
            }
        }
    }
    /**
     * 通过经纬度获取地址
     *
     * @param latLng
     */
    private void latlonToAddress(LatLng latLng) {
        //位置点  通过经纬度进行构建
        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        //逆编码查询  第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 20, GeocodeSearch.AMAP);
        //异步获取地址信息
        geocodeSearch.getFromLocationAsyn(query);
    }

    /**
     * 添加地图标点
     *
     * @param latLng
     */
    private void addMarker(LatLng latLng) {

        //添加标点
        Marker marker = aMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.img_point))
                .position(latLng));

        Log.e("进来！！！", "执行到了！");

//        //设置标点的绘制动画效果
//        Animation animation = new RotateAnimation(marker.getRotateAngle(), marker.getRotateAngle() + 180, 0, 0, 0);
//        long duration = 1000L;
//        animation.setDuration(duration);
//        animation.setInterpolator(new LinearInterpolator());

//        marker.setAnimation(animation);
//        marker.startAnimation();

        markerList.add(marker);
    }
    private void addPolyLine(List<LatLng> latLngs) {
//        PolylineOptions polylineOptions = new PolylineOptions();
//        polylineOptions.set
        Polyline polyline = aMap.addPolyline(new PolylineOptions()
                .addAll(latLngs)
                .width(5)
                .geodesic(true)
                .color(Color.parseColor("#e5c69d")));


        polyline.setGeodesic(true);


        Log.e("进来！！！", "执行到了！");

    }





    /**
     * 改变地图中心位置
     * @param latLng 位置
     */
    private void updateMapCenter(LatLng latLng) {
        // CameraPosition 第一个参数： 目标位置的屏幕中心点经纬度坐标。
        // CameraPosition 第二个参数： 目标可视区域的缩放级别
        // CameraPosition 第三个参数： 目标可视区域的倾斜度，以角度为单位。
        // CameraPosition 第四个参数： 可视区域指向的方向，以角度为单位，从正北向顺时针方向计算，从0度到360度
        CameraPosition cameraPosition = new CameraPosition(latLng, 4, 0, 0);
        //位置变更
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        //改变位置
        aMap.animateCamera(cameraUpdate);
    }

}