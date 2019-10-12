package BloodDonation;

import java.util.Vector;

public class BloodGroupMatrix {
	
	static int[][] blood_matrix  = new int[][]
    {
        {1, 1, 1, 1, 1, 1, 1, 1}, 
        {0, 1, 0, 1, 0, 1, 0, 1}, 
        {0, 0, 1, 1, 0, 0, 1, 1}, 
        {0, 0, 0, 1, 0, 0, 0, 1}, 
        {0, 0, 0, 0, 1, 1, 1, 1}, 
        {0, 0, 0, 0, 0, 1, 0, 1}, 
        {0, 0, 0, 0, 0, 0, 1, 1}, 
        {0, 0, 0, 0, 0, 0, 0, 1}
    };
    
    static String[] blood_groups = { "AB+" , "AB-" , "A+" , "A-" , "B+" , "B-" , "O+" , "O-" };
    
    private static int bloodGroupIndex(String bg) {
		if(bg.equals("AB+"))
			return 0;
		if(bg.equals("AB-"))
			return 1;
		if(bg.equals("A+"))
			return 2;
		if(bg.equals("A-"))
			return 3;
		if(bg.equals("B+"))
			return 4;
		if(bg.equals("B-"))
			return 5;
		if(bg.equals("O+"))
			return 6;
		if(bg.equals("O-"))
			return 7;
    	return -1;
    }
	
    public static void displayBloodMatrix() {
    	for(int i=0;i<8;i++)
    		System.out.print("\t"+blood_groups[i]);
		System.out.print("\n");
    	for(int i=0;i<8;i++) {
    		System.out.print(blood_groups[i]);
    		for(int j=0;j<8;j++) {
    			System.out.print("\t"+blood_matrix[i][j]);
    		}
    		System.out.print("\n");
    	}
    }
    
    public static Vector<String> getCompatibleBloodGroups(String patient) {
    	int patient_index = bloodGroupIndex(patient);
    	Vector<String> donor_blood_groups = new Vector<String>();
    	for(int i=0;i<8;i++){
    		if(blood_matrix[patient_index][i]==1)
    			donor_blood_groups.add(blood_groups[i]);
    	}
    	return donor_blood_groups;
    }
    
	public static void main(String args[]) {
		//displayBloodMatrix();
		
		for( String blood_group : blood_groups ) {
			System.out.print(blood_group+"\t-\t");
			Vector<String> donor_blood_groups = getCompatibleBloodGroups(blood_group);
			for( String donor : donor_blood_groups ) {
				System.out.print(donor+"\t");
			}
			System.out.print("\n");
		}
	}

}
