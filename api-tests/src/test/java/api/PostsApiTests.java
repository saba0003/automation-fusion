package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ConfigReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Reporter.log;

public class PostsApiTests {

    private static final String BASE_URI = ConfigReader.get("base.uri");

    /** Checked ✅ */
    @Test(priority = 1, groups = {"API", "Positive"})
    public void getAllPosts_shouldReturn200_andAtLeast100Posts() {
        log("==== Starting Test: GET /posts ====", true);

        log("Sending GET request to /posts", true);

        Response response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .when()
                .get("/posts");

        int statusCode = response.statusCode();
        log("Status code received: " + statusCode, true);

        assertThat("Status code should be 200 when successfully retrieving multiple posts",
                statusCode, is(200));

        int postCount = response.jsonPath().getList("$").size();
        log("Number of posts received: " + postCount, true);

        assertThat("There should be at least 100 posts", postCount, greaterThanOrEqualTo(100));

        log("Test Passed: /posts returned 200 and >=100 posts", true);

        log("==== Test Finished ====", true);
    }

    /** Checked ✅ */
    @Test(priority = 2, groups = {"API", "Positive"})
    public void getPostById_shouldReturn200_andUserIdIs1() {
        log("==== Starting Test: GET /posts/1 ====", true);

        log("Sending GET request to /posts/1", true);

        Response response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .when()
                .get("/posts/1");

        int statusCode = response.statusCode();
        log("Status code received: " + statusCode, true);

        assertThat("Status code should be 200 when successfully retrieving a post", statusCode, is(200));

        int userId = response.jsonPath().getInt("userId");
        log("userId received: " + userId, true);

        assertThat("User ID should be 1", userId, is(1));

        log("Test Passed: /posts/1 returned 200 and userId == 1", true);

        log("==== Test Finished ====", true);
    }

    /** Checked ✅ */
    @Test(priority = 3, groups = {"API", "Positive"})
    public void createNewPost_shouldReturn201_andCorrectResponseBody() {
        log("==== Starting Test: POST /posts ====", true);

        int requestUserId = 11;
        String requestTitle = "Post Tested, Sanity - not really.";
        String requestBodyDescription =
                "This whole project is a mix-product of some sleep-deprived coder and a very cooperative AI.";

        log("Preparing POST request body", true);
        String requestBody = String.format(
            """
                {
                    "userId": %d,
                    "title": "%s",
                    "body": "%s"
                }
            """                                                     ,
                requestUserId, requestTitle, requestBodyDescription);

        log("Sending POST request to /posts", true);
        Response response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/posts");

        int statusCode = response.statusCode();
        log("Status code received: " + statusCode, true);

        assertThat("Status code should be 201 when successfully creating new post", statusCode, is(201));

        int responseUserId = response.jsonPath().getInt("userId");
        int responseId = response.jsonPath().getInt("id"); // Usually 101
        String responseTitle = response.jsonPath().getString("title");
        String responseBody = response.jsonPath().getString("body");

        log("Response JSON:\n" +
                "\tid: " + responseId + "\n" +
                "\tuserId: " + responseUserId + "\n" +
                "\ttitle: \"" + responseTitle + "\"\n" +
                "\tbody: \"" + responseBody + "\"",
                true);

        assertThat("User ID should match", responseUserId, is(requestUserId));
        assertThat("ID should be present", responseId, is(101));
        assertThat("Title should match", responseTitle, equalTo(requestTitle));
        assertThat("Body should match", responseBody, equalTo(requestBodyDescription));

        log("Test Passed: POST /posts returned 201 with correct body", true);
        log("==== Test Finished ====", true);
    }

    /** Checked ✅ */
    @Test(priority = 4, groups = {"API", "Positive"})
    public void updatePost_shouldReturn200_andUpdateFieldsCorrectly() {
        log("==== Starting Test: PUT /posts/1 ====", true);

        int updatedUserId = 11;
        String updatedTitle = "What is the downside to eating a clock?";
        String updatedBody = "It's time consuming.";

        log("Preparing PUT request body", true);
        String requestBody = String.format(
        """
            {
                "userId": %d,
                "title": "%s",
                "body": "%s"
            }
        """                                              ,
                updatedUserId, updatedTitle, updatedBody);

        log("Sending PUT request to /posts/1", true);

        Response response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .put("/posts/1");

        int statusCode = response.statusCode();
        log("Status code received: " + statusCode, true);

        assertThat("Status code should be 201 when successfully updating a post", statusCode, is(200));

        int responseUserId = response.jsonPath().getInt("userId");
        int responseId = response.jsonPath().getInt("id");
        String responseTitle = response.jsonPath().getString("title");
        String responseBody = response.jsonPath().getString("body");

        log("Response JSON:\n" +
                "\tid: " + responseId + "\n" +
                "\tuserId: " + responseUserId + "\n" +
                "\ttitle: \"" + responseTitle + "\"\n" +
                "\tbody: \"" + responseBody + "\"",
                true);

        assertThat("User ID should be updated", responseUserId, is(updatedUserId));
        assertThat("Post ID should remain unchanged", responseId, is(1));
        assertThat("Title should be updated", responseTitle, equalTo(updatedTitle));
        assertThat("Body should be updated", responseBody, equalTo(updatedBody));

        log("Test Passed: PUT /posts/1 returned 200 and echoed updated fields", true);
        log("==== Test Finished ====", true);
    }

