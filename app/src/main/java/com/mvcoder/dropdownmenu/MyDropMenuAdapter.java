package com.mvcoder.dropdownmenu;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;

import com.mvcoder.filter.adapter.MenuAdapter;
import com.mvcoder.filter.adapter.SimpleTextAdapter;
import com.mvcoder.filter.interfaces.OnFilterDoneListener;
import com.mvcoder.filter.interfaces.OnFilterItemClickListener;
import com.mvcoder.filter.typeview.SingleListView;
import com.mvcoder.filter.util.UIUtil;
import com.mvcoder.filter.view.FilterCheckedTextView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MyDropMenuAdapter implements MenuAdapter {

    private final Context mContext;
    private OnFilterDoneListener onFilterDoneListener;
    private String[] titles;
    private Map<Integer,List<String>> listMap;
    private int[] defaultIndexs;

    public MyDropMenuAdapter(Context context, String[] titles, OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.titles = titles;
        this.onFilterDoneListener = onFilterDoneListener;
        defaultIndexs = new int[titles.length];
    }

    @Override
    public int getMenuCount() {
        return titles.length;
    }

    @Override
    public String getMenuTitle(int position) {
        return titles[position];
    }

    @Override
    public int getBottomMargin(int position) {
        if (position == 3) {
            return 0;
        }
        return UIUtil.dp(mContext, 140);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);
        switch (position) {
            case 0:
                view = createSingleListView(position);
                break;
            case 1:
                view = createSingleListView(0);
                break;
            case 2:
                view = createSingleListView(0);
                break;
        }

        return view;
    }

    private View createSingleListView(final int position) {
        String[] flavours = new String[]{"全部","甜品","粤菜","西餐"};
        List<String> stringList = Arrays.asList(flavours);
        SingleListView<String> singleListView = new SingleListView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String string) {
                        return string;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark2));
                        checkedTextView.setTextColor(Color.WHITE);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item ,int index) {
                        FilterUrl.instance().singleListPosition = item;
                        FilterUrl.instance().position = position;
                        FilterUrl.instance().positionTitle = item;
                        FilterUrl.instance().indexInList = index;

                        onFilterDone(position,item,item, index);
                    }
                });

        /*List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            list.add("" + i);
        }*/
        //String[] dateList = mContext.getResources().getStringArray(arrayId);
        singleListView.setList(stringList, defaultIndexs[position]);
        return singleListView;
    }

    private <T> void onFilterDone(int position, String title, T item, int index) {
        if (onFilterDoneListener != null) {
            onFilterDoneListener.onFilterDone(position, title, item, index);
        }
    }

    @Override
    public void setItemListMap(Map<Integer, List<String>> listMap) {

    }
}
