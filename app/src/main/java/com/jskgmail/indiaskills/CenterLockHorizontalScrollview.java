package com.jskgmail.indiaskills;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class CenterLockHorizontalScrollview extends HorizontalScrollView {
	Context context;
	int prevIndex = 0;

	int preBookmarkIndex=-1;

	public CenterLockHorizontalScrollview(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.setSmoothScrollingEnabled(true);

	}

	public void setAdapter(Context context, CustomListAdapter mAdapter) {

		try {
			fillViewWithAdapter(mAdapter);
		} catch (ZeroChildException e) {

			e.printStackTrace();
		}
	}

	private void fillViewWithAdapter(CustomListAdapter mAdapter)
			throws ZeroChildException {
		if (getChildCount() == 0) {
			throw new ZeroChildException(
			        "CenterLockHorizontalScrollView must have one child");
		}
		if (getChildCount() == 0 || mAdapter == null)
			return;

		ViewGroup parent = (ViewGroup) getChildAt(0);

		parent.removeAllViews();

		for (int i = 0; i < mAdapter.getCount(); i++) {
			parent.addView(mAdapter.getView(i, null, parent));
		}
	}

	public void setCenter(int index,int is_bookmarked) {

		ViewGroup parent = (ViewGroup) getChildAt(0);

		View preView = parent.getChildAt(prevIndex);
		//if (!String.valueOf(preView.getBackground()).equals("android.graphics.drawable.ColorDrawable@8f0a669"))
     //   Log.e("pppppp", String.valueOf(preView.getDrawingCacheBackgroundColor()));
        if (preBookmarkIndex==prevIndex)
            preView.setBackgroundColor(Color.YELLOW);
            else
            preView.setBackgroundColor(Color.GREEN);

		android.widget.LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(5, 5, 5, 5);
		preView.setLayoutParams(lp);

		View view = parent.getChildAt(index);
		view.setBackgroundColor(Color.RED);

		int screenWidth = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getWidth();

		int scrollX = (view.getLeft() - (screenWidth / 2))
				+ (view.getWidth() / 2);
		this.smoothScrollTo(scrollX, 0);
		prevIndex = index;

	}


    public void setBookmark(int index,int is_bookmarked) {

        ViewGroup parent = (ViewGroup) getChildAt(0);

        View preView = parent.getChildAt(prevIndex);
       // preView.setBackgroundColor(Color.GREEN);
        if(is_bookmarked==1) {
            preView.setBackgroundColor(Color.YELLOW);
            preBookmarkIndex = prevIndex;
        }
        android.widget.LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 5, 5, 5);
        preView.setLayoutParams(lp);



    }








}
