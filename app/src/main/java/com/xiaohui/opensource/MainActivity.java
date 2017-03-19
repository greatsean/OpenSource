package com.xiaohui.opensource;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.xiaohui.parser.LogUtil;
import com.xiaohui.parser.ParserTask;
import com.xiaohui.parser.TaskStatusListener;
import com.xiaohui.parser.impl.VolleyTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startListInvalidate(View view){
        startActivity(new Intent(this,ListInvalidateActivity.class));
    }
    public void startReq(View view){
        String regPath="http://api.test.putao.so/sbiz/movie/list?citycode=%E6%B7%B1%E5%9C%B3&";
        ParserTask task=new VolleyTask.Builder(regPath,MovieListResp.class)
//                .putParams("citycode","深圳")
                .setTaskStatusListener(new TaskStatusListener<MovieListResp>() {
            @Override
            public void onTaskStart() {

            }

            @Override
            public void onTaskFinish() {

            }

            @Override
            public void onTaskSuccess(MovieListResp movieListResp) {
                LogUtil.d("lxh",movieListResp.toString());
                Toast.makeText(MainActivity.this,movieListResp.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTaskFailure(Exception e) {

            }
        }).createTask();
        task.asyncParse();
    }
}
