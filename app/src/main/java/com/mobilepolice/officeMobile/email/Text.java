package com.mobilepolice.officeMobile.email;

import android.annotation.SuppressLint;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class Text {

    /**
     * 以pop3方式读取邮件，此方法不能读取邮件是否为已读，已经通过测试
     * */
    private void getEmail() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.host", "smtp.163.com");
            props.put("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props, null);
            URLName urln = new URLName("pop3", "pop3.163.com", 110, null,
                    "邮箱名（没有@163.com）", "密码");
            // 邮件协议为pop3，邮件服务器是pop3.163.com，端口为110，用户名/密码为abcw111222/123456w
            Store store = session.getStore(urln);
            store.connect();
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            Message message[] = folder.getMessages();
            if (message.length > 0) {
                Map<String, Object> map;
                System.out.println("Messages's length: " + message.length);
                ReciveOneMail pmm = null;
                for (int i = 0; i < message.length; i++) {
                    System.out.println("======================");
                    pmm = new ReciveOneMail((MimeMessage) message[i]);
                    System.out.println("Message " + i + " subject: "
                            + pmm.getSubject());
                    System.out.println("Message " + i + " sentdate: "
                            + pmm.getSentDate());
                    System.out.println("Message " + i + " replysign: "
                            + pmm.getReplySign());

                    boolean isRead = pmm.isNew();// 判断邮件是否为已读
                    System.out.println("Message " + i + " hasRead: " + isRead);
                    System.out.println("Message " + i + "  containAttachment: "
                            + pmm.isContainAttach((Part) message[i]));
                    System.out.println("Message " + i + " form: "
                            + pmm.getFrom());
                    System.out.println("Message " + i + " to: "
                            + pmm.getMailAddress("to"));
                    System.out.println("Message " + i + " cc: "
                            + pmm.getMailAddress("cc"));
                    System.out.println("Message " + i + " bcc: "
                            + pmm.getMailAddress("bcc"));
                    pmm.setDateFormat("yy年MM月dd日 HH:mm");
                    System.out.println("Message " + i + " sentdate: "
                            + pmm.getSentDate());
                    System.out.println("Message " + i + " Message-ID: "
                            + pmm.getMessageId());
                    // 获得邮件内容===============
                    pmm.getMailContent((Part) message[i]);
                    System.out.println("Message " + i + " bodycontent: \r\n"
                            + pmm.getBodyText());
                    String file_path = File.separator + "mnt" + File.separator
                            + "sdcard" + File.separator;
                    System.out.println(file_path);
                    pmm.setAttachPath(file_path);
                    pmm.saveAttachMent((Part) message[i]);

                    map = new HashMap<String, Object>();
                    map.put("from", pmm.getFrom());
                    map.put("title", pmm.getSubject());
                    map.put("time", pmm.getSentDate());
                    map.put("read", isRead);
                    list.add(map);
                }
//                SimpleAdapter adapter = new SimpleAdapter(MainActivity.this,
//                        list, R.layout.item_receiveemail, new String[] {
//                        "from", "title", "time", "read" }, new int[] {
//                        R.id.item_receive_sendname,
//                        R.id.item_receive_title,
//                        R.id.item_receive_sendtime,
//                        R.id.item_receive_read });
//                lv.setAdapter(adapter);

            } else {
//                Toast.makeText(MainActivity.this, "没有邮件", Toast.LENGTH_SHORT)
//                        .show();
            }
        } catch (NoSuchProviderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 以imap方式读取邮件，可以判定读取邮件是否为已读
     * */
    private void getImapEmail() {
        String user = "abcw111222@163.com";// 邮箱的用户名
        String password = "123456w"; // 邮箱的密码

        Properties prop = System.getProperties();
        prop.put("mail.store.protocol", "imap");
        prop.put("mail.imap.host", "imap.163.com");

        Session session = Session.getInstance(prop);

        int total = 0;
        IMAPStore store;
        try {
            store = (IMAPStore) session.getStore("imap"); // 使用imap会话机制，连接服务器

            store.connect(user, password);

            IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX"); // 收件箱
            folder.open(Folder.READ_WRITE);
            // 获取总邮件数
            total = folder.getMessageCount();
            System.out.println("---共有邮件：" + total + " 封---");
            // 得到收件箱文件夹信息，获取邮件列表
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            System.out.println("未读邮件数：" + folder.getUnreadMessageCount());
            Message[] messages = folder.getMessages();
            if (messages.length > 0) {
                Map<String, Object> map;
                System.out.println("Messages's length: " + messages.length);
                ReciveOneMail pmm = null;
                for (int i = 0; i < messages.length; i++) {
                    System.out.println("======================");
                    pmm = new ReciveOneMail((MimeMessage) messages[i]);
                    System.out.println("Message " + i + " subject: "
                            + pmm.getSubject());
                    try {
                        System.out.println("Message " + i + " sentdate: "
                                + pmm.getSentDate());
                        System.out.println("Message " + i + " replysign: "
                                + pmm.getReplySign());

                        boolean isRead;// 用来判断该邮件是否为已读
                        String read;
                        Flags flags = messages[i].getFlags();
                        if (flags.contains(Flags.Flag.SEEN)) {
                            System.out.println("这是一封已读邮件");
                            isRead = true;
                            read = "已读";
                        } else {
                            System.out.println("未读邮件");
                            isRead = false;
                            read = "未读";
                        }
                        System.out.println("Message " + i + " hasRead: "
                                + isRead);
                        System.out.println("Message " + i
                                + "  containAttachment: "
                                + pmm.isContainAttach((Part) messages[i]));
                        System.out.println("Message " + i + " form: "
                                + pmm.getFrom());
                        System.out.println("Message " + i + " to: "
                                + pmm.getMailAddress("to"));
                        System.out.println("Message " + i + " cc: "
                                + pmm.getMailAddress("cc"));
                        System.out.println("Message " + i + " bcc: "
                                + pmm.getMailAddress("bcc"));
                        pmm.setDateFormat("yy年MM月dd日 HH:mm");
                        System.out.println("Message " + i + " sentdate: "
                                + pmm.getSentDate());
                        System.out.println("Message " + i + " Message-ID: "
                                + pmm.getMessageId());
                        // 获得邮件内容===============
                        pmm.getMailContent((Part) messages[i]);
                        System.out.println("Message " + i
                                + " bodycontent: \r\n" + pmm.getBodyText());
                        String file_path = File.separator + "mnt"
                                + File.separator + "sdcard" + File.separator;
                        System.out.println(file_path);
                        pmm.setAttachPath(file_path);
                        pmm.saveAttachMent((Part) messages[i]);

                        map = new HashMap<String, Object>();
                        map.put("from", pmm.getFrom());
                        map.put("title", pmm.getSubject());
                        map.put("time", pmm.getSentDate());
                        map.put("read", read);
                        list.add(map);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
//                SimpleAdapter adapter = new SimpleAdapter(MainActivity.this,
//                        list, R.layout.item_receiveemail, new String[] {
//                        "from", "title", "time", "read" }, new int[] {
//                        R.id.item_receive_sendname,
//                        R.id.item_receive_title,
//                        R.id.item_receive_sendtime,
//                        R.id.item_receive_read });
//                lv.setAdapter(adapter);
            }
        } catch (javax.mail.NoSuchProviderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    /**
     * 接收邮箱中的邮件，可以接收带附件的
     * （目前接收的邮件中含有中文内容，会出现乱码，但是标题不会乱码）
     * 邮件中的内容如果用专门的阅读器也不会出现乱码现象，图片等都可以下载
     * */
    @SuppressLint("DefaultLocale")
    public class ReciveOneMail {

        private MimeMessage mimeMessage = null;
        private String saveAttachPath = ""; // 附件下载后的存放目录
        private StringBuffer bodytext = new StringBuffer();// 存放邮件内容
        private String dateformat = "yy-MM-dd HH:mm"; // 默认的日前显示格式

        public ReciveOneMail(MimeMessage mimeMessage) {
            this.mimeMessage = mimeMessage;
        }

        public void setMimeMessage(MimeMessage mimeMessage) {
            this.mimeMessage = mimeMessage;
        }

        /**
         * 获得发件人的地址和姓名
         */
        public String getFrom() throws Exception {
            InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
            String from = address[0].getAddress();
            if (from == null)
                from = "";
            String personal = address[0].getPersonal();
            if (personal == null)
                personal = "";
            String fromaddr = personal + "<" + from + ">";
            return fromaddr;
        }

        /**
         * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同 "to"----收件人 "cc"---抄送人地址 "bcc"---密送人地址
         */
        @SuppressLint("DefaultLocale")
        public String getMailAddress(String type) throws Exception {
            String mailaddr = "";
            String addtype = type.toUpperCase();
            InternetAddress[] address = null;
            if (addtype.equals("TO") || addtype.equals("CC")
                    || addtype.equals("BCC")) {
                if (addtype.equals("TO")) {
                    address = (InternetAddress[]) mimeMessage
                            .getRecipients(Message.RecipientType.TO);
                } else if (addtype.equals("CC")) {
                    address = (InternetAddress[]) mimeMessage
                            .getRecipients(Message.RecipientType.CC);
                } else {
                    address = (InternetAddress[]) mimeMessage
                            .getRecipients(Message.RecipientType.BCC);
                }
                if (address != null) {
                    for (int i = 0; i < address.length; i++) {
                        String email = address[i].getAddress();
                        if (email == null)
                            email = "";
                        else {
                            email = MimeUtility.decodeText(email);
                        }
                        String personal = address[i].getPersonal();
                        if (personal == null)
                            personal = "";
                        else {
                            personal = MimeUtility.decodeText(personal);
                        }
                        String compositeto = personal + "<" + email + ">";
                        mailaddr += "," + compositeto;
                    }
                    mailaddr = mailaddr.substring(1);
                }
            } else {
                throw new Exception("Error emailaddr type!");
            }
            return mailaddr;
        }

        /**
         * 获得邮件主题
         */
        public String getSubject() throws MessagingException {
            String subject = "";
            try {
                subject = MimeUtility.decodeText(mimeMessage.getSubject());
                if (subject == null)
                    subject = "";
            } catch (Exception exce) {
            }
            return subject;
        }

        /**
         * 获得邮件发送日期
         */
        @SuppressLint("SimpleDateFormat")
        public String getSentDate() throws Exception {
            Date sentdate = mimeMessage.getSentDate();
            SimpleDateFormat format = new SimpleDateFormat(dateformat);
            return format.format(sentdate);
        }

        /**
         * 获得邮件正文内容
         */
        public String getBodyText() {
            return bodytext.toString();
        }

        /**
         * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
         */
        public void getMailContent(Part part) throws Exception {
            String contenttype = part.getContentType();
            int nameindex = contenttype.indexOf("name");
            boolean conname = false;
            if (nameindex != -1)
                conname = true;
            System.out.println("CONTENTTYPE: " + contenttype);
            if (part.isMimeType("text/plain") && !conname) {
                bodytext.append((String) part.getContent());
            } else if (part.isMimeType("text/html") && !conname) {
                bodytext.append((String) part.getContent());
            } else if (part.isMimeType("multipart/*")) {
                Multipart multipart = (Multipart) part.getContent();
                int counts = multipart.getCount();
                for (int i = 0; i < counts; i++) {
                    getMailContent(multipart.getBodyPart(i));
                }
            } else if (part.isMimeType("message/rfc822")) {
                getMailContent((Part) part.getContent());
            } else {
            }
        }

        /**
         * 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"
         */
        public boolean getReplySign() throws MessagingException {
            boolean replysign = false;
            String needreply[] = mimeMessage
                    .getHeader("Disposition-Notification-To");
            if (needreply != null) {
                replysign = true;
            }
            return replysign;
        }

        /**
         * 获得此邮件的Message-ID
         */
        public String getMessageId() throws MessagingException {
            return mimeMessage.getMessageID();
        }

        /**
         * 【判断此邮件是否已读，如果未读返回返回false,反之返回true】pop3协议使用时不能判断。
         */
        public boolean isNew() throws MessagingException {
            boolean isnew = false;//由于isnew设为false所以每次显示的都为未读
            Flags flags = ((Message) mimeMessage).getFlags();
            System.out.println("--------flags-------" + flags);
            Flags.Flag[] flag = flags.getSystemFlags();
            System.out.println("----flag----" + flag);
            System.out.println("flags's length: " + flag.length);
            for (int i = 0; i < flag.length; i++) {
                System.out.println("flag=======" + flag[i]);
                System.out.println("-=-=-=Flags.Flag.SEEN=-=-=-="+Flags.Flag.SEEN);
                if (flag[i] == Flags.Flag.SEEN) {
                    isnew = true;
                    System.out.println("seen Message.......");
                    break;
                }
            }
            return isnew;
        }

        /**
         * 判断此邮件是否包含附件
         */
        @SuppressLint("DefaultLocale")
        public boolean isContainAttach(Part part) throws Exception {
            boolean attachflag = false;
            // String contentType = part.getContentType();
            if (part.isMimeType("multipart/*")) {
                Multipart mp = (Multipart) part.getContent();
                for (int i = 0; i < mp.getCount(); i++) {
                    BodyPart mpart = mp.getBodyPart(i);
                    String disposition = mpart.getDisposition();
                    if ((disposition != null)
                            && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                            .equals(Part.INLINE))))
                        attachflag = true;
                    else if (mpart.isMimeType("multipart/*")) {
                        attachflag = isContainAttach((Part) mpart);
                    } else {
                        String contype = mpart.getContentType();
                        if (contype.toLowerCase().indexOf("application") != -1)
                            attachflag = true;
                        if (contype.toLowerCase().indexOf("name") != -1)
                            attachflag = true;
                    }
                }
            } else if (part.isMimeType("message/rfc822")) {
                attachflag = isContainAttach((Part) part.getContent());
            }
            return attachflag;
        }

        /**
         * 【保存附件】
         */
        @SuppressLint("DefaultLocale")
        public void saveAttachMent(Part part) throws Exception {
            String fileName = "";
            if (part.isMimeType("multipart/*")) {
                Multipart mp = (Multipart) part.getContent();
                for (int i = 0; i < mp.getCount(); i++) {
                    BodyPart mpart = mp.getBodyPart(i);//主体部分得到处理
                    String disposition = mpart.getDisposition();
                    if ((disposition != null)
                            && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                            .equals(Part.INLINE)))) {//ATTACHMENT附件，INLINE嵌入
                        fileName = mpart.getFileName();
                        if (fileName.toLowerCase().indexOf("gb18030") != -1) {
                            fileName = MimeUtility.decodeText(fileName);
                        }
                        saveFile(fileName, mpart.getInputStream());
                    } else if (mpart.isMimeType("multipart/*")) {
                        saveAttachMent(mpart);
                    } else {
                        fileName = mpart.getFileName();
                        if ((fileName != null)
                                && (fileName.toLowerCase().indexOf("GB18030") != -1)) {
                            fileName = MimeUtility.decodeText(fileName);
                            saveFile(fileName, mpart.getInputStream());
                        }
                    }
                }
            } else if (part.isMimeType("message/rfc822")) {
                saveAttachMent((Part) part.getContent());
            }
        }

        /**
         * 【设置附件存放路径】
         */

        public void setAttachPath(String attachpath) {
            this.saveAttachPath = attachpath;
        }

        /**
         * 【设置日期显示格式】
         */
        public void setDateFormat(String format) throws Exception {
            this.dateformat = format;
        }

        /**
         * 【获得附件存放路径】
         */
        public String getAttachPath() {
            return saveAttachPath;
        }

        /**
         * 【真正的保存附件到指定目录里】
         */
        @SuppressLint("DefaultLocale")
        private void saveFile(String fileName, InputStream in) throws Exception {
            String osName = System.getProperty("os.name");
            System.out.println("----fileName----" + fileName);
            // String storedir = getAttachPath();
//        String separator = "";
            if (osName == null)
                osName = "";
            File storefile = new File(File.separator + "mnt" + File.separator
                    + "sdcard" + File.separator + fileName);

            storefile.createNewFile();
            System.out.println("storefile's path: " + storefile.toString());
//         for(int i=0;storefile.exists();i++){
//         storefile = new File(storedir+separator+fileName+i);
//         }

            BufferedOutputStream bos = null;
            BufferedInputStream bis = null;
            try {
                bos = new BufferedOutputStream(new FileOutputStream(storefile));
                bis = new BufferedInputStream(in);
                int c;
                while ((c = bis.read()) != -1) {
                    bos.write(c);
                    bos.flush();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                throw new Exception("文件保存失败!");
            } finally {
                bos.close();
                bis.close();
            }
        }
    }
}
