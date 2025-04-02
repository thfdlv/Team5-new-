package com.example.jsonExam.member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    void registerUser(UserDTO user);

    @Select("SELECT * FROM users WHERE id = #{id} AND pw = #{pw}")
    UserDTO findUserByIdAndPassword(@Param("id") String id, @Param("pw") String password);

    UserDTO getUserByPw(String pw);

    UserDTO getUserById(String id); // ✅ static 제거

    int updateUser(UserDTO user);
    int deleteUser(String id);
    void insertUser(UserDTO user);
}



