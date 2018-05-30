package Fun;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;

public class BaseController extends Controller {
	public String _SQL = "";

	public String Q(String s) {
		return "'" + s + "'";
	}	
	public void clearhead() {
	    this.getResponse().setHeader("Cache-Control", "");
        this.getResponse().setHeader("Content-Type", "");
        this.getResponse().setHeader("Date", "");
        this.getResponse().setHeader("Expires", "");
        this.getResponse().setHeader("Pragma", "");
        this.getResponse().setHeader("Server", "");

	}
	public  Date StrToDate(String sdate) {
		if (isN(sdate)) {
			return null;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date t = sdf.parse(sdate);
				return t;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				return null;
			}
		}
	}

	public Date StrToDateTime(String sdate) {
		if (isN(sdate)) {
			return null;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date t = sdf.parse(sdate);
				return t;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				return null;
			}
		}
	}

	public String P(String p) {
		String s = this.getPara(p);
		if (s == null)
			return "";
		else
			return s;

	}


	public Boolean isN(String value) {
		if (value != null)
			if (!value.trim().equals(""))
				return false;
			else
				return true;
		else
			return true;
	}
	
}
