package com.zzw.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Description:
 * 使用jdk8的localDate替代date
 * @Author zzw
 * @Date: 19-3-5 上午8:27
 */
public class Jdk8DateApiPlugin extends PluginAdapter {


    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        generateToString(introspectedTable, topLevelClass);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        generateToString(introspectedTable, topLevelClass);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        generateToString(introspectedTable, topLevelClass);
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
    }


    private void generateToString(IntrospectedTable introspectedTable,
                                  TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> fullyQualifiedJavaTypes = topLevelClass.getImportedTypes();
        FullyQualifiedJavaType oldFullyQualifiedJavaType = new FullyQualifiedJavaType("java.util.Date");
        if(fullyQualifiedJavaTypes.contains(oldFullyQualifiedJavaType)){
            fullyQualifiedJavaTypes.remove(oldFullyQualifiedJavaType);
            FullyQualifiedJavaType newFullyQualifiedJavaType = new FullyQualifiedJavaType("java.time.LocalDate");
            fullyQualifiedJavaTypes.add(newFullyQualifiedJavaType);
            List<Field> fieldList = topLevelClass.getFields();
            fieldList.stream().forEach(field -> {
                if(field.getType().equals(oldFullyQualifiedJavaType)){
                    field.setType(newFullyQualifiedJavaType);
                }
            });
        }

    }
}
