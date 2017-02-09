package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
//***********
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//************
import com.google.gson.Gson;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.id;


public class AddOrEditPetData extends AppCompatActivity {
    private GestureDetector gestureDetector;  //手势监听器对象
    //**
    String iv_String_使用者id;
    String iv_String_查詢使用者="http://twpetanimal.ddns.net:9487/api/v1/animalDatas?$filter=animalOwner_userID eq '"+iv_String_使用者id+"'";
    //*
    object_petDataForSelfDB[] iv_object_petDataForSelfDB;
    //*




    private ListView iv_listView_listViewOfPetData;
    List<simplePetData> iv_list_petData = new ArrayList<simplePetData>();
    private Adapter_AddOrEditPetData iv_Adapter_AddOrEditPetData_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_data_select_page);
        init();


    }

    private void init() {
        //**測試用假資料
        testPetData();


        //********
        query查詢使用者目前送養之寵物();

        //******
        gestureDetector=new  GestureDetector(this,onGestureListener);  //手势监听器的处理效果   由onGestureListener来处理
        //***
    }


    private void create產生LIST畫面() {
        //********
        iv_listView_listViewOfPetData = (ListView)findViewById(R.id.list1);
        iv_listView_listViewOfPetData.setAdapter(new Adapter_AddOrEditPetData(this,iv_list_petData));
        //點擊清單事件
        iv_listView_listViewOfPetData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(AddOrEditPetData.this,"position: "+position+"  :  long : "+id,Toast.LENGTH_LONG).show();
                Intent l_intent = new Intent(AddOrEditPetData.this,page_editPetData.class);
                object_petDataForSelfDB l_object_petDataForSelfDB =iv_object_petDataForSelfDB[position];
                l_intent.putExtra("object_ConditionOfAdoptPet_objA",l_object_petDataForSelfDB );
                //Log.d("",iv_object_petDataForSelfDB[0].getAnimalName());
                startActivityForResult(l_intent,CDictionary.IntentRqCodeOfOpenEditPetData);
                finish();

            }
        });
        //*******
        iv_Adapter_AddOrEditPetData_adapter = new Adapter_AddOrEditPetData(this,iv_list_petData);
        iv_listView_listViewOfPetData.setAdapter(iv_Adapter_AddOrEditPetData_adapter);
    }

    //******TEST************
    private void testPetData() {
        iv_String_使用者id="0cee9178-0b2d-42e9-859d-cf061e4750f3";
        /*
        iv_list_petData.add(new simplePetData(R.drawable.aaa,"testTitle","接著，你要決定你的每一個item需要哪些資料\n" +
                "為了以後的方便，我們要自己建一個class來放這些資料\n" +
                "以這篇為例子\n" +
                "我要傳入的資料有:電影台名稱 電影標題 電影時間\n" +
                "其實我的case比較特殊啦\n" +
                "因為可以很明顯看到每個list item長的並不是很相同\n" +
                "所以我加了一個type的參數"));
        iv_list_petData.add(new simplePetData(R.drawable.aaa,"testTitle","testContent"));
        iv_list_petData.add(new simplePetData(R.drawable.aaa,"testTitle","testContent"));
        iv_list_petData.add(new simplePetData(R.drawable.aaa,"testTitle","testContent"));
        iv_list_petData.add(new simplePetData(R.drawable.aaa,"testTitle","testContent"));
        */



    }

    //****method*****************
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return  gestureDetector.onTouchEvent(event);//当前Activity被触摸时回调
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
// TODO Auto-generated method stub
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private  GestureDetector.OnGestureListener onGestureListener
            =new  GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent startEvent, MotionEvent endEvent,
                               float velocityX, float velocityY){

            //Toast.makeText(ScrollingActivity.this,"yes",Toast.LENGTH_SHORT).show();

            //得到滑动手势的起始和终点的X.Y坐标，并进行计算
/*
            float x= e2.getX()-e1.getX();

            //通过计算结果判断用户是向左滑动或者向右滑动
            if (x>0&&count!=5){
                count++;
            }else  if(x<0&&count!=0){
                count--;
            }
            iv.setImageResource(resId[count]);

           */

            //
            if (startEvent.getY() - endEvent.getY() > 100) {
                //Toast.makeText(ScrollingActivity.this, "手势向上滑动", Toast.LENGTH_SHORT).show();
                return true;
            } else if (startEvent.getY() - endEvent.getY() < -100) {
                //Toast.makeText(ScrollingActivity.this, "手势向下滑动", Toast.LENGTH_SHORT).show();
                return true;
            } else if (startEvent.getX() - endEvent.getX() > 100) {
                Toast.makeText(AddOrEditPetData.this, "手势向左滑动", Toast.LENGTH_SHORT).show();

                // return true;
            } else if (startEvent.getX() - endEvent.getX() < -100) {
                //Toast.makeText(ScrollingActivity.this, "手势向右滑动", Toast.LENGTH_SHORT).show();

                //return true;
            }

            //
            return true;

        }
    };


    //***
    public void query查詢使用者目前送養之寵物(){
        if(iv_String_使用者id!=null){
            //***
            OkHttpClient l_OkHttp_client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://twpetanimal.ddns.net:9487/api/v1/animalDatas?$filter=animalOwner_userID eq '"+iv_String_使用者id+"'")
                    .addHeader("Content-Type", "application/json")
                    .get()
                    .build();

            Call call = l_OkHttp_client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("http", "fail");

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String json = response.body().string();
                    Log.d("http", json);

                    //textView.setText(json);
                    Gson gson = new Gson();
                    iv_object_petDataForSelfDB = gson.fromJson(json, object_petDataForSelfDB[].class);
                    if(iv_object_petDataForSelfDB.length == 0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                 Snackbar.make(AddOrEditPetData.this.findViewById(R.id.list1),"您目前沒有送養中的寵物,可向左滑動新增送養",Snackbar.LENGTH_INDEFINITE)
                                        //spinner.getSelectedItem()=取得被點擊的項目物件 此處用TOSTRING轉成字串 注意第一個參數是VIEW物件
                                        .setAction("確定", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //點擊後觸發的事件
                                            }
                                        })
                                        .setActionTextColor(Color.BLUE)//設定文字顏色
                                        .show();

                            }
                        });

                        return;
                    }

                    //Log.d("http", iv_object_petDataForSelfDB.toString());



                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (object_petDataForSelfDB obj:iv_object_petDataForSelfDB) {
                                iv_list_petData.add(new simplePetData((obj.getAnimalData_Pic().size()!=0? obj.getAnimalData_Pic().get(0).getAnimalPicAddress().toString():"https://cdn2.iconfinder.com/data/icons/stickerweather/128/na.png"),obj.getAnimalName(),"品種: "+obj.getAnimalType()+"\n"+"刊登日期: "+"" +
                                        obj.getAnimalDate()));
                            }
                            create產生LIST畫面();
                        }
                    });
                }
            });

        }
        else {
            /*
            new AlertDialog.Builder(AddOrEditPetData.this)
                    .setMessage("")
                    .setTitle("")
                    .setPositiveButton("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
                    */
        }

    }

    //*****************
}
