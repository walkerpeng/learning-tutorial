package me.walker.apachepoidemo;

import java.io.*;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Excel下载Utils
 */
public class DownloadUtils {

    public static void downFile(String filepath, HttpServletRequest request, HttpServletResponse response) {
        try {
            File temFile = new File(filepath);
            if (!temFile.exists()) {
                response.getWriter().write("ERROR:File Not Found");
                return;
            }
            String fileName = filepath.substring(filepath.lastIndexOf(File.separator) + 1);
            String browser = request.getHeader("user-agent");
            Pattern p = Pattern.compile(".*MSIE.*?;.*");
            Matcher m = p.matcher(browser);

            if (m.matches()) {
                response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8").replace("+", ""));
            } else {
                response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1").replace(" ", ""));
            }
            response.setHeader("Cache-Control", "max-age=" + 100);
            response.setContentLength((int) temFile.length());
            response.setContentType("application/x-download");
            response.setHeader("windows-Target", "_blank");

            OutputStream os = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(temFile));
            BufferedOutputStream bos = new BufferedOutputStream(os);
            byte[] buffer = new byte[4096];
            int length = 0;
            while ((length = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, length);
            }
            bos.close();
            bis.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
