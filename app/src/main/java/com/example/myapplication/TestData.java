package com.example.myapplication;

import android.graphics.PointF;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TestData
 *
 * @author: onlylemi
 */
public final class TestData {


    private TestData() {
    }

    public static List<Pair<Integer, PointF>> getNodesList() {
        List<Pair<Integer, PointF>> nodes = new ArrayList<>();
        List<PointF> nodesFirstFloor = new ArrayList<>();
        nodes.add(new Pair(1, new PointF(200, 160)));
        nodes.add(new Pair(1, new PointF(270, 160)));
        nodes.add(new Pair(1, new PointF(270, 250)));
        nodes.add(new Pair(1, new PointF(1350, 250)));
        nodes.add(new Pair(1, new PointF(1350, 160)));
        nodes.add(new Pair(1, new PointF(1420, 160)));
        nodes.add(new Pair(2, new PointF(200, 215)));
        nodes.add(new Pair(2, new PointF(300, 215)));
        nodes.add(new Pair(2, new PointF(300, 255)));
        nodes.add(new Pair(2, new PointF(1360, 255)));
        nodes.add(new Pair(2, new PointF(1360, 215)));
        nodes.add(new Pair(2, new PointF(1420, 215)));


        return nodes;
    }

    public static List<PointF> getNodesContactList() {
        List<PointF> nodesContact = new ArrayList<PointF>();
        nodesContact.add(new PointF(0, 1));
        nodesContact.add(new PointF(1, 2));
        nodesContact.add(new PointF(2, 3));
        nodesContact.add(new PointF(3, 4));
        nodesContact.add(new PointF(4, 5));
        nodesContact.add(new PointF(0, 6));
        nodesContact.add(new PointF(6, 7));
        nodesContact.add(new PointF(7, 8));
        nodesContact.add(new PointF(8, 9));
        nodesContact.add(new PointF(9, 10));
        nodesContact.add(new PointF(10, 11));
        nodesContact.add(new PointF(11, 5));
        return nodesContact;
    }

    public static Map<String, PointF> getMarks() {
        Map<String, PointF> marks = new HashMap();
        marks.put("192б", new PointF(350, 185));
        marks.put("189а", new PointF(350, 310));
        marks.put("189", new PointF(470, 310));
        marks.put("192а.1", new PointF(480, 185));
        marks.put("192а.2", new PointF(550, 185));
        marks.put("1 этаж Выход 1", new PointF(700, 400));
        marks.put("1 этаж Выход 2", new PointF(825, 400));
        marks.put("1 этаж Выход 3", new PointF(930, 400));
        marks.put("192.1", new PointF(650, 185));
        marks.put("192.2", new PointF(690, 185));
        marks.put("1 этаж Главная Лестница", new PointF(820, 185));
        marks.put("190.1", new PointF(930, 185));
        marks.put("190.2", new PointF(970, 185));
        marks.put("190.3", new PointF(1060, 185));
        marks.put("186", new PointF(1150, 185));
        marks.put("187б", new PointF(1260, 185));
        marks.put("187", new PointF(1150, 310));
        marks.put("187а", new PointF(1240, 310));
        marks.put("185", new PointF(1320, 310));
        marks.put("1 этаж Лестница 2", new PointF(1450, 160));
        marks.put("1 этаж Лестница 1", new PointF(200, 160));
        marks.put("191", new PointF(280, 310));
        marks.put("296", new PointF(305, 330));
        marks.put("297в", new PointF(470, 340));
        marks.put("298", new PointF(470, 215));
        marks.put("297б", new PointF(600, 340));
        marks.put("297а", new PointF(710, 340));
        marks.put("297", new PointF(950, 330));
        marks.put("299б", new PointF(1190, 330));
        marks.put("299ав", new PointF(1280, 310));
        marks.put("2 этаж Главная Лестница", new PointF(830, 185));
        marks.put("294.1", new PointF(665, 215));
        marks.put("294.2", new PointF(705, 215));
        marks.put("294а.1", new PointF(955, 215));
        marks.put("294а.2", new PointF(995, 215));
        marks.put("294б", new PointF(1080, 215));
        marks.put("294в", new PointF(1175, 215));
        marks.put("2 этаж Лестница 2", new PointF(1420, 215));
        marks.put("2 этаж Лестница 1", new PointF(200, 215));
        return marks;
    }

    public static List<String> getMarksName() {
        List<String> marksName = new ArrayList<>();
        Map<String,PointF> marks= TestData.getMarks();
        for (Map.Entry<String,PointF> mark : marks.entrySet()) {
            marksName.add(mark.getKey());
        }
        Collections.sort(marksName);
        return marksName;
    }
}