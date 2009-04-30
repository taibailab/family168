package com.family168.jbpm;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.*;
import java.util.List;

import javax.imageio.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.family168.geom.*;

import org.dom4j.*;

import org.dom4j.xpath.*;

import org.jbpm.*;

import org.jbpm.model.OpenProcessDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationContext;

import org.springframework.web.context.support.WebApplicationContextUtils;


public class JpdlImageServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(JpdlImageServlet.class);

    public void doGet(HttpServletRequest request,
        HttpServletResponse response) throws IOException, ServletException {
        try {
            ApplicationContext ctx = null;
            ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

            RepositoryService repositoryService = (RepositoryService) ctx
                .getBean("repositoryService");

            long id = Long.parseLong(request.getParameter("id"));
            InputStream is = repositoryService.getResourceAsStream(id,
                    "process.jpdl.xml");

            if (is == null) {
                is = repositoryService.getResourceAsStream(id,
                        "jpdl/end/process.jpdl.xml");
            }

            if (is == null) {
                is = repositoryService.getResourceAsStream(id,
                        "jpdl/guess/process.jpdl.xml");
            }

            if (is == null) {
                is = repositoryService.getResourceAsStream(id,
                        "jpdl/swimlane/process.jpdl.xml");
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = 0;

            while ((len = is.read(b, 0, 1024)) != -1) {
                baos.write(b, 0, len);
            }

            baos.flush();

            byte[] bytes = baos.toByteArray();

            logger.info(new String(bytes));

            Element root = DocumentHelper.parseText(new String(bytes))
                                         .getRootElement();
            BufferedImage bi = new BufferedImage(800, 600,
                    BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2 = bi.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

            Map<String, Rect> nodeMap = new HashMap<String, Rect>();

            for (Element elem : (List<Element>) root.elements()) {
                String nodeName = elem.getQName().getName();
                String q = elem.attribute("g").getValue();
                String[] location = q.split(",");
                int x = Integer.parseInt(location[0]);
                int y = Integer.parseInt(location[1]);
                int w = Integer.parseInt(location[2]);
                int h = Integer.parseInt(location[3]);
                String picName = null;

                if (nodeName.equals("start")) {
                    picName = "/icons/48/start_event_empty.png";
                } else if (nodeName.equals("end")) {
                    picName = "/icons/48/end_event_terminate.png";
                }

                if (picName != null) {
                    BufferedImage bi2 = ImageIO.read(this.getClass()
                                                         .getResourceAsStream(picName));
                    g2.drawImage(bi2, x, y, null);
                    w = 48;
                    h = 48;
                } else {
                    String name = elem.attribute("name").getValue();
                    g2.setColor(new Color(27, 118, 164));
                    g2.setStroke(new BasicStroke(2));
                    g2.drawRoundRect(x + 4, y + 4, w - 8, h - 8, 20, 20);

                    Font font = new Font("宋体", Font.PLAIN, 12);
                    g2.setFont(font);

                    FontRenderContext frc = g2.getFontRenderContext();
                    Rectangle2D r2 = font.getStringBounds(name, frc);
                    logger.info("{}", r2);

                    double xx = x + ((w - r2.getWidth()) / 2);
                    double yy = (y + ((h - r2.getHeight()) / 2))
                        - r2.getY();
                    g2.drawString(name, (int) xx, (int) yy);
                }

                try {
                    if (!nodeName.equals("start")) {
                        String name = elem.attribute("name").getValue();
                        Rect rect = new Rect(x, y, w, h);
                        nodeMap.put(name, rect);
                    }
                } catch (Exception ex) {
                    logger.warn("{}", ex);
                }
            }

            logger.info("{}", nodeMap);

            for (Element elem : (List<Element>) root.elements()) {
                String nodeName = elem.getQName().getName();
                String q = elem.attribute("g").getValue();
                String[] location = q.split(",");
                int x = Integer.parseInt(location[0]);
                int y = Integer.parseInt(location[1]);
                int w = Integer.parseInt(location[2]);
                int h = Integer.parseInt(location[3]);

                if (nodeName.equals("start")) {
                    w = 48;
                    h = 48;
                }

                Rect fromRect = new Rect(x, y, w, h);

                for (Element transition : (List<Element>) elem.elements()) {
                    String qName = transition.getQName().getName();

                    if (!qName.equals("transition")) {
                        continue;
                    }

                    String to = transition.attribute("to").getValue();
                    Rect toRect = nodeMap.get(to);
                    logger.info("from:{},rect:{}", nodeName, fromRect);
                    logger.info("to:{},rect:{}", to, toRect);

                    Line line = fromRect.getCrossLine(toRect);

                    if (line != null) {
                        //g2.setColor(Color.BLACK);
                        //g2.drawLine(line.x1, line.y1, line.x2, line.y2);
                        LineDrawer drawer = new LineDrawer(line.x1,
                                line.y1, line.x2, line.y2);
                        drawer.draw(g2, 1F);
                    }
                }
            }

            response.setContentType("image/png");
            ImageIO.write(bi, "png", response.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
