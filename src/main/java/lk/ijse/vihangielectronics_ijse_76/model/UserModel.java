package lk.ijse.vihangielectronics_ijse_76.model;

import lk.ijse.vihangielectronics_ijse_76.dto.UserDto;
import lk.ijse.vihangielectronics_ijse_76.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {

    public boolean saveUser(UserDto userDTO) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO user ( userName, password, jobRoll, status,email) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(
                sql,
                //userDTO.getUser_Id(),
                userDTO.getUserName(),
                userDTO.getPassword(),
                userDTO.getStatus(),
                userDTO.getEmail(),
                userDTO.getJobRoll()
        );
    }

    public boolean updateUser(UserDto userDTO) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE user SET userName=?, password=?, jobRoll=?, status=? WHERE email=?";
        return CrudUtil.execute(
                sql,
                userDTO.getUserName(),
                userDTO.getPassword(),
                userDTO.getJobRoll(),
                userDTO.getStatus(),
                userDTO.getEmail()
        );
    }

    public boolean deleteUser(String userId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM User WHERE user_id=?";
        return CrudUtil.execute(sql, userId);
    }

    public UserDto searchUser(String userId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM User WHERE user_id=?";
        ResultSet rs = CrudUtil.execute(sql, userId);

        if (rs.next()) {
            return new UserDto(
                    rs.getString("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("job_role"),
                    rs.getString("status")
            );
        }
        return null;
    }

    public List<UserDto> getAllUsers() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM User");
        List<UserDto> userList = new ArrayList<>();

        while (rs.next()) {
            userList.add(new UserDto(
                    rs.getString("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("job_role"),
                    rs.getString("status")
            ));
        }
        return userList;
    }
}