package com.egs.shop.model.constant;

public class ExceptionMessages {
    public static final String USER_NOT_FOUND = "User not found!";
    public static final String MALFORMED_JSON_REQUEST = "There is a malformed json request";
    public static final String UNEXPECTED_ERROR = "Unexpected error occurred";
    public static final String INTERNAL_ERROR = "Internal error occurred";
    public static final String DB_ERROR = "Database transaction error occurred";
    public static final String USERNAME_ALREADY_EXISTS = "Username already used for another account!";
    public static final String EMAIL_ALREADY_EXISTS = "Email already used for another account!";
    public static final String PASSWORD_NOT_MATCHED = "Password and confirm password not matched!";
    public static final String USER_BLOCKED = "User has been blocked!";
    public static final String USER_NOT_ACTIVATED = "User not activated yet.";
    public static final String CATEGORY_NOT_FOUND = "Category not found!";
    public static final String CATEGORY_ALREADY_EXISTS = "A category with the same title already exists!";
    public static final String PRODUCT_NOT_FOUND = "Product not found!";
    public static final String COMMENT_NOT_FOUND = "Requested comment not found!";
    public static final String AUTHENTICATED_USER_NOT_FOUND = "Authenticated user not found. Please login again.";
    public static final String COMMENT_ACCESS_DENIED = "You don't have access to this comment";
    public static final String COMMENT_ALREADY_EXISTS = "You have already commented on this product";
}
