package com.alkemy.ong.util;

public class Constants {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String[] ALL_ROLES = {ROLE_ADMIN, ROLE_USER};

    public static final String URI_PAGE_TESTIMONIAL = "/testimonials/page?numberPage=";

    public static final Integer PAGE_SIZE = 10;

    public static final String FIRST_PAGE = "0";

    public static final Integer FIRST_PAGE_INTEGER = 0;

    public static abstract class Endpoints {
        public static final String ID = "/{id}";
        public static final String PAGE = "/page";
        public static final String USER = "/users";
        public static final String USER_ID = USER + ID;
        public static final String SLIDE = "/slides";
        public static final String SLIDE_ID = SLIDE + ID;
        public static final String CONTACT = "/contacts";
        public static final String ORGANIZATION = "/organization/public";
        public static final String ORGANIZATION_ID = ORGANIZATION + ID;
        public static final String COMMENT = "/comments";
        public static final String COMMENT_ID = COMMENT + ID;
        public static final String NEWS = "/news";
        public static final String NEWS_PAGINATED = NEWS + "**";
        public static final String NEWS_ID = NEWS + ID;
        public static final String NEWS_ID_COMMENT = NEWS + ID + COMMENT;
        public static final String ACTIVITY = "/activities";
        public static final String CATEGORY = "/categories";
        public static final String CATEGORY_ID = CATEGORY + ID;
        public static final String MEMBER = "/members";
        public static final String MEMBER_ID = MEMBER + ID;
        public static final String MEMBER_PAGES = MEMBER + "**";
        public static final String MEMBER_ALL = MEMBER + "/all";
        public static final String API_DOCUMENTATION = "/api/docs";
        public static final String API_UI = "/api/**";
        public static final String API_DESCRIPTION = "/v3/**";
        public static final String API_V2_DESCRIPTION_DOCUMENTATION = "/v2/api-docs";
        public static final String API_V3_DESCRIPTION_DOCUMENTATION = "/v3/api-docs";
        public static final String API_SWAGGER_UI = "/swagger-ui.html";
        public static final String TESTIMONIAL ="/testimonials";
        public static final String TESTIMONIAL_ID = TESTIMONIAL + ID;
        public static final String TESTIMONIAL_PAGE = TESTIMONIAL + PAGE;
        public static final String LOGIN = "/auth/login";
        public static final String REGISTER = "/auth/register";
        public static final String ME = "/auth/me";
    }

    public static abstract class httpCodes{
        public static final String STATUS_OK = "200";
        public static final String STATUS_CREATED = "201";
        public static final String STATUS_ACCEPTED = "202";
        public static final String STATUS_NO_CONTENT = "204";
        public static final String STATUS_BAD_REQUEST = "400";
        public static final String STATUS_UNAUTHORIZED = "401";
        public static final String STATUS_FORBIDDEN = "403";
        public static final String STATUS_NOT_FOUND = "404";
        public static final String STATUS_INTERNAL_SERVER_ERROR = "500";
    }

    public static abstract class messagesForDocs{

        public static final String BEARER_AUTH = "Bearer Authentication";
        public static final String GET_CATEGORIES = "Get all categories";
        public static final String GET_CATEGORIES_SUCCESSFUL = "Get all categories successfully";
        public static final String INVALID_PAGE = "Invalid page number";
        public static final String PAGE_NOT_FOUND = "Page not found";
        public static final String NO_CATEGORIES = "There are no categories";
        public static final String FORBIDDEN = "Action forbidden";
        public static final String GET_CATEGORY_ID = "Get category by id";
        public static final String CATEGORY_NOT_FOUND = "Category not found";
        public static final String GET_CATEGORY_SUCCESSFUL = "Get category successfully";
        public static final String ADD_CATEGORY = "Add a new category";
        public static final String CATEGORY_CREATED = "Category added successfully";
        public static final String BAD_REQUEST = "Bad request";
        public static final String DELETE_CATEGORY = "Delete a category";
        public static final String UPDATE_CATEGORY = "Update a category";
        public static final String CATEGORY_DELETED = "Category deleted successfully";
        public static final String CATEGORY_UPDATED = "Category updated successfully";
        public static final String CREATE_COMMENT = "Create a comment";
        public static final String COMMENT_CREATED = "Comment created successfully";

