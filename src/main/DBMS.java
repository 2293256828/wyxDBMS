package main;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import auth.OperationEnum;
import auth.RoleEnum;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import model.Field;
import model.SingleFilter;
import model.Table;
import model.User;
import regex.RegexPatterns;
import service.TableService;
import service.UserService;
import util.StringUtil;


@Slf4j
@Data
public class DBMS {
    private String currentDbName;
    private User currentUser;


    public void start() {
        //model.User currentUser = new model.User("user1", "abc");
        //         UserService.init("root","root",RoleEnum.ROOT);

        Scanner sc = new Scanner(System.in);
        do {
            log.info("\n\t\t----请输入用户名----");
            String loginUserName = sc.nextLine();
            log.info("\n\t\t----请输入密码----");
            String password = sc.nextLine();
            currentUser = UserService.login(loginUserName, password);
            if (null == currentUser) {
                log.error("登录失败");
            } else {
                log.info(currentUser.getUsername() + "登陆成功!");
                break;
            }
        } while (true);
        //model.User.grant(currentUser.getUsername(), model.User.READ_ONLY);
        //currentUser.grant(model.User.READ_ONLY);

        File userFolder = new File("WySQL");
        File[] files = userFolder.listFiles(File::isDirectory);
        List<String> names = Arrays.stream(files).map(File::getName).collect(Collectors.toList());
        log.info("----请输入数据库名----");
        System.out.print("可用:");
        for (String name : names) {
            System.out.print("\t" + name + "\n");
        }
        String db = sc.nextLine();
        File dbFolder = new File(userFolder, db);
        currentDbName = db;
        Table.init(dbFolder.getName());
        log.info("----进入数据库:{}----\n可用表:", db);
        Arrays.stream(Objects.requireNonNull(new File("WySQL", db).listFiles(File::isDirectory)))
                .map(File::getName)
                .collect(Collectors.toList())
                .forEach(item -> {
                    System.out.print(item + "\n");
                });
        String cmd;
        while (!"exit".equals(cmd = sc.nextLine())) {
            cmd = cmd.toLowerCase(Locale.ROOT);
            Matcher matcherGrantAdmin = RegexPatterns.PATTERN_GRANT_ADMIN.matcher(cmd);
            Matcher matcherRevokeUser = RegexPatterns.PATTERN_REVOKE_USER.matcher(cmd);
            Matcher matcherInsert = RegexPatterns.PATTERN_INSERT.matcher(cmd);
            Matcher matcherCreateTable = RegexPatterns.PATTERN_CREATE_TABLE.matcher(cmd);
            Matcher matcherAlterTableAdd = RegexPatterns.PATTERN_ALTER_TABLE_ADD.matcher(cmd);
            Matcher matcherDelete = RegexPatterns.PATTERN_DELETE.matcher(cmd);
            Matcher matcherUpdate = RegexPatterns.PATTERN_UPDATE.matcher(cmd);
            Matcher matcherDropTable = RegexPatterns.PATTERN_DROP_TABLE.matcher(cmd);
            Matcher matcherSelect = RegexPatterns.PATTERN_SELECT.matcher(cmd);
            Matcher matcherDeleteIndex = RegexPatterns.PATTERN_DELETE_INDEX.matcher(cmd);
            while (matcherGrantAdmin.find() && allowDCL(currentUser)) {
                RoleEnum role = convertStringToRole(matcherGrantAdmin.group(1));
                String username = matcherGrantAdmin.group(2);
                if (role == null) {
                    log.error("can not find role:" + role);
                    break;
                }
                UserService.authorUser(username, role);
            }

            //revoke permission from #{username}
            while (matcherRevokeUser.find() && allowDCL(currentUser)) {
                String username = matcherRevokeUser.group(1);
                UserService.deleteUser(username);
            }

            //alter table #{tableName}
            while (matcherAlterTableAdd.find() && allowDML(currentUser)) {
                String tableName = matcherAlterTableAdd.group(1);
                String propertys = matcherAlterTableAdd.group(2);

                TableService.alterTableAdd(tableName, propertys, currentDbName);
            }
            while (matcherDropTable.find() && allowDML(currentUser)) {

                String tableName = matcherDropTable.group(1);

                TableService.dropTable(currentDbName, tableName);
            }
            while (matcherCreateTable.find() && allowDML(currentUser)) {

                String tableName = matcherCreateTable.group(1);
                String propertys = matcherCreateTable.group(2);
                Map<String, Field> fieldMap = StringUtil.parseCreateTable(propertys);

                TableService.createTable(tableName, fieldMap, currentDbName);
            }
            while (matcherDelete.find() && allowDDL(currentUser)) {
                String tableName = matcherDelete.group(1);
                String whereStr = matcherDelete.group(2);
                delete(tableName, whereStr);
            }
            while (matcherUpdate.find() && allowDDL(currentUser)) {
                String tableName = matcherUpdate.group(1);
                String setStr = matcherUpdate.group(2);
                String whereStr = matcherUpdate.group(3);

                update(tableName, setStr, whereStr);
            }
            while (matcherInsert.find() && allowDDL(currentUser)) {
                String tableName = matcherInsert.group(1);
                String[] fieldValues = matcherInsert.group(5).trim().split(",");
                String[] fieldNames = matcherInsert.group(3).trim().split(",");
                boolean setSpecial = matcherInsert.group(2) != null;

                insert(tableName, fieldValues, fieldNames, setSpecial);
            }
            while (matcherSelect.find()) {
                String tableNames = matcherSelect.group(2);
                String whereStr = matcherSelect.group(3);
                String sentence = matcherSelect.group(1);

                TableService
                        .select(tableNames, whereStr, sentence, currentDbName);
            }
            while (matcherDeleteIndex.find() && allowDML(currentUser)) {
                TableService.deleteIndex(matcherDeleteIndex.group(1), currentDbName);
            }
        }
    }

