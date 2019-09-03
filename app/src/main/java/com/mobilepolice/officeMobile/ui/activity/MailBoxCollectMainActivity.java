package com.mobilepolice.officeMobile.ui.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.bean.Email;
import com.mobilepolice.officeMobile.email.MailHelper;
import com.mobilepolice.officeMobile.email.MailReceiver;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

/**
 * 收件箱首页
 */
public class MailBoxCollectMainActivity extends MyActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mailbox_collect_main;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    protected void initView() {
        setTitle("收件箱");
        type = getIntent().getStringExtra("TYPE");
        status=getIntent().getIntExtra("status", -1);
        lv_box = (ListView) findViewById(R.id.lv_box);
        myAdapter = new MyAdapter();
        lv_box.setAdapter(myAdapter);

        dialog=new ProgressDialog(this);
        dialog.setMessage("正加载");
        dialog.show();
    }

    @Override
    protected void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mailReceivers = MailHelper
                            .getInstance(MailBoxCollectMainActivity.this).getAllMail(type);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(1);
                }
                // 去数据库查询
                messageids = getAllMessageids();
                switch (status) {
                    case 0://查询全部
                        getAllMails(mailReceivers);
                        break;
                    case 1://查询未读
                        getNotRead(mailReceivers);
                        break;
                    case 2://查询已读
                        getYesRead(mailReceivers);
                        break;
                }
                Message message = new Message();
                message.what = 2;

                handler.sendMessage(message);
                //handler.sendEmptyMessage(2);
            }
        }).start();
    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }


    private ArrayList<Email> mailslist = new ArrayList<Email>();
    private ArrayList<ArrayList<InputStream>> attachmentsInputStreamsList = new ArrayList<ArrayList<InputStream>>();
    private String type;
    private int status;
    private MyAdapter myAdapter;
    private ListView lv_box;
    private List<MailReceiver> mailReceivers;
    private ProgressDialog dialog;
    private Uri uri=Uri.parse("content://com.mobilepolice.office.emailstatusprovider");
    private List<String> messageids;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    myAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    dialog.dismiss();
                    toast("网络连接超时");
                    Intent intent=new Intent(MailBoxCollectMainActivity.this, HomeActivity.class);
                    startActivity(intent);
                case 2:
                    dialog.dismiss();
                    break;
            }
            super.handleMessage(msg);
        }

    };



    /**
     * 查询出已读邮件
     * @return
     */
    private List<String> getAllMessageids(){
        List<String> messageids=new ArrayList<String>();
        Cursor c=getContentResolver().query(uri, null, "mailfrom=?", new String[]{MyApplication.info.getUserName()}, null);
        while(c.moveToNext()){
            messageids.add(c.getString(2));
        }
        return messageids;
    }



    /**
     * 适配器
     * @author Administrator
     *
     */
    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mailslist.size();
        }

        @Override
        public Object getItem(int position) {
            return mailslist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            try{
            convertView = LayoutInflater.from(mContext).inflate(R.layout.email_mailbox_item, null);
            TextView tv_from = (TextView) convertView.findViewById(R.id.tv_from);
            tv_from.setText(mailslist.get(position).getFrom());
            TextView tv_sentdate = (TextView) convertView.findViewById(R.id.tv_sentdate);
            tv_sentdate.setText(mailslist.get(position).getSentdata());
            if (mailslist.get(position).isNews()) {
                TextView tv_new = (TextView) convertView.findViewById(R.id.tv_new);
                tv_new.setVisibility(View.VISIBLE);
            }
            TextView tv_subject = (TextView) convertView.findViewById(R.id.tv_subject);
                tv_subject.setText(mailslist.get(position).getSubject());
            }catch (Exception ex){
                ex.getStackTrace();
            }

            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //点击表示已读把ID存入数据库
                    String mailID=mailslist.get(position).getMessageID();
                    if(!messageids.contains(mailID)){
                        ContentValues values=new ContentValues();
                        values.put("mailfrom", MyApplication.info.getUserName());
                        values.put("messageid", mailID);
                        getContentResolver().insert(uri, values);
                    }

                    ((MyApplication)getApplication()).setAttachmentsInputStreams(attachmentsInputStreamsList.get(position));
                    final Intent intent = new Intent(MailBoxCollectMainActivity.this, MailBoxCollectDetailActivity.class).putExtra("EMAIL", mailslist.get(position));
                    startActivity(intent);
                }
            });
            return convertView;
        }

    }

    /**
     * 获取所有邮件
     * @param mails
     */
    private void getAllMails(List<MailReceiver> mails) {
        if(mails!=null) {
            for (MailReceiver mailReceiver : mails) {
                Email email = new Email();
                try {
                    email.setMessageID(mailReceiver.getMessageID());
                    email.setFrom(mailReceiver.getFrom());
                    email.setTo(mailReceiver.getMailAddress("TO"));
                    email.setCc(mailReceiver.getMailAddress("CC"));
                    email.setBcc(mailReceiver.getMailAddress("BCC"));
                    email.setSubject(mailReceiver.getSubject());
                    email.setSentdata(mailReceiver.getSentDate());
                    email.setContent(mailReceiver.getMailContent());
                    email.setReplysign(mailReceiver.getReplySign());
                    email.setHtml(mailReceiver.isHtml());
                    email.setNews(mailReceiver.isNew());
                    email.setAttachments(mailReceiver.getAttachments());
                    email.setCharset(mailReceiver.getCharset());
                    attachmentsInputStreamsList.add(0, mailReceiver.getAttachmentsInputStreams());
                    mailslist.add(0, email);
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 查询未读
     */
    private void getNotRead(List<MailReceiver> mails){
        for (MailReceiver mailReceiver : mails) {
            Email email = new Email();
            try {
                if(messageids.contains(mailReceiver.getMessageID())){
                    continue;
                }
                System.out.println(mailReceiver.getMailContent());

                String mailContent = "";

                email.setMessageID(mailReceiver.getMessageID());
                email.setFrom(mailReceiver.getFrom());
                email.setTo(mailReceiver.getMailAddress("TO"));
                email.setCc(mailReceiver.getMailAddress("CC"));
                email.setBcc(mailReceiver.getMailAddress("BCC"));
                email.setSubject(mailReceiver.getSubject());
                email.setSentdata(mailReceiver.getSentDate());
                email.setContent(mailContent);
                email.setReplysign(mailReceiver.getReplySign());
                email.setHtml(mailReceiver.isHtml());
                email.setNews(mailReceiver.isNew());
                email.setAttachments(mailReceiver.getAttachments());
                email.setCharset(mailReceiver.getCharset());
                attachmentsInputStreamsList.add(0,mailReceiver.getAttachmentsInputStreams());
                mailslist.add(0, email);
                handler.sendEmptyMessage(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询已读
     */
    private void getYesRead(List<MailReceiver> mails){
        for (MailReceiver mailReceiver : mails) {
            Email email = new Email();
            try {
                if(messageids.contains(mailReceiver.getMessageID())){
                    email.setMessageID(mailReceiver.getMessageID());
                    email.setFrom(mailReceiver.getFrom());
                    email.setTo(mailReceiver.getMailAddress("TO"));
                    email.setCc(mailReceiver.getMailAddress("CC"));
                    email.setBcc(mailReceiver.getMailAddress("BCC"));
                    email.setSubject(mailReceiver.getSubject());
                    email.setSentdata(mailReceiver.getSentDate());
                    email.setContent(mailReceiver.getMailContent());
                    email.setReplysign(mailReceiver.getReplySign());
                    email.setHtml(mailReceiver.isHtml());
                    email.setNews(mailReceiver.isNew());
                    email.setAttachments(mailReceiver.getAttachments());
                    email.setCharset(mailReceiver.getCharset());
                    attachmentsInputStreamsList.add(0,mailReceiver.getAttachmentsInputStreams());
                    mailslist.add(0, email);
                    handler.sendEmptyMessage(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
