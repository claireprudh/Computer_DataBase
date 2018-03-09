package com.excilys.formation.cdb.persistence;

public class RequestCreator {
	
	private static RequestCreator instance;
	
	private RequestCreator() {
		
	}
	
	public static RequestCreator getInstance() {
		
		if (instance == null) {
			instance = new RequestCreator();
		}
		
		return instance;
	}
	
	public String select(Column... columns) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		
		for (Column column : columns) {
			sb.append(column.getName());
			sb.append(" , ");
		}
		
		sb.replace(sb.length() - 2, sb.length() - 1, " ");
		sb.append("FROM computer ");
		
		System.out.println(sb.toString());
		return sb.toString();
	}

}
