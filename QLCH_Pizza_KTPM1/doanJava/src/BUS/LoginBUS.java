/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.*;
import DAO.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author dangh
 */
public class LoginBUS {
    public  static  ArrayList<LoginDTO> Arr_login = new ArrayList();
    public  void docdangnhap() throws Exception
    {
        LoginDAO data = new LoginDAO();
        if(Arr_login == null)
            Arr_login = new ArrayList<>();
        Arr_login = data.docdangnhap();
        //ddd
    }
    public static ArrayList<LoginDTO> findTaikhoan(String tuKhoa) {
        ArrayList<LoginDTO> Arr_temp = new ArrayList<>();
        Vector temp = new Vector();
            for (int j = 0; j< Arr_login.size() ;j++) {
                temp.add(Arr_login.get(j).getType());
            }
            for (int i = 0; i< Arr_login.size() ;i++) {
                if(Arr_login.get(i).getId().toLowerCase().contains(tuKhoa.toLowerCase())||
                Arr_login.get(i).getUsername().toLowerCase().contains(tuKhoa.toLowerCase())||
                Arr_login.get(i).getPassword().toLowerCase().contains(tuKhoa.toLowerCase())||
                temp.get(i).toString().contains(tuKhoa))
                    Arr_temp.add(Arr_login.get(i));           
            }
        return Arr_temp;
    }
    public static boolean checkID(String id, ArrayList<LoginDTO> temp){
        for (LoginDTO login: temp){
            if (login.getId().equals(id))
                return true;
        }
        return false;
    }
    public static boolean checkUsername(String id, ArrayList<LoginDTO> temp){
        for (LoginDTO login: temp){
            if (login.getUsername().equals(id))
                return true;
        }
        return false;
    }
}