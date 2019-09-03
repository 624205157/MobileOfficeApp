package com.mobilepolice.officeMobile.pdf;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.bean.DocPendingBean;
import com.mobilepolice.officeMobile.utils.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PdfGenerator {

    private static Bitmap resizedBitmap;

    public static void generate(Context context, List<VideoModel> list, String fileName) {
        try {

            File f = new File(FileUtil.PDF_PATH + fileName);
            Log.d(PdfGenerator.class.toString(), "file:" + f);
            FileOutputStream output = new FileOutputStream(f);
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, output);
            //PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/Users/H__D/Desktop/test8.pdf"));

//            //用户密码
//            String userPassword = "123456";
//            //拥有者密码
//            String ownerPassword = "hd";
//
//
//            writer.setEncryption(userPassword.getBytes(), ownerPassword.getBytes(), PdfWriter.ALLOW_PRINTING,
//                    PdfWriter.ENCRYPTION_AES_128);
            document.open();
//            User user = DatabaseQueries.getUser(context);
//            String text = user.getName() + "- Raport z zastrzyków: \n \n ";
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);
//            //BaseFont.createFont("assets/fonts/STSONG.TTF",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);//设置中文显示问题
            Font font = new Font(baseFont, 32);//设置字体格式及大小

            //  Font font = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK);
//            document.addHeader("demo", "测试");
//            document.addTitle("主题");
//            document.addAuthor("wgl");
            //document.add(new Paragraph("测试"));

//            Paragraph p = new Paragraph("标题", font);
//            p.setAlignment(Element.ALIGN_CENTER);
//            document.add(p);
//            Paragraph p = new Paragraph("测试", font);
//            p.setSpacingBefore(50);
//            p.setAlignment(Element.ALIGN_CENTER);
//            document.add(p);
//             p = new Paragraph("sfsfdasfdsf", font);
//            p.setSpacingBefore(50);
//            p.setAlignment(Element.ALIGN_CENTER);
//            document.add(p);

            for (VideoModel currentInj : list) {
                if (currentInj.getName().equals("标题")) {
                    Paragraph p = new Paragraph(currentInj.getDesc(), setChineseFont());
                    p.setSpacingBefore(50);
                    p.setAlignment(Element.ALIGN_CENTER);
                    document.add(p);


                } else {
                    PdfPTable nextTable = addTable(currentInj, context, setChineseFont());
                    document.add(nextTable);
                }
                Log.d(PdfGenerator.class.toString(), "next injection:");
            }

            document.close();
            writer.close();
        } catch (Exception e) {
            Log.e(PdfGenerator.class.toString(), "blad przy generowaniu pdf" + e.toString());
        }
    }

    private static PdfPTable addTable(VideoModel currentInj, Context context, Font font) {
        PdfPCell cell;
        PdfPTable table = new PdfPTable(2);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        String injectionDate = calendar.get(Calendar.DAY_OF_MONTH) + "." +
//                calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR) +
//                " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
//        PdfPCell cell = new PdfPCell(new Phrase("Data zastrzyku: " + injectionDate));
//        cell.setBorder(0);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("Miejsce zastrzyku:"));
//        cell.setBorder(0);
//        cell.setPadding(20);
//        table.addCell(cell);\
        if (!currentInj.isFlag()) {
            Phrase phrase = new Phrase(currentInj.getName(), setChineseFont());
            cell = new PdfPCell(phrase);
            //  cell.setBorder(0);
            cell.setFixedHeight(60);
            cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            cell.setPaddingTop(60);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(currentInj.getDesc(), setChineseFont()));
            cell.setPadding(20);
            table.addCell(cell);
        } else {

            Phrase phrase = new Phrase(currentInj.getName(), setChineseFont());
            cell = new PdfPCell(phrase);
            cell.setFixedHeight(200);
            cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            cell.setPaddingTop(90);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            try {
//            String field="f"+ String.valueOf(currentInj.getArea()) + String.valueOf(currentInj.
//                    getPoint());
                //  Drawable d = context.getResources().getDrawable(R.mipmap.ic_logo);//ContextCompat.getDrawable(context, context.getResources().
//                    getIdentifier(field, "drawable", context.getPackageName()));

//                BitmapDrawable bitDw = ((BitmapDrawable) d);
//                Bitmap bmp = bitDw.getBitmap();

                Bitmap bmp = BitmapFactory.decodeFile(currentInj.getPath());
                bmp = getResizedBitmap(bmp, 200, 200);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image image = Image.getInstance(stream.toByteArray());
                PdfPCell imageCell = new PdfPCell(image);
//            imageCell.setRowspan(2);
                //  imageCell.setBorder(0);
                table.addCell(imageCell);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        com.itextpdf.text.List list = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
//        if (TextUtils.isEmpty(currentInj.getName())) {
//            ListItem item = new ListItem("  -temperatura\n");
//            list.add(item);
//        } else if (TextUtils.isEmpty(currentInj.getName())) {
//            ListItem item = new ListItem("  -ból miesni\n");
//            list.add(item);
//        } else if (TextUtils.isEmpty(currentInj.getName())) {
//            ListItem item = new ListItem("  -dreszcze\n");
//            list.add(item);
//        }
//        Phrase phrase = new Phrase();
//        phrase.add(list);
//        cell = new PdfPCell(phrase);
//        table.addCell(cell);
//        table.setSpacingAfter(10f);
        return table;
    }

    // 产生PDF字体
    public static Font setChineseFont() {
        BaseFont bf = null;
        Font fontChinese = null;
        try {
            bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            fontChinese = new Font(bf, 22, Font.NORMAL);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fontChinese;
    }

    public static void test(String fileName, DocPendingBean.ObjBean objbean, List<DocPendingBean.AttributesBean.ApproveListInfoBean> list) {
        try {
            File f = new File(FileUtil.PDF_PATH + fileName);
            Log.d(PdfGenerator.class.toString(), "file:" + f);
            MyApplication.getInstance().path_pdf = f.getAbsolutePath();
            FileOutputStream output = new FileOutputStream(f);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, output);
            document.open();
            // Document document = new Document(PageSize.A4, 20, 20, 50, 20);
//        File outputFile = new File("a.pdf");
//        设置输出位置
            //  PdfWriter.getInstance(document, output);
            //打开文档
            //document.open();
            PdfPTable table = new PdfPTable(2);

            BaseFont baseFont = BaseFont.createFont(AsianFontMapper.ChineseSimplifiedFont,
                    AsianFontMapper.ChineseSimplifiedEncoding_H, BaseFont.NOT_EMBEDDED);
            Image jpg1 = Image.getInstance(objbean.getImg()); //原来的图片的路径
            float heigth = jpg1.getHeight();
            float width = jpg1.getWidth();
            int percent = getPercent2(heigth, width);
            System.out.println("--" + percent);
            //设置图片居中显示
            jpg1.setAlignment(Image.MIDDLE);
            //直接设置图片的大小~~~~~~~第三种解决方案，按固定比例压缩
            //jpg1.scaleAbsolute(210.0f, 297.0f);
            //按百分比显示图片的比例
            jpg1.scalePercent(percent);//表示是原来图像的比例;
            //可设置图像高和宽的比例
            //jpg1.scalePercent(50, 100);
            document.add(jpg1);

            //table.setSpacingBefore(2200f);
//            Bitmap bmp = BitmapFactory.decodeFile(objbean.getImg());
//
//            bmp = getResizedBitmap(bmp, );
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            Image image1 = Image.getInstance(stream.toByteArray());
//            // image1.scaleAbsolute(300.0f, 300.0f);
//            image1.setAlignment(Element.ALIGN_RIGHT);
//            //合理压缩，h>w，按w压缩，否则按w压缩
//            //int percent=getPercent(heigth, width);
//            //统一按照宽度压缩
//            int percent = getPercent2(bmp.getHeight(), bmp.getWidth());
//            System.out.println("--" + percent);
//            //设置图片居中显示
//            image1.setAlignment(Image.MIDDLE);
//            //直接设置图片的大小~~~~~~~第三种解决方案，按固定比例压缩
//            //jpg1.scaleAbsolute(210.0f, 297.0f);
//            //按百分比显示图片的比例
//            image1.scalePercent(percent);//表示是原来图像的比例
//
//            document.add(image1);
            document.newPage();
            //构造表头
            buildHeader(table, setChineseFont(), objbean, list);
            document.add(table);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 第一种解决方案
     * 在不改变图片形状的同时，判断，如果h>w，则按h压缩，否则在w>h或w=h的情况下，按宽度压缩
     *
     * @param h
     * @param w
     * @return
     */

    public static int getPercent(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        if (h > w) {
            p2 = 297 / h * 100;
        } else {
            p2 = 210 / w * 100;
        }
        p = Math.round(p2);
        return p;
    }

    /**
     * 第二种解决方案，统一按照宽度压缩
     * 这样来的效果是，所有图片的宽度是相等的，自我认为给客户的效果是最好的
     *
     * @param
     */
    public static int getPercent2(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        p2 = 530 / w * 100;
        System.out.println("--" + p2);
        p = Math.round(p2);
        return p;
    }

    /**
     * 第三种解决方案，就是直接压缩，不安像素比例，全部压缩到固定值，如210*297
     *
     * @param
     */
//    public static void main(String[] args) {
//        TestCreatePDF pt=new TestCreatePDF();
//        pt.creatPDF("imgCreatPdf");
//    }
    private static void buildHeader(PdfPTable table, Font baseFont,  DocPendingBean.ObjBean objbean, List<DocPendingBean.AttributesBean.ApproveListInfoBean> list) {


        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setColspan(2);
        pdfPCell.setBorder(0);
        pdfPCell.setFixedHeight(80);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setPhrase(new Phrase(objbean.getTitel(), baseFont));
        table.addCell(pdfPCell);

        pdfPCell = new PdfPCell();
        pdfPCell.setColspan(1);        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setBorder(0);
        pdfPCell.setFixedHeight(60);
        pdfPCell.setPhrase(new Phrase("所属部门", baseFont));
        table.addCell(pdfPCell);

        PdfPCell pdfPCell2 = new PdfPCell();
        pdfPCell2.setColspan(1);
        pdfPCell2.setBorder(0);
        pdfPCell2.setFixedHeight(60);
        pdfPCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell2.setPhrase(new Phrase(objbean.getDepartmentName(), baseFont));
        table.addCell(pdfPCell2);

        pdfPCell = new PdfPCell();
        pdfPCell.setColspan(1);
        pdfPCell.setFixedHeight(60);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setBorder(0);
        pdfPCell.setPhrase(new Phrase("发文类型", baseFont));
        table.addCell(pdfPCell);

        pdfPCell = new PdfPCell();
        pdfPCell.setColspan(1);
        pdfPCell.setFixedHeight(60);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setBorder(0);
        pdfPCell.setPhrase(new Phrase(objbean.getRequestFlag(), baseFont));
        table.addCell(pdfPCell);

        pdfPCell = new PdfPCell();
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setColspan(1);
        pdfPCell.setFixedHeight(60);
        pdfPCell.setBorder(0);
        pdfPCell.setPhrase(new Phrase("发文份数", baseFont));
        table.addCell(pdfPCell);

        pdfPCell = new PdfPCell();
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setColspan(1);
        pdfPCell.setFixedHeight(60);
        pdfPCell.setBorder(0);
        pdfPCell.setPhrase(new Phrase("3", baseFont));
        table.addCell(pdfPCell);


        pdfPCell = new PdfPCell();
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setColspan(1);
        pdfPCell.setFixedHeight(60);
        pdfPCell.setBorder(0);
        pdfPCell.setPhrase(new Phrase("秘密等级", baseFont));
        table.addCell(pdfPCell);

        pdfPCell = new PdfPCell();
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setColspan(1);
        pdfPCell.setFixedHeight(60);
        pdfPCell.setBorder(0);
        if (objbean.getSecretLevel().equals("1")) {
            pdfPCell.setPhrase(new Phrase("一级", baseFont));
        } else if (objbean.getSecretLevel().equals("2")) {
            pdfPCell.setPhrase(new Phrase("二级", baseFont));
        } else if (objbean.getSecretLevel().equals("3")) {
            pdfPCell.setPhrase(new Phrase("三级", baseFont));
        }
        table.addCell(pdfPCell);

        pdfPCell = new PdfPCell();
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setColspan(1);
        pdfPCell.setFixedHeight(60);
        pdfPCell.setBorder(0);
        pdfPCell.setPhrase(new Phrase("紧急程度", baseFont));
        table.addCell(pdfPCell);

        pdfPCell = new PdfPCell();
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setColspan(1);
        pdfPCell.setFixedHeight(60);
        pdfPCell.setBorder(0);
        if (objbean.getUrgentLevel().equals("1")) {
            pdfPCell.setPhrase(new Phrase("一级", baseFont));
        } else if (objbean.getUrgentLevel().equals("2")) {
            pdfPCell.setPhrase(new Phrase("二级", baseFont));
        } else if (objbean.getUrgentLevel().equals("3")) {
            pdfPCell.setPhrase(new Phrase("三级", baseFont));
        }
        table.addCell(pdfPCell);


        pdfPCell = new PdfPCell();
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setColspan(1);
        pdfPCell.setFixedHeight(60);
        pdfPCell.setBorder(0);
        pdfPCell.setPhrase(new Phrase("公文模式", baseFont));
        table.addCell(pdfPCell);

        pdfPCell = new PdfPCell();
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setColspan(1);
        pdfPCell.setFixedHeight(60);
        pdfPCell.setBorder(0);
        if (objbean.getSchema().equals("1")) {
            pdfPCell.setPhrase(new Phrase("审批", baseFont));
        } else if (objbean.getSchema().equals("2")) {
            pdfPCell.setPhrase(new Phrase("会签", baseFont));
        }
        table.addCell(pdfPCell);


        if (list != null && list.size() > 0) {

            if (list.size() > 0) {
                String conent = list.get(0).getApproveOpinion();
                String name = list.get(0).getApprovePersonName();
                if (!TextUtils.isEmpty(list.get(0).getCreateDate())) {
                    if (list.get(0).getApproveFlag().equals("1")) {

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                        table.addCell(pdfPCell);

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase(conent, baseFont));
                        table.addCell(pdfPCell);
                    } else if (list.get(0).getApproveFlag().equals("2")) {
                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(100);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                        table.addCell(pdfPCell);
                        try {
                            // Bitmap bmp = BitmapFactory.decodeFile(list.get(1).getApproveResultFile());
                            Bitmap bmp = decodeUriAsBitmapFromNet(list.get(0).getApproveResultFile());
                            bmp = getResizedBitmap(bmp, 200, 200);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            Image image = Image.getInstance(stream.toByteArray());
                            PdfPCell imageCell = new PdfPCell(image);
                            imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            imageCell.setFixedHeight(100);
                            imageCell.setColspan(1);
                            imageCell.setBorder(0);
                            table.addCell(imageCell);
                        } catch (Exception ex) {
                        }
                    } else if (list.get(0).getApproveFlag().equals("3")) {
                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                        table.addCell(pdfPCell);

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase(name, baseFont));
                        table.addCell(pdfPCell);
                    }
                } else {

                    if (!list.get(0).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                        table.addCell(pdfPCell);

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase(name, baseFont));
                        table.addCell(pdfPCell);
                    } else {


                        try {
                            if (!TextUtils.isEmpty(MyApplication.getInstance().path_image)) {

                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(100);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                                table.addCell(pdfPCell);

                                Bitmap bmp = BitmapFactory.decodeFile(MyApplication.getInstance().path_image);
                                bmp = getResizedBitmap(bmp, 200, 200);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                Image image = Image.getInstance(stream.toByteArray());
                                PdfPCell imageCell = new PdfPCell(image);
                                imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                imageCell.setFixedHeight(100);
                                imageCell.setColspan(1);
                                imageCell.setBorder(0);
                                table.addCell(imageCell);
                            } else if (!TextUtils.isEmpty(MyApplication.getInstance().path_content)) {

                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(60);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                                table.addCell(pdfPCell);

                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(60);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase(MyApplication.getInstance().path_content, baseFont));
                                table.addCell(pdfPCell);
                            } else if (!TextUtils.isEmpty(MyApplication.getInstance().path_content)) {
                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(60);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                                table.addCell(pdfPCell);

                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(60);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase(name, baseFont));
                                table.addCell(pdfPCell);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


            if (list.size() > 1) {
                String conent = list.get(1).getApproveOpinion();
                String name = list.get(1).getApprovePersonName();
                if (!TextUtils.isEmpty(list.get(1).getCreateDate())) {
                    if (list.get(1).getApproveFlag().equals("1")) {

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                        table.addCell(pdfPCell);

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase(conent, baseFont));
                        table.addCell(pdfPCell);
                    } else if (list.get(1).getApproveFlag().equals("2")) {
                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(100);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                        table.addCell(pdfPCell);
                        try {
                            //Bitmap bmp = BitmapFactory.decodeFile(list.get(1).getApproveResultFile());
                            Bitmap bmp = decodeUriAsBitmapFromNet(list.get(1).getApproveResultFile());
                            bmp = getResizedBitmap(bmp, 200, 200);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            Image image = Image.getInstance(stream.toByteArray());
                            PdfPCell imageCell = new PdfPCell(image);
                            imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            imageCell.setFixedHeight(100);
                            imageCell.setColspan(1);
                            imageCell.setBorder(0);
                            table.addCell(imageCell);
                        } catch (Exception ex) {
                        }
                    } else if (list.get(1).getApproveFlag().equals("3")) {
                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                        table.addCell(pdfPCell);

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase(name, baseFont));
                        table.addCell(pdfPCell);
                    }
                } else {

                    if (!list.get(1).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                        table.addCell(pdfPCell);

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase(name, baseFont));
                        table.addCell(pdfPCell);
                    } else {
                        try {
                            if (!TextUtils.isEmpty(MyApplication.getInstance().path_image)) {


                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(100);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                                table.addCell(pdfPCell);

                                Bitmap bmp = BitmapFactory.decodeFile(MyApplication.getInstance().path_image);
                                bmp = getResizedBitmap(bmp, 200, 200);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                Image image = Image.getInstance(stream.toByteArray());
                                PdfPCell imageCell = new PdfPCell(image);
                                imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                imageCell.setFixedHeight(100);
                                imageCell.setColspan(1);
                                imageCell.setBorder(0);
                                table.addCell(imageCell);
                            } else if (!TextUtils.isEmpty(MyApplication.getInstance().path_content)) {

                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(60);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                                table.addCell(pdfPCell);

                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(60);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase(MyApplication.getInstance().path_content, baseFont));
                                table.addCell(pdfPCell);
                            } else if (!TextUtils.isEmpty(MyApplication.getInstance().path_content)) {
                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(60);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                                table.addCell(pdfPCell);

                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(60);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase(name, baseFont));
                                table.addCell(pdfPCell);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


            if (list.size() > 2) {
                String conent = list.get(2).getApproveOpinion();
                String name = list.get(2).getApprovePersonName();
                if (!TextUtils.isEmpty(list.get(2).getCreateDate())) {
                    if (list.get(2).getApproveFlag().equals("1")) {

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                        table.addCell(pdfPCell);

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase(conent, baseFont));
                        table.addCell(pdfPCell);
                    } else if (list.get(2).getApproveFlag().equals("2")) {
                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(100);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                        table.addCell(pdfPCell);
                        try {
                            // Bitmap bmp = BitmapFactory.decodeFile(MyApplication.getInstance().path_image);
                            Bitmap bmp = decodeUriAsBitmapFromNet(list.get(2).getApproveResultFile());
                            bmp = getResizedBitmap(bmp, 200, 200);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            Image image = Image.getInstance(stream.toByteArray());
                            PdfPCell imageCell = new PdfPCell(image);
                            imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            imageCell.setFixedHeight(100);
                            imageCell.setColspan(1);
                            imageCell.setBorder(0);
                            table.addCell(imageCell);
                        } catch (Exception ex) {
                        }
                    } else if (list.get(2).getApproveFlag().equals("3")) {
                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("审批人", baseFont));
                        table.addCell(pdfPCell);

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase(name, baseFont));
                        table.addCell(pdfPCell);
                    }
                } else {
                    if (!list.get(2).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                        table.addCell(pdfPCell);

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase(name, baseFont));
                        table.addCell(pdfPCell);
                    } else {

                        try {
                            if (!TextUtils.isEmpty(MyApplication.getInstance().path_image)) {


                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(100);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                                table.addCell(pdfPCell);

                                Bitmap bmp = BitmapFactory.decodeFile(MyApplication.getInstance().path_image);
                                bmp = getResizedBitmap(bmp, 200, 200);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                Image image = Image.getInstance(stream.toByteArray());
                                PdfPCell imageCell = new PdfPCell(image);
                                imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                imageCell.setFixedHeight(100);
                                imageCell.setColspan(1);
                                imageCell.setBorder(0);
                                table.addCell(imageCell);
                            } else if (!TextUtils.isEmpty(MyApplication.getInstance().path_content)) {

                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(60);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                                table.addCell(pdfPCell);

                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(60);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase(MyApplication.getInstance().path_content, baseFont));
                                table.addCell(pdfPCell);
                            } else if (!TextUtils.isEmpty(MyApplication.getInstance().path_content)) {
                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(60);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                                table.addCell(pdfPCell);

                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(60);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase(name, baseFont));
                                table.addCell(pdfPCell);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


            if (list.size() > 3) {
                String conent = list.get(3).getApproveOpinion();
                String name = list.get(3).getApprovePersonName();
                if (!TextUtils.isEmpty(list.get(3).getCreateDate())) {
                    if (list.get(3).getApproveFlag().equals("1")) {

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                        table.addCell(pdfPCell);

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase(conent, baseFont));
                        table.addCell(pdfPCell);
                    } else if (list.get(3).getApproveFlag().equals("2")) {
                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(100);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                        table.addCell(pdfPCell);
                        try {
                            // Bitmap bmp = BitmapFactory.decodeFile(MyApplication.getInstance().path_image);
                            Bitmap bmp = decodeUriAsBitmapFromNet(list.get(3).getApproveResultFile());
                            bmp = getResizedBitmap(bmp, 200, 200);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            Image image = Image.getInstance(stream.toByteArray());
                            PdfPCell imageCell = new PdfPCell(image);
                            imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            imageCell.setFixedHeight(100);
                            imageCell.setColspan(1);
                            imageCell.setBorder(0);
                            table.addCell(imageCell);
                        } catch (Exception ex) {
                        }
                    } else if (list.get(3).getApproveFlag().equals("3")) {
                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                        table.addCell(pdfPCell);

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase(name, baseFont));
                        table.addCell(pdfPCell);
                    }
                } else {


                    if (!list.get(3).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                        table.addCell(pdfPCell);

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase(name, baseFont));
                        table.addCell(pdfPCell);
                    } else {


                        if (!list.get(3).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
                            pdfPCell = new PdfPCell();
                            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            pdfPCell.setColspan(1);
                            pdfPCell.setFixedHeight(60);
                            pdfPCell.setBorder(0);
                            pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                            table.addCell(pdfPCell);

                            pdfPCell = new PdfPCell();
                            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            pdfPCell.setColspan(1);
                            pdfPCell.setFixedHeight(60);
                            pdfPCell.setBorder(0);
                            pdfPCell.setPhrase(new Phrase(name, baseFont));
                            table.addCell(pdfPCell);
                        } else {

                            try {
                                if (!TextUtils.isEmpty(MyApplication.getInstance().path_image)) {


                                    pdfPCell = new PdfPCell();
                                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    pdfPCell.setColspan(1);
                                    pdfPCell.setFixedHeight(100);
                                    pdfPCell.setBorder(0);
                                    pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                                    table.addCell(pdfPCell);

                                    Bitmap bmp = BitmapFactory.decodeFile(MyApplication.getInstance().path_image);
                                    bmp = getResizedBitmap(bmp, 200, 200);
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                    Image image = Image.getInstance(stream.toByteArray());
                                    PdfPCell imageCell = new PdfPCell(image);
                                    imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    imageCell.setFixedHeight(100);
                                    imageCell.setColspan(1);
                                    imageCell.setBorder(0);
                                    table.addCell(imageCell);
                                } else if (!TextUtils.isEmpty(MyApplication.getInstance().path_content)) {

                                    pdfPCell = new PdfPCell();
                                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    pdfPCell.setColspan(1);
                                    pdfPCell.setFixedHeight(60);
                                    pdfPCell.setBorder(0);
                                    pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                                    table.addCell(pdfPCell);

                                    pdfPCell = new PdfPCell();
                                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    pdfPCell.setColspan(1);
                                    pdfPCell.setFixedHeight(60);
                                    pdfPCell.setBorder(0);
                                    pdfPCell.setPhrase(new Phrase(MyApplication.getInstance().path_content, baseFont));
                                    table.addCell(pdfPCell);
                                } else if (!TextUtils.isEmpty(MyApplication.getInstance().path_content)) {
                                    pdfPCell = new PdfPCell();
                                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    pdfPCell.setColspan(1);
                                    pdfPCell.setFixedHeight(60);
                                    pdfPCell.setBorder(0);
                                    pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                                    table.addCell(pdfPCell);

                                    pdfPCell = new PdfPCell();
                                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    pdfPCell.setColspan(1);
                                    pdfPCell.setFixedHeight(60);
                                    pdfPCell.setBorder(0);
                                    pdfPCell.setPhrase(new Phrase(name, baseFont));
                                    table.addCell(pdfPCell);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }


            if (list.size() > 4) {
                String conent = list.get(4).getApproveOpinion();
                String name = list.get(4).getApprovePersonName();
                if (!TextUtils.isEmpty(list.get(4).getCreateDate())) {
                    if (list.get(4).getApproveFlag().equals("1")) {

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                        table.addCell(pdfPCell);

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase(conent, baseFont));
                        table.addCell(pdfPCell);
                    } else if (list.get(4).getApproveFlag().equals("2")) {
                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(100);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                        table.addCell(pdfPCell);
                        try {
                            // Bitmap bmp = BitmapFactory.decodeFile(MyApplication.getInstance().path_image);
                            Bitmap bmp = decodeUriAsBitmapFromNet(list.get(4).getApproveResultFile());
                            bmp = getResizedBitmap(bmp, 200, 200);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            Image image = Image.getInstance(stream.toByteArray());
                            PdfPCell imageCell = new PdfPCell(image);
                            imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            imageCell.setFixedHeight(100);
                            imageCell.setColspan(1);
                            imageCell.setBorder(0);
                            table.addCell(imageCell);
                        } catch (Exception ex) {
                        }
                    } else if (list.get(4).getApproveFlag().equals("3")) {
                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                        table.addCell(pdfPCell);

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase(name, baseFont));
                        table.addCell(pdfPCell);
                    }
                } else {


                    if (!list.get(4).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                        table.addCell(pdfPCell);

                        pdfPCell = new PdfPCell();
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(1);
                        pdfPCell.setFixedHeight(60);
                        pdfPCell.setBorder(0);
                        pdfPCell.setPhrase(new Phrase(name, baseFont));
                        table.addCell(pdfPCell);
                    } else {

                        try {
                            if (!TextUtils.isEmpty(MyApplication.getInstance().path_image)) {


                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(100);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                                table.addCell(pdfPCell);

                                Bitmap bmp = BitmapFactory.decodeFile(MyApplication.getInstance().path_image);
                                bmp = getResizedBitmap(bmp, 200, 200);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                Image image = Image.getInstance(stream.toByteArray());
                                PdfPCell imageCell = new PdfPCell(image);
                                imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                imageCell.setFixedHeight(100);
                                imageCell.setColspan(1);
                                imageCell.setBorder(0);
                                table.addCell(imageCell);
                            } else if (!TextUtils.isEmpty(MyApplication.getInstance().path_content)) {

                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(60);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                                table.addCell(pdfPCell);

                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(60);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase(MyApplication.getInstance().path_content, baseFont));
                                table.addCell(pdfPCell);
                            } else if (!TextUtils.isEmpty(MyApplication.getInstance().path_content)) {
                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(60);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase("会签人", baseFont));
                                table.addCell(pdfPCell);

                                pdfPCell = new PdfPCell();
                                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCell.setColspan(1);
                                pdfPCell.setFixedHeight(60);
                                pdfPCell.setBorder(0);
                                pdfPCell.setPhrase(new Phrase(name, baseFont));
                                table.addCell(pdfPCell);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


        }

//
//        try {
////            String field="f"+ String.valueOf(currentInj.getArea()) + String.valueOf(currentInj.
////                    getPoint());
//            //  Drawable d = context.getResources().getDrawable(R.mipmap.ic_logo);//ContextCompat.getDrawable(context, context.getResources().
////                    getIdentifier(field, "drawable", context.getPackageName()));
//
////                BitmapDrawable bitDw = ((BitmapDrawable) d);
////                Bitmap bmp = bitDw.getBitmap();
//
//            if (!TextUtils.isEmpty(MyApplication.getInstance().path_image)) {
//
//
//                pdfPCell = new PdfPCell();
//                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                pdfPCell.setColspan(1);
//                pdfPCell.setFixedHeight(100);
//                pdfPCell.setBorder(0);
//                pdfPCell.setPhrase(new Phrase("审批人", baseFont));
//                table.addCell(pdfPCell);
//
//                Bitmap bmp = BitmapFactory.decodeFile(MyApplication.getInstance().path_image);
//                bmp = getResizedBitmap(bmp, 200, 200);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                Image image = Image.getInstance(stream.toByteArray());
//                PdfPCell imageCell = new PdfPCell(image);
//                imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                imageCell.setFixedHeight(100);
//                imageCell.setColspan(1);
//                imageCell.setBorder(0);
////            imageCell.setRowspan(2);
////              imageCell.setBorder(0);
//                table.addCell(imageCell);
//            } else if (!TextUtils.isEmpty(MyApplication.getInstance().path_content)) {
//
//                pdfPCell = new PdfPCell();
//                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                pdfPCell.setColspan(1);
//                pdfPCell.setFixedHeight(60);
//                pdfPCell.setBorder(0);
//                pdfPCell.setPhrase(new Phrase("审批人", baseFont));
//                table.addCell(pdfPCell);
//
//                pdfPCell = new PdfPCell();
//                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                pdfPCell.setColspan(1);
//                pdfPCell.setFixedHeight(60);
//                pdfPCell.setBorder(0);
//                pdfPCell.setPhrase(new Phrase(MyApplication.getInstance().path_content, baseFont));
//                table.addCell(pdfPCell);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    /**
     * @param uri：图片的本地url地址
     * @return Bitmap；
     */
    private Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url
     * @return
     */
    private static Bitmap decodeUriAsBitmapFromNet(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }


    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        if (resizedBitmap == null) {
            resizedBitmap = Bitmap.createBitmap(
                    bm, 0, 0, width, height, matrix, false);
            bm.recycle();
        }

        return resizedBitmap;
    }

    public static void makepdf(ArrayList<VideoModel> dataList1) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
//        File f = new File(Environment.getExternalStorageDirectory(), filename);
//        if (!f.exists()) {
//            f.mkdirs();
//        }
        PdfWriter.getInstance(document, new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "test.pdf"));
        document.open();


        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK);
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD, BaseColor.BLACK);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);
        Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLACK);


        Paragraph bottomLine = new Paragraph("Appstic It", font2);
        bottomLine.setAlignment(Element.ALIGN_CENTER);


        Paragraph bottomLine1 = new Paragraph("Head Office: Appstic It,Level- 2, Rangmohal Towre, Sylhet", font);
        bottomLine1.setAlignment(Element.ALIGN_CENTER);

        Paragraph bottomLine2 = new Paragraph("01680680000, 01626926214  Web : www.appsticit.com", font);
        bottomLine2.setAlignment(Element.ALIGN_CENTER);


        Paragraph bottomLine3 = new Paragraph("Contact us for Pro-Version", font);
        bottomLine3.setAlignment(Element.ALIGN_CENTER);

        Paragraph money_receipt = new Paragraph("2018-11-16", font3);
        money_receipt.setAlignment(Element.ALIGN_CENTER);


        PdfPTable t = new PdfPTable(4);
        t.setSpacingBefore(25);
        t.setSpacingAfter(15);
        t.setWidthPercentage(100f);
        PdfPCell c1 = new PdfPCell(new Phrase("Product Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setColspan(1);
        c1.setBorder(0);
        t.addCell(c1);

        c1 = new PdfPCell(new Phrase("Buying Price"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setColspan(1);
        c1.setBorder(0);
        t.addCell(c1);

        c1 = new PdfPCell(new Phrase("Selling Price"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setColspan(1);
        c1.setBorder(0);
        t.addCell(c1);

        c1 = new PdfPCell(new Phrase("Profit"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setColspan(1);
        c1.setBorder(0);
        t.addCell(c1);


        // ArrayList<VideoModel> dataList1 = df1.arrayList(getIntent().getStringExtra("date")) ;

        int buy = 0;
        int sell = 0;
        int toatl = 0;
        int profit = 0;
        for (int i = 0; i < dataList1.size(); i++) {

            c1 = new PdfPCell(new Phrase(dataList1.get(i).getName()));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setColspan(1);
            c1.setBorder(0);
            t.addCell(c1);

            buy += dataList1.get(i).buy_Price;
            c1 = new PdfPCell(new Phrase(dataList1.get(i).buy_Price));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setColspan(1);
            c1.setBorder(0);
            t.addCell(c1);

            sell += dataList1.get(i).sell_Price;
            c1 = new PdfPCell(new Phrase(dataList1.get(i).sell_Price));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setColspan(1);
            c1.setBorder(0);
            t.addCell(c1);


            toatl = dataList1.get(i).sell_Price - dataList1.get(i).buy_Price;
            c1 = new PdfPCell(new Phrase(toatl + ""));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setColspan(1);
            c1.setBorder(0);
            t.addCell(c1);

            profit += toatl;

        }

        c1 = new PdfPCell(new Phrase("Total ", font1));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setVerticalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(0);
        c1.setFixedHeight(180);
        c1.setColspan(1);

        t.addCell(c1);

        c1 = new PdfPCell(new Phrase(buy + "", font1));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setColspan(1);
        c1.setBorder(0);
        t.addCell(c1);

        c1 = new PdfPCell(new Phrase(sell + "", font1));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setColspan(1);
        c1.setBorder(0);
        t.addCell(c1);

        c1 = new PdfPCell(new Phrase(profit + "", font1));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setColspan(1);
        c1.setBorder(0);
        t.addCell(c1);

        t.setHorizontalAlignment(Element.ALIGN_BOTTOM);
        document.bottom(document.bottomMargin());
        document.add(bottomLine);
        document.add(bottomLine1);
        document.add(bottomLine2);
        document.add(bottomLine3);
        document.add(money_receipt);
        document.add(t);
        document.close();

    }

    public PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(20);
        cell.setFixedHeight(180);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;

    }

    public class RotateEvent extends PdfPageEventHelper {
        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            writer.addPageDictEntry(PdfName.ROTATE, PdfPage.SEASCAPE);
        }
    }

    public static void AddLoad(Context context, String fileName) {

        try {

            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                    + fileName);
            Log.d(PdfGenerator.class.toString(), "file:" + f);
            FileOutputStream outputFile = new FileOutputStream(f);

            Document pdfDocument = new Document(PageSize.A4);
            PdfWriter.getInstance(pdfDocument, outputFile);

            pdfDocument.open();

            Paragraph paragraph1 = new Paragraph("****" + "\nTuition Class - For Best Choice" + "\nPerformance Report", FontFactory.getFont(FontFactory.COURIER, 18, Font.BOLD, new CMYKColor(100, 100, 0, 80)));
            paragraph1.setSpacingBefore(50);
            paragraph1.setAlignment(Element.ALIGN_CENTER);

            Paragraph paragraphSName = new Paragraph("Student Name : SID", FontFactory.getFont(FontFactory.COURIER, 16, Font.NORMAL, new CMYKColor(100, 100, 0, 80)));
            paragraph1.setSpacingBefore(10);

            Paragraph paragraph2 = new Paragraph("1.  Attendance ", FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD, new CMYKColor(100, 100, 0, 80)));
            paragraph2.setSpacingBefore(20);
            paragraph2.setAlignment(Element.ALIGN_LEFT);

            Paragraph paragraph3 = new Paragraph("wang", FontFactory.getFont(FontFactory.COURIER, 16, Font.NORMAL, new CMYKColor(100, 100, 0, 80)));
            paragraph3.setSpacingBefore(20);
            paragraph3.setAlignment(Element.ALIGN_LEFT);

            Paragraph paragraph4 = new Paragraph("2. Payments ", FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD, new CMYKColor(100, 100, 0, 80)));
            paragraph4.setSpacingBefore(20);
            paragraph4.setAlignment(Element.ALIGN_LEFT);

            Paragraph paragraphPerfomance = new Paragraph("3. Perfomance compared with overall class ", FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD, new CMYKColor(100, 100, 0, 80)));
            paragraphPerfomance.setSpacingBefore(10);
            paragraphPerfomance.setAlignment(Element.ALIGN_LEFT);
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);
            //BaseFont.createFont("assets/fonts/STSONG.TTF",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);//设置中文显示问题
            Font font = new Font(baseFont, 32);//设置字体格式及大小
            font.setColor(BaseColor.RED);
            Paragraph paragraph5 = new Paragraph("王宫了是的发送到 ", font);// FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD, new CMYKColor(100, 100, 0, 80)));
            paragraph5.setSpacingBefore(50);
            paragraph5.setAlignment(Element.ALIGN_RIGHT);

            //add chart of students perfomance compared to overall class
            Drawable d = context.getResources().getDrawable(R.mipmap.ic_logo);//ContextCompat.getDrawable(context, context.getResources().
//                    getIdentifier(field, "drawable", context.getPackageName()));
            BitmapDrawable bitDw = ((BitmapDrawable) d);
            Bitmap bmp = bitDw.getBitmap();
            bmp = getResizedBitmap(bmp, 300, 300);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 50, stream);
            Image image1 = Image.getInstance(stream.toByteArray());
            image1.scaleAbsolute(300.0f, 300.0f);
            image1.setAlignment(Element.ALIGN_RIGHT);
//            String path =String.valueOf(extr)+"/"+s;
//            Image image1 = Image.getInstance(path);
//            image1.scaleAbsolute(300.0f, 300.0f);
//            image1.setAlignment(Element.ALIGN_CENTER);

            Paragraph paragraph6 = new Paragraph("3.2. perfomance compared with past perfomance ", FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD, new CMYKColor(100, 100, 0, 80)));
            paragraph6.setSpacingBefore(70);
            paragraph6.setAlignment(Element.ALIGN_LEFT);

            // add chart of student perfomance report compared to history
