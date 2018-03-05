package com.excilys.formation.cdb.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.excilys.formation.cdb.ihm.Page;


public class PageTag extends SimpleTagSupport {
	
	private static int begin;
	private static int current;
	private static int end;
	private static int span = 5;
	

	public void doTag() throws JspException, IOException {
	      JspWriter out = getJspContext().getOut();
	      
	      StringBuilder listPages = new StringBuilder();
	      
	      if (begin > 1) {
	    	  listPages.append("<li><a> ... </a></li>\n");
	      }
	      
	      for (int i = begin; i <= end; i++) {
	    	  listPages.append("<li><a href=\"dashboard?page=" +  (i) + " \">" + (i) + "</a></li>\n");
	      }
	      if (end < Page.pageMax) {
	    	  listPages.append("<li><a> ... </a></li>\n");
	      }
	      
	     out.print(listPages.toString());
	      
	      
	      
	      
	    
	   }

	/**
	 * @return the begin
	 */
	public static int getBegin() {
		return begin;
	}

	/**
	 * @param begin the begin to set
	 */
	private static void setBegin(int begin) {
		PageTag.begin = begin;
	}

	/**
	 * @return the end
	 */
	public static int getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	private static void setEnd(int end) {
		PageTag.end = end;
	}

	public static int getCurrent() {
		return current;
	}

	public static void setCurrent(int current) {
		PageTag.current = current;
		System.out.println("Je suis passé par ici");
		if (current > span) {
			PageTag.setBegin(current - span);
		} else {
			PageTag.setBegin(1);
		}
		if (current < Page.pageMax - span) {
			PageTag.setEnd(current + span);
			System.out.println("J'ai mis le end à sa valeur");
		} else {
			PageTag.setEnd(Page.pageMax);
			System.out.println("J'ai mis le end à une autre valeur");
		}
		
		System.out.println("begin : " + begin + " current : " + current + " end : " + end);
	}
	
	
//	"<c:forEach  begin=\"1\" end=\"${ maxPage }\" step = \"1\" var=\"i\" >\n" + 
//		"				\n" + 
//		"				<li><a href=\"dashboard?page=${ i }\">${ i }</a></li>\n" + 
//		"				\n" + 
//		"				</c:forEach>"
//		+ "Hello Custom Tag!"

}