    private boolean allowDCL(User user) {
        if (!user.getRole().getAllowOperations().contains(OperationEnum.DCL)) {
            log.error("无权限进行DCL:{}", user.getRole().name());
            return false;
        }
        return true;
    }

    private boolean allowDML(User user) {
        if (!user.getRole().getAllowOperations().contains(OperationEnum.DML)) {
            log.error("无权限执行DML:{}", user.getRole().name());
            return false;
        }
        return true;
    }

    private boolean allowDDL(User user) {
        if (!user.getRole().getAllowOperations().contains(OperationEnum.DDL)) {
            log.error("无权限执行DDL:{}", user.getRole().name());
            return false;
        }
        return true;
    }

    private RoleEnum convertStringToRole(String role) {

        switch (role) {
            case "admin":
                return RoleEnum.ADMIN;
            case "dba":
                return RoleEnum.DBA;
            case "currentUser":
                return RoleEnum.USER;
            case "root":
                return RoleEnum.ROOT;
            default:
                return null;
        }
    }



    private void insert(String tableName, String[] fieldValues, String[] fieldNames, boolean setSpecial) {

        Table table = TableService.getTable(tableName, currentDbName);
        if (null == table) {
            log.info("未找到表：" + tableName);
            return;
        }
        Map dictMap = table.getFieldMap();
        Map<String, String> data = new HashMap<>();

        //如果插入指定的字段
        if (setSpecial) {
            //如果insert的名值数量不相等，错误
            if (fieldNames.length != fieldValues.length) {
                return;
            }
            for (int i = 0; i < fieldNames.length; i++) {
                String fieldName = fieldNames[i].trim();
                String fieldValue = fieldValues[i].trim();
                //如果在数据字典中未发现这个字段，返回错误
                if (!dictMap.containsKey(fieldName)) {
                    return;
                }
                data.put(fieldName, fieldValue);
            }
        } else {//否则插入全部字段
            Set<String> allFieldNames = dictMap.keySet();
            int i = 0;
            for (String fieldName : allFieldNames) {
                String fieldValue = fieldValues[i].trim();
                data.put(fieldName, fieldValue);
                i++;
            }
        }
        table.insert(data);
    }

    private void update(String tableName, String setStr, String whereStr) {

        Table table = TableService.getTable(tableName, currentDbName);
        if (null == table) {
            log.info("未找到表：" + tableName);
            return;
        }
        Map<String, Field> fieldMap = table.getFieldMap();
        Map<String, String> data = StringUtil.parseUpdateSet(setStr);

        List<SingleFilter> singleFilters = new ArrayList<>();
        if (null == whereStr) {
            table.update(data, singleFilters);
        } else {
            List<Map<String, String>> filtList = StringUtil.parseWhere(whereStr);
            for (Map<String, String> filtMap : filtList) {
                SingleFilter singleFilter = new SingleFilter(fieldMap.get(filtMap.get("fieldName"))
                        , filtMap.get("relationshipName"), filtMap.get("condition"));

                singleFilters.add(singleFilter);
            }
            table.update(data, singleFilters);
        }
    }

    private void delete(String tableName, String whereStr) {

        Table table = TableService.getTable(tableName, currentDbName);
        if (null == table) {
            log.error("未找到表：" + tableName);
            return;
        }

        Map<String, Field> fieldMap = table.getFieldMap();

        List<SingleFilter> singleFilters = new ArrayList<>();
        if (null == whereStr) {
            table.delete(singleFilters);
        } else {
            List<Map<String, String>> filtList = StringUtil.parseWhere(whereStr);
            for (Map<String, String> filtMap : filtList) {
                SingleFilter singleFilter = new SingleFilter(fieldMap.get(filtMap.get("fieldName"))
                        , filtMap.get("relationshipName"), filtMap.get("condition"));
                singleFilters.add(singleFilter);
            }
            table.delete(singleFilters);
        }
    }


}

