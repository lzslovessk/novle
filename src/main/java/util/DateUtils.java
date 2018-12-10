package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	public static final Logger logger = LoggerFactory.getLogger("DateUtils");

    public static final String YYYYMMDD = "yyyy-MM-dd";

    public static final String YYYYMMDD_ZH = "yyyy年MM月dd日";

    public static final int FIRST_DAY_OF_WEEK = Calendar.MONDAY; // 中国周一是一周的第一天

    /**
     * @param strDate
     * @return
     */
    public static Date parseDate(String strDate) {
        return parseDate(strDate, null);
    }

    /**
     * parseDate
     * @param strDate
     * @param pattern
     * @return
     */
    public static Date parseDate(String strDate, String pattern) {
        Date date = null;
        try {
            if (pattern == null) {
                pattern = YYYYMMDD;
            }
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            date = format.parse(strDate);
        } catch (Exception e) {
            logger.error("异常",e);
        }
        return date;
    }

    /**
     * format date
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDate(date, null);
    }

    /**
     * format date
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        String strDate = "";
        try {
            if (pattern == null) {
                pattern = YYYYMMDD;
            }
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            if(date != null){
            	strDate = format.format(date);
            }
        } catch (Exception e) {
            logger.error("异常",e);
        }
        return strDate;
    }

    /**
     * 取得一年的第几周
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week_of_year = c.get(Calendar.WEEK_OF_YEAR);
        return week_of_year - 1;
    }

    /**
     * getWeekBeginAndEndDate
     * @param date
     * @param pattern
     * @return
     */
    public static String getWeekBeginAndEndDate(Date date, String pattern) {
        Date monday = getMondayOfWeek(date);
        Date sunday = getSundayOfWeek(date);
        return formatDate(monday, pattern) + " - " + formatDate(sunday, pattern);
    }

    /**
     * 根据日期取得对应周周一日期
     * @param date
     * @return
     */
    public static Date getMondayOfWeek(Date date) {
        Calendar monday = Calendar.getInstance();
        monday.setTime(date);
        monday.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
        monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return monday.getTime();
    }

    /**
     * 根据日期取得对应周周日日期
     * @param date
     * @return
     */
    public static Date getSundayOfWeek(Date date) {
        Calendar sunday = Calendar.getInstance();
        sunday.setTime(date);
        sunday.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
        sunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return sunday.getTime();
    }

    /**
     * 取得月的剩余天数
     * @param date
     * @return
     */
    public static int getRemainDayOfMonth(Date date) {
        int dayOfMonth = getDayOfMonth(date);
        int day = getPassDayOfMonth(date);
        return dayOfMonth - day;
    }

    /**
     * 取得月已经过的天数
     * @param date
     * @return
     */
    public static int getPassDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得月天数
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得月第一天
     * @param date
     * @return
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得月最后一天
     * @param date
     * @return
     */
    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得季度第一天
     * @param date
     * @return
     */
    public static Date getFirstDateOfSeason(Date date) {
        return getFirstDateOfMonth(getSeasonDate(date)[0]);
    }

    /**
     * 取得季度最后一天
     * @param date
     * @return
     */
    public static Date getLastDateOfSeason(Date date) {
        return getLastDateOfMonth(getSeasonDate(date)[2]);
    }

    /**
     * 取得季度天数
     * @param date
     * @return
     */
    public static int getDayOfSeason(Date date) {
        int day = 0;
        Date[] seasonDates = getSeasonDate(date);
        for (Date date2 : seasonDates) {
            day += getDayOfMonth(date2);
        }
        return day;
    }

    /**
     * 取得季度剩余天数
     * @param date
     * @return
     */
    public static int getRemainDayOfSeason(Date date) {
        return getDayOfSeason(date) - getPassDayOfSeason(date);
    }

    /**
     * 取得季度已过天数
     * @param date
     * @return
     */
    public static int getPassDayOfSeason(Date date) {
        int day = 0;

        Date[] seasonDates = getSeasonDate(date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);

        if (month == Calendar.JANUARY || month == Calendar.APRIL || month == Calendar.JULY || month == Calendar.OCTOBER) {// 季度第一个月
            day = getPassDayOfMonth(seasonDates[0]);
        } else if (month == Calendar.FEBRUARY || month == Calendar.MAY || month == Calendar.AUGUST
                || month == Calendar.NOVEMBER) {// 季度第二个月
            day = getDayOfMonth(seasonDates[0]) + getPassDayOfMonth(seasonDates[1]);
        } else if (month == Calendar.MARCH || month == Calendar.JUNE || month == Calendar.SEPTEMBER
                || month == Calendar.DECEMBER) {// 季度第三个月
            day = getDayOfMonth(seasonDates[0]) + getDayOfMonth(seasonDates[1]) + getPassDayOfMonth(seasonDates[2]);
        }
        return day;
    }

    /**
     * 取得季度月
     * @param date
     * @return
     */
    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int nSeason = getSeason(date);
        if (nSeason == 1) {// 第一季度
            c.set(Calendar.MONTH, Calendar.JANUARY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MARCH);
            season[2] = c.getTime();
        } else if (nSeason == 2) {// 第二季度
            c.set(Calendar.MONTH, Calendar.APRIL);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MAY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.JUNE);
            season[2] = c.getTime();
        } else if (nSeason == 3) {// 第三季度
            c.set(Calendar.MONTH, Calendar.JULY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);
            season[2] = c.getTime();
        } else if (nSeason == 4) {// 第四季度
            c.set(Calendar.MONTH, Calendar.OCTOBER);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            season[2] = c.getTime();
        }
        return season;
    }

    /**
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     * @param date
     * @return
     */
    public static int getSeason(Date date) {

        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    public static Long getNowTimeInMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null) {
            date = new Date();
        }
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static Long getNowTimeInMillis() {
        return getNowTimeInMillis(null);
    }
    
    public static float getHourMinDiff(Date date1,Date date2){
    	if(date2.before(date1)){
    		date2.setTime(date2.getTime() + 24 * 60 * 60 * 1000);
    	}
		Long diff = date2.getTime() - date1.getTime();
		long day = diff / (24 * 60 * 60 * 1000);
		long hour = (diff / (60 * 60 * 1000) - day * 24);
		long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		float diffHour = hour;
		if (min >= 30) {
			diffHour += 0.5;
		}
		return diffHour;
		
	}
    
    
    public static float getHourMaxDiff(Date date1,Date date2){
    	if(date2.before(date1)){
    		date2.setTime(date2.getTime() + 24 * 60 * 60 * 1000);
    	}
		Long diff = date2.getTime() - date1.getTime();
		long day = diff / (24 * 60 * 60 * 1000);
		long hour = (diff / (60 * 60 * 1000) - day * 24);
		long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		float diffHour = hour;
		if (min > 0 && min <= 30) {
			diffHour += 0.5;
		}else if(min > 30){
			diffHour += 1;
		}
		return diffHour;
		
	}
    
    /**
     * 获取第num个月后的日期
     * @param num
     * @return
     */
    public static Date getDateOverMonth(Date date,int num){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.MONTH, num);
    	return calendar.getTime();
    }
    
    public static Date getDateOverMonthFistDay(Date date,int num){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.DAY_OF_MONTH, num);
    	return calendar.getTime();
    }
    
    /**
     * 获取第num个月后的日期
     * @param num
     * @return
     */
    public static Date getDateOverDay(Date date,int num){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.DATE, num);
    	return calendar.getTime();
    }
    
    
   
    //计算两个日期间隔天数
    public static int daysBetween(Date smdate,Date bdate) throws ParseException {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }
    
    /**
     * 
     * @param date1
     * @param date2
     * @return 计算两个日期相间隔的月数
     * @throws ParseException
     */
    public static int getMonthSpace(String date1, String date2) throws ParseException {

        int result = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));

        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        
        if(result==0){
        	return result;
        }else{
        	return Math.abs(result);
        }

    }
    
    public static Date getXhMonthInterval(Date indate,int bet){
    	Date interestdatetemp = getDateOverMonth(indate, bet);
    	String interestdatetemp_f = formatDate(interestdatetemp);
    	String contractStart_f = formatDate(indate);
		String contractStart_f_day = contractStart_f.substring(8, 10);
		if(!interestdatetemp_f.substring(8, 10).equals(contractStart_f_day)){
			Date temp = getDateOverMonthFistDay(interestdatetemp,1);
			return temp;
		}else {
			return interestdatetemp;
		}
    	
    	
    }
    
    
    //得到指定日期 指定天数后的日期
    public static Date getXDaysDate(String date,long bet) throws ParseException{
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式
    	Date dates = dateFormat.parse(date); // 指定日期
    	Date newDate = addDate(dates, bet-1); // 指定日期加上bet天（包括当天，bet需要-1）
    	
    	return newDate;
    }
    
    
    public static Date addDate(Date date,long day) throws ParseException {
    	 long time = date.getTime(); // 得到指定日期的毫秒数
    	 day = day*24*60*60*1000; // 要加上的天数转换成毫秒数
    	 time+=day; // 相加得到新的毫秒数
    	 return new Date(time); // 将毫秒数转换成日期
    }

}