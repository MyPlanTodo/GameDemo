package com.example.administrator.gamedemo.utils.base;

import android.view.View;

/**
 * Created by 大灯泡 on 2016/11/1.
 */

public interface OnRecyclerViewItemClickListener<T> {
    void onItemClick(View v, int position, T data);
}
