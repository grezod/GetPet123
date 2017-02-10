package iii.org.tw.getpet;


import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;

import static android.Manifest.permission.*;
import static android.R.attr.bitmap;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static iii.org.tw.getpet.R.id.edTxt_animalNote;
import static iii.org.tw.getpet.R.id.edTxt_animalReason;
import static iii.org.tw.getpet.R.id.imageView;
import static iii.org.tw.getpet.R.id.imgBtn2;
import static iii.org.tw.getpet.R.id.imgBtn3;
import static iii.org.tw.getpet.R.id.imgBtn4;
import static iii.org.tw.getpet.R.id.imgBtn5;
import static iii.org.tw.getpet.R.id.spinner_animalArea;
import static iii.org.tw.getpet.R.id.spinner_animalKind;
import static iii.org.tw.getpet.R.id.spinner_animalType;
import static iii.org.tw.getpet.R.style.dialog;
//import static iii.com.tw.testuploadpage2.R.id.edTxt_animalData_animalTypeID;


import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

//***********
import cz.msebera.android.httpclient.Header;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

//*******CLASS與畫面配對
//ScrollingActivity.java + activity_scrolling.xml
// actDialogOfPetAdoptCondition.java + activity_act_dialog_of_pet_adopt_condition.xml
//**********

public class page_editPetData extends AppCompatActivity {
    //**
    Bitmap iv_bitmap_getFromUrl;
    //*/
    boolean iv_Boolean_判斷是否該觸發發品種列表刷新事件 = false;
    //**
    object_petDataForSelfDB iv_object_petDataForSelfDB;
    //**
    ArrayAdapter<String> iv_ArrayAdapter_spinner_animalType;
    ArrayAdapter<String> iv_ArrayAdapter_spinner_animalChip;
    ArrayAdapter<String> iv_ArrayAdapter_spinner_animalArea;
    ArrayAdapter<String> iv_ArrayAdapter_spinner_animalGender;
    ArrayAdapter<String> iv_ArrayAdapter_spinner_animalBirth;
    ArrayAdapter<String> iv_ArrayAdapter_spinner_animalKind;
    //*
    object_ConditionOfAdoptPet iv_object_conditionOfAdoptPet_a;
    //**
    public static page_editPetData page_editPetData;
    public static page_editPetData iv_page_editPetData;
    //***
    OkHttpClient Iv_OkHttp_client = new OkHttpClient();
    public static final MediaType Iv_MTyp_JSON = MediaType.parse("application/json; charset=utf-8");
    //**
    private static final int REQUEST_READ_STORAGE = 3;
    //*
    static final int requestCodeImgBtn1 = 1001;
    static final int requestCodeImgBtn2 = 1002;
    static final int requestCodeImgBtn3 = 1003;
    static final int requestCodeImgBtn4 = 1004;
    static final int requestCodeImgBtn5 = 1005;
    //*
    static final int iv_requestCodeOfImgBtn1ForCamera = 1006;
    static final int iv_requestCodeOfImgBtn2ForCamera = 1007;
    static final int iv_requestCodeOfImgBtn3ForCamera = 1008;
    static final int iv_requestCodeOfImgBtn4ForCamera = 1009;
    static final int iv_requestCodeOfImgBtn5ForCamera = 10010;
    //*
    int iv_forCountIn塞圖片到imageButton;
    //**
    boolean selectedImgForUpload1 = false;
    boolean selectedImgForUpload2 = false;
    boolean selectedImgForUpload3 = false;
    boolean selectedImgForUpload4 = false;
    boolean selectedImgForUpload5 = false;
    //**
    Bitmap bitmap1;
    Bitmap bitmap2;
    Bitmap bitmap3;
    Bitmap bitmap4;
    Bitmap bitmap5;
    //***
    object_ConditionOfAdoptPet object_conditionOfAdoptPet;
    //**
    Gson iv_gson;
    //**
    AlertDialog iv_ADialog_a;
    AlertDialog iv_ADialog_b;
    //**
    ImageButton[] iv_ImageButtonArray;
    Bitmap[] bitmapArray = {bitmap1, bitmap2, bitmap3, bitmap4, bitmap5};
    boolean[] selectedImgForUploadArray = {selectedImgForUpload1, selectedImgForUpload2, selectedImgForUpload3, selectedImgForUpload4, selectedImgForUpload5};
    ArrayList<object_OfPictureImgurSite> iv_ArrayList_object_OfPictureImgurSite;
    ArrayList<object_ConditionOfAdoptPet> iv_ArrayList_object_ConditionOfAdoptPet;
    final String[] area = {"全部", "臺北市", "新北市", "基隆市", "宜蘭縣",
            "桃園縣", "新竹縣", "新竹市", "苗栗縣", "臺中市", "彰化縣",
            "南投縣", "雲林縣", "嘉義縣", "嘉義市", "臺南市", "高雄市",
            "屏東縣", "花蓮縣", "臺東縣", "澎湖縣", "金門縣", "連江縣"};
    final String[] iv_array_animalGender = {"公", "母"};
    final String[] iv_array_YesOrNO = {"否", "是"};
    private ArrayList<String>[] iv_Array_動物品種清單;
    private ArrayList<String> iv_ArrayList_動物類別清單;
    private ArrayList<Bitmap> iv_ArrayList_Bitmap;

