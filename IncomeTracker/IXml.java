package IncomeTracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import IncomeTracker.Taxes.taxRate;

interface IXml {

//	for payees list
	static void marshal(Path source, List<Paid> payees) {
		ListPaid listpaid = new ListPaid();
		listpaid.setListPaid(payees);
		try {
			JAXBContext context = JAXBContext.newInstance(ListPaid.class);
			Marshaller marshal = context.createMarshaller();
			marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshal.marshal(listpaid, System.out);
			marshal.marshal(listpaid, source.toFile());
		} catch (DataBindingException f) {
			System.out.println("IXml.marshal(): failed marshal\n" +f.getMessage());
		} catch (JAXBException g) {
			System.out.println("IXml.marshal(): failed marshal\n" +g.getMessage());
			g.printStackTrace();
		}
	}
//	for tax rate
	static void marshal(Path source, double rate) {
		Taxes.taxRate taxRate = new Taxes.taxRate();
		taxRate.setXmlTaxRate(rate);
		try {
			JAXBContext context = JAXBContext.newInstance(taxRate.class);
			Marshaller marshal = context.createMarshaller();
			marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshal.marshal(taxRate, System.out);
			marshal.marshal(taxRate, source.toFile());
		} catch (DataBindingException f) {
			System.out.println("IXml.marshal(): failed marshal\n" +f.getMessage());
		} catch (JAXBException g) {
			System.out.println("IXml.marshal(): failed marshal\n" +g.getMessage());
			g.printStackTrace();
		}
	}
//	for payees list
	static List<Paid> unmarshal(List<Paid> payees, Path source) {
		try {
			JAXBContext context = JAXBContext.newInstance(ListPaid.class);
			Unmarshaller unmarshal = context.createUnmarshaller();
			ListPaid listpaid = (ListPaid) unmarshal.unmarshal(source.toFile());
			payees = listpaid.getListPaid();
		} catch (DataBindingException e) {
			System.out.println("IXml.unmarshal():\n"+e.getMessage());
		} catch (JAXBException f) {
			System.out.println("IXml.unmarshal():\n"+f.getMessage());
		}
		return payees;
	}
//	for tax rate
	static double unmarshal(double rate, Path source) {
		try {
			JAXBContext context = JAXBContext.newInstance(taxRate.class);
			Unmarshaller unmarshal = context.createUnmarshaller();
			Taxes.taxRate taxRate = (taxRate) unmarshal.unmarshal(source.toFile());
			rate = taxRate.getXmlTaxRate();
		} catch (DataBindingException e) {
			System.out.println("IXml.unmarshal():\n"+e.getMessage());
		} catch (JAXBException f) {
			System.out.println("IXml.unmarshal():\n"+f.getMessage());
		}
		return rate;
	}
	
	static void createXmlFile(Path source) {	
		try {
			Files.createFile(source);
			System.out.println("No payees exist yet.");
		} catch (IOException e) {
			e.getMessage();
			System.out.println("IXml.createXmlFile()");
		} catch (UnsupportedOperationException f) {
			f.getMessage();
			System.out.println("IXml.createXmlFile()");
		} catch (Exception g) {
			g.getMessage();
			System.out.println("IXml.createXmlFile()");
		}
	}
}
