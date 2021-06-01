package cn.fetosoft.chat.core.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Title：LocalDate及LocalDateTime工具类
 * @Author：guobingbing
 * @Date 2019/7/17 14:51
 * @Description
 * @Version
 */
public final class LocalDateUtil {

	/**
	 * java.util.Date 转换成 LocalDate
	 * @author guobingbing
	 * @date 2019/7/17 14:52
	 * @param date
	 * @return java.time.LocalDate
	 */
	public static LocalDate dateToLocalDate(Date date){
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		return instant.atZone(zoneId).toLocalDate();
	}

	/**
	 * 字符串转LocalDate
	 * @author guobingbing
	 * @date 2019/7/20 17:39
	 * @param dateStr
	 * @param pattern
	 * @return java.time.LocalDate
	 */
	public static LocalDate strToLocalData(String dateStr, String pattern){
		if(pattern==null || pattern.trim().length()==0){
			pattern = "yyyy-MM-dd";
		}
		return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * java.util.Date 转换成 LocalDateTime
	 * @author guobingbing
	 * @date 2019/7/17 14:56
	 * @param date
	 * @return java.time.LocalDateTime
	 */
	public static LocalDateTime dateToLocalDateTime(Date date){
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		return instant.atZone(zoneId).toLocalDateTime();
	}

	/**
	 * LocalDate 转换成 java.util.Date
	 * @author guobingbing
	 * @date 2019/7/17 14:53
	 * @param localDate
	 * @return java.util.Date
	 */
	public static Date localDateToDate(LocalDate localDate){
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
		return Date.from(zdt.toInstant());
	}

	/**
	 * localDateTime 转换成 java.util.Date
	 * @author guobingbing
	 * @date 2019/7/17 14:59
	 * @param localDateTime
	 * @return java.util.Date
	 */
	public static Date localDateTimeToDate(LocalDateTime localDateTime){
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = localDateTime.atZone(zoneId);
		return Date.from(zdt.toInstant());
	}

	/**
	 * 格式化LocalDate
	 * @author guobingbing
	 * @date 2019/7/17 15:05
	 * @param localDate
	 * @param pattern
	 * @return java.lang.String
	 */
	public static String formatLocalDate(LocalDate localDate, String pattern){
		return localDate.format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 格式化 LocalDateTime
	 * @author guobingbing
	 * @date 2019/7/17 15:05
	 * @param localDateTime
	 * @param pattern
	 * @return java.lang.String
	 */
	public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern){
		return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
	}
}
