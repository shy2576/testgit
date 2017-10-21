package com.example.weather;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvCity;
    private TextView tvWeather;
    private TextView tvTemp;
    private TextView tvWind;
    private TextView tvPm;
    private ImageView ivIcon;
    private Map<String,String>map;
    private List<Map<String,String>> list;
    private String temp,weather,name,pm,wind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        try{
            InputStream is=this.getResources().openRawResource(R.raw.weather2);
            List<WeatherInfo> weatherInfos=WeatherService.getInfosFromJson(is);
            list=new ArrayList<Map<String, String>>();
            for (WeatherInfo info:weatherInfos){
                map=new HashMap<String, String>();
                map.put("temp",info.getTemp());
                map.put("weather",info.getWeather());
                map.put("name",info.getName());
                map.put("pm",info.getPm());
                map.put("wind",info.getWind());
                list.add(map);
            }
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this,"解析信息失败",Toast.LENGTH_SHORT).show();
        }
        getMap(1,R.drawable.sun);
    }
    private void initView(){
        tvCity=(TextView)findViewById(R.id.tv_city);
        tvWeather=(TextView)findViewById(R.id.tv_weather);
        tvWind=(TextView)findViewById(R.id.tv_wind);
        tvTemp=(TextView)findViewById(R.id.tv_temp);
        tvPm=(TextView)findViewById(R.id.tv_pm);
        ivIcon=(ImageView)findViewById(R.id.iv_icon);
        findViewById(R.id.btn_sh).setOnClickListener(this);
        findViewById(R.id.btn_bj).setOnClickListener(this);
        findViewById(R.id.btn_gz).setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_sh:
                getMap(0,R.drawable.weather);
                break;
            case R.id.btn_bj:
                getMap(1,R.drawable.sun);
                break;
            case R.id.btn_gz:
                getMap(2,R.drawable.clouds);
                break;
        }
    }
    private void getMap(int number,int iconNumber){
        Map<String,String>cityMap=list.get(number);
        temp=cityMap.get("temp");
        weather=cityMap.get("weather");
        name=cityMap.get("name");
        pm=cityMap.get("pm");
        wind=cityMap.get("wind");
        tvCity.setText(name);
        tvWeather.setText(weather);
        tvTemp.setText(" "+temp);
        tvWind.setText("风力："+wind);
        tvPm.setText("pm："+pm);
        ivIcon.setImageResource(iconNumber);
    }
}