    //*******
    private View.OnClickListener btn_click = new View.OnClickListener() {
        int IntentRCodeOfOpenAlbum = 0;
        int l_IntentRCodeOfOpenCamera = 0;

        @Override
        public void onClick(final View v) {

            switch (v.getId()) {
                case R.id.imgBtn1:
                    IntentRCodeOfOpenAlbum = requestCodeImgBtn1;
                    //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn1)",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imgBtn2:
                    IntentRCodeOfOpenAlbum = requestCodeImgBtn2;
                    //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn2)",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imgBtn3:
                    IntentRCodeOfOpenAlbum = requestCodeImgBtn3;
                    //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn3)",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imgBtn4:
                    IntentRCodeOfOpenAlbum = requestCodeImgBtn4;
                    //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn4)",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imgBtn5:
                    IntentRCodeOfOpenAlbum = requestCodeImgBtn5;
                    //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn5)",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnEdit:
                    iv_ADialog_a = new AlertDialog.Builder(page_editPetData.this)
                            .setMessage("是否確定送出資料")
                            .setTitle("送出確認")
                            .setPositiveButton("送出", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //**
                                    for (int i =0 ; i<iv_ArrayList_Bitmap.size();i+=1) {
                                        bitmapArray[i] = iv_ArrayList_Bitmap.get(i);
                                        selectedImgForUploadArray[i]=true;

                                    }
                                    //**
                                    String l_string_未填寫的欄位有哪些 = check確認是否欄位都有填寫();

                                    if (l_string_未填寫的欄位有哪些.length() > 10) {
                                        new AlertDialog.Builder(page_editPetData.this)
                                                .setMessage(l_string_未填寫的欄位有哪些)
                                                .setTitle("欄位未填")
                                                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {


                                                    }
                                                })
                                                .show();

                                    } else {
                                        try {
                                            //delete資料(String.valueOf(iv_object_petDataForSelfDB.getAnimalID()));

                                            uploadImageAndGetSiteBack();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        addAllDataToDBServer();
                                    }

                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                    break;
                case R.id.btnDelete:
                    delete資料(String.valueOf(iv_object_petDataForSelfDB.getAnimalID()));
                    break;
                case R.id.btnCamera:
                    Intent intentCamera = new Intent(
                            MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);

                    startActivity(intentCamera);
                    break;
                case R.id.btnAdoptCondition:
                    Log.d("123", "btnAdoptCondition");
                    Intent intent = new Intent(page_editPetData.this, actDialogOfPetAdoptCondition.class);
                    intent.putExtra("l_object_ConditionOfAdoptPet_objA", iv_object_conditionOfAdoptPet_a);
                    startActivityForResult(intent, CDictionary.IntentRqCodeOfPetAdoptCondition);
                    break;
            }





            if (v.getId() == R.id.btnEdit || v.getId() == R.id.btnDelete ||v.getId() == R.id.btnCamera) {
                return;
            }

            if (v.getId() != R.id.btnAdoptCondition) {
                //Toast.makeText(ScrollingActivity.this,String.valueOf(IntentRCodeOfOpenAlbum),Toast.LENGTH_SHORT).show();


                //**
                //**
                new AlertDialog.Builder(page_editPetData.this)
                        .setMessage("如欲使用相簿內的相片 請點選相簿\n如欲使用相機直接拍攝 請點擊相機")
                        .setTitle("請選擇使用相簿或相機")
                        .setPositiveButton("相簿", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //**
                                Intent intent = new Intent();
                                //開啟Pictures畫面Type設定為image
                                intent.setType("image/*");
                                //使用Intent.ACTION_GET_CONTENT這個Action會開啟選取圖檔視窗讓您選取手機內圖檔
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                //取得相片後返回本畫面
                                startActivityForResult(intent, IntentRCodeOfOpenAlbum);
                                //**

                            }
                        })
                        .setNeutralButton("取消",null )
                        .setNegativeButton("相機", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (v.getId()) {
                                    case R.id.imgBtn1:
                                        l_IntentRCodeOfOpenCamera = iv_requestCodeOfImgBtn1ForCamera;
                                        //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn1)",Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.imgBtn2:
                                        l_IntentRCodeOfOpenCamera = iv_requestCodeOfImgBtn2ForCamera;
                                        //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn2)",Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.imgBtn3:
                                        l_IntentRCodeOfOpenCamera = iv_requestCodeOfImgBtn3ForCamera;
                                        //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn3)",Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.imgBtn4:
                                        l_IntentRCodeOfOpenCamera = iv_requestCodeOfImgBtn4ForCamera;
                                        //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn4)",Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.imgBtn5:
                                        l_IntentRCodeOfOpenCamera = iv_requestCodeOfImgBtn5ForCamera;
                                        //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn5)",Toast.LENGTH_SHORT).show();
                                        break;}

                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(takePictureIntent, l_IntentRCodeOfOpenCamera);
                                }




                                //**
                               // return;
                            }
                        })
                        .show();
                //**
                //***


            }

        }
    };
    private int iv_int_countHowManyPicNeedUpload;

    //**********
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_edit_pet_data);

        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);


        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限
            ActivityCompat.requestPermissions(this,
                    new String[]{READ_EXTERNAL_STORAGE},
                    REQUEST_READ_STORAGE);
        } else {
            //已有權限，可進行檔案存取
        }
        init();
        try {
            fill表單欄位();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    //***
    private void fill表單欄位() throws ExecutionException, InterruptedException {
        Intent l_intent = getIntent();
        object_petDataForSelfDB l_object_petDataForSelfDB = (object_petDataForSelfDB) l_intent.getSerializableExtra("object_ConditionOfAdoptPet_objA");
        iv_object_petDataForSelfDB = (object_petDataForSelfDB) l_intent.getSerializableExtra("object_ConditionOfAdoptPet_objA");
        //**********
        /*
         edTxt_animalOwner_userID;
         edTxt_animalID;
         edTxt_animalDate;
         */
        //*********************

        edTxt_animalName.setText(l_object_petDataForSelfDB.getAnimalName());
        edTxt_animalDate.setText(l_object_petDataForSelfDB.getAnimalDate());

        edTxt_animalAge.setText(l_object_petDataForSelfDB.getAnimalAge());
        edTxt_animalColor.setText(l_object_petDataForSelfDB.getAnimalColor());
        edTxt_animalHealthy.setText(l_object_petDataForSelfDB.getAnimalHealthy());
        edTxt_animalDisease_Other.setText(l_object_petDataForSelfDB.getAnimalDisease_Other());
        edTxt_animalReason.setText(l_object_petDataForSelfDB.getAnimalReason());
        edTxt_animalNote.setText(l_object_petDataForSelfDB.getAnimalNote());
        Log.d("*********************", l_object_petDataForSelfDB.getAnimalID() + "");

        //***********************

        if (!l_object_petDataForSelfDB.getAnimalAddress().equals(null)) {

            spinner_animalArea.setSelection(iv_ArrayAdapter_spinner_animalArea.getPosition(l_object_petDataForSelfDB.getAnimalAddress().toString()));
        }


        if (!l_object_petDataForSelfDB.getAnimalGender().equals(null)) {

            spinner_animalGender.setSelection(iv_ArrayAdapter_spinner_animalGender.getPosition(l_object_petDataForSelfDB.getAnimalGender().toString()));
        }
        if (!l_object_petDataForSelfDB.getAnimalChip().equals(null)) {

            spinner_animalChip.setSelection(iv_ArrayAdapter_spinner_animalChip.getPosition(l_object_petDataForSelfDB.getAnimalChip().toString()));
        }
        if (!l_object_petDataForSelfDB.getAnimalBirth().equals(null)) {


            spinner_animalBirth.setSelection(iv_ArrayAdapter_spinner_animalBirth.getPosition(l_object_petDataForSelfDB.getAnimalBirth().toString()));
        }


        //*******
        fill塞圖片到imageButton(iv_ImageButtonArray,l_object_petDataForSelfDB);





        //********
    }

    private void fill塞圖片到imageButton(ImageButton[] p_ImageButtonArray,final object_petDataForSelfDB p_object_petDataForSelfDB) throws ExecutionException, InterruptedException {
        //***********
        iv_forCountIn塞圖片到imageButton = 0;



        for (final object_OfPictureImgurSite obj:p_object_petDataForSelfDB.getAnimalData_Pic()) {
            if(obj.getAnimalPicAddress().length()!=0){
               Glide.with(this).load(obj.getAnimalPicAddress()).into(p_ImageButtonArray[iv_forCountIn塞圖片到imageButton]);
                    Thread thread= new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(obj.getAnimalPicAddress());
                                iv_bitmap_getFromUrl = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //bitmapArray[iv_forCountIn塞圖片到imageButton] = iv_bitmap_getFromUrl;
                                        iv_ArrayList_Bitmap.add(iv_bitmap_getFromUrl);
                                        Log.d("bitmap length",iv_ArrayList_Bitmap.size()+"");

                                        //iv_forCountIn塞圖片到imageButton+=1;


                                    }
                                });


                            } catch(IOException e) {
                                System.out.println(e);
                            }
                        }
                    });
                    thread.start();
                //**
               // selectedImgForUploadArray[iv_forCountIn塞圖片到imageButton] = true;
                iv_forCountIn塞圖片到imageButton+=1;

            }
        }





        //**

    }


    //****
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        //******如果是彈跳視窗的回應********************************
        if (resultCode == CDictionary.IntentRqCodeOfPetAdoptCondition) {
            iv_object_conditionOfAdoptPet_a =
                    (object_ConditionOfAdoptPet) data.getSerializableExtra("l_object_ConditionOfAdoptPet_objA");

            //**
            Log.d("test", iv_object_conditionOfAdoptPet_a.toString());
            //*


        }


        //***********如果是圖片按鈕的回應************************

        if (resultCode == RESULT_OK) {
            //****
            Bitmap mScaleBitmap = null;

            ///*************
            Log.d("OK", "OK");

            //取得圖檔的路徑位置
            Uri uri = data.getData();
            //寫log
            // Log.e("uri", uri.toString());
            //抽象資料的接口
            //Toast.makeText(ScrollingActivity.this,"11",Toast.LENGTH_SHORT).show();


            ContentResolver cr = this.getContentResolver();
            try {
                //由抽象資料接口轉換圖檔路徑為Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                //取得圖片控制項ImageView
                //ImageButton imageView = (ImageButton) findViewById(R.id.imgBtn1);
                // 將Bitmap設定到ImageView
                //*****************


                float mScale = 1;

                //如果圖片寬度大於手機寬度則進行縮放，否則直接將圖片放入ImageView內
                if (bitmap.getWidth() >= 480) {
                    //判斷縮放比例
                    mScale = (float) 480 / bitmap.getWidth();
                }

                Matrix mMat = new Matrix();
                mMat.setScale(mScale, mScale);

                mScaleBitmap = Bitmap.createBitmap(bitmap,
                        0,
                        0,
                        bitmap.getWidth(),
                        bitmap.getHeight(),
                        mMat,
                        false);


                //***************

                ImageButton imgBtn = (ImageButton) findViewById(R.id.imgBtn1);
                //**check requestCode to decide show image on which button
                switch (requestCode) {
                    case requestCodeImgBtn1:
                        imgBtn = (ImageButton) findViewById(R.id.imgBtn1);
                        //selectedImgForUpload1 = true;
                        selectedImgForUploadArray[0] = true;
                        bitmapArray[0] = mScaleBitmap;

                        //Toast.makeText(ScrollingActivity.this, selectedImgForUpload1==true? "TrueY":"FalseY", Toast.LENGTH_SHORT).show();
                        break;
                    case requestCodeImgBtn2:
                        imgBtn = (ImageButton) findViewById(R.id.imgBtn2);
                        selectedImgForUploadArray[1] = true;
                        bitmapArray[1] = mScaleBitmap;

                        //Toast.makeText(ScrollingActivity.this, "String.valueOf(R.id.imgBtn2)", Toast.LENGTH_SHORT).show();
                        break;
                    case requestCodeImgBtn3:
                        imgBtn = (ImageButton) findViewById(R.id.imgBtn3);
                        selectedImgForUploadArray[2] = true;
                        bitmapArray[2] = mScaleBitmap;

                        //Toast.makeText(ScrollingActivity.this, "String.valueOf(R.id.imgBtn3)", Toast.LENGTH_SHORT).show();
                        break;
                    case requestCodeImgBtn4:
                        imgBtn = (ImageButton) findViewById(R.id.imgBtn4);
                        selectedImgForUploadArray[3] = true;
                        bitmapArray[3] = mScaleBitmap;

                        //Toast.makeText(ScrollingActivity.this, "String.valueOf(R.id.imgBtn4)", Toast.LENGTH_SHORT).show();
                        break;
                    case requestCodeImgBtn5:
                        imgBtn = (ImageButton) findViewById(R.id.imgBtn5);
                        selectedImgForUploadArray[4] = true;
                        bitmapArray[4] = mScaleBitmap;
                        //Toast.makeText(ScrollingActivity.this, "String.valueOf(R.id.imgBtn5)", Toast.LENGTH_SHORT).show();
                        break;
                }
                //**

                imgBtn.setImageBitmap(mScaleBitmap);
                Log.d("mScaleBitmapSize", "" + mScaleBitmap.getWidth() + "  and " + mScaleBitmap.getHeight());
                Log.d("bitmapSize", "" + bitmap.getWidth() + "  and " + bitmap.getHeight());
                //**

            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
    }


    //********
    public void Factory_DynamicAnimalTypeListCreator(String p_String_url) {
        OkHttpClient l_okHttpClient_client = new OkHttpClient();

        if ("".equals(p_String_url)) {
            p_String_url = "http://twpetanimal.ddns.net:9487/api/v1/animalData_Type";
        }

        Request request = new Request.Builder()
                .url(p_String_url)
                .addHeader("Content-Type", "raw")
                .get()
                .build();

        Call call = l_okHttpClient_client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
                JSONArray l_JSONArray_jObj = null;


                //**
                try {
                    l_JSONArray_jObj = new JSONArray(json);

                    iv_ArrayList_動物類別清單 = new ArrayList<String>();
                    //**
                    for (int i = 0; i < l_JSONArray_jObj.length(); i += 1) {
                        JSONObject l_JSONObject = (JSONObject) l_JSONArray_jObj.get(i);
                        if (!iv_ArrayList_動物類別清單.contains(l_JSONObject.getString("animalKind"))) {
                            iv_ArrayList_動物類別清單.add(l_JSONObject.getString("animalKind"));
                            Log.d("l_JSString(animalType)", l_JSONObject.getString("animalType"));
                        }
                    }
                    Log.d("iv_ArrayList_動物類別清單", iv_ArrayList_動物類別清單.toString() + "共" + iv_ArrayList_動物類別清單.size() + "種");
                    iv_Array_動物品種清單 = new ArrayList[iv_ArrayList_動物類別清單.size()];
                    for (int j = 1; j <= iv_ArrayList_動物類別清單.size(); j += 1) {
                        iv_Array_動物品種清單[j - 1] = new ArrayList<String>();
                    }


                    for (int i = 0; i < l_JSONArray_jObj.length(); i += 1) {
                        JSONObject l_JSONObject = (JSONObject) l_JSONArray_jObj.get(i);
                        for (int j = 1; j <= iv_ArrayList_動物類別清單.size(); j += 1) {

                            if (l_JSONObject.getString("animalKind").equals(iv_ArrayList_動物類別清單.get(j - 1)) && !iv_Array_動物品種清單[j - 1].contains(l_JSONObject.getString("animalType"))) {
                                //iv_Array_動物品種清單[j-1].add(l_JSONObject.getString("animalType"));
                                iv_Array_動物品種清單[j - 1].add(l_JSONObject.getString("animalType"));
                            }
                        }
                    }

                    for (int i = 1; i <= iv_ArrayList_動物類別清單.size(); i += 1) {
                        //iv_Array_動物品種清單[i-1].toString();
                        Log.d("第" + i + "份動物品種清單", iv_Array_動物品種清單[i - 1].toString());
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch英文的動物類別轉換為中文(iv_ArrayList_動物類別清單);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //****************
                        spinner_animalKind = (Spinner) findViewById(R.id.spinner_animalKind);
                        iv_ArrayAdapter_spinner_animalKind = new ArrayAdapter<String>(page_editPetData.this, android.R.layout.simple_spinner_dropdown_item, iv_ArrayList_動物類別清單); //selected item will look like a spinner set from XML
                        Log.d("+++++++222+++", iv_ArrayAdapter_spinner_animalKind.toString());
                        iv_ArrayAdapter_spinner_animalKind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_animalKind.setAdapter(iv_ArrayAdapter_spinner_animalKind);
                        //**

                        if (!iv_object_petDataForSelfDB.getAnimalKind().equals(null)) {
                            Log.d("++++++++", iv_object_petDataForSelfDB.getAnimalKind().toString());
                            // Log.d("++++++++",iv_ArrayAdapter_spinner_animalKind.toString());
                            spinner_animalKind.setSelection(iv_ArrayAdapter_spinner_animalKind.getPosition(iv_object_petDataForSelfDB.getAnimalKind().toString()));
                            //**
                            //*
                        }
                        //*
                        spinner_animalKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (iv_Boolean_判斷是否該觸發發品種列表刷新事件) {

                                    iv_ArrayAdapter_spinner_animalType = new ArrayAdapter<String>(page_editPetData.this, android.R.layout.simple_spinner_dropdown_item, iv_Array_動物品種清單[position]);
                                    spinner_animalType.setAdapter(iv_ArrayAdapter_spinner_animalType);
                                }
                                iv_Boolean_判斷是否該觸發發品種列表刷新事件 = true;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                        //*
                        //****************

                        spinner_animalType = (Spinner) findViewById(R.id.spinner_animalType);
                        ArrayAdapter<String> l_ArrayAdapter_spinner_animalType = new ArrayAdapter<String>(page_editPetData.this, android.R.layout.simple_spinner_dropdown_item, iv_Array_動物品種清單[0]); //selected item will look like a spinner set from XML
                        l_ArrayAdapter_spinner_animalType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_animalType.setAdapter(l_ArrayAdapter_spinner_animalType);

                        if (!iv_object_petDataForSelfDB.getAnimalType().equals(null)) {
                            //*
                            iv_ArrayAdapter_spinner_animalType = new ArrayAdapter<String>(page_editPetData.this, android.R.layout.simple_spinner_dropdown_item, iv_Array_動物品種清單[iv_ArrayAdapter_spinner_animalKind.getPosition(iv_object_petDataForSelfDB.getAnimalKind().toString())]);
                            spinner_animalType.setAdapter(iv_ArrayAdapter_spinner_animalType);
                            //*
                            Log.d("222222", iv_ArrayAdapter_spinner_animalType.getPosition(iv_object_petDataForSelfDB.getAnimalType().toString()) + "");

                            spinner_animalType.setSelection(iv_ArrayAdapter_spinner_animalType.getPosition(iv_object_petDataForSelfDB.getAnimalType().toString()));

                        }


                    }
                });
            }
        });


    }

    //***
    private void switch英文的動物類別轉換為中文(ArrayList<String> p_ArrayList_動物類別清單) {
        for (int i = 0; i < p_ArrayList_動物類別清單.size(); i += 1) {

            switch (p_ArrayList_動物類別清單.get(i).toLowerCase()) {
                case "cat":
                    p_ArrayList_動物類別清單.set(i, "貓");
                    break;
                case "dog":
                    p_ArrayList_動物類別清單.set(i, "狗");
                    break;
                case "bird":
                    p_ArrayList_動物類別清單.set(i, "鳥");
                    break;
                case "OTHER":
                    p_ArrayList_動物類別清單.set(i, "其他");
                    break;
                case "rabbit":
                    p_ArrayList_動物類別清單.set(i, "兔子");
                    break;
                case "mice":
                    p_ArrayList_動物類別清單.set(i, "老鼠");
                    break;

            }
        }
        Log.d("轉換後類別清單", iv_ArrayList_動物類別清單.toString());


    }

    //***********
    private String switch英文的動物類別轉換為中文forString(String p_animalKind) {


        switch (p_animalKind) {
            case "cat":
                p_animalKind = "貓";
                break;
            case "dog":
                p_animalKind = "狗";
                break;
            case "bird":
                p_animalKind = "鳥";
                break;
            case "OTHER":
                p_animalKind = "其他";
                break;
            case "rabbit":
                p_animalKind = "兔子";
                break;
            case "mice":
                p_animalKind = "老鼠";
                break;

        }

        return p_animalKind;


    }


    //*********************

    private void init() {

        iv_ArrayList_Bitmap = new ArrayList<>();
        Factory_DynamicAnimalTypeListCreator("");
        iv_int_countHowManyPicNeedUpload = 0;
        iv_ArrayList_object_ConditionOfAdoptPet = new ArrayList<>();
        iv_ArrayList_object_OfPictureImgurSite = new ArrayList<>();
        iv_gson = new Gson();


        setViewComponent();

        //****************************
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        ///**************************


        //**
    }


    private void setViewComponent() {


        //**********
        spinner_animalArea = (Spinner) findViewById(R.id.spinner_animalArea);
        iv_ArrayAdapter_spinner_animalArea = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, area); //selected item will look like a spinner set from XML
        iv_ArrayAdapter_spinner_animalArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_animalArea.setAdapter(iv_ArrayAdapter_spinner_animalArea);
        //**
        spinner_animalGender = (Spinner) findViewById(R.id.spinner_animalGender);
        iv_ArrayAdapter_spinner_animalGender = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, iv_array_animalGender); //selected item will look like a spinner set from XML
        iv_ArrayAdapter_spinner_animalGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_animalGender.setAdapter(iv_ArrayAdapter_spinner_animalGender);
        //**
        spinner_animalBirth = (Spinner) findViewById(R.id.spinner_animalBirth);
        iv_ArrayAdapter_spinner_animalBirth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, iv_array_YesOrNO); //selected item will look like a spinner set from XML
        iv_ArrayAdapter_spinner_animalBirth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_animalBirth.setAdapter(iv_ArrayAdapter_spinner_animalBirth);
        //**
        spinner_animalChip = (Spinner) findViewById(R.id.spinner_animalChip);
        iv_ArrayAdapter_spinner_animalChip = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, iv_array_YesOrNO); //selected item will look like a spinner set from XML
        iv_ArrayAdapter_spinner_animalChip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_animalChip.setAdapter(iv_ArrayAdapter_spinner_animalChip);
        //**********
        edTxt_animalAge = (EditText) findViewById(R.id.edTxt_animalAge);
        edTxt_animalColor = (EditText) findViewById(R.id.edTxt_animalColor);
        edTxt_animalDate = (EditText) findViewById(R.id.edTxt_animalDate);
        edTxt_animalDisease_Other = (EditText) findViewById(R.id.edTxt_animalDisease_Other);
        edTxt_animalHealthy = (EditText) findViewById(R.id.edTxt_animalHealthy);
        edTxt_animalName = (EditText) findViewById(R.id.edTxt_animalName);
        edTxt_animalNote = (EditText) findViewById(R.id.edTxt_animalNote);
        edTxt_animalReason = (EditText) findViewById(R.id.edTxt_animalReason);
        //*隱藏區****
        edTxt_animalOwner_userID = (EditText) findViewById(R.id.edTxt_animalOwner_userID);
        edTxt_animalOwner_userID.setVisibility(View.GONE);
        //
        edTxt_animalID = (EditText) findViewById(R.id.edTxt_animalID);
        edTxt_animalID.setVisibility(View.GONE);
        //
        edTxt_animalDate = (EditText) findViewById(R.id.edTxt_animalDate);
        edTxt_animalDate.setVisibility(View.GONE);
        //***
        page_editPetData = this;
        //**************
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(btn_click);
        //**************
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(btn_click);
        //**************
        btnCamera = (Button) findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(btn_click);
        //**************
        imgBtn1 = (ImageButton) findViewById(R.id.imgBtn1);
        imgBtn1.setOnClickListener(btn_click);
        //**************
        imgBtn2 = (ImageButton) findViewById(R.id.imgBtn2);
        imgBtn2.setOnClickListener(btn_click);
        //**************
        imgBtn3 = (ImageButton) findViewById(R.id.imgBtn3);
        imgBtn3.setOnClickListener(btn_click);
        //**************
        imgBtn4 = (ImageButton) findViewById(R.id.imgBtn4);
        imgBtn4.setOnClickListener(btn_click);
        //**************
        imgBtn5 = (ImageButton) findViewById(R.id.imgBtn5);
        imgBtn5.setOnClickListener(btn_click);
        //**************
        btnAdoptCondition = (Button) findViewById(R.id.btnAdoptCondition);
        btnAdoptCondition.setOnClickListener(btn_click);
        iv_ImageButtonArray = new ImageButton[]{imgBtn1, imgBtn2, imgBtn3, imgBtn4, imgBtn5};
    }

    public String check確認是否欄位都有填寫() {

        //************
        String p_string_未填寫的欄位有哪些 = "尚未填寫以下欄位:\n";
        Log.d("原始長度", p_string_未填寫的欄位有哪些.length() + "");
        //*********
        p_string_未填寫的欄位有哪些 += edTxt_animalName.getText().toString().isEmpty() ? "寵物姓名\n" : "";
        p_string_未填寫的欄位有哪些 += edTxt_animalAge.getText().toString().isEmpty() ? "寵物年齡\n" : "";
        //p_string_未填寫的欄位有哪些 += edTxt_animalChip.getText().toString().isEmpty() ? "是否植入晶片\n" : "";
        p_string_未填寫的欄位有哪些 += edTxt_animalHealthy.getText().toString().isEmpty() ? "健康狀態\n" : "";
        p_string_未填寫的欄位有哪些 += spinner_animalArea.getSelectedItem().toString().equals("全部") ? "未選縣市\n" : "";
        p_string_未填寫的欄位有哪些 += edTxt_animalColor.getText().toString().isEmpty() ? "毛色\n" : "";
        //p_string_未填寫的欄位有哪些 += edTxt_animalDate.getText().toString().isEmpty() ? "送養日期\n" : "";
        p_string_未填寫的欄位有哪些 += edTxt_animalReason.getText().toString().isEmpty() ? "送養理由\n" : "";

        return p_string_未填寫的欄位有哪些;
    }

    private void delete資料(String p_animalId){

        Request request = new Request.Builder()
                .url("http://twpetanimal.ddns.net:9487/api/v1/animalDatas/"+p_animalId)
                .addHeader("Content-Type", "raw")
                .delete()
                .build();

        Call call = Iv_OkHttp_client.newCall(request);
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
                //parseJson(json);

                Intent l_intent = new Intent(page_editPetData.this, iii.org.tw.getpet.AddOrEditPetData.class);
                startActivity(l_intent);
                finish();
            }
        });


    }


    @Override
    public void finish() {
        Intent l_intent = new Intent(page_editPetData.this, iii.org.tw.getpet.AddOrEditPetData.class);
        startActivity(l_intent);
        super.finish();
    }

    private void addAllDataToDBServer() {
        //******判斷使用者是否填寫領養條件

        if (iv_object_conditionOfAdoptPet_a == null) {
            iv_object_conditionOfAdoptPet_a = new object_ConditionOfAdoptPet();
            iv_object_conditionOfAdoptPet_a.createAdefault_object_ConditionOfAdoptPet();
        }

        iv_ArrayList_object_ConditionOfAdoptPet.add(iv_object_conditionOfAdoptPet_a);
        //*********
        object_petDataForSelfDB l_PetData_PetObj = new object_petDataForSelfDB();
        l_PetData_PetObj.setAnimalAddress(spinner_animalArea.getSelectedItem().toString());
        l_PetData_PetObj.setAnimalAge(edTxt_animalAge.getText().toString());
        l_PetData_PetObj.setAnimalKind(spinner_animalKind.getSelectedItem().toString());
        l_PetData_PetObj.setAnimalType(spinner_animalType.getSelectedItem().toString());
        l_PetData_PetObj.setAnimalBirth(spinner_animalBirth.getSelectedItem().toString());
        l_PetData_PetObj.setAnimalChip(spinner_animalChip.getSelectedItem().toString());
        l_PetData_PetObj.setAnimalColor(edTxt_animalColor.getText().toString());
        l_PetData_PetObj.setAnimalDate(edTxt_animalDate.getText().toString());
        l_PetData_PetObj.setAnimalDisease_Other(edTxt_animalDisease_Other.getText().toString());
        l_PetData_PetObj.setAnimalGender(spinner_animalGender.getSelectedItem().toString());
        l_PetData_PetObj.setAnimalHealthy(edTxt_animalHealthy.getText().toString());
        l_PetData_PetObj.setAnimalName(edTxt_animalName.getText().toString());
        l_PetData_PetObj.setAnimalNote(edTxt_animalNote.getText().toString());
        l_PetData_PetObj.setAnimalReason(edTxt_animalReason.getText().toString());
        l_PetData_PetObj.setAnimalData_Condition(iv_ArrayList_object_ConditionOfAdoptPet);
        l_PetData_PetObj.setAnimalData_Pic(iv_ArrayList_object_OfPictureImgurSite);
        l_PetData_PetObj.setAnimalOwner_userID(iv_object_petDataForSelfDB.getAnimalOwner_userID());
        //****************
        Gson l_gsn_gson = new Gson();
        String l_strPetDataObjToJSONString = l_gsn_gson.toJson(l_PetData_PetObj);
        //***********
        RequestBody requestBody = RequestBody.create(Iv_MTyp_JSON, l_strPetDataObjToJSONString);

        //***************
        Request request = new Request.Builder()
                .url("http://twpetanimal.ddns.net:9487/api/v1/AnimalDatas")
                .addHeader("Content-Type", "raw")
                .post(requestBody)
                .build();

        Call call = Iv_OkHttp_client.newCall(request);
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONObject jObj = new JSONObject(json);
                            String id = jObj.getString("animalID");
                            //Toast.makeText(page_editPetData.this, "此筆資料的id: " + id + ")", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

                //parseJson(json);
            }
        });


        //*******************
        Intent l_intent = new Intent(this, iii.org.tw.getpet.AddOrEditPetData.class);
        startActivity(l_intent);
        finish();


    }



    private void uploadImageAndGetSiteBack() throws Exception {

        //***************

        //**********
        iv_int_countHowManyPicNeedUpload = 0;

        iv_ADialog_a.dismiss();
        {
            for (int i = 0; i < selectedImgForUploadArray.length; i++) {

                if (selectedImgForUploadArray[i] == true) {
                    iv_int_countHowManyPicNeedUpload += 1;


                }
            }

        }

        //********

        CountDownLatch latch = new CountDownLatch(iv_int_countHowManyPicNeedUpload);//N个工人的协作

        Log.d("", "進入uploadImageAndGetSiteBack");


        for (int i = 0; i < selectedImgForUploadArray.length; i++) {

            if (selectedImgForUploadArray[i] == true) {

                // Toast.makeText(ScrollingActivity.this, selectedImgForUploadArray[i]==true? "True: "+i:"sFalse : "+i, Toast.LENGTH_SHORT).show();

                //Drawable drawable = imgBtnArray[i].getBackground();
                //bitmapArray[i] = ((BitmapDrawable) imgBtnArray[i].getDrawable()).getBitmap();
                String bitmapStream = transBitmapToStream(bitmapArray[i]);
                //imgurUpload(bitmapStream);
                Log.d(" 進入迴圈", String.valueOf(selectedImgForUploadArray.length));
                uploadImgByCallable l_uploadImgByCallable = new uploadImgByCallable(bitmapStream, latch);
                l_uploadImgByCallable.start();
            }

        }

        latch.await();
        Log.d(" await完畢", " ");

    }


    public String create取得現在時間字串() {

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        Date date = new Date();
        String strDate = sdFormat.format(date);
        return strDate;
    }

    private String transBitmapToStream(Bitmap myBitmap) {
        Bitmap bitmap = myBitmap; //程式寫在後面

        //將 Bitmap 轉為 base64 字串
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapData = bos.toByteArray();
        String imageBase64 = Base64.encodeToString(bitmapData, Base64.DEFAULT);
        return imageBase64;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class uploadImgByCallable extends Thread {
        String image;
        CountDownLatch latch;

        public uploadImgByCallable(String p_image, CountDownLatch p_latch) {
            this.image = p_image;
            this.latch = p_latch;
        }

        @Override
        public void run() {
            Log.d(" 進入線程", " 進入線程");


            imgurUploadInClass(image);

        }


        private void imgurUploadInClass(final String image) { //插入圖片
            Log.d(" 進入imgurUpload", " 進入imgurUpload");
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            Date date = new Date();
            String strDate = sdFormat.format(date);
            Log.d(" 1進入imgurUpload", " 進入imgurUpload");

            //String urlString = "https://imgur-apiv3.p.mashape.com/3/image/";
            String urlString = "https://imgur-apiv3.p.mashape.com/3/image";
            String mashapeKey = ""; //設定自己的 Mashape Key
            String clientId = ""; //設定自己的 Clinet ID
            String titleString = "GetPet" + strDate; //設定圖片的標題


            SyncHttpClient client0 = new SyncHttpClient();
            client0.addHeader("X-Mashape-Key", mashapeKey);
            client0.addHeader("Authorization", "Client-ID " + clientId);
            client0.addHeader("Content-Type", "application/x-www-form-urlencoded");

            RequestParams params = new RequestParams();
            params.put("title", titleString);
            params.put("image", image);
            Log.d(" 準備POST", " ");

            client0.post(urlString, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    if (!response.optBoolean("success") || !response.has("data")) {
                        Log.d("editor", "response: " + response.toString());
                        return;
                    }

                    JSONObject data = response.optJSONObject("data");
                    String link = data.optString("link", "");
                    int width = data.optInt("width", 0);
                    int height = data.optInt("height", 0);
                    Log.d("imgSite", link);
                    //**
                    object_OfPictureImgurSite l_object_OfPictureImgurSite = new object_OfPictureImgurSite(data.optString("link"));
                    iv_ArrayList_object_OfPictureImgurSite.add(l_object_OfPictureImgurSite);
                    latch.countDown();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject error) {

                    Log.d("上傳圖片失敗", "");
                    Log.d("上傳圖片失敗", "");
                }
            });
        }
    }


    Button btnAdoptCondition;
    Button btnEdit;
    Button btnDelete;
    Button btnCamera;
    //*********
    ImageButton imgBtn1;
    ImageButton imgBtn2;
    ImageButton imgBtn3;
    ImageButton imgBtn4;
    ImageButton imgBtn5;
    ImageButton[] imgBtnArray = {imgBtn1, imgBtn2, imgBtn3, imgBtn4, imgBtn5};
    //*********************
    EditText edTxt_animalOwner_userID;
    EditText edTxt_animalID;
    EditText edTxt_animalDate;
    //***********
    EditText edTxt_animalBirth;
    EditText edTxt_animalChip;
    EditText edTxt_animalAddress;
    EditText edTxt_animalGender;
    EditText edTxt_animalGetter_userID;
    EditText edTxt_animalAdopted;
    EditText edTxt_animalAdoptedDate;
    //*********************
    EditText edTxt_animalName;

    EditText edTxt_animalAge;
    EditText edTxt_animalColor;
    EditText edTxt_animalHealthy;
    EditText edTxt_animalDisease_Other;
    EditText edTxt_animalReason;
    EditText edTxt_animalNote;
    //***********************
    Spinner spinner_animalArea;
    Spinner spinner_animalKind;
    Spinner spinner_animalType;
    Spinner spinner_animalGender;
    Spinner spinner_animalChip;
    Spinner spinner_animalBirth;
    //*******


}
