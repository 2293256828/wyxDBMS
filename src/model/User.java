package model;

import java.io.*;
import java.util.Set;

import auth.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
@AllArgsConstructor
public class User implements Serializable {
    private String username;
    private String password;
    private RoleEnum role;
}
