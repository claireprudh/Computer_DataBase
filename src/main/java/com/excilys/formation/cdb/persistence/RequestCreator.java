package com.excilys.formation.cdb.persistence;

import org.springframework.stereotype.Component;

@Component
public class RequestCreator {
	
	public String select(Column... columns) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		
		for (Column column : columns) {
			sb.append(column.getName());
			sb.append(" , ");
		}
		
		sb.replace(sb.length() - 2, sb.length() - 1, " ");
		sb.append("FROM computer ");
		
		return sb.toString();
	}

}
