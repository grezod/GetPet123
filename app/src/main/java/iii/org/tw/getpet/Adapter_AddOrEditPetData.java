package iii.org.tw.getpet;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by poloi on 2017/1/30.
 */

public class Adapter_AddOrEditPetData extends BaseAdapter {
    private LayoutInflater iv_LayoutInflater_myInflater;
    private List<simplePetData> iv_List_simplePetDatas;
    private Context context;
    simplePetData iv_simplePetData;

    public Adapter_AddOrEditPetData(Context p_context,List<simplePetData> p_List_simplePetDatas) {
        iv_LayoutInflater_myInflater = LayoutInflater.from(p_context);
        this.iv_List_simplePetDatas = p_List_simplePetDatas;
        this.context = p_context;
    }

    @Override
    public int getCount() {
        return iv_List_simplePetDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return iv_List_simplePetDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return iv_List_simplePetDatas.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View p_convertView, ViewGroup parent) {
        //************
        ViewHolder l_holder = null;
        if(p_convertView==null){
            p_convertView = iv_LayoutInflater_myInflater.inflate(R.layout.adapter_add_or_edit_pet_data, null);
            l_holder = new ViewHolder(
                    (ImageView) p_convertView.findViewById(R.id.imageView),
                    (TextView) p_convertView.findViewById(R.id.title1),
                    (TextView) p_convertView.findViewById(R.id.content)
            );
            p_convertView.setTag(l_holder);
        }else{
            l_holder = (ViewHolder) p_convertView.getTag();
        }
        //***********
         iv_simplePetData = (simplePetData)getItem(position);
        //***********

        String imgURL = iv_simplePetData.getImageUrl();
        if(imgURL.length()>0){
            Glide.with(context).load(imgURL).into(l_holder.l_imageView);
            //Picasso.with(context).load(imgURL).into(ivImage);
        }
        //**
        l_holder.l_txtTitle.setText(iv_simplePetData.getTitle());
        l_holder.l_txtContent.setText(iv_simplePetData.getContent());
        //***


        return p_convertView;
    }

    //************


    //**

    //****
    private class ViewHolder {
        TextView l_txtTitle;
        TextView l_txtContent;
        ImageView l_imageView;
        public ViewHolder(ImageView p_imageView,TextView p_txtTitle, TextView p_txtTime){
            l_imageView = p_imageView;
            this.l_txtTitle = p_txtTitle;
            this.l_txtContent = p_txtTime;
        }
    }
    //*********
}
