package course.ums.ws.add;

import com.course.ums.auth.AuthManager;
import com.course.ums.db.DBManager;
import com.course.ums.ws.JSONRoute;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SemesterAdd extends JSONRoute {
    @Override
    public JSONObject handleJSONRequest(JSONObject request) throws Exception {
        String token = request.getString("token");
        if(!DBManager.validateToken(token, AuthManager.ROLE_ADMIN)) {
            throw new RuntimeException("Unauthorized!");
        }

        String year = request.getString("year");
        String index = request.getString("index");

        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO semesters (year,index) VALUES(?,?)");
        ps.setString(1, year);
        ps.setString(2, index);
        ps.execute();

        JSONObject result = new JSONObject();
        PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement("SELECT id FROM semesters WHERE year=?");
        preparedStatement.setString(1, year);

        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {


            int id = rs.getInt("id");
            result.put("semester_id: ", id);
        } else { result.put("Result: ","Semester insertion failed!"); }
        return result;
    }

}

