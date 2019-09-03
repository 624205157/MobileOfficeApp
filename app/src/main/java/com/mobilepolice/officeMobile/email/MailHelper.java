
package com.mobilepolice.officeMobile.email;

import android.content.Context;
import android.os.Environment;

import com.mobilepolice.officeMobile.base.MyApplication;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPInputStream;
import com.sun.mail.imap.IMAPStore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class MailHelper {

    private static MailHelper instance;
    private List<MailReceiver> mailList;
    private HashMap<String, Integer> serviceHashMap;
    private Context context;

    public static MailHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MailHelper(context);
        }
        return instance;
    }

    /**
     * 构造函数
     */
    private MailHelper(Context context) {
        this.context = context;
    }

    public String getUpdateUrlStr() throws Exception {
        String urlStr = null;
        if (serviceHashMap == null) {
            serviceHashMap = this.getServeHashMap();
        }
        if (serviceHashMap.get("update") == 1) {
            urlStr = mailList.get(1).getSubject();
        }
        return urlStr;
    }

    public String getUserHelp() throws Exception {
        String userandmoney = null;
        if (serviceHashMap == null) {
            serviceHashMap = this.getServeHashMap();
        }
        if (serviceHashMap.get("userhelp") == 1) {
            userandmoney = mailList.get(3).getSubject();
        }
        return userandmoney;
    }

    public int getAllUserHelp() throws Exception {
        String userandmoney = null;
        int money = 0;
        if (serviceHashMap == null) {
            serviceHashMap = this.getServeHashMap();
        }
        if (serviceHashMap.get("userhelp") == 1) {
            userandmoney = mailList.get(3).getSubject();
        }
        if (userandmoney != null && userandmoney.contains("all-user-100")) {
            money = Integer.parseInt(userandmoney.substring(userandmoney.lastIndexOf("-" + 1),
                    userandmoney.length()));
        }
        return money;
    }

    public boolean getAdControl() throws Exception {
        String ad = null;
        if (serviceHashMap == null) {
            serviceHashMap = this.getServeHashMap();
        }
        if (serviceHashMap.get("adcontrol") == 1) {
            ad = mailList.get(2).getSubject();
        }
        if (ad.equals("ad=close")) {
            return false;
        }
        return true;
    }

    public HashMap<String, Integer> getServeHashMap() throws Exception {
        serviceHashMap = new HashMap<String, Integer>();
        if (mailList == null) {
            mailList = getAllMail("INBOX");
        }
        String serviceStr = mailList.get(0).getSubject();
        if (serviceStr.contains("update 1.0=true")) {
            serviceHashMap.put("update", 1);
        } else if (serviceStr.contains("update 1.0=false")) {
            serviceHashMap.put("update", 0);
        }
        if (serviceStr.contains("adcontrol 1.0=true")) {
            serviceHashMap.put("adcontrol", 1);
        } else if (serviceStr.contains("adcontrol 1.0=false")) {
            serviceHashMap.put("adcontrol", 0);
        }
        if (serviceStr.contains("userhelp 1.0=true")) {
            serviceHashMap.put("userhelp", 1);
        } else if (serviceStr.contains("userhelp 1.0=false")) {
            serviceHashMap.put("userhelp", 0);
        }
        return serviceHashMap;
    }

    /**
     * 取得所有的邮件
     *
     * @param folderName 文件夹名，例：收件箱是"INBOX"
     * @throws MessagingException
     * @return　List<MailReceiver> 放有ReciveMail对象的List
     */
    public List<MailReceiver> getAllMail(String folderName) throws MessagingException {
        List<MailReceiver> mailList = new ArrayList<MailReceiver>();

        // 连接服务器
/*        Store store=MyApplication.session.getStore("pop3");
        String temp=MyApplication.info.getMailServerHost();
        String host=temp.replace("smtp", "pop");
        
        store.connect(host, MyApplication.info.getUserName(), MyApplication.info.getPassword());*/


//        String temp = MyApplication.info.getMailServerHost();
//        String host = temp.replace("smtp", "imap");
        String host=MyApplication.info.getImapServerHost();
        Properties prop = System.getProperties();
        prop.put("mail.store.protocol", "imap");
        prop.put("mail.imap.host", host);

        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = new Properties();
        props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.imap.socketFactory.fallback ", "false");
        props.setProperty("mail.imap.port", "993");
        props.setProperty("mail.imap.socketFactory.port", "993");
        props.put("mail.store.protocol", "imap");
        props.put("mail.imap.host", host);
        props.put("mail.imap.port", "993");

        Session session = Session.getInstance(props);

        IMAPStore store = (IMAPStore) session.getStore("imap"); // 使用imap会话机制，连接服务器

        store.connect(MyApplication.info.getUserName(), MyApplication.info.getPassword());

        IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX"); // 收件箱
//        IMAPFolder folder1 = (IMAPFolder) store.getFolder("草稿箱"); // 收件箱
//        IMAPFolder folder2 = (IMAPFolder) store.getFolder("已发送"); // 收件箱
//        IMAPFolder folder3 = (IMAPFolder) store.getFolder("已删除"); // 收件箱
        //folder.open(Folder.READ_WRITE);
        folder.open(Folder.READ_WRITE);
//        Store store=MyApplication.session.getStore("pop3");
//        String temp=MyApplication.info.getMailServerHost();
//        String host=temp.replace("smtp", "pop3");
//        store.connect(host, MyApplication.info.getUserName(), MyApplication.info.getPassword());
        
/*        URLName urln = new URLName("pop3", "pop3.163.com", 110, null,  
                "邮箱名（没有@163.com）", "密码");  
        Store store = session.getStore(urln);  
        store.connect();  */
        // 邮件协议为pop3，邮件服务器是pop3.163.com，端口为110，用户名/密码为abcw111222/123456w  


//        Folder folder = store.getFolder("INBOX");
//        folder.open(Folder.READ_WRITE);

        // 打开文件夹
/*        Folder folder = store.getFolder(folderName);
        folder.open(Folder.READ_ONLY);*/

        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);

        // 总的邮件数
        int mailCount = folder.getMessageCount();
        if (mailCount == 0) {
            folder.close(true);
            store.close();
            return null;
        } else {
            // 取得所有的邮件
            Message[] messages = folder.getMessages();


            for (int i = 0; i < messages.length; i++) {
                       try {
                Object obj = messages[i].getContent();
//                        MimeUtil.cleanContentType();
                          // mailReceiver(messages[i]);
//                           if(i==1)
//                           {
//
//                               messages[i].setFlag(Flags.Flag.DELETED, true);
//                           }
//                           if(i==messages.length-1)
//                           {
//                               messages[i].setFlag(Flags.Flag.SEEN ,true);
//                           }
//                           Object obj = msg.getContent();
                           if (!(obj instanceof Multipart)){//不是复合邮件体
                               String str="";
                              // return false;
                           }else{// 处理
                               String strr="";
                           }







//                Multipart mp = (Multipart) obj;
//
//
//
//                           for (int j = 0; j < mp.getCount(); j++) {
//                               Part part = mp.getBodyPart(j);
//                               if (part.getDisposition() != null
//                                       && part.getDisposition().equals(Part.ATTACHMENT)) {
//
//                               } else if (part.isMimeType("text/*")) {
//                                   part.getContent().toString();
//                               }
//                           }

//                String contentType = messages[i].getContentType();
//                Part part = (Part) messages[i];


                           // 解析邮件
//                           for (Message message : messages) {
//                               IMAPMessage msg = (IMAPMessage) message;
//                               String subject = MimeUtility.decodeText(msg.getSubject());
//                               System.out.println("[" + subject + "]未读，是否需要阅读此邮件（yes/no）？");
//                               BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//                               String answer = reader.readLine();  // www.2cto.com
//                               if ("yes".equalsIgnoreCase(answer)) {
//                                  // POP3ReceiveMailTest.parseMessage(msg);  // 解析邮件
//                                   // 第二个参数如果设置为true，则将修改反馈给服务器。false则不反馈给服务器
//                                   msg.setFlag(Flags.Flag.SEEN, true);   //设置已读标志
//                               }
//                           }
////
//                                System.out.println("contentType:" + contentType);
//
//                        showMessage(messages[i]);

                          // IMAPStore imapStore = (IMAPStore) session.getStore("imap");
                        //isContainAttach(part);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                // 自定义的邮件对象
                MailReceiver reciveMail = new MailReceiver((MimeMessage) messages[i]);
                mailList.add(reciveMail);// 添加到邮件列表中
            }
            return mailList;
        }
    }


    /**
     * 解析邮件
     *
     * @param
     * @param
     * @return
     * @throws IOException
     * @throws MessagingException
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
        private void mailReceiver (Message msg)throws Exception {
            // 发件人信息
            Address[] froms = msg.getFrom();
            if (froms != null) {
                //System.out.println("发件人信息:" + froms[0]);
                InternetAddress addr = (InternetAddress) froms[0];
                System.out.println("发件人地址:" + addr.getAddress());
                System.out.println("发件人显示名:" + addr.getPersonal());
            }
            System.out.println("邮件主题:" + msg.getSubject());
            // getContent() 是获取包裹内容, Part相当于外包装
            Object o = msg.getContent();
            if (o instanceof Multipart) {
                Multipart multipart = (Multipart) o;
                reMultipart(multipart);
            } else if (o instanceof Part) {
                Part part = (Part) o;
                rePart(part);
            } else {
                System.out.println("类型" + msg.getContentType());
                System.out.println("内容" + msg.getContent());
            }
        }


        /**
         * @param part 解析内容
         * @throws Exception
         */
        private void rePart (Part part) throws MessagingException,
                UnsupportedEncodingException, IOException, FileNotFoundException {
            if (part.getDisposition() != null) {

                String strFileNmae = MimeUtility.decodeText(part.getFileName()); //MimeUtility.decodeText解决附件名乱码问题
                System.out.println("发现附件: " + MimeUtility.decodeText(part.getFileName()));
                System.out.println("内容类型: " + MimeUtility.decodeText(part.getContentType()));
                System.out.println("附件内容:" + part.getContent());
                InputStream in = part.getInputStream();// 打开附件的输入流
                // 读取附件字节并存储到文件中
                String BASE_PATH = Environment
                        .getExternalStorageDirectory().toString()
                        + File.separator
                        + "oa" + File.separator;
              String directoryPath=context.getFilesDir()+File.separator+"dir"+File.separator;
// directoryPath=context.getCacheDir()+File.separator+dir;
            File file = new File(directoryPath);
            if(!file.exists()) {//判断文件目录是否存在  
                file.mkdirs();
            }
                java.io.FileOutputStream out = new FileOutputStream(directoryPath+strFileNmae);
                int data;
                while ((data = in.read()) != -1) {
                    out.write(data);
                }
                in.close();
                out.close();
            } else {
                if (part.getContentType().startsWith("text/plain")) {
                    System.out.println("文本内容：" + part.getContent());
                } else {
                    //System.out.println("HTML内容：" + part.getContent());
                }
            }
        }

        /**
         * @param multipart // 接卸包裹（含所有邮件内容(包裹+正文+附件)）
         * @throws Exception
         */
        private void reMultipart (Multipart multipart) throws Exception {
            //System.out.println("邮件共有" + multipart.getCount() + "部分组成");
            // 依次处理各个部分
            for (int j = 0, n = multipart.getCount(); j < n; j++) {
                //System.out.println("处理第" + j + "部分");
                Part part = multipart.getBodyPart(j);//解包, 取出 MultiPart的各个部分, 每部分可能是邮件内容,
                // 也可能是另一个小包裹(MultipPart)
                // 判断此包裹内容是不是一个小包裹, 一般这一部分是 正文 Content-Type: multipart/alternative
                if (part.getContent() instanceof Multipart) {
                    Multipart p = (Multipart) part.getContent();// 转成小包裹
                    //递归迭代
                    reMultipart(p);
                } else {
                    rePart(part);
                }
            }
        }


        public boolean isContainAttach (Part part) throws Exception {
            boolean attachflag = false;
            // String contentType = part.getContentType();
            if (part.isMimeType("multipart/*")) {
                IMAPInputStream inputStream = (IMAPInputStream) part.getContent();


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


        public void showMessage (Message message) throws IOException, MessagingException {

            boolean showMessage = true;
            String mail = "";
            Object object = (Object) message.getContent();

            if (object instanceof String) {
                mail = (String) object;
            } else if (object instanceof IMAPInputStream) {
                IMAPInputStream input = (IMAPInputStream) object;
                StringBuilder builder = new StringBuilder();
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                if (reader != null) {
                    reader.close();
                }
                mail = builder.toString();
            }
        }
    }