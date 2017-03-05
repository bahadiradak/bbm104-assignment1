/**
 * @author Bahadir
 * @since 2016-03-05
 */


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.Date;
import java.text.DecimalFormat;

public class Main {
	
	static ArrayList<ArrayList<String>> Medication=new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> Patient=new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> ListWithSS=new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> ListWithPationDrug=new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> ListWithPationDate=new ArrayList<ArrayList<String>>();
	static ArrayList<Date> StartDate=new ArrayList<>();
	static ArrayList<Date> EndDate=new ArrayList<>();
	static String PatientSocialSecurity;
	static String PrescriptionSocialSecurity;
	static String PatientDrug;
	static String PrescriptionDrug;
	static String PrescriptionDate;
	static String PrescriptionDrugFirst;
	static String Bigsize;
	
	String []data = new String[3];
	
	public static void main(String[] args) throws ParseException {
		if(args.length==2){
			readPrint(args[0]);
			readSplitPrint(args[1]);

		} 
		else
			System.out.println("It needs two arguments for running");
	}

	private static void readSplitPrint(String path) throws ParseException { 
		
		
		
		try {
			
			for (String line : Files.readAllLines(Paths.get(path))) {

					ArrayList<String> drugs = new ArrayList<String>(Arrays.asList(line.split("\t"))); 
					Medication.add(drugs);
					/** 
					 * we have to add all drugs in one list
					 * @param	Medication	means all drug name which is given medicaments.txt
					 */
					
			}

			
			for(int i=0;i<Medication.size();i++){
				PrescriptionSocialSecurity = Medication.get(i).get(1);
				/** 
				 * Every people has social security and every element's first index(second element) in Medication list is social security name
				 */
				if (PrescriptionSocialSecurity.equals(PatientSocialSecurity) ){
					/** 
					 * in this line it catch same name of patien's social security and social security in Medication list
					 */
					ListWithSS.add(Medication.get(i));
					/** 
					 * if they are same it add that new list which name is ListWithSS include same social security
					 */
				}

			}
		
			
			for(int i=1;i<Patient.size();i++){
				PatientDrug = Patient.get(i).get(0);
				/** 
				 * @param PatientDrug means every medicine in prescription.txt 
				 */
				for(int j=0;j<ListWithSS.size();j++){		
					PrescriptionDrug = ListWithSS.get(j).get(0);
					/** 
					 * @param PrescriptionDrug is every first element of ListWithSS's element
					 */
					if (PrescriptionDrug.equals(PatientDrug)){
						/** 
						 * in this line it check Patient's medicine same as Prescription's medicine which is include same social security
						 */
						ListWithPationDrug.add(ListWithSS.get(j));
						/** 
						 * if they are same add that new list name is ListWithPationDrug, in that list also has multiple same name medicine it will elimination later 
						 */
					}
				}

			}

			SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy");
			/** 
			 * in this line we define Date format 
			 */
			PrescriptionDate = Patient.get(0).get(2);
			/** 
			 * Patient's first element include information of patient that is name surname social security prescription date medicines and medicines quantity,we need to take third element
			 */ 
			Date prisdate = dt.parse(PrescriptionDate);
			/** 
			 * in that method we change string to date format @return Date
			 */
			for (int l=0;l<ListWithPationDrug.size();l++){
				Date stdate = dt.parse(ListWithPationDrug.get(l).get(2));
				StartDate.add(stdate);
				/**
				 * @param StartDate list of start of medicines time
				 */
				Date endate = dt.parse(ListWithPationDrug.get(l).get(3));
				EndDate.add(endate);
				/**
				 * @param EndDate list of end of medicines time
				 */
			}
			
			for (int u=0;u<StartDate.size();u++){
				if ( prisdate.compareTo(StartDate.get(u))>0 ){
					if(prisdate.compareTo(EndDate.get(u))<0){
						/**
						 * in this 2 lines we check medicine's time between StartDate and EndDate
						 */
						ListWithPationDate.add(ListWithPationDrug.get(u));
						/**
						 * if medicine's time is between StartDate and EndDate we add that ListWithPationDate list
						 */
					}
				}

			}
			
			ArrayList<Float> Price = new ArrayList<Float>();
			/**
			 * @param Price define a list it will add medicine's pries
			 */
			for(int h=0;h<Patient.size();h++){
				PatientDrug = Patient.get(h).get(0);
				ArrayList<String> Temporary = new ArrayList<String>();
				/** 
				 * @param Temporary a list it will add medicine's pries but it is change every loop so it is temporary
				 */
				for(int m=0;m<ListWithPationDate.size();m++){
					
					PrescriptionDrug = ListWithPationDate.get(m).get(0);
					/**
					 * @param PrescriptionDrug means medicine that is every element of ListWithPationDate ' first element
					 */
					PrescriptionDrugFirst = ListWithPationDate.get(m).get(4);
					/**
					 * @param PrescriptionDrugFirst means medicine's rate that is every element of ListWithPationDate ' fourth element
					 */
					
					if (PrescriptionDrug.equals(PatientDrug)){
						/**
						 * if PrescriptionDrug same as PatientDrug we add this Temporary list
						 */
						Temporary.add(PrescriptionDrugFirst);
					
					}					
				}
	
				if(Temporary.size()>1 ){
					Float number1 = Float.valueOf(Collections.min(Temporary));
					Price.add(number1);
				}
				else if(Temporary.size()==1){
					Float number2 = Float.valueOf(Collections.min(Temporary));
					Price.add(number2);
				}			
				/**
				 * if only print Temporary we will have some problem because some list member more than 1 some of them only 1 element in these line we arrest this
				 */
			}

			ArrayList<Integer> quantitiy = new ArrayList<Integer>();
			/**
			 * @param quantitiy a list it will add number of medicines that write in prescription.txt
			 */
			for(int d=1;d<Patient.size();d++){
				int rank1 = Integer.valueOf((Patient.get(d).get(1)));
				quantitiy.add(rank1);
			}
			
			ArrayList<Float> total = new ArrayList<Float>();
			/**
			 * @param total a list it will add amount of all acceptable medicines price
			 */
	        for (int x = 0; x<quantitiy.size();x++) {
	        	System.out.println((Patient.get(x+1).get(0))+"	"+Price.get(x)+"	"+quantitiy.get(x)+"	"+ Price.get(x)*quantitiy.get(x));
	        	Float total1 = Float.valueOf(Price.get(x)*quantitiy.get(x));
	        	total.add(total1);
	        }
			Float sum = (float) 0;
			for (Float d : total){
				sum+=d;
			}
			/**
			 * in this line we sum all acceptable medicine price
			 */
			DecimalFormat dateformatwith2 = new DecimalFormat("#,###,##0.00");
			/**
			 * @return dateformatwith2 change double format like 4.20 
			 */

			System.out.println("Total	" +dateformatwith2.format(sum));

		}	
		  catch (IOException e) { 
			e.printStackTrace();
		}		
	}
	
	private static void readPrint(String path) {
		try {
			
			for (String line : Files.readAllLines(Paths.get(path))) {
				ArrayList<String> info = new ArrayList<String>(Arrays.asList(line.split("\t")));
			    Patient.add(info);	  
			}
			PatientSocialSecurity = Patient.get(0).get(1);/** PatientSocialSecurity which is given in prescription.txt*/
	
			
		} catch (IOException e) { 
			e.printStackTrace();
		}						
		}
	
}

	
	






