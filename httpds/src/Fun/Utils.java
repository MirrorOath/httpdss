package Fun;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Utils {

	    /**
		 * 通过行政代码查询数据库名
		 * @param area
		 * @return
		 */
		public  static String getDb(String area){
			Map<String,String> map=new HashMap<String,String>();
			map.put("3202", "ccstv4");
			map.put("2106", "ccstv4lndd");
			map.put("5201", "ccstv4gzgy");
			map.put("3303", "ccstv4zjwz");
			map.put("2202", "ccstv4jljl");
			map.put("3206", "ccstv4jsnt");
			map.put("3288", "ccstv4");
			Iterator iter = map.entrySet().iterator();
			String str="";
			while(iter.hasNext()){
				Map.Entry entry = (Map.Entry) iter.next();  
				if(entry.getKey().equals(area)){
					str= (String) entry.getValue();
					break;
				}
			}
			return str;
		}
	    public static String DateToStr(Date sdate) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String t = sdf.format(sdate);
			return t;
		}

		public static String DateTimeToStr(Date sdate) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String t = sdf.format(sdate);
			return t;
		}
		public static Date StrToDate(String sdate) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date t = sdf.parse(sdate);
					return t;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					return null;
				}
		}
		/**
		 * 把秒数转换成年月日
		 * @param date
		 * @param second
		 * @return
		 */
		public static String AddDay(String date, int second) {
			Calendar c = Calendar.getInstance();
			c.setTime(StrToDate(date));
			c.add(Calendar.SECOND, second);
			String enddate = DateTimeToStr(c.getTime());
			return enddate;
		}
		
		/**
		 * 方法1
	     * 字符串转换成十六进制值
	     * @param bin String 我们看到的要转换成十六进制的字符串
	     * @return 
	     */
		public static String bin2hex(String bin) {
	        char[] digital = "0123456789ABCDEF".toCharArray();
	        StringBuffer sb = new StringBuffer("");
	        byte[] bs = bin.getBytes();
	        int bit;
	        for (int i = 0; i < bs.length; i++) {
	            bit = (bs[i] & 0x0f0) >> 4;
	            sb.append(digital[bit]);
	            bit = bs[i] & 0x0f;
	            sb.append(digital[bit]);
	        }
	        return sb.toString();
	    }
	    
	    /**
	     * 方法2
	     * byte[]与16进制字符串相互转换
	     */
	    public static String bytesToHexFun1(byte[] bytes) {
	        // 一个byte为8位，可用两个十六进制位标识
	     final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	        char[] buf = new char[bytes.length * 2];
	        int a = 0;
	        int index = 0;
	        for(byte b : bytes) { // 使用除与取余进行转换
	            if(b < 0) {
	                a = 256 + b;
	            } else {
	                a = b;
	            }

	            buf[index++] = HEX_CHAR[a / 16];
	            buf[index++] = HEX_CHAR[a % 16];
	        }

	        return new String(buf);
	    }
	    

	    
	    /**
	     * ASCII码转变为字符串
	     * (十六进制转十进制再转换成字符串)
	     * @param text
	     * @return
	     */
	    public static String asciiToString(String text) {
	    	String strs="";
	    	for (int i = 0; i < text.length()/2;i++) {
	    		 int keys=i*2;
	    		int dd=Integer.parseInt(text.substring(keys, keys+2), 16);
	    		strs+=String.valueOf((char)dd);
	    		
			}
	         return strs;
	    }
	    
	    /**
	     * 16进制转10进制、秒数
	     * @param text
	     * @return
	     */
	    public  static String hexToint(String text) {
	    	String strs="";
	    	for (int i = 0; i < text.length()/2;i++) {
	    		 int keys=i*2;
	    		int dd=Integer.parseInt(text.substring(keys, keys+2), 16);
	    		strs+=String.valueOf(dd);
	    		
			}
	         return strs;
	    }
	    /**
	     * 把字符串后两位排到前面
	     * @param values
	     * @return
	     */
	    public static String codeToDesc(String values){
	    	//AFCCDE5A
	    	String str=values.substring(6, 8)+values.substring(4, 6)+values.substring(2, 4)+values.substring(0, 2);
	    	return str;
	    }
	    

	    public static void main(String []args){
	          int time= 1524550831;//秒 17520422290
	          System.out.print( AddDay("1970-01-01",time));

	}
}
