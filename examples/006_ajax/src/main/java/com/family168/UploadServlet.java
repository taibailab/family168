package com.family168;

import java.io.IOException;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.family168.ajaxupload.MonitoredDiskFileItemFactory;
import com.family168.ajaxupload.UploadListener;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;


public class UploadServlet extends HttpServlet {
    public void doPost(HttpServletRequest request,
        HttpServletResponse response) throws IOException {
        try {
            Integer delay = 3;
            UploadListener listener = new UploadListener(request, delay);
            DiskFileItemFactory factory = new MonitoredDiskFileItemFactory(listener);
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            upload.setSizeMax(1000000000);
            factory.setSizeThreshold(2000000);

            //factory.setRepository("D:\\");
            List fileItems = upload.parseRequest(request);
            System.out.println(fileItems);
        } catch (FileUploadException ex) {
            ex.printStackTrace();
        }

        response.sendRedirect("ajax-upload-ok.html");
    }
}
