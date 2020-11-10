package com.lys.kit.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class InvokeHelper
{
	public static Class getClass(String className)
	{
		try
		{
			return Class.forName(className);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static Object newInstance(String className)
	{
		return newInstance(getClass(className));
	}

	public static Object newInstance(Class cls)
	{
		try
		{
			Object obj = cls.newInstance();
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static Object newInstance(String className, Class[] pareTyple, Object[] pareVaules)
	{
		return newInstance(getClass(className), pareTyple, pareVaules);
	}

	public static Object newInstance(Class cls, Class[] pareTyple, Object[] pareVaules)
	{
		try
		{
			Constructor cons = cls.getConstructor(pareTyple);
			Object obj = cons.newInstance(pareVaules);
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static Object getField(String className, Object obj, String fieldName)
	{
		return getField(getClass(className), obj, fieldName);
	}

	public static Object getField(Class cls, Object obj, String fieldName)
	{
		try
		{
			// LOG.v(String.format("get className = %s, fieldName = %s", className, fieldName));
			Field field = cls.getDeclaredField(fieldName);
			field.setAccessible(true); // 绕过权限检测
			Object ret = field.get(obj);
			// LOG.v("ret = " + ret);
			return ret;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static Object getStaticField(String className, String fieldName)
	{
		return getField(className, null, fieldName);
	}

	public static Object getStaticField(Class cls, String fieldName)
	{
		return getField(cls, null, fieldName);
	}

	public static Object invokeMethod(String className, Object obj, String methodName, Class[] pareTyple, Object[] pareVaules)
	{
		return invokeMethod(getClass(className), obj, methodName, pareTyple, pareVaules);
	}

	public static Object invokeMethod(Class cls, Object obj, String methodName, Class[] pareTyple, Object[] pareVaules)
	{
		try
		{
			// LOG.v(String.format("call className = %s, methodName = %s", className, methodName));
			Method method = cls.getDeclaredMethod(methodName, pareTyple);
			method.setAccessible(true); // 绕过权限检测
			Object ret = method.invoke(obj, pareVaules);
			// LOG.v("ret = " + ret);
			return ret;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static Object invokeStaticMethod(String className, String methodName, Class[] pareTyple, Object[] pareVaules)
	{
		return invokeMethod(className, null, methodName, pareTyple, pareVaules);
	}

	public static Object invokeStaticMethod(Class cls, String methodName, Class[] pareTyple, Object[] pareVaules)
	{
		return invokeMethod(cls, null, methodName, pareTyple, pareVaules);
	}
}
