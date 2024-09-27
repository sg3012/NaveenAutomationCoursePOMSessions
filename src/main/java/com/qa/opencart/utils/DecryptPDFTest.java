package com.qa.opencart.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

public class DecryptPDFTest {

	public static void main(String[] args) {
		System.out.println("decrypting pdf file......");

//		// USING PDFBox:
		try {
			File file = new File("C:\\FixedPDFs\\dubai_xref_fixed.pdf");
            PDDocument document = Loader.loadPDF(file);
            document.setAllSecurityToBeRemoved(true);
            document.save("C:\\Users\\sgupta12\\Eclipse_Workspace\\Practice"
            		+ "\\NaveenAutomatioCoursePOMSessions\\src"
            		+ "\\test\\resources\\testpdfdecrypt\\dubaitest.pdf");
            document.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// USING iTEXT:
//		try {
//			PdfReader reader = new PdfReader("C:\\FixedPDFs\\dubai_xref_fixed.pdf");
//			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("C:\\Users"
//					+ "\\sgupta12\\Eclipse_Workspace"
//					+ "\\Practice\\NaveenAutomatioCoursePOMSessions"
//					+ "\\src\\test\\resources\\testpdfdecrypt\\Decrypted.pdf"));
//			stamper.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}

}
