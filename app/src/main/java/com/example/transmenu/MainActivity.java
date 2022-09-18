package com.example.transmenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCam, btnTrans;
    ImageView imgView;
    Context context;
    LinearLayout layout1, layout2;
    String FoodName;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCam = (Button) findViewById(R.id.cam_btn);
        imgView = (ImageView) findViewById(R.id.imgView);
        btnTrans = (Button)findViewById(R.id.read_btn);
        btnCam.setOnClickListener(this);
        btnTrans.setOnClickListener(this);
        context = this;
        layout1 = (LinearLayout)findViewById(R.id.restLayout1);
        layout2 = (LinearLayout)findViewById(R.id.restLayout2);

        mFirebaseAuth = FirebaseAuth.getInstance();

        Integer[] B_RestID= {R.drawable.b_rest1, R.drawable.b_rest2, R.drawable.b_rest3, R.drawable.b_rest4};
        Integer[] L_RestID ={R.drawable.l_rest1, R.drawable.l_rest2, R.drawable.l_rest3, R.drawable.l_rest4};


        //중문 식당 리스트
        int i;
        for(i=0;i<4;i++){
            ImageButton imgbtn1 =new ImageButton(context);
            ImageButton imgbtn2 =new ImageButton(context);

            imgbtn1.setLayoutParams(new ViewGroup.LayoutParams(375,280));
            imgbtn1.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imgbtn1.setPadding(20, 20, 20, 20);
            imgbtn1.setBackgroundColor(Color.argb(0, 0, 0, 0));
            imgbtn1.setImageResource(B_RestID[i]);
            layout1.addView(imgbtn1);

            imgbtn2.setLayoutParams(new ViewGroup.LayoutParams(375,280));
            imgbtn2.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imgbtn2.setPadding(20, 20, 20, 20);
            imgbtn2.setBackgroundColor(Color.argb(0, 0, 0, 0));
            imgbtn2.setImageResource(L_RestID[i]);
            layout2.addView(imgbtn2);

            switch (i){
                case 0:
                    imgbtn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/entry/place/1357121810?c=14188171.3751451,4387841.9271084,15,0,0,0,dh"));
                            startActivity(urlintent);
                        }
                    });

                    imgbtn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/entry/place/17401759?c=14188572.2811593,4388325.1044778,16,0,0,0,dh"));
                            startActivity(urlintent);
                        }
                    });
                    break;
                case 1:
                    imgbtn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/entry/place/1408991939?c=14188171.3751451,4387841.9271084,15,0,0,0,dh"));
                            startActivity(urlintent);
                        }
                    });

                    imgbtn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/entry/place/17341916?c=14187949.7714348,4387958.0480442,16,0,0,0,dh"));
                            startActivity(urlintent);
                        }
                    });
                    break;
                case 2:
                    imgbtn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/entry/place/31197391?c=14188171.3751451,4387841.9271084,15,0,0,0,dh"));
                            startActivity(urlintent);
                        }
                    });

                    imgbtn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/entry/place/1707433717?c=14188315.1331356,4388032.0964338,16,0,0,0,dh"));
                            startActivity(urlintent);
                        }
                    });
                    break;
                case 3:
                    imgbtn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/entry/place/38474519?c=14188171.3751451,4387841.9271084,15,0,0,0,dh"));
                            startActivity(urlintent);
                        }
                    });

                    imgbtn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/entry/place/38632509?c=14188315.1331356,4388032.0964338,16,0,0,0,dh"));
                            startActivity(urlintent);
                        }
                    });
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cam_btn:
                //사진 촬영
                Intent i1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i1,0);
                break;
            case R.id.read_btn:
                //메뉴판 읽기(텍스트 추출) 버튼 클릭 시 OCR 작동 및 텍스트 추출
                FoodName = "콩나물국밥"; //임의로 추출된 텍스트, OCR 이후 추출된 텍스트를 넣는다.
                //텍스트(음식 이름)를 넘기면 해당 음식에 대한 재료 크롤링(FoodActivity)
                Intent i2 = new Intent(MainActivity.this, FoodActivity.class);
                i2.putExtra("code",FoodName);
                startActivity(i2);
        }
    }


    //사진 띄우기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imgBitmap);
        }
    }


    //종료 확인 메시지
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit Confirm");
        builder.setMessage("Are you sure you want to exit the app?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mFirebaseAuth.signOut();
                finish();
                ActivityCompat.finishAffinity(MainActivity.this);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}