package auth;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum RoleEnum {
    DBA("数据库工程师", Arrays.asList(OperationEnum.READ,OperationEnum.DML,OperationEnum.DDL)),
    ADMIN("管理员", Arrays.asList(OperationEnum.READ,OperationEnum.DML,OperationEnum.DCL)),
    USER("用户",Arrays.asList(OperationEnum.READ,OperationEnum.DML)),
    ROOT("超级用户",Arrays.asList(OperationEnum.READ,OperationEnum.DML,OperationEnum.DDL,OperationEnum.DCL))
    ;

    private String desc;
    private List<OperationEnum>allowOperations;
}
