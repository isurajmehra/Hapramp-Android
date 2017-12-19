package com.hapramp.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hapramp.R;
import com.hapramp.utils.FontManager;

import static com.hapramp.utils.SkillsConverter.ACTION;
import static com.hapramp.utils.SkillsConverter.ART;
import static com.hapramp.utils.SkillsConverter.DANCE;
import static com.hapramp.utils.SkillsConverter.LITERATURE;
import static com.hapramp.utils.SkillsConverter.MUSIC;
import static com.hapramp.utils.SkillsConverter.PHOTOGRAPHY;
import static com.hapramp.utils.SkillsConverter.TRAVEL;

/**
 * Created by Ankit on 6/22/2017.
 */

public class SkillsItemView extends FrameLayout {



    ImageView skillsBgImage;
    TextView skillSelectionOverlay;
    TextView skillTitle;

    public SkillsItemView(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.skills_view, this);
        skillsBgImage = (ImageView) view.findViewById(R.id.skills_bg_image);
        skillSelectionOverlay = (TextView) view.findViewById(R.id.skill_selection_overlay);
        skillTitle = (TextView) view.findViewById(R.id.skill_title);
        skillSelectionOverlay.setTypeface(new FontManager().getTypeFace(FontManager.FONT_MATERIAL));
    }

    public void setSkillsBgImage(int type){

        int resId = -1;
        String color = "#795548";

        switch (type){
            case PHOTOGRAPHY:
                resId = R.drawable.photography_icon;
                color = "#f44336";
                break;
            case DANCE:
                resId = R.drawable.dance_icon;
                color = "#e91e63";
                break;
            case ACTION:
                resId = R.drawable.act_icon;
                color = "#9c27b0";
                break;
            case ART:
                resId = R.drawable.art_icon;
                color = "#3949ab";
                break;
            case MUSIC:
                resId = R.drawable.music_icon;
                color = "#009688";
                break;
            case TRAVEL:
                resId = R.drawable.travel_icon;
                color = "#607d8b";
                break;
            case LITERATURE:
                resId = R.drawable.literature_icon;
                color = "#ff5722";
                break;
        }

        skillsBgImage.setImageResource(resId);
        setOverlayColor(color);

    }

    private void setOverlayColor(String color){
        GradientDrawable background = (GradientDrawable) skillSelectionOverlay.getBackground();
        background.setColor(Color.parseColor(color));
    }

    public void setSelection(boolean selected){
        if(selected){
            skillSelectionOverlay.setVisibility(VISIBLE);
        }else{
            skillSelectionOverlay.setVisibility(GONE);
        }
    }

    public void setSkillTitle(String title){
        skillTitle.setText(title);
    }

}
