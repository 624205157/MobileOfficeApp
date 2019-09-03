package com.mobilepolice.officeMobile.pdf;

import com.itextpdf.text.DocumentException;

import java.util.List;

public interface ExportManager {

    void write(List<String> list) throws DocumentException;
    void read();
    void update();
}
