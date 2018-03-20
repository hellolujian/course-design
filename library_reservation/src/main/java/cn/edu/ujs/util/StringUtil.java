package cn.edu.ujs.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/1/14.
 */
public class StringUtil {

    //对于给定长度的数字自动补0返回字符串
    public static String getStringFormat(String format, Integer integer) {
        return String.format(format,integer);
    }

    //对于[1,2,3]这样的字符串返回List<Integer>
    public static List<Integer> getListByString(String listString) {
        String str = listString.substring(1,listString.length()-1);
        String[] ids = listString.substring(1,listString.length()-1).split(",");
        List<Integer> integerList = new ArrayList<>();
        for(String id : ids) {
            integerList.add(new Integer(id));
        }
        return integerList;
    }

    //对于["1-01-001","1-01-002"]这样的字符串返回List<String>
    public static List<String> getListBySeatString(String listString) {
        String str = listString.substring(1,listString.length()-1);
        String[] seatIds = listString.substring(1,listString.length()-1).split(",");
        List<String> seatIdList = new ArrayList<>();
        for (String seatId : seatIds) {
            seatIdList.add(seatId.substring(1,seatId.length()-1));
        }
        return seatIdList;
    }
}
