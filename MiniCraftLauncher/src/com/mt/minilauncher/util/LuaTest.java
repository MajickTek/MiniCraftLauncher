package com.mt.minilauncher.util;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class LuaTest {
	public static void main(String[] args) {
		ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine e = sem.getEngineByName("luaj");
        ScriptEngineFactory f = e.getFactory();
        
        try {
			e.eval("print('hi')");
		} catch (ScriptException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
