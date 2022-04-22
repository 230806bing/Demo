package com.example.demo.mapdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.CustomMapStyleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.demo.R;
import com.example.demo.jumoanimationdemo.FirstActivity;
import com.example.demo.jumoanimationdemo.SecondActivity;
import com.example.demo.rippleanimationdemo.AppContext;
import com.example.demo.rippleanimationdemo.RipplePosition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements AMapLocationListener,
        LocationSource, PoiSearch.OnPoiSearchListener, AMap.OnMapClickListener, AMap.OnMapLongClickListener,
        GeocodeSearch.OnGeocodeSearchListener, EditText.OnKeyListener, AMap.OnMarkerClickListener,
        AMap.OnMarkerDragListener, AMap.InfoWindowAdapter {

    //请求权限码
    private static final int REQUEST_PERMISSIONS = 9527;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //内容
//    private TextView tvContent;

    //地图
    private MapView mapView;

    //地图控制器
        private AMap aMap = null;
        //位置更改监听
        private OnLocationChangedListener mListener;
    //定位图标ui
    private MyLocationStyle myLocationStyle = new MyLocationStyle();
    //修改比例尺/放大缩小图标
    private UiSettings mUiSettings;

    //POI查询对象
    private PoiSearch.Query query;
    //POI搜索对象
    private PoiSearch poiSearch;
    //城市码
    private String cityCode = null;
    //浮动按钮
    private FloatingActionButton fabPOI;


    //地理编码搜索
    private GeocodeSearch geocodeSearch;
    //解析成功标识码
    private static final int PARSE_SUCCESS_CODE = 1000;

    private EditText etAddress;

    //城市
    private String city;


    //浮动按钮  清空地图标点
    private FloatingActionButton fabClearMarker;

    //标点列表
    private List<Marker> markerList = new ArrayList<>();

    private float x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.map_view);

        fabPOI = findViewById(R.id.fab_poi);
        fabClearMarker = findViewById(R.id.fab_clear_marker);

        etAddress = findViewById(R.id.et_address);

        etAddress.setOnKeyListener(this);
        mapView.onCreate(savedInstanceState);
        MapsInitializer.updatePrivacyShow(this, true, true);
        MapsInitializer.updatePrivacyAgree(this, true);
        try {
            initLocation();
            initMap(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }


        checkingAndroidVersion();

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

        setCustomMap(this, aMap);
        //设置自定义样式
        //设置最小缩放等级为16 ，缩放级别范围为[3, 20]
        aMap.setMinZoomLevel(4);
        aMap.setMaxZoomLevel(10);
        //开启室内地图
//        aMap.showIndoorMap(true);

        //实例化UiSettings类对象
        mUiSettings = aMap.getUiSettings();
        //隐藏缩放按钮
        mUiSettings.setZoomControlsEnabled(false);
        //显示比例尺 默认不显示
        mUiSettings.setScaleControlsEnabled(true);

        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色  都为0则透明
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        // 自定义精度范围的圆形边框宽度  0 无宽度
        myLocationStyle.strokeWidth(0);
        // 设置圆形的填充颜色  都为0则透明
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));

        //设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);

        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);

        //设置点击监听
        aMap.setOnMapClickListener(this);
        //设置长按监听
        aMap.setOnMapLongClickListener(this);
        //设置Marker点击事件
        aMap.setOnMarkerClickListener(this);
        //设置Marker拖拽事件
        aMap.setOnMarkerDragListener(this);

        //设置infowindowadapter监听
        aMap.setInfoWindowAdapter(this);

