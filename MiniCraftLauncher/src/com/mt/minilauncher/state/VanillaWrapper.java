package com.mt.minilauncher.state;

public class VanillaWrapper implements IWrapper{

	@Override
	public void download() {
		System.out.println("downloading");
	}

	@Override
	public void launch() {
		System.out.println("launching");
	}

}
