package com.example.ex.repo;

import com.example.ex.model.Room;
import com.example.ex.ulti.DBConnection;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomDAOImp implements RoomDAO {
    @Override
    public List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "{CALL view_all_rooms()}";
        try (Connection connection = DBConnection.getInstance().getConnection();
             CallableStatement stmt = connection.prepareCall(sql);) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setRoomName(rs.getString("room_name"));
                room.setRoomType(rs.getString("room_type"));
                room.setStatus(Room.Role.valueOf(rs.getString("status")));
                room.setDelete(rs.getBoolean("is_delete"));
                room.setPrice(rs.getDouble("price"));
                room.setImage(rs.getString("image"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public Room getRoom(int id) {
        String sql = "{CALL get_room_by_id(?)}";
        try (Connection connection = DBConnection.getInstance().getConnection();
             CallableStatement stmt = connection.prepareCall(sql);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setRoomName(rs.getString("room_name"));
                room.setRoomType(rs.getString("room_type"));
                room.setStatus(Room.Role.valueOf(rs.getString("status")));
                room.setDelete(rs.getBoolean("is_delete"));
                room.setPrice(rs.getDouble("price"));
                room.setImage(rs.getString("image"));
                return room;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addRoom(Room room) {
        String sql = "{CALL add_room(?,?,?,?)}";
        try (Connection connection = DBConnection.getInstance().getConnection();
             CallableStatement stmt = connection.prepareCall(sql);) {
            stmt.setString(1, room.getRoomName());
            stmt.setString(2, room.getRoomType());
            stmt.setDouble(3, room.getPrice());
            stmt.setString(4, room.getImage());
            int row = stmt.executeUpdate();
            if (row > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteRoom(int id) {
        String sql = "{CALL delete_room(?)}";
        try (Connection connection = DBConnection.getInstance().getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql);) {
            callableStatement.setInt(1, id);
            int row = callableStatement.executeUpdate();
            if (row > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateRoom(Room room) {
        String sql = "{CALL update_room(?,?,?,?,?,?,?)}";
        try (Connection connection = DBConnection.getInstance().getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql);) {
            callableStatement.setInt(1, room.getId());
            callableStatement.setString(2, room.getRoomName());
            callableStatement.setString(3, room.getRoomType());
            callableStatement.setString(4, String.valueOf(room.getStatus()));
            callableStatement.setBoolean(5, room.isDelete());
            callableStatement.setDouble(6, room.getPrice());
            callableStatement.setString(7, room.getImage());
            int row = callableStatement.executeUpdate();
            if (row > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Room> getRoomsCustomerView() {
        List<Room> rooms = new ArrayList<>();
        String sql = "{CALL get_room_by_where_is_delete_false()}";
        try (Connection connection = DBConnection.getInstance().getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql);) {
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setRoomName(rs.getString("room_name"));
                room.setRoomType(rs.getString("room_type"));
                room.setStatus(Room.Role.valueOf(rs.getString("status")));
                room.setDelete(rs.getBoolean("is_delete"));
                room.setPrice(rs.getDouble("price"));
                room.setImage(rs.getString("image"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public boolean bookRoom(int id) {
        String sql = "{CALL booked_room(?)}";
        try (Connection connection = DBConnection.getInstance().getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql);) {
            callableStatement.setInt(1, id);
            int row = callableStatement.executeUpdate();
            if (row > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
