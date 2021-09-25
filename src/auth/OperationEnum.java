package auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: wangjiahao05 <wangjiahao05@.com>
 * Created on 2021-09-24
 */
@Getter
@AllArgsConstructor
public enum OperationEnum {
    READ( "读"),
    DML("数据写"),
    DDL("结构写"),
    DCL("权限控制")
            ;


    private String message;

    }
