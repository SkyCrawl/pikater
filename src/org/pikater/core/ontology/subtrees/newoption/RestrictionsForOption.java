package org.pikater.core.ontology.subtrees.newoption;

import jade.content.Concept;

import java.util.ArrayList;
import java.util.List;

import org.pikater.core.ontology.subtrees.newoption.base.IValidated;
import org.pikater.core.ontology.subtrees.newoption.restrictions.TypeRestriction;
import org.pikater.shared.util.ICloneable;
import org.pikater.shared.util.collections.CollectionUtils;

public class RestrictionsForOption implements Concept, ICloneable, IValidated {
	private static final long serialVersionUID = 236577012228824852L;

	private List<TypeRestriction> restrictions;

	/**
	 * Should only be used internally and by JADE.
	 */
	@Deprecated
	public RestrictionsForOption() {
		this.restrictions = new ArrayList<TypeRestriction>();
	}

	public RestrictionsForOption(List<TypeRestriction> restrictions) {
		this();
		this.restrictions.addAll(restrictions);
	}

	public List<TypeRestriction> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<TypeRestriction> restrictions) {
		this.restrictions = restrictions;
	}

	/*
	 * Some convenience interface.
	 */
	public TypeRestriction fetchByIndex(int index) {
		return restrictions.get(index);
	}

	public void add(TypeRestriction restriction) {
		this.restrictions.add(restriction);
	}

	public int size() {
		return restrictions.size();
	}

	/*
	 * Inherited interface.
	 */
	@Override
	public RestrictionsForOption clone() {
		RestrictionsForOption result;
		try {
			result = (RestrictionsForOption) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		result.setRestrictions(CollectionUtils.deepCopy(restrictions));
		return result;
	}

	@Override
	public boolean isValid() {
		for (TypeRestriction restriction : restrictions) {
			if (!restriction.isValid()) {
				return false;
			}
		}
		return true;
	}
}