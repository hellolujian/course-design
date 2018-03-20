package cn.edu.ujs.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2018/1/11.
 */
public class AdminUtil {

    /**
     * 将字符串[1,2,3]转化为List<Integer>
     * @param idList
     * @return
     */
    public static List<Integer> stringToList(String idList) {
        String[] ids = idList.substring(1, idList.length() - 1).split(",");
        List<Integer> integerList = new ArrayList<>();
        for (String id : ids) {
            integerList.add(new Integer(id));
        }
        return integerList;
    }
}
