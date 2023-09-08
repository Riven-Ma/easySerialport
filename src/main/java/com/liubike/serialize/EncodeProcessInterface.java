package com.liubike.serialize;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public interface EncodeProcessInterface {
	
	
	
	void encoddingFieldBefore(Class<?> clazz,Field field,Object value,FieldTag fieldTag,final LinkedList<Byte> bytes);
	
	void encoddingFieldAfter(Class<?> clazz,Field field,Object value,FieldTag fieldTag,final LinkedList<Byte> bytes);


	void encoddingTailBefore(final LinkedList<Byte> bytes);
	
}
