package com.rockchips.tvlauncher.view;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rockchips.tvlauncher.R;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by GaoFei on 2017/6/7.
 */

public class FunctionItemView extends LinearLayout {

    @ViewInject(R.id.img_icon)
    private ImageView mImgIcon;
    @ViewInject((R.id.text_title))
    private TextView mTextTitle;

    public FunctionItemView(Context context){
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        init(context);
    }
    private void init(Context context){
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_item_function, this, false);
        addView(itemView);
        x.view().inject(this, this);
    }

    public void setImgRes(int resID){
        mImgIcon.setImageResource(resID);
    }

    public void setTitle(String title){
        mTextTitle.setText(title);
    }

}