//         在线样式
//        String path = Environment.getExternalStoragePublicDirectory("data").getPath()+"/style.data";
//        aMap.setCustomMapStyle(
//                new com.amap.api.maps.model.CustomMapStyleOptions()
//                        .setEnable(true)
//                        .setStyleId("8d3149388d28aee882bdd9e2286f9372")//官网控制台-自定义样式 获取
//                        .setStyleDataPath(path)
//        );

        //构造对象
        geocodeSearch = new GeocodeSearch(this);
        //设置监听
        geocodeSearch.setOnGeocodeSearchListener(this);
    }

    private void setCustomMap(Context context, AMap aMap) {
        CustomMapStyleOptions customMapStyleOptions = new CustomMapStyleOptions();
        customMapStyleOptions.setEnable(true);
        customMapStyleOptions.setStyleData(getAssetsStyle(context));
        customMapStyleOptions.setStyleExtraData(getAssetsStyleExtra(context));
        aMap.setCustomMapStyle(customMapStyleOptions);
    }

    private byte[] getAssetsStyleExtra(Context context) {
        byte[] buffer1 = null;
        InputStream is1 = null;
        try {
            is1 = context.getAssets().open("style_extra.data");
            int lenght1 = is1.available();
            buffer1 = new byte[lenght1];
            is1.read(buffer1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is1 != null) {
                    is1.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer1;
    }

    private static byte[] getAssetsStyle(Context context) {
        byte[] buffer1 = null;
        InputStream is1 = null;
        try {
            is1 = context.getAssets().open("style.data");
            int lenght1 = is1.available();
            buffer1 = new byte[lenght1];
            is1.read(buffer1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is1 != null) {
                    is1.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer1;
    }


//    private void writeIntoSD() {
//        // 先获取系统默认的文档存放根目录
//        try {
//
//            // 先获取系统默认的文档存放根目录
//            File parent_path = Environment.getExternalStorageDirectory();
//            Log.e("parent_path", parent_path.toString());
//            File dir = new File(parent_path.getAbsoluteFile(), "style");
//            Log.e("dir", dir.toString());
//            if (!dir.exists()) {
//                dir.mkdir();
//            }
//            Log.e("结果", dir.mkdir() + "");
//            File file1 = new File(dir.getAbsoluteFile(), "style.data");
//            File file2 = new File(dir.getAbsoluteFile(), "style_extra.data");
//            Log.e("file1,2", file1.toString() + "," + file2.toString());
//            if (file1.exists() && file2.exists()) {
//                return;
//            }
//            //读取数据文件
//            InputStream open1 = this.getResources().getAssets().open("style.data");
//            InputStream open2 = this.getResources().getAssets().open("style_extra.data");
//            file1.createNewFile();
//            file2.createNewFile();
//            FileOutputStream fos1 = new FileOutputStream(file1);
//            FileOutputStream fos2 = new FileOutputStream(file2);
//            int len1, len2;
//            byte[] buf1 = new byte[1024];
//            byte[] buf2 = new byte[1024];
//            while ((len1 = open1.read(buf1)) != -1) {
//                fos1.write(buf1, 0, len1);
//                Log.e("执行到", "这里了1111");
//            }
//            while ((len2 = open2.read(buf2)) != -1) {
//                fos2.write(buf2, 0, len2);
//                Log.e("执行到", "这里了2222");
//            }
//            fos1.flush();
//            fos2.flush();
//            fos1.close();
//            fos2.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 检查Android版本
     */
    private void checkingAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Android6.0及以上先获取权限再定位
            requestPermission();
        } else {
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

    private void showMsg(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
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

                fabPOI.show();
                cityCode = aMapLocation.getCityCode();

//                tvContent.setText(address == null ? "无地址" :address);
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    /**
     * 浮动按钮点击查询附近POI
     *
     * @param view
     */
    public void queryPOI(View view) throws AMapException {
        //构造query对象
        query = new PoiSearch.Query("购物", "", cityCode);
        // 设置每页最多返回多少条poiitem
        query.setPageSize(10);
        //设置查询页码
        query.setPageNum(1);
        //构造 PoiSearch 对象
        poiSearch = new PoiSearch(this, query);
        //设置搜索回调监听
        poiSearch.setOnPoiSearchListener(this);
        //发起搜索附近POI异步请求
        poiSearch.searchPOIAsyn();
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
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            mLocationClient.startLocation();//启动定位
        }
    }

    /**
     * 停止定位
     */

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    /**
     * POI搜索返回
     *
     * @param poiResult POI所有数据
     * @param i
     */
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
//解析result获取POI信息

        //获取POI组数列表
        ArrayList<PoiItem> poiItems = poiResult.getPois();
        for (PoiItem poiItem : poiItems) {
            Log.d("MainActivity", " Title：" + poiItem.getTitle() + " Snippet：" + poiItem.getSnippet());
        }
    }

    /**
     * POI中的项目搜索返回
     *
     * @param poiItem 获取POI item
     * @param i
     */
    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        latlonToAddress(latLng);
        //添加标点
//        aMap.addMarker(new MarkerOptions().position(latLng).snippet("DefaultMarker"));
        addMarker(latLng);

        updateMapCenter(latLng);
        Point point = aMap.getProjection().toScreenLocation(latLng);
        Log.e("TAG", point.toString());
        x = (float) point.x;
        y = (float) point.y;

//        showMsg("点击了地图，经度："+latLng.longitude+"，纬度："+latLng.latitude);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        latlonToAddress(latLng);
//        showMsg("长按了地图，经度："+latLng.longitude+"，纬度："+latLng.latitude);

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
                //显示解析后的坐标
                showMsg("坐标：" + latLonPoint.getLongitude() + "，" + latLonPoint.getLatitude());
            }
        } else {
            showMsg("获取坐标失败");
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
     * 键盘点击
     *
     * @param v
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            //获取输入框的值
            String address = etAddress.getText().toString().trim();
            if (address == null || address.isEmpty()) {
                showMsg("请输入地址");
            } else {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //隐藏软键盘
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

                // name表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode
                GeocodeQuery query = new GeocodeQuery(address, "中国");
                geocodeSearch.getFromLocationNameAsyn(query);
            }
            return true;
        }
        return false;
    }


    /**
     * 添加地图标点
     *
     * @param latLng
     */
    private void addMarker(LatLng latLng) {


        //显示浮动按钮
        fabClearMarker.show();
        //添加标点
        Marker marker = aMap.addMarker(new MarkerOptions()
                .draggable(true)//可拖动
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point))
                .title("title")
                .snippet("DefaultMarker"));

