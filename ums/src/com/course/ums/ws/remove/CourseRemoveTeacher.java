package course.ums.ws.remove;

import com.course.ums.auth.AuthManager;
import com.course.ums.db.DBManager;
import com.course.ums.ws.JSONRoute;

import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class CourseRemoveTeacher extends JSONRoute {
    @Override
    public JSONObject handleJSONRequest(JSONObject request) throws Exception {
        String token = request.getString("token");
        if(DBManager.validateToken(token, AuthManager.ROLE_ADMIN)) {
            throw new RuntimeException("Unauthorized!");
        }

        String teachers_courses_id = request.getString("teachers_courses_id");


        PreparedStatement ps = DBManager.getConnection().prepareStatement("DELETE FROM teachers_courses WHERE id = ?");
        ps.setString(1, teachers_courses_id);


        int rowsDeleted = ps.executeUpdate();


        PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement("SELECT LAST id FROM teachers_courses ");
        preparedStatement.setString(1, request.getString("id"));


        ResultSet result = preparedStatement.executeQuery();
        String id = result.getString(1);

        JSONObject result = new JSONObject();

        if (rowsDeleted > 0) {


            System.out.println("A user was deleted successfully!");
            result.put("teachers_courses_id: ", id);
        }  else { result.put("Result: ","Teacher course deletion failed!"); }

        return result;
    }

}