    /** Checked ✅ */
    @Test(priority = 5, groups = {"API", "Positive"})
    public void deletePost_shouldReturnEither200or204_andEmptyBody() {
        log("==== Starting Test: DELETE /posts/1 ====", true);

        log("Sending DELETE request to /posts/1", true);

        Response response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .when()
                .delete("/posts/1");

        int statusCode = response.statusCode();
        log("Status code received: " + statusCode, true);

        assertThat("Status code should be either 200 or 204 when successfully deleting a post",
                statusCode, anyOf(is(200), is(204)));

        String responseBody = response.getBody().asString();
        log("Response body received: '" + responseBody + "'", true);

        assertThat("Response body should be either empty or whitespace",
                responseBody.trim(),either(is("{}")).or(is(emptyString())));

        log("Test Passed: DELETE /posts/1 returned expected status code and empty body", true);
        log("==== Test Finished ====", true);
    }

    /** Checked ✅ */
    @Test(priority = 6, groups = {"API", "Negative"})
    public void getNonexistentPost_shouldReturn404() {
        log("==== Starting Test: GET /posts/999999 ====", true);

        log("Sending GET request to /posts/999999", true);

        Response response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .when()
                .get("/posts/999999");

        int statusCode = response.statusCode();
        log("Status code received: " + statusCode, true);

        assertThat("Status code should be 404 for nonexistent resource", statusCode, is(404));

        log("Test Passed: GET /posts/999999 returned 404 as expected", true);
        log("==== Test Finished ====", true);
    }

    /** Checked ❓ */
    @Test(priority = 7, groups = {"API", "Negative", "UnexpectedBehavior"})
    public void createPost_withEmptyBody_shouldReturn201EvenThoughItShouldFail() {
        log("==== Starting Test: POST /posts with empty body ====", true);

        log("Sending POST request with empty JSON body", true);

        Response response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .header("Content-Type", "application/json")
                .body("{}")
                .when()
                .post("/posts");

        int statusCode = response.statusCode();
        log("Status code received: " + statusCode, true);

        assertThat("Status code should be 201 when creating a post with empty body even though it should fail",
                statusCode, is(201));

        String responseBody = response.getBody().asString();
        log("Response body received: " + responseBody, true);

        int responseId = response.jsonPath().getInt("id"); // Usually 101

        assertThat("Response should still be a valid JSON object", responseBody.trim(), is(not(emptyString())));
        assertThat("ID should be present", responseId, is(101));

        log("Test Passed: POST /posts returned 201 with empty body", true);
        log("==== Test Finished ====", true);
    }

    /** Checked ✅ */
    @Test(priority = 8, groups = {"API", "Negative"})
    public void updateNonexistentPost_shouldReturn500() {
        log("==== Starting Test: PUT /posts/999999 ====", true);

        int requestUserId = 1;
        String requestTitle = "When two devs tell you that you are a teapot, you must let out the freaking steam.";
        String requestBodyDescription = "Payload contains boiling sarcasm. Endpoint may hiss in protest.";

        log("Preparing PUT request body", true);
        String requestBody = String.format(
        """
            {
                "userId": %d,
                "title": "%s",
                "body": "%s"
            }
        """                                                     ,
            requestUserId, requestTitle, requestBodyDescription);

        log("Sending PUT request to /posts/999999", true);

        Response response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put("/posts/999999");

        int statusCode = response.statusCode();
        log("Status code received: " + statusCode, true);

        assertThat("Status code should be 500 when updating a non-existent post", statusCode, is(500));

        log("Test Passed: PUT /posts/999999 returned 500", true);
        log("==== Test Finished ====", true);
    }

    /** Checked ❓ */
    @Test(priority = 9, groups = {"API", "Negative", "UnexpectedBehavior"})
    public void deleteNonexistentPost_shouldReturn200EvenWhenNothingDeleted() {
        log("==== Starting Test: DELETE /posts/999999 ====", true);

        log("Sending DELETE request to /posts/999999", true);

        Response response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .when()
                .delete("/posts/999999");

        int statusCode = response.statusCode();
        log("Status code received: " + statusCode, true);

        assertThat("Status code should be 200 even when deleting nonexistent post even though it should be 404",
                statusCode, is(200));

        String responseBody = response.getBody().asString();
        log("Response body received: '" + responseBody + "'", true);

        assertThat("Response body should be empty or '{}'",
                responseBody.trim(), anyOf(is("{}"), is(emptyString())));

        log("Test Passed: DELETE /posts/999999 returned 200 with empty body", true);
        log("==== Test Finished ====", true);
    }
}
