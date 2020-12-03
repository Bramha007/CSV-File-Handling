package com.main;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.LTI.College;


public class MainFile {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		
		do {

			System.out.println("1. Register New College");
			System.out.println("2. Colleges that teach engineering in Mumbai");
			System.out.println("3. Enter the college id of the College to be removed");
			System.out.println("4. Exit Application");
			System.out.print("Enter your choice : ");
			choice = sc.nextInt();
			sc.nextLine();
			switch(choice) {
				case 1 : 
					System.out.println("Enter the College Id : ");
					int collegeId = sc.nextInt();
					sc.nextLine();
					System.out.println("Enter the College Name : ");
					String collegeName = sc.nextLine();
					System.out.println("Enter Stream : ");
					String stream = sc.nextLine();
					System.out.println("Enter College city :");
					String city = sc.nextLine();
					System.out.println("Enter College's fees :");
					float fees = sc.nextFloat();
					System.out.println("Enter the College city pincode :");
					int pin = sc.nextInt();
					System.out.println("Executing");

					College clg = new College();
					clg.setCollegeId(collegeId);
					clg.setCollegeName(collegeName);
					clg.setCoursetype(stream);
					clg.setFees(fees);
					clg.setCity(city);
					clg.setPinCode(pin);
					
					addToCSVFile(clg.getCollegeId(), clg.getCollegeName(), clg.getCoursetype(), clg.getFees(), clg.getCity(), clg.getPinCode());
					
					break;
					
				case 2:
					File myFile = new File("college.csv");
					Scanner myReader = new Scanner(myFile);
					int i = 1;
					while(myReader.hasNext()) {
						String[] data = myReader.nextLine().split(",");
						if(data.length == 0) {
							continue;
						}
						else {
							String _city = data[3];
							String _course = data[2];
							if(_city.trim().equals("Mumbai") && _course.trim().equals("Engineering")) {
								System.out.println(i +". "+ data[1].trim());
								i++;
							}
						}
					}
					break;
						
				case 3:
					System.out.println("Enter the collegID to be removed :");
					String delRec = sc.nextLine();
		
					Stream<String> streamF = Files.lines(Paths.get("college.csv"));
					List<String> op = streamF.filter(line -> !line.split(",")[0].trim().equals(delRec)).collect(Collectors.toList());
						
					File file = new File("college.csv");
					file.delete();
					File newfile = new File("college.csv");
					newfile.createNewFile();
					try {
						FileWriter fw = new FileWriter("college.csv");
						op.forEach(ele -> {
							try {
								fw.write(ele+'\n');
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
						fw.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
					break;
				
				case 4:
					System.out.println("EXITING");
					System.exit(0);

				default :
					break;
				
			}
		}while(choice != 4);

		sc.close();
	}
	
	
	public static void addToCSVFile(int id, String name, String course, float fees, String city, int pin) {
		try {
			FileWriter fw = new FileWriter("college.csv", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			pw.println(id+", "+name+", "+course+", "+city+", "+fees+", "+pin);
			pw.flush();
			pw.close();
			System.out.println("Record Saved");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Record not Saved");
		}
	}

}
