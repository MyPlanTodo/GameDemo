package com.example.administrator.gamedemo.utils.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.gamedemo.model.MomentsInfo;
import com.example.administrator.gamedemo.model.Share;


/**
 * Created by lixu on 2016/12/12.
 *
 * 衹有文字的vh
 *
 */

public class TextOnlyMomentsVH extends ShareViewHolder {
    public TextOnlyMomentsVH(Context context,
                             ViewGroup viewGroup,
                             int layoutResId) {
        super(context, viewGroup, layoutResId);
    }

    @Override
    public void onFindView(@NonNull View rootView) {

    }

    @Override
    public void onBindDataToView(@NonNull Share data, int position, int viewType) {

    }



}
