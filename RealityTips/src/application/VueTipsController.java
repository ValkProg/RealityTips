package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VueTipsController {
	
	 @FXML private TextField textBill;
	 @FXML private TextField textTips;
	 @FXML private TextField textPerson;
	 
	 @FXML private TextField tipPerPerson;
	 @FXML private TextField totalPerPerson;
	 
	 @FXML private DatePicker datePayment;
	 @FXML private Label errorLabel;
	 
	 
	 public void calculate() {
		
		try
		{
			refresh();
			
			 String date = checkDate(datePayment.getValue());
			 float bill = checkNumber(textBill.getText());
			 float percentage = checkNumber(textTips.getText());
			 float nbPerson = checkNumber(textPerson.getText());
			 
			 if (nbPerson == 0) throw new Exception("Nombre de personne à 0");
			 
			 float ResultTipsPerPerson = (bill * (percentage/100)) / nbPerson;

			 tipPerPerson.setText(Float.toString(ResultTipsPerPerson));
			 totalPerPerson.setText(Float.toString((bill / nbPerson) + ResultTipsPerPerson));

			 String textFormatted = date.toString() + " ;" + bill + " ;" + ResultTipsPerPerson + " ;" + nbPerson;
	
			 findDateInFile(date);
			 writeToFile(textFormatted); 
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			errorLabel.setText(e.getMessage());
		}
		  
	 }

	private void refresh() {
		errorLabel.setText("");
	}

	public void searchValue() throws Exception {
		try 
		{
			 Scanner fileScanner = new Scanner(new File("random.txt"));
			 String date = checkDate(this.datePayment.getValue());
			 
			 while (fileScanner.hasNextLine()) {
				 String line = fileScanner.nextLine();
				 if(line.contains(date)) {
					String[] values = line.split(" ;");
					this.textBill.setText(values[1]);
					this.textTips.setText(values[2]);
					this.textPerson.setText(values[3]);
				 }
			 }
		 } 
		 catch (FileNotFoundException e) {
			 throw new FileNotFoundException(e.getMessage());
		 }
	}
	 
	private boolean findDateInFile(String date) throws IOException {
		 try 
		 {
			 Scanner fileScanner = new Scanner(new File("random.txt"));
			 String lines = "";
			 boolean isFind = false;
			 while (fileScanner.hasNextLine()) {
				 String line = fileScanner.nextLine();
				 if(line.contains(date)) {
					 isFind = true;
				 }
				 else {
					 lines += line+"\n";
				 }
		     }
			 
			 if(isFind) {
				 FileWriter writer = new FileWriter("random.txt");
				 writer.write(lines);
				 writer.close();
			 }
			 return isFind;
		 } 
		 catch (FileNotFoundException e) {
			 throw new FileNotFoundException(e.getMessage());
		 }
	}

	private void writeToFile(String text) {
		 try 
		 {
			 FileWriter writer = new FileWriter("random.txt", true);
			 writer.write(text);
			 writer.write("\n");
			 writer.close();
		 } catch (FileNotFoundException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	}

	private String checkDate(LocalDate date) throws Exception {
		 try {
			 //return new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 Date d = sdf.parse(date.toString());
			 sdf.applyPattern("dd/MM/yyyy");
			 return sdf.format(d);
		 }
		 catch (Exception e) {
			 //throw new Exception(e.getMessage());
			 throw new Exception("La date n'est pas valide");
		 }
	 }
	 
	 private Float checkNumber(String field) throws Exception{
		 try {
			 if(Float.parseFloat(field) < 0) throw new Exception("Nombre négatif !");
			 return Float.parseFloat(field);
		 }
		 catch (Exception e) {
			 //throw new Exception(e.getMessage());
			 throw new Exception("Un TextField ne contient pas de valeur numérique");
		 }
	 }

}
