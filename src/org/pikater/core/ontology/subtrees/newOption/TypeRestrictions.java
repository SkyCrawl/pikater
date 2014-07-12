package org.pikater.core.ontology.subtrees.newOption;

import jade.content.Concept;

import java.util.ArrayList;
import java.util.List;

import org.pikater.core.ontology.subtrees.newOption.restrictions.TypeRestriction;

public class TypeRestrictions implements Concept
{
	private static final long serialVersionUID = 236577012228824852L;
	
	private List<TypeRestriction> restrictions;
	
	public TypeRestrictions() {}
	public TypeRestrictions(List<TypeRestriction> possibleTypes) {
		this.restrictions = possibleTypes;
	}

	public List<TypeRestriction> getAll() {
		return restrictions;
	}
	public TypeRestriction getByIndex(int index)
	{
		return restrictions.get(index);
	}
	public void set(List<TypeRestriction> possibleTypes) {
		this.restrictions = possibleTypes;
	}
	public void add(TypeRestriction types) {

		if (this.restrictions == null) {
			this.restrictions = new ArrayList<TypeRestriction>();
		}

		this.restrictions.add(types);
	}
	public void add(ValueType type, int minCount, int maxCount) {
		
		if (type == null) {
			throw new IllegalArgumentException("Argument type is null");
		}

		if (minCount < 0 || maxCount <= 0 || minCount > maxCount) {
			throw new IllegalArgumentException("Arguments minCount and maxCount represents incorrect interval");
		}
		
		for (int typeCountI = minCount; typeCountI <= maxCount; typeCountI++) {
			
			List<ValueType> typeList = new ArrayList<ValueType>();
			for (int typeIndex = 1; typeIndex <= typeCountI; typeIndex++) {
				typeList.add(type);
			}
			add(new TypeRestriction(typeList) );
			
		}
	}
	
	public int size()
	{
		return restrictions.size();
	}
	
	/*
	// don't implement the IRestriction interface pointlessly
	public ValueType getType()
	{
		if (restrictions == null || restrictions.isEmpty())
		{
			return null;
		}
		else
		{
			ValueType type0 = restrictions.get(0).getTypes().get(0);
			for (TypeRestriction typesI : restrictions) {
				for (ValueType typeJ : typesI.getTypes()) {
	
					if (typeJ.equals(type0)) {
						return null;
					}
				}
			}
			return type0;
		}
	}
	*/

	@Override
	public TypeRestrictions clone()
	{
		return new TypeRestrictions(new ArrayList<TypeRestriction>(restrictions));
	}
}