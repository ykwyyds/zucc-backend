package me.zhengjie.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 课程相关工具类
 */
public class CourseUtil {
    public static int[] courseCount=new int[]{1,2,3,4,5,6,7,8,9,10,11};
    public static String[] courseTime=new String[]{
            "08:00~08:50","08:50~09:50","09:50~10:40","10:40~11:40","11:40~12:25","13:30~14:20","14:20~15:20","15:20~16:10","16:10~17:10","18:30~19:20","19:20~20:05"
    };
    public static int getWeekOfDate(Date date) {
        int[] weekOfDaysNum = { 7, 1, 2, 3, 4, 5, 6 };
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDaysNum[w];
    }
    //检测当前时间是否在上课中
    public static boolean checkInCourse(List<Integer> todayCourseCount){
        boolean b=false;
        Date now=new Date();
        int week=getWeekOfDate(now);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String hhmm= sdf.format(now);
//        if(week>=1 && week<=5){
        for(int c:todayCourseCount){
            String time=courseTime[c-1];
            String[] times=time.split("~");
            String begin=times[0];
            String end=times[1];
            if(hhmm.compareTo(begin)>0 && hhmm.compareTo(end)<0){
                return true;
            }
        }
//        }
        return b;
    }
}
