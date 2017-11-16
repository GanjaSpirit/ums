package com.course.ums;

import com.course.ums.ws.AddUser;
import com.course.ums.ws.ListUsers;
import com.course.ums.ws.user.Authenticate;
import com.course.ums.ws.user.StudentAdd;
import com.course.ums.ws.user.TeacherAdd;
import course.ums.ws.add.CourseAdd;
import course.ums.ws.add.CourseAddTeacher;
import course.ums.ws.add.GroupAdd;
import course.ums.ws.add.SemesterAdd;
import course.ums.ws.remove.CourseRemoveTeacher;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;


public class Test {
    public static void main(String[] args) throws Exception {

        Spark.port(8080);
        Spark.post("/hello", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                return "world";
            }
        });
        Spark.post("/user/add", new AddUser());
        Spark.post("/user/list", new ListUsers());
        Spark.get("/user/list", new ListUsers());

        Spark.post("user/authenticate", new Authenticate());
        Spark.post("user/student/add", new StudentAdd());
        Spark.post("user/teacher/add", new TeacherAdd());
        Spark.post("user/course/add",new CourseAdd());
        Spark.post("user/semester/add",new SemesterAdd());
        Spark.post("user/group/add",new GroupAdd());
        Spark.post("user/teacher/course/add",new CourseAddTeacher());
        Spark.post("user/teacher/course/remove",new CourseRemoveTeacher());
        Spark.post("user/course/add",new CourseAdd());

    }
}
