package course.ums.ws.add;

import com.course.ums.auth.AuthManager;
import com.course.ums.db.DBManager;
import com.course.ums.ws.JSONRoute;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CourseAddTeacher extends JSONRoute {
    @Override
    public JSONObject handleJSONRequest(JSONObject request) throws Exception {
        String token = request.getString("token");
        if(!DBManager.validateToken(token, AuthManager.ROLE_ADMIN)) {
            throw new RuntimeException("Unauthorized!");
        }

        String teachers_id = request.getString("teachers_id");
        String courses_id = request.getString("courses_id");

        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO teachers_courses (teachers_id,courses_id) VALUES(?,?)");
        ps.setString(1, teachers_id);
        ps.setString(2, courses_id);
        ps.execute();

        JSONObject result = new JSONObject();
        PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement("SELECT LAST id FROM teachers_courses");
        preparedStatement.setString(1, request.getString("id"));

        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {


            int id = rs.getInt("id");
            result.put("teachers_courses_id: ", id);
        } else { result.put("Result: ","Teacher course insertion failed!"); }
        return result;
    }

}
