package controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;

import com.fasterxml.jackson.core.Version;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import Fun.BaseController;
import Fun.Utils;
import Fun.UtilsPuMonth;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DataParamerController extends BaseController {
	public void index() {
		this.render("/index.jsp");
	}

	/**
	 * Version 2.0 模块http提交数据新接口
	 * 
	 */

	public void postdata() {
		JSONObject jo = new JSONObject();
		JSONArray jsn = new JSONArray();
		JSONObject jo1 = new JSONObject();
		String datas = "";

		try {
			String str = this.getRequest().getMethod();
			str = str.toLowerCase();
			if (str.equals("get")) {
				datas = this.getPara("data");
			} else {
				BufferedReader br = new BufferedReader(
						new InputStreamReader((ServletInputStream) this.getRequest().getInputStream(), "utf-8"));
				StringBuffer sb = new StringBuffer("");
				String temp;
				while ((temp = br.readLine()) != null) {
					sb.append(temp);
				}
				br.close();
				datas = sb.toString();
			}

			final JSONObject ja = JSONObject.fromObject(datas);
			boolean flag = Db.tx(new IAtom() {
				@Override
				public boolean run() throws SQLException {
					String codes = "", uid = "", actcode = "", btype = "", zcdm = "", times = "", pm = "", optype = "";
					boolean ret = false;
					if (!isN(ja.getString("areacode"))) {
						String areacode = ja.getString("areacode");
						codes = areacode.substring(0, 4);
					}
					if (!isN(ja.getString("uid"))) {
						uid = ja.getString("uid");
					}
					if (!isN(ja.getString("btype"))) {
						btype = ja.getString("btype");
					}
					if (!isN(ja.getString("zcdm"))) {
						zcdm = ja.getString("zcdm");
					}
					if (!isN(ja.getString("datetime"))) {
						times = ja.getString("datetime");
					}

					if (!isN(ja.getString("pm"))) {
						pm = ja.getString("pm");
					}
					if (!isN(ja.getString("optype"))) {
						optype = ja.getString("optype");
					}
					JSONObject jo=new JSONObject();
					if (pm.equals("ccstlngdg")) {
						jo= UtilsPuMonth.savedata1("ccstlngdg", "dg_ccst_qpreginfo", "ccst_czlist", "dg_ccst_qpczlist", zcdm, "1",
								times,"4","",1);
					} else {
						jo= UtilsPuMonth.savedata1(Utils.getDb(codes), "ccst_qpreginfo", "ccst_czlist", "ccst_qpczlist", zcdm,
								"1", times,"4","",1);
					}
					return true;
				}
			});

			if (flag) {
				jo.put("status", "0000");
				jo.put("message", "success");
				jo1.put("btype ", "0");
				jsn.add(jo1);
				jo.put("result", jsn);
			} else {
				jo.put("errorCode", "9999");
				jo.put("message", "保存失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("errorCode", "9999");
			jo.put("message", e.getMessage());
		}
//		this.setAttr("datas", jo.toString());
		this.clearhead();
		this.renderJson(jo);
	}

	/**
	 * 手持终端http请求授权接口
	 */
	public void mobreg() {
		String datas = "";
		String lists = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str = this.getRequest().getMethod();
			str = str.toLowerCase();
			if (str.equals("get")) {
				datas = this.getPara("data");
			} else {
				BufferedReader br = new BufferedReader(
						new InputStreamReader((ServletInputStream) this.getRequest().getInputStream(), "utf-8"));
				StringBuffer sb = new StringBuffer("");
				String temp;
				while ((temp = br.readLine()) != null) {
					sb.append(temp);
				}
				br.close();
				datas = sb.toString();
			}
			if (datas.length() == 16 || datas.length() == 15) {
				String sql = "select * from dict_mobauthorinfo where MobID=" + Q(datas);
				Record res = Db.use("qymobclouddb").findFirst(sql);
				if (res != null) {
					String states = "";
					if (res.getDate("EndDate") != null) {
						if (res.getDate("EndDate").getTime() > new Date().getTime()) {
							states = "0";
						} else {
							states = "1";
						}
					} else {
						states = "1";
					}
					lists = res.getStr("CloudID") + "," + df.format(new Date()) + "," + states;
				} else {
					lists = "";
				}
			} else {
				lists = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
			lists = "";
		}

		// this.setAttr("datas", jo.toString());
//		this.setAttr("listdatas", lists);
		this.clearhead();
		this.renderText(lists);

	}

	/**
	 * 手持终端http同步词典接口
	 */
	public void mobgetdict() {
		String datas = "";
		String lists = "";
		try {
			String str = this.getRequest().getMethod();
			str = str.toLowerCase();
			if (str.equals("get")) {
				datas = this.getPara("data");
			} else {
				BufferedReader br = new BufferedReader(
						new InputStreamReader((ServletInputStream) this.getRequest().getInputStream(), "utf-8"));
				StringBuffer sb = new StringBuffer("");
				String temp;
				while ((temp = br.readLine()) != null) {
					sb.append(temp);
				}
				br.close();
				datas = sb.toString();
			}
			if (datas.length() == 7) {
				String sql = "select * from dict_qpmedia where FillingMediumCode=" + Q(datas);
				Record res = Db.use("qymobclouddb").findFirst(sql);
				if (res != null) {

					lists = res.get("Mediumname", "").toString();
				} else {
					lists = "";
				}
			} else {
				lists = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
			lists = "";
		}

//		this.setAttr("listdatas", lists);
		this.clearhead();
		this.renderText(lists);
	}

	/**
	 * 手持终端http提交识别数据接口
	 */
	public void mobpdata() {
		JSONObject jo = new JSONObject();
		JSONArray jsn = new JSONArray();
		JSONObject jo1 = new JSONObject();
		String datas = "";
		byte[] bytes = null;
		try {
			String str = this.getRequest().getMethod();
			str = str.toLowerCase();
			if (str.equals("get")) {
				datas = this.getPara("data");
			} else {
				DataInputStream dis = new DataInputStream(this.getRequest().getInputStream());
				ByteArrayOutputStream baot = new ByteArrayOutputStream();
				bytes = new byte[256]; // 定义一个数组 用来读取
				int n = 0;// 每次读取输入流的量
				while ((n = dis.read(bytes)) != -1) {
					baot.write(bytes); // 将读取的字节流写入字节输出流
				}
			}
			String strs = Utils.bytesToHexFun1(bytes);
			strs = strs.substring(0, strs.indexOf("0000"));
			if(strs.length()%2!=0) {
			    strs+="0";
			}
			String userid = strs.substring(0, 2);// 员工
			String CloudId = Utils.asciiToString(strs.substring(2, 20));// 企业id+手持机顺序编号
			String Optype = strs.substring(20, 22);// 操作类型
			String BackType = strs.substring(22, 24);// 返回类型
			String CodeType = strs.substring(24, 26);// 条码类型
			String codes = strs.substring(26, strs.length());

			String codeval = "";
			int k = 0;
			for (int i = 0; i < codes.length() / 2; i++) {
				int j = i * 2;
				String ss = codes.substring(j, j + 2);
				if (ss.equals("5F")) {
					k = j;
					break;
				} else {
					codeval += codes.substring(j, j + 2);
				}
			}
			codeval = Utils.asciiToString(codeval);// 条码值
			// 把十六进制转十进制数字
			String s=codes.substring(k + 2, k + 10);
			String scantimes = Utils.codeToDesc(s);
			String jiaoyan = codes.substring(k + 10, k + 12);
            String Exmsg =Utils.asciiToString(codes.substring(k + 12, codes.length()));			
			int tiems = Integer.parseInt(scantimes, 16);
			String datetimes = Utils.AddDay("1970-01-01", tiems);// 扫描时间
			String areacode = codeval.substring(1, 5);
			JSONObject ret = new JSONObject();
			if (areacode.equals("0312")) {
				ret = UtilsPuMonth.savedata1("ccstlngdg", "dg_ccst_qpreginfo", "ccst_czlist", "dg_ccst_qpczlist", codeval, Integer.parseInt(Optype, 16)+"",
						datetimes,Integer.parseInt(CodeType, 16)+"",Exmsg,Integer.parseInt(BackType, 16));
			} else {
				String dbs=Utils.getDb(areacode);
				ret = UtilsPuMonth.savedata1(dbs, "ccst_qpreginfo", "ccst_czlist", "ccst_qpczlist", codeval, Integer.parseInt(Optype, 16)+"",
						datetimes,Integer.parseInt(CodeType, 16)+"",Exmsg,Integer.parseInt(BackType, 16));
			}
			
			if(ret!=null){
				jo.put("status", "0000");
				jo.put("message", "success");
				ret.put("btype", Optype);
				jsn.add(ret);
				jo.put("result", jsn);
			}else{
				jo.put("status", "99");
				jo.put("message", "保存失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("status", "99");
			jo.put("message", e.getMessage());
		}
		this.setAttr("datas", jo.toString());
		this.clearhead();
		this.renderJson(jo);
	}

	
}
