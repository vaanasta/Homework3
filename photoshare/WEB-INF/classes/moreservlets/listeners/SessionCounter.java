package moreservlets.listeners;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/** Listener that keeps track of the number of sessions
 * that the Web application is currently using and has
 * ever used in its life cycle.
 */

public class SessionCounter implements HttpSessionListener {
  private int totalSessionCount = 0;
  private int currentSessionCount = 0;
  private int maxSessionCount = 0;
  private ServletContext context = null;
  
  private long total_time=0;
  private long time;
  public void sessionCreated(HttpSessionEvent event) {
      
   totalSessionCount++;
   currentSessionCount++;
   time=System.currentTimeMillis();
   if (currentSessionCount > maxSessionCount) {
     maxSessionCount = currentSessionCount;
   }
   if (context == null) {
    storeInServletContext(event);
   }
}

public void sessionDestroyed(HttpSessionEvent event) {
  currentSessionCount--;
  total_time+=System.currentTimeMillis()-time;
}

//public int getAverageTime() {
    //return (int)(total_time/totalSessionCount)*1000;
  
//}
/** The total number of sessions created. */

public int getTotalSessionCount() {
  return(totalSessionCount);
    
}

/** The number of sessions currently in memory. */

public int getCurrentSessionCount() {
  return(currentSessionCount);
}
   /** The largest number of sessions ever in memory
    * at any one time.
    */

   public int getMaxSessionCount() {
    return(maxSessionCount);
  }

  // Register self in the servlet context so that
  // servlets and JSP pages can access the session counts.

  private void storeInServletContext(HttpSessionEvent event) {
    HttpSession session = event.getSession();
    context = session.getServletContext();
    context.setAttribute("sessionCounter", this);
  }
}