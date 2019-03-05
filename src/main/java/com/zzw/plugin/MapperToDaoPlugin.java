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
import org.mybatis.generator.api.dom.java.*;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * mapper替换成dao
 * @author zzw
 */
public class MapperToDaoPlugin extends PluginAdapter {

    private boolean useToStringFromRoot;

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        useToStringFromRoot = isTrue(properties.getProperty("useToStringFromRoot"));
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
        String[] packageName = introspectedTable.getBaseRecordType().split("\\.");
        String entityName = packageName[packageName.length - 1];
        // 实体类生成路径
        introspectedTable.setBaseRecordType(introspectedTable.getBaseRecordType().replace(entityName,entityName.toLowerCase() + "." +entityName));
    }


    private void generateToString(IntrospectedTable introspectedTable,
            TopLevelClass topLevelClass) {
        String entityName = topLevelClass.getType().getShortName().toLowerCase();
        // xml文件名称 mapper文件名称
        introspectedTable.setMyBatis3XmlMapperFileName(introspectedTable.getMyBatis3XmlMapperFileName().replace("Mapper.xml","Dao.xml"));
        // mapper包名称 mapper文件路径
        introspectedTable.setMyBatis3XmlMapperPackage(introspectedTable.getMyBatis3XmlMapperPackage().replace("mapper","mapper." + entityName));
        // mapper全限定名称 dao接口路径
        introspectedTable.setMyBatis3JavaMapperType(introspectedTable.getMyBatis3JavaMapperType().replace("mapper","dao." + entityName).replace("Mapper","Dao"));
    }

}
