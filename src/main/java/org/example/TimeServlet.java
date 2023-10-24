package org.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.example.TimeConfig.*;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
   protected int zoneId;
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
       response.setContentType(CONTENT_TYPE_HTML);

       String timezoneParam = request.getParameter(PARAM_TIMEZONE);

       TimeZone timezone = TimeZone.getTimeZone(DEFAULT_TIMEZONE);

       if (timezoneParam != null) {
           String stringZoneID = timezoneParam.substring(3).trim();

           zoneId = Integer.parseInt(stringZoneID);
           timezone.setRawOffset(zoneId * MILLISECONDS_IN_HOUR);
       }

       handleValidTimezone(timezone, response);
   }

    private void handleValidTimezone(TimeZone timezone, HttpServletResponse response) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        dateFormat.setTimeZone(timezone);
        String currentTime = dateFormat.format(new Date());

        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head><title>Current Time</title></head>");
            out.println("<body>");
            out.println("<h2>Current Time:</h2>");
            out.println("<p>" + currentTime + zoneId + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
