package com.lky.store.utils;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BeanFactory {

	public static Object createObject(String name) {
		// 通过name获取application.xml中获取name对应的class值
		SAXReader reader = new SAXReader();
		// 位于src下才可以获取
		InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");
		try {
			// 获取到document对象、根节点beans、子节点bean
			Document document = reader.read(is);
			Element rootElement = document.getRootElement();
			List<Element> elements = rootElement.elements();
			for (Element e : elements) {
				String id = e.attributeValue("id");
				if (id.equals(name)) {
					String str = e.attributeValue("class");
					// 通过class反射
					Class clazz = Class.forName(str);
					return clazz.newInstance();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