        public static final String GET_COMMENTS = "Get all comments";
        public static final String GET_COMMENTS_SUCCESSFUL = "Get all comments successfully";
        public static final String NO_COMMENTS = "There are no comments";
        public static final String DELETE_COMMENT = "A user can delete only a comment " +
                "of their own and admin can delete any comment";
        public static final String COMMENT_DELETED = "Comment deleted successfully";
        public static final String COMMENT_NOT_FOUND = "Comment not found";
        public static final String UPDATE_COMMENT = "A user can update only a comment " +
                "of their own and admin can delete any comment";
        public static final String COMMENT_UPDATED = "Comment updated successfully";
        public static final String NEWS_NOT_FOUND = "News not found";
        public static final String UPDATE_COMMENT_UNAUTHORIZED = "Update comment unauthorized";
        public static final String DELETE_COMMENT_UNAUTHORIZED = "Delete comment unauthorized";



    }

    public static abstract class MemberApi{

        public static final String TAG_NAME = "Member";
        public static final String TAG_DESCRIPTION = "The member API";

        public static final String SUCCESS = "This operation was succesful";
        public static final String INVALID_DATA = "The data entered is invalid";
        public static final String NOT_FOUND_MEMBER = "The member was not found with the indicated ID";
        public static final String ERROR_SERVER = "Error of the server";
        public static final String NO_AUTHORIZATION = "Access denied, needed authorization to access";
        public static final String THERE_ARE_NO_MEMBER = "There are no saved members";
        public static final String ERROR_SERVER_ADD = "Server error adding member";
        public static final String ERROR_SERVER_UPDATE = "Server error updating member";

        public static final String SUMARY_ADD = "Add a member";
        public static final String SUMARY_UPDATE = "Update a member";
        public static final String SUMARY_DELETE = "Delete a member";
        public static final String SUMARY_GET_ALL = "Get all members";
        public static final String SUMARY_PAGINATION = "Get member page";

        public static final String DESCRIPTION_ADD = "This endpoint is used to add a member";
        public static final String DESCRIPTION_UPDATE = "This endpoint is used to update a member";
        public static final String DESCRIPTION_DELETE = "This endpoint is used to delete a member by ID";
        public static final String DESCRIPTION_GET_ALL = "This endpoint is used to get all members";
        public static final String DESCRIPTION_PAGINATION = "This endpoint is used to get member page";

        public static final String PARAMETER_ID = "Id of the member";
        public static final String PARAMETER_MEMBER_ADD = "Member to add";
        public static final String PARAMETER_MEMBER_UPDATE = "Member to update";
        public static final String PARAMETER_MEMBER_PAGE = "Number page, size page and sort";

    }

    public static abstract class UserApi{

        public static final String TAG_NAME = "User";
        public static final String TAG_DESCRIPTION = "View, update and delete User";

        public static final String SUCCESS = "This operation was succesful";
        public static final String INVALID_DATA = "The data entered is invalid";
        public static final String NOT_FOUND_USER = "The member was not found with the indicated ID";
        public static final String NO_AUTHORIZATION = "Access denied, needed authorization to access";
        public static final String THERE_ARE_NO_USERS = "There are no saved users";
        public static final String ERROR_SERVER_UPDATE = "Server error updating user";

        public static final String SUMARY_UPDATE = "Update a user";
        public static final String SUMARY_DELETE = "Delete a user";
        public static final String SUMARY_GET_ALL = "Get all users";

        public static final String DESCRIPTION_UPDATE = "This endpoint is used to update a user";
        public static final String DESCRIPTION_DELETE = "This endpoint is used to delete a user by ID";
        public static final String DESCRIPTION_GET_ALL = "This endpoint is used to get all users";

        public static final String PARAMETER_ID = "Id of the user";
        public static final String PARAMETER_USER_UPDATE = "User to update";

    }
}

