package course.ums.ws.add;

import com.course.ums.auth.AuthManager;
import com.course.ums.db.DBManager;
import com.course.ums.ws.JSONRoute;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GroupAddTeacher extends JSONRoute{

    @Override
    public JSONObject handleJSONRequest(JSONObject request) throws Exception {
        String token = request.getString("token");
        if(!DBManager.validateToken(token, AuthManager.ROLE_ADMIN)) {
            throw new RuntimeException("Unauthorized!");
        }

        String semester_id = request.getString("group_id");

        PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement("SELECT id FROM semesters WHERE name=?");
        preparedStatement.setString(1, semester_id);
        preparedStatement.executeQuery();

        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO groups (semester_id) VALUES(?)");
        ps.setString(1, semester_id);
        ps.execute();

        JSONObject result = new JSONObject();
        PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement("SELECT id FROM groups WHERE name=?");
        preparedStatement.setString(1, semester_id);

        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {


            int id = rs.getInt("id");
            result.put("course_id: ", id);
        } else { result.put("Result: ","Course insertion failed!"); }
        return result;
    }
}
