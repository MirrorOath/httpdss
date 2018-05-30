package Fun;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import net.sf.json.JSONObject;

/**
 * 公有方法类
 * 
 * @author Administrator
 *
 */
public class UtilsPuMonth {
	public static String Q(String s) {
		return "'" + s + "'";
	}

	/**
	 * 保存及修改信息的公共方法
	 * 
	 * @param db
	 * @param reginfo
	 * @param czlist
	 * @param qpczlist
	 * @param zcdm
	 * @param optype
	 * @param times
	 * @return
	 */
	public static JSONObject savedata1(String db, String reginfo, String czlist, String qpczlist, String zcdm,
			String optype, String times,String codetype,String Exmsg,int Backtype) {
		JSONObject jo = new JSONObject();
		String sql = "select * from " + reginfo + " where  (zcdm=" + Q(zcdm) + " or zcdmtsg08=" + Q(zcdm) + " or jzgno="
				+ Q(zcdm) + " or jzgtsg08=" + Q(zcdm) + ")";
		List<Record> ls = Db.use(db).find(sql);
		int typcode = 1;
		try {
			typcode = Integer.valueOf(optype);

		} catch (Exception e) {
			// TODO: handle exception
			typcode = 1;
		}
		Boolean ret = false;
		Boolean isouttime = false;
		String pid = "", czjz = "";
		if (ls != null) {
			for (Record re : ls) {
				pid = re.getStr("Pid");
				czjz = re.getStr("Medium");
				if ((re.getDate("LastXjrq_Jyjl").getTime() < new Date().getTime())
						|| (re.getDate("LastBFRQ_Jyjl").getTime() < new Date().getTime())) {
					sql = "insert into " + czlist
							+ " (checkdatetime,rno,Syzbh,zcdm,Pcode,Type_name,Medium_name,Pid,Pdate,LastJydate,"
							+ "LastXjrq_Jyjl,LastBFRQ_Jyjl,Safedate,InfoLabeltype,InfoLabel,Checkresult,updatetime,OpType) "
							+ "select " + Q(times)
							+ ",RNO,Syzbh,zcdm,Pcode,Type_name,Medium_name,Pid,Pdate,LastJydate,LastXjrq_Jyjl,"
							+ "LastBFRQ_Jyjl,BFRQ,InfoLabeltype,InfoLabeltype_name,'1',now()," + typcode + " from "
							+ reginfo + " where zcdm=" + Q(re.getStr("zcdm"));
					isouttime = true;

				} else {
					if (optype.equals("czfj") || optype.equals("2")) {// 充装后复检
						sql = " update  " + czlist + " set updatetime=now(), OpType=" + typcode + ",Checkdatatime_fj="
								+ Q(times) + ",Checkresult_fj="+ Q(Exmsg) + "  where DATE_FORMAT(checkdatetime,'%Y-%m-%d')="
								+ Q(times.substring(1, 10)) + " and OpType=1 and zcdm=" + Q(re.getStr("zcdm"));
						
					} else {
						sql = "insert into " + czlist
								+ " (fill_weight,checkdatetime,rno,Syzbh,zcdm,Pcode,Type_name,Medium_name,Pid,Pdate,LastJydate,"
								+ "LastXjrq_Jyjl,LastBFRQ_Jyjl,Safedate,InfoLabeltype,InfoLabel,Checkresult,updatetime,OpType)"
								+ " select "+Q(Exmsg)+"," + Q(times)
								+ ",RNO,Syzbh,zcdm,Pcode,Type_name,Medium_name,Pid,Pdate,LastJydate,LastXjrq_Jyjl,"
								+ "LastBFRQ_Jyjl,BFRQ,InfoLabeltype,InfoLabeltype_name,'0',now()," + typcode + " from "
								+ reginfo + " where zcdm=" + Q(re.getStr("zcdm"));
					}

				}
				String upsql = "select * from " + qpczlist + " where RNOID=" + Q(re.getStr("RNO"));
				Record rs = Db.use(db).findFirst(upsql);
				if (optype.equals("czjc")) {// 充装检查
					upsql = "update " + reginfo + " set lczdw=" + Q(rs.get("Rname").toString()) + ",lczrq=" + Q(times)
							+ "," + "lastczdz=" + Q(rs.get("RAddress").toString()) + " where zcdm="
							+ Q(re.getStr("zcdm"));
				}else
				if (optype.equals("czqjc") || optype.equals("1")) {// 充装前检查
					upsql = "update " + reginfo + " set lczdw=" + Q(rs.get("Rname").toString()) + ",lczjcrq=" + Q(times)
							+ ",lastczdz=" + Q(rs.get("RAddress").toString())+",lczl="+Q(Exmsg) + " where zcdm=" + Q(re.getStr("zcdm"));
				}else
				if (optype.equals("czfj") || optype.equals("2")) {// 充装后复检
					upsql = "update " + reginfo + " set lczdw=" + Q(rs.get("Rname").toString()) + ",lczfjrq=" + Q(times)
							+ ",lastczdz=" + Q(rs.get("RAddress").toString()) + " where zcdm=" + Q(re.getStr("zcdm"));
				}else {
				    upsql="";
				}
				int k=-1;
				if (optype.equals("czfj") || optype.equals("2")) {// 充装后复检
				    k=Db.use(db).update(sql);  
				}
				
                if(!upsql.equals(""))
                {
                    k=Db.use(db).update(upsql);
                }				
				if ( k>= 0 ) {
					ret = true;
				} else {
					ret = false;
					break;
				}

			}
			if (ret) {
				if (isouttime) {
					jo.put("state", 1);

				} else {
					jo.put("staite", 0);
				}
                if (Backtype==1){
                    jo.put("pid", pid);
                    jo.put("czjz", czjz);
                }				
				if (Backtype==2) {
					jo.put("qpnum", ls.size());
				} 

			}
		}
		return jo;
	}
}
