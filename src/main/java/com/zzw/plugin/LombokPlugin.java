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

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * 实体类家lombok注解 考虑改成配置文件形式
 * @Author zzw
 */
public class LombokPlugin extends PluginAdapter {

    private boolean useToStringFromRoot;

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
        generateAnnotation(introspectedTable, topLevelClass);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        generateAnnotation(introspectedTable, topLevelClass);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        generateAnnotation(introspectedTable, topLevelClass);
        return true;
    }

    private void generateAnnotation(IntrospectedTable introspectedTable,
            TopLevelClass topLevelClass) {
        // 类注解
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addAnnotation("@Accessors(chain = true)");
        // 清除实体类get与set方法
        topLevelClass.getMethods().clear();
        // import
        Set<FullyQualifiedJavaType> fullyQualifiedJavaTypes = topLevelClass.getImportedTypes();
        FullyQualifiedJavaType fullyQualifiedJavaType = new FullyQualifiedJavaType("lombok.Data");
        fullyQualifiedJavaTypes.add(fullyQualifiedJavaType);
        fullyQualifiedJavaType = new FullyQualifiedJavaType("lombok.experimental.Accessors");
        fullyQualifiedJavaTypes.add(fullyQualifiedJavaType);
    }

}
