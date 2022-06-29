package com.parpiiev.time.controllers;

public interface Paths {
    String INDEX_FILE = "index";
    String INDEX_PAGE = "/";
    String ERROR_FILE = "errors/error";
    String ERROR_FILE_404 = "errors/error-404";
    String ERROR_FILE_500 = "errors/error-500";
    String ERROR_ACCESS_FILE = "errors/error-access";
    String REGISTRATION_PAGE = "/registration";
    String REGISTRATION_FILE = "registration";

    String LOGOUT_PAGE = "/logout";
    String ADMIN_MENU_FILE = "admin-menu";
    String ADMIN_MENU_PAGE = "/admin-menu";
    String USER_MENU_FILE = "user-menu";
    String BACK_TO_USER_MENU_FILE = "redirect:/user-menu";
    String USER_MENU_SEND_DELETE_REQUEST = "/user-menu/delete-request/{activityId}";
    String USER_MENU_PAGE = "/user-menu";
    String ALL_USERS_PAGE = "/admin-menu/all-users";
    String BACK_TO_ALL_USERS_PAGE = "redirect:/admin-menu/all-users";
    String ALL_USERS_FILE = "admin-menu/all-users";

    String USER_DELETE_PAGE = "admin-menu/all-users/delete/{id}";
    String USER_UPDATE_PAGE = "admin-menu/all-users/edit/{id}";
    String USER_UPDATE_FILE = "admin-menu/user-update";

    String ALL_CATEGORIES_PAGE = "/admin-menu/all-categories";
    String ALL_CATEGORIES_FILE = "admin-menu/all-categories";

    String CATEGORY_DELETE_PAGE = "admin-menu/all-categories/delete/{id}";
    String BACK_TO_ALL_CATEGORIES_PAGE = "redirect:/admin-menu/all-categories";
    String CATEGORY_UPDATE_PAGE = "admin-menu/all-categories/edit/{id}";
    String CATEGORY_UPDATE_FILE = "admin-menu/category-update";

    String ALL_USERS_ACTIVITY_PAGE = "/admin-menu/all-users-activity";
    String ALL_USERS_ACTIVITY_FILE = "admin-menu/all-users-activity";
    String USERS_ACTIVITY_DELETE_PAGE = "admin-menu/all-users-activity/delete/{id}";
    String ADMIN_MENU_UPDATE_ACTIVITY_TIME_PAGE = "admin-menu/admin-time-edit/edit/{id}";
    String USERS_ACTIVITY_UPDATE_PAGE = "admin-menu/all-users-activity/edit/{id}/{user_id}/{activityId}";
    String ADMIN_MENU_UPDATE_ACTIVITY_FILE = "admin-menu/admin-time-update";
    String BACK_TO_ALL_USERS_ACTIVITY_PAGE = "redirect:/admin-menu/all-users-activity";

    String ALL_ACTIVITIES_PAGE = "/admin-menu/all-activities";
    String ALL_ACTIVITIES_FILE = "admin-menu/all-activities";
    String ACTIVITIES_DELETE_PAGE = "admin-menu/all-activities/delete/{id}";

    String ACTIVITIES_UPDATE_PAGE = "admin-menu/all-activities/edit/{id}";
    String ACTIVITY_UPDATE_FILE = "admin-menu/activity-update";
    String BACK_TO_ALL_ACTIVITIES_PAGE = "redirect:/admin-menu/all-activities";

    String ALL_REQUESTS_PAGE = "/admin-menu/all-requests";
    String ALL_REQUESTS_FILE = "admin-menu/all-requests";
    String REQUEST_DELETE_PAGE = "/admin-menu/all-requests/delete/{id}/{userId}/{activityId}/{status}";

    String USER_MENU_UPDATE_ACTIVITY_PAGE = "user-menu/time-edit/{id}/{activityId}";
    String USER_MENU_UPDATE_ACTIVITY_FILE = "/user-menu/time-update";
    String USER_MENU_UPDATE_ACTIVITY_TIME_PAGE = "/user-menu/time-edit/{id}";
    String BACK_TO_USER_MENU_PAGE = "redirect:/user-menu";
    String USER_MENU_ADD_REQUEST_FORM_PAGE = "/user-menu/add-request-form/{id}";
    String USER_MENU_ADD_REQUEST_FORM_FILE = "user-menu/add-request-form";
    String USER_MENU_ADD_REQUEST_POST_PAGE = "/user-menu/add-request-form";

    String REQUEST_DECLINE_PAGE = "/admin-menu/all-requests/decline/{id}";
    String BACK_TO_ALL_REQUESTS_PAGE = "redirect:/admin-menu/all-requests";
    String REQUEST_APPROVE_PAGE = "/admin-menu/all-requests/approve/{id}";

}