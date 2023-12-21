package com.demo_books_shop.utils;

import java.io.File;

public class FileUtils {
    public static String getImageFolder(){
        return System.getProperty("user.home")+ File.separator + "images" + File.separator;
    }
}
