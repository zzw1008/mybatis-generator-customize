# mybatis-generator-customize
编写mybatisgenerator插件，对生成的文件进行个性化修改

启动类StartUp
原作者提供的插件：mybatis-generator-customize/src/main/java/com/zzw/plugin/refer/**

自己实现的：
1.entity类日期类型使用LocalDate Jdk8DateApiPlugin
2.entity类使用lombok注解 LombokPlugin
3.将mapper修改为dao MapperToDaoPlugin
4.entity类field注释使用table列的注释 TableCommentPlugin
