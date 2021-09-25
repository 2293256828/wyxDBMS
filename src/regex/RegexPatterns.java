package regex;

import java.util.regex.Pattern;

/**
 * @Author: wangjiahao05 <wangjiahao05@kuaishou.com>
 * Created on 2021-09-25
 */
public class RegexPatterns {
    public static void main(String[] args) {
        Pattern a=Pattern.compile("abc?");
        System.out.println(a.matcher("ab").matches());
        System.out.println(a.matcher("a").matches());
        System.out.println(a.matcher("abd").matches());
    }
    //insert into  #{tableName}(#{field1},#{field2},#{field3}) values(qdsa,ads,adsads);
    //insert into  #{tableName} values(qdsa,ads,adsads);

    public static final Pattern PATTERN_INSERT =
            Pattern.compile("insert\\s+into\\s+(\\w+)\\s*(\\(((\\w+,?)+)\\))?\\s+\\w+\\((([^)]+,?)+)\\);?");

    //create table #{tableName}(#{field} #{type},#{field} #{type},);
    public static final Pattern PATTERN_CREATE_TABLE =
            Pattern.compile("create\\s+table\\s+(\\w+)\\s*\\(((?:\\s*\\w+\\s+\\w+,?)+)\\)\\s*;?");
    public static final Pattern PATTERN_ALTER_TABLE_ADD =
            Pattern.compile("alter\\s+table\\s+(\\w+)\\s+add\\s+(\\w+\\s+\\w+)\\s*;");
    public static final Pattern PATTERN_DELETE = Pattern.compile(
            "delete\\s+from\\s+(\\w+)(?:\\s+where\\s+(\\w+\\s*[<=>]\\s*[^\\s+;]+(?:\\s+and\\s+(?:\\w+)\\s*(?:[<=>])\\s*"
                    + "(?:[^\\s+;]+))*))?\\s*;");
    public static final Pattern PATTERN_UPDATE = Pattern.compile(
            "update\\s+(\\w+)\\s+set\\s+(\\w+\\s*=\\s*[^,\\s+]+(?:\\s*,\\s*\\w+\\s*=\\s?[^,\\s+]+)*)(?:\\s+where\\s+"
                    + "(\\w+\\s*[<=>]\\s*[^\\s+;]+(?:\\s+and\\s+(?:\\w+)\\s*(?:[<=>])\\s*(?:[^\\s+;]+))*))?\\s*;");
    public static final Pattern PATTERN_DROP_TABLE = Pattern.compile("drop\\s+table\\s+(\\w+);");
    public static final Pattern PATTERN_SELECT = Pattern.compile(
            "select\\s+(\\*|(?:(?:\\w+(?:\\.\\w+)?)+(?:\\s*,\\s*\\w+(?:\\.\\w+)?)*))\\s+from\\s+(\\w+(?:\\s*,\\s*\\w+)*)"
                    + "(?:\\s+where\\s+([^;]+\\s*;))?");
    public static final Pattern PATTERN_DELETE_INDEX = Pattern.compile("delete\\s+index\\s+(\\w+)\\s*;");
    public static final Pattern PATTERN_GRANT_ADMIN = Pattern.compile("grant\\s+(\\w+)\\s+to\\s+([^;\\s+]+)\\s*;");
    public static final Pattern PATTERN_REVOKE_USER = Pattern.compile("revoke\\s+permisson\\s+from\\s+([^;\\s+]+)\\s*;");
    public static final Pattern PATTERN_END=Pattern.compile("\\s*end\\s*");
    public static final Pattern PATTERN_BEGIN=Pattern.compile("\\s*begin\\s*");
}
