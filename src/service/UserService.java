package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import auth.RoleEnum;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import model.User;

/**
 * @Author: wangjiahao05 <wangjiahao05@.com>
 * Created on 2021-09-24
 */
@Slf4j
public class UserService {

    public static void authorUser(String username, RoleEnum role) {
        File file = new File("WySQL/", "currentUser.info");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            Set<User> users = (Set<User>) ois.readObject();
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    user.setRole(role);
                    oos.writeObject(users);
                    log.info("授权成功:" + username + " " + role);
                    return;
                }
            }
            log.error("授权失败,未找到用户" + username + " " + role);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static User getUser(String username) {
        File file = new File("WySQL/", "currentUser.info");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Set<User> users = (Set<User>) ois.readObject();
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    return user;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void init(String username, String password, RoleEnum role) {
        User user = new User(username, password, role);
        File file = new File("WySQL/", "currentUser.info");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            Set<User> users = new HashSet<>();
            users.add(user);
            log.info("root注册成功,账号:{},密码:{},角色:{}",username,password,role);
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public static void register(String username, String password, RoleEnum role) {
        User user = new User(username, password, role);
        File file = new File("WySQL/", "currentUser.info");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            Set<User> users = (Set<User>) ois.readObject();
            users.add(user);
            log.info("注册成功,账号:{},密码:{},角色:{}",username,password,role);
            oos.writeObject(users);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static User login(String username, String password) {
        File file = new File("WySQL/", "user.info");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Set<User> users = (Set<User>) ois.readObject();
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    if (user.getPassword().equals(password)) {
                        return user;
                    }
                    log.error("用户不存在");
                    return null;
                }
            }
            log.error("密码错误");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteUser(String username) {
        File file = new File("WySQL/", "currentUser.info");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            Set<User> users = (Set<User>) ois.readObject();
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    users.remove(user);
                    log.info("删除成功:{}", username);
                    oos.writeObject(users);
                    return;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}