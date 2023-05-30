package com.example.capstoneproject.common;

import android.util.Log;

import java.util.*;

public class DateDiff {

    /**
     * 날짜 차이를 계산하여 반환하는 메서드입니다.
     *
     * @param nYear1  비교할 첫 번째 날짜의 연도
     * @param nMonth1 비교할 첫 번째 날짜의 월
     * @param nDate1  비교할 첫 번째 날짜의 일
     * @param nYear2  비교할 두 번째 날짜의 연도
     * @param nMonth2 비교할 두 번째 날짜의 월
     * @param nDate2  비교할 두 번째 날짜의 일
     * @return        첫 번째 날짜와 두 번째 날짜의 차이를 일 단위로 반환
     */
    public int getDifferenceOfDate(int nYear1, int nMonth1, int nDate1, int nYear2, int nMonth2, int nDate2) {
        Calendar cal = Calendar.getInstance();
        int nTotalDate1 = 0, nTotalDate2 = 0, nDiffOfYear = 0, nDiffOfDay = 0;

        // 첫 번째 날짜의 연도가 두 번째 날짜의 연도보다 큰 경우
        if (nYear1 > nYear2) {
            for (int i = nYear2; i < nYear1; i++) {
                cal.set(i, 12, 0);
                nDiffOfYear += cal.get(Calendar.DAY_OF_YEAR);
            }
            nTotalDate1 += nDiffOfYear;
        }
        // 첫 번째 날짜의 연도가 두 번째 날짜의 연도보다 작은 경우
        else if (nYear1 < nYear2) {
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


    /**
     * 주어진 날짜와 시간과 현재 시간과의 차이를 계산하여 문자열로 반환하는 메서드입니다.
     *
     * @param year  비교할 대상의 연도
     * @param month 비교할 대상의 월
     * @param day   비교할 대상의 일
     * @param hour  비교할 대상의 시간
     * @param min   비교할 대상의 분
     * @return      현재 시간으로부터 대상 시간까지의 남은 시간을 표시한 문자열
     */
    public String getTime(int year, int month, int day, int hour, int min){
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int baseYear = calendar.get(Calendar.YEAR);
        int baseMonth = calendar.get(Calendar.MONTH)+1;
        int baseDay = calendar.get(Calendar.DAY_OF_MONTH);
        int c_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int c_min = calendar.get(Calendar.MINUTE);
        int c_sec = calendar.get(Calendar.SECOND);

        Calendar baseCal = new GregorianCalendar(baseYear,baseMonth,baseDay,c_hour,c_min,c_sec);
        Calendar targetCal = new GregorianCalendar(year,month,day,hour,min,0);  //비교대상날짜

        long diffSec = (targetCal.getTimeInMillis() - baseCal.getTimeInMillis()) / 1000;
        long diffDays = diffSec / (24*60*60);

        targetCal.add(Calendar.DAY_OF_MONTH, (int)(-diffDays));

        int hourTime = (int)Math.floor((double)(diffSec/3600));
        int minTime = (int)Math.floor((double)(((diffSec - (3600 * hourTime)) / 60)));
        int secTime = (int)Math.floor((double)(((diffSec - (3600 * hourTime)) - (60 * minTime))));

        String resultHour = String.format("%02d", hourTime);
        String resultMin = String.format("%02d", minTime);
        String resultSec = String.format("%02d", secTime);

        return resultHour + " 시간 " +resultMin + " 분 "+ resultSec + "초 남았습니다.";

    }
}