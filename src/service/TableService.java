package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import join.Join;
import join.JoinCondition;
import lombok.extern.slf4j.Slf4j;
import model.Field;
import model.SingleFilter;
import model.Table;
import util.StringUtil;

/**
 * @Author: wangjiahao05 <wangjiahao05@.com>
 * Created on 2021-09-24
 */
@Slf4j
public class TableService {

    /**
     * 创建一个新的表文件
     *
     * @param tableName 表名
     * @param dbName
     * @return 如果表存在返回失败的信息，否则返回success
     */
    public static void createTable(String tableName, Map<String, Field> fields, String dbName) {
        if (existTable(tableName, dbName)) {
            log.error("创建表失败，因为已经存在表:{}",tableName);
            return ;
        }
        Table table = new Table(tableName);


        table.getDictFile().getParentFile().mkdirs();//创建真实目录

        table.addDict(fields);
        log.info("建表成功:{}",tableName);
    }
    /**
     * 根据表名获取表
     *
     * @param tableName 表名
     * @param dbName
     * @return 如果不存在此表返回null, 否则返回对应Table对象
     */
    public static Table getTable(String tableName, String dbName) {
        if (!existTable(tableName, dbName)) {
            log.error("不存在表:{}.{}",dbName,tableName);
            return null;
        }
        Table table = new Table(tableName);
        try (
                FileReader fr = new FileReader(table.getDictFile());
                BufferedReader br = new BufferedReader(fr)
        ) {

            String line = null;
            //读到末尾是NULL
            while (null != (line = br.readLine())) {
                String[] fieldValues = line.split(" ");//用空格产拆分字段
                Field field = new Field();
                field.setName(fieldValues[0]);
                field.setType(fieldValues[1]);
                //如果长度为3说明此字段是主键
                field.setPrimaryKey("*".equals(fieldValues[2]));
                //将字段的名字作为key
                table.getFieldMap().put(fieldValues[0], field);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        File[] dataFiles = new File(table.getFolder(), "data").listFiles();
        if (null != dataFiles && 0 != dataFiles.length) {
            for (int i = 1; i <= dataFiles.length; i++) {
                File dataFile = new File(table.getFolder() + "/data", i + ".data");
                table.getDataFileSet().add(dataFile);
            }
        }

        if (table.getIndexFile().exists()) {
            table.readIndex();
        } /*else {
            table.buildIndex();
            //table.writeIndex();
        }*/

        return table;
    }


    public static void dropTable(String dbName, String tableName) {
        if (!existTable(tableName, dbName)) {
            log.error("错误:不存在表:{}",tableName);
            return ;
        }
        File folder = new File("WySQL/"  + dbName + "/" + tableName);
        deleteFolder(folder);
        log.info("success");
    }
    private static void deleteFolder(File file) {
        if (file.isFile()) {//判断是否是文件
            file.delete();//删除文件
        } else if (file.isDirectory()) {//否则如果它是一个目录
            File[] files = file.listFiles();//声明目录下所有的文件 files[];
            for (int i = 0; i < files.length; i++) {//遍历目录下所有的文件
                deleteFolder(files[i]);//把每个文件用这个方法进行迭代
            }
            file.delete();//删除文件夹
        }
    }

    /**
     * 判断表是否存在
     *
     * @param name 表名
     * @param dbName
     * @return 存在与否
     */
    private static boolean existTable(String name, String dbName) {
        File folder = new File("WySQL/"  + dbName + "/" + name);
        return folder.exists();
    }

    public static void deleteIndex(String tableName, String currentDbName) {
        Table table = getTable(tableName, currentDbName);
        if(table!=null){ table.deleteIndex(); }
    }

    public static void select(String tableNames, String whereStr, String sentence, String currentDbName) {
        //将读到的所有数据放到tableDatasMap中
        Map<String, List<Map<String, String>>> tableDatasMap = new LinkedHashMap<>();

        //将投影放在Map<String,List<String>> projectionMap中
        Map<String, List<String>> projectionMap = new LinkedHashMap<>();

        List<String> tables = StringUtil.parseFrom(tableNames);

        //将tableName和table.fieldMap放入
        Map<String, Map<String, Field>> fieldMaps = new HashMap();

        for (String tableName : tables) {
            Table table = TableService.getTable(tableName, currentDbName);
            if (null == table) {
                log.info("未找到表：" + tableName);
                return;
            }
            Map<String, Field> fieldMap = table.getFieldMap();
            fieldMaps.put(tableName, fieldMap);

            //解析选择
            List<SingleFilter> singleFilters = new ArrayList<>();
            List<Map<String, String>> filtList = StringUtil.parseWhere(whereStr, tableName, fieldMap);
            for (Map<String, String> filtMap : filtList) {
                SingleFilter singleFilter = new SingleFilter(fieldMap.get(filtMap.get("fieldName"))
                        , filtMap.get("relationshipName"), filtMap.get("condition"));

                singleFilters.add(singleFilter);
            }

            //解析最终投影
            List<String> projections = StringUtil.parseProjection(sentence, tableName, fieldMap);
            projectionMap.put(tableName, projections);


            //读取数据并进行选择操作
            List<Map<String, String>> srcDatas = table.read(singleFilters);
            List<Map<String, String>> datas = associatedTableName(tableName, srcDatas);

            tableDatasMap.put(tableName, datas);
        }


        //解析连接条件，并创建连接对象jion
        List<Map<String, String>> joinConditionMapList = StringUtil.parseWhereJoin(whereStr, fieldMaps);
        List<JoinCondition> joinConditionList = new LinkedList<>();
        for (Map<String, String> joinMap : joinConditionMapList) {
            String tableName1 = joinMap.get("tableName1");
            String tableName2 = joinMap.get("tableName2");
            String fieldName1 = joinMap.get("field1");
            String fieldName2 = joinMap.get("field2");
            Field field1 = fieldMaps.get(tableName1).get(fieldName1);
            Field field2 = fieldMaps.get(tableName2).get(fieldName2);
            String relationshipName = joinMap.get("relationshipName");
            JoinCondition joinCondition = new JoinCondition(tableName1, tableName2, field1, field2, relationshipName);

            joinConditionList.add(joinCondition);

            //将连接条件的字段加入投影中
            projectionMap.get(tableName1).add(fieldName1);
            projectionMap.get(tableName2).add(fieldName2);
        }

        List<Map<String, String>> resultDatas = Join.joinData(tableDatasMap, joinConditionList, projectionMap);
        //log.info(resultDatas);

        //将需要显示的字段名按table.filed的型式存入dataNameList
        List<String> dataNameList = new LinkedList<>();
        for (Map.Entry<String, List<String>> projectionEntry : projectionMap.entrySet()) {
            String projectionKey = projectionEntry.getKey();
            List<String> projectionValues = projectionEntry.getValue();
            for (String projectionValue : projectionValues) {
                dataNameList.add(projectionKey + "." + projectionValue);
            }

        }

        //计算名字长度，用来对齐数据
        int[] len = new int[dataNameList.size()];
        Iterator<String> dataNames = dataNameList.iterator();
        for (int i = 0; i < dataNameList.size(); i++) {
            String dataName = dataNames.next();
            len[i] = dataName.length();
            if(dataName.length()<8){
                System.out.printf("|%s|\t\t\t", dataName);
            }else if(dataName.length()<16){
                System.out.printf("|%s|\t\t", dataName);
            }else if(dataName.length()<24) {
                System.out.printf("|%s|\t", dataName);
            }

        }

        System.out.println("|");

        for (Map<String, String> line : resultDatas) {
            Iterator<String> valueIter = line.values().iterator();
            for (int k : len) {
                String value = valueIter.next();
                System.out.printf("|%s", value);
                System.out.print("\t\t\t\t");
            }
            System.out.println("|");
        }
    }
    /**
     * 将数据整理成tableName.fieldName dataValue的型式
     *
     * @param tableName 表名
     * @param srcDatas 原数据
     * @return 添加表名后的数据
     */
    private static List<Map<String, String>> associatedTableName(String tableName, List<Map<String, String>> srcDatas) {
        List<Map<String, String>> destDatas = new ArrayList<>();
        for (Map<String, String> srcData : srcDatas) {
            Map<String, String> destData = new LinkedHashMap<>();
            for (Map.Entry<String, String> data : srcData.entrySet()) {
                destData.put(tableName + "." + data.getKey(), data.getValue());
            }
            destDatas.add(destData);
        }
        return destDatas;
    }

    public static void alterTableAdd(String tableName, String propertys, String currentDbName) {
        Map<String, Field> fieldMap = StringUtil.parseCreateTable(propertys);
        Table table = TableService.getTable(tableName, currentDbName);
        if (null == table) {
            log.info("未找到表：" + tableName);
            return;
        }
        table.addDict(fieldMap);

    }
}
