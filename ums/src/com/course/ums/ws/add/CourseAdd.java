package course.ums.ws.add;

import com.course.ums.auth.AuthManager;
import com.course.ums.db.DBManager;
import com.course.ums.ws.JSONRoute;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CourseAdd extends JSONRoute {
    @Override
    public JSONObject handleJSONRequest(JSONObject request) throws Exception {
        String token = request.getString("token");
        if(!DBManager.validateToken(token, AuthManager.ROLE_ADMIN)) {
            throw new RuntimeException("Unauthorized!");
        }

        String course_name = request.getString("course_name");

        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO courses (name) VALUES(?)");
        ps.setString(1, course_name);
        ps.execute();

        JSONObject result = new JSONObject();
        PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement("SELECT id FROM courses WHERE name=?");
        preparedStatement.setString(1, course_name);

        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {


            int id = rs.getInt("id");
            result.put("course_id: ", id);
        } else { result.put("Result: ","Course insertion failed!"); }
        return result;
    }

}
