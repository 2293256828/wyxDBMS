package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SingleFilter {
    private Field field;
    private String relationshipName;
    private String condition;


    /**
     * @param srcDatas 原数据
     * @return 过滤后的数据
     */
    public List<Map<String, String>> singleFiltData(List<Map<String, String>> srcDatas) {
        // model.Field field, model.Relationship relationship, String condition
        Relationship relationship = Relationship.parseRel(relationshipName);
        List<Map<String, String>> datas = new ArrayList<>();
        //如果没有限定条件，返回原始列表
        if (null == field || null == relationship) {
            return srcDatas;
        }
        for (Map<String, String> srcData : srcDatas) {
            //如果条件匹配成功,则新的列表存储此条数据
            if (Relationship.matchCondition(srcData, field, relationship, condition)) {
                datas.add(srcData);
            }
        }
        return datas;
    }



    public Relationship getRelationship() {
        return Relationship.parseRel(relationshipName);
    }
}
