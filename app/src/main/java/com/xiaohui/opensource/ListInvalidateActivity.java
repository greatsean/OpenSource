package com.xiaohui.opensource;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListInvalidateActivity extends AppCompatActivity {
    Random random=new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_invalidate);
        ListView listView = (ListView) findViewById(R.id.listview);
        List<ItemBean> strings = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            strings.add(new ItemBean(false,"itemssss" + i));
        }
        listView.setAdapter(new ArrayAdapter<ItemBean>(this, 0, strings) {

            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Context context = parent.getContext();
                ViewHolder viewHolder=null;
                if (convertView==null){
                    convertView = View.inflate(context, R.layout.list_i, null);
                    viewHolder=new ViewHolder(convertView);
                }else
                {
                    viewHolder= (ViewHolder) convertView.getTag();
                }
                final ItemBean item = getItem(position);
                viewHolder.textView.setText(item.txt);
                if (item.exp){
                    viewHolder.button.setText("展开");
                    viewHolder.linearLayout.setVisibility(View.VISIBLE);
                    viewHolder.linearLayout.removeAllViews();

                    for (int i = 0; i < item.count; i++) {
                        TextView t= (TextView) View.inflate(context,android.R.layout.simple_list_item_1,null);
                        t.setText("hehhehh");
                        viewHolder.linearLayout.addView(t);
                    }

                }else{
                    viewHolder.linearLayout.setVisibility(View.GONE);
                    viewHolder.button.setText("收起");
                }
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        item.exp=!item.exp;
                        item.count++;
                        notifyDataSetChanged();
                    }
                });

                return convertView;
            }
        });
    }

    class ViewHolder {
        TextView textView;
        Button button;
        LinearLayout linearLayout;

        ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.txt_title);
            button = (Button) view.findViewById(R.id.btn_expand);
            linearLayout = (LinearLayout) view.findViewById(R.id.addlayout);
            view.setTag(this);
        }
    }

    class ItemBean {
        String txt;
        boolean exp;

        int count;
        public ItemBean(boolean exp, String txt) {
            this.exp = exp;
            this.txt = txt;
        }
    }
}
