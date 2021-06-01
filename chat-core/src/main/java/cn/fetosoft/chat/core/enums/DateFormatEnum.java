package cn.fetosoft.chat.core.enums;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 非线程安全的
 * 日期格式化
 * @author guobingbing
 * @create 2020-02-23 16:55
 */
public enum DateFormatEnum {

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	YMD_HMS("yyy-MM-dd HH:mm:ss"),

	/**
	 * yyyyMMddHHmmss
	 */
	YMDHMS("yyyyMMddHHmmss"),

	/**
	 * yyyy-MM-dd
	 */
	Y_MD("yyyy-MM-dd"),

	/**
	 * yyyyMMdd
	 */
	YMD("yyyyMMdd");

	private final static Map<String, SimpleDateFormat> sdfMap = new HashMap<>(16);

	private String pattern;

	DateFormatEnum(String pattern){
		this.pattern = pattern;
	}

	public String getPattern(){
		return this.pattern;
	}

	/**
	 * 初始化SimpleDateFormat
	 * @param pattern
	 * @return
	 */
	private SimpleDateFormat getFormat(String pattern){
		SimpleDateFormat sdf = sdfMap.get(pattern);
		if(sdf==null){
			sdf = new SimpleDateFormat(pattern);
			sdfMap.put(pattern, sdf);
		}
		return sdf;
	}

	/**
	 * 日期转String
	 * @param date
	 * @return
	 */
	public String dateToString(Date date){
		if(date!=null) {
			return this.getFormat(pattern).format(date);
		}else{
			return StringUtils.EMPTY;
		}
	}

	/**
	 * String转日期
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public Date stringToDate(String dateStr) throws ParseException {
		return this.getFormat(pattern).parse(dateStr);
	}

	public String localDateToString(LocalDate date){
		if(date!=null) {
			return date.format(DateTimeFormatter.ofPattern(this.getPattern()));
		}else{
			return StringUtils.EMPTY;
		}
	}

	public LocalDate stringToLocalDate(String dateStr){
		return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(this.getPattern()));
	}
}
