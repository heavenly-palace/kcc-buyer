package com.kcc.buyer.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.kcc.buyer.domain.*;

import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GeneratePdf {

    // 定义全局的字体静态变量
    private Font titlefont;
    private Font headfont;
    private Font keyfont;
    private Font textfont;
    private Font signature;
    private Font orderId;

    // 最大宽度
    private int maxWidth = 520;

    BaseFont bfChinese;

    {
        try {
            //String fontPath = "E:\\BaiduNetdiskDownload\\微软雅黑.ttf";
            String fontPath = "/home/font/微软雅黑.ttf";
            bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            //Font yahei12 = new Font(baseFont1, 12f);
            //bfChinese = BaseFont.createFont("Microsoft YaHei", "GB2312", BaseFont.NOT_EMBEDDED);
            titlefont = new Font(bfChinese, 16, Font.BOLD);
            signature = new Font(bfChinese, 18, Font.NORMAL);
            headfont = new Font(bfChinese, 12, Font.NORMAL);
            keyfont = new Font(bfChinese, 10, Font.NORMAL);
            textfont = new Font(bfChinese, 10, Font.NORMAL);
            orderId = new Font(bfChinese, 12, Font.NORMAL);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }



    public ByteArrayOutputStream generatePDF(Order order){

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
        Paragraph titleBuyerAccount = createTitle("采购账户/增值税专用发票开票信息", headfont);
        Paragraph titleSupplierAccount = createTitle("供应商账户信息", headfont);
        Paragraph titleOrderDetails = createTitle("产品订单明细", headfont);
        Paragraph titlePayPlan = createTitle("付款计划", headfont);

        //订单编号
        Phrase number = new Phrase();
        number.setLeading(30f);
        number.setFont(orderId);
        number.add("No: " + order.getOrderUuid());

        // 直线
        Paragraph p1 = new Paragraph();
        p1.setSpacingBefore(-10f);
        p1.setSpacingAfter(40f);
        p1.add(new Chunk(new LineSeparator()));

        CompanyInfo supplier;
        CompanyInfo buyer;
        List<CompanyInfo> companyInfoList = order.getCompanyInfoList();
        if(companyInfoList.get(0).getType().equals(1)){
            buyer = companyInfoList.get(1);
            supplier = companyInfoList.get(0);
        }else {
            buyer = companyInfoList.get(0);
            supplier = companyInfoList.get(1);
        }

        //生成厂商公司基本信息表格
        PdfPTable tableCompany = createTable(new float[] { 40, 120, 40, 120 });
        createTableCompany(tableCompany, buyer, supplier);

        //生成采购商账户信息表格
        PdfPTable tableBuyerAccount = createTable(new float[] { 30, 120, 40, 80});
        createTableAccount(tableBuyerAccount, buyer);

        //生成供应商账户信息表格
        PdfPTable tableSupplierAccount = createTable(new float[] { 30, 120, 40, 80});
        createTableAccount(tableSupplierAccount, supplier);

        //生成订单明细表格
        List<OrderDetail> orderDetailList = order.getOrderDetailList();
        PdfPTable tableOrderDetails = createTable(new float[] { 20, 60, 30, 20, 30, 20, 30, 30, 30, 30});
        addOrderTableTitle(tableOrderDetails);
        double totalRateMoney = 0.00d;
        for (int i = 0; i < orderDetailList.size(); i++) {
            OrderDetail orderDetail = orderDetailList.get(i);
            totalRateMoney += addOrderTableCell(tableOrderDetails, i + 1, orderDetail);
        }
        addTableCell(tableOrderDetails, "（金额大写）：    " + order.getUpperAtMoney(),5);
        addTableCell(tableOrderDetails, "（金额小写）：" + String.format("%.2f", order.getAtMoney()) + "        （含税金：" + String.format("%.2f", totalRateMoney) + "）", 5);

        //生成付款明细项表格
        List<PayPlan> payPlanList = order.getPayPlanList();
        PdfPTable tablePayPlan = createTable(new float[] { 9, 50, 25, 25, 30});
        for (int i = 0; i < payPlanList.size(); i++) {
            PayPlan payPlan = payPlanList.get(i);
            addPayPlanTableCell(tablePayPlan, i+1, payPlan);
        }

        //生成订单备注信息
        Paragraph comment;
        comment = order.getComment().equals("") ? createContent("", keyfont) : createContent("备注：" + order.getComment(), keyfont);

        try {
            Paragraph p2 = new Paragraph();
            p2.add(new Chunk("signature:",signature));
            p2.setSpacingBefore(70f);
         //   p2.setSpacingAfter(40f);
            p2.add(new Chunk(new LineSeparator()));
            p2.setAlignment(2);
            p2.setIndentationLeft(300);
            p2.setPaddingTop(-200f);

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
            document.add(titlePayPlan);
            document.add(tablePayPlan);
            document.add(comment);
            document.add(p2);
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
        elements.setLeading(10f); //行间距
        elements.setSpacingBefore(20f); //设置段落上空白
        elements.setSpacingAfter(10f); //设置段落下空白
        return elements;
    }


    /**
     * 创建文本
     * @param title
     * @param style
     * @return
     */
    private Paragraph createContent(String title, Font style){
        Paragraph elements = new Paragraph(title, style);
        elements.setAlignment(0); //设置文字居中 0靠左   1，居中     2，靠右
        elements.setIndentationLeft(-20); //设置左缩进
        elements.setIndentationRight(0); //设置右缩进
        elements.setFirstLineIndent(24); //设置首行缩进
        elements.setLeading(10f); //行间距
        elements.setSpacingBefore(20f); //设置段落上空白
        elements.setSpacingAfter(10f); //设置段落下空白
        return elements;
    }

    /**
     * 创建厂商基本信息单元格
     * @param pdfPTable
     * @param buyer
     * @param supplier
     */
    private void createTableCompany(PdfPTable pdfPTable, CompanyInfo buyer, CompanyInfo supplier){
        addTableCell(pdfPTable, "供应商:", supplier.getName());
        addTableCell(pdfPTable, "采购商:", buyer.getName());

        addTableCell(pdfPTable, "联系人姓名:", supplier.getContacts());
        addTableCell(pdfPTable, "联系人姓名:", buyer.getContacts());

        addTableCell(pdfPTable, "电子邮件:", supplier.getEmail());
        addTableCell(pdfPTable, "电子邮件:", buyer.getEmail());

        addTableCell(pdfPTable, "电话:", supplier.getCellphone());
        addTableCell(pdfPTable, "电话:", buyer.getCellphone());

        addTableCell(pdfPTable, "传真:", supplier.getFax());
        addTableCell(pdfPTable, "传真:", buyer.getFax());

        addTableCell(pdfPTable, "手机:", supplier.getTelephone());
        addTableCell(pdfPTable, "手机:", buyer.getTelephone());

        addTableCell(pdfPTable, "地址:", supplier.getLocation());
        addTableCell(pdfPTable, "地址:", buyer.getLocation());
    }

    /**
     * 创建Company账户信息单元格
     * @param pdfPTable
     * @param supplier
     */
    private void createTableAccount(PdfPTable pdfPTable, CompanyInfo supplier) {
        AccountInfo supplierAccountInfo = supplier.getAccountInfo();

        addTableCell(pdfPTable, "公司全称:", supplier.getName());
        addTableCell(pdfPTable, "纳税人识别号:", supplierAccountInfo.getTfn());

        addTableCell(pdfPTable, "公司地址:", supplierAccountInfo.getLocation());
        addTableCell(pdfPTable, "电话号码:", supplierAccountInfo.getTelephone());

        addTableCell(pdfPTable, "开户银行:", supplierAccountInfo.getBankName());
        addTableCell(pdfPTable, "银行账号:", supplierAccountInfo.getBankAccount());

    }


    private void addOrderTableTitle(PdfPTable tableOrderDetails){
        tableOrderDetails.addCell(createCellColor("编号", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCellColor("设备/服务描述", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCellColor("单位包装", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCellColor("数量", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCellColor("币种", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCellColor("税率", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCellColor("税前单价", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCellColor("税金", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCellColor("税后单价", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCellColor("总价", keyfont, Element.ALIGN_CENTER));
    }

    private void addOrderPayPlan(PdfPTable tableOrderDetails){
        tableOrderDetails.addCell(createCellColor("编号", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCellColor("付款明细项", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCellColor("计划日期", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCellColor("付款比例", keyfont, Element.ALIGN_CENTER));
        tableOrderDetails.addCell(createCellColor("付款金额", keyfont, Element.ALIGN_CENTER));
    }

    /**
     * 创建OrderDetail单元格
     * @param tableOrderDetails
     * @param index 下标
     * @param orderDetail
     */
    private Double addOrderTableCell(PdfPTable tableOrderDetails, int index, OrderDetail orderDetail){
        int rowspan = 1;
        if(orderDetail.getManufacturerName() != null && !orderDetail.getManufacturerName().isEmpty()){ rowspan+=1; }
        if(orderDetail.getProductModel() != null && !orderDetail.getProductModel().isEmpty()){ rowspan+=1; }
        tableOrderDetails.addCell(createCellRowSpan(String.valueOf(index), keyfont, Element.ALIGN_CENTER, rowspan));
        tableOrderDetails.addCell(createCellRowSpan(orderDetail.getDescribe(), keyfont, Element.ALIGN_LEFT, 1 ));
        tableOrderDetails.addCell(createCellRowSpan(orderDetail.getPack(), keyfont, Element.ALIGN_CENTER, rowspan));
        tableOrderDetails.addCell(createCellRowSpan(String.valueOf(orderDetail.getQuantity()), keyfont, Element.ALIGN_CENTER, rowspan));
        tableOrderDetails.addCell(createCellRowSpan(orderDetail.getCurrency(), keyfont, Element.ALIGN_CENTER, rowspan));
        NumberFormat num = NumberFormat.getPercentInstance();
        String rates = num.format(orderDetail.getTaxRate() / 100);
        tableOrderDetails.addCell(createCellRowSpan(rates, keyfont, Element.ALIGN_CENTER, rowspan));
        tableOrderDetails.addCell(createCellRowSpan(String.valueOf(String.format("%.2f", orderDetail.getPrice())), keyfont, Element.ALIGN_CENTER, rowspan));
        Double rateMoney = orderDetail.getaTPrice() - orderDetail.getPrice();
        Double totalRateMoney = rateMoney * orderDetail.getQuantity();
        tableOrderDetails.addCell(createCellRowSpan(String.valueOf(String.format("%.2f", rateMoney)), keyfont, Element.ALIGN_CENTER, rowspan));
        tableOrderDetails.addCell(createCellRowSpan(String.valueOf(String.format("%.2f", orderDetail.getaTPrice())), keyfont, Element.ALIGN_CENTER, rowspan));
        tableOrderDetails.addCell(createCellRowSpan(String.valueOf(String.format("%.2f", orderDetail.getTotalPrice())), keyfont, Element.ALIGN_CENTER, rowspan));

        if(orderDetail.getManufacturerName() != null && !orderDetail.getManufacturerName().isEmpty()){ tableOrderDetails.addCell(createCell(orderDetail.getManufacturerName(), keyfont, Element.ALIGN_LEFT)); }
        if(orderDetail.getProductModel() != null && !orderDetail.getProductModel().isEmpty()){ tableOrderDetails.addCell(createCell(orderDetail.getProductModel(), keyfont, Element.ALIGN_LEFT)); }

        return totalRateMoney;
    }

    private void addRows(PdfPTable tableOrderDetails, int rows){
        for (int i = 0; i <rows ; i++) {
            for (int j = 0; j <10 ; j++) {
                tableOrderDetails.addCell(createCell(" ", keyfont));
            }
        }
    }

    /**
     * 创建PayPlan单元格
     * @param tablePayPlan
     * @param index 下标
     * @param payPlan
     */
    private void addPayPlanTableCell(PdfPTable tablePayPlan, int index, PayPlan payPlan){
        addOrderPayPlan(tablePayPlan);
        tablePayPlan.addCell(createCell(String.valueOf(index), keyfont, Element.ALIGN_CENTER));
        tablePayPlan.addCell(createCell(payPlan.getDescribe(), keyfont, Element.ALIGN_LEFT));
        Date planPlanDate = payPlan.getPlanDate();
        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
        String date = f.format(planPlanDate);
        tablePayPlan.addCell(createCell(date, keyfont, Element.ALIGN_CENTER));
        tablePayPlan.addCell(createCell(String.valueOf(payPlan.getPayRatio()), keyfont, Element.ALIGN_CENTER));
        tablePayPlan.addCell(createCell(String.valueOf(String.format("%.2f", payPlan.getPayMoney())), keyfont, Element.ALIGN_LEFT));
    }

    private void addTableCell(PdfPTable pdfPTable, Object key, Object value){
        pdfPTable.addCell(createCellColor(String.valueOf(key), keyfont, Element.ALIGN_LEFT));
        pdfPTable.addCell(createCell(String.valueOf(value), keyfont, Element.ALIGN_LEFT));
    }

    private void addTableCell(PdfPTable pdfPTable, Object key, Object value, int colspan){
        pdfPTable.addCell(createCell(String.valueOf(key), keyfont, Element.ALIGN_LEFT));
        pdfPTable.addCell(createCell(String.valueOf(value), keyfont, Element.ALIGN_LEFT, colspan));
    }

    /**
     * 给指定table创建单元格
     * @param pdfPTable
     * @param value
     * @param colspan 跨列
     */
    private void addTableCell(PdfPTable pdfPTable, Object value, int colspan){
        pdfPTable.addCell(createCell(String.valueOf(value), keyfont, Element.ALIGN_LEFT, colspan));
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
        cell.setMinimumHeight(10);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    private PdfPCell createCellRowSpan(String value, Font font, int align, int rowspan) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setMinimumHeight(10);
        cell.setRowspan(rowspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    private PdfPCell createCellColor(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new BaseColor(100,100,100,100));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setMinimumHeight(10);
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
        cell.setMinimumHeight(20);
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
