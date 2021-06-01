package cn.fetosoft.chat.test.mybatis;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.Properties;

/**
 * @author guobingbing
 * @create 2018/4/1 9:29
 */
public class MybatisGeneratorCommon implements CommentGenerator {

	@Override
	public void addConfigurationProperties(Properties properties) {

	}

	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
		//判断数据库中该字段注释是否为空
		if(StringUtils.isEmpty(introspectedColumn.getRemarks()))
			return;
		field.addJavaDocLine("/**\n     * "+introspectedColumn.getRemarks()+"\n     */");
	}

	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {

	}

	@Override
	public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

	}

	@Override
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

	}

	@Override
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {

	}

	@Override
	public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

	}

	@Override
	public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
		if(StringUtils.isEmpty(introspectedColumn.getRemarks()))
			return;
		method.addJavaDocLine("/**\n     * 获取"+introspectedColumn.getRemarks()+"\n     */");
	}

	@Override
	public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
		if(StringUtils.isEmpty(introspectedColumn.getRemarks()))
			return;
		method.addJavaDocLine("/**\n     * 设置"+introspectedColumn.getRemarks()+"\n     */");
	}

	@Override
	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {

	}

	@Override
	public void addJavaFileComment(CompilationUnit compilationUnit) {

	}

	@Override
	public void addComment(XmlElement xmlElement) {

	}

	@Override
	public void addRootComment(XmlElement xmlElement) {

	}
}
