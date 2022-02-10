package com.example.spb.presenter.utils;

import com.example.spb.entity.ImageDouble;
import com.example.spb.entity.Topic;

import java.util.ArrayList;
import java.util.List;

public class MyResolve {

    public static StringBuffer OutTopic(List<Topic> topics) {
        StringBuffer stringBuffer = new StringBuffer();
        if (topics.size() == 0) {
            return stringBuffer.append("");
        } else {
            for (Topic t : topics) {
                stringBuffer.append(t.getTopic_name()).append("|");
            }
            return stringBuffer;
        }
    }

    public static List<Topic> InTopic(String data) {
        List<Topic> topics = new ArrayList<>();
        String[] s = data.split("\\|");
        for (int a = 0; a < s.length; a++) {
            Topic topic = new Topic();
            topic.setTopic_name(s[a]);
            topics.add(topic);
        }
        return topics;
    }

    public static List<ImageDouble> InDoubleImage(String data) {
        List<ImageDouble> imageDoubles = new ArrayList<>();
        String[] one = data.split("@");
        String[] two = one[0].split("\\|");
        String[] three = one[1].split("\\|");
        for (int a = 0; a < two.length; a++) {
            ImageDouble imageDouble = new ImageDouble();
            imageDouble.setMinPath(two[a]);
            imageDouble.setMaxPath(three[a]);
            imageDoubles.add(imageDouble);
        }
        return imageDoubles;
    }

    public static List<String> InFaTag(String data) {
        List<String> tags = new ArrayList<>();
        String[] s = data.split("\\|");
        for (int a = 0; a < s.length; a++) {
            if (s[a] != null && !"".equals(s[a])) {
                tags.add(s[a]);
            }
        }
        return tags;
    }

    public static List<Integer> InSignDay(String data) {
        List<Integer> days = new ArrayList<>();
        String[] s = data.split("\\|");
        for (int a = 0; a < s.length; a++) {
            days.add(Integer.valueOf(s[a]));
        }
        return days;
    }

    public static StringBuffer OutSignDay(int data) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int a = 1; a <= data; a++) {
            stringBuffer.append(a + "|");
        }
        return stringBuffer;
    }

    public static List<String> InSignLike(String data) {
        List<String> id = new ArrayList<>();
        String[] s = data.split("&");
        for (int a = 0; a < s.length; a++) {
            id.add(s[a]);
        }
        return id;
    }

    public static List<String> InSignTopic(String data) {
        List<String> id = new ArrayList<>();
        String[] s = data.split("&");
        for (int a = 0; a < s.length; a++) {
            id.add(s[a]);
        }
        return id;
    }

    public static List<String> InBadge(String data) {
        List<String> badges = new ArrayList<>();
        String[] s = data.split("\\|");
        for (int a = 0; a < s.length; a++) {
            badges.add(s[a]);
        }
        return badges;
    }
}
