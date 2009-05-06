package com.family168.security.web;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.family168.core.utils.JsonUtils;

import com.family168.security.domain.Menu;
import com.family168.security.domain.User;
import com.family168.security.manager.MenuManager;
import com.family168.security.manager.UserManager;

import com.opensymphony.xwork2.ActionContext;

import org.apache.struts2.ServletActionContext;

import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;


public class SecurityAction {
    private MenuManager menuManager;
    private UserManager userManager;

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    public PrintWriter getWriter() throws IOException {
        return ServletActionContext.getResponse().getWriter();
    }

    public void isLogin() throws Exception {
        Object securityContext = ActionContext.getContext().getSession()
                                              .get("SPRING_SECURITY_CONTEXT");
        getResponse()
            .setHeader("Cache-Control", "no-cache, must-revalidate");
        getResponse().setCharacterEncoding("UTF-8");

        if (securityContext != null) {
            Object obj = SecurityContextHolder.getContext()
                                              .getAuthentication()
                                              .getPrincipal();
            String username = null;

            if (obj instanceof UserDetails) {
                username = ((UserDetails) obj).getUsername();
            } else {
                username = obj.toString();
            }

            User user = userManager.findUniqueBy("username", username);
            List<Long> menuIds = menuManager.loadUserMenus(user.getId());
            List<Menu> menus = menuManager.loadTops("theSort", "asc");

            List<Menu> menuList = filterMenu(menus, menuIds);
            Bean bean = new Bean(true, "已经登陆", menuList);
            JsonUtils.write(bean, getWriter(),
                new String[] {
                    "parent", "roles", "hibernateLazyInitializer",
                    "handler", "checked"
                }, "yyyy-MM-dd");
        } else {
            getWriter().print("{success:false,msg:'请先登录'}");
        }
    }

    public List<Menu> filterMenu(Collection<Menu> menus, List<Long> menuIds) {
        List<Menu> menuList = new ArrayList<Menu>();

        for (Menu m : menus) {
            if (menuIds.contains(m.getId())) {
                menuList.add(m);

                List<Menu> children = filterMenu(m.getChildren(), menuIds);
                m.getChildren().clear();
                m.getChildren().addAll(children);
            }
        }

        return menuList;
    }

    public static class Bean {
        private boolean success;
        private String msg;
        private Object info;

        public Bean(boolean success, String msg, Object info) {
            this.success = success;
            this.msg = msg;
            this.info = info;
        }

        public boolean getSuccess() {
            return success;
        }

        public String getMsg() {
            return msg;
        }

        public Object getInfo() {
            return info;
        }
    }
}
