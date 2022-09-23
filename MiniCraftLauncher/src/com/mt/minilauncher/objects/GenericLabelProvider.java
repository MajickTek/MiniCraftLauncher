package com.mt.minilauncher.objects;

import org.eclipse.jface.viewers.LabelProvider;

public class GenericLabelProvider extends LabelProvider {

	private static GenericLabelProvider instance;
	
	public static GenericLabelProvider getInstance() {
		synchronized(GenericLabelProvider.class) {
			if (instance == null) {
				instance = new GenericLabelProvider();
			}
			return instance;
		}
	}
	
	@Override
	public String getText(Object element) {
		return element.toString();
	}
	
	
}
