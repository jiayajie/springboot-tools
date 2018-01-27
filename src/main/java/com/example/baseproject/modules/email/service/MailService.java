package com.example.baseproject.modules.email.service;

import java.util.Map;

/**
 * Created With IDEA.
 *
 * @author dongyaofeng
 * @date 2017/12/27 16:04
 */
public interface MailService {

    void sendSimpleMail(String to, String subject, String content);

    void sendHtmlMail(String to, String subject, Map<String, Object> model) throws Exception;

    void sendAttachmentsMail(String to, String subject, String content, String filePath);

    void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);
}
