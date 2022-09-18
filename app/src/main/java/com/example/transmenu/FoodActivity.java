package com.example.transmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class FoodActivity extends AppCompatActivity {
    Intent intent;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    String food_url, ing_url, food_name;
    TextView result_text;
    ImageView food_imView;
    Button review_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        result_text = (TextView)findViewById(R.id.result_kr);
        intent =getIntent();
        food_name =intent.getStringExtra("code");
        food_url = "https://www.10000recipe.com/recipe/list.html?q=" + food_name + "&order=reco&page=1";
        review_btn = (Button)findViewById(R.id.show_review);

        food_imView = findViewById(R.id.food_img);
        result_text.setText(food_name);
        /*
        * 음식 재료 크롤링해서 뿌리기
        * */

        recyclerView = findViewById(R.id.recyclerview_ingredient);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        getData();

        //리뷰 보러가기 버튼 클릭
        review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodActivity.this, ReviewActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getData(){
        FoodJsoup jsoupAsyncTask = new FoodJsoup();
        jsoupAsyncTask.execute();
    }

    private class FoodJsoup extends AsyncTask<Void, Void, Void> {
        ArrayList<String> listTitle = new ArrayList<>();    //재료 리스트

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc = (Document) Jsoup.connect(food_url).get();
                final Elements rank_list1 = doc.select("li.common_sp_list_li div.common_sp_thumb a");   //검색결과 리스트
                String tmp = rank_list1.attr("href");   //가장 첫번째(인기많은) 음식 코드
                ing_url = "https://www.10000recipe.com/" + tmp;     //음식 정보 링크

                Document doc2 = (Document) Jsoup.connect(ing_url).get();
                final Elements food = doc2.select("div.cont_ingre2 div.ready_ingre3 ul li a");  //재료 목록
                final Elements food_img = doc2.select("div.view2_pic div.centeredcrop img");    //이미 링크가 있는 태그
                String img = food_img.attr("src");  //음식 이미지 링크

                new DownloadFileTask().execute(img);


                Handler handler = new Handler(Looper.getMainLooper()); // 객체생성
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        int cnt=0;
                        String tmp;
                        //재료 정보 크롤링
                        for(Element element: food) {
                            Log.d("뭐가 있나", element.text() +"\n");   //가져온 재료 로그에서 확인
                            tmp = element.text();
                            if(tmp != "구매"){
                                Log.d("있나", element.text() +"\n");
                                listTitle.add(element.text());
                                cnt++;
                            }
                        }

                        for (int i = 0; i < cnt ; i++) {
                            if(listTitle.get(i) != "구매"){
                                FoodInfo data = new FoodInfo();
                                data.setNoTitle(listTitle.get(i));
                                adapter.addItem(data);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class DownloadFileTask extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bmp = null;
            try{
                String img_url = strings[0];
                URL url = new URL(img_url);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            return bmp;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap result){
            food_imView.setImageBitmap(result);
        }
    }


}