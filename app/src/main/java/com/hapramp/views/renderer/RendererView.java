package com.hapramp.views.renderer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hapramp.R;
import com.hapramp.editor.FontSize;
import com.hapramp.steem.FeedDataConstants;
import com.hapramp.steem.PostStructureModel;
import com.hapramp.steem.models.data.FeedDataItemModel;
import com.hapramp.views.types.BlockquoteTypeView;
import com.hapramp.views.types.BulletTypeView;
import com.hapramp.views.types.HeadingOneTypeView;
import com.hapramp.views.types.HeadingThreeTypeView;
import com.hapramp.views.types.HeadingTwoTypeView;
import com.hapramp.views.types.HeadingFourTypeView;
import com.hapramp.views.types.HorizontalDividerTypeView;
import com.hapramp.views.types.ImageTypeView;
import com.hapramp.views.types.TextTypeView;
import com.hapramp.views.types.YoutubeVideoTypeView;

import java.util.List;

/**
 * Created by Ankit on 4/8/2018.
 */

public class RendererView extends FrameLayout {

    private Context mContext;
    private LinearLayout container;

    public RendererView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public RendererView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RendererView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View parentView = LayoutInflater.from(mContext).inflate(R.layout.renderer_view, this);
        container = parentView.findViewById(R.id.container);
    }

    public void render(PostStructureModel postStructureModel) {

        //todo show rendering progress bar

        List<FeedDataItemModel> dataSeries = postStructureModel.getDataSeries();
        //loop for views
        for (int i = 0; i < dataSeries.size(); i++) {
            //check for view type
            View viewToAdd = getViewType(dataSeries.get(i));
            if (viewToAdd == null)
                continue;
            //add to parent view
            container.addView(viewToAdd, i,
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        //todo hide progress bar

    }


    private View getViewType(FeedDataItemModel data) {

        View view = null;
        switch (data.getContentType()) {

            case FeedDataConstants.ContentType.TEXT:
                TextTypeView textTypeView = new TextTypeView(mContext);
                textTypeView.setText(data.getContent());
                textTypeView.setTextSize(FontSize.NORMALTEXTSIZE);
                return textTypeView;

            case FeedDataConstants.ContentType.IMAGE:
                ImageTypeView imageTypeView = new ImageTypeView(mContext);
                imageTypeView.setImageInfo(data.getCaption() , data.getContent());
                return imageTypeView;

            case FeedDataConstants.ContentType.H1:
                HeadingOneTypeView headingTypeView1 = new HeadingOneTypeView(mContext);
                headingTypeView1.setText(data.getContent());
                headingTypeView1.setTextSize(FontSize.H1TEXTSIZE);
                return headingTypeView1;

            case FeedDataConstants.ContentType.H2:
                HeadingTwoTypeView headingTypeView2 = new HeadingTwoTypeView(mContext);
                headingTypeView2.setText(data.getContent());
                headingTypeView2.setTextSize(FontSize.H2TEXTSIZE);
                return headingTypeView2;

            case FeedDataConstants.ContentType.H3:
                HeadingThreeTypeView headingTypeView3 = new HeadingThreeTypeView(mContext);
                headingTypeView3.setText(data.getContent());
                headingTypeView3.setTextSize(FontSize.H3TEXTSIZE);
                return headingTypeView3;

            case FeedDataConstants.ContentType.H4:
                HeadingFourTypeView headingTypeView4 = new HeadingFourTypeView(mContext);
                headingTypeView4.setText(data.getContent());
                return headingTypeView4;

            case FeedDataConstants.ContentType.UL:
                BulletTypeView bulletTypeView_UL = new BulletTypeView(mContext);
                bulletTypeView_UL.setBulletType(BulletTypeView.TYPE_UL);
                bulletTypeView_UL.setText(data.getContent());
                bulletTypeView_UL.setTextSize(FontSize.NORMALTEXTSIZE);
                return bulletTypeView_UL;

            case FeedDataConstants.ContentType.OL:
                BulletTypeView bulletTypeView_OL = new BulletTypeView(mContext);
                bulletTypeView_OL.setBulletType(BulletTypeView.TYPE_OL);
                bulletTypeView_OL.setText(data.getContent());
                bulletTypeView_OL.setTextSize(FontSize.NORMALTEXTSIZE);
                return bulletTypeView_OL;

            case FeedDataConstants.ContentType.BLOCKQUOTE:
                BlockquoteTypeView blockquoteTypeView = new BlockquoteTypeView(mContext);
                blockquoteTypeView.setText(data.getContent());
                return blockquoteTypeView;

            case FeedDataConstants.ContentType.HR:
                return new HorizontalDividerTypeView(mContext);

            case FeedDataConstants.ContentType.YOUTUBE:
                YoutubeVideoTypeView youtubeVideoTypeView = new YoutubeVideoTypeView(mContext);
                youtubeVideoTypeView.setVideoKey(data.getContent());
                return youtubeVideoTypeView;

            default:
                return view;

        }

    }


}
