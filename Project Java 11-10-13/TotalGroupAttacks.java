package dto;

import java.util.ArrayList;
import java.util.Collections;
import dto.AttackInOneYearComparator;

public class TotalGroupAttacks {
	
	private String groupName;
	private ArrayList<AttacksInOneYear> attacksDuringYears;
	
	public TotalGroupAttacks(String newGroup, ArrayList<AttacksInOneYear> newAttacksDuringYears)
	{
		this.groupName = newGroup;
		this.attacksDuringYears = newAttacksDuringYears;
	}
	
	
	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public ArrayList<AttacksInOneYear> getAttacksDuringYears() {
		return attacksDuringYears;
	}


	public void setAttacksDuringYears(ArrayList<AttacksInOneYear> attacksDuringYears) {
		this.attacksDuringYears = attacksDuringYears;
	}
	
	public void sortByYears() {
		
		Collections.sort(attacksDuringYears, new AttackInOneYearComparator());
		
	}
	

}
