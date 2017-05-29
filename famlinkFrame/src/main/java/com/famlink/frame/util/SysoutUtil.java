package com.famlink.frame.util;

import com.famlink.frame.configure.Config;

/**
 * Created by wangkai on 16/7/12.
 */
public class SysoutUtil {
    public static void out(Object out){
        if(Config.is_debug== true){

            System.out.println(out+"");
        }
    }
}
