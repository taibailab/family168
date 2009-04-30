/**
 *
 */
package com.family168.jbpm;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jbpm.pvm.internal.type.Converter;
import org.jbpm.pvm.internal.type.Matcher;
import org.jbpm.pvm.internal.type.Type;
import org.jbpm.pvm.internal.type.TypeMapping;
import org.jbpm.pvm.internal.type.matcher.ClassNameMatcher;
import org.jbpm.pvm.internal.type.matcher.HibernateLongIdMatcher;
import org.jbpm.pvm.internal.type.matcher.HibernateStringIdMatcher;
import org.jbpm.pvm.internal.type.matcher.SerializableMatcher;
import org.jbpm.pvm.internal.util.ReflectUtil;
import org.jbpm.pvm.internal.util.XmlUtil;
import org.jbpm.pvm.internal.xml.Parse;
import org.jbpm.pvm.internal.xml.Parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;


/**
 *
 * 类型转换
 *
 */
public class VariableTypeSet extends org.jbpm.pvm.internal.type.DefaultTypeSet {
    private static final long serialVersionUID = 0L;
    private String typeConfig;

    public void init() {
        Parser parser = new Parser();
        Parse parse = parser.createParse();
        InputStream is = VariableTypeSet.class.getClassLoader()
                                              .getResourceAsStream(typeConfig);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
            .newInstance();

        try {
            Document document = documentBuilderFactory.newDocumentBuilder()
                                                      .parse(is);
            Element element = document.getDocumentElement();

            parse(element, parse, parser);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void parse(Element element, Parse parse, Parser parser) {
        List<Element> typeElements = XmlUtil.elements(element, "type");

        for (Element typeElement : typeElements) {
            TypeMapping typeMapping = parseTypeMapping(typeElement, parse,
                    parser);
            addTypeMapping(typeMapping);
        }
    }

    protected TypeMapping parseTypeMapping(Element element, Parse parse,
        Parser parser) {
        TypeMapping typeMapping = new TypeMapping();
        Type type = new Type();
        typeMapping.setType(type);

        // type name
        if (element.hasAttribute("name")) {
            type.setName(element.getAttribute("name"));
        }

        String hibernateSessionFactoryName = XmlUtil.attribute(element,
                "hibernate-session-factory");

        // first we get the matcher
        Matcher matcher = null;

        if (element.hasAttribute("class")) {
            String className = element.getAttribute("class");

            // if type="serializable"
            if ("serializable".equals(className)) {
                matcher = new SerializableMatcher();

                // if type="persistable"
            } else if ("persistable".equals(className)) {
                if (element.hasAttribute("id-type")) {
                    String idType = element.getAttribute("id-type");

                    if ("long".equalsIgnoreCase(idType)) {
                        matcher = new HibernateLongIdMatcher(hibernateSessionFactoryName);
                    } else if ("string".equalsIgnoreCase(idType)) {
                        matcher = new HibernateStringIdMatcher(hibernateSessionFactoryName);
                    } else {
                        parse.addProblem(
                            "id-type was not 'long' or 'string': "
                            + idType);
                    }
                } else {
                    parse.addProblem(
                        "id-type is required in a persistable type");
                }

                // otherwise, we expect type="some.java.ClassName"
            } else {
                matcher = new ClassNameMatcher(className);
            }
        } else {
            // look for the matcher element
            Element matcherElement = XmlUtil.element(element, "matcher");
            Element matcherObjectElement = XmlUtil.element(matcherElement);

            if (matcherObjectElement != null) {
                try {
                    matcher = (Matcher) parser.parseElement(matcherObjectElement,
                            parse);
                } catch (ClassCastException e) {
                    parse.addProblem("matcher is not a "
                        + Matcher.class.getName() + ": "
                        + ((matcher != null)
                        ? matcher.getClass().getName() : "null"));
                }
            } else {
                parse.addProblem("no matcher specified in "
                    + XmlUtil.toString(element));
            }
        }

        typeMapping.setMatcher(matcher);

        // parsing the converter
        Converter converter = null;

        if (element.hasAttribute("converter")) {
            String converterClassName = element.getAttribute("converter");
            ClassLoader classLoader = parse.getClassLoader();

            try {
                Class<?> converterClass = ReflectUtil.loadClass(classLoader,
                        converterClassName);
                converter = (Converter) converterClass.newInstance();
            } catch (Exception e) {
                parse.addProblem("couldn't instantiate converter "
                    + converterClassName);
            }
        } else {
            // look for the matcher element
            Element converterElement = XmlUtil.element(element, "converter");
            Element converterObjectElement = XmlUtil.element(converterElement);

            if (converterObjectElement != null) {
                try {
                    converter = (Converter) parser.parseElement(converterObjectElement,
                            parse);
                } catch (ClassCastException e) {
                    parse.addProblem("converter is not a "
                        + Converter.class.getName() + ": "
                        + ((converter != null)
                        ? converter.getClass().getName() : "null"));
                }
            }
        }

        type.setConverter(converter);

        // parsing the variable class
        Class<?> variableClass = null;

        if (element.hasAttribute("variable-class")) {
            String variableClassName = element.getAttribute(
                    "variable-class");
            ClassLoader classLoader = parse.getClassLoader();

            try {
                variableClass = ReflectUtil.loadClass(classLoader,
                        variableClassName);
            } catch (Exception e) {
                parse.addProblem("couldn't instantiate variable-class "
                    + variableClassName, e);
            }
        } else {
            parse.addProblem("variable-class is required on a type: "
                + XmlUtil.toString(element));
        }

        type.setVariableClass(variableClass);

        return typeMapping;
    }

    public String getTypeConfig() {
        return typeConfig;
    }

    public void setTypeConfig(String typeConfig) {
        this.typeConfig = typeConfig;
    }
}
