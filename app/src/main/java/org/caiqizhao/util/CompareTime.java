package org.caiqizhao.util;

import android.content.Intent;

public class CompareTime {
    //时间格式年-月-日 时：分
    public static int compare_time(String time1,String time2){
        int time1_year = Integer.parseInt(time1.split(" ")[0].split("-")[0]);
        int time2_year = Integer.parseInt(time2.split(" ")[0].split("-")[0]);
//        String time
        if(time1_year>time2_year)
            return -1;
        else if (time1_year<time2_year)
            return 1;
        else {
            int time1_moth = Integer.parseInt(time1.split(" ")[0].split("-")[1]);
            int time2_moth = Integer.parseInt(time2.split(" ")[0].split("-")[1]);
            if(time1_moth>time2_moth)
                return -1;
            else if (time1_moth<time2_moth)
                return 1;
            else {
                int time1_day = Integer.parseInt(time1.split(" ")[0].split("-")[2]);
                int time2_day = Integer.parseInt(time2.split(" ")[0].split("-")[2]);
                if(time1_day>time2_day)
                    return -1;
                else if (time1_day<time2_day)
                    return 1;
                else {
                    int time1_h = Integer.parseInt(time1.split(" ")[1].split(":")[0]);
                    int time2_h = Integer.parseInt(time2.split(" ")[1].split(":")[0]);
                    if(time1_h>time2_h)
                        return -1;
                    else if (time1_h<time2_h)
                        return 1;
                    else {
                        int time1_m = Integer.parseInt(time1.split(" ")[1].split(":")[1]);
                        int time2_m = Integer.parseInt(time2.split(" ")[1].split(":")[1]);
                        if(time1_m>time2_m)
                            return -1;
                        else if (time1_m<time2_m)
                            return 1;
                        else
                            return 0;
                    }

                }
            }
        }

    }
}
