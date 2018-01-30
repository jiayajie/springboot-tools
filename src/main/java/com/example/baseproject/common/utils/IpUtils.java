package com.example.baseproject.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Company
 * @Discription
 * @Author guoxiaojing
 * @CreateDate 2018/1/30 16:26
 * @Version 1.0
 */
public class IpUtils {
    public static String getRemoteIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
//		log.info("获取IP，X-Forwarded-For："+ipAddress);
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("X-Real-IP");
//			log.info("获取IP，X-Real-IP："+ipAddress);
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
//			log.info("获取IP，WL-Proxy-Client-IP："+ipAddress);
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
//			log.info("获取IP，request.getRemoteAddr()："+ipAddress);
            if (ipAddress.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                }
                ipAddress = inet.getHostAddress();
            }

        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}