//        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point));

        //设置标点的绘制动画效果
//        Animation animation = new RotateAnimation(marker.getRotateAngle(), marker.getRotateAngle() + 180, 0, 0, 0);
//        long duration = 1000L;
//        animation.setDuration(duration);
//        animation.setInterpolator(new LinearInterpolator());
//
//        marker.setAnimation(animation);
//        marker.startAnimation();

        markerList.add(marker);
    }

    public void clearAllMarker(View view) {
        if (markerList != null && markerList.size() > 0) {
            for (Marker markerItem : markerList) {
                markerItem.remove();
            }
        }
        fabClearMarker.hide();
    }


//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        switch (event.getAction()) {
//
//            /**
//             * 点击的开始位置
//             */
//            case MotionEvent.ACTION_DOWN:
//                x = event.getX();
//                y = event.getY();
//                Log.e("kan", x+","+y);
//                Toast.makeText(MainActivity.this,"开始位置：(" + event.getX() + "," + event.getY(),Toast.LENGTH_SHORT).show();
//                break;
//            /**
//             * 触屏实时位置
//             */
//            case MotionEvent.ACTION_MOVE:
//                x = event.getX();
//                y = event.getY();
//                Log.e("kan", x+","+y);
//                Toast.makeText(MainActivity.this,"实时位置：(" + event.getX() + "," + event.getY(),Toast.LENGTH_SHORT).show();
//                break;
//            /**
//             * 离开屏幕的位置
//             */
//            case MotionEvent.ACTION_UP:
//                x = event.getX();
//                y = event.getY();
//                Log.e("kan", x+","+y);
//                Toast.makeText(MainActivity.this,"结束位置：(" + event.getX() + "," + event.getY(),Toast.LENGTH_SHORT).show();
//
//                break;
//            default:
//                break;
//        }
//        return true;
//    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        AppContext app = (AppContext) getApplication();
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        RipplePosition ripplePosition = new RipplePosition(x,y);
        Log.e("TAG", x+","+y);
        app.setPosition(ripplePosition);
        startActivity(intent);
        // 取消系统默认的 Activity 跳转动画
        overridePendingTransition(0, 0);

//        if (!marker.isInfoWindowShown()){
//            marker.showInfoWindow();
//        }else {
//            marker.hideInfoWindow();
//        }
//        showMsg("点击了标点");
        return true;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.d("TAG", "开始拖动");
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Log.d("TAG", "拖动中");

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.d("TAG", "结束拖动");

    }

    /**
     * 修改内容
     *
     * @param marker
     * @return
     * */

    @Override
    public View getInfoWindow(Marker marker) {
        View infoContent = getLayoutInflater().inflate(
                R.layout.custom_info_contents,null);
        render(marker,infoContent);
        return infoContent;
    }

    /**
     * 修改背景
     *
     * @param marker
     */
    @Override
    public View getInfoContents(Marker marker) {
        View infoWindow = getLayoutInflater().inflate(
                R.layout.custom_info_window, null);

        render(marker, infoWindow);
        return infoWindow;
    }

    /**
     * 渲染
     *
     * @param marker
     * @param view
     */
    private void render(Marker marker, View view) {
        ((ImageView) view.findViewById(R.id.badge))
                .setImageResource(R.drawable.icon_yuan);

        //修改InfoWindow标题内容样式
        String title = marker.getTitle();
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(Color.RED), 0,
                    titleText.length(), 0);
            titleUi.setTextSize(15);
            titleUi.setText(titleText);

        } else {
            titleUi.setText("");
        }
        //修改InfoWindow片段内容样式
        String snippet = marker.getSnippet();
        TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
        if (snippet != null) {
            SpannableString snippetText = new SpannableString(snippet);
            snippetText.setSpan(new ForegroundColorSpan(Color.GREEN), 0,
                    snippetText.length(), 0);
            snippetUi.setTextSize(20);
            snippetUi.setText(snippetText);
        } else {
            snippetUi.setText("");
        }
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