package chatting.data;

import java.io.Serializable;
import java.sql.Date;

public class Log implements Serializable{
	private String name;
	private char class_name;
	private char admin;
	private int grants;
	private int action;
	private int stdNo;
	private int logNo;
	private char result;
	private String logs;
	private Date dates;
	private Date time;
	
	private String grantList[] = {"ACC","UP","DN","NEW", "DEL"};
	private String actionList[] = {"해제","접속","생성","삭제", "업로드", "다운로드"};
	@Override
	public String toString() {
		String admin = (this.admin=='o') ? "관리자" : "사용자"; 
		String grant_list="";
		int mask = 1;
		for(int i = 0; i < 5; i++ )
		{
			if( (grants & mask) == mask )
			{
				grant_list+=grantList[i]+"|";
				
			}
			mask <<= 1;
		}
		
		return String.format("%c반\t%s\t%s\t%-20s\t%s\t%c\t%-30s%ty\n", class_name, name, admin, grant_list, actionList[action], result, logs, dates );
		
		/*
		return "Log [name=" + name + ", class_name=" + class_name + ", admin=" + admin + ", grant=" + grants
				+ ", action=" + action + ", stdNo=" + stdNo + ", logNo=" + logNo + ", result=" + result + ", logs="
				+ logs + ", dates=" + dates + ", time=" + time + "]";
				*/
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getClass_name() {
		return class_name;
	}
	public void setClass_name(char class_name) {
		this.class_name = class_name;
	}
	public char getAdmin() {
		return admin;
	}
	public void setAdmin(char admin) {
		this.admin = admin;
	}
	public int getGrants() {
		return grants;
	}
	public void setGrants(int grants) {
		this.grants = grants;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public int getStdNo() {
		return stdNo;
	}
	public void setStdNo(int stdNo) {
		this.stdNo = stdNo;
	}
	public int getLogNo() {
		return logNo;
	}
	public void setLogNo(int logNo) {
		this.logNo = logNo;
	}
	public char getResult() {
		return result;
	}
	public void setResult(char result) {
		this.result = result;
	}
	public String getLogs() {
		return logs;
	}
	public void setLogs(String logs) {
		this.logs = logs;
	}
	public Date getDates() {
		return dates;
	}
	public void setDates(Date dates) {
		this.dates = dates;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String[] getGrantList() {
		return grantList;
	}
	public void setGrantList(String[] grantList) {
		this.grantList = grantList;
	}
	public String[] getActionList() {
		return actionList;
	}
	public void setActionList(String[] actionList) {
		this.actionList = actionList;
	}
	
	
	

	
	
	
}
