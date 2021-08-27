package com.example.demo2;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.mks.mqtt.config.BaseController;
import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

//    public static void main(String[] args) {
//
//
//        // 数据源配置
//        DataSourceConfig dsc = new  DataSourceConfig.Builder("jdbc:mysql://192.168.3.137:3306/mks_mqtt?useUnicode=true&useSSL=false&characterEncoding=utf8",
//                "test",
//                "mks123456").build();
//        // 代码生成器
//        AutoGenerator mpg = new AutoGenerator(dsc);
//
////        // 全局配置
//        GlobalConfig globalConfig = GeneratorBuilder.globalConfigBuilder()
//                .fileOverride().openDir(true).enableKotlin().enableSwagger()
//                .outputDir("/opt/baomidou")
//                .author("baomidou").dateType(DateType.TIME_PACK).commentDate("yyyy-MM-dd")
//                .build();
//        mpg.global(globalConfig);
//
//
//        // 包配置
//        PackageConfig pc = new PackageConfig.Builder().parent("com.example").moduleName("demo2").build();
//        mpg.packageInfo(pc);
//
//        // 自定义配置
////        InjectionConfig cfg = new InjectionConfig() {
////        };
//
//
//        // 配置模板
////        TemplateConfig tempcfg = new TemplateConfig.Builder().disable(TemplateType.ENTITY).disable(TemplateType.CONTROLLER).disable(TemplateType.XML).build();
////        mpg.template(tempcfg);
//
//        // 策略配置
//       StrategyConfig strategy = new StrategyConfig.Builder()
//                .enableCapitalMode()// 启用sql过滤
//                .enableCapitalMode()// 是否大写命名
//                .entityBuilder()// 实体配置构建者
//                .enableLombok()// 开启lombok模型
//                .versionColumnName("version") //乐观锁数据库表字段
//                .naming(NamingStrategy.underline_to_camel)// 数据库表映射到实体的命名策略
//                .addTableFills(new Column("create_time", FieldFill.INSERT))    //基于数据库字段填充
//                .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))    //基于模型属性填充
//                .controllerBuilder() //控制器属性配置构建
//                .enableRestStyle()
//                .build();
//       mpg.strategy(strategy);
//       mpg.execute();
//    }
    private static final String REPLACE_LOCAL_PATH = "/target/test-classes/";
    private static final String JAVA_PATH = "/src/main/java";
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();

        // 获取项目路径
        String projectPath = ClassLoader.getSystemResource("").getPath().replace(REPLACE_LOCAL_PATH, "");


        gc.setOutputDir(projectPath + JAVA_PATH);
        gc.setAuthor("mybatis-plus-generator:3.4.1");
        gc.setOpen(false);
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        gc.setFileOverride(true); //覆盖
        gc.setBaseResultMap(false);
        gc.setEntityName("%sEntity");
        gc.setEnableCache(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.3.137:3306/mks_mqtt?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("test");
        dsc.setPassword("mks123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName("xxxssss");
        pc.setParent("com.mks.mqtt");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                StringBuilder sb = new StringBuilder();
                sb.append(projectPath);
                sb.append("/src/main/resources/mapper/");
                if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(pc.getModuleName())) {
                    sb.append(pc.getModuleName());
                    sb.append("/");
                }

                sb.append(tableInfo.getEntityName());
                sb.append("Mapper");
                sb.append(StringPool.DOT_XML);

                return sb.toString();
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

//        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setVersionFieldName("version");
        strategy.setLogicDeleteFieldName("isDelete");
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);

        // 公共父类
        strategy.setSuperControllerClass(BaseController.class.getName());


        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");

        // 设置排除表
//        if (CollectionUtils.isNotEmpty(Arrays.asList(INCLUDE_TABLE))) {
//            strategy.setInclude(INCLUDE_TABLE);
//        }
//        if (CollectionUtils.isNotEmpty(Arrays.asList(EXCLUDE_TABLE))) {
//            strategy.setExclude(INCLUDE_TABLE);
//        }

        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();
    }
}
