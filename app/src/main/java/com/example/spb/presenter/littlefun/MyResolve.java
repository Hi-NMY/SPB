package com.example.spb.presenter.littlefun;

import android.widget.ImageButton;
import com.example.spb.entity.Bar;
import com.example.spb.entity.ImageDouble;
import com.example.spb.entity.Topic;

import java.util.ArrayList;
import java.util.List;

public class MyResolve {

    public static StringBuffer OutTopic(List<Topic> topics){
        StringBuffer stringBuffer = new StringBuffer();
        if (topics.size()  == 0){
            return stringBuffer.append("");
        }else {
            for (Topic t : topics){
                stringBuffer.append(t.getTopic_name() + "&");
            }
            return stringBuffer;
        }
    }

    public static List<Topic> InTopic(String date){
        List<Topic> topics = new ArrayList<>();
        String[] s = date.split("&");
        for (int a = 0; a < s.length; a++) {
            Topic topic = new Topic();
            topic.setTopic_name(s[a]);
            topics.add(topic);
        }
        return topics;
    }

    public static StringBuffer OutDoubleImage(List<ImageDouble> imageDoubles){
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer1 = new StringBuffer();
        if (imageDoubles == null || imageDoubles.size() == 0){
            return stringBuffer.append("");
        }else {
            for (ImageDouble i : imageDoubles){
                stringBuffer.append(i.getMinPath() + "&");
                stringBuffer1.append(i.getMaxPath() + "&");
            }
            return stringBuffer.append("@" + stringBuffer1);
        }
    }

    public static List<ImageDouble> InDoubleImage(String date){
        List<ImageDouble> imageDoubles = new ArrayList<>();
        String[] one = date.split("@");
        String[] two = one[0].split("&");
        String[] three = one[1].split("&");
        for (int a = 0 ; a < two.length ; a++){
            ImageDouble imageDouble = new ImageDouble();
            imageDouble.setMinPath(two[a]);
            imageDouble.setMaxPath(three[a]);
            imageDoubles.add(imageDouble);
        }
        return imageDoubles;
    }

    public static Bar InBar(Bar bar,String date){
        String[] one = date.split("&");
        bar.setPb_one_id(one[0]);
        bar.setPb_image_url(one[1]);
        bar.setPb_date(one[2]);
        bar.setPb_voice(one[3]);
        bar.setPb_thumb_num(0);
        bar.setPb_comment_num(0);
        return bar;
    }
}
