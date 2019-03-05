/**
 *    Copyright 2006-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.zzw.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.IntrospectedTable.TargetRuntime;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * field使用table表的注释
 */
public class TableCommentPlugin extends PluginAdapter {


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
        //给实体类添加注释
        List<IntrospectedColumn> columnList = introspectedTable.getBaseColumns();
        List<Field> fieldList = topLevelClass.getFields();
        fieldList.stream().forEach(field -> {
            field.getJavaDocLines().clear();
            field.addJavaDocLine("/**");
            columnList.stream().forEach(column->{
                if(field.getName().equals(column.getJavaProperty())){
                    field.addJavaDocLine(" * " + column.getRemarks());
                }
            });
            field.addJavaDocLine(" */");

        });
    }

    /**
     * 接口文件修改
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {

        if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3) {
            // don't need to do this for MYBATIS3_DSQL as that runtime already adds this annotation
            interfaze.addImportedType(
                    new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
            interfaze.addAnnotation("@Mapper");
        }
        return true;
    }
}