//            RelativeLayout chart1 = lineChart;
//            chart1.setDrawingCacheEnabled(true);
//            chart1.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//            Bitmap bitmap1 = chart1.getDrawingCache();
//            String extr1 = context.getExternalFilesDir("/My app").toString();
//            File mFolder1 = new File(extr1);
//            if (!mFolder1.exists()) {
//                mFolder1.mkdir();
//            }
//            String s1 = "LineChart.png";
//            File f1 = new File(mFolder1.getAbsolutePath(), s1);
//            FileOutputStream fos1 = null;
//            fos1 = new FileOutputStream(f1);
//            bitmap1.compress(Bitmap.CompressFormat.JPEG, 50, fos1);
//            fos1.flush();
//            fos1.close();
//            bitmap1.recycle();

//            String path1 =String.valueOf(extr)+"/"+s1;
//            Image image2 = Image.getInstance(path1);
//            image2.scaleAbsolute(300.0f,300.0f);
//            image2.setAlignment(Element.ALIGN_CENTER);


            pdfDocument.add(paragraph1);
            pdfDocument.add(paragraphSName);
            pdfDocument.add(paragraph2);
            pdfDocument.add(paragraph3);
            pdfDocument.add(paragraph4);
            // pdfDocument.add(getPamentReport(SID,classID));
            pdfDocument.add(paragraphPerfomance);
            // pdfDocument.add(perfpmanceCompredToOverallClass(classID,SID));
            pdfDocument.newPage();
            pdfDocument.add(paragraph5);
            pdfDocument.add(image1);
            pdfDocument.add(paragraph6);
            pdfDocument.add(image1);

            pdfDocument.close();
            //out.close();
            // return outputFile;

        } catch (BadElementException e) {
            e.printStackTrace();
            // return null;
        } catch (DocumentException e) {
            e.printStackTrace();
            //  return null;
        } catch (IOException e) {
            e.printStackTrace();
            //  return null;
        }


    }

}
