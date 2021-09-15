package com.gmall.mybatisplus_ext.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import lombok.experimental.Accessors;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;
import java.util.function.Predicate;


/**
 * 批量新增数据,自选字段 insert * My提供组件从SQL语法上来看，只支持Mysql Sql Service语法，重写代码兼容一下Oracle * * <p> * 自己的通用 mapper 如下使用: * <pre> * int insertBatchSomeColumn(List<T> entityList); * </pre> * </p> * * <li> 注意: 这是自选字段 insert !!,如果个别字段在 entity 里为 null 但是数据库中有配置默认值, insert 后数据库字段是为 null 而不是默认值 </li> * * <p> * 常用的 {@link Predicate}: * </p> * * <li> 例1: t -> !t.isLogicDelete() , 表示不要逻辑删除字段 </li> * <li> 例2: t -> !t.getProperty().equals("version") , 表示不要字段名为 version 的字段 </li> * <li> 例3: t -> t.getFieldFill() != FieldFill.UPDATE) , 表示不要填充策略为 UPDATE 的字段 </li> * * @author miemie * @since 2018-11-29
 */
public class CustomInsertBatchSomeColumn extends InsertBatchSomeColumn {


    /**
     * * 字段筛选条件
     */
    @Accessors(chain = true)
    private Predicate<TableFieldInfo> predicate;

    public CustomInsertBatchSomeColumn(Predicate<TableFieldInfo> predicate) {
        this.predicate = predicate;
    }

    public CustomInsertBatchSomeColumn() {
    }

    @SuppressWarnings("Duplicates")
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {

        // 这里我设置了一个全局属性，不从数据源拿了，省事

        // 你们可以通过@ConfigurationProperties 或者@Value 搞一哈

        KeyGenerator keyGenerator = new NoKeyGenerator();
        SqlMethod sqlMethod = SqlMethod.INSERT_ONE;
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        String insertSqlColumn = tableInfo.getKeyInsertSqlColumn(false) +
                this.filterTableFieldInfo(fieldList, predicate, TableFieldInfo::getInsertSqlColumn, EMPTY);
        String columnScript = LEFT_BRACKET + insertSqlColumn.substring(0, insertSqlColumn.length() - 1) + RIGHT_BRACKET;
        String insertSqlProperty = tableInfo.getKeyInsertSqlProperty(ENTITY_DOT, false) +
                this.filterTableFieldInfo(fieldList, predicate, i -> i.getInsertSqlProperty(ENTITY_DOT), EMPTY);
        insertSqlProperty = LEFT_BRACKET + insertSqlProperty.substring(0, insertSqlProperty.length() - 1) + RIGHT_BRACKET;

        //oracle 语法处理
        insertSqlProperty = insertSqlProperty;
        String valuesScript = this.convertForeach(insertSqlProperty, "list", null, ENTITY, null, COMMA, null);

        String keyProperty = null;
        String keyColumn = null;
        // 表包含主键处理逻辑,如果不包含主键当普通字段处理
        if (StringUtils.isNotBlank(tableInfo.getKeyProperty())) {
            if (tableInfo.getIdType() == IdType.AUTO) {
                /* 自增主键 */
                keyGenerator = new Jdbc3KeyGenerator();
                keyProperty = tableInfo.getKeyProperty();
                keyColumn = tableInfo.getKeyColumn();
            } else {
                if (null != tableInfo.getKeySequence()) {
                    keyGenerator = TableInfoHelper.genKeyGenerator(getMethod(sqlMethod), tableInfo, builderAssistant);
                    keyProperty = tableInfo.getKeyProperty();
                    keyColumn = tableInfo.getKeyColumn();
                }
            }
        }
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), columnScript, valuesScript);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, getMethod(sqlMethod), sqlSource, keyGenerator, keyProperty, keyColumn);
    }

    /**
     * <p> * 生成 foreach 标签的脚本 * </p> * * @param sqlScript foreach 内部的 sql 脚本 * @param collection collection * @param index index * @param open open * @param item item * @param separator separator * @param close close * @return foreach 脚本
     */
    private String convertForeach(final String sqlScript, final String collection, final String index,
                                  final String item, final String open, final String separator, final String close) {
        StringBuilder sb = new StringBuilder("<foreach");
        if (StringUtils.isNotBlank(collection)) {
            sb.append(" collection=\"").append(collection).append(QUOTE);
        }
        if (StringUtils.isNotBlank(index)) {
            sb.append(" index=\"").append(index).append(QUOTE);
        }
        if (StringUtils.isNotBlank(item)) {
            sb.append(" item=\"").append(item).append(QUOTE);
        }
        if (StringUtils.isNotBlank(open)) {
            sb.append(" open=\"").append(open).append(QUOTE);
        }
        if (StringUtils.isNotBlank(separator)) {
            sb.append(" separator=\"").append(separator).append(QUOTE);
        }
        if (StringUtils.isNotBlank(close)) {
            sb.append(" close=\"").append(close).append(QUOTE);
        }
        return sb.append(RIGHT_CHEV).append(NEWLINE).append(sqlScript).append(NEWLINE).append("</foreach>").toString();
    }

    @Override
    public String getMethod(SqlMethod sqlMethod) {
        // 自定义 mapper 方法名
        return "insertBatchSomeColumn";
    }


    public CustomInsertBatchSomeColumn setPredicate(Predicate<TableFieldInfo> predicate) {
        this.predicate = predicate;
        return this;
    }
}