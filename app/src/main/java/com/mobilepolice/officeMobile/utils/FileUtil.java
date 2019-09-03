package com.mobilepolice.officeMobile.utils;

/**
 * Created by Administrator on 2017-03-28.
 */

import android.content.Context;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileUtil {
    // 文件的存储路径
    public static final String BASE_PATH = Environment
            .getExternalStorageDirectory().toString()
            + File.separator
            + "oa" + File.separator;
    public static final String RECORD_PATH = BASE_PATH + "record"
            + File.separator + "wxr" + File.separator;
    public static final String NET_PATH = BASE_PATH + "net" + File.separator;
    public static final String HEADIMAGE_PATH = BASE_PATH + "headimage"
            + File.separator;
    public static final String APK_PATH = BASE_PATH + "apk" + File.separator;
    public static final String FILE_PATH = BASE_PATH + "file" + File.separator;
    public static final String ERROR_PATH = BASE_PATH + "log" + File.separator;
    public static final String PDF_PATH = BASE_PATH + "pdf" + File.separator;
    public static final String DOC_ICON = BASE_PATH + "icon" + File.separator;

    public static void init() {
        String filepath;
        File file;
        filepath = FileUtil.BASE_PATH;
        file = new File(filepath);
        if (!file.exists()) {
            file.mkdirs();
        }
        filepath = FileUtil.NET_PATH;
        file = new File(filepath);
        if (!file.exists()) {
            file.mkdirs();
        }
        filepath = FileUtil.RECORD_PATH;
        file = new File(filepath);
        if (!file.exists()) {
            file.mkdirs();
        }
        filepath = FileUtil.HEADIMAGE_PATH;
        file = new File(filepath);
        if (!file.exists()) {
            file.mkdirs();
        }
        filepath = FileUtil.APK_PATH;
        file = new File(filepath);
        if (!file.exists()) {
            file.mkdirs();
        }
        filepath = FileUtil.FILE_PATH;
        file = new File(filepath);
        if (!file.exists()) {
            file.mkdirs();
        }
        filepath = FileUtil.ERROR_PATH;
        file = new File(filepath);
        if (!file.exists()) {
            file.mkdirs();
        }
        filepath = FileUtil.PDF_PATH;
        file = new File(filepath);
        if (!file.exists()) {
            file.mkdirs();
        }
        filepath = FileUtil.DOC_ICON;
        file = new File(filepath);
        if (!file.exists()) {
            file.mkdirs();
        }


    }

    // 删除一个文件夹
    public static void deleteFiles(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete();
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFiles(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        }
    }

    // 删除一个文件夹下所有文件
    public static void deleteFilesInFold(File file) {
        File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
        for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
            if (files[i].isFile()) {
                files[i].delete();
            }
        }
    }

    @SuppressWarnings("resource")
    public static long getFileSize(String filePath) throws Exception {
        File file = new File(filePath);
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        }
        return size;
    }

    public static String convertCodeAndGetText(String str_filepath) {// 转变编码
        File file = new File(str_filepath);
        BufferedReader reader;
        String text = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream in = new BufferedInputStream(fis);
            in.mark(4);
            byte[] first3bytes = new byte[3];
            in.read(first3bytes);
            in.reset();
            if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB
                    && first3bytes[2] == (byte) 0xBF) {// utf-8
                reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            } else if (first3bytes[0] == (byte) 0xFF
                    && first3bytes[1] == (byte) 0xFE) {
                reader = new BufferedReader(
                        new InputStreamReader(in, "unicode"));
            } else if (first3bytes[0] == (byte) 0xFE
                    && first3bytes[1] == (byte) 0xFF) {
                reader = new BufferedReader(new InputStreamReader(in,
                        "utf-16be"));
            } else if (first3bytes[0] == (byte) 0xFF
                    && first3bytes[1] == (byte) 0xFF) {
                reader = new BufferedReader(new InputStreamReader(in,
                        "utf-16le"));
            } else {
                reader = new BufferedReader(new InputStreamReader(in, "GBK"));
            }
            String str = reader.readLine();
            while (str != null) {
                text = text + str + "\n";
                str = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static void saveExpiredMess(Context context, String json, String path) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {//
            out = context.openFileOutput(path, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getExpiredMess(Context contexts, String path) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = contexts.openFileInput(path);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    public static final String ROOT_PATH = "wxr/";
    public static final String RECORD_DIR = "record/";

    //    public static final String RECORD_PATH = ROOT_PATH + RECORD_DIR;
    //获取文件存放根路径
    public static File getAppDir(Context context) {
        String dirPath = "";
        //SD卡是否存在
        boolean isSdCardExists = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
        boolean isRootDirExists = Environment.getExternalStorageDirectory().exists();
        if (isSdCardExists && isRootDirExists) {
            dirPath = String.format("%s/%s/", Environment.getExternalStorageDirectory().getAbsolutePath(), ROOT_PATH);
        } else {
            dirPath = String.format("%s/%s/", context.getApplicationContext().getFilesDir().getAbsolutePath(), ROOT_PATH);
        }

        File appDir = new File(dirPath);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        return appDir;
    }

    //获取录音存放路径
    public static File getAppRecordDir(Context context) {
        // File appDir = getAppDir(context);
        File recordDir = new File(RECORD_PATH);
        if (!recordDir.exists()) {
            recordDir.mkdir();
        }
        return recordDir;
    }
}
