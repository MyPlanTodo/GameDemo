package com.example.administrator.gamedemo.activity.image;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.administrator.gamedemo.R;
import com.example.administrator.gamedemo.utils.ToolUtil;
import com.example.administrator.gamedemo.utils.base_image.PhotoBrowseInfo;
import com.example.administrator.gamedemo.utils.base_image.PhotoBrowseUtil;
import com.example.administrator.gamedemo.widget.ImageLoadMnanger;
import com.example.administrator.gamedemo.widget.imageview.GalleryPhotoView;
import com.example.administrator.gamedemo.widget.imageview.HackyViewPager;
import com.orhanobut.logger.Logger;
import java.util.LinkedList;
import java.util.List;
import uk.co.senab.photoview_2.PhotoView;
import uk.co.senab.photoview_2.PhotoViewAttacher;


/**
 * Created by 大灯泡 on 2016/12/16.
 * <p>
 * 朋友圈图片浏览控件
 */
// TODO: 2016/12/16 退场动画结束后，图片与原图片重叠后的图片跳动问题，以及滑动过后图片无法返回到原来位置的问题
public class PhotoBrowseActivity extends Activity {
    private static final String TAG = "PhotoBrowseActivity";

    private HackyViewPager photoViewpager;
    private View blackBackground;
    private List<GalleryPhotoView> viewBuckets;
    private PhotoBrowseInfo photoBrowseInfo;
    private InnerPhotoViewerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_browse);
        preInitData();
        initView();
    }



    private void preInitData() {
        photoBrowseInfo = getIntent().getParcelableExtra("photoinfo");
        viewBuckets = new LinkedList<>();
        final int photoCount = photoBrowseInfo.getPhotosCount();
        for (int i = 0; i < photoCount; i++) {
            GalleryPhotoView photoView = new GalleryPhotoView(this);
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    finish();
                }
            });
            viewBuckets.add(photoView);
        }
    }

    private void initView() {
        photoViewpager = (HackyViewPager) findViewById(R.id.photo_viewpager);
        blackBackground = findViewById(R.id.v_background);

        adapter = new InnerPhotoViewerAdapter(this);
        photoViewpager.setAdapter(adapter);
        photoViewpager.setLocked(photoBrowseInfo.getPhotosCount() == 1);
        photoViewpager.setCurrentItem(photoBrowseInfo.getCurrentPhotoPosition());
    }

    @Override
    protected void onDestroy() {
        if (!ToolUtil.isListEmpty(viewBuckets)) {
            for (PhotoView photoView : viewBuckets) {
                photoView.destroy();
            }
        }
        Glide.get(this).clearMemory();
        super.onDestroy();
    }

    //=============================================================Tools method
    public static void startToPhotoBrowseActivity(Activity from, @NonNull PhotoBrowseInfo info) {
        if (info == null || !info.isValided()) return;
        Intent intent = new Intent(from, PhotoBrowseActivity.class);
        intent.putExtra("photoinfo", info);
        from.startActivity(intent);
        //禁用动画
        from.overridePendingTransition(0, 0);
    }

    @Override
    public void finish() {
        final PhotoView currentPhotoView = viewBuckets.get(photoViewpager.getCurrentItem());
        if (currentPhotoView == null) {
            Logger.d("childView is null");
            super.finish();
            return;
        }
        final Rect startRect = new Rect();
        startRect.set(PhotoBrowseUtil.calcuateDrawableBounds(currentPhotoView));
        final Rect endRect = photoBrowseInfo.getViewLocalRects().get(photoViewpager.getCurrentItem());
        PhotoBrowseUtil.playExitAnima(currentPhotoView, blackBackground, startRect, endRect, new PhotoBrowseUtil.OnAnimaEndListener() {
            @Override
            public void onAnimaEnd(Animator animator) {
                PhotoBrowseActivity.super.finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    //=============================================================InnerAdapter

    private class InnerPhotoViewerAdapter extends PagerAdapter {
        private Context context;
        private boolean isFirstInitlize;

        public InnerPhotoViewerAdapter(Context context) {
            this.context = context;
            isFirstInitlize = true;
        }

        @Override
        public int getCount() {
            return photoBrowseInfo.getPhotosCount();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            GalleryPhotoView photoView = viewBuckets.get(position);
            photoView.setCleanOnDetachedFromWindow(false);
            String photoUrl = photoBrowseInfo.getPhotoUrls().get(position);
            ImageLoadMnanger.INSTANCE.loadImageDontAnimate(photoView, photoUrl);
            container.addView(photoView);
            return photoView;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, final int position, final Object object) {
            if (isFirstInitlize && object instanceof GalleryPhotoView && position == photoBrowseInfo.getCurrentPhotoPosition()) {
                isFirstInitlize = false;
                final GalleryPhotoView targetView = (GalleryPhotoView) object;
                final Rect startRect = photoBrowseInfo.getViewLocalRects().get(position);
                targetView.playEnterAnima(startRect, null);
            }
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
