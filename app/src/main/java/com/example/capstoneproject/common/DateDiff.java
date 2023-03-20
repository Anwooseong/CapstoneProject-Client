package com.example.capstoneproject.common;

import java.util.*;

public class DateDiff {

    public int getDifferenceOfDate(int nYear1, int nMonth1, int nDate1, int nYear2, int nMonth2, int nDate2) {
        Calendar cal = Calendar.getInstance();
        int nTotalDate1 = 0, nTotalDate2 = 0, nDiffOfYear = 0, nDiffOfDay = 0;
        if (nYear1 > nYear2) {
            for (int i = nYear2; i < nYear1; i++) {
                cal.set(i, 12, 0);
                nDiffOfYear += cal.get(Calendar.DAY_OF_YEAR);
            }
            nTotalDate1 += nDiffOfYear;
        } else if (nYear1 < nYear2) {
            for (int i = nYear1; i < nYear2; i++) {
                cal.set(i, 12, 0);
                nDiffOfYear += cal.get(Calendar.DAY_OF_YEAR);
            }
            nTotalDate2 += nDiffOfYear;
        }
        cal.set(nYear1, nMonth1 - 1, nDate1);
        nDiffOfDay = cal.get(Calendar.DAY_OF_YEAR);
        nTotalDate1 += nDiffOfDay;
        cal.set(nYear2, nMonth2 - 1, nDate2);
        nDiffOfDay = cal.get(Calendar.DAY_OF_YEAR);
        nTotalDate2 += nDiffOfDay;

        return nTotalDate1 - nTotalDate2;
    }

    private String getTime(){
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int c_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int c_min = calendar.get(Calendar.MINUTE);
        int c_sec = calendar.get(Calendar.SECOND);

        Calendar baseCal = new GregorianCalendar(year,month,day,c_hour,c_min,c_sec);
        Calendar targetCal = new GregorianCalendar(year,month,day+2,0,0,0);  //비교대상날짜

        long diffSec = (targetCal.getTimeInMillis() - baseCal.getTimeInMillis()) / 1000;
        long diffDays = diffSec / (24*60*60);

        targetCal.add(Calendar.DAY_OF_MONTH, (int)(-diffDays));

        int hourTime = (int)Math.floor((double)(diffSec/3600));
        int minTime = (int)Math.floor((double)(((diffSec - (3600 * hourTime)) / 60)));
        int secTime = (int)Math.floor((double)(((diffSec - (3600 * hourTime)) - (60 * minTime))));

        String hour = String.format("%02d", hourTime);
        String min = String.format("%02d", minTime);
        String sec = String.format("%02d", secTime);

        return year+"년"+month+"월"+ (day+2)+"일 까지 " + hour + " 시간 " +min + " 분 "+ sec + "초 남았습니다.";

    }
}