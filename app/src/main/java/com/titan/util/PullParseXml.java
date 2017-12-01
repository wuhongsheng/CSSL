package com.titan.util;

import android.util.Xml;

import com.titan.model.Row;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PullParseXml {

    public List<Row> PullParseXML(InputStream inStream, String tablename) {
        List<Row> list = null;
        Row row = null;
        // 构建XmlPullParserFactory
        try {
            // XmlPullParserFactory pullParserFactory =
            // XmlPullParserFactory.newInstance();
            // 获取XmlPullParser的实例
            // XmlPullParser xmlPullParser = pullParserFactory.newPullParser();
            XmlPullParser xmlPullParser = Xml.newPullParser();
            // 设置输入流 xml文件
            xmlPullParser.setInput(inStream, "UTF-8");

            // 开始
            int eventType = xmlPullParser.getEventType();
            String tabname = null;
            try {
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String nodeName = xmlPullParser.getName();
                    switch (eventType) {
                        // 文档开始
                        case XmlPullParser.START_DOCUMENT:
                            list = new ArrayList<Row>();
                            break;
                        // 开始节点
                        case XmlPullParser.START_TAG:
                            // 判断如果其实节点为dic
                            if ("dic".equals(nodeName)) {
                                tabname = xmlPullParser.getAttributeValue(0);
                            } else if ("row".equals(nodeName) && tablename.equals(tabname)) {
                                row = new Row();
                                row.setId(xmlPullParser.getAttributeValue(0));
                                row.setName(xmlPullParser.getAttributeValue(1));
                                list.add(row);
                            }
                            break;
                        // 结束节点
                        case XmlPullParser.END_TAG:
                            if ("dic".equals(nodeName) && tablename.equals(tabname)) {
                                row = null;
                                return list;
                            }
                            break;
                        default:
                            break;
                    }
                    eventType = xmlPullParser.next();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return list;
    }
}
