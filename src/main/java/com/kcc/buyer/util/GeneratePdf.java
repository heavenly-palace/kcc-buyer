package com.kcc.buyer.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.kcc.buyer.domain.*;

import java.io.*;
import java.util.List;

public class GeneratePdf {

    // 定义全局的字体静态变量
    private Font titlefont;
    private Font headfont;
    private Font keyfont;
    private Font textfont;
    private Font orderNo;

    // 最大宽度
    private int maxWidth = 520;

    BaseFont bfChinese;

    {
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            titlefont = new Font(bfChinese, 16, Font.BOLD);
            headfont = new Font(bfChinese, 12, Font.NORMAL);
            keyfont = new Font(bfChinese, 10, Font.NORMAL);
            textfont = new Font(bfChinese, 10, Font.NORMAL);
            orderNo = new Font(bfChinese, 8, Font.NORMAL);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }



    public ByteArrayOutputStream generatePDF(OrderVO orderVO){

        Document document = new Document(); // 默认页面大小是A4
        document.addTitle("k.c communication");
        document.addAuthor("kcc-tech");
        document.addSubject("order");
        document.addKeywords("kcc");
        document.addCreator("sulei");

        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            writer.setPageEvent(new Watermark());
            writer.setPageEvent(new MyHeaderFooter());
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.open();

        //文本标题
        Paragraph titleOrder = createTitle("ORDER", titlefont);
        Paragraph titleBuyerAccount = createTitle("采购商账户信息", headfont);
        Paragraph titleSupplierAccount = createTitle("供应商账户信息", headfont);
        Paragraph titleOrderDetails = createTitle("订单产品明细", headfont);

        //订单编号
        Phrase number = new Phrase();
        number.setLeading(30f);
        number.setFont(orderNo);
        number.add("Number: " + orderVO.getOrderUuid());

        // 直线
        Paragraph p1 = new Paragraph();
        p1.setSpacingBefore(-10f);
        p1.setSpacingAfter(40f);
        p1.add(new Chunk(new LineSeparator()));

        CompanyVO supplierVO = orderVO.getSupplier();
        CompanyVO buyerVO = orderVO.getBuyer();

        //生成厂商公司基本信息表格
        PdfPTable tableCompany = createTable(new float[] { 60, 120, 60, 120 });
        createTableCompany(tableCompany, buyerVO, supplierVO);

        //生成采购商账户信息表格
        PdfPTable tableBuyerAccount = createTable(new float[] { 60, 120, 60, 120});
        createTableAccount(tableBuyerAccount, buyerVO);

        //生成供应商账户信息表格
        PdfPTable tableSupplierAccount = createTable(new float[] { 60, 120, 60, 120});
        createTableAccount(tableSupplierAccount, supplierVO);

        //生成订单明细表格
        List<OrderDetail> orderDetailList = orderVO.getOrderDetailList();
        PdfPTable tableOrderDetails = createTable(new float[] { 25, 60, 30, 15, 30, 30, 30});
        addOrderTableTitle(tableOrderDetails);
        for (int i = 0; i < orderDetailList.size(); i++) {
            OrderDetail orderDetail = orderDetailList.get(i);
            addOrderTableCell(tableOrderDetails, i+1, orderDetail);
        }
        addTableCell(tableOrderDetails, "金额大写", "(税前)"+orderVO.getUpperMoney(),2);
        addTableCell(tableOrderDetails, "(税后)"+orderVO.getUpperAtMoney(), 4);
        addTableCell(tableOrderDetails, "金额小写", "(税前)"+orderVO.getMoney(),2);
        addTableCell(tableOrderDetails, "(税后)"+orderVO.getAtMoney(), 4);

        try {
            document.add(titleOrder);
            document.add(number);
            document.add(p1);
            document.add(tableCompany);
            document.add(titleBuyerAccount);
            document.add(tableBuyerAccount);
            document.add(titleSupplierAccount);
            document.add(tableSupplierAccount);
            document.add(titleOrderDetails);
            document.add(tableOrderDetails);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();

        return baos;
    }

    /**
     * 创建头部标题文本
     * @param title
     * @param style
     * @return
     */
    private Paragraph createTitle(String title, Font style){
        Paragraph elements = new Paragraph(title, style);
        elements.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        elements.setIndentationLeft(12); //设置左缩进
        elements.setIndentationRight(12); //设置右缩进
        elements.setFirstLineIndent(24); //设置首行缩进
        elements.setLeading(20f); //行间距
        elements.setSpacingBefore(20f); //设置段落上空白
        elements.setSpacingAfter(10f); //设置段落下空白
        return elements;
    }

    /**
     * 创建厂商基本信息单元格
     * @param pdfPTable
     * @param buyerVO
     * @param supplierVO
     */
    private void createTableCompany(PdfPTable pdfPTable, CompanyVO buyerVO, CompanyVO supplierVO){
        addTableCell(pdfPTable, "供应商:", supplierVO.getName());
        addTableCell(pdfPTable, "采购商:", buyerVO.getName());

        addTableCell(pdfPTable, "联系人姓名:", supplierVO.getContacts());
        addTableCell(pdfPTable, "联系人姓名:", buyerVO.getContacts());

        addTableCell(pdfPTable, "电子邮件:", supplierVO.getEmail());
        addTableCell(pdfPTable, "电子邮件:", buyerVO.getEmail());

        addTableCell(pdfPTable, "电话:", supplierVO.getCellphone());
        addTableCell(pdfPTable, "电话:", buyerVO.getCellphone());

        addTableCell(pdfPTable, "传真:", supplierVO.getFax());
        addTableCell(pdfPTable, "传真:", buyerVO.getFax());

        addTableCell(pdfPTable, "手机:", supplierVO.getTelephone());
        addTableCell(pdfPTable, "手机:", buyerVO.getTelephone());

        addTableCell(pdfPTable, "地址:", supplierVO.getLocation());
        addTableCell(pdfPTable, "地址:", buyerVO.getLocation());
    }

    /**
     * 创建Company账户信息单元格
     * @param pdfPTable
     * @param supplierVO
     */
    private void createTableAccount(PdfPTable pdfPTable, CompanyVO supplierVO) {
        Account account = supplierVO.getAccount();

        addTableCell(pdfPTable, "公司全称:", supplierVO.getName());
        addTableCell(pdfPTable, "纳税人识别号:", account.getTfn());

        addTableCell(pdfPTable, "交货地址:", account.getLocation());
        addTableCell(pdfPTable, "电话号码:", account.getTelephone());

        addTableCell(pdfPTable, "开户银行:", account.getBankName());
        addTableCell(pdfPTable, "银行账号:", account.getBankAccount());

    }


    private void addOrderTableTitle(PdfPTable tableOrderDetails){
        tableOrderDetails.addCell(createCell("品目编号", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell("设备/服务描述", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell("单位包装", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell("数量", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell("币种", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell("单价", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell("总价", keyfont, Element.ALIGN_CENTER));
    }

    /**
     * 创建OrderDetail单元格
     * @param tableOrderDetails
     * @param index 下标
     * @param orderDetail
     */
    private void addOrderTableCell(PdfPTable tableOrderDetails, int index, OrderDetail orderDetail){
        tableOrderDetails.addCell(createCell(String.valueOf(index), keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell(orderDetail.getDescribe(), keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell(orderDetail.getPack(), keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell(String.valueOf(orderDetail.getQuantity()), keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell(orderDetail.getCurrency(), keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell(String.valueOf(orderDetail.getPrice()), keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCell(String.valueOf(orderDetail.getTotalPrice()), keyfont, Element.ALIGN_CENTER));
    }

    private void addTableCell(PdfPTable pdfPTable, Object key, Object value){
        pdfPTable.addCell(createCell(String.valueOf(key), keyfont, Element.ALIGN_CENTER));
        pdfPTable.addCell(createCell(String.valueOf(value), keyfont, Element.ALIGN_CENTER));
    }

    private void addTableCell(PdfPTable pdfPTable, Object key, Object value, int colspan){
        pdfPTable.addCell(createCell(String.valueOf(key), keyfont, Element.ALIGN_CENTER));
        pdfPTable.addCell(createCell(String.valueOf(value), keyfont, Element.ALIGN_CENTER, colspan));
    }

    /**
     * 给指定table创建单元格
     * @param pdfPTable
     * @param value
     * @param colspan 跨列
     */
    private void addTableCell(PdfPTable pdfPTable, Object value, int colspan){
        pdfPTable.addCell(createCell(String.valueOf(value), keyfont, Element.ALIGN_CENTER, colspan));
    }


    /**------------------------创建表格单元格的方法start----------------------------*/
    /**
     * 创建单元格(指定字体)
     * @param value
     * @param font
     * @return
     */
    private PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平..）
     * @param value
     * @param font
     * @param align
     * @return
     */
    private PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并）
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @return
     */
    private PdfPCell createCell(String value, Font font, int align, int colspan) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @param boderFlag
     * @return
     */
    private PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setPadding(3.0f);
        if (!boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        } else if (boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(0.0f);
            cell.setPaddingBottom(15.0f);
        }
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平..、边框宽度：0表示无边框、内边距）
     * @param value
     * @param font
     * @param align
     * @param borderWidth
     * @param paddingSize
     * @param flag
     * @return
     */
    private PdfPCell createCell(String value, Font font, int align, float[] borderWidth, float[] paddingSize, boolean flag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        cell.setBorderWidthLeft(borderWidth[0]);
        cell.setBorderWidthRight(borderWidth[1]);
        cell.setBorderWidthTop(borderWidth[2]);
        cell.setBorderWidthBottom(borderWidth[3]);
        cell.setPaddingTop(paddingSize[0]);
        cell.setPaddingBottom(paddingSize[1]);
        if (flag) {
            cell.setColspan(2);
        }
        return cell;
    }
/**------------------------创建表格单元格的方法end----------------------------*/


/**--------------------------创建表格的方法start------------------- ---------*/
    /**
     * 创建默认列宽，指定列数、水平(居中、右、左)的表格
     * @param colNumber
     * @param align
     * @return
     */
    private PdfPTable createTable(int colNumber, int align) {
        PdfPTable table = new PdfPTable(colNumber);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(align);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    /**
     * 创建指定列宽、列数的表格
     * @param widths
     * @return
     */
    private PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    /**
     * 创建空白的表格
     * @return
     */
    private PdfPTable createBlankTable() {
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(0);
        table.addCell(createCell("", keyfont));
        table.setSpacingAfter(20.0f);
        table.setSpacingBefore(20.0f);
        return table;
    }
/**--------------------------创建表格的方法end------------------- ---------*/

}